package minesweeper;

import java.util.Scanner;

public class GameHandler {

    private final int sizeOfField;
    private final int mines;

    GameHandler(int mines, int size) {
        Scanner scanner = new Scanner(System.in);
        this.mines = mines;
        this.sizeOfField = size;
        Game game = new Game(this.sizeOfField);
        int y;
        int x;
        String command;
        while (!game.isGameOn()) {
            game.printField();
            System.out.print("Set/unset mines marks or claim a cell as free ");
            y = scanner.nextInt();
            x = scanner.nextInt();
            y--;
            x--;
            command = scanner.next();
            if (game.isPositionSelected(x, y)) {
                System.out.println("It is already selected. Choose another one");
                continue;
            }
            if ("free".equals(command)) {
                game.setMinesOnField(mines, x * size + y);
            } else if ("mine".equals(command)) {
                game.setFlag(x, y);
            } else {
                System.out.println("Invalid Input");
            }
        }
        while (game.isGameOn()) {
            game.printField();
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            y = scanner.nextInt();
            x = scanner.nextInt();
            x--;
            y--;
            command = scanner.next();
            if (game.isPositionSelected(x, y)) {
                System.out.println("It is already selected. Choose another one");
                continue;
            }
            if ("free".equals(command)) {
                game.checkPos(x, y);
            } else if ("mine".equals(command)) {
                game.setFlag(x, y);
            } else {
                System.out.println("Invalid Input");
                continue;
            }
            game.isGameFinished();

        }
    }
}
