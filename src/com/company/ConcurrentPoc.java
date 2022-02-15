package com.company;

import java.util.concurrent.*;


class Task implements Runnable {
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("end task " + name);
    }
}


public class ConcurrentPoc {
    // from: 多线程--线程池
    public void threadPool(){
        System.out.println("In the threadPool");
        // 创建一个固定大小的线程池:
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 6; i++) {
            es.submit(new Task("" + i));
        }
        // 关闭线程池:使用
        // shutdown()方法关闭线程池的时候，它会等待正在执行的任务先完成，然后再关闭。
        // shutdownNow()会立刻停止正在执行的任务，
        // awaitTermination()则会等待指定的时间让线程池关闭。
        es.shutdown();
    }
}
