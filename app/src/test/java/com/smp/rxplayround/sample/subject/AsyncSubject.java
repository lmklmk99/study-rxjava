package com.smp.rxplayround.sample.subject;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;

/**
 * Created by myungpyo.shim on 2016. 4. 25..
 */
@Slf4j
public class AsyncSubject extends BasePlayground {

    @Test
    public void play() throws Exception {

        rx.subjects.AsyncSubject<String> asyncSubject = rx.subjects.AsyncSubject.create();

        asyncSubject.onNext("test1");
        asyncSubject.onNext("test2");

        asyncSubject.subscribe(new Observer<String>() {
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

        asyncSubject.onNext("test3");
        asyncSubject.onNext("test4");
        asyncSubject.onCompleted();
    }
}
