package org.unite.tectalk;

import java.util.stream.IntStream;

public class MaxPlatformThreads {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("JDK version = " + System.getProperty("java.version"));

        sleep(15_000);

        var threads =
                IntStream.range(0, 10_000)
                        .mapToObj(index ->
                                Thread.ofPlatform()
                                        .name("platform-", index)
                                        .unstarted(() -> {
                                            System.out.println(index);
                                            sleep(5_000);
                                        }))
                        .toList();

        for (Thread thread : threads) {
            thread.start();
        }
        for (var thread : threads) {
            thread.join();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}