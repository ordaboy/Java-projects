package minesweeper;

public class Game {
    private final Cell[][] field;
    private final int sizeOfField;
    private int numberOfMines = 0;
    private int countTotalFlags = 0;
    private int countRightFlags = 0;
    private int countVisibleCell = 0;
    private boolean gameOn;

    public Game(int size) {
        this.sizeOfField = size;
        this.gameOn = false;
        field = new Cell[this.sizeOfField][this.sizeOfField];
        for (int i = 0; i < this.sizeOfField; i++) {
            for (int j = 0; j < this.sizeOfField; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    public void setMinesOnField(int mines, int firstMoveCell) {
        this.gameOn = true;
        this.numberOfMines = mines;
        setRandomMines(firstMoveCell);
        calculateNumbers();
        checkPos(firstMoveCell / this.sizeOfField, firstMoveCell % this.sizeOfField);
    }

    private void setRandomMines(int firstMoveCell) {
        for (int i = 0; i < this.numberOfMines; i++) {
            int x;
            int a;
            int b;
            do {
                x = (int) (Math.random() * this.sizeOfField * this.sizeOfField);
                a = x / this.sizeOfField;
                b = x % this.sizeOfField;
            } while (field[a][b].isMine() || (x == firstMoveCell));
            field[a][b].setMine();
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < this.sizeOfField; i++) {
            for (int j = 0; j < this.sizeOfField; j++) {
                if (field[i][j].isMine()) {
                    continue;
                }
                int count = 0;
                if (i > 0) {
                    if (field[i - 1][j].isMine()) {
                        count++;
                    }
                    if (j > 0) {
                        if (field[i - 1][j - 1].isMine()) {
                            count++;
                        }
                    }
                    if (j < this.sizeOfField - 1) {
                        if (field[i - 1][j + 1].isMine()) {
                            count++;
                        }
                    }
                }
                if (i < this.sizeOfField - 1) {
                    if (field[i + 1][j].isMine()) {
                        count++;
                    }
                    if (j > 0) {
                        if (field[i + 1][j - 1].isMine()) {
                            count++;
                        }
                    }
                    if (j < this.sizeOfField - 1) {
                        if (field[i + 1][j + 1].isMine()) {
                            count++;
                        }
                    }
                }
                if (j > 0) {
                    if (field[i][j - 1].isMine()) {
                        count++;
                    }
                }
                if (j < this.sizeOfField - 1) {
                    if (field[i][j + 1].isMine()) {
                        count++;
                    }
                }
                if (count > 0) {
                    field[i][j].setNumber(count);
                }
            }
        }
    }

    public boolean isGameOn() {
        return this.gameOn;
    }

    public void isGameFinished() {
        if (this.countRightFlags == countTotalFlags &&
                this.countVisibleCell == this.sizeOfField * this.sizeOfField - this.numberOfMines) {
            this.gameOn = false;
            System.out.println("Congratulations! You found all mines!");
            printField();
        }
    }

    public boolean isPositionSelected(int x, int y) {
        return field[x][y].isVisible() && !field[x][y].hasFlag();
    }

    public void setFlag(int x, int y) {
        if (field[x][y].hasFlag()) {
            field[x][y].setOrUnsetFlag(false);
            if (field[x][y].isMine()) {
                this.countRightFlags--;
            }
            this.countTotalFlags--;
        } else {
            field[x][y].setOrUnsetFlag(true);
            if (field[x][y].isMine()) {
                this.countRightFlags++;
            }
            this.countTotalFlags++;
        }
    }

    public void checkPos(int x, int y) {
        if (field[x][y].isMine()) {
            setMinesVisible();
            gameOver();
        } else {
            checkAround(x, y);
        }
    }

    private void setMinesVisible() {
        for (int i = 0; i < this.sizeOfField; i++) {
            for (int j = 0; j < this.sizeOfField; j++) {
                if (field[i][j].isMine()) {
                    field[i][j].setOrUnsetFlag(false);
                    field[i][j].setVisible();
                }
            }
        }
    }

    private void gameOver() {
        System.out.println("Failed. You stepped on a mine");
        printField();
        this.gameOn = false;
    }

    private void checkAround(int x, int y) {
        if (field[x][y].getNumberOfMinesAround() == '0' && !field[x][y].isVisible()) {
            if (field[x][y].hasFlag()) {
                setFlag(x, y);
            }
            field[x][y].setVisible();
            incrementVisible();
            if (y > 0) {
                checkAround(x, y - 1);
                if (x > 0) {
                    checkAround(x - 1, y - 1);
                }
                if (x < this.sizeOfField - 1) {
                    checkAround(x + 1, y - 1);
                }
            }
            if (y < this.sizeOfField - 1) {
                checkAround(x, y + 1);
                if (x > 0) {
                    checkAround(x - 1, y + 1);
                }
                if (x < this.sizeOfField - 1) {
                    checkAround(x + 1, y + 1);
                }
            }
            if (x > 0) {
                checkAround(x - 1, y);
            }
            if (x < this.sizeOfField - 1) {
                checkAround(x + 1, y);
            }
        } else if (!field[x][y].isMine() && !field[x][y].isVisible()) {
            if (field[x][y].hasFlag()) {
                setFlag(x, y);
            }
            field[x][y].setVisible();
            incrementVisible();
        }
    }

    private void incrementVisible() {
        this.countVisibleCell++;
    }

    public void printField() {
        System.out.print("\n |");
        for (int i = 1; i <= this.sizeOfField; i++) {
            System.out.print(i);
        }
        System.out.print("|\n-|");
        for (int i = 0; i < this.sizeOfField; i++) {
            System.out.print("-");
        }
        System.out.println("|");
        for (int i = 0; i < this.sizeOfField; i++) {
            System.out.printf("%d|", i + 1);
            for (int j = 0; j < this.sizeOfField; j++) {
                if (field[i][j].hasFlag()) {
                    System.out.print('*');
                } else if (field[i][j].isVisible()) {
                    if (field[i][j].isMine()) {
                        System.out.print('X');
                    } else if (field[i][j].getNumberOfMinesAround() == '0') {
                        System.out.print('/');
                    } else {
                        System.out.print(field[i][j].getNumberOfMinesAround());
                    }
                } else {
                    System.out.print('.');
                }
            }
            System.out.println("|");
        }
        System.out.print("-|");
        for (int i = 0; i < this.sizeOfField; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }
}
