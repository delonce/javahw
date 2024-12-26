package org.delonce.Figure.impl;

import org.delonce.Figure.AbstractFigure;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class Rectangle extends AbstractFigure {

    private List<Double> sidesSize;

    public Rectangle(int X, int Y, List<Double> sidesSize) {
        super(X, Y);
        this.sidesSize = sidesSize;
    }

    @Override
    public double getPerimeter() {
        double perimeter = 0;
        for (Double side : this.sidesSize) {
            perimeter += side;
        }
        return perimeter * 2;
    }

    @Override
    public String toString() {
        return super.toString() + "Rectangle{" +
                "sidesSize=" + sidesSize +
                '}';
    }
}
