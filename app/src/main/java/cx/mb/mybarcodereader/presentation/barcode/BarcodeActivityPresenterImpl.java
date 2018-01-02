package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;

import java.util.List;

import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.consumer.BarcodeScanResult;
import cx.mb.mybarcodereader.consumer.BarcodeScanResultConsumer;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.orma.Barcode;
import cx.mb.mybarcodereader.orma.OrmaDatabase;
import cx.mb.mybarcodereader.service.HashService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Presenter class for BarcodeActivity.
 */
public class BarcodeActivityPresenterImpl implements BarcodeActivityPresenter, BarcodeScanResult {

    /**
     * Parent activity.
     */
    private BarcodeActivity parent;

    /**
     * Scan status.
     */
    private final BehaviorSubject<BarcodeScanResultModel> status = BehaviorSubject.create();

    /**
     * Hash service.
     */
    private final HashService hashService;

    /**
     * Orma database.
     */
    private final OrmaDatabase database;

    /**
     * Disposable items.
     */
    private final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Constructor.
     *
     * @param hashService hash service.
     * @param database database.
     */
    public BarcodeActivityPresenterImpl(HashService hashService, OrmaDatabase database) {
        this.hashService = hashService;
        this.database = database;
    }

    @Override
    public void barcodeResult(BarcodeResult result) {

        Timber.d(result.toString());

        final BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        if (barcodeFormat != BarcodeFormat.CODABAR && barcodeFormat != BarcodeFormat.CODE_39 && barcodeFormat != BarcodeFormat.QR_CODE) {
            Toast.makeText(parent, R.string.error_unsupported_barcode_format, Toast.LENGTH_SHORT).show();
            return;
        }

        final BarcodeScanResultModel model = new BarcodeScanResultModel();
        model.setScanned(true);
        model.setType(barcodeFormat.name());
        model.setText(result.getText());
        model.setBitmap(result.getBitmap());

        status.onNext(model);
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    @Override
    public void onCreate(Activity parent) {
        this.parent = (BarcodeActivity) parent;
        Disposable subscribe = status.subscribe(new BarcodeScanResultConsumer(this, this.hashService, this.database));
        disposables.add(subscribe);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    @Override
    public void startCamera() {
        status.onNext(BarcodeScanResultModel.getDefault());
    }

    @Override
    public boolean isScanned() {
        return this.status.getValue().isScanned();
    }

    @Override
    public void notify(boolean scanned, String id) {

        if (!scanned) {
            parent.update("", "");
            parent.resumeScan();
            parent.restart.setVisibility(View.GONE);
            new Handler().postDelayed(() -> parent.barcodeView.decodeSingle(BarcodeActivityPresenterImpl.this), 1000);
            return;
        }

        final Barcode item = database.selectFromBarcode().keyEq(id).valueOrNull();
        if (item == null) {
            this.status.onNext(BarcodeScanResultModel.getDefault());
            return;
        }
        parent.update(item.getType(), item.getText());

        parent.pauseScan();
        parent.restart.setVisibility(View.VISIBLE);
    }
}
