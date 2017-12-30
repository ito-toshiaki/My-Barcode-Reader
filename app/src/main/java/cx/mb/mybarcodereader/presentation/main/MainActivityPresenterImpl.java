package cx.mb.mybarcodereader.presentation.main;

import android.app.Activity;

import cx.mb.mybarcodereader.adapter.BarcodeListAdapter;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivity;
import cx.mb.mybarcodereader.realm.OrmaDatabase;

/**
 * Presenter class for MainActivity.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    /**
     * parent.
     */
    private MainActivity parent;

    @Override
    public void onCreate(Activity parent, OrmaDatabase database) {
        this.parent = (MainActivity) parent;

        BarcodeListAdapter adapter = new BarcodeListAdapter(this.parent, database.relationOfBarcode().orderByCreateAtAsc());
        ((MainActivity) parent).resultList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
    }


    @Override
    public void startCamera() {
        parent.startActivity(BarcodeActivity.createIntent(parent));
    }

}
