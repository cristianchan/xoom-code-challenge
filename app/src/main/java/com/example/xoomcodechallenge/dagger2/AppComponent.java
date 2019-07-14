package com.example.xoomcodechallenge.dagger2;

import android.app.Application;
import com.example.xoomcodechallenge.AppController;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        CommonModule.class,
        DbModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(AppController appController);

}
