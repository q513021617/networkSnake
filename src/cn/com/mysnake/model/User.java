package cn.com.mysnake.model;

public class User {
    private Integer id;
    private String userName;
    private String userpassword;



    public User() {

    }

    public User(Integer id, String userName, String userpassword) {
        this.id = id;
        this.userName = userName;
        this.userpassword = userpassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userpassword='" + userpassword + '\'' +
                '}';
    }
}
