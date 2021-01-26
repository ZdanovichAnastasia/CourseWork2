package DB;

import model.Expenses;
import model.Operations;
import model.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TableResources implements TablesInterface{
    private static TableResources instance;
    private Connection Connection;

    private TableResources() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableResources getInstance() {
        if (instance == null) {
            instance = new TableResources();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS resources (\n" +
                "  resources_id INT NOT NULL AUTO_INCREMENT,\n" +
                "  resources_name VARCHAR(255) NULL DEFAULT NULL,\n" +
                "  resources_cost INT NOT NULL,\n" +
                "  expenses_id INT NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (resources_id),\n" +
                "  CONSTRAINT resources_fk\n" +
                "    FOREIGN KEY (expenses_id)\n" +
                "    REFERENCES expenses (expenses_id));";
        Connection.execute(str);
    };

    public String findResources(Resources obj, int idExp) {
        String idR = "";
        String str = "SELECT * From resources Where resources_name = '" + obj.getResourceName() +//"'";
                "' and expenses_id = '" + idExp + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            idR = item[0];

        return idR;
    }
    public ArrayList<String[]> findCostRes(int idC){
        String str = "select distinct resources_name from kurs.expenses ex left join kurs.resources r on r.expenses_id = ex.expenses_id where ex.company_idCompany = '"+idC+"'";
        ArrayList<String[]> masName = Connection.getArrayResult(str);
        ArrayList<String[]> data = new ArrayList<>();
        for(String[] name: masName){
            if(name[0]!=null) {
                String[] mas = new String[3];
                mas[1] = name[0];
                str = "select sum(resources_cost), resources_id from kurs.expenses ex left join kurs.resources r on r.expenses_id = ex.expenses_id " +
                        "where ex.company_idCompany = '" + idC + "' and resources_name ='" + name[0] + "'";
                ArrayList<String[]> result = Connection.getArrayResult(str);
                mas[2] = result.get(0)[0];
                mas[0] = result.get(0)[1];
                data.add(mas);
            }
        }
        Collections.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] a, String[] b) {
                return a[0].compareTo(b[0]);
            }
        });
        return data;
    }
    public String findResources(String idRes) {
        String str = "select resources_id, resources_name from kurs.resources where resources_id = '"+idRes+"'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String name = "";
        for (String[] item : result)
            name = item[1];
        return name;
    }
    public String findIdExp(String idExp){
        String nameExp = "";
        String str = "select distinct resources_id from kurs.resources where expenses_id = '"+idExp+"'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            nameExp = item[0];
        return nameExp;
    }
    public ArrayList<String[]> findRes(int idUs){
        String str = "select resources_id, resources_name, company_idCompany from kurs.resources r left join kurs.expenses ex on ex.expenses_id = r.expenses_id left join kurs.company c on c.idCompany = ex.company_idCompany left join kurs.datacompany dc on dc.idcompany = c.idCompany where iduser = '"+idUs+"'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        return result;
    }
    @Override
    public void insert(Object obj) {
        Resources resources = (Resources)obj;
        String str = "INSERT INTO resources (resources_name, resources_cost, expenses_id) VALUES('" + resources.getResourceName()
                + "', '" + resources.getResourceCost() + "', '" + resources.getExpId() +"')";
        Connection.execute(str);
    }
    public void update(int idUs, String nameRes, Resources obj){
        String str = "select resources_id, resources_name, company_idCompany from kurs.resources r left join kurs.expenses ex on ex.expenses_id = r.expenses_id left join kurs.company c on c.idCompany = ex.company_idCompany left join kurs.datacompany dc on dc.idcompany = c.idCompany where iduser = '"+idUs+"' and resources_name = '"+nameRes+"'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] items: result) {
            String str1 = "UPDATE kurs.resources SET resources_name = '"+ obj.getResourceName()+
                    "' WHERE resources_id = '" + items[0] + "'";
            Connection.execute(str1);
        }
    }
    public ArrayList<String[]> update(Expenses exp){
        String str = "select resources_id, resources_name from resources where expenses_id = '" + exp.getIdExpenses() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String str1 = "update resources SET resources_cost = '" + exp.getSumOfExpenses() + "' where expenses_id = '" + exp.getIdExpenses() + "'";
        Connection.execute(str1);
        return result;
    }


    public Resources selectResources(Resources obj) {
        String str = "SELECT * From resources Where resources_name = '" + obj.getResourceName() + "'";
        //"' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        Resources res = new Resources();
        for (String[] items: result){
            res.setIdResource(Integer.parseInt(items[0]));
            res.setResourceName(items[1]);
            res.setResourceCost(Double.parseDouble(items[2]));
            res.setExpId(Integer.parseInt(items[3]));
        }
        return res;
    }
    public ArrayList<Resources> selectAll() {
        String str = "SELECT * From resources";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Resources> listResources = new ArrayList<>();
        for (String[] items: result){
            Resources res = new Resources();
            res.setIdResource(Integer.parseInt(items[0]));
            res.setResourceName(items[1]);
            res.setResourceCost(Double.parseDouble(items[2]));
            res.setExpId(Integer.parseInt(items[3]));
            listResources.add(res);
        }
        return listResources;
    }
    public ArrayList<String[]> sortName(int idC){
        String str = "select distinct resources_name from kurs.expenses ex left join kurs.resources r on r.expenses_id = ex.expenses_id where ex.company_idCompany = '"+idC+"' order by resources_name";
        ArrayList<String[]> masName = Connection.getArrayResult(str);
        ArrayList<String[]> data = new ArrayList<>();
        for(String[] name: masName){
            if(name[0]!=null) {
                String[] mas = new String[3];
                mas[1] = name[0];
                str = "select sum(resources_cost), resources_id from kurs.expenses ex left join kurs.resources r on r.expenses_id = ex.expenses_id " +
                        "where ex.company_idCompany = '" + idC + "' and resources_name ='" + name[0] + "'";
                ArrayList<String[]> result = Connection.getArrayResult(str);
                mas[2] = result.get(0)[0];
                mas[0] = result.get(0)[1];
                data.add(mas);
            }
        }
        return data;
    }
    @Override
    public void delete(String id) {
        String str = "DELETE FROM resources WHERE  resources_id = '" + id+"'";
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
