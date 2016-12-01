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
//        create.testTimer();

        RxTransForm transForm = new RxTransForm();
//        transForm.testBuffer();
//        transForm.testFlatmap();
//        transForm.testFlagmapIterater();
//        transForm.testGroupBy();
//        transForm.testCast();
//        transForm.testScan();
//        transForm.testWindow();
//        transForm.testSwitchMap();
        RxFilter filter = new RxFilter();
//        filter.testDebound();
//        filter.testDistinct();
//        filter.testDistinctUtilChanged();
//        filter.testFilter();
//        filter.testElementAt();
//        filter.testFirst();
//        filter.testTake();
//        filter.testThrottleLast();
        RxCombining rxCombining = new RxCombining();
//        RxCombining.testCombiningLast();
//        RxCombining.testJoin();
//        RxCombining.testMerge();
//        RxCombining.testConcat();
//        rxCombining.testMergeDelayError();
//        rxCombining.testStartWith();
//        rxCombining.testSwtich();
//        rxCombining.testZip();
//        rxCombining.testZipWith();

        RxError rxError = new RxError();
//        rxError.testCatch();
//        rxError.testOnErrorResumeNext();
        rxError.testOnExceptionResumeNext();
    }

}
