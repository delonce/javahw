package org.delonce;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        //cycleGrayCode(3).forEach(System.out::println);

        String inputFileName = "texts.txt";

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(inputFileName)) {
            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                Arrays.stream(line.split("[^a-zA-ZА-Яа-я0-9]+")).map(String::toLowerCase)
                        .collect(
                                Collectors.groupingBy(Function.identity(), Collectors.counting())
                        )
                        .entrySet()
                        .stream()
                        .collect(Collectors.groupingBy(
                                Map.Entry::getValue,
                                Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                        ))
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<Long, List<String>>comparingByKey().reversed())
                        .map(Map.Entry::getValue)
                        .toList()
                        .stream()
                        .flatMap(strings -> strings.stream().sorted())
                        .limit(10)
                        .forEach(System.out::println);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stream<Integer> cycleGrayCode(int n) {
        return Stream.iterate(0, x -> (x + 1) % (int) Math.pow(2, n))
                .map(x -> x ^ (x >> 1));
    }

}