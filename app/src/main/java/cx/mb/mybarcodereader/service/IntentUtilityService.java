package cx.mb.mybarcodereader.service;

import android.content.Intent;

/**
 * Create intent utility.
 * Created by toshiaki on 2018/01/04.
 */
public interface IntentUtilityService {

    /**
     * Crete intent of copy text.
     * @param text text to copy.
     * @return intent.
     */
    Intent createTextCopyIntent(String text);
}
