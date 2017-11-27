package cx.mb.mybarcodereader;

import lombok.Getter;

/**
 * Event of barcode scanned.
 */
public class BarcodeScanResultEvent {

    /**
     * scnanned return true.
     */
    @Getter
    private boolean scanned;

    /**
     * Constructor.
     * @param scanned scanned ?
     */
    public BarcodeScanResultEvent(boolean scanned) {
        this.scanned = scanned;
    }
}
