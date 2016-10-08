package com.joez.dagger;

import com.joez.ui.Dagger2Aty;

import dagger.Component;

/**
 * Created by gzzyj on 2016/7/30.
 */
@ActivityScope
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(Dagger2Aty aty);

}
