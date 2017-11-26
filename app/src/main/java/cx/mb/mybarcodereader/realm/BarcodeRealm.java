package cx.mb.mybarcodereader.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Barcode Object.
 */
@Getter
@Setter
public class BarcodeRealm extends RealmObject {

    /**
     * Primary key. (hashed 'text')
     */
    @PrimaryKey
    private String key;

    /**
     * Text.
     */
    @Required
    private String text;

    /**
     * Raw image.
     */
    @Required
    private byte[] image;

    /**
     * Date of create(scan).
     */
    @Required
    private Date createAt;
}
