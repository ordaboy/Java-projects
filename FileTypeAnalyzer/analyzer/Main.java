package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Args (patterns' file, path) required!");
        }
        File patternsFile = new File(args[1]);
        List<Map.Entry<String, String>> list = readPatterns(patternsFile);

        File initDir = new File(args[0]);
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
            // Choose appropriate algorithm to search
            SearchAlgorithm algorithm = null;
            algorithm = new RabinKarpSearchAlgorithm();
//            algorithm = new KMPSearchAlgorithm();
//            algorithm = new NaiveSearchAlgorithm();
            Finder finder = new Finder(file, algorithm);
            boolean added = false;
            for (Map.Entry entry : list) {
                String pattern = (String) entry.getKey();
                String result = (String) entry.getValue();
                boolean found = finder.contains(pattern);
                if (found) {
                    futures.add(executor.submit(() -> System.out.println(file.getName() + ": " + result)));
                    added = true;
                    break;
                }
            }
            if (!added) {
                futures.add(executor.submit(() -> System.out.println(file.getName() + ": Unknown file type")));
            }
        }
        executor.shutdown();
        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Map.Entry<String, String>> readPatterns(File patternsFile) {
        List<Map.Entry<String, String>> ans = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(patternsFile);
            while (scanner.hasNext()) {
                String[] temp = scanner.nextLine().replace("\"", "").split(";");
                ans.add(0, new MyEntry<>(temp[1], temp[2]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ans;

    }
}
