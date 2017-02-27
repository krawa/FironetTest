package com.krawa.fironettest.di.component;

import com.krawa.fironettest.di.module.AppModule;
import com.krawa.fironettest.di.module.NetModule;
import com.krawa.fironettest.di.scope.PerApplication;
import com.krawa.fironettest.presentation.presenter.MapsPresenter;

import dagger.Component;

@Component(modules = {AppModule.class, NetModule.class})
@PerApplication
public interface AppComponent {

    void inject(MapsPresenter mapsPresenter);

}
