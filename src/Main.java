
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    //Также вместо вызова метода saveTxt в методе main сериализуйте корзину в
    // json-формате в файл basket.json. Аналогично при старте программы загружайте
    // корзину десериализацией из json-а из файла basket.json,
    // а не из обычной текстовой сериализации как было до того.
    // При этом логику сериализации в методах в классе корзины трогать не нужно.
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        int[] prices = {10, 20, 30};
        String[] products = {"Сахар", "Соль", "Помидоры"};
        Basket basket = new Basket(prices, products);

        ClientLog clientLog = new ClientLog();
        //File file = new File("basket.txt");
        File file = new File("basket.json");

        if (file.exists()) {
            System.out.println("Восстановление корзины.....");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("basket.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("num");
            for (Object num : jsonArray) {
                System.out.println(num);
            }
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
                int productNumber = Integer.parseInt(parts[0]) - 1;
                int productCount = Integer.parseInt(parts[1]);
                basket.addToCart(productNumber, productCount);// вызываем метод добавления товара в корзину
                clientLog.log(productNumber + 1, productCount);


                JSONObject object = new JSONObject();
                object.put("num", productCount);
                object.put("count", productNumber + 1);
                try (FileWriter file1 = new FileWriter("basket.json", true)) {
                    object.write(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            basket.printCart();
            // basket.saveTxt(file);

            clientLog.exportAsCSV("log.csv");

        }
    }
}





