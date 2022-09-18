import java.io.*;
public class Basket {
    protected static  int[] prices = {10,20,30}; //цены
    protected static  String[] products = {"Сахар", "Соль", "Помидоры"}; //товары
    protected int[] quantityOfGoods; //количество
    protected int sumTotal = 0;
    protected int productPieces = 0;
    protected int sumAll;
    protected int sumProducts = 0;

    protected Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        quantityOfGoods = new int[products.length];
    }

    //метод вывода на экран покупательской корзины
    protected void printCart() {

            System.out.println("Ваша корзина:");
            for (int i = 0; i < quantityOfGoods.length; i++) {
                if (quantityOfGoods[i] != 0) {
                    quantityOfGoods[i] += sumProducts;
                    productPieces = quantityOfGoods[i];
                    sumAll = productPieces * prices[i];
                    sumTotal += sumAll;
                    System.out.println(products[i] + " " + productPieces + " шт " + prices[i] + " руб/шт " + sumAll + "  руб в сумме.");
                }

            }
        System.out.println("Итого: " + sumTotal + " руб.");
    }

    //метод добавления amount штук продукта номер productNum в корзину;
    protected void addToCart(int productNum, int quantity) {
        quantityOfGoods[productNum] += quantity;
    }
    protected static Basket loadFromTxtFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String[] readCount = br.readLine().split(" ");
            int[] quantities = new int[readCount.length];
            for (int i = 0; i < readCount.length; i++) {
                quantities[i] = Integer.parseInt(readCount[i]);
            }

            String[] readPrice = br.readLine().split(" ");
            int[] prices = new int[readPrice.length];
            for (int i = 0; i < readPrice.length; i++) {
                prices[i] = Integer.parseInt(readPrice[i]);
            }

            String[] products = br.readLine().split(" ");
            Basket basket = new Basket(prices, products);
            for (int i = 0; i < quantities.length; i++) {
                basket.addToCart(i, quantities[i]);
            }
            basket.printCart();
            basket.saveTxt(file);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //запись корзины в файл basket.txt:
    protected void saveTxt(File file) throws IOException {
        try (PrintWriter saveTxt = new PrintWriter(file)) {
            for (int quantity : quantityOfGoods) {
                saveTxt.print(quantity + " ");
            }
            saveTxt.print("\n");
            for (int price : prices) {
                saveTxt.print(price + " ");
            }
            saveTxt.print("\n");
            for (String product : products) {
                saveTxt.print(product + " ");
            }
        }
        System.out.println("Данные сохранены в файл basket.txt");
    }

}