package Model;

public class User {
    private int userId;
    private String email;
    private String password;
    private String phoneNo;

    // Constructor
    public User(int userId, String email, String password, String phoneNo) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    // Getters and setters
    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getPhoneNo() {

        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {

        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
