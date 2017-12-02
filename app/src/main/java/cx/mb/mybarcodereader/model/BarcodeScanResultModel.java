package cx.mb.mybarcodereader.model;

import android.graphics.Bitmap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Barcode result model.
 */
@Getter
@Setter
@ToString
public class BarcodeScanResultModel implements Serializable {

    /**
     * if scanned return true.
     */
    private boolean scanned;

    /**
     * Barcode type string.
     */
    private String type;

    /**
     * Text.
     */
    private String text;

    /**
     * Scanned bitmap.
     */
    private Bitmap bitmap;

    public static BarcodeScanResultModel getDefault() {
        final BarcodeScanResultModel model = new BarcodeScanResultModel();
        model.setScanned(false);
        model.setType("");
        model.setText("");
        model.setBitmap(null);

        return model;
    }
}
