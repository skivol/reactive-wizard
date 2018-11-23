package se.fortnox.reactivewizard.server;

import javax.inject.Singleton;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class ConnectionCounter {
    private final AtomicLong connections = new AtomicLong(0);
    private final Semaphore connectionsZero = new Semaphore(1);

    public void increase() {
        if (connections.getAndIncrement() == 0) {
            connectionsZero.tryAcquire();
        }
    }

    public void decrease() {
        if (connections.decrementAndGet() == 0) {
            connectionsZero.release();
        }
    }

    public boolean awaitZero(int time, TimeUnit timeUnit) {
        try {
            boolean success = connectionsZero.tryAcquire(time, timeUnit);
            if (success) {
                connectionsZero.release();
            }
            return success;
        } catch (Exception e) {
            return false;
        }
    }

    public long getCount() {
        return connections.get();
    }
}
