package cx.mb.mybarcodereader.consumer;


import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.orma.Barcode;
import cx.mb.mybarcodereader.orma.OrmaDatabase;
import cx.mb.mybarcodereader.service.HashService;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Consumer of barcode scanned.
 */
public class BarcodeScanResultConsumer implements Consumer<BarcodeScanResultModel> {

    /**
     * Hash service.
     */
    private final HashService hash;

    /**
     * Result interface.
     */
    private final BarcodeScanResult parent;

    /**
     * Orma database.
     */
    private final OrmaDatabase database;

    /**
     * Constructor.
     * @param parent result destination.
     * @param hash hash service.
     * @param database database.
     */
    @Inject
    public BarcodeScanResultConsumer(BarcodeScanResult parent, HashService hash, OrmaDatabase database) {
        this.parent = parent;
        this.hash = hash;
        this.database = database;
    }

    @Override
    public void accept(BarcodeScanResultModel result) {

        Timber.d("isScanned:%s", result);

        final String primaryKey = !result.isScanned() ? "" : UUID.randomUUID().toString() + "_" + hash.calculate(result.getText());
        try {
            if (!result.isScanned()) {
                return;
            }

            Barcode item = new Barcode();
            item.setKey(primaryKey);

            item.setType(result.getType());
            item.setText(result.getText());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            result.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
            item.setImage(bos.toByteArray());
            item.setCreateAt(new Date());

            database.insertIntoBarcode(item);

        } finally {
            parent.notify(result.isScanned(), primaryKey);
        }
    }
}
