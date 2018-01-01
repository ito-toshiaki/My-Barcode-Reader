package cx.mb.mybarcodereader.presentation.main;


import android.app.Activity;

import cx.mb.mybarcodereader.orma.OrmaDatabase;

/**
 * Presenter class for MainActivity.
 */
public interface MainActivityPresenter {

    /**
     * Start BarcodeActivity.
     */
    void startCamera();

    /**
     * onCreate.
     * @param  parent parent activity.
     * @param database database.
     */
    void onCreate(Activity parent, OrmaDatabase database);

    /**
     * onDestroy.
     */
    void onDestroy();
}
