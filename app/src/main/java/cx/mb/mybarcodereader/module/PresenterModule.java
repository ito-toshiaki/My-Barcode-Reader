package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.presentation.main.MainActivityPresenter;
import cx.mb.mybarcodereader.presentation.main.MainActivityPresenterImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    /**
     * Returns MainActivityPresenter.
     *
     * @return presenter.
     */
    @Provides
    public MainActivityPresenter provideMainActivityPresenter() {
        return new MainActivityPresenterImpl();
    }

}
