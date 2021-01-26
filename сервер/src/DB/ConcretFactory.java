package DB;

public class ConcretFactory extends AbstractFactory{
    @Override
    public TableUsers getUsers() {
        return TableUsers.getInstance();
    }

    public TableCompany getCompany() {
        return TableCompany.getInstance();
    }
    public TableExpenses getExpenses() {
        return TableExpenses.getInstance();
    }
    public TableResources getResources() {
        return TableResources.getInstance();
    }
    public TableDataCompany getDataCompany() {
        return TableDataCompany.getInstance();
    }
    public TableOperations getOperations() {
        return TableOperations.getInstance();
    }
    public TableProduction getProduction() {
        return TableProduction.getInstance();
    }
    public TableResOp getResOp() { return TableResOp.getInstance(); }
    public TableOpObj getOpObj() { return TableOpObj.getInstance(); }
    public TableReport getReport() {return  TableReport.getInstance();}

}
