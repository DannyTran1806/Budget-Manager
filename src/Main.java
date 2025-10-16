import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        double balance = 0.0;
        double total = 0.0;

        List<String> foodList = new ArrayList<>();
        List<String>  clothesList= new ArrayList<>();
        List<String>  entertainmentList= new ArrayList<>();
        List<String>  othersList= new ArrayList<>();
        List<Item>  itemsToBeSorted = new ArrayList<>();

        boolean exit = false;
        createFile("purchases.txt");
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
                    System.out.println();
                    List<String> currentList = switch (category) {
                        case 1 -> foodList;
                        case 2 -> clothesList;
                        case 3 -> entertainmentList;
                        case 4 -> othersList;
                        default -> null;
                    };

                    if (currentList == null) {
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
                            System.out.println();
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
                    System.out.println();
                }
                case 4 -> {
                    System.out.println();
                    System.out.printf(Locale.US,"Balance: $%.2f%n", balance);
                    System.out.println();
                }
                case 5 -> { // Save to file
                    FileWriter writer = new FileWriter("/Users/dannytran/Documents/IntelliJ/BudgetManager/purchases.txt",false);
                    writer.write("Balance:\n");
                    writer.write(String.valueOf(balance)+ "\n");
                    for(String foodItem : foodList){
                        writer.write("Food:\n");
                        writer.write(foodItem+"\n");
                    }
                    for(String clothesItem : clothesList){
                        writer.write("Clothes:\n");
                        writer.write(clothesItem+"\n");
                    }
                    for(String entertainmentItem : entertainmentList){
                        writer.write("Entertainment:\n");
                        writer.write(entertainmentItem+"\n");
                    }
                    for(String othersItem : othersList){
                        writer.write("Other:\n");
                        writer.write(othersItem+"\n");
                    }
                    writer.close();
                    System.out.println("Purchases were saved!");
                }
                case 6 -> {
                    // Load the file
                    File file = new File("/Users/dannytran/Documents/IntelliJ/BudgetManager/purchases.txt");
                    try (Scanner scanner = new Scanner(file)){
                        while (scanner.hasNextLine()) {
                            String category = scanner.nextLine().trim();
                            switch (category) {
                                case "Balance:" -> balance = Double.parseDouble(scanner.nextLine());
                                case "Food:" -> foodList.add(scanner.nextLine());
                                case "Clothes:" -> clothesList.add(scanner.nextLine());
                                case "Entertainment:" -> entertainmentList.add(scanner.nextLine());
                                case "Other:" -> othersList.add(scanner.nextLine());
                                default ->  System.out.println("Invalid choice.");
                            }
                        }
                    }catch (FileNotFoundException e) {
                        System.out.println("No such file exists!");
                    }
                }
                case 7 -> {
                    switch (sortingOptions(in)){
                        case 1 -> { //Sort ALL purchases
                            printAllPurchasesSorted(foodList, clothesList, entertainmentList, othersList);
                        }
                        case 2 -> { // Sort by Type (total sum)
                            printByType2(Map.of(
                                    "Food", foodList,
                                    "Clothes", clothesList,
                                    "Entertainment", entertainmentList,
                                    "Other", othersList
                            ));

                        }
                        case 3 -> { //Sort by certain type
                            switch(displayCategoryMenu(in)){
                                case 1 -> {
                                    System.out.println("Food:");
                                    sortAndPrint(foodList);
                                }
                                case 2 -> {
                                    System.out.println("Clothes:");
                                    sortAndPrint(clothesList);
                                }
                                case 3 -> {
                                    System.out.println("Entertainment:");
                                    sortAndPrint(entertainmentList);
                                }
                                case 4 -> {
                                    System.out.println("Other:");
                                    sortAndPrint(othersList);
                                }
                            }

                        }
                        case 4 -> {
                            System.out.println();
                            break;
                        }
                        default -> System.out.println("Invalid choice.");
                    }
                }
                case 0 -> {
                    System.out.println();
                    System.out.println("Bye!");
                    exit = true;
                }
            }
        }
    }
    //  --------- Input helpers (keep it simple for now) ---------
    private static int readIntInRange(Scanner in) {
        while(true){
            if (in.hasNextInt()) {
                int value = in.nextInt();
                in.nextLine(); // consume trailing newline
                return value;
            }
            else {
                in.nextLine();
            }
        }
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
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
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

    private static int sortingOptions(Scanner in){
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
        return readIntInRange(in);
    }
    // --------- Actions ---------
    private static double addIncome(Scanner in){
        while(true){
            System.out.println("Enter income:");
            if (in.hasNextDouble()){
                double value = in.nextDouble();
                in.nextLine();
                return value;
            }else {
                System.out.println("Please enter a valid number.");
                in.nextLine();
            }
        }
    }

    private static double addPurchase(Scanner in, List<String> purchases){
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

    private static void createFile(String pathName){
        File file = new File(pathName);
        try {
            if(file.createNewFile()){
                System.out.println("File created: " + file.getName() + "\n");
            }else {
                System.out.println("File already exists.\n");
            }
        }catch (IOException e) {
            System.out.println("Cannot create the file: " + file.getPath());
        }
    }

    private static List<Item> transferStringToItemList (List<String> purchaseList){
        ArrayList<Item> itemList = new ArrayList<>();
        for (String purchaseItem : purchaseList){
            String[] parts = purchaseItem.trim().split(" ");
            String last = parts[parts.length - 1].replace("$","");
            itemList.add(new Item(parts[0],Double.parseDouble(last)));
        }
        return itemList;
    }

    private static void sortByPricesDesc(List<Item> items){
        int n = items.size();
        int i, j;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                double pj = items.get(j).getItemPrice();
                double pj1 = items.get(j + 1).getItemPrice();
                if (pj < pj1) {
                    // Swap arr[j] and arr[j+1]
                    Item temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                    swapped = true;
                }
            }
            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }
    private static void sortList(List<String> purchaseList) {
        List<Item> items = transferStringToItemList(purchaseList);
        sortByPricesDesc(items);
        purchaseList.clear();
        for (Item it : items) {
            // Einheitliches Format; Trennzeichen bewusst als Punkt:
            purchaseList.add(it.getItemName() + " $" + String.format(Locale.US, "%.2f", it.getItemPrice()));
        }
    }

    private static void sortAndPrint(List<String> purchaseList){
        List<String> copyFoodList = new ArrayList<>(purchaseList);
        sortList(copyFoodList);
        printListWithCategoryTotal(copyFoodList);
        System.out.println();
    }

    private static double sortByCertainType(List<String> purchases, String categoryName){
        if(purchases.isEmpty()){
            System.out.println(categoryName + " - $0");
            return 0.00;
        }
        double sum = 0.0;
        for(String purchase : purchases){
            String[] parts = purchase.trim().split(" ");
            String last = parts[parts.length - 1].replace("$","");
            sum += Double.parseDouble(last);
        }
        System.out.printf(Locale.US, "%s - $%.2f%n", categoryName, sum);
        return sum;
    }

    private static void printAllPurchasesSorted(List<String>...lists){
        double grandTotal = 0.0;
        int count = 0;
        List<Item> items = new ArrayList<>();
        List<String> listOfAll = new ArrayList<>();

        for (List<String> list : lists){
            for (String listItem : list){
                String[] parts = listItem.trim().split(" ");
                String last = parts[parts.length - 1].replace("$","");
                items.add(new Item(parts[0],Double.parseDouble(last)));
                grandTotal += extractPrice(listItem);
                count++;
            }
        }
        sortByPricesDesc(items);
        for (Item it : items) {
            listOfAll.add(it.getItemName() + " $" + String.format(Locale.US, "%.2f", it.getItemPrice()));
        }
        for (String item : listOfAll) {
            System.out.println(item);
        }
        if(count == 0){
            System.out.println("The purchase list is empty!");
        }else {
            System.out.printf(Locale.US, "Total: $%.2f%n", grandTotal);
        }
    }


    private static void printByType1 (List<String>...lists){
        double grandTotal = 0.0;
        int count = 0;
        double sumOfCategory = 0.0;
        double temp = 0.0;
        List<Item> items = new ArrayList<>();
        for (List<String> list : lists){
            for (String listItem : list){
                String[] parts = listItem.trim().split(" ");
                String last = parts[parts.length - 1].replace("$","");
                temp += Double.parseDouble(last);
                grandTotal += extractPrice(listItem);
                count++;
            }
            items.add(new Item("Test", temp));
            temp = 0.0;
        }
        sortByPricesDesc(items);
        for (Item it : items){
            System.out.println(it.getItemName() + " - $" +  String.format(Locale.US, "%.2f", it.getItemPrice()));
        }
        System.out.println("Total sum: $" + String.format("%.2f", grandTotal));
    }
    private static void printByType2(Map<String, List<String>> categories) {
        double grandTotal = 0.0;
        List<Item> items = new ArrayList<>();

        for (Map.Entry<String, List<String>> e : categories.entrySet()) {
            double sum = sumPrices(e.getValue());         // s. Hilfsfunktion unten
            items.add(new Item(e.getKey(), sum));         // <-- richtiger Name!
            grandTotal += sum;
        }

        sortByPricesDesc(items);

        for (Item it : items) {
            System.out.println(it.getItemName() + " - $" + String.format(Locale.US, "%.2f", it.getItemPrice()));
        }
        System.out.println("Total sum: $" + String.format(Locale.US, "%.2f", grandTotal));
    }

    private static double sumPrices(List<String> list) {
        double sum = 0.0;
        for (String s : list) {
            sum += extractPrice(s); // oder parseLastTokenPrice(s)
        }
        return sum;
    }

}
