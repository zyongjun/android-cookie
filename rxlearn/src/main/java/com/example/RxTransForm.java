package com.example;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

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
}
