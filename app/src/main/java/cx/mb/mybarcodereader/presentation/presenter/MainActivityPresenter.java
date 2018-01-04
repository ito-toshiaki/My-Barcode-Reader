package cx.mb.mybarcodereader.presentation.presenter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import javax.inject.Inject;

import cx.mb.mybarcodereader.adapter.BarcodeListAdapter;
import cx.mb.mybarcodereader.orma.Barcode;
import cx.mb.mybarcodereader.orma.OrmaDatabase;
import cx.mb.mybarcodereader.presentation.activity.BarcodeActivity;
import cx.mb.mybarcodereader.presentation.activity.MainActivity;
import cx.mb.mybarcodereader.service.IntentUtilityService;
import timber.log.Timber;

/**
 * Presenter class for MainActivity.
 */
public class MainActivityPresenter implements AdapterView.OnItemLongClickListener {

    /**
     * Database.
     */
    private final OrmaDatabase database;

    /**
     * parent.
     */
    private MainActivity parent;

    /**
     * Intent utility service.
     */
    private IntentUtilityService service;

    /**
     * Constructor.
     * @param database database.
     * @param service intent utility service.
     */
    @Inject
    public MainActivityPresenter(OrmaDatabase database, IntentUtilityService service) {
        this.database = database;
        this.service = service;
    }

    /**
     * onCreate.
     * @param parent Parent activity.
     */
    public void onCreate(Activity parent) {
        this.parent = (MainActivity) parent;

        BarcodeListAdapter adapter = new BarcodeListAdapter(this.parent, this.database.relationOfBarcode().orderByCreateAtDesc());
        ((MainActivity) parent).getResultList().setAdapter(adapter);
        ((MainActivity) parent).getResultList().setOnItemLongClickListener(this);
    }

    /**
     * Start camera.
     */
    public void startCamera() {
        parent.startActivity(BarcodeActivity.createIntent(parent));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        final Barcode item = (Barcode) parent.getResultList().getItemAtPosition(i);
        if (item == null) {
            Timber.w("item pos:%d is null.", i);
            return true;
        }

        final Intent intent = service.createTextCopyIntent(item.getText());
        parent.startActivity(intent);

        return true;
    }
}
