package com.swy.juc.thread_pc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConsoleTwoThread {

    public static void main(String[] args) throws Exception {
        SharedResource sharedReSource = new SharedResource();

        Productor productor = new Productor(sharedReSource);
        Consumer consumer = new Consumer(sharedReSource);

        new Thread(()->{
            try {
                for(int i=0; i<1000; i++){
                    synchronized (sharedReSource){
                        productor.increment();
                    }
//                    System.out.println(sharedReSource.getCnt());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "productor").start();

        new Thread(()->{
            try {
                for(int i=0; i<1000; i++){
                    synchronized (sharedReSource){
                        consumer.decrement();
                    }
//                    System.out.println(sharedReSource.getCnt());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "consumer").start();
    }
}
