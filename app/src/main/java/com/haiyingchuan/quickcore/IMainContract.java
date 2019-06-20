package com.haiyingchuan.quickcore;

import com.kbryant.quickcore.mvp.BaseView;
import com.kbryant.quickcore.util.ApiException;

public interface IMainContract {

    interface IView extends BaseView {

        String getClient_model();

        void onSuccess(String data);

        void onFail(ApiException data);
    }

    interface IPresenter {
        void  getData(String client_model);
    }
}
