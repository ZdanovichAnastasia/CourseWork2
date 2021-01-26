package DB;

import model.DataCompany;

import java.util.ArrayList;

public class TableDataCompany implements TablesInterface{
    private static TableDataCompany instance;
    private Connection Connection;

    private TableDataCompany() {
        Connection = Connection.getInstance();
    }
    public static synchronized TableDataCompany getInstance() {
        if (instance == null) {
            instance = new TableDataCompany();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS datacompany (\n" +
                "  idcompany INT NOT NULL,\n" +
                "  iduser INT NOT NULL,\n" +
                "  CONSTRAINT idcompany\n" +
                "  FOREIGN KEY (idcompany)\n" +
                "  REFERENCES company (idCompany),\n" +
                "  CONSTRAINT iduser\n" +
                "  FOREIGN KEY (iduser)\n" +
                "  REFERENCES users (idUsers));";
        Connection.execute(str);
    };
    @Override
    public String find(Object obj) {
        DataCompany dataCompany = (DataCompany)obj;
        String str = "SELECT * From datacompany Where idcompany = '" + dataCompany.getIdCompany()+ "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String idUser = result.get(0)[1];
        return idUser;
    }


    @Override
    public void insert(Object obj) {
        DataCompany dataCompany = (DataCompany)obj;
        String str = "INSERT INTO datacompany (idcompany, iduser) VALUES('" + dataCompany.getIdCompany() + "', '" +dataCompany.getIdUser()+ "')";
        Connection.execute(str);
    }

    public DataCompany selectCompanies(DataCompany obj) {
        String str = "SELECT * From datacompany Where idcompany = '" + obj.getIdCompany() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        DataCompany datacompany = new DataCompany();
        for (String[] items: result){
            datacompany.setIdCompany(Integer.parseInt(items[0]));
            datacompany.setIdUser(Integer.parseInt(items[1]));
        }
        return datacompany;
    }
    public ArrayList<DataCompany> selectAll() {
        String str = "SELECT * From datacompany";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<DataCompany> listCompanies = new ArrayList<>();
        for (String[] items: result){
            DataCompany comp = new DataCompany();
            comp.setIdCompany(Integer.parseInt(items[0]));
            comp.setIdUser(Integer.parseInt(items[1]));
            listCompanies.add(comp);
        }
        return listCompanies;
    }
    @Override
    public void delete(String id) {
        String str = "DELETE FROM datacompany WHERE  idcompany = '" + id+"'";
        Connection.execute(str);
    }
    public void deleteU(int id) {
        String str = "DELETE FROM datacompany WHERE  iduser = '" + id+"'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }
}

/*implements InterfaceDataCompany{
    private static TableDataCompany instance;
    private Connection Connection;

    private TableDataCompany() {
        Connection = Connection.getInstance();
    }
    public static synchronized TableDataCompany getInstance() {
        if (instance == null) {
            instance = new TableDataCompany();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS datacompany (\n" +
                "  idcompany INT NOT NULL,\n" +
                "  iduser INT NOT NULL,\n" +
                "  CONSTRAINT idcompany\n" +
                "  FOREIGN KEY (idcompany)\n" +
                "  REFERENCES company (idCompany),\n" +
                "  CONSTRAINT iduser\n" +
                "  FOREIGN KEY (iduser)\n" +
                "  REFERENCES users (idUsers));";
        Connection.execute(str);
    };
    @Override
    public String findDataCompany(DataCompany obj) {
        String str = "SELECT * From datacompany Where idcompany = '" + obj.getIdCompany()+ "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String idUser = result.get(0)[1];
        return idUser;
    }
    @Override
    public String findDataUser(DataCompany obj) {
        String str = "SELECT * From datacompany Where iduser = '" + obj.getIdUser()+ "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String idCompany = "";
        for (String[] item: result)
            idCompany = item[0];
        return idCompany;
    }


    @Override
    public void insert(DataCompany obj) {
        String str = "INSERT INTO datacompany (idcompany, iduser) VALUES('" + obj.getIdCompany() + "', '" +obj.getIdUser()+ "')";
        Connection.execute(str);
    }

    @Override
    public DataCompany selectCompanies(DataCompany obj) {
        String str = "SELECT * From datacompany Where idcompany = '" + obj.getIdCompany() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        DataCompany datacompany = new DataCompany();
        for (String[] items: result){
            datacompany.setIdCompany(Integer.parseInt(items[0]));
            datacompany.setIdUser(Integer.parseInt(items[1]));
        }
        return datacompany;
    }
    @Override
    public ArrayList<DataCompany> selectAll() {
        String str = "SELECT * From datacompany";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<DataCompany> listCompanies = new ArrayList<>();
        for (String[] items: result){
            DataCompany comp = new DataCompany();
            comp.setIdCompany(Integer.parseInt(items[0]));
            comp.setIdUser(Integer.parseInt(items[1]));
            listCompanies.add(comp);
        }
        return listCompanies;
    }

    @Override
    public void deleteC(int id) {
        String str = "DELETE FROM datacompany WHERE  idcompany = '" + id+"'";
        Connection.execute(str);
    }
    @Override
    public void deleteU(int id) {
        String str = "DELETE FROM datacompany WHERE  iduser = '" + id+"'";
        Connection.execute(str);
    }
}*/
