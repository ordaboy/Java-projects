package analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Args(path, pattern, message) required!");
        }
        String dir = args[0];
        String pattern = args[1];
        String result = args[2];

        File initDir = new File(dir);
        if (!initDir.exists()) {
            throw new IllegalArgumentException(initDir.getName() + " doesn't exist!");
        }
        File[] files = initDir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException(initDir.getName() + " doesn't contain files!");
        }
        ExecutorService executor = Executors.newFixedThreadPool(files.length);
        final List<Future<?>> futures = new ArrayList<>();
        for (File file : files) {
            KMPSearchAlgorithm algorithm = new KMPSearchAlgorithm();
            Finder finder = new Finder(file, algorithm);
            futures.add(executor.submit(() -> {
                System.out.println(file.getName() + ": " + (finder.contains(pattern) ? result : "Unknown file type"));
            }));
        }
        executor.shutdown();
        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
