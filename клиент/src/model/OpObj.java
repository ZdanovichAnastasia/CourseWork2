package model;

//import java.io.Serializable;
import java.io.Serializable;
import java.util.Objects;

public class OpObj implements Serializable {
    private int idOperations;
    private int idCostObj;
    private int driverObj;
    private double unitSumObj;

    public OpObj() {
        this.idOperations = 0;
    }

    public int getIdOperations() {
        return idOperations;
    }

    public void setIdOperations(int idOperations) {
        this.idOperations = idOperations;
    }

    public int getIdCostObj() {
        return idCostObj;
    }

    public void setIdCostObj(int idCostObj) {
        this.idCostObj = idCostObj;
    }

    public int getDriverObj() {
        return driverObj;
    }

    public void setDriverObj(int driverObj) {
        this.driverObj = driverObj;
    }

    public double getUnitSumObj() {
        return unitSumObj;
    }

    public void setUnitSumObj(double unitSumObj) {
        this.unitSumObj = unitSumObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpObj that = (OpObj) o;

        return Objects.equals(this.idOperations, that.idOperations) &&
                Objects.equals(this.idCostObj, that.idCostObj)&&
                Objects.equals(this.driverObj, that.driverObj)&&
                Objects.equals(this.unitSumObj, that.unitSumObj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idOperations, this.idCostObj, this.driverObj, this.unitSumObj);
    }

    @Override
    public String toString() {
        return "OpObj{" +
                "idOperations=" + idOperations +
                ", idCostObj='" + idCostObj + '\'' +
                ", driverObj='" + driverObj + '\'' +
                ", unitSumObj='" + unitSumObj + '\'' +
                '}';
    }
}