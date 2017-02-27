package com.krawa.fironettest;

import com.arellomobile.mvp.MvpApplication;
import com.krawa.fironettest.di.component.AppComponent;
import com.krawa.fironettest.di.component.DaggerAppComponent;
import com.krawa.fironettest.di.module.AppModule;
import com.krawa.fironettest.di.module.NetModule;

public class App extends MvpApplication {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

}
