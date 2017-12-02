package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import java.util.List;

/**
 * Presenter class for BarcodeActivity.
 */
public interface BarcodeActivityPresenter extends BarcodeCallback {

    @Override
    void barcodeResult(BarcodeResult result);

    @Override
    void possibleResultPoints(List<ResultPoint> resultPoints);

    /**
     * onCreate.
     * @param parent parent activity.
     */
    void onCreate(Activity parent);

    /**
     * onDestroy.
     */
    void onDestroy();

    /**
     * start camera.(default)
     */
    void startCamera();
}
