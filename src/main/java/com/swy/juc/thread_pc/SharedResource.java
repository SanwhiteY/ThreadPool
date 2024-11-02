package com.swy.juc.thread_pc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class SharedResource {
    private volatile AtomicInteger cnt = new AtomicInteger(0);

    public AtomicInteger getCnt(){
        return cnt;
    }

    public void setCnt(AtomicInteger cnt){
        this.cnt = cnt;
    }
}


//public class SharedResource {
//    private static AtomicStampedReference<Integer> cnt = new AtomicStampedReference<>(0, 0);
//
//    public AtomicStampedReference<Integer> getCnt(){
//        return cnt;
//    }
//
//    public void setCnt(AtomicStampedReference<Integer> cnt){
//        this.cnt = cnt;
//    }
//}