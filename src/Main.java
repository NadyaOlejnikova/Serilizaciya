import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);
        int[] prices = {70, 30, 115};

        String[] products = {"Сахар", "Соль", "Помидоры"};
        Basket basket = new Basket(prices, products);

        ClientLog clientLog = new ClientLog();
        //File file = new File("basket.txt");
        File file = new File("basket.json");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        XPath xPath = XPathFactory.newInstance().newXPath();
        boolean loadEnable = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(doc));
        if (loadEnable && file.exists()) {
            XPath xPath1 = XPathFactory.newInstance().newXPath();
            String loadFileName = xPath1
                    .compile("/config/load/fileName")
                    .evaluate(doc);
            System.out.println("файл " + loadFileName);
            System.out.println("Восстановление корзины.....");
            //Basket.loadFromTxtFile(file);
            Basket.loadJson(file);
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
                XPath xPath2 = XPathFactory.newInstance().newXPath();
                boolean saveEnabled = Boolean.parseBoolean(xPath
                        .compile("/config/save/enabled")
                        .evaluate(doc));
                if (input != null && !input.equals("end")) {
                    XPath xPath1 = XPathFactory.newInstance().newXPath();
                    String saveFileName = xPath2
                            .compile("/config/save/fileName")
                            .evaluate(doc);
                    System.out.println("сохранено в " + saveFileName);
                    String[] parts = input.split(" ");
                    int productNumber = Integer.parseInt(parts[0]) - 1;
                    int productCount = Integer.parseInt(parts[1]);
                    basket.addToCart(productNumber, productCount);// вызываем метод добавления товара в корзину
                    clientLog.log(productNumber + 1, productCount);
                    JSONObject obj = new JSONObject();
                    obj.accumulate("Number", productNumber + 1);
                    obj.accumulate("Count", productCount);
                    System.out.println(obj);
                    try (FileWriter reader = new FileWriter("basket.json", true)) {
                        reader.write(obj.toString());
                    }
                }
                basket.printCart();
                // basket.saveTxt(file);
                XPath xPath3 = XPathFactory.newInstance().newXPath();
                boolean logEnabled = Boolean.parseBoolean(xPath
                        .compile("/config/log/enabled")
                        .evaluate(doc));
                String logFileName = xPath3
                        .compile("/config/log/fileName")
                        .evaluate(doc);
                System.out.println("сохранено в " + logFileName);
                clientLog.exportAsCSV("log.csv");

            }
        }
    }
}





