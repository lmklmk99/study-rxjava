package com.smp.rxplayround.sample.schedulers;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Minku on 2016. 5. 25..
 */
@Slf4j
public class InOut extends BasePlayground {

    @Test
    public void play() throws Exception {
        Observable<String> observable1 = Observable.just("A", "B", "C", "D", "E");

        observable1.subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
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
