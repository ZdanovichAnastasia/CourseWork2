package DB;

import model.ResOp;

import java.util.ArrayList;

public class TableResOp implements TablesInterface{
    private static TableResOp instance;
    private Connection Connection;

    private TableResOp() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableResOp getInstance() {
        if (instance == null) {
            instance = new TableResOp();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS res_op (\n" +
                "  idres INT NOT NULL,\n" +
                "  idop INT NOT NULL,\n" +
                "  driver_op INT NOT NULL,\n" +
                "  unitsumop DOUBLE NOT NULL,\n" +
                "  CONSTRAINT op\n" +
                "    FOREIGN KEY (idop)\n" +
                "    REFERENCES operation (operation_id),\n" +
                "  CONSTRAINT res\n" +
                "    FOREIGN KEY (idres)\n" +
                "    REFERENCES resources (resources_id));";
        Connection.execute(str);
    };

    @Override
    public String find(Object obj) {
        ResOp resOp = (ResOp)obj;
        String str = "SELECT * From res_op Where idop = '" + resOp.getIdOperations()+ "' and idres = '" +resOp.getIdResources() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String id = "";
        for (String[] item: result)
            id = item[0];
        return id;
    }

    public String[] findRes(String  id, int flag) {
        String str = "";
        if(flag == 1){
            str = "SELECT idres From res_op Where idop = '" + id + "'";
        }
        else {
            str = "SELECT idop From res_op Where idres = '" + id + "'";
        }
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String[] idmas = new String[result.size()];
        int i = 0;
        for (String[] item: result) {
            //System.out.println("idmas "+ item[0]);
            idmas[i] = item[0];
            i++;
        }
        return idmas;
    }

    @Override
    public void insert(Object obj) {
        ResOp resOp = (ResOp)obj;
        String str = "INSERT INTO res_op (idres, idop, driver_op, unitsumop) VALUES('" + resOp.getIdResources() + "', '"+ resOp.getIdOperations()
                + "', '"+ resOp.getDriverOp()+ "', '" +resOp.getUnitSumOp()+ "')";
        Connection.execute(str);
    }

    public ArrayList<ResOp> selectAll() {
        String str = "SELECT * From res_op";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<ResOp> listResOp = new ArrayList<>();
        for (String[] items: result){
            ResOp resOp = new ResOp();
            resOp.setIdResources(Integer.parseInt(items[0]));
            resOp.setIdOperations(Integer.parseInt(items[1]));
            resOp.setDriverOp(Integer.parseInt(items[2]));
            resOp.setUnitSumOp(Double.parseDouble(items[3]));
            listResOp.add(resOp);
        }
        return listResOp;
    }
    public ArrayList<String[]> select(String resName, String idC) {
        String str = "select idop, driver_op from kurs.expenses ex left join  kurs.resources r on r.expenses_id = ex.expenses_id left join kurs.res_op ro on ro.idres = r.resources_id where company_idCompany = '" + idC + "'  and resources_name = '" + resName + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String str1 = "select sum(resources_cost) from kurs.expenses ex left join  kurs.resources r on r.expenses_id = ex.expenses_id  where company_idCompany = '" + idC + "'  and resources_name = '" + resName + "'";
        ArrayList<String[]> result1 = Connection.getArrayResult(str1);
        String sum = result1.get(0)[0];
        ArrayList<String[]> listResOp = new ArrayList<>();
        for (String[] items: result){
            String[] mas = new String[3];
            mas[0] = items[0];
            mas[1] = items[1];
            mas[2] = sum;
            if(mas[0]!=null && mas[1]!=null) {
                listResOp.add(mas);
            }
        }
        return listResOp;
    }
    public void insertUnitSum(String idres, String idop, String unitSum){
        String str = "UPDATE kurs.res_op SET unitsumop='" + unitSum + "' WHERE idres = '" + idres + "' and idop = '" + idop + "'";
        Connection.execute(str);
    }

    @Override
    public void delete(String id) {
        String str = "DELETE FROM res_op WHERE  idres = '" + id+"'";
        Connection.execute(str);
    }
    public void deleteO(int id) {
        String str = "DELETE FROM res_op WHERE  idop = '" + id+"'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }
}
