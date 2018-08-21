package com.zl.thread.service.impl;

import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhouliang
 * @Date: 2018/8/21 11:25
 */
@Service
public class CountDownLatchService {
    /**
     * CountDownLatch 在主线程里面操作其他子线程等待的类
     *
     * 可以控制子线程什么时候执行，什么时候阻塞等待
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int taskNum=10;
        AtomicInteger atomicInteger=new AtomicInteger(0);

        CountDownLatch readyLatch=new CountDownLatch(taskNum);
        CountDownLatch beginLatch=new CountDownLatch(1);
        CountDownLatch endLatch=new CountDownLatch(taskNum);

        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("主线程------------>>>> 开始分发任务。");

        for (int i = 1; i <= 10; i++) {
            Task task = new Task(String.valueOf(i), readyLatch, beginLatch, endLatch, atomicInteger);
            executorService.execute(task);
        }

        System.out.println("主线程--------->>> 等待所有子线程准备。");
        //让所有的子线程都进入子线程里面的方法
        readyLatch.await();
        //上面能跑过  readyLatch.await(); 之后代表所有子线程都进入子线程的放
        System.out.println("主线程---------->>>> 所有子线程准备完毕，子线程开始执行。。");
        //这个时候释放阻塞的全部子线程
        beginLatch.countDown();
        //等待所有的子线程跑完累加
        endLatch.await();
        System.out.println("主线程-------->>>>> 所有子线程执行完毕，获得结果："+atomicInteger.get());
    }

    public static class Task implements Runnable{
        private String taskCode;

        private AtomicInteger count;

        private CountDownLatch ready_latch;

        private CountDownLatch begin_latch;

        private CountDownLatch end_latch;

        public Task(String taskCode, CountDownLatch ready_latch, CountDownLatch begin_latch,
                    CountDownLatch end_latch, AtomicInteger count) {
            super();
            this.taskCode = taskCode;
            this.begin_latch = begin_latch;
            this.end_latch = end_latch;
            this.ready_latch = ready_latch;
            this.count = count;
        }

        @Override
        public void run() {
            try{
                //子线程只要一进入这个方法，计算器就减去1
                ready_latch.countDown();
                //但是不能让这些子线程继续往下面跑了，则应该阻塞这些子线程
                begin_latch.await();//什么时候放开这些子线程？等这些子线程都全部进入这个方法的时候
                //在主线程释放这些阻塞的子线程之后，正常进行 逻辑计算
                count.addAndGet(Integer.valueOf(taskCode));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //每个子线程跑完必然进入这里面
                end_latch.countDown();
            }
        }
    }
}
