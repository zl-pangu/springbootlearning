package com.zl.thread.service.impl;

import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhouliang
 * @Date: 2018/8/21 10:34
 */
@Service
public class AtomicIntegerService {
    /**
     * 类似于i++，但是是线程安全的
     */
    public static  final AtomicInteger atomicInteger=new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        atomicIntegerTest();

        Thread.sleep(3000);
        System.err.println("最终结果是===============================>"+atomicInteger.get());
    }

    private static void atomicIntegerTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(()->{
                for (int j = 0; j < 4; j++) {
                    System.out.println(atomicInteger.getAndIncrement());
                }
            });
        }
        executorService.shutdown();
    }

}
