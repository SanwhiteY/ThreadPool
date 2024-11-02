package com.swy.juc.thread_pc;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Callable<Integer> {
    private SharedResource sharedResource;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int k = 0;
    private int t = 0;

    private AtomicInteger cnt;

    public Consumer(){
    }

    public Consumer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
        this.cnt = sharedResource.getCnt();
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        System.out.println(k++);
        try{
            cnt = sharedResource.getCnt();
            while(cnt.get() == 0){
                condition.await();
            }
            cnt.compareAndSet(cnt.get(), cnt.get() - 1);
            System.out.println(Thread.currentThread().getName() + "=>" + sharedResource.getCnt());

            condition.signalAll();
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
//            decrement();
            if(cnt.get() != 0){
                synchronized (sharedResource){
                    while(cnt.get() != 0){
                        decrement();
                    }
                }

            }
        } catch (InterruptedException e) {
            sign = 0;
            throw new RuntimeException(e);
        }
        return sign;
    }
}
