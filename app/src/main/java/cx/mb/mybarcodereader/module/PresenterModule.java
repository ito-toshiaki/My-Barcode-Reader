package cx.mb.mybarcodereader.module;

import android.app.Activity;
import android.app.Application;
import cx.mb.mybarcodereader.presentation.barcodeview.BarcodeViewActivityPresenter;
import cx.mb.mybarcodereader.presentation.barcodeview.BarcodeViewActivityPresenterImpl;
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
     * Returnes BarcodeViewActivityPresenter.
     * @return presenter.
     */
    @Provides
    public BarcodeViewActivityPresenter provideBarcodeViewActivityPresenter() {
        return new BarcodeViewActivityPresenterImpl();
    }
}
