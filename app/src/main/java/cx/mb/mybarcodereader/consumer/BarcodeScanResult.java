package cx.mb.mybarcodereader.consumer;

/**
 * Barcode scan event interface.
 */
public interface BarcodeScanResult {

    /**
     * Event notification from Consumer.
     *  @param scanned barcode scanned?
     * @param id      primary key.
     */
    void notify(boolean scanned, String id);
}
