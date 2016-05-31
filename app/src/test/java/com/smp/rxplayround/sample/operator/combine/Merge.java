package com.smp.rxplayround.sample.operator.combine;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;

/**
 * Created by Minku on 2016. 5. 24..
 */
@Slf4j
public class Merge extends BasePlayground {

    @Test
    public void play() throws Exception {
        Observable<Long> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS).take(10);
        Observable<Long> observable2 = Observable.interval(300, TimeUnit.MILLISECONDS).take(3);

        Observable<Long> combineObservable = Observable.merge(observable1, observable2);

        combineObservable.subscribe(new Observer<Long>() {
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
