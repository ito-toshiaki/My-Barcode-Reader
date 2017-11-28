package cx.mb.mybarcodereader.service;

/**
 * Calculate hash string.
 */
public interface HashService {

    /**
     * Calculate hash string.
     *
     * @param source source string.
     * @return hashed string.
     */
    String calculate(String source);
}
