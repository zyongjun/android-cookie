package com.example;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * author: zyongjun on 2016/11/27 0027.
 * email: zhyongjun@windhike.cn
 */

public class RxCombining {

    private void print(String string) {
        System.out.println(string);
    }
    
    private Observable<Integer> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i < 6; i++) {
                    subscriber.onNext(i * index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).observeOn(Schedulers.newThread());
    }
    public void testCombiningLast() {
        Observable.combineLatest(createObserver(2), createObserver(1), new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer num1, Integer num2) {
                print("left:" + num1 + " right:" + num2);
                return num1 + num2;
            }
        }).subscribe();
    }

    public void testJoin() {
        Observable.just("left","left1").join(createObserver(1), new Func1<String, Observable<Long>>() {
            @Override
            public Observable<Long> call(String s) {
                return Observable.timer(3000, TimeUnit.MILLISECONDS);
            }
        }, new Func1<Integer, Observable<Long>>() {
            @Override
            public Observable<Long> call(Integer integer) {
                return Observable.timer(1000, TimeUnit.MILLISECONDS);
            }
        }, new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                return s+integer;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                print("-------------next"+s);
            }
        });
    }
}