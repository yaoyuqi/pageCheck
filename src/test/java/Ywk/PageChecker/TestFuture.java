package Ywk.PageChecker;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestFuture {

    @Test
    public void testFutureError() {
        var executor = Executors.newFixedThreadPool(20);

        var futures = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> makeFuture(i, executor))
                .collect(Collectors.toList());

        var lastFuture = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));

        lastFuture.handle((v, e) -> {
            if (e != null) {
                System.out.println("error " + e.getMessage());
            }
            return v;
        }).join();

//        lastFuture.join();
    }

    private CompletableFuture<String> makeFuture(int i, Executor executor) {
        var future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
//                var rand = new Random();
//                var seed = rand.nextInt(10);
////                System.out.println(seed);
//                if (seed > 8) {
//                    throw new RuntimeException("aaaaaa" + i);
//                }
                if (i == 8 || i == 20) {
                    throw new RuntimeException("aaaaaa" + i);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "finished!" + i;
        }, executor)
                .thenApply(str -> str + "---applied");
//                .handle((str, e) -> {
////                    if (e != null) {
////                        System.out.println(e.getMessage());
////                    }
//                    return str;
//                });

        future.thenAccept(s -> {
            System.out.println("last accept ----" + i + "----" + s);

        });
        return future;
    }

}
