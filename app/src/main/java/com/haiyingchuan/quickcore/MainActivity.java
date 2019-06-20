package com.haiyingchuan.quickcore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kbryant.quickcore.core.HasDaggerInject;
import com.kbryant.quickcore.util.ApiException;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IMainContract.IView,
        HasDaggerInject<ActivityComponent> {

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attachView(this);
        getNetData();
    }

    private void getNetData() {
        presenter.getData(getClient_model());
    }

    @Override
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public String getClient_model() {
        return android.os.Build.MODEL;
    }

    @Override
    public void onSuccess(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(ApiException data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastMsg(String msg) {

    }
}
