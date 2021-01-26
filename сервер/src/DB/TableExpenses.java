package DB;

import model.Company;
import model.Expenses;

import java.util.ArrayList;

public class TableExpenses implements TablesInterface{
    private static TableExpenses instance;
    private Connection Connection;

    private TableExpenses() {
        Connection = Connection.getInstance();
    }
    public static synchronized TableExpenses getInstance() {
        if (instance == null) {
            instance = new TableExpenses();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS expenses (\n" +
                "  expenses_id INT NOT NULL AUTO_INCREMENT,\n" +
                "  expenses_name VARCHAR(255) NOT NULL,\n" +
                "  expenses_group VARCHAR(255) NOT NULL,\n" +
                "  expenses_summ FLOAT NOT NULL,\n" +
                "  expenses_type VARCHAR(45) NOT NULL,\n" +
                "  company_idCompany INT NOT NULL,\n" +
                "  PRIMARY KEY (expenses_id),\n" +
                "  CONSTRAINT fk_expenses_company1\n" +
                "  FOREIGN KEY (company_idCompany)\n" +
                "  REFERENCES company (idCompany));";
        Connection.execute(str);
    };
    @Override
    public String find(Object obj) {
        Expenses expenses = (Expenses)obj;
        String id = "";
        String str = "SELECT * From expenses Where expenses_name = '" + expenses.getExpensesName() + "' and company_idCompany = '" + expenses.getIdCompany() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            id = item[0];
        return id;
    }

    @Override
    public void insert(Object obj) {
        Expenses expenses = (Expenses)obj;
        String str = "INSERT INTO expenses (expenses_name, expenses_group, expenses_summ, expenses_type, company_idCompany) VALUES('" + expenses.getExpensesName()
                + "', '" + expenses.getExpensesGroup() + "', '" + expenses.getSumOfExpenses() + "', '" + expenses.getExpensesType()+ "', '" + expenses.getIdCompany()+"')";
        Connection.execute(str);
    }
    //@Override
    public void update(Object obj){
        Expenses expenses = (Expenses)obj;
        String str = "UPDATE kurs.expenses SET expenses_name = '"+ expenses.getExpensesName()+
                "', expenses_group = '" + expenses.getExpensesGroup() + "', expenses_summ = '" + expenses.getSumOfExpenses() + "', expenses_type='" + expenses.getExpensesType() +
                "' WHERE expenses_id = '" + expenses.getIdExpenses() + "'";
        Connection.execute(str);
    }

    public ArrayList<Expenses> selectAll() {
        String str = "SELECT * From expenses";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Expenses> listExpenses = new ArrayList<>();
        for (String[] items: result){
            Expenses exp = new Expenses();
            exp.setIdExpenses(Integer.parseInt(items[0]));
            exp.setExpensesName(items[1]);
            exp.setExpensesGroup(items[2]);
            exp.setSumOfExpenses(items[3]);
            exp.setExpensesType(items[4]);
            exp.setIdCompany(Integer.parseInt(items[5]));
            listExpenses.add(exp);
        }
        return listExpenses;
    }
    public ArrayList<Expenses> sort(String s) {
        String str = "SELECT * From expenses order by "+s;
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Expenses> listExpenses = new ArrayList<>();
        for (String[] items: result){
            Expenses exp = new Expenses();
            exp.setIdExpenses(Integer.parseInt(items[0]));
            exp.setExpensesName(items[1]);
            exp.setExpensesGroup(items[2]);
            exp.setSumOfExpenses(items[3]);
            exp.setExpensesType(items[4]);
            exp.setIdCompany(Integer.parseInt(items[5]));
            listExpenses.add(exp);
        }
        return listExpenses;
    }
    public void delete(String name, int idC) {
        String str = "DELETE FROM expenses WHERE  expenses_name = '" + name+"' and company_idCompany = '" + idC + "'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }

    @Override
    public void delete(String id) {

    }
}
