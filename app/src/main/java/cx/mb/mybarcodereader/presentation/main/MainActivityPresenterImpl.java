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
    private Activity parent;

    @Override
    public void onCreate(Activity parent) {
        this.parent = parent;
    }

    @Override
    public void startCamera() {
        parent.startActivity(BarcodeActivity.createIntent(parent));
    }

}
