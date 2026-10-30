import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CODSOFT TASK 3 - ATM INTERFACE
 * Simulation only. Demo PIN: 1234 | Opening balance: Rs. 10,000.
 */
public class ATMInterface {
    private static final Scanner sc = new Scanner(System.in);
    private static final double DAILY_LIMIT = 20000.0;

    private static class Transaction {
        String type;
        double amount;
        double balance;
        LocalDateTime time;

        Transaction(String type, double amount, double balance) {
            this.type = type;
            this.amount = amount;
            this.balance = balance;
            this.time = LocalDateTime.now();
        }

        public String toString() {
            String date = time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            return String.format("%-12s Rs. %9.2f | Balance: Rs. %9.2f | %s",
                    type, amount, balance, date);
        }
    }

    private static class BankAccount {
        private final String holder;
        private final String accountNumber;
        private final int pin;
        private double balance;
        private double withdrawnToday;
        private final List<Transaction> transactions = new ArrayList<>();

        BankAccount(String holder, String accountNumber, int pin, double balance) {
            this.holder = holder;
            this.accountNumber = accountNumber;
            this.pin = pin;
            this.balance = balance;
        }

        boolean verifyPin(int enteredPin) {
            return pin == enteredPin;
        }

        boolean deposit(double amount) {
            if (!validAmount(amount)) return false;
            balance += amount;
            transactions.add(new Transaction("DEPOSIT", amount, balance));
            return true;
        }

        String withdraw(double amount) {
            if (!validAmount(amount)) return "Enter a valid positive amount.";
            if (amount > balance) return "Insufficient account balance.";
            if (withdrawnToday + amount > DAILY_LIMIT) return "Daily withdrawal limit exceeded.";
            balance -= amount;
            withdrawnToday += amount;
            transactions.add(new Transaction("WITHDRAWAL", amount, balance));
            return null;
        }

        private boolean validAmount(double amount) {
            return Double.isFinite(amount) && amount > 0;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("Aditi Singh", "123456789012", 1234, 10000);
        System.out.println("============= WELCOME TO JAVA ATM =============");
        System.out.println("Demo PIN: 1234 | Opening balance: Rs. 10,000");

        if (!login(account)) {
            System.out.println("Account session locked after 3 incorrect attempts.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--------------- ATM MENU ---------------");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Transaction history");
            System.out.println("5. Exit");

            int choice = readInt("Choose option (1-5): ", 1, 5);
            switch (choice) {
                case 1:
                    System.out.printf("Available balance: Rs. %.2f%n", account.balance);
                    break;
                case 2:
                    double deposit = readAmount("Enter deposit amount: Rs. ");
                    if (account.deposit(deposit)) {
                        System.out.printf("Deposit successful. Balance: Rs. %.2f%n", account.balance);
                    } else {
                        System.out.println("Deposit amount must be greater than zero.");
                    }
                    break;
                case 3:
                    double withdrawal = readAmount("Enter withdrawal amount: Rs. ");
                    String error = account.withdraw(withdrawal);
                    if (error == null) {
                        System.out.printf("Collect your cash. Balance: Rs. %.2f%n", account.balance);
                    } else {
                        System.out.println("Transaction failed: " + error);
                    }
                    break;
                case 4:
                    printHistory(account.transactions);
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using Java ATM.");
                    break;
            }
        }
    }

    private static boolean login(BankAccount account) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            int pin = readInt("Enter 4-digit PIN: ", 0, 9999);
            if (account.verifyPin(pin)) {
                String masked = "XXXXXX" +
                        account.accountNumber.substring(account.accountNumber.length() - 4);
                System.out.println("Welcome, " + account.holder + "!");
                System.out.println("Account number: " + masked);
                return true;
            }
            System.out.println("Incorrect PIN. Attempts left: " + (3 - attempt));
        }
        return false;
    }

    private static void printHistory(List<Transaction> transactions) {
        System.out.println("\n------------- TRANSACTION HISTORY -------------");
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static double readAmount(String message) {
        while (true) {
            System.out.print(message);
            try {
                double amount = Double.parseDouble(sc.nextLine().trim());
                if (Double.isFinite(amount) && amount > 0) return amount;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a valid amount greater than zero.");
        }
    }

    private static int readInt(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.printf("Enter a whole number from %d to %d.%n", min, max);
        }
    }
}
