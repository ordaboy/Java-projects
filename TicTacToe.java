package tictactoe;
import java.util.Scanner;

public class Main {
    public static void printField(char[][] arr) {
        System.out.println("---------");
        for (int i = 2; i >= 0; i--) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("|\n");
        }
        System.out.println("---------\n");
    }
    
    public static char check(char[][] arr) {
        for (int i = 0; i < 3; i++) {
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2]) {
                if (arr[i][0] == 'X') {
                    return 'X';
                } else if (arr[i][0] == 'O') {
                    return 'O';
                }
            }
            if (arr[0][i] == arr[1][i] && arr[1][i] == arr[2][i]) {
                if (arr[0][i] == 'X') {
                    return 'X';
                } else if (arr[0][i] == 'O') {
                    return 'O';
                }
            }
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2]) {
            if (arr[0][0] == 'X') {
                return 'X';
            } else if (arr[0][0] == 'O') {
                return 'O';
            }
        }
        if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0]) {
            if (arr[0][2] == 'X') {
                return 'X';
            } else if (arr[0][2] == 'O') {
                return 'O';
            }
        }
        return 'N';
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] symbols = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                symbols[i][j] = ' ';
            }
        }
        printField(symbols);
        boolean gameOn = true;
        boolean q = true;
        int count = 0;
        while (gameOn) {
            System.out.print("Enter the coordinates: ");
            boolean right = true;
            String temp = scanner.next();
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) < '0' || temp.charAt(i) > '9') {
                    right = false;
                    break;
                }
            }
            if (!right) {
                System.out.println("You should enter numbers!");
                continue;
            }
            // System.out.println(temp);
            int a = Integer.parseInt(temp);
            temp = scanner.next();
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) < '0' || temp.charAt(i) > '9') {
                    right = false;
                    break;
                }
            }
            if (!right) {
                System.out.println("You should enter numbers!");
                continue;
            }
            // System.out.println(temp);
            int b = Integer.parseInt(temp);
            // System.out.println("a and b are equal to: " + a + " " + b);
            if (a > 3 || a < 1 || b > 3 || b < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (symbols[b - 1][a - 1] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                if (q) {
                    symbols[b - 1][a - 1] = 'X';
                    q = false;
                } else {
                    symbols[b - 1][a - 1] = 'O';
                    q = true;
                }
                count++;
                printField(symbols);
                switch (check(symbols)) {
                    case 'X':
                        System.out.println("X wins");
                        gameOn = false;
                        break;
                    case 'O':
                        System.out.println("O wins");
                        gameOn = false;
                        break;
                    default:
                        if (count == 9) {
                            gameOn = false;
                            System.out.println("Draw");
                        }
                        break;
                }
            }
            
        }
        
    }
}
