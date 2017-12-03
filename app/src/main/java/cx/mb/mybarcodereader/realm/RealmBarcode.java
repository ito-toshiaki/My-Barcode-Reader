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
public class RealmBarcode extends RealmObject {

    /**
     * Primary key. (uuid + hashed text)
     */
    @PrimaryKey
    private String key;

    /**
     * Barcode type string.
     */
    @Required
    private String type;

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
