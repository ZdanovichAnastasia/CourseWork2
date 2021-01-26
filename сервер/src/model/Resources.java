package model;

import java.io.Serializable;
import java.util.Objects;

public class Resources implements Serializable {
    private int idResource;
    private String resourceName;
    private double resourceCost;
    private int expId;

    public Resources() {
        this.idResource = 0;
    }

    public int getIdResource() {
        return idResource;
    }
    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public double getResourceCost() {
        return resourceCost;
    }
    public void setResourceCost(double resourceCost) {
        this.resourceCost = resourceCost;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resources that = (Resources) o;

        return Objects.equals(this.idResource, that.idResource) &&
                Objects.equals(this.resourceName, that.resourceName) &&
                Objects.equals(this.resourceCost, that.resourceCost)&&
                Objects.equals(this.expId, that.expId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idResource, this.resourceName, this.resourceCost, this.expId);// this.surname, this.phone, this.login, this.password, this.role);
    }

    @Override
    public String toString() {
        return "Resources{" +
                "idResource=" + idResource +
                ", resourceName='" + resourceName + '\'' +
                ", resourceCost='" + resourceCost + '\'' +
                ", expId='" + expId + '\'' +
                '}';
    }
}
