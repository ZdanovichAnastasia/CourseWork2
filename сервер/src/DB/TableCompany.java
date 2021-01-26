package DB;

import model.Company;

import java.util.ArrayList;

public class TableCompany implements TablesInterface{
    private static TableCompany instance;
    private Connection Connection;

    private TableCompany() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableCompany getInstance() {
        if (instance == null) {
            instance = new TableCompany();
        }
        return instance;
    }
    @Override
    public void create(){
        String str =
                "  CREATE TABLE IF NOT EXISTS company (\n" +
                "  idCompany INT NOT NULL AUTO_INCREMENT,\n" +
                "  companyName VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  country VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  industry VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (idCompany));";
        Connection.execute(str);
    };
    public String findCompany(Company obj, int fl) {
        String status = "";
        String str = "SELECT * From company Where companyName = '" + obj.getCompanyName() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        if (fl == 1)
        {
            for (String[] item : result)
                status = item[1];
        }
        if (fl == 2)
        {
            for (String[] item : result)
                status = item[0];
        }
        return status;
    }
    public String findNameCompany(int idC) {
        String status = "";
        String str = "SELECT * From company Where idCompany = '" + idC + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            status = item[1];
        return status;
    }
    @Override
    public void insert(Object obj) {
        Company company = (Company)obj;
        String str = "INSERT INTO company (companyName, country, industry) VALUES('" + company.getCompanyName()
                + "', '" + company.getCountry() + "', '" + company.getIndustry() + "')";
        Connection.execute(str);
    }
    public Company selectCompanies(Company obj) {
        String str = "SELECT * From company Where companyName = '" + obj.getCompanyName() + "'";
        //"' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        Company company = new Company();
        for (String[] items: result){
            company.setIdCompany(Integer.parseInt(items[0]));
            company.setCompanyName(items[1]);
            company.setCountry(items[2]);
            company.setIndustry(items[3]);
            //user.setSurname(items[2]);
            //user.setPhone(items[3]);
            //user.setLogin(items[4]);
            //user.setPassword(items[5]);
        }
        return company;
    }
    public ArrayList<Company> selectAll() {
        String str = "SELECT * From company";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Company> listCompanies = new ArrayList<>();
        for (String[] items: result){
            Company comp = new Company();
            comp.setIdCompany(Integer.parseInt(items[0]));
            comp.setCompanyName(items[1]);
            comp.setCountry(items[2]);
            comp.setIndustry(items[3]);
            listCompanies.add(comp);
        }
        return listCompanies;
    }
    @Override
    public void delete(String name) {
        String str = "DELETE FROM company WHERE  companyName = '" + name+"'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }

    @Override
    public String find(Object obj) {
        return null;
    }
}
/*implements InterfaceCompany{
    private static TableCompany instance;
    private Connection Connection;

    private TableCompany() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableCompany getInstance() {
        if (instance == null) {
            instance = new TableCompany();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS company (\n" +
                "  idCompany INT NOT NULL AUTO_INCREMENT,\n" +
                "  companyName VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  country VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  industry VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (idCompany));";
        Connection.execute(str);
    };
    @Override
    public String findCompany(Company obj, int fl) {
        String status = "";
        String str = "SELECT * From company Where companyName = '" + obj.getCompanyName() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        if (fl == 1)
        {
            for (String[] item : result)
               status = item[1];
            }
        if (fl == 2)
        {
            for (String[] item : result)
                status = item[0];
        }
        return status;
    }
    @Override
    public String findNameCompany(int idC) {
        String status = "";
        String str = "SELECT * From company Where idCompany = '" + idC + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            status = item[1];
        return status;
    }
    @Override
    public void insert(Company obj) {
        String str = "INSERT INTO company (companyName, country, industry) VALUES('" + obj.getCompanyName()
                + "', '" + obj.getCountry() + "', '" + obj.getIndustry() + "')";
                //+ "', '" + obj.getPassword() + "', '" + obj.getRole() + "')";
        Connection.execute(str);
    }
    @Override
    public Company selectCompanies(Company obj) {
        String str = "SELECT * From company Where companyName = '" + obj.getCompanyName() + "'";
                //"' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        Company company = new Company();
        for (String[] items: result){
            company.setIdCompany(Integer.parseInt(items[0]));
            company.setCompanyName(items[1]);
            company.setCountry(items[2]);
            company.setIndustry(items[3]);
            //user.setSurname(items[2]);
            //user.setPhone(items[3]);
            //user.setLogin(items[4]);
            //user.setPassword(items[5]);
        }
        return company;
    }
    @Override
    public ArrayList<Company> selectAll() {
        String str = "SELECT * From company";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Company> listCompanies = new ArrayList<>();
        for (String[] items: result){
            Company comp = new Company();
            comp.setIdCompany(Integer.parseInt(items[0]));
            comp.setCompanyName(items[1]);
            comp.setCountry(items[2]);
            comp.setIndustry(items[3]);
            listCompanies.add(comp);
        }
        return listCompanies;
    }
    @Override
    public void delete(String name) {
        String str = "DELETE FROM company WHERE  companyName = '" + name+"'";
        Connection.execute(str);
    }

}*/
