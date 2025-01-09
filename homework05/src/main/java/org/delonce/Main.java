package org.delonce;

import org.delonce.figure.impl.Circle;
import org.delonce.figure.impl.Ellipse;
import org.delonce.figure.impl.Rectangle;
import org.delonce.figure.impl.Square;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputFileName = "shapes.txt"; // Имя файла в ресурсах
        String outputFileName = "output/output.txt"; // Имя выходного файла

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(inputFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFileName)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String shapeType = parts[0];

                switch (shapeType) {
                    case "Circle":
                        int circleX = Integer.parseInt(parts[1]);
                        int circleY = Integer.parseInt(parts[2]);
                        double circleRadius = Double.parseDouble(parts[3]);
                        Circle circle = new Circle(circleX, circleY, circleRadius);
                        writer.println(circle);
                        writer.println("Периметр: " + circle.getPerimeter());
                        break;
                    case "Rectangle":
                        int rectX = Integer.parseInt(parts[1]);
                        int rectY = Integer.parseInt(parts[2]);
                        double side1 = Double.parseDouble(parts[3]);
                        double side2 = Double.parseDouble(parts[4]);
                        List<Double> sides = Arrays.asList(side1, side2);

                        Rectangle rectangle = new Rectangle(rectX, rectY, sides);
                        writer.println(rectangle);
                        writer.println("Периметр: " + rectangle.getPerimeter());
                        break;
                    case "Square":
                        int sqX = Integer.parseInt(parts[1]);
                        int sqY = Integer.parseInt(parts[2]);
                        double sqSide1 = Double.parseDouble(parts[3]);
                        double sqSide2 = Double.parseDouble(parts[4]);
                        List<Double> sqSides = Arrays.asList(sqSide1, sqSide2);

                        Square square = new Square(sqX, sqY, sqSides);
                        writer.println(square);
                        square.moveFigure(-1, -6);
                        writer.println("Периметр: " + square.getPerimeter());
                        writer.println(square);
                        break;
                    case "Ellipse":
                        int elX = Integer.parseInt(parts[1]);
                        int elY = Integer.parseInt(parts[2]);
                        double d = Double.parseDouble(parts[3]);
                        double D = Double.parseDouble(parts[4]);
                        Ellipse ellipse = new Ellipse(elX, elY, d, D);

                        writer.println(ellipse);
                        ellipse.moveFigure(100, 500);
                        writer.println("Периметр: " + ellipse.getPerimeter());
                        writer.println(ellipse);
                        break;
                    default:
                        writer.println("Неизвестный тип фигуры: " + shapeType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
