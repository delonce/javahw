package org.delonce.Figure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractFigure {
    private int X;
    private int Y;

    public abstract double getPerimeter();

    @Override
    public String toString() {
        return "AbstractFigure{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
