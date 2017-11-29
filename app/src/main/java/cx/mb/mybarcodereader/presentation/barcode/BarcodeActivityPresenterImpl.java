package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import cx.mb.mybarcodereader.consumer.BarcodeScanResultConsumer;
import cx.mb.mybarcodereader.event.BarcodeScanResultEvent;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.realm.BarcodeRealm;
import cx.mb.mybarcodereader.service.HashService;
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

    /**
     * Hash service.
     */
    private HashService hashService;

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
            Toast.makeText(parent, "UNSUPPORTED FORMAT.", Toast.LENGTH_SHORT).show();
            return;
        }

        final BarcodeScanResultModel model = new BarcodeScanResultModel();
        model.setScanned(true);

//        final String type;
//        switch (barcodeFormat) {
//            case CODABAR:
//                type = parent.getString(R.string.barcode_type_codabar);
//                break;
//            case CODE_39:
//                type = parent.getString(R.string.barcode_type_code_39);
//                break;
//            case QR_CODE:
//                type = parent.getString(R.string.barcode_type_qr);
//                break;
//            default:
//                throw new RuntimeException(String.format(Locale.ENGLISH, "Barcode Format:%s", barcodeFormat.name()));
//        }
        model.setType(barcodeFormat.name());
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
        isScanned.subscribe(new BarcodeScanResultConsumer(this.hashService));

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

            final BarcodeRealm item = realm.where(BarcodeRealm.class).equalTo("key", event.getKey()).findFirst();
            if (item == null) {
                this.isScanned.onNext(BarcodeScanResultModel.getDefault());
                return;
            }

            ((BarcodeActivity) parent).updateText(item.getType(), item.getText());
            ((BarcodeActivity) parent).pauseScan();
        } else {
            ((BarcodeActivity) parent).updateText("", "");
            ((BarcodeActivity) parent).resumeScan();
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
