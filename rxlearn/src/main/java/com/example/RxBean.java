package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class RxBean {

    private List<String> generateCap() {
        List<String> list = new ArrayList<>();
        list.add("D");
        list.add("B");
        list.add("C");
        return list;
    }

    private List<String> generateEnd() {
        List<String> list = new ArrayList<>();
        list.add("X");
        list.add("Y");
        list.add("Z");
        return list;
    }

    private Observable<String> getObservableList() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                List<String> list = generateCap();
                for (String s : list) {
                    if(!subscriber.isUnsubscribed()) {
                        subscriber.onNext(s);
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    private void print(String string) {
        System.out.println(string);
    }
    public void testMerge() {
        Observable oa = Observable.from(generateCap());
        Observable<String> ob = Observable.from(generateEnd());
        Observable oc = Observable.merge(oa,ob);
        oc.subscribe(new Observer() {
            @Override
            public void onCompleted() {
                print("completed:-----");
            }

            @Override
            public void onError(Throwable e) {
                print("onError:-----");
            }

            @Override
            public void onNext(Object o) {
                print("onNext:-----"+o.toString());
            }
        });
    }

    public void testSubscriber() {
        getObservableList().toSortedList().subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {
                print("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<String> list) {
                for (String s : list) {
                    print("---"+s);
                }
            }
        });
    }

    public void testFrom() {
        Observable.from(generateEnd()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                print("complete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                print(s);
            }
        });
    }

    public void testJoin() {
        Observable.just("A","B","C").repeat(2)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        print(s);
                    }
                });
    }
    private String i = null;
    public void testDefer() {
        i ="A";
        Observable<String> ob=Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.from(new String[]{i});
            }
        });
        i ="B";
        ob.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                print(s);
            }
        });
    }

    public void testRange() {
        Observable.range(3,2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        print("next "+integer);
                    }
                });

    }

    public void testInterval() {
        Subscription sup = Observable.interval(3, 4,TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        print("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        print(e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        print("-----");
                    }
                });

    }

    public void testTimer() {
        Observable.timer(2,TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        print("complete");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        print("-------------"+aLong);
                    }
                });
    }

    public void testFilter() {
        Observable.from(generateEnd()).repeat(3).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s+"1";
            }
        })
//                .timeout(1,TimeUnit.SECONDS)
//               .firstOrDefault("C")
//                .distinctUntilChanged()
//                .distinct()
//                .take(1)
//                .takeLast(1)
//                .filter(new Func1<String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) {
//                        return !s.equals("Z");
//                    }
//                })
                .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                print(s);
            }
        });
    }

    public void testFlatmap() {
        Observable<String> o = Observable.just("a","b")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just(s+"d");
                    }
                });
        o.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String r) {
                        print(r);
                    }
                });
    }
}
