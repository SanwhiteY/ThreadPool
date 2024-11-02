package com.swy.juc.thread_pc;

import java.util.concurrent.*;

public class ThreadPool {
    private final ThreadPoolExecutor tpool;
    public ThreadPool(int corePoolSize, int maxPoolSize, int keepAliveTime, TimeUnit unit, int queueCapacity) {
        this.tpool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                new ArrayBlockingQueue<>(queueCapacity),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    public Future<Integer> submitTask(Callable<Integer> task) {
        return tpool.submit(task);
    }

    public void shutdown() {
        tpool.shutdown();
    }

    public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        tpool.awaitTermination(timeout, unit);
    }

    public int getActiveCount() {
        return tpool.getActiveCount();
    }
}
