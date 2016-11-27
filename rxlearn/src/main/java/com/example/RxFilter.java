package com.example;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * author: zyongjun on 2016/11/25 0025.
 * email: zhyongjun@windhike.cn
 */

public class RxFilter {
    private void print(String string) {
        System.out.println(string);
    }

    private Observable<Integer> getTimeObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i=0;i<10;i++) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
    }

    public void testDebound() {
        getTimeObservable()
//                .debounce(1, TimeUnit.SECONDS)
                .debounce(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        return Observable.just(integer).delay(3,TimeUnit.SECONDS);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                print("--------------"+integer);
            }
        });
    }

    public void testDistinct() {
        Observable.just(1,2,3,4,2,3,4,5)
//                .distinct()
                .distinct(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return null;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        print("----------"+integer);
                    }
                });
    }

    public void testDistinctUtilChanged() {
        Observable.just(1,2,3,4,2,3,4,5,5,6)
//                .distinctUntilChanged()
                .distinctUntilChanged(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return null;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        print("-----------"+integer);
                    }
                });
    }

    public void testFilter() {
        Observable.just(1,2,3)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer==2;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                print("============"+integer);
            }
        });
    }

    public void testElementAt() {
        Observable.just(1,2,3,4,5)
                .elementAt(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        print("--------------"+integer);
                    }
                });
    }

    public void testFirst() {
        Observable.just(1,2,3,4,5,6)
//                .first()
                .first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer>4;
                    }
                })
//                .firstOrDefault(6)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        print("==onerror==="+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        print("============"+integer);
                    }
                });
    }

    public void testTake() {
        Observable.just(1,2,3)
                .take(2)
//                .skip(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        print("---------"+integer);
                    }
                });
    }

    public void testThrottleLast() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i=0;i<10;i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).throttleFirst(2,TimeUnit.SECONDS)
        .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                print("--------------"+integer);
            }
        });
    }

    public void testThrottleFirst() {

    }
}
