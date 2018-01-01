package cx.mb.mybarcodereader.module;

import android.app.Application;

import com.github.gfx.android.orma.AccessThreadConstraint;

import javax.inject.Singleton;

import cx.mb.mybarcodereader.orma.OrmaDatabase;
import dagger.Module;
import dagger.Provides;

/**
 * Application module.
 */
@Module
public class AppModule {

    /**
     * Application class.
     */
    private final Application app;

    /**
     * Constructor.
     *
     * @param app application.
     */
    public AppModule(Application app) {
        this.app = app;
    }

    /**
     * Returns application.
     *
     * @return application
     */
    @Provides
    @Singleton
    public Application provideMyApplication() {
        return app;
    }

    /**
     * Return orma database.
     *
     * @return OrmaDatabase.
     */
    @Provides
    @Singleton
    public OrmaDatabase provideOrmaDatabase() {
        return OrmaDatabase
                .builder(this.app)
                .writeOnMainThread(AccessThreadConstraint.WARNING)
                .build();
    }
}
