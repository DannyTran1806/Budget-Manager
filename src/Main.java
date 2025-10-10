import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner choice = new Scanner(System.in);
        double balance = 0;
        double total = 0;
        boolean exit = false;
        List<String> purchases = new ArrayList<>();
        do {
            System.out.println("Choose your action:");
            System.out.println("1) Add Income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("0) Exit");

            switch(choice.nextInt()) {
                case 1:
                    System.out.println();
                    balance += addIncome();
                    System.out.println("Income was added!");
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    total += addPurchase(purchases, total);
                    System.out.println("Purchase was added!");
                    break;
                case 3:
                    System.out.println();
                    for (String purchase : purchases) {
                        System.out.println(purchase);
                    }
                    System.out.printf(Locale.US, "Total sum: $%.2f%n", total);
                    break;
                case 4:
                    System.out.println();
                    System.out.printf(Locale.US,"Balance: $%.2f%n", balance);
                    System.out.println();
                    break;
                case 0:
                    exit = true;
                    break;
            }
        } while(!exit);
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

    public static double addPurchase(List<String> purchases, double total){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        String purchaseName = scanner.nextLine();
        System.out.println("Enter its price:");
        String price = scanner.nextLine();
        purchases.add(purchaseName + " " + "$" + price);
        double itemAmount = Double.parseDouble(price);
        total += itemAmount;
        return total;
    }

}
