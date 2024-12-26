package org.delonce;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Integer[] array = {5, 3, 8, 1, 2, 7, 4, 6, 5, 2};

        List<Integer> list = new ArrayList<>(Arrays.asList(array));
        System.out.println("Список: " + list);

        Collections.sort(list);
        System.out.println("Отсортированный список (натуральный порядок): " + list);

        Collections.sort(list, Collections.reverseOrder());
        System.out.println("Отсортированный список (обратный порядок): " + list);

        Collections.shuffle(list);
        System.out.println("Перемешанный список: " + list);

        if (!list.isEmpty()) {
            Integer firstElement = list.remove(0);
            list.add(firstElement);
        }
        System.out.println("Список после циклического сдвига на 1 элемент: " + list);

        Set<Integer> uniqueSet = new HashSet<>(list);
        list.clear();
        list.addAll(uniqueSet);
        System.out.println("Список с уникальными элементами: " + list);

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer num : array) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        list.clear();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > 1) {
                list.add(entry.getKey());
            }
        }
        System.out.println("Список с дублирующимися элементами: " + list);

        Integer[] resultArray = list.toArray(new Integer[0]);
        System.out.println("Полученный массив: " + Arrays.toString(resultArray));

        Set<String> stringSet = new HashSet<>();
        stringSet.add("Apple");
        stringSet.add("Banana");
        stringSet.add("Cherry");
        stringSet.add("Date");
        stringSet.add("Elderberry");
        stringSet.add("Fig");
        stringSet.add("Grape");
        stringSet.add("Honeydew");
        stringSet.add("Kiwi");
        stringSet.add("Lemon");

        stringSet.add("Mango");
        stringSet.add("Nectarine");
        stringSet.add("Orange");
        stringSet.add("Papaya");
        stringSet.add("Quince");

        System.out.println("Элементы множества:");
        for (String fruit : stringSet) {
            System.out.println(fruit);
        }

        boolean added = stringSet.add("Apple");
        System.out.println("Добавлен элемент 'Apple': " + added); // Ожидается false

        boolean containsBanana = stringSet.contains("Banana");
        System.out.println("Содержит ли коллекция 'Banana': " + containsBanana);

        stringSet.remove("Cherry");
        System.out.println("После удаления 'Cherry': " + stringSet);

        int size = stringSet.size();
        System.out.println("Количество элементов в коллекции: " + size);

        stringSet.clear();
        System.out.println("После удаления всех элементов: " + stringSet);

        boolean isEmpty = stringSet.isEmpty();
        System.out.println("Является ли коллекция пустой: " + isEmpty);
    }
}
