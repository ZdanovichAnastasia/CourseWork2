package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Report implements Serializable {
    private int idReport;
    private double income;
    private Date date;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private int idCompany;

    public Report() {
        this.idReport = 0;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public String getDate() {
        return formatter.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report that = (Report) o;

        return Objects.equals(this.idReport, that.idReport) &&
                Objects.equals(this.income, that.income) &&
                Objects.equals(this.date, that.date) &&
                Objects.equals(this.idCompany, that.idCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idReport, this.income, this.date, this.idCompany);// this.surname, this.phone, this.login, this.password, this.role);
    }

    @Override
    public String toString() {
        return "Report{" +
                "idReport=" + idReport +
                ", income='" + income + '\'' +
                ", date='" + date + '\'' +
                ", idCompany='" + idCompany + '\'' +
                '}';
    }
}