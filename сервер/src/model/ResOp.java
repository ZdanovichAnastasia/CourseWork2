package model;

import java.io.Serializable;
import java.util.Objects;

public class ResOp implements Serializable {
    private int idOperations;
    private int idResources;
    private int driverOp;
    private double unitSumOp;

    public ResOp() {
        this.idOperations = 0;
    }

    public int getIdOperations() {
        return idOperations;
    }
    public void setIdOperations(int idOperations) {
        this.idOperations = idOperations;
    }

    public int getIdResources() {
        return idResources;
    }
    public void setIdResources(int idResources) {
        this.idResources = idResources;
    }

    public double getUnitSumOp() {
        return unitSumOp;
    }

    public void setUnitSumOp(double unitSumOp) {
        this.unitSumOp = unitSumOp;
    }

    public int getDriverOp() {
        return driverOp;
    }

    public void setDriverOp(int driverOp) {
        this.driverOp = driverOp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResOp that = (ResOp) o;

        return Objects.equals(this.idOperations, that.idOperations) &&
                Objects.equals(this.idResources, that.idResources)&&
                Objects.equals(this.driverOp, that.driverOp) &&
                Objects.equals(this.unitSumOp, that.unitSumOp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idOperations, this.idResources, this.driverOp, this.unitSumOp);
    }

    @Override
    public String toString() {
        return "ResOp{" +
                "idOperations=" + idOperations +
                ", idResources='" + idResources + '\'' +
                ", driverOp='" + driverOp + '\'' +
                ", unitSumOp='" + unitSumOp + '\'' +
                '}';
    }
}
