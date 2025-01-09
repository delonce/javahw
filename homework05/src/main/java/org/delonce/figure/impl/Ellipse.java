package org.delonce.figure.impl;

public class Ellipse extends Circle {
    private final double D;

    public Ellipse(int X, int Y, double d, double D) {
        super(X, Y, d);
        this.D = D;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI *
                Math.sqrt(
                        (Math.pow(getRadius(), 2) + Math.pow(this.D, 2)) / 2
                );
    }

    @Override
    public String toString() {
        return super.toString() + "Ellipse{" +
                "D=" + D +
                '}';
    }
}
