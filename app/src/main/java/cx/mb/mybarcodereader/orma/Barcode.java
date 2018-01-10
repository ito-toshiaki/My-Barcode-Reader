package cx.mb.mybarcodereader.orma;

import android.graphics.Bitmap;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

/**
 * Barcode Object.
 * Created by toshiaki on 2017/12/30.
 */
@Table
public class Barcode {

    /**
     * Primary key. (uuid + hashed text)
     */
    @PrimaryKey()
    public String key;

    /**
     * Barcode type string.
     */
    @Column
    public String type;

    /**
     * Text.
     */
    @Column
    public String text;

    /**
     * Raw image.
     */
    @Column
    public byte[] image;

    /**
     * Raw bitmap (not save.)
     */
    public Bitmap bitmap;

    /**
     * Date of create(scan).
     */
    @Column(helpers = Column.Helpers.ORDERS)
    public Date createAt;
}
