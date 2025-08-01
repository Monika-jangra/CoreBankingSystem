package Model;

public class Account {
    private int accountId;
    private String name;
    private double balance;
    private int userId;
    private String accountNumber; // ✅ New field

    // Constructor with account number
    public Account(int accountId, String name, double balance, int userId, String accountNumber) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    // Overloaded constructor if needed (without accountId, e.g., before DB insertion)
    public Account(String name, double balance, int userId, String accountNumber) {
        this.name = name;
        this.balance = balance;
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    // Getters and setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
