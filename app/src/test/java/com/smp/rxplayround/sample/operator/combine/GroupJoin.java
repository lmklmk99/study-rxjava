package com.smp.rxplayround.sample.operator.combine;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Minku on 2016. 5. 24..
 */
@Slf4j
public class GroupJoin extends BasePlayground {

    @Test
    public void play() throws Exception {
        final String[] convert = {"A", "B", "C", "D", "E"};
        Observable<Long> combineObservable = Observable.interval(100, TimeUnit.MILLISECONDS).take(5);
        Observable<Long> observable1 = Observable.interval(150, TimeUnit.MILLISECONDS).take(5);

        combineObservable.groupJoin(observable1,
                new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long s) {
                        return Observable.timer(750, TimeUnit.MILLISECONDS);
                    }
                },
                new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long s) {
                        return Observable.timer(130, TimeUnit.MILLISECONDS);
                    }
                },
                new Func2<Long, Observable<Long>, Observable<String>>() {
                    @Override
                    public Observable<String> call(final Long left, Observable<Long> longObservable) {
                        return longObservable.map(new Func1<Long, String>() {
                            @Override
                            public String call(Long aLong) {
                                return convert[left.intValue()] + aLong;
                            }
                        });
                    }
                }).subscribe(new Subscriber<Observable<String>>() {
            @Override
            public void onCompleted() {
                log.debug("groupJoin onCompleted");
                stopWaitingForObservable();
            }

            @Override
            public void onError(Throwable e) {
                log.debug("groupJoin onError");
                stopWaitingForObservable();
            }

            @Override
            public void onNext(Observable<String> stringObservable) {
                log.debug("groupJoin onNext");
                stringObservable.subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        log.debug("Join onCompleted");
                        stopWaitingForObservable();
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.debug("Join onError");
                        stopWaitingForObservable();
                    }

                    @Override
                    public void onNext(String s) {
                        log.debug("Join onNext : " + s);
                    }
                });
            }
        });

        waitForObservable();
    }
}
