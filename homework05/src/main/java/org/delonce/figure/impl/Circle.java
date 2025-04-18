package org.delonce.figure.impl;

import org.delonce.figure.AbstractFigure;
import lombok.Getter;
import lombok.Setter;
import org.delonce.movable.Movable;

@Getter
@Setter
public class Circle extends AbstractFigure implements Movable {
    private double radius;

    public Circle(int X, int Y, double radius) {
        super(X, Y);
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * this.radius * Math.PI;
    }

    @Override
    public void moveFigure(int X, int Y) {
        setX(X);
        setY(Y);
    }

    @Override
    public String toString() {
        return super.toString() + "Circle{" +
                "radius=" + radius +
                '}';
    }
}
