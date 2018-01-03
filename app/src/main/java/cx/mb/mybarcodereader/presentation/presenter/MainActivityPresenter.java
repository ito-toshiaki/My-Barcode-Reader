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
     * Constructor.
     * @param database database.
     */
    @Inject
    public MainActivityPresenter(OrmaDatabase database) {
        this.database = database;
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
     * onDestroy.
     */
    public void onDestroy() {
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

        ClipData.Item clip = new ClipData.Item(item.getText());
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, clip.getText());   // メモ帳のテキスト欄、メールアプリの本文にテキストをセット
        parent.startActivity(sendIntent);

        return true;
    }
}
