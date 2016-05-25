package com.smp.rxplayround.sample.subject;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class ReplaySubject extends BasePlayground {

    @Test
    public void play() throws Exception {

        rx.subjects.ReplaySubject<String> replaySubject = rx.subjects.ReplaySubject.create();

        replaySubject.onNext("test1");
        replaySubject.onNext("test2");

        replaySubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log.debug("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log.debug("onError : {}", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log.debug("onNext : {}", s);
            }
        });

        replaySubject.onNext("test3");
        replaySubject.onNext("test4");
        replaySubject.onCompleted();
    }
}
