package com.smp.rxplayround.sample.subject;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class PublishSubject extends BasePlayground {

    @Test
    public void play() throws Exception {

        rx.subjects.PublishSubject<String> publishSubject = rx.subjects.PublishSubject.create();

        publishSubject.onNext("test1");
        publishSubject.onNext("test2");

        publishSubject.subscribe(new Observer<String>() {
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

        publishSubject.onNext("test3");
        publishSubject.onNext("test4");
        publishSubject.onCompleted();
    }
}
