package minesweeper;

public class Field {
    private final char[][] field;
    private final Cell[][] visible;
    private final Flag[][] flags;
    private final int size;
    private int countTotalFlags = 0;
    private int countRightFlags = 0;
    private boolean gameOn = false;
    private int countVisible = 0;
    private int mines = 0;

    public boolean isGameOn() {
        return gameOn;
    }

    private void incrementVisible() {
        this.countVisible++;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public Field(int size) {
        this.size = size;
        this.field = new char[this.size][this.size];
        this.visible = new Cell[this.size][this.size];
        this.flags = new Flag[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                field[i][j] = '/';
                visible[i][j] = Cell.HIDDEN;
                flags[i][j] = Flag.NOFLAG;
            }
        }
    }

    public void setMines(int mines, int free) {
        this.mines = mines;
        setRandomMines(free);
        calculateNumbers();
        checkPos(free / this.size, free % this.size);
    }

    private void setRandomMines(int free) {
        for (int i = 0; i < this.mines; i++) {
            int x;
            do {
                x = (int) (Math.random() * this.size * this.size);
            } while (field[x / this.size][x % this.size] != '/' || (x == free));
            field[x / this.size][x % this.size] = 'X';
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (field[i][j] == 'X') {
                    continue;
                }
                int count = 0;
                if (i > 0) {
                    if (field[i - 1][j] == 'X') {
                        count++;
                    }
                    if (j > 0) {
                        if (field[i - 1][j - 1] == 'X') {
                            count++;
                        }
                    }
                    if (j < this.size - 1) {
                        if (field[i - 1][j + 1] == 'X') {
                            count++;
                        }
                    }
                }
                if (i < this.size - 1) {
                    if (field[i + 1][j] == 'X') {
                        count++;
                    }
                    if (j > 0) {
                        if (field[i + 1][j - 1] == 'X') {
                            count++;
                        }
                    }
                    if (j < this.size - 1) {
                        if (field[i + 1][j + 1] == 'X') {
                            count++;
                        }
                    }
                }
                if (j > 0) {
                    if (field[i][j - 1] == 'X') {
                        count++;
                    }
                }
                if (j < this.size - 1) {
                    if (field[i][j + 1] == 'X') {
                        count++;
                    }
                }
                if (count > 0) {
                    field[i][j] = (char) (count + 48);
                }
            }
        }
    }

    public void PrintField() {
        System.out.print("\n |");
        for (int i = 1; i <= this.size; i++) {
            System.out.print(i);
        }
        System.out.print("|\n-|");
        for (int i = 0; i < this.size; i++) {
            System.out.print("-");
        }
        System.out.println("|");
        for (int i = 0; i < this.size; i++) {
            System.out.printf("%d|", i + 1);
            for (int j = 0; j < this.size; j++) {
                if (flags[i][j] == Flag.FLAG) {
                    System.out.print('*');
                } else if (visible[i][j] == Cell.VISIBLE) {
                    System.out.print(field[i][j]);
                } else {
                    System.out.print('.');
                }
            }
            System.out.println("|");
        }
        System.out.print("-|");
        for (int i = 0; i < this.size; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    public boolean isPosIsFree(int x, int y) {
        return visible[x][y] == Cell.HIDDEN && flags[x][y] == Flag.NOFLAG;
    }

    public void setFlag(int x, int y) {
        if (flags[x][y] == Flag.FLAG) {
            flags[x][y] = Flag.NOFLAG;
            if (field[x][y] == 'X') {
                countRightFlags--;
            }
            countTotalFlags--;
        } else {
            flags[x][y] = Flag.FLAG;
            if (field[x][y] == 'X') {
                countRightFlags++;
            }
            countTotalFlags++;
        }
    }

    public boolean checkGame() {
        return (countRightFlags == this.mines && countTotalFlags == this.mines && this.countVisible == this.size * this.size - this.mines);
    }

    private void gameOver() {
        System.out.println("Failed. You stepped on a mine");
        PrintField();
        setGameOn(false);
    }

    public void checkPos(int x, int y) {
        if (field[x][y] == 'X') {
            setMinesVisible();
            gameOver();
        } else {
            checkAround(x, y);
        }
    }

    private void checkAround(int x, int y) {
        if (field[x][y] == '/' && visible[x][y] == Cell.HIDDEN) {
            if (flags[x][y] == Flag.FLAG) {
                setFlag(x, y);
            }
            visible[x][y] = Cell.VISIBLE;
            incrementVisible();
            if (y > 0) {
                checkAround(x, y - 1);
                if (x > 0) {
                    checkAround(x - 1, y - 1);
                }
                if (x < this.size - 1) {
                    checkAround(x + 1, y - 1);
                }
            }
            if (y < this.size - 1) {
                checkAround(x, y + 1);
                if (x > 0) {
                    checkAround(x - 1, y + 1);
                }
                if (x < this.size - 1) {
                    checkAround(x + 1, y + 1);
                }
            }
            if (x > 0) {
                checkAround(x - 1, y);
            }
            if (x < this.size - 1) {
                checkAround(x + 1, y);
            }
        } else if (field[x][y] != 'X' && visible[x][y] == Cell.HIDDEN) {
            if (flags[x][y] == Flag.FLAG) {
                setFlag(x, y);
            }
            visible[x][y] = Cell.VISIBLE;
            incrementVisible();
        }
    }

    private void setMinesVisible() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == 'X') {
                    flags[i][j] = Flag.NOFLAG;
                    visible[i][j] = Cell.VISIBLE;
                }
            }
        }
    }
}
