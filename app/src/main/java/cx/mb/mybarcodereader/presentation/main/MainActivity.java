package cx.mb.mybarcodereader.presentation.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.application.MyApplication;

import javax.inject.Inject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((MyApplication) getApplication()).getAppComponent().inject(this);
        presenter.onCreate(this);
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
