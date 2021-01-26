package model;

import java.io.Serializable;
import java.util.Objects;

public class Production implements Serializable {
    private int idProduction;
    private String productionName;
    private int productionCol;
    private double costProduction;
    private int idCompany;
    private double profit;
    //private String role;

    public Production() {
        this.idProduction = 0;
    }

    public int getIdProduction() {
        return idProduction;
    }

    public void setIdProduction(int idProduction) {
        this.idProduction = idProduction;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }
    public int getProductionCol() {
        return productionCol;
    }

    public void setProductionCol(int productionCol) {
        this.productionCol = productionCol;
    }
    public double getCostProduction() {
        return costProduction;
    }

    public void setCostProduction(double costProduction) {
        this.costProduction = costProduction;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        return Objects.equals(this.idProduction, that.idProduction) &&
                Objects.equals(this.productionName, that.productionName) &&
                Objects.equals(this.productionCol, that.productionCol) &&
                Objects.equals(this.costProduction, that.costProduction)&&
                Objects.equals(this.profit, that.profit)&&
                Objects.equals(this.idCompany, that.idCompany);
        //Objects.equals(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProduction, this.productionName, this.productionCol, this.costProduction, this.profit, this.idCompany);// this.surname, this.phone, this.login, this.password, this.role);
    }

    @Override
    public String toString() {
        return "Production{" +
                "idProduction=" + idProduction +
                ", productionName='" + productionName + '\'' +
                ", driverOfCostObjects='" + productionCol + '\'' +
                ", costProduction='" + costProduction + '\'' +
                ", prodit='" + profit + '\'' +
                ", dCompany='" + idCompany + '\'' +
                //", role='" + role +
                // ", passportSeries='" + passportSeries + '\'' +
                //", passportNumber=" + passportNumber +
                '}';
    }
}