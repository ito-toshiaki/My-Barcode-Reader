package cx.mb.mybarcodereader.presentation.main;

import android.app.Activity;

import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivity;

/**
 * Presenter class for MainActivity.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    /**
     * parent.
     */
    private MainActivity parent;

    @Override
    public void onCreate(Activity parent) {
        this.parent = (MainActivity) parent;
    }

    @Override
    public void onDestroy() {
        // do nothing.
    }


    @Override
    public void startCamera() {
        parent.startActivity(BarcodeActivity.createIntent(parent));
    }

}
