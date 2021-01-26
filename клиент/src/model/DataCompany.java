package model;

import java.io.Serializable;
import java.util.Objects;

public class DataCompany implements Serializable{
    private int idCompany;
    private int idUser;

    public DataCompany() {
        this.idCompany = 0;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataCompany that = (DataCompany) o;

        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.idUser, that.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.idUser);
    }

    @Override
    public String toString() {
        return "Users{" +
                "idCompany=" + idCompany +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}
