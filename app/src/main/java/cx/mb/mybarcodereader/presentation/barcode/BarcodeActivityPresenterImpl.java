package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import cx.mb.mybarcodereader.BarcodeScanResultEvent;
import cx.mb.mybarcodereader.consumer.BarcodeScanResultConsumer;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
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
    private Activity parent;

    /**
     * Realm.
     */
    private Realm realm;

    /**
     * if scanned, set true.
     */
    private BehaviorSubject<BarcodeScanResultModel> isScanned = BehaviorSubject.create();

    @Override
    public void barcodeResult(BarcodeResult result) {

        Timber.d(result.toString());
        Toast.makeText(parent, result.getText(), Toast.LENGTH_SHORT).show();

        final BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        if (barcodeFormat != BarcodeFormat.CODABAR && barcodeFormat != BarcodeFormat.CODE_39 && barcodeFormat != BarcodeFormat.QR_CODE) {
            Toast.makeText(parent, "UNSUPPORTED FORMAT.", Toast.LENGTH_SHORT).show();
            return;
        }

        final BarcodeScanResultModel model = new BarcodeScanResultModel();
        model.setScanned(true);
        model.setText(result.getText());
        model.setBitmap(result.getBitmap());

        isScanned.onNext(model);
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    @Override
    public void onCreate(Activity parent) {
        this.parent = parent;
        isScanned.subscribe(new BarcodeScanResultConsumer());

        realm = Realm.getDefaultInstance();
        EventBus.getDefault().register(this);
        isScanned.onNext(BarcodeScanResultModel.getDefault());
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        realm.close();
    }

    @Override
    public void restart() {
       isScanned.onNext(BarcodeScanResultModel.getDefault());
    }

    /**
     * Event notification from Consumer.
     *
     * @param event event.
     */
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBarcodeScanEvent(BarcodeScanResultEvent event) {
        if (event.isScanned()) {
            ((BarcodeActivity) parent).barcodeView.pause();
        } else {
            ((BarcodeActivity) parent).barcodeView.resume();
            new Handler().postDelayed(() -> ((BarcodeActivity) parent).barcodeView.decodeSingle(BarcodeActivityPresenterImpl.this), 1000);
        }
    }

//    @Override
//    public void onPause() {
//
//    }
//
//    @Override
//    public void onResume() {
//
//    }
}
