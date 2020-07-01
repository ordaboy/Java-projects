package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int numberOfMines = scanner.nextInt();
        int sizeOfField = 9;
        Game game = new Game(sizeOfField);
        while (!game.isGameOn()) {
            getInput(scanner, numberOfMines, sizeOfField, game);
        }
        while (game.isGameOn()) {
            getInput(scanner, numberOfMines, sizeOfField, game);
            game.isGameFinished();

        }
    }

    private static void getInput(Scanner scanner, int numberOfMines, int sizeOfField, Game game) {
        int y;
        int x;
        String command;
        game.printField();
        System.out.print("Set/unset mines marks or claim a cell as free ");
        y = scanner.nextInt();
        x = scanner.nextInt();
        y--;
        x--;
        command = scanner.next();
        if (game.isPositionSelected(x, y)) {
            System.out.println("It is already selected. Choose another one");
            return;
        }
        if ("free".equals(command)) {
            if (game.isGameOn()) {
                game.checkPos(x, y);
            } else {
                game.setMinesOnField(numberOfMines, x * sizeOfField + y);
            }
        } else if ("mine".equals(command)) {
            game.setFlag(x, y);
        } else {
            System.out.println("Invalid Input");
            return;
        }
    }
}
