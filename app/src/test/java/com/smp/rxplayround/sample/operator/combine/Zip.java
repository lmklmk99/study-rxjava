package com.smp.rxplayround.sample.operator.combine;

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
public class Zip extends BasePlayground {

    @Test
    public void play() throws Exception {
        final String[] convert = {"A", "B", "C", "D", "E"};
        Observable<Long> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS).take(5);
        Observable<Long> observable2 = Observable.interval(150, TimeUnit.MILLISECONDS).take(3);

        Observable.zip(observable1, observable2, new Func2<Long, Long, String>() {
            @Override
            public String call(Long s, Long s2) {
                return convert[s.intValue()] + s2;
            }
        }).subscribe(new Observer<String>() {
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
            public void onNext(String s) {
                log.debug("onNext : " + s);
            }
        });

        waitForObservable();
    }
}
