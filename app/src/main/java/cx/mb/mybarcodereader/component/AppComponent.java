package cx.mb.mybarcodereader.component;

import cx.mb.mybarcodereader.application.MyApplication;
import cx.mb.mybarcodereader.module.AppModule;
import cx.mb.mybarcodereader.module.PresenterModule;
import cx.mb.mybarcodereader.presentation.main.MainActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * DI container.
 */
@Singleton
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppComponent {

    /**
     * Injection for MyApplication.
     * @param target target.
     */
    void inject(MyApplication target);

    /**
     * Injection for MainActivity.
     * @param target target.
     */
    void  inject(MainActivity target);
}
