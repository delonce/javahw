package org.delonce;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Apple", "Fruit");
        hashMap.put("Carrot", "Vegetable");
        hashMap.put("Banana", "Fruit");
        hashMap.put("Broccoli", "Vegetable");
        hashMap.put("Grape", "Fruit");
        hashMap.put("Potato", "Vegetable");
        hashMap.put("Orange", "Fruit");
        hashMap.put("Spinach", "Vegetable");
        hashMap.put("Peach", "Fruit");
        hashMap.put("Cucumber", "Vegetable");

        hashMap.put("Strawberry", "Fruit");
        hashMap.put("Tomato", "Fruit"); // Ключи с одинаковыми значениями
        hashMap.put("Lettuce", "Vegetable");
        hashMap.put("Pumpkin", "Vegetable");
        hashMap.put("Zucchini", "Vegetable");

        System.out.println("Содержимое HashMap:");
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        String previousValue = hashMap.put("Apple", "Green Fruit");
        System.out.println("Добавлен элемент с существующим ключом 'Apple'. Предыдущее значение: " + previousValue);

        List<String> keysList = new ArrayList<>(hashMap.keySet());
        System.out.println("Список всех ключей: " + keysList);

        Set<String> valuesSet = new HashSet<>(hashMap.values());
        System.out.println("Список всех уникальных значений: " + valuesSet);
        System.out.println("Количество уникальных значений: " + valuesSet.size());

        boolean containsKey = hashMap.containsKey("Banana");
        System.out.println("Содержит ли коллекция ключ 'Banana': " + containsKey);

        boolean containsValue = hashMap.containsValue("Vegetable");
        System.out.println("Содержит ли коллекция значение 'Vegetable': " + containsValue);

        int size = hashMap.size();
        System.out.println("Количество элементов в коллекции: " + size);

        String removedValue = hashMap.remove("Carrot");
        System.out.println("Удален элемент с ключом 'Carrot' и значением: " + removedValue);
        System.out.println("Содержимое HashMap после удаления: " + hashMap);
    }
}
