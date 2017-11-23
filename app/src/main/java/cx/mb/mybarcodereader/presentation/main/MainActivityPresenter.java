package cx.mb.mybarcodereader.presentation.main;


import android.app.Activity;

/**
 * Presenter class for MainActivity.
 */
public interface MainActivityPresenter {

    /**
     * Start BarcodeViewActivity.
     */
    void startCamera();

    /**
     * onCreate.
     * @param  parent parent activity.
     */
    void onCreate(Activity parent);
}
