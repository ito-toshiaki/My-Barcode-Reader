package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.service.HashService;
import cx.mb.mybarcodereader.service.HashServiceMd5Impl;
import cx.mb.mybarcodereader.service.IntentUtilityService;
import cx.mb.mybarcodereader.service.IntentUtilityServiceImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Service module.
 */
@Module
public class ServiceModule {

    /**
     * Returns  HashService.
     *
     * @return service.
     */
    @Provides
    public HashService provideHashService() {
        return new HashServiceMd5Impl();
    }

    /**
     * Returns IntentUtlitlityService.
     *
     * @return service.
     */
    @Provides
    public IntentUtilityService provideIntentUtilityService() {
        return new IntentUtilityServiceImpl();
    }
}
