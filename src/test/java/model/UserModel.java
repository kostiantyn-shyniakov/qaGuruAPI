package model;

public class UserModel {
    private String name;
    private String job;

    public UserModel(String email, String password) {
        this.name = email;
        this.job = password;
    }
}
