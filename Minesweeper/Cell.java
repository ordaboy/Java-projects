package minesweeper;

public class Cell {
    private int numberOfMinesAround;
    private boolean isMine;
    private boolean isVisible;
    private boolean flag;

    Cell() {
        this.numberOfMinesAround = 0;
        this.isMine = false;
        this.isVisible =false;
        this.flag = false;
    }

    public boolean hasFlag() {
        return this.flag;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public char getNumberOfMinesAround() {
        char t = (char) (this.numberOfMinesAround + 48);
        return t;
    }

    public boolean isMine() {
        return this.isMine;
    }

    public void setMine() {
        this.numberOfMinesAround = 9;
        this.isMine = true;
    }

    public void setNumber(int count) {
        this.numberOfMinesAround = count;
    }

    public void setOrUnsetFlag(boolean flag) {
        this.flag = flag;
    }

    public void setVisible() {
        this.isVisible = true;
    }
}
