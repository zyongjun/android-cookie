package com.example;

public class RxTest {

    public static void main(String[] args) {
        RxBean rx = new RxBean();
//        rx.testMerge();
//        rx.testSubscriber();
//        rx.testFrom();
//        rx.testJoin();
//        rx.testDefer();
//        rx.testRange();
//        rx.testInterval();
//        rx.testTimer();
//        rx.testFilter();
//        rx.testFlatmap();

        RxCreate create = new RxCreate();
//        create.testCreate();
//        create.testRange();
//        create.testJust();
//        create.testInterval();
        create.testTimer();
    }

}
