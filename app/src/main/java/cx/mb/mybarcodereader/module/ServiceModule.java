package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.service.HashService;
import cx.mb.mybarcodereader.service.HashServiceMd5Impl;
import dagger.Module;
import dagger.Provides;

/**
 * Service module.
 */
@Module
public class ServiceModule {

    /**
     * Returns MainActivityPresenter.
     *
     * @return presenter.
     */
    @Provides
    public HashService provideHashService() {
        return new HashServiceMd5Impl();
    }
}
