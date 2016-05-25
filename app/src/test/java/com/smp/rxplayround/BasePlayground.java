package com.smp.rxplayround;

import android.support.annotation.Nullable;

import java.util.concurrent.CountDownLatch;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public abstract class BasePlayground {

    @Nullable
    private CountDownLatch lock;

    protected void waitForObservable() {
        lock = new CountDownLatch(1);
    }
    protected void waitForObservable(int count) {
        lock = new CountDownLatch(count);
        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void stopWaitingForObservable() {
        if (lock == null) {
            return;
        }
        lock.countDown();
    }
}