package com.smp.rxplayround.sample.operator.combine;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

/**
 * Created by Minku on 2016. 5. 24..
 */
@Slf4j
public class Switch extends BasePlayground {

    @Test
    public void play() throws Exception {
        Observable<Observable<Long>> observable = Observable.create(new Observable.OnSubscribe<Observable<Long>>() {
            @Override
            public void call(final Subscriber<? super Observable<Long>> subscriber) {
                final Observable<Long> first = Observable.interval(30, TimeUnit.MILLISECONDS).take(5);
                final Observable<Long> second = Observable.interval(30, TimeUnit.MILLISECONDS).take(7);
                final Observable<Long> third = Observable.interval(30, TimeUnit.MILLISECONDS).take(10);

                subscriber.onNext(first);

                Schedulers.newThread().createWorker().schedule(new Action0() {
                    @Override
                    public void call() {
                        subscriber.onNext(second);
                    }
                }, 100, TimeUnit.MILLISECONDS);

                Schedulers.newThread().createWorker().schedule(new Action0() {
                    @Override
                    public void call() {
                        subscriber.onNext(third);
                        subscriber.onCompleted();
                    }
                }, 230, TimeUnit.MILLISECONDS);
            }
        });

        Observable.switchOnNext(observable).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
                stopWaitingForObservable();

            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError");
                stopWaitingForObservable();

            }

            @Override
            public void onNext(Long aLong) {
                log.debug("onNext : " + aLong);
            }
        });

        waitForObservable();
    }
}
