package cx.mb.mybarcodereader.presentation.main;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tbruyelle.rxpermissions2.RxPermissions;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.application.MyApplication;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Main(History) activity.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Camera button.
     */
    @BindView(R.id.main_shutter)
    ImageButton shutter;

    /**
     * Presenter module.
     */
    @Inject
    MainActivityPresenter presenter;

    /**
     * Disposable items.
     */
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((MyApplication) getApplication()).getAppComponent().inject(this);
        presenter.onCreate(this);

        // default is gone.
        shutter.setVisibility(View.GONE);

        final RxPermissions rxPermissions = new RxPermissions(this);
        final Disposable disposable = rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Timber.i("external storage permission granted.");
                        shutter.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, R.string.error_permission_external_storage, Toast.LENGTH_SHORT).show();
                    }
                });
       disposables.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    /**
     * Shutter button clicked.
     * @param btn button.
     */
    @OnClick(R.id.main_shutter)
    void onClick(ImageButton btn) {

        presenter.startCamera();
    }
}
