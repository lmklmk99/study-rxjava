package com.smp.rxplayround.sample.subject;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class BehaviorSubject extends BasePlayground {

    @Test
    public void play() throws Exception {

        rx.subjects.BehaviorSubject<String> behaviorSubject = rx.subjects.BehaviorSubject.create();

        behaviorSubject.onNext("test1");
        behaviorSubject.onNext("test2");

        behaviorSubject.subscribe(new Observer<String>() {
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

        behaviorSubject.onNext("test3");
        behaviorSubject.onNext("test4");
        behaviorSubject.onCompleted();
    }
}
