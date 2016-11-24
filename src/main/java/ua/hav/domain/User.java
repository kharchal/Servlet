package ua.hav.domain;

import ua.hav.domain.annotations.Display;
import ua.hav.domain.annotations.Id;
import ua.hav.domain.annotations.Role;
import ua.hav.domain.annotations.Table;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юля on 11.08.2016.
 */
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Display(size = 3)
    private int id;

    @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z_-]+$")
    @Size(min = 3, max = 8)
    @Display
    private String login;

    @Size(min = 3, max = 8)
    @Display(show = false)
    private String password;

    @Display
    @Size(min = 0, max = 15)
    private String name;

    @Size(min = 0, max = 9999)
    @Display(size = 4)
    private int account;

    @Size(min = 1, max = 1)
    @Pattern(regexp = "[aug]{1}")
    @Role
    @Display(size = 3)
    private String role;

    @Display(size = 10)
    @ua.hav.domain.annotations.List(clazz = Phone.class)
    private List<Phone> phones;

    public User() {
        phones = new ArrayList<>();
    }

    public User(String login, String password, String name, int account, String role) {
        this();
        this.login = login;
        this.password = password;
        this.name = name;
        this.account = account;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", account=" + account +
                ", role='" + role + '\'' +
                ", phones=" + phones +
                '}';
    }
}
