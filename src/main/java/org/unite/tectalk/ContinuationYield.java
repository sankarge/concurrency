package org.unite.tectalk;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public class ContinuationYield {

    //--enable-preview --add-exports java.base/jdk.internal.vm=ALL-UNNAMED

    public static void main(String[] args) {
        ContinuationScope scope = new ContinuationScope("global");
        Continuation continuation = new Continuation(scope, () -> {
            System.out.println("C1: " + Thread.currentThread());
            Continuation.yield(scope);

            System.out.println("C2: " + Thread.currentThread());
            Continuation.yield(scope);

            System.out.println("C3: " + Thread.currentThread());
            Continuation.yield(scope);
        });

        System.out.println("Run first");
        continuation.run();

        System.out.println("Run second");
        continuation.run();

        System.out.println("Run third");
        continuation.run();

        System.out.println("Done");
    }
}
