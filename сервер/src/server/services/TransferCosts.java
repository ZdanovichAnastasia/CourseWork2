package server.services;

import DB.ConcretFactory;
import model.*;

import java.util.ArrayList;

public class TransferCosts {
    public TransferCosts(){}
    public void UniqueSumOp(int idRes, String nameRes, int idC ){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<String[]> selectList = sqlFactory.getResOp().select(nameRes ,String.valueOf(idC));
        int sumdriver = 0;
        for (String[] sel : selectList) {
            sumdriver += Integer.parseInt(sel[1]);
        }
        for (String[] sel : selectList) {
            double unitS = 0;
            try {
                if(sumdriver == 0){
                    throw new MyException("Error");
                }
                else {
                    unitS = (Integer.parseInt(sel[1]) * Double.parseDouble(sel[2])) / sumdriver;
                }
            }
            catch (MyException e){ }
            sqlFactory.getResOp().insertUnitSum(String.valueOf(idRes), sel[0], String.valueOf(unitS));
            sqlFactory.getOperations().getSum(sel[0]);
            UniqueSumObj(Integer.parseInt(sel[0]), sqlFactory.getOperations().findOperations(Integer.parseInt(sel[0]), idC), idC);
        }
    }
    public void UniqueSumObj(int idOp, String nameOp, int idC){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Operations> listOperations = sqlFactory.getOperations().selectAll();
        for(Operations operations: listOperations){
            if(idC == operations.getIdCompany()){
                sqlFactory.getOperations().getSum(String.valueOf(operations.getIdOperation()));
            }
        }
        ArrayList<String[]> selectList = sqlFactory.getOpObj().select(nameOp, String.valueOf(idC));
        int sumdriver = 0;
        for (String[] sel : selectList) {
            sumdriver += Integer.parseInt(sel[1]);
        }
        for (String[] sel : selectList) {
            double unitS = (Integer.parseInt(sel[1]) * Double.parseDouble(sel[2])) / sumdriver;
            sqlFactory.getOpObj().insertUnitSum(String.valueOf(idOp), sel[0], String.valueOf(unitS));
        }
    }

    public String transferOp(ResOp resOp, String nameRes, String[] nameOp, int idUs){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();

        int idC = -1;
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        Operations op = new Operations();
        op.setOperationName(nameOp[0]);
        op.setIdCompany(idC);
        String idOp = sqlFactory.getOperations().find(op);
        resOp.setIdOperations(Integer.parseInt(idOp));
        ArrayList<String[]> res = sqlFactory.getResources().findCostRes(idC);
        for(String[] res1: res){
            if(nameRes.equals(res1[1])){
                resOp.setIdResources(Integer.parseInt(res1[0]));
            }
        }
        if(sqlFactory.getResOp().find(resOp).equals("")){
            sqlFactory.getResOp().insert(resOp);
            UniqueSumOp(resOp.getIdResources(), nameRes, idC);

            return "OK";
        }
        else {
           return "This is already existed";
        }
    }
    public String transferPr(OpObj opObj, String nameOp, String[] nameObj, int idUs){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();

        int idC = -1;
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        Production pr = new Production();
        pr.setProductionName(nameObj[0]);
        pr.setIdCompany(idC);
        String idPr = sqlFactory.getProduction().find(pr);
        opObj.setIdCostObj(Integer.parseInt(idPr));
        Operations operations =new Operations();
        operations.setOperationName(nameOp);
        operations.setIdCompany(idC);
        opObj.setIdOperations(Integer.parseInt(sqlFactory.getOperations().find(operations)));
        if(sqlFactory.getOpObj().find(opObj).equals("")){
            sqlFactory.getOpObj().insert(opObj);
            UniqueSumObj(opObj.getIdOperations(), nameOp, idC);
            return "OK";
        }
        else {
            return "This is already existed";
        }
    }
}
