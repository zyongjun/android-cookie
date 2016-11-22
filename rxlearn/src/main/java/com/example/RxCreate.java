package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * author: zyongjun on 2016/11/22 0022.
 * email: zhyongjun@windhike.cn
 */

public class RxCreate {
    private void print(String string) {
        System.out.println(string);
    }
    public void testCreate() {
        Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(1);
//                    subscriber.onError(new Exception("error"));
                    subscriber.onCompleted();
                }

            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                print("===="+integer);
            }
        });
    }

    public void testJust() {
        Observable.just(1,2,3)
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                print("=======just======="+integer);
            }
        });
    }

    public void defer() {
        Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(1,2,3);
            }
        });
    }

    public void testRange() {
        Observable.range(0,10)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        print("======range:"+integer);
                    }
                });
    }

    public void testFrom() {
        List<Integer>  list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Observable.from(list);
    }

    public void testInterval() {
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        print("====interval==="+aLong);
                    }
                });
    }

    public void testTimer() {
        Observable.timer(1,TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        print("====timer====="+aLong);
                    }
                });
    }

    public void testRepeat() {
        Observable.just(1).repeat(5);
    }
}
