package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.consumer.BarcodeScanResultConsumer;
import cx.mb.mybarcodereader.event.BarcodeScanResultEvent;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.realm.BarcodeRealm;
import cx.mb.mybarcodereader.service.HashService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.realm.Realm;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

import java.util.List;

/**
 * Presenter class for BarcodeActivity.
 */
public class BarcodeActivityPresenterImpl implements BarcodeActivityPresenter {

    /**
     * Parent activity.
     */
    private BarcodeActivity parent;

    /**
     * Realm.
     */
    private Realm realm;

    /**
     * Scan status.
     */
    private final BehaviorSubject<BarcodeScanResultModel> status = BehaviorSubject.create();

    /**
     * Hash service.
     */
    private final HashService hashService;

    /**
     * Disposable items.
     */
    private final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Constructor.
     *
     * @param hashService hash service.
     */
    public BarcodeActivityPresenterImpl(HashService hashService) {
        this.hashService = hashService;
    }

    @Override
    public void barcodeResult(BarcodeResult result) {

        Timber.d(result.toString());
        Toast.makeText(parent, result.getText(), Toast.LENGTH_SHORT).show();

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
        Disposable subscribe = status.subscribe(new BarcodeScanResultConsumer(this.hashService));
        disposables.add(subscribe);

        realm = Realm.getDefaultInstance();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        EventBus.getDefault().unregister(this);
        realm.close();
    }

    @Override
    public void startCamera() {
        status.onNext(BarcodeScanResultModel.getDefault());
    }

    /**
     * Event notification from Consumer.
     *
     * @param event event.
     */
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBarcodeScanEvent(BarcodeScanResultEvent event) {

        if (!event.isScanned()) {
            parent.update("", "");
            parent.resumeScan();
            parent.restart.setVisibility(View.GONE);
            new Handler().postDelayed(() -> parent.barcodeView.decodeSingle(BarcodeActivityPresenterImpl.this), 1000);
            return;
        }

        final BarcodeRealm item = realm.where(BarcodeRealm.class).equalTo("key", event.getKey()).findFirst();
        if (item == null) {
            this.status.onNext(BarcodeScanResultModel.getDefault());
            return;
        }

        parent.update(item.getType(), item.getText());
        parent.pauseScan();
        parent.restart.setVisibility(View.VISIBLE);
    }
}
