package DB;

import model.Expenses;
import model.Operations;

import java.util.ArrayList;

public class TableOperations implements TablesInterface{
    private static TableOperations instance;
    private Connection Connection;

    private TableOperations() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableOperations getInstance() {
        if (instance == null) {
            instance = new TableOperations();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS operation (\n" +
                "  operation_id INT NOT NULL AUTO_INCREMENT,\n" +
                "  operation_name VARCHAR(255) NULL DEFAULT NULL,\n" +
                "  operation_cost DOUBLE NULL DEFAULT NULL,\n" +
                "  type VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  company_idCompany INT NOT NULL,\n" +
                "  PRIMARY KEY (operation_id),\n" +
                "  CONSTRAINT fk_operation_company1\n" +
                "  FOREIGN KEY (company_idCompany)\n" +
                "  REFERENCES company (idCompany));";
        Connection.execute(str);
    };
    @Override
    public String find(Object obj) {
        Operations operations = (Operations)obj;
        String id = "";
        String str = "SELECT * From operation Where operation_name = '" + operations.getOperationName() +
                "' and company_idCompany = '" + operations.getIdCompany() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            id = item[0];
        return id;
    }
    public String findOperations(int idOp, int idC) {
        String name = "";
        String str = "SELECT * From operation Where operation_id = '" + idOp +
                "' and company_idCompany = '" + idC + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            name = item[1];

        return name;
    }
    @Override
    public void insert(Object obj) {
        Operations operations = (Operations)obj;
        String str = "INSERT INTO operation (operation_name, operation_cost, type, company_idCompany) VALUES('" + operations.getOperationName()
                + "', '" + operations.getOperationCost() + "', '" + operations.getOperationType()+ "', '" + operations.getIdCompany()+"')";
        Connection.execute(str);
    }
   // @Override
    public void update(Operations obj){
        String str = "UPDATE kurs.operation SET operation_name = '"+ obj.getOperationName()+
                "', type = '" + obj.getOperationType() +
                "' WHERE operation_id = '" + obj.getIdOperation() + "'";
        Connection.execute(str);
    }
    public double getSum(String idOp) {
        double summ = 0;
        String str = "select sum(unitsumop) from kurs.res_op ro where idop = '" + idOp + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result){
            if(item[0]!=null){
                summ += Double.parseDouble(item[0]);
            }
        }
        String str1 = "UPDATE kurs.operation SET operation_cost='" + summ + "' WHERE operation_id = '" + idOp + "'";
        Connection.execute(str1);
        return summ;
    }

    public ArrayList<Operations> selectAll() {
        String str = "SELECT * From operation";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Operations> listOperations = new ArrayList<>();
        for (String[] items: result){
            Operations op = new Operations();
            op.setIdOperation(Integer.parseInt(items[0]));
            op.setOperationName(items[1]);
            op.setOperationCost(Double.parseDouble(items[2]));
            op.setOperationType(items[3]);
            op.setIdCompany(Integer.parseInt(items[4]));
            listOperations.add(op);
        }
        return listOperations;
    }
    public ArrayList<Operations> sort(String s) {
        String str = "SELECT * From operation order by "+s;
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Operations> listOperations = new ArrayList<>();
        for (String[] items: result){
            Operations op = new Operations();
            op.setIdOperation(Integer.parseInt(items[0]));
            op.setOperationName(items[1]);
            op.setOperationCost(Double.parseDouble(items[2]));
            op.setOperationType(items[3]);
            op.setIdCompany(Integer.parseInt(items[4]));
            listOperations.add(op);
        }
        return listOperations;
    }
    public void delete(String name, int idC) {
        String str = "DELETE FROM operation WHERE  operation_name = '" + name+ "' and company_idCompany = '" + idC + "'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }

    @Override
    public void delete(String id) {

    }
}
