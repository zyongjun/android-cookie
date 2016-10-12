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
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
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
//        just();
//        repeat();
//        defer();
//        range();
//        interval();
//        timer();
//        intervalDelay();
//        filter();
//        take();
//        takeLast();
//        distinct();
//        elementAt();
//        sampling();

        //map
//        map();
        flatmap();
    }

    private static final String TAG = "RxJavaPresenter";
    public void refreshList() {
        getApps().toSortedList().subscribe(new Observer<List<AppInfo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
                mAppList.addAll(appInfos);
                mView.notifyDataChanged();
            }
        });
    }

    public void just() {
        Observable.just(1,2,3).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: ------"+integer );
            }
        });
    }

    public void repeat() {
//        Observable.just(1,2).repeat(2).subscribe(new Observer<Integer>() {
//            @Override
//            public void onCompleted() {
//                Log.e(TAG, "onCompleted: " );
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e(TAG, "onNext: "+integer );
//            }
//        });
    }

    private Observable<Integer> getInt() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                Log.e(TAG, "call: ------------------" );
                subscriber.onNext(42);
                subscriber.onCompleted();
            }
        });
    }
//delay obserable create until subscribed
    public void defer() {
        Observable defer = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable call() {
                Log.e(TAG, "call: ==============defer call" );
                return getInt();
            }
        });
        defer.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                Log.e(TAG, "call: --------------------action"+o);
            }
        });
    }

    public void range() {
        Observable.range(10,2).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer );
            }
        });
    }

    private void interval() {
        Subscription stopmePlease = Observable.interval(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, "onNext: "+aLong );
            }
        });

    }

    private void timer() {
        Observable.timer(3,TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, "onNext: "+aLong );
            }
        });
    }

    private void intervalDelay() {
        Observable.interval(3,4,TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, "onNext: "+aLong );
            }
        });
    }

    private List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        return list;
    }
    private void filter() {

        Observable.from(getData()).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer%2==0;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer );
            }
        });
    }
// take before count if not enough return have
    private void take() {
        Observable.from(getData()).take(5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " );
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: "+integer );
                    }
                });
    }

    private void takeLast() {
        Observable.from(getData()).takeLast(8)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " );
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: "+integer );
                    }
                });
    }

    private void distinct() {
        Observable.from(getData())
                .take(3).repeat(3)
               .distinct().subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ",null );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ",e);
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer );
            }
        });
    }

    private void elementAt() {
//        Observable.from(getData()).elementAt(10)
        Observable.from(getData()).elementAtOrDefault(10,10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ",null );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", null);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: "+integer,null );
                    }
                });
    }

    private void sampling() {
        Observable.from(getData())
                .sample(2,TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ",null );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ",null );
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: "+integer,null );
                    }
                });
    }

    private void map() {
        Observable.from(getData())
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer*10;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ",null );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ",e );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer,null );
            }
        });
    }

    private void flatmap() {
        Observable.from(getData()).flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(integer);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ",null );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ",e );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer,null );
            }
        });
    }
}
