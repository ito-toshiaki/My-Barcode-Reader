package cx.mb.mybarcodereader.consumer;


import android.graphics.Bitmap;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.realm.RealmBarcode;
import cx.mb.mybarcodereader.service.HashService;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import timber.log.Timber;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

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
     * Constructor.
     * @param parent result destination.
     * @param hash hash service.
     */
    @Inject
    public BarcodeScanResultConsumer(BarcodeScanResult parent, HashService hash) {
        this.parent = parent;
        this.hash = hash;
    }

    @Override
    public void accept(BarcodeScanResultModel result) {

        Timber.d("isScanned:%s", result);

        final String primaryKey = !result.isScanned() ? "" : UUID.randomUUID().toString() + "_" + hash.calculate(result.getText());
        try {
            if (!result.isScanned()) {
                return;
            }

            try (Realm realm = Realm.getDefaultInstance()) {
                Timber.d("PrimaryKey:%s", primaryKey);

                realm.executeTransaction(_realm -> {
                    final long count = _realm.where(RealmBarcode.class).equalTo("key", primaryKey).count();
                    if (count > 0) {
                        Timber.w("pk:%s is already exists.", primaryKey);
                        return;
                    }
                    final RealmBarcode obj = _realm.createObject(RealmBarcode.class, primaryKey);
                    obj.setType(result.getType());
                    obj.setText(result.getText());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    result.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
                    obj.setImage(bos.toByteArray());
                    obj.setCreateAt(new Date());
                });
            }
        } finally {
            parent.notify(result.isScanned(), primaryKey);
        }
    }
}
