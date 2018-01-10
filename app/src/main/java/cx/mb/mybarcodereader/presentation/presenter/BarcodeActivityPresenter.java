package cx.mb.mybarcodereader.presentation.presenter;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import java.util.List;

import javax.inject.Inject;

import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.consumer.BarcodeScanResult;
import cx.mb.mybarcodereader.consumer.BarcodeScanResultConsumer;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.orma.Barcode;
import cx.mb.mybarcodereader.orma.OrmaDatabase;
import cx.mb.mybarcodereader.presentation.activity.BarcodeActivity;
import cx.mb.mybarcodereader.service.HashService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Presenter class for BarcodeActivity.
 */
public class BarcodeActivityPresenter implements BarcodeScanResult, BarcodeCallback {

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
     * @param database    database.
     */
    @Inject
    public BarcodeActivityPresenter(HashService hashService, OrmaDatabase database) {
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
        // do nothing.
    }

    /**
     * onCreate.
     * @param parent parent activity.
     */
    public void onCreate(Activity parent) {
        this.parent = (BarcodeActivity) parent;
        Disposable subscribe = status.subscribe(new BarcodeScanResultConsumer(this, this.hashService, this.database));
        disposables.add(subscribe);
    }

    /**
     * onDestroy.
     */
    public void onDestroy() {
        disposables.clear();
    }

    /**
     * Start camera.
     */
    public void startCamera() {
        status.onNext(BarcodeScanResultModel.getDefault());
    }

    /**
     * Return scanned.
     * @return scanned.
     */
    public boolean isScanned() {
        final BarcodeScanResultModel value = this.status.getValue();
        return value != null && value.isScanned();
    }

    @Override
    public void notify(boolean scanned, String id) {

        if (!scanned) {
            parent.update("", "");
            parent.resumeScan();
            parent.getRestart().setVisibility(View.GONE);
            new Handler().postDelayed(() -> parent.getBarcodeView().decodeSingle(this), 1000);
            return;
        }

        final Barcode item = database.selectFromBarcode().keyEq(id).valueOrNull();
        if (item == null) {
            this.status.onNext(BarcodeScanResultModel.getDefault());
            return;
        }
        parent.update(item.type, item.text);

        parent.pauseScan();
        parent.getRestart().setVisibility(View.VISIBLE);
    }
}
