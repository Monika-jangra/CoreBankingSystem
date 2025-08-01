package Main;

import Dao.AccountDao;
import Dao.TransactionDao;
import Dao.UserDao;
import Model.Account;
import Model.Transaction;
import Model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {
    static Scanner sc = new Scanner(System.in);
    static User currentUser = null;

    static UserDao userDAO = new UserDao();
    static AccountDao accountDAO = new AccountDao();
    static TransactionDao transactionDAO = new TransactionDao();

    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                System.out.println("\n--- Welcome to Secure Banking ---");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choice: ");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> System.exit(0);
                    default -> System.out.println("Invalid choice.");
                }
            } else {
                showUserMenu();
            }
        }
    }

    private static void showUserMenu() {
        System.out.println("\n--- Hello, " + currentUser.getEmail() + " ---");
        System.out.println("1. Create Account");
        System.out.println("2. View Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. View Transactions");
        System.out.println("6. View Last Month Transactions");
        System.out.println("7. Logout");
        System.out.print("Choice: ");
        int ch = sc.nextInt();

        switch (ch) {
            case 1 -> createAccount();
            case 2 -> viewAccount();
            case 3 -> deposit();
            case 4 -> withdraw();
            case 5 -> viewTransactions();
            case 6 -> viewLastMonthTransactions();
            case 7 -> currentUser = null;
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void register() {
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Password: ");
        String password = sc.next();
        System.out.print("Phone number: ");
        String phoneNo = sc.next();

        User user = new User(0, email, password, phoneNo);

        try {
            if (userDAO.registerUser(user)) {
                System.out.println("Registered successfully!");
            }
        } catch (Exception e) {
            System.out.println("An account with this email may already exist.");
        }
    }

    private static void login() {
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Password: ");
        String password = sc.next();

        try {
            currentUser = userDAO.loginUser(email, password);
            if (currentUser != null) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAccount() {
        try {
            Account existing = accountDAO.getAccountByUserId(currentUser.getUserId());
            if (existing != null) {
                System.out.println("You already have an account.");
                return;
            }

            System.out.print("Account Name: ");
            String name = sc.next();
            System.out.print("Initial Balance: ");
            double balance = sc.nextDouble();
            String accNumber = generateAccountNumber();

            Account newAcc = new Account(name, balance, currentUser.getUserId(), accNumber);
            if (accountDAO.createAccount(newAcc)) {
                System.out.println("Account created! Account Number: " + accNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewAccount() {
        try {
            Account acc = accountDAO.getAccountByUserId(currentUser.getUserId());
            if (acc != null) {
                System.out.println(acc);
            } else {
                System.out.println("No account found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deposit() {
        try {
            Account acc = accountDAO.getAccountByUserId(currentUser.getUserId());
            if (acc == null) {
                System.out.println("No account found.");
                return;
            }

            System.out.print("Amount to deposit: ");
            double amount = sc.nextDouble();
            double newBalance = acc.getBalance() + amount;

            if (accountDAO.updateBalance(acc.getAccountId(), newBalance)) {
                transactionDAO.recordTransaction(new Transaction(0, acc.getAccountId(), "DEPOSIT", amount, null));
                printReceipt("Deposit", amount, acc.getAccountNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void withdraw() {
        try {
            Account acc = accountDAO.getAccountByUserId(currentUser.getUserId());
            if (acc == null) {
                System.out.println("No account found.");
                return;
            }

            System.out.print("Amount to withdraw: ");
            double amount = sc.nextDouble();

            if (acc.getBalance() >= amount) {
                double newBalance = acc.getBalance() - amount;
                if (accountDAO.updateBalance(acc.getAccountId(), newBalance)) {
                    transactionDAO.recordTransaction(new Transaction(0, acc.getAccountId(), "WITHDRAW", amount, null));
                    printReceipt("Withdraw", amount, acc.getAccountNumber());
                }
            } else {
                System.out.println("Insufficient balance.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewTransactions() {
        try {
            int accountId = accountDAO.getAccountIdByUserId(currentUser.getUserId());
            if (accountId == -1) {
                System.out.println("No account found.");
                return;
            }

            List<Transaction> tens = transactionDAO.getTransactions(accountId);
            for (Transaction txn : tens) {
                System.out.println(txn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewLastMonthTransactions() {
        try {
            int accountId = accountDAO.getAccountIdByUserId(currentUser.getUserId());
            if (accountId == -1) {
                System.out.println("No account found.");
                return;
            }

            List<Transaction> tens = transactionDAO.getLastMonthTransactions(accountId);
            if (tens.isEmpty()) {
                System.out.println("No transactions found for last month.");
            } else {
                for (Transaction txn : tens) {
                    System.out.println(txn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void printReceipt(String type, double amount, String accNumber) {
        System.out.println("\n--- Transaction Receipt ---");
        System.out.println("Transaction Type: " + type);
        System.out.println("Amount: ₹" + amount);
        System.out.println("Date: " + LocalDateTime.now());
        System.out.println("Account Number: " + accNumber);
        System.out.println("Status: Success ✅");
        System.out.println("---------------------------");
        System.out.println("📲 SMS Alert: " + type + " of ₹" + amount + " completed successfully.\n");
    }

    private static String generateAccountNumber() {
        return String.valueOf(1000000000L + (long) (Math.random() * 9000000000L));
    }
}
