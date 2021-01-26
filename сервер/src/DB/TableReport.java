package DB;
import model.Report;

import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TableReport implements  TablesInterface {
    private static TableReport instance;
    private Connection Connection;
    public static synchronized TableReport getInstance() {
        if (instance == null) {
            instance = new TableReport();
        }
        return instance;
    }

    private TableReport() {
        Connection = Connection.getInstance();
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS report (\n" +
                "  idreport INT NOT NULL AUTO_INCREMENT,\n" +
                "  dateRep DATE NULL DEFAULT NULL,\n" +
                "  income FLOAT NULL DEFAULT NULL,\n" +
                "  company_idCompany INT NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (idreport),\n" +
                "  CONSTRAINT fk_report_company1\n" +
                "  FOREIGN KEY (company_idCompany)\n" +
                "  REFERENCES company (idCompany));";
        Connection.execute(str);
    };
    @Override
    public void insert(Object obj) {
        String str = null;
        Report report = (Report)obj;
        str = "INSERT INTO report (dateRep, income, company_idCompany) VALUES('" + report.getDate()
                + "', '" + report.getIncome() + "', '" + report.getIdCompany()+"')";
        Connection.execute(str);
    }
    @Override
    public void update(Object obj, String id) {
        Report report = (Report)obj;
        String str = "UPDATE report SET income = '"+ report.getIncome()+
                "' WHERE idreport = '" + id+ "'";
        Connection.execute(str);
    }
    @Override
    public void delete(String id) {
        String str = "DELETE FROM report WHERE idreport = '" + id+ "'";
        Connection.execute(str);
    }
    @Override
    public String find(Object obj) {
        String id = "";
        Report report = (Report)obj;
        System.out.println(report.getDate()+" || "+report.getIdCompany());
        String str = "SELECT * From report Where dateRep = '" + report.getDate() +
                "' and  company_idCompany= '" + report.getIdCompany() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        for (String[] item : result)
            id = item[0];
        System.out.println("RESFIND " + id);

        return id;
    }

    public ArrayList<Report> selectReports(int idC) {
        String str = "SELECT * From report Where company_idCompany = '" + idC + "'";
        //"' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Report> listReport = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        for (String[] items: result){
            Report rep = new Report();
            rep.setIdReport(Integer.parseInt(items[0]));
            try {
                rep.setDate(formatter.parse(items[1].trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rep.setIncome(Double.parseDouble(items[2]));
            rep.setIdCompany(Integer.parseInt(items[3]));
            listReport.add(rep);
        }
        return listReport;
    }
    public ArrayList<Report> selectAll() {
        String str = "SELECT * From report";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Report> listReport = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        for (String[] items: result){
            Report rep = new Report();
            rep.setIdReport(Integer.parseInt(items[0]));
            try {
                rep.setDate(formatter.parse(items[1].trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rep.setIncome(Double.parseDouble(items[2]));
            rep.setIdCompany(Integer.parseInt(items[3]));
            listReport.add(rep);
        }
        return listReport;
    }

}
