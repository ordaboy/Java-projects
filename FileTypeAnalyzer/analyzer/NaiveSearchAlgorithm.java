package analyzer;

public class NaiveSearchAlgorithm implements SearchAlgorithm {
    public NaiveSearchAlgorithm() {
    }

    public boolean contains(String text, String pattern) {
        boolean found = false;
        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            boolean equal = true;
            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                found = true;
                break;
            }
        }
        return found;
    }
}
