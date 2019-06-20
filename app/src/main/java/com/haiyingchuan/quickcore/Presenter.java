package com.haiyingchuan.quickcore;

import android.support.v4.app.Fragment;

import com.kbryant.quickcore.event.ViewEvent;
import com.kbryant.quickcore.execute.impl.ModelAndView;
import com.kbryant.quickcore.mvp.model.ModelHelper;
import com.kbryant.quickcore.mvp.presenter.QuickPresenter;
import com.kbryant.quickcore.util.ApiException;

import javax.inject.Inject;

public class Presenter extends QuickPresenter implements IMainContract.IPresenter {


    @Inject
    public Presenter(ModelHelper modelHelper) {
        super(modelHelper);
    }

    @Override
    public void getData(String client_model) {
        ModelAndView.create(view(IMainContract.IView.class), modelHelper())
                .request(service(MainService.class).postAppOpenRecord(client_model),
                        new ViewEvent<IMainContract.IView, String>() {
                            @Override
                            public void call(IMainContract.IView view, String data) {
                                view.onSuccess(data);
                            }
                        }, new ViewEvent<IMainContract.IView, ApiException>() {
                            @Override
                            public void call(IMainContract.IView view, ApiException data) {
                                view.onFail(data);
                            }
                        });
    }
}
