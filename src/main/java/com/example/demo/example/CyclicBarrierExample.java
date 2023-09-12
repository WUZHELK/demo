package com.example.demo.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CyclicBarrierExample {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i < 10; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        log.info("finish");
        executorService.shutdown();
    }

    private static void test(int threadCount) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadCount);
        cyclicBarrier.await();
        log.info("{} is continue", threadCount);
    }
}
