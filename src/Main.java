import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Choose your action:");

    }

    public static double addIncome(){
        System.out.println("Enter income:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }

    public static void countPurchases(){
        Scanner scanner = new Scanner(System.in);
        double total = 0;
        List<String> purchases = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            purchases.add(input);
            String[] entries = input.split(" ");
            String pricePart = entries[entries.length - 1];
            double price = Double.parseDouble(pricePart.replace("$", ""));
            total += price;
        }

        for(String purchase : purchases) {
            System.out.println(purchase);
        }
        System.out.println();
        System.out.printf(Locale.US,"Total: $%.2f%n" , total);

        scanner.close();
    }
}
