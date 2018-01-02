package cx.mb.mybarcodereader.presentation.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import cx.mb.mybarcodereader.adapter.BarcodeListAdapter;
import cx.mb.mybarcodereader.orma.Barcode;
import cx.mb.mybarcodereader.orma.OrmaDatabase;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivity;
import timber.log.Timber;

/**
 * Presenter class for MainActivity.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter, AdapterView.OnItemLongClickListener {

    /**
     * parent.
     */
    private MainActivity parent;

    @Override
    public void onCreate(Activity parent, OrmaDatabase database) {
        this.parent = (MainActivity) parent;

        BarcodeListAdapter adapter = new BarcodeListAdapter(this.parent, database.relationOfBarcode().orderByCreateAtDesc());
        ((MainActivity) parent).resultList.setAdapter(adapter);

        ((MainActivity) parent).resultList.setOnItemLongClickListener(this);
    }

    @Override
    public void onDestroy() {
    }


    @Override
    public void startCamera() {
        parent.startActivity(BarcodeActivity.createIntent(parent));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        final Barcode item = (Barcode) parent.resultList.getItemAtPosition(i);
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
