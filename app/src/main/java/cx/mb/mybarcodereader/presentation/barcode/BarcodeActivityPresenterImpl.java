package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import timber.log.Timber;

import java.util.List;
import java.util.Map;

/**
 * Presenter class for BarcodeActivity.
 */
public class BarcodeActivityPresenterImpl implements BarcodeActivityPresenter {

    /**
     * Parent activity.
     */
    private Activity parent;

    @Override
    public void barcodeResult(BarcodeResult result) {

        final BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        final Bitmap bitmap = result.getBitmap();
        switch (barcodeFormat) {
            case CODABAR:
                final Map<ResultMetadataType, Object> resultMetadata = result.getResult().getResultMetadata();
                break;
            case CODE_39:
                break;
            case QR_CODE:
                break;
            default:
                Toast.makeText(parent, "UNSUPPORTED FORMAT.", Toast.LENGTH_SHORT).show();

        }

        Timber.d(result.toString());
        Toast.makeText(parent, result.getText(), Toast.LENGTH_SHORT).show();
//        ((BarcodeActivity)parent).barcodeView.pause();
        Handler handler = new Handler();
        handler.postDelayed(() -> ((BarcodeActivity)parent).barcodeView.decodeSingle(this), 1000);
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    @Override
    public void onCreate(Activity parent) {
       this.parent = parent;
    }

    @Override
    public void onDestroy() {
       // do nothing.
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
