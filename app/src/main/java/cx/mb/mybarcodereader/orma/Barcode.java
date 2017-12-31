package cx.mb.mybarcodereader.orma;

import android.graphics.Bitmap;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Barcode Object.
 * Created by toshiaki on 2017/12/30.
 */
@Table
@Setter
@Getter
public class Barcode {

    /**
     * Primary key. (uuid + hashed text)
     */
    @PrimaryKey()
    private String key;

    /**
     * Barcode type string.
     */
    @Column
    private String type;

    /**
     * Text.
     */
    @Column
    private String text;

    /**
     * Raw image.
     */
    @Column
    private byte[] image;

    /**
     * Raw bitmap (not save.)
     */
    private Bitmap bitmap;

    /**
     * Date of create(scan).
     */
    @Column(helpers = Column.Helpers.ORDER_IN_ASC)
    private Date createAt;
}
