package model;

//import java.io.Serializable;
import java.io.Serializable;
import java.util.Objects;

public class Operations implements Serializable {
    private int idOperation;
    private String operationName;
    private String operationType;
    private double operationCost;
    private int idCompany;
    //private String role;

    public Operations() {
        this.idOperation = 0;
    }

    public int getIdOperation() {
        return idOperation;
    }
    public void setIdOperation(int idOperation) {
        this.idOperation = idOperation;
    }

    public String getOperationName() {
        return operationName;
    }
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public double getOperationCost() {
        return operationCost;
    }
    public void setOperationCost(double operationCost) {
        this.operationCost = operationCost;
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

        Operations that = (Operations) o;

        return Objects.equals(this.idOperation, that.idOperation) &&
                Objects.equals(this.operationName, that.operationName) &&
                Objects.equals(this.operationType, that.operationType) &&
                Objects.equals(this.idCompany, that.idCompany) &&
                Objects.equals(this.operationCost, that.operationCost);
        //Objects.equals(this.password, that.password) &&
        //Objects.equals(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idOperation, this.operationName, this.operationType, this.idCompany, this.operationCost);// this.surname, this.phone, this.login, this.password, this.role);
    }

    @Override
    public String toString() {
        return "Operations{" +
                "idOperation=" + idOperation +
                ", operationName='" + operationName + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationCost='" + operationCost + '\'' +
                ", idCompany='" + idCompany + '\'' +
                //", password='" + password + '\'' +
                //", role='" + role +
                // ", passportSeries='" + passportSeries + '\'' +
                //", passportNumber=" + passportNumber +
                '}';
    }
}
