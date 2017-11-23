package cx.mb.mybarcodereader.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Application module.
 */
@Module
public class AppModule {

    /**
     * Application class.
     */
    private Application app;

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

}
