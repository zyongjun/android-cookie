package com.example;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class RxBean {

    private List<String> generateCap() {
        List<String> list = new ArrayList<>();
        list.add("A");
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

}
