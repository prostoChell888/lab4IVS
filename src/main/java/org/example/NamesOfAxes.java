package org.example;

public class NamesOfAxes {

    public NamesOfAxes(String nameOfX, String nameOfY) {
        this.nameOfX = nameOfX;
        this.nameOfY = nameOfY;
    }

    private String nameOfX;
    private String nameOfY;

    public void setNameOfX(String nameOfX) {
        this.nameOfX = nameOfX;
    }

    public void setNameOfY(String nameOfY) {
        this.nameOfY = nameOfY;
    }

    public String getNameOfX() {
        return nameOfX;
    }

    public String getNameOfY() {
        return nameOfY;
    }
}
