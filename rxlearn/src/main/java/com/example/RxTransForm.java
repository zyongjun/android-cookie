package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * author: gzzyj on 2016/11/23.
 * email:zhyongjun@windhike.cn
 */

public class RxTransForm {
    private void print(String string) {
        System.out.println(string);
    }

    private List<String> generateCap() {
        List<String> list = new ArrayList<>();
        list.add("D");
        list.add("B");
        list.add("C");
        return list;
    }

    public void testBuffer() {

//        Observable.just(1,2,3,4,5,6,7,8,9)
////                .buffer(2)
//                .buffer(2,3)
//                .subscribe(new Action1<List<Integer>>() {
//                    @Override
//                    public void call(List<Integer> integers) {
//                        print("=========="+integers.toString());
//                    }
//                });
        Observable.from(generateCap())
        .buffer(2)
        .subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> lists) {
                print("=========="+lists.toString());
            }
        });
    }

    public void testFlatmap() {
        Observable.just(1,2,3,4,5,6,7,8,9)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        return Observable.just(integer);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        print("------"+integer);
                    }
                });
    }

    public void testFlagmapIterater() {
        Observable.just(1,2,3,4,5,6,7,8,9)
                .flatMapIterable(new Func1<Integer, Iterable<Integer>>() {
                    @Override
                    public Iterable<Integer> call(Integer integer) {
                        print("----------"+integer);
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(integer);
                        return list;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                    }
                });
    }

    public void testGroupBy() {
        Observable.just(1,2,3,4,5,6,7,8,9)
                .groupBy(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer%2;
                    }
                })
        .subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
            @Override
            public void call(GroupedObservable<Integer, Integer> result) {
//                print("============="+result.getKey());
                if(result.getKey() == 0) {
                    result.subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            print("=============" + result.getKey() + "---" + integer);
                        }
                    });
                }
            }
        });
    }

    public void testMap() {
        Observable.just(1,2,3,4)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer*2;
                    }
                });
    }

    public void testCast() {
        Observable.just(1,2,3)
                .cast(Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer s) {
                        print("--------"+s);
                    }
                });
    }

    public void testScan() {
        Observable.just(1,2,3,4,5,6,7,8)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        print("----"+integer+"---"+integer2);
                        return integer*integer2;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    public void testWindow() {
        Observable.just(1,2,3,4,5,6)
                .window(2)
                .subscribe(new Action1<Observable<Integer>>() {
                    @Override
                    public void call(Observable<Integer> integerObservable) {
                        print("------call----");
                        integerObservable.subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                print("--------------"+integer);
                            }
                        });
                    }
                });
    }

    public void testSwitchMap() {
        Observable.just(1,2,3,4,5,6)
                .switchMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        return Observable.just(integer).subscribeOn(Schedulers.newThread());
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                print("------"+integer);
            }
        });
    }
}
