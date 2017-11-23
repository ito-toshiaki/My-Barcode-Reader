package cx.mb.mybarcodereader.presentation.barcodeview;

import android.app.Activity;
import android.widget.Toast;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import timber.log.Timber;

import java.util.List;

/**
 * Presenter class for BarcodeViewActivity.
 */
public class BarcodeViewActivityPresenterImpl implements BarcodeViewActivityPresenter {

    /**
     * Parent activity.
     */
    private Activity parent;

    @Override
    public void barcodeResult(BarcodeResult result) {
        Timber.d(result.toString());
        Toast.makeText(parent, result.getText(), Toast.LENGTH_SHORT).show();
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
