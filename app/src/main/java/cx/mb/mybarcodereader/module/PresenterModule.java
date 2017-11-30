package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenter;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenterImpl;
import cx.mb.mybarcodereader.presentation.main.MainActivityPresenter;
import cx.mb.mybarcodereader.presentation.main.MainActivityPresenterImpl;
import cx.mb.mybarcodereader.service.HashService;
import dagger.Module;
import dagger.Provides;

/**
 * Presenter module.
 */
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
     * Returns BarcodeActivityPresenter.
     * @return presenter.
     */
    @Provides
    public BarcodeActivityPresenter provideBarcodeActivityPresenter(HashService service) {
        return new BarcodeActivityPresenterImpl(service);
    }
}
