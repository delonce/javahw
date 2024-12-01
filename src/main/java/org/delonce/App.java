package org.delonce;


public class App {
    public static void main(String[] args) {
        Product prod1 = new Product();
        Product prod2 = new Product(1, "Яблоко", 0.50, 5);
        Product prod3 = new Product(2, "Картон", 2.67, 20);

        System.out.println(prod1);
        System.out.println(prod2);
        System.out.println(prod3);

        prod1.setId(50);
        prod2.setAmount(500);
        prod3.setPrice(40.24);

        System.out.println(prod1);
        System.out.println(prod2);
        System.out.println(prod3);

        System.out.println(prod1.getId());
        System.out.println(prod2.getPrice());
        System.out.println(prod3.getDescription());
    }
}
