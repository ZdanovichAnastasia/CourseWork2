package DB;

import model.OpObj;

import java.util.ArrayList;

public class TableOpObj implements TablesInterface{
    private static TableOpObj instance;
    private Connection Connection;

    private TableOpObj() {
        Connection = Connection.getInstance();
    }

    public static synchronized TableOpObj getInstance() {
        if (instance == null) {
            instance = new TableOpObj();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS op_obj (\n" +
                "  idop INT NULL DEFAULT NULL,\n" +
                "  idobj INT NULL DEFAULT NULL,\n" +
                "  driver_obj INT NULL DEFAULT NULL,\n" +
                "  unitsumobj DOUBLE NULL DEFAULT NULL,\n" +
                "  CONSTRAINT obj\n" +
                "  FOREIGN KEY (idobj)\n" +
                "  REFERENCES production (production_id),\n" +
                "  CONSTRAINT oper`\n" +
                "  FOREIGN KEY (idop)\n" +
                "  REFERENCES operation (operation_id));";
        Connection.execute(str);
    };
    @Override
    public String find(Object obj) {
        OpObj opObj = (OpObj)obj;
        String str = "SELECT * From op_obj Where idobj = '" + opObj.getIdCostObj()+ "' and idop = '"+ opObj.getIdOperations()+"'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String id = "";
        for (String[] item: result)
            id = item[1];
        return id;
    }

    public String[] findOp(String idPr) {
        String str = "SELECT * From op_obj Where idobj = '" + idPr+ "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String[] idCompany = new String[result.size()];
        int i = 0;
        for (String[] item: result) {
            idCompany[i] = item[0];
            i++;
        }
        return idCompany;
    }
    @Override
    public void insert(Object obj) {
        OpObj opObj = (OpObj)obj;
        String str = "INSERT INTO op_obj (idop, idobj, driver_obj, unitsumobj) VALUES('" + opObj.getIdOperations() + "', '"+ opObj.getIdCostObj()
                + "', '"+ opObj.getDriverObj()+ "', '" +opObj.getUnitSumObj()+ "')";
        Connection.execute(str);
    }

    public ArrayList<OpObj> selectAll() {
        String str = "SELECT * From op_obj";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<OpObj> listOpObj = new ArrayList<>();
        for (String[] items: result){
            OpObj opObj = new OpObj();
            opObj.setIdOperations(Integer.parseInt(items[0]));
            opObj.setIdCostObj(Integer.parseInt(items[1]));
            opObj.setDriverObj(Integer.parseInt(items[2]));
            opObj.setUnitSumObj(Double.parseDouble(items[3]));
            listOpObj.add(opObj);
        }
        return listOpObj;
    }

    public ArrayList<String[]> select(String opName, String idC) {
        String str = "select idobj, driver_obj, operation_cost from kurs.operation op left join kurs.op_obj oo on oo.idop = op.operation_id where company_idCompany = '" + idC + "'  and operation_name = '" + opName + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<String[]> listResOp = new ArrayList<>();
        for (String[] items: result){
            String[] mas = new String[3];
            mas[0] = items[0];
            mas[1] = items[1];
            mas[2] = items[2];
            if(mas[0] != null && mas[1] != null && mas[2] != null) {
                listResOp.add(mas);
            }
        }
        return listResOp;
    }
    public void insertUnitSum(String idop, String idobj, String unitSum){
        String str = "UPDATE kurs.op_obj SET unitsumobj='" + unitSum + "' WHERE idobj = '" + idobj + "' and idop = '" + idop + "'";
        Connection.execute(str);
    }
    @Override
    public void delete(String id) {
        String str = "DELETE FROM op_obj WHERE  idobj = '" + id+"'";
        Connection.execute(str);
    }

    public void deleteO(int id) {
        String str = "DELETE FROM op_obj WHERE  idop = '" + id+"'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }
}

