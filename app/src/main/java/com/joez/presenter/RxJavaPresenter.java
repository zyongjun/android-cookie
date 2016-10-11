package com.joez.presenter;

import com.joez.view.IRxJavaView;
import com.windhike.mvputils.BaseRecyclerPresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class RxJavaPresenter extends BaseRecyclerPresenter<IRxJavaView> {

    public RxJavaPresenter() {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void testSimpleRxCopy() {
        Observable.just("this is mine").map(new Func1<String, String>() {

            @Override
            public String call(String s) {
                return s.substring(3).toUpperCase();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mView.updateResultDisplay(s);
            }
        });
    }
}
