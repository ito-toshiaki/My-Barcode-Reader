package cx.mb.mybarcodereader.event;

import lombok.Getter;

/**
 * Event of barcode scanned.
 */
public class BarcodeScanResultEvent {

    /**
     * scanned return true.
     */
    @Getter
    private boolean scanned;

    /**
     * scanned item id.
     */
    @Getter
    private String key;

    /**
     * Constructor.
     *
     * @param scanned scanned ?
     * @param key     id.
     */
    public BarcodeScanResultEvent(boolean scanned, String key) {
        this.scanned = scanned;
        this.key = key;
    }
}
