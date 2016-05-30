package com.smp.rxplayround.sample.schedulers;

import com.smp.rxplayround.BasePlayground;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Minku on 2016. 5. 25..
 */
@Slf4j
public class Computation extends BasePlayground {
    @Test
    public final void play() throws Exception {
        Observable<Integer> o1 = Observable.<Integer> just(1, 2, 3, 4, 5);
        Observable<Integer> o2 = Observable.<Integer> just(6, 7, 8, 9, 10);
        Observable<String> o = Observable.<Integer> merge(o1, o2).map(new Func1<Integer, String>() {

            @Override
            public String call(Integer t) {
                return "Value_" + t + "_Thread_" + Thread.currentThread().getName();
            }
        });

        o.subscribeOn(Schedulers.computation()).toBlocking().forEach(new Action1<String>() {

            @Override
            public void call(String t) {
                System.out.println("t: " + t);
            }
        });
    }
}
