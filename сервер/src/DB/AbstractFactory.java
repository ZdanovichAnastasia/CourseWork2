package DB;

public  abstract class AbstractFactory {
    public abstract TableUsers getUsers();
    public abstract TableCompany getCompany();
    public abstract TableDataCompany getDataCompany();
    public abstract TableExpenses getExpenses();
    public abstract TableResources getResources();
    public abstract TableOperations getOperations();
    public abstract TableProduction getProduction();
    public abstract TableResOp getResOp();
    public abstract TableOpObj getOpObj();
    public abstract TableReport getReport();
}
