package encryptdecrypt;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

class ConfigParams {
    String mode;
    String orgData;
    String cipherData;
    String inFile;
    String outFile;
    String alg;
    int key;

    ConfigParams() {
        this.mode = "enc";
        this.key = 0;
        this.orgData = "";
        this.cipherData = "";
        this.outFile = null;
        this.inFile = null;
        this.alg = "shift";
    }
}

public class Main {
    public static void main(String[] args) {
        ConfigParams prog = new ConfigParams();

        GetArguments(args, prog);

        if (prog.inFile != null && prog.orgData.equals("")) {
            getData(prog);
        }

        ProcessOriginalData(prog);

        PrintCipherData(prog);
    }

    public static void ProcessOriginalData(ConfigParams prog) {
        SelectionContext sc = new SelectionContext();

        if ("enc".equals(prog.mode)) {
            switch (prog.alg) {
                case "unicode":
                    sc.setAlgorithm(new EncryptByUnicode(prog.key));
                    break;
                case "shift":
                    sc.setAlgorithm((new EncryptByShift(prog.key)));
                    break;
            }
        } else if ("dec".equals(prog.mode)) {
            switch (prog.alg) {
                case "unicode":
                    sc.setAlgorithm(new DecryptByUnicode(prog.key));
                    break;
                case "shift":
                    sc.setAlgorithm((new DecryptByShift(prog.key)));
                    break;
            }
        }
        prog.cipherData = sc.process(prog.orgData);
    }

    public static void GetArguments(String[] args, ConfigParams prog) {
        for (int i = 0; i < args.length; i++) {
            String temp = args[i];
            switch (temp) {
                case "-mode":
                    i++;
                    prog.mode = args[i];
                    break;
                case "-key":
                    i++;
                    prog.key = Integer.parseInt(args[i]);
                    break;
                case "-data":
                    i++;
                    prog.orgData = args[i];
                    break;
                case "-in":
                    i++;
                    prog.inFile = args[i];
                    break;
                case "-out":
                    i++;
                    prog.outFile = args[i];
                    break;
                case "-alg":
                    i++;
                    prog.alg = args[i];
                    break;
            }
        }
    }

    public static void PrintCipherData(ConfigParams prog) {
        if (prog.outFile != null) {
            writeData(prog);
        } else {
            System.out.println(prog.cipherData);
        }
    }

    private static void writeData(ConfigParams prog) {
        File outFile = new File(prog.outFile);
        try {
            FileWriter writer = new FileWriter(outFile);
            writer.write(prog.cipherData);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getData(ConfigParams prog) {
        File inFile = new File(prog.inFile);
        try {
            Scanner scanner = new Scanner(inFile);
            while (scanner.hasNext()) {
                prog.orgData += scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

class SelectionContext {

    private Method algorithm;

    public void setAlgorithm(Method algorithm) {
        this.algorithm = algorithm;
    }

    public String process(String data) {
        return algorithm.ProcessData(data);
    }
}

interface Method {

    String ProcessData(String data);
}

abstract class dsa implements Method {

    protected int key;

    dsa(int key) {
        this.key = key;
    }

    @Override
    public String ProcessData(String data) {
        return null;
    }
}

class EncryptByShift extends dsa {

    EncryptByShift(int key) {
        super(key);
    }

    @Override
    public String ProcessData(String data) {
        String newWord = "";
        for (int i = 0; i < data.length(); i++) {
            char t = data.charAt(i);
            if (Character.isUpperCase(t)) {
                int x = t + key;
                if (x > 90) {
                    x -= 26;
                }
                t = (char) x;
            } else if (Character.isLowerCase(t)) {
                int x = t + key;
                if (x > 122) {
                    x -= 26;
                }
                t = (char) x;
            }
            newWord += t;
        }
        return newWord;
    }
}

class DecryptByShift extends dsa {

    DecryptByShift(int key) {
        super(key);
    }

    @Override
    public String ProcessData(String data) {
        String newWord = "";
        for (int i = 0; i < data.length(); i++) {
            char t = data.charAt(i);
            if (Character.isUpperCase(t)) {
                int x = t - key;
                if (x < 65) {
                    x += 26;
                }
                t = (char) x;
            } else if (Character.isLowerCase(t)) {
                int x = t - key;
                if (x < 97) {
                    x += 26;
                }
                t = (char) x;
            }
            newWord += t;
        }
        return newWord;
    }
}

class EncryptByUnicode extends dsa{

    public EncryptByUnicode(int key) {
        super(key);
    }

    @Override
    public String ProcessData(String data) {
        String newWord = "";
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            c = (char) (c + this.key);
            newWord += c;
        }
        return newWord;
    }
}

class DecryptByUnicode extends dsa {

    DecryptByUnicode(int key) {
        super(key);
    }

    @Override
    public String ProcessData(String data) {
        String newWord = "";
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            c = (char) (c - this.key);
            newWord += c;
        }
        return newWord;
    }
}
