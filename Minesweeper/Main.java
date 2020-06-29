package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();
        int size = 9;
        Field field = new Field(size);
        int y;
        int x;
        String command;
        while (!field.isGameOn()) {
            field.PrintField();
            System.out.print("Set/unset mines marks or claim a cell as free ");
            y = scanner.nextInt();
            x = scanner.nextInt();
            y--;
            x--;
            command = scanner.next();
            if ("free".equals(command)) {
                field.setGameOn(true);
                field.setMines(mines, x * size + y);
            } else if ("mine".equals(command)) {
                field.setFlag(x, y);
            } else {
                System.out.println("Invalid Input");
            }
        }
        while (field.isGameOn()) {
            field.PrintField();
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            y = scanner.nextInt();
            x = scanner.nextInt();
            x--;
            y--;
            command = scanner.next();
            if (!field.isPosIsFree(x, y) && !("mine".equals(command))) {
                System.out.println("Choose another one!");
                continue;
            }
            if ("free".equals(command)) {
                field.checkPos(x, y);
            } else if ("mine".equals(command)) {
                field.setFlag(x, y);
            } else {
                System.out.println("Invalid Input");
                continue;
            }
            if (field.checkGame()) {
                System.out.println("Congratulations! You found all mines!");
                field.setGameOn(false);
            }

        }
    }
}
