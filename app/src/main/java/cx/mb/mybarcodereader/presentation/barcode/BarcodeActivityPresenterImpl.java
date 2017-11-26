package cx.mb.mybarcodereader.presentation.barcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import cx.mb.mybarcodereader.realm.BarcodeRealm;
import io.realm.Realm;
import io.realm.RealmQuery;
import timber.log.Timber;

import java.io.ByteArrayOutputStream;
import java.util.Date;
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

    /**
     * Realm.
     */
    private Realm realm;

    @Override
    public void barcodeResult(BarcodeResult result) {

        Timber.d(result.toString());
        Toast.makeText(parent, result.getText(), Toast.LENGTH_SHORT).show();

        final BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        final Bitmap bitmap = result.getBitmap();
        if (barcodeFormat != BarcodeFormat.CODABAR && barcodeFormat != BarcodeFormat.CODE_39 && barcodeFormat != BarcodeFormat.QR_CODE) {
            Toast.makeText(parent, "UNSUPPORTED FORMAT.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            final String primaryKey = result.getText();
            realm.executeTransaction(realm -> {
                final long count = realm.where(BarcodeRealm.class).equalTo("key", primaryKey).count();
                if (count > 0) {
                    Timber.d("pk:%s is already exists.", primaryKey);
                    return;
                }

                final BarcodeRealm obj = realm.createObject(BarcodeRealm.class, result.getText());
                obj.setText(result.getText());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                obj.setImage(bos.toByteArray());
                obj.setCreateAt(new Date());
            });

        } finally {
            Handler handler = new Handler();
            handler.postDelayed(() -> ((BarcodeActivity) parent).barcodeView.decodeSingle(this), 1000);
        }
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    @Override
    public void onCreate(Activity parent) {
       this.parent = parent;
       realm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        realm.close();
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
