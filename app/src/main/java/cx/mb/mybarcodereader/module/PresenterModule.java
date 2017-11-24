package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenter;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenterImpl;
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

    /**
     * Returnes BarcodeActivityPresenter.
     * @return presenter.
     */
    @Provides
    public BarcodeActivityPresenter provideBarcodeActivityPresenter() {
        return new BarcodeActivityPresenterImpl();
    }
}
