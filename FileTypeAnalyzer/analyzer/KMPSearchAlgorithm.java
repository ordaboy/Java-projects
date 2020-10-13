package analyzer;

public class KMPSearchAlgorithm implements SearchAlgorithm {
    public KMPSearchAlgorithm() {
    }

    public boolean contains(String text, String pattern) {
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

    private static int[] prefixFunction(String pattern) {
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
}
