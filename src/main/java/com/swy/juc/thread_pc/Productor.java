package com.swy.juc.thread_pc;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Productor implements Callable<Integer> {
    private SharedResource sharedResource;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private AtomicInteger cnt;

    private Productor(){
    }

    public Productor(SharedResource sharedResource){
        this.sharedResource = sharedResource;
        this.cnt = sharedResource.getCnt();
    }

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            cnt = sharedResource.getCnt();
//            while(cnt.get() != 0) {
//                condition.await();
//            }
//            cnt.compareAndSet(0, 1);
            cnt.compareAndSet(cnt.get(), cnt.get() + 1);
            System.out.println(Thread.currentThread().getName() + "=>" + sharedResource.getCnt());
//            condition.signalAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Integer call() throws Exception {
        Integer sign = 1;
        try {
            increment();
//            synchronized (sharedResource){
//                increment();
//            }
//            if(cnt.get() == 0){
//                synchronized (sharedResource){
//                    if(cnt.get() == 0){
//                        increment();
//                    }
//                }
//            }

        } catch (InterruptedException e) {
            sign = 0;
            throw new RuntimeException(e);
        }
        return sign;
    }
}
