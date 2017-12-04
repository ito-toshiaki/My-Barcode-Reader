package cx.mb.mybarcodereader.presentation.main;

import android.app.Activity;
import cx.mb.mybarcodereader.adapter.ResultListAdapter;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivity;
import cx.mb.mybarcodereader.realm.RealmBarcode;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Presenter class for MainActivity.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    /**
     * parent.
     */
    private MainActivity parent;

    /**
     * Realm.
     */
    private Realm realm;

    @Override
    public void onCreate(Activity parent) {
        this.parent = (MainActivity) parent;
        realm = Realm.getDefaultInstance();

        final RealmResults<RealmBarcode> items = realm.where(RealmBarcode.class)
                .findAllSorted("createAt", Sort.DESCENDING);
        final ResultListAdapter adapter = new ResultListAdapter(items);
        ((MainActivity) parent).resultList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        realm.close();
    }


    @Override
    public void startCamera() {
        parent.startActivity(BarcodeActivity.createIntent(parent));
    }

}
