package cx.mb.mybarcodereader.module;

import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenter;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenterImpl;
import cx.mb.mybarcodereader.presentation.main.MainActivityPresenter;
import cx.mb.mybarcodereader.presentation.main.MainActivityPresenterImpl;
import cx.mb.mybarcodereader.realm.OrmaDatabase;
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
     * @param service Hash service.
     * @param database Database.
     * @return presenter.
     */
    @Provides
    public BarcodeActivityPresenter provideBarcodeActivityPresenter(HashService service, OrmaDatabase database) {
        return new BarcodeActivityPresenterImpl(service, database);
    }
}
