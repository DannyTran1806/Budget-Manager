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
                    double itemAmount = addPurchase(purchases);
                    total += itemAmount;
                    if (balance <= 0){
                        balance = 0;
                    }else {
                        balance -= itemAmount;
                    }
                    System.out.println("Purchase was added!");
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    if(purchases.isEmpty()){
                        System.out.println("The purchase list is empty");
                    }else {
                        for (String purchase : purchases) {
                            System.out.println(purchase);
                        }
                        System.out.printf(Locale.US, "Total sum: $%.2f%n", total);
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    System.out.printf(Locale.US,"Balance: $%.2f%n", balance);
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    exit = true;
                    System.out.println("Bye!");
                    System.out.println();
                    break;
            }
        } while(!exit);
    }

    public static double addIncome(){
        System.out.println("Enter income:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();

    }

    public static double addPurchase(List<String> purchases){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        String purchaseName = scanner.nextLine();
        System.out.println("Enter its price:");
        String price = scanner.nextLine();
        purchases.add(purchaseName + " " + "$" + price);
        return Double.parseDouble(price);
    }

}
