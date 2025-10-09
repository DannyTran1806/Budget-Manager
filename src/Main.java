import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double total = 0;
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            String[] entries = input.split(" ");
            String pricePart = entries[entries.length - 1];
            double price = Double.parseDouble(pricePart.replace("$", ""));
            total += price;
        }

        System.out.println("Total: $" + total);

        scanner.close();
    }
}
