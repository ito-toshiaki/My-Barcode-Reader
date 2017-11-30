package cx.mb.mybarcodereader.presentation.barcode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.application.MyApplication;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import javax.inject.Inject;

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
    private CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Create boot intent.
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
                        Timber.i("All permission granted.");
                        presenter.startCamera();
                        restart.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, "NOT GRANTED", Toast.LENGTH_SHORT).show();
                    }
                });
        disposables.add(disposable);
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
        barcodeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    /**
     * Click on Restart.
     * @param btn button.
     */
    @OnClick(R.id.barcode_restart)
    public void onClick(FloatingActionButton btn) {
        presenter.restart();
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
     * @param type type.
     * @param text text.
     */
    public void updateText(String type, String text) {
        this.barcodeType.setText(type);
        this.barcodeText.setText(text);
    }
}
