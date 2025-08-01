package Model;

public class Transaction {
    private int transactionId;
    private int accountId;
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(int transactionId, int accountId, String type, double amount, String timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {

        return transactionId;
    }

    public void setTransactionId(int transactionId) {

        this.transactionId = transactionId;
    }

    public int getAccountId() {

        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public double getAmount() {

        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }

    public String getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(String timestamp) {

        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
