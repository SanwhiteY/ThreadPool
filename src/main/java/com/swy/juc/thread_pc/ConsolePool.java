package com.swy.juc.thread_pc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConsolePool {

    public static void main(String[] args) throws Exception {
        SharedResource sharedReSource = new SharedResource();

        Productor productor = new Productor(sharedReSource);
        Consumer consumer = new Consumer(sharedReSource);

        ThreadPool threadPool = new ThreadPool(5, 10, 60, TimeUnit.SECONDS, 40);

        List<Future<Integer>> futures = new ArrayList<>();

        for(int i = 0; i < 1000; ++i){
            futures.add(threadPool.submitTask(productor));
            futures.add(threadPool.submitTask(consumer));
            Thread.sleep(1);
        }


        threadPool.shutdown();

        threadPool.awaitTermination(30, TimeUnit.SECONDS);



//        new Thread(()->{
//            try {
//                for(int i=0; i<1000; i++){
//                    synchronized (sharedReSource){
//                        productor.increment();
//                    }
////                    System.out.println(sharedReSource.getCnt());
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }, "productor").start();
//
//        new Thread(()->{
//            try {
//                for(int i=0; i<1000; i++){
//                    synchronized (sharedReSource){
//                        consumer.decrement();
//                    }
////                    System.out.println(sharedReSource.getCnt());
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }, "consumer").start();
    }
}
