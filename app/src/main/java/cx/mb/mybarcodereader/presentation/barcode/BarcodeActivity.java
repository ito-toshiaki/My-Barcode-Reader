package cx.mb.mybarcodereader.presentation.barcode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.application.MyApplication;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        presenter.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
