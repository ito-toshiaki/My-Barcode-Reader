package cx.mb.mybarcodereader.consumer;


import android.graphics.Bitmap;
import cx.mb.mybarcodereader.event.BarcodeScanResultEvent;
import cx.mb.mybarcodereader.model.BarcodeScanResultModel;
import cx.mb.mybarcodereader.realm.BarcodeRealm;
import cx.mb.mybarcodereader.service.HashService;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import org.greenrobot.eventbus.EventBus;
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
    private HashService hash;

    /**
     * Constructor.
     * @param hash hash service.
     */
    @Inject
    public BarcodeScanResultConsumer(HashService hash) {
        this.hash = hash;
    }

    @Override
    public void accept(BarcodeScanResultModel result) throws Exception {

        Timber.d("isScanned:%s", result);

        try {
            if (!result.isScanned()) {
                return;
            }

            try (Realm realm = Realm.getDefaultInstance()) {
                final String primaryKey = UUID.randomUUID().toString() + "_" + hash.calculate(result.getText());
                Timber.d("PrimaryKey:%s", primaryKey);

                realm.executeTransaction(_realm -> {
                    final long count = _realm.where(BarcodeRealm.class).equalTo("key", primaryKey).count();
                    if (count > 0) {
                        Timber.d("pk:%s is already exists.", primaryKey);
                        return;
                    }
                    final BarcodeRealm obj = _realm.createObject(BarcodeRealm.class, result.getText());
                    obj.setText(result.getText());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    result.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bos);
                    obj.setImage(bos.toByteArray());
                    obj.setCreateAt(new Date());
                });
            }
        } finally {
            if (EventBus.getDefault().hasSubscriberForEvent(BarcodeScanResultEvent.class)) {
                EventBus.getDefault().post(new BarcodeScanResultEvent(result.isScanned()));
            } else {
                Timber.w("BarcodeScanResultEvent is not subscribed.");
            }
        }
    }
}
