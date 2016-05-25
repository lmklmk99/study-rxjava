package com.smp.rxplayround.sample;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;

/**
 * Created by Minku on 2016. 5. 24..
 */
@Slf4j
public class StartWith extends BasePlayground {

    @Test
    public void play() throws Exception {
        Observable<Long> observable = Observable.interval(100, TimeUnit.MILLISECONDS).take(5);
        observable.startWith(Long.valueOf(12)).subscribe(new Observer<Long>() {
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
