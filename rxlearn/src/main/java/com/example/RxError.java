package com.example;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * author: zyongjun on 2016/12/1 0001.
 * email: zhyongjun@windhike.cn
 */

public class RxError {
    private void print(String string) {
        System.out.println(string);
    }

    private Observable<Integer> getErrorObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1;i<6;i++) {
                    if (i!=2) {
                        subscriber.onNext(i);
                    } else {
                        subscriber.onError(new Throwable("on error happened"));
                    }

                }
            }
        });
    }

    public void testCatch() {
        getErrorObservable().onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return 999;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                print("=========onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                print("======onError:"+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                print("========onNext:"+integer);
            }
        });

    }

    public void testOnErrorResumeNext() {
        getErrorObservable()
//                .onErrorResumeNext(Observable.just(1,2,3))
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(Throwable throwable) {
                        return Observable.just(1,2,3);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                print("==========onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                print("==onError=="+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                print("=====onNext======="+integer);
            }
        });
    }

    public void testOnExceptionResumeNext() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1;i<6;i++) {
                    if (i == 3) {
                        subscriber.onError(new Throwable("on error happened"));
                    } else if(i==2){
                        subscriber.onError(new Exception("an exception happend"));
                    }else{
                        subscriber.onNext(i);
                    }

                }
            }
        }).onExceptionResumeNext(Observable.just(10,11))
                .subscribe(getIntegerSubscriber());
    }

    private Subscriber<Integer> getIntegerSubscriber() {
        return new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                print("==========onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                print("==onError=="+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                print("=====onNext======="+integer);
            }
        };
    }

    public void testRetry() {
        getErrorObservable().retry(2)
                .subscribe(getIntegerSubscriber());
    }

    public void testRetryWhen() {
        getErrorObservable().retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            private int count;
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                count++;
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        // For IOExceptions, we  retry
                        if (throwable instanceof IOException) {
                            return Observable.just(null);
                        }
                        // For anything else, don't retry
                        return Observable.error(throwable);
                    }
                });
            }
        }).subscribe(getIntegerSubscriber());
    }
}
