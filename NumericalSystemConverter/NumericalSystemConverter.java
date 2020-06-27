package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean fractional = false;
        int sourceBase = 1;
        try {
            sourceBase = scanner.nextInt();
        } catch (Exception e) {
            PrintError();
            return;
        }
        String num = scanner.next();
        if (num.contains(".")) {
            fractional = true;
        }
        int newBase = 1;
        try {
            newBase = scanner.nextInt();
        } catch (Exception e) {
            PrintError();
            return;
        }
        if (sourceBase < 1 || newBase < 1 || sourceBase > 36 || newBase > 36) {
            PrintError();
            return;
        }
        if (!CheckBase(num, sourceBase)) {
            PrintError();
            return;
        }
        double ans = 0;
        if (sourceBase == 1) {
            ans = num.length();
        } else if (sourceBase != 10) {
            if (fractional) {
                String intPart;
                String fracPart;
                int pos = num.indexOf('.');
                intPart = num.substring(0, pos);
                fracPart = num.substring(pos + 1);
                ans = Integer.parseInt(intPart, sourceBase);
                ans += CalcFrac(fracPart, sourceBase);
            } else {
                ans = Integer.parseInt(num, sourceBase);
            }
        } else {
            ans = Double.parseDouble(num);
        }
        if (newBase == 1) {
            for (int i = 0; i < (int) ans; i++) {
                System.out.print(1);
            }
        } else {
            if (fractional) {
                System.out.println(CalcNewStr(ans, newBase));
            } else {
                System.out.println(Integer.toString((int) ans, newBase));
            }
        }
    }

    static boolean CheckBase(String num, int base) {
        for (int i = 0; i < num.length(); i++) {
            int t = 0;
            if (num.charAt(i) == '.') continue;
            if (Character.isLowerCase(num.charAt(i))) {
                t = num.charAt(i) - 87;
            } else {
                t = num.charAt(i) - 48;
            }
            if (t > base) {
                return false;
            }
        }
        return true;
    }

    static void PrintError() {
        System.out.println("error");
    }

    static String CalcNewStr(double num, int base) {
        String ans = "";
        String intPart = Integer.toString((int)num, base);
        ans += intPart + ".";
        double frac = num - (int)num;
        for (int i = 0; i < 5; i++) {
            frac *= base;
            ans += Integer.toString((int)frac, base);
            frac -= (int)frac;
        }
        return ans;
    }

    static double CalcFrac(String num, int base) {
        double ans = 0;
        int deg = base;
        for (int i = 0; i < num.length(); i++) {
            char t = num.charAt(i);
            double x = 0;
            if (Character.isLowerCase(t)) {
                x = t - 87;
            } else {
                x = t - 48;
            }
            ans += x / deg;
            deg *= base;
        }
        return ans;
    }
}
