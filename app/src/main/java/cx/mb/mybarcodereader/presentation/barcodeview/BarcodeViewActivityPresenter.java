package cx.mb.mybarcodereader.presentation.barcodeview;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import java.util.List;

/**
 * Presenter class for BarcodeViewActivity.
 */
public interface BarcodeViewActivityPresenter extends BarcodeCallback {
    @Override
    void barcodeResult(BarcodeResult result);

    @Override
    void possibleResultPoints(List<ResultPoint> resultPoints);

    /**
     * onPause.
     */
    void onPause();

    /**
     * onResume.
     */
    void onResume();
}
