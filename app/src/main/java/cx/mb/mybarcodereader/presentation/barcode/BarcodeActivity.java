package cx.mb.mybarcodereader.presentation.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.barcodeView)
    CompoundBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().inject(this);

        presenter.onCreate(this);
        barcodeView.decodeSingle(presenter);
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
}
