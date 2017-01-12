package itp341.mohan.tanuja.empoweringsally;

/**
 * Created by tanujamohan on 11/23/16.
 */
public class User {

    public String mUsername;
    public String mPassword;

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() { return mUsername; }

    public String getPassword() { return mPassword; }

    public void setUsername(String username) { mUsername = username; }

    public void setPassword(String password) { mPassword = password; }
}
