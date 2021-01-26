package model;

import java.io.Serializable;
import java.util.Objects;

public class Expenses implements Serializable {
    private int idExpenses;
    private String expensesName;
    private String expensesGroup;
    private String expensesType;
    private String sumofExpenses;
    private int idCompany;

    public Expenses() {
        this.idExpenses = 0;
    }

    public int getIdExpenses() {
        return idExpenses;
    }

    public void setIdExpenses(int idCompany) {
        this.idExpenses = idCompany;
    }

    public String getExpensesName() {
        return expensesName;
    }

    public void setExpensesName(String companyName) {
        this.expensesName = companyName;
    }
    public String getExpensesGroup() {
        return expensesGroup;
    }

    public void setExpensesGroup(String expensesGroup) {
        this.expensesGroup = expensesGroup;
    }
    public String getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(String expensesType) {
        this.expensesType = expensesType;
    }
    public String getSumOfExpenses() {
        return sumofExpenses;
    }

    public void setSumOfExpenses(String sumofExpenses) {
        this.sumofExpenses = sumofExpenses;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getIdCompany() {
        return idCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expenses that = (Expenses) o;

        return Objects.equals(this.idExpenses, that.idExpenses) &&
                Objects.equals(this.expensesName, that.expensesName) &&
                Objects.equals(this.expensesGroup, that.expensesGroup) &&
                Objects.equals(this.expensesType, that.expensesType) &&
                Objects.equals(this.sumofExpenses, that.sumofExpenses) &&
                Objects.equals(this.idCompany, that.idCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idExpenses, this.expensesName, this.expensesGroup, this.expensesType, this.sumofExpenses, this.idCompany);
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "idExpenses=" + idExpenses +
                ", expensesName='" + expensesName + '\'' +
                ", expensesGroup='" + expensesGroup + '\'' +
                ", sumofExpenses='" + sumofExpenses + '\'' +
                ", expensesType='" + expensesType + '\'' +
                ", idCompany='" + idCompany + '\'' +
                '}';
    }
}
