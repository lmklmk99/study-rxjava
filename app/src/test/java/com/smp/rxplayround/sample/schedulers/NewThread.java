package com.smp.rxplayround.sample.schedulers;

import com.smp.rxplayround.BasePlayground;

import junit.framework.Assert;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import rx.Scheduler;
import rx.functions.Action0;
import rx.internal.schedulers.ScheduledAction;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.CountDownLatch;
import 	java.util.concurrent.atomic.AtomicReference;
import 	java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Minku on 2016. 5. 25..
 */
@Slf4j
public class NewThread extends BasePlayground {
    @Test(timeout = 3000)
    public void play() throws InterruptedException {
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        try {
            final CountDownLatch run = new CountDownLatch(1);
            final CountDownLatch done = new CountDownLatch(1);
            final AtomicReference<Throwable> exception = new AtomicReference<Throwable>();
            final AtomicBoolean interruptFlag = new AtomicBoolean();

            ScheduledAction sa = (ScheduledAction) worker.schedule(new Action0() {
                @Override
                public void call() {
                    try {
                        run.await();
                    } catch (InterruptedException ex) {
                        exception.set(ex);
                    }
                }
            });

            sa.add(Subscriptions.create(new Action0() {
                @Override
                public void call() {
                    interruptFlag.set(Thread.currentThread().isInterrupted());
                    done.countDown();
                }
            }));

            run.countDown();

            done.await();

            Assert.assertEquals(null, exception.get());
            Assert.assertFalse("Interrupted?!", interruptFlag.get());
        } finally {
            worker.unsubscribe();
        }
    }
}
