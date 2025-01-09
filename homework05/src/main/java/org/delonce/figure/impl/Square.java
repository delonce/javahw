package org.delonce.figure.impl;

import org.delonce.movable.Movable;

import java.util.List;

public class Square extends Rectangle implements Movable {

    public Square(int X, int Y, List<Double> sidesSize) {
        super(X, Y, sidesSize);
    }

    @Override
    public void moveFigure(int X, int Y) {
        setX(X);
        setY(Y);
    }
}
