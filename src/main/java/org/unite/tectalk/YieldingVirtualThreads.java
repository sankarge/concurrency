package org.unite.tectalk;


import java.util.stream.IntStream;

public class YieldingVirtualThreads {

    public static void main(String[] args) throws InterruptedException {

        var pThreads = IntStream.range(0, 100)
                .mapToObj(index ->
                        Thread.ofPlatform()
                                .name("platform-" + index)
                                .unstarted(blockingTask(index)))
                .toList();

        for (Thread thread : pThreads) {
            thread.start();
        }
        for (Thread thread : pThreads) {
            thread.join();
        }


        var vThreads = IntStream.range(0, 100)
                .mapToObj(index ->
                        Thread.ofVirtual()
                                .name("virtual-" + index)
                                .unstarted(blockingTask(index)))
                .toList();

        for (Thread thread : vThreads) {
            thread.start();
        }
        for (Thread thread : vThreads) {
            thread.join();
        }
    }

    private static Runnable blockingTask(int index) {
        return () -> {
            sleep(100); //Blocking call #1
            if (index == 0) {
                System.out.println(Thread.currentThread());
            }

            sleep(100); //Blocking call #2
            if (index == 0) {
                System.out.println(Thread.currentThread());
            }

            sleep(200); //Blocking call #3
            if (index == 0) {
                System.out.println(Thread.currentThread());
            }

            sleep(100); //Blocking call #4
            if (index == 0) {
                System.out.println(Thread.currentThread());
            }
        };
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
