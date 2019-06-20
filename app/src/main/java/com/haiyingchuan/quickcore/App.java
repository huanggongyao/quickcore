package com.haiyingchuan.quickcore;

import android.app.Application;

import com.kbryant.quickcore.core.HasDaggerApplication;
import com.kbryant.quickcore.core.impl.AppTarget;
import com.kbryant.quickcore.di.component.AppComponent;
import com.kbryant.quickcore.di.module.GlobalConfigModule;
import com.kbryant.quickcore.di.module.HttpConfigModule;


public class App extends Application implements HasDaggerApplication<ActivityComponent> {
    @Override
    public void onCreate() {
        super.onCreate();
        AppTarget.create(this);
    }

    @Override
    public GlobalConfigModule setupGlobalConfigModule() {
        return GlobalConfigModule
                .builder(this)
                .addServices(MainService.class)
                .build();
    }

    @Override
    public HttpConfigModule setupHttpConfigModule() {
        return HttpConfigModule.builder()
                .setHost(Api.HOST)
                .addInterceptor(new RequestInterceptor())
                .setConnectTimeout(30)
                .setReadTimeout(30)
                .setWriteTimeout(30)
                .build();
    }

    @Override
    public ActivityComponent activityComponent(AppComponent appComponent) {
        return DaggerActivityComponent.builder().appComponent(appComponent).build();
    }
}
