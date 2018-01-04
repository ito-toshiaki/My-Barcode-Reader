package cx.mb.mybarcodereader.presentation.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cx.mb.mybarcodereader.R;
import cx.mb.mybarcodereader.application.MyApplication;
import cx.mb.mybarcodereader.presentation.presenter.MainActivityPresenter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

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
     * Result list.
     */
    @BindView(R.id.main_result_list)
    ListView resultList;

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
     */
    @OnClick(R.id.main_shutter)
    void onClick() {
        /*
        final Notices notices = new Notices();
        notices.addNotice(new Notice("Butter Knife", "", "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Dagger2", "", "Copyright (C) 2012 The Dagger Authors.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Timber", "", "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Lombok", "", "Copyright © 2009-2017 The Project Lombok Authors", new MITLicense()));
        notices.addNotice(new Notice("Stetho", "", "Copyright (c) 2014-present, Facebook, Inc.", new BSD2ClauseLicense()));
        notices.addNotice(new Notice("Stetho-Realm", "", "Copyright (c) 2015-present, uPhyca, Inc.", new BSD2ClauseLicense()));
        notices.addNotice(new Notice("Realm", "", "Copyright 2017 Realm Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("android-adapters", "", "Copyright 2017 Realm Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Zxing Core", "", "Copyright (C) 2010 ZXing authors", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("ZXing Android Embedded", "", "Copyright (C) 2012-2017 ZXing authors, Journey Mobileh", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("RxJava", "", "Copyright (c) 2016-present, RxJava Contributors.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("RxAndroid", "", "Copyright 2015 The RxAndroid authors", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("RxPermissions", "", "Copyright (C) 2015 Thomas Bruyelle", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("LeakCanary for Android", "", "Copyright 2015 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("No op LeakCanary for Android", "", "Copyright 2015 Square, Inc.", new ApacheSoftwareLicense20()));

        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(getApplicationContext())
                .setNotices(notices)
                .setShowFullLicenseText(false)
                .setIncludeOwnLicense(true)
                .build();

        fragment.show(getSupportFragmentManager(), null);
        */

        presenter.startCamera();
    }

    /**
     * Get result list object.
     * @return result list.
     */
    public ListView getResultList() {
        return this.resultList;
    }
}
