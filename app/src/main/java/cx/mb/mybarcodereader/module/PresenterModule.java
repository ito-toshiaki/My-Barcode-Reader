package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.presentation.main.MainActivityPresenter;
import cx.mb.mybarcodereader.presentation.main.MainActivityPresenterImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    /**
     * Returns SplashPresenter.
     *
     * @return SplashPresenter.
     */
    @Provides
    public MainActivityPresenter provideMainActivityPresenter() {
        return new MainActivityPresenterImpl();
    }

}
