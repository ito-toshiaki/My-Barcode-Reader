package cx.mb.mybarcodereader.application;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import cx.mb.mybarcodereader.BuildConfig;
import cx.mb.mybarcodereader.component.AppComponent;
import cx.mb.mybarcodereader.component.DaggerAppComponent;
import cx.mb.mybarcodereader.module.AppModule;
import cx.mb.mybarcodereader.module.PresenterModule;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Custom Application.
 */
public class MyApplication extends Application {

    /**
     * DI Container.
     */
    private AppComponent appComponent;

    @Override
    public void onCreate() {

        super.onCreate();

        Realm.init(this);

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Timber.plant(new Timber.DebugTree());
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule())
                .build();

        this.getAppComponent().inject(this);
    }

    /**
     * Returns appComponent.
     * @return appComponent.
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
