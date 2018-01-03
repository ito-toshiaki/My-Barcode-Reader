package cx.mb.mybarcodereader.presentation.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.application.MyApplication;
import cx.mb.mybarcodereader.presentation.presenter.BarcodeActivityPresenter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static android.content.Intent.EXTRA_TEXT;

/**
 * Barcode Reader Activity.
 */
public class BarcodeActivity extends AppCompatActivity {

    /**
     * Presenter.
     */
    @Inject
    BarcodeActivityPresenter presenter;

    /**
     * Barcode view.
     */
    @BindView(R.id.barcode_preview)
    CompoundBarcodeView barcodeView;

    /**
     * Restart button.
     */
    @BindView(R.id.barcode_restart)
    FloatingActionButton restart;

    /**
     * Barcode type.
     */
    @BindView(R.id.barcode_type)
    TextView barcodeType;

    /**
     * Barcode text.
     */
    @BindView(R.id.barcode_text)
    TextView barcodeText;

    /**
     * Disposable items.
     */
    private final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Create boot intent.
     *
     * @param context parent context.
     * @return intent
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, BarcodeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        presenter.onCreate(this);

        restart.setVisibility(View.GONE);
        final RxPermissions rxPermissions = new RxPermissions(this);
        final Disposable disposable = rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        Timber.i("camera permission granted.");
                        presenter.startCamera();
                    } else {
                        Toast.makeText(this, R.string.error_permission_camera, Toast.LENGTH_SHORT).show();
                    }
                });
        disposables.add(disposable);
    }

    /**
     * Barcode text long click.
     * @return if return true, not fire click event.
     */
    @OnLongClick(R.id.barcode_text)
    @SuppressWarnings("unused")
    public boolean onLongClick() {

        if (TextUtils.isEmpty(barcodeText.getText().toString())) {
            Toast.makeText(this, getText(R.string.error_barcode_text_is_empty), Toast.LENGTH_LONG).show();
            return true;
        }

        ClipData.Item clip = new ClipData.Item(this.barcodeText.getText());
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(EXTRA_TEXT, clip.getText());   // メモ帳のテキスト欄、メールアプリの本文にテキストをセット
        startActivity(sendIntent);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
        presenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!this.presenter.isScanned()) {
            barcodeView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!this.presenter.isScanned()) {
            barcodeView.resume();
        }
    }

    /**
     * Click on Restart.
     */
    @OnClick(R.id.barcode_restart)
    public void onClick() {
        presenter.startCamera();
    }

    /**
     * Pause barcode scan.
     */
    public void pauseScan() {
        this.barcodeView.pause();
    }

    /**
     * Resume barcode scan.
     */
    public void resumeScan() {
        this.barcodeView.resume();
    }

    /**
     * Set barcode type and text.
     *
     * @param type type.
     * @param text text.
     */
    public void update(String type, String text) {
        this.barcodeType.setText(type);
        this.barcodeText.setText(text);
    }

    /**
     * Get restart button.
     * @return restart button.
     */
    public FloatingActionButton getRestart() {
        return this.restart;
    }

    /**
     * Get barcode view.
     * @return barcode view.
     */
    public CompoundBarcodeView getBarcodeView() {
        return  this.barcodeView;
    }
}
