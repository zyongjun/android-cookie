package com.example;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * author: zyongjun on 2016/12/4 0004.
 * email: zhyongjun@windhike.cn
 */

public class RxUtility {
    private void print(String string) {
        System.out.println(string);
    }

    private void printTime() {
        System.out.print("----time:"+System.currentTimeMillis()/1000);
    }

    public void testDelay() {
        printTime();
        Observable.just("a","b","c")
//                .delay(3, TimeUnit.SECONDS)
//                .delay(3,TimeUnit.SECONDS,Schedulers.immediate())
//                .delay(3,TimeUnit.SECONDS,Schedulers.trampoline())
//                .delaySubscription(2,TimeUnit.SECONDS,Schedulers.immediate())
                .delaySubscription(Observable.just("d","e"))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        print("====error=="+e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        print("==================="+s+"---"+System.currentTimeMillis()/1000);
                    }
                });
    }

    public void testDo() {
        Observable.just(1,2,3)
//                .doOnEach(new Action1<Notification<? super Integer>>() {
//                    @Override
//                    public void call(Notification<? super Integer> notification) {
//                        print("+=========="+notification.getValue());
//                    }
//                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        print("==================terminate");
                    }
                })
                .subscribe();
    }

    public void testMaterialize() {
        Observable.just(1,2,3)
                .materialize()
                .subscribe(new Action1<Notification<Integer>>() {
                    @Override
                    public void call(Notification<Integer> integerNotification) {

                    }
                });
    }

    public void testTimeInterval() {
        Observable.just(1,2,3)
                .timestamp()
                .subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {

                    }
                })
//                .timeInterval()
//                .subscribe(new Action1<TimeInterval<Integer>>() {
//                    @Override
//                    public void call(TimeInterval<Integer> integerTimeInterval) {
//                        print("======"+integerTimeInterval.getValue()+"----"+integerTimeInterval.getIntervalInMilliseconds());
//                    }
//                })
                ;
    }
}
