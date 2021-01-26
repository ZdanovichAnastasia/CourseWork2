package DB;

import model.Operations;
import model.Production;

import java.util.ArrayList;

public class TableProduction implements TablesInterface{
    private static TableProduction instance;
    private Connection Connection;

    private TableProduction() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableProduction getInstance() {
        if (instance == null) {
            instance = new TableProduction();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS production (\n" +
                "  production_id INT NOT NULL AUTO_INCREMENT,\n" +
                "  production_name VARCHAR(50) NOT NULL,\n" +
                "  cost DOUBLE NULL DEFAULT NULL,\n" +
                "  productioncol VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  profit DOUBLE NOT NULL,\n" +
                "  company_idCompany INT NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (production_id),\n" +
                "  CONSTRAINT fk_production_company1\n" +
                "  FOREIGN KEY (company_idCompany)\n" +
                "  REFERENCES company (idCompany));";
        Connection.execute(str);
    };
    @Override
    public String find(Object obj) {
        Production production = (Production)obj;
        String id = "";
        String str = "SELECT * From production Where production_name = '" + production.getProductionName() +
                "' and company_idCompany = '" + production.getIdCompany() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            id = item[0];

        return id;
    }

    @Override
    public void insert(Object obj) {
        Production production = (Production)obj;
        String str = "INSERT INTO production (production_name, cost, productioncol, profit, company_idCompany) VALUES('" + production.getProductionName()
                + "', '" + production.getCostProduction() + "', '" + production.getProductionCol() + "', '" + production.getProfit() + "', '" + production.getIdCompany()+"')";
        Connection.execute(str);
    }
    public double getSum(String idPr) {
        double summ = 0;
        String str = "select sum(unitsumobj) from kurs.op_obj where idobj = '" + idPr + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result){
            if(item[0]!=null){
                summ += Double.parseDouble(item[0]);
            }
        }
        String str1 = "UPDATE kurs.production SET cost='" + summ + "' WHERE production_id = '" + idPr + "'";
        Connection.execute(str1);
        return summ;
    }

    public void update(Production obj){
        String str = "UPDATE kurs.production SET production_name = '"+ obj.getProductionName()+
                "', productioncol = '" + obj.getProductionCol() +
                "', profit = '" + obj.getProfit() +
                "' WHERE production_id = '" + obj.getIdProduction() + "'";
        Connection.execute(str);
    }


    public ArrayList<Production> selectAll() {
        String str = "SELECT * From production";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Production> listProduction = new ArrayList<>();
        for (String[] items: result){
            Production pr = new Production();
            pr.setIdProduction(Integer.parseInt(items[0]));
            pr.setProductionName(items[1]);
            pr.setCostProduction(Double.parseDouble(items[2]));
            pr.setProductionCol(Integer.parseInt(items[3]));
            pr.setProfit(Double.parseDouble(items[4]));
            pr.setIdCompany(Integer.parseInt(items[5]));
            listProduction.add(pr);
        }
        return listProduction;
    }
    public ArrayList<Production> sort(String s) {
        String str = "SELECT * From production order by "+s;
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Production> listProduction = new ArrayList<>();
        for (String[] items: result){
            Production pr = new Production();
            pr.setIdProduction(Integer.parseInt(items[0]));
            pr.setProductionName(items[1]);
            pr.setCostProduction(Double.parseDouble(items[2]));
            pr.setProductionCol(Integer.parseInt(items[3]));
            pr.setProfit(Double.parseDouble(items[4]));
            pr.setIdCompany(Integer.parseInt(items[5]));
            listProduction.add(pr);
        }
        return listProduction;
    }

    public void delete(String name, int idC) {
        String str = "DELETE FROM production WHERE production_name = '" + name+"' and company_idCompany = '" + idC + "'";
        Connection.execute(str);
    }
    @Override
    public void delete(String id) {
        String str = "DELETE FROM production WHERE production_id = '" + id+ "'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }
}

