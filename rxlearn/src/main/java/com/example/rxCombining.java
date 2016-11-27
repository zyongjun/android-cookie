package com.example;

import rx.Observable;
import rx.Subscriber;
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
}
