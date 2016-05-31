package com.smp.rxplayround.sample.schedulers;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Minku on 2016. 5. 25..
 */
@Slf4j
public class NewThread extends BasePlayground {
    @Test
    public void play() throws Exception {
        final String[] convert = {"A", "B", "C", "D", "E"};
        final Observable<Long> observable1 = Observable.interval(50, TimeUnit.MILLISECONDS).take(5);
        Observable<Long> observable2 = Observable.interval(100, TimeUnit.MILLISECONDS).take(5);

        Schedulers.newThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                observable1.subscribeOn(Schedulers.io())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                log.debug("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log.debug("onError");
                            }

                            @Override
                            public void onNext(Long s) {
                                log.debug("onNext : " + convert[s.intValue()]);

                            }
                        });
            }
        }, 130, TimeUnit.MILLISECONDS);


        observable2
                .subscribe(new Observer<Long>() {
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
