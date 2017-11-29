package cx.mb.mybarcodereader.component;

import cx.mb.mybarcodereader.application.MyApplication;
import cx.mb.mybarcodereader.module.AppModule;
import cx.mb.mybarcodereader.module.PresenterModule;
import cx.mb.mybarcodereader.module.ServiceModule;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivity;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenter;
import cx.mb.mybarcodereader.presentation.barcode.BarcodeActivityPresenterImpl;
import cx.mb.mybarcodereader.presentation.main.MainActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * DI container.
 */
@Singleton
@Component(modules = {AppModule.class, PresenterModule.class, ServiceModule.class})
public interface AppComponent {

    /**
     * Injection for MyApplication.
     *
     * @param target target.
     */
    void inject(MyApplication target);

    /**
     * Injection for MainActivity.
     *
     * @param target target.
     */
    void inject(MainActivity target);

    /**
     * Injection for BarcodeActivity.
     *
     * @param target target.
     */
    void inject(BarcodeActivity target);

}
