package com.haiyingchuan.quickcore;

import com.kbryant.quickcore.di.ActivityScope;
import com.kbryant.quickcore.di.component.AppComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
