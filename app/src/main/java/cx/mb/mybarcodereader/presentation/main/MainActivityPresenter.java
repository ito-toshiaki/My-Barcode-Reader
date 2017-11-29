package cx.mb.mybarcodereader.presentation.main;


import android.app.Activity;

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
     */
    void onCreate(Activity parent);
}
