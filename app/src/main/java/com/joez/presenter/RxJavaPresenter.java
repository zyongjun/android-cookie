package com.joez.presenter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import com.joez.model.AppInfo;
import com.joez.view.IRxJavaView;
import com.joez.view.holder.AppHolder;
import com.windhike.mvputils.BaseRecyclerPresenter;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class RxJavaPresenter extends BaseRecyclerPresenter<IRxJavaView> {
    private List<AppInfo> mAppList = new ArrayList<>();
    public RxJavaPresenter() {
        registViewTemplate(1, AppHolder.class);
    }

    public List<AppInfo> getAppList() {
        return mAppList;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: ==="+mAppList.size() );
        return mAppList.size();
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

    private Observable<AppInfo> getApps() {
        return Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                final Intent intent = new Intent(Intent.ACTION_MAIN,null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                PackageManager manager = mView.getViewContext().getPackageManager();
                List<ResolveInfo> infos = manager.queryIntentActivities(intent,0);
                List<AppInfo> appInfos = new ArrayList<AppInfo>(infos.size());
                for (ResolveInfo info : infos) {
                    AppInfo app = new AppInfo(info.loadLabel(manager).toString(),0,null);
                    appInfos.add(app);
                    if(subscriber.isUnsubscribed()){
                        return;
                    }
                    subscriber.onNext(app);
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }

    @Override
    public void attach(IRxJavaView view) {
        super.attach(view);
        refreshList();
    }

    private static final String TAG = "RxJavaPresenter";
    public void refreshList() {
        getApps().toSortedList().subscribe(new Observer<List<AppInfo>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ------" );
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e(TAG, "onError: ===============");
            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
                Log.e(TAG, "onNext: ==============" );
                mAppList.addAll(appInfos);
                mView.notifyDataChanged();
            }
        });
    }
}
