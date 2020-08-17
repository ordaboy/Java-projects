package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Less arguments!");
            System.exit(0);
        }
        String algorithm = args[0];
        String file = args[1];
        String pattern = args[2];
        String result = args[3];

        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(file));
            String str = "";
            for (int i = 0; i < allBytes.length; i++) {
                str += (char) allBytes[i];
            }
            if ("--naive".equals(algorithm)) {
                boolean found = false;
                long startTime = System.nanoTime();
                for (int i = 0; i < str.length() - pattern.length() + 1; i++) {
                    boolean equal = true;
                    for (int j = 0; j < pattern.length(); j++) {
                        if (str.charAt(i + j) != pattern.charAt(j)) {
                            equal = false;
                            break;
                        }
                    }
                    if (equal) {
                        found = true;
                        break;
                    }
                }
                long elapsedTime = System.nanoTime() - startTime;
                System.out.println(found ? result : "Unknown file type");
                System.out.println("It took " + elapsedTime + " seconds");
            } else if ("--KMP".equals(algorithm)) {
                boolean found = false;
                long startTime = System.nanoTime();
                found = KMPSearch(str, pattern);
                long elapsedTime = System.nanoTime() - startTime;
                System.out.println(found ? result : "Unknown file type");
                System.out.println("It took " + elapsedTime + " seconds");
            } else {
                System.out.println("Wrong input!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] prefixFunction(String pattern) {
        int[] result = new int[pattern.length()];
        for (int i = 1; i < pattern.length(); i++) {
            int j = result[i - 1];
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = result[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            result[i] = j;
        }
        return result;
    }

    public static boolean KMPSearch(String text, String pattern) {
        int[] prefFunc = prefixFunction(pattern);
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefFunc[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                return true;
            }
        }
        return false;
    }
}
