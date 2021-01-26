package model;

import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {
    private int idCompany;
    private String companyName;
    private String country;
    private String industry;

    public Company() {
        this.idCompany = 0;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company that = (Company) o;

        return Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.companyName, that.companyName)&&
               Objects.equals(this.country, that.country)&&
                Objects.equals(this.industry, that.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCompany, this.companyName, this.country, this.industry);
    }

    @Override
    public String toString() {
        return "Company{" +
                "idCompany=" + idCompany +
                ", companyName='" + companyName + '\'' +
                ", country='" + country + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
