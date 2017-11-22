package cx.mb.mybarcodereader.application;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import cx.mb.mybarcodereader.BuildConfig;
import timber.log.Timber;

/**
 * Custom Application.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Timber.plant(new Timber.DebugTree());
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }
    }
}
