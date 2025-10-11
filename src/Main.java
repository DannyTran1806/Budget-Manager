import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double balance = 0.0;
        double total = 0.0;

        List<String> foodList = new ArrayList<>();
        List<String>  clothesList= new ArrayList<>();
        List<String>  entertainmentList= new ArrayList<>();
        List<String>  othersList= new ArrayList<>();

        boolean exit = false;

        boolean back = false;
        List<String> purchases = new ArrayList<>();

        while (!exit) {
            printMainMenu();
            int choice = readIntInRange(in);

            switch (choice) {
                case 1 -> {
                    // Add Income
                    System.out.println();
                    balance += addIncome(in);
                    System.out.println("Income was added!");
                    System.out.println();
                }
                case 2 -> {
                    // Add purchase
                    System.out.println();
                    int category = displayCategoryMenu(in);
                    if (category == 5) { //Back
                        System.out.println();
                        break;
                    }
                    List<String> currentList = switch (category) {
                        case 1 -> foodList;
                        case 2 -> clothesList;
                        case 3 -> entertainmentList;
                        case 4 -> othersList;
                        default -> null;
                    };

                    if (currentList == null) {
                        System.out.println("Invalid category.");
                        System.out.println();
                        break;
                    }

                    double itemAmount = addPurchase(in, currentList);
                    total += itemAmount;
                    // prevent negative balance silently going more negative
                    if (balance <= 0) {
                        balance = 0;
                    } else {
                        balance -= itemAmount;
                        if (balance < 0) balance = 0;
                    }
                    System.out.println("Purchase was added!");
                    System.out.println();


                }
                case 3 -> { // Show lists
                    System.out.println();
                    int listChoice = listingOptionsPurchases(in);
                    switch (listChoice) {
                        case 1 -> {
                            System.out.println("Food:");
                            printListWithCategoryTotal(foodList);
                        }
                        case 2 -> {
                            System.out.println("Clothes:");
                            printListWithCategoryTotal(clothesList);
                        }
                        case 3 -> {
                            System.out.println("Entertainment:");
                            printListWithCategoryTotal(entertainmentList);
                        }
                        case 4 -> {
                            System.out.println("Other:");
                            printListWithCategoryTotal(othersList);
                        }
                        case 5 -> {
                            // All
                            System.out.println("All:");
                            printAllPurchases(foodList, clothesList, entertainmentList, othersList);
                        }
                        case 6 -> {}
                        default -> System.out.println("Invalid choice.");
                    }

                }
                case 4 -> {
                    System.out.println();
                    System.out.printf(Locale.US,"Balance: $%.2f%n", balance);
                    System.out.println();
                }
                case 0 -> {
                    System.out.println();
                    exit = true;
                    System.out.println("Bye!");
                    System.out.println();
                }
            }
        }
    }


    //  --------- Input helpers (keep it simple for now) ---------
    private static int readIntInRange(Scanner in) {
        return in.nextInt();
    }

    private static double readMoney(Scanner in, String prompt){
        System.out.println(prompt);
        String s = in.nextLine().trim();
        return Double.parseDouble(s);
    }
    // --------- Menu printers ---------
    private static void printMainMenu(){
        System.out.println("Choose your action:");
        System.out.println("1) Add Income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
    }

    private static  int displayCategoryMenu(Scanner in){
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
        return readIntInRange(in);
    }

    private static int listingOptionsPurchases(Scanner in){
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
        return readIntInRange(in);
    }
    // --------- Actions ---------
    private static double addIncome(Scanner in){
        System.out.println("Enter income:");
        return in.nextDouble();
    }

    private static double addPurchase(Scanner in, List<String> purchases){
        if(in.hasNextLine()){
            in.nextLine();
        }
        System.out.println("Enter purchase name:");
        String purchaseName = in.nextLine().trim();
        double price = readMoney(in, "Enter price: ");
        purchases.add(purchaseName + " " + "$" + String.format(Locale.US, "%.2f", price));
        return price;
    }

    private static double printListWithCategoryTotal(List<String> purchases){
        if(purchases.isEmpty()){
            System.out.println("The purchase list is empty!");
            return 0.00;
        }
        double sum = 0.0;
        for(String purchase : purchases){
            System.out.println(purchase);
            String[] parts = purchase.trim().split(" ");
            String last = parts[parts.length - 1].replace("$","");
            sum += Double.parseDouble(last);
        }
        System.out.printf(Locale.US, "Total sum: $%.2f%n", sum);
        return sum;
    }

    private static void printAllPurchases(List<String>...lists){
        double grandTotal = 0.0;
        int count = 0;

        for (List<String> list : lists){
            for(String purchase : list){
                System.out.println(purchase);
                grandTotal += extractPrice(purchase);
                count++;
            }
        }

        if(count == 0){
            System.out.println("The purchase list is empty!");
        }else {
            System.out.printf(Locale.US, "Total sum: $%.2f%n", grandTotal);
        }
    }

    private static double extractPrice(String purchase){
        String[] parts = purchase.trim().split(" ");
        String last = parts[parts.length - 1].replace("$","");
        return Double.parseDouble(last);
    }
}
