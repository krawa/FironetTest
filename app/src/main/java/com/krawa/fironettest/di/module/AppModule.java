package com.krawa.fironettest.di.module;

import android.content.Context;

import com.krawa.fironettest.App;
import com.krawa.fironettest.di.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    protected final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @PerApplication
    public Context provideContext() {
        return application.getApplicationContext();
    }

}
