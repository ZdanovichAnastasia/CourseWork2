package model;

import java.io.Serializable;
import java.util.Objects;

public class Users implements Serializable{
    private int idUser;
    private String name;
    private String surname;
    private String phone;
    private String login;
    private String password;
    private String role;


    public Users() {
        this.idUser = 0;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users that = (Users) o;

        return Objects.equals(this.idUser, that.idUser) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.surname, that.surname) &&
                Objects.equals(this.phone, that.phone) &&
                Objects.equals(this.login, that.login) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.role, that.role);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.idUser, this.name, this.surname, this.phone, this.login, this.password, this.role);
    }

    @Override
    public String toString() {
        return "Users{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
               // ", passportSeries='" + passportSeries + '\'' +
                //", passportNumber=" + passportNumber +
                '}';
    }
}
