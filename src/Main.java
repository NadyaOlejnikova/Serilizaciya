import java.io.*;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int[] prices = {10,20,30};
       String[] products = {"Сахар", "Соль", "Помидоры"};
        Basket basket = new Basket(prices, products);
        File file = new File("basket.bin");
        if (file.exists()) {
            System.out.println("Восстановление корзины.....");
            Basket.loadFromTxtFile(file);
        } else {
            System.out.println("Файл с данными не найден, заполните корзину.");
            System.out.println("Список товаров:  ");
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + "  " + products[i] + "  " + prices[i] + " руб.");
            }
            while (true) {
                System.out.println("Введите номер товара и его количество или для выхода наберите end.");
                String input = scanner.nextLine();
                if ("end".equals(input)) {
                    break;
                }
                String[] parts = input.split(" ");
                int productNumber  = Integer.parseInt(parts[0]) - 1;
                int productCount  = Integer.parseInt(parts[1]);
                basket.addToCart(productNumber, productCount );// вызываем метод добавления товара в корзину

            }
            basket.printCart();
            basket.saveTxt(file);
            Basket.saveBin();
            Basket.loadFromBinFile();
        }
    }

}





