package server.services;

import DB.ConcretFactory;
import model.*;

import java.util.ArrayList;

public class EditPar {
    private TransferCosts objTransfer = new TransferCosts();
    public EditPar(){}
    public void editWorker(Users user, String login){
        ConcretFactory sqlFactory = new ConcretFactory();
        sqlFactory.getUsers().update(user, login);
    }
    public String editExp(Expenses exp, int idUs, String nameExp){
        Expenses exp1 = new Expenses();
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        exp.setIdCompany(idC);
        exp1.setExpensesName(nameExp);
        exp1.setIdCompany(idC);
        //String idExp = sqlFactory.getExpenses().findExpenses(exp1);
        String idExp = sqlFactory.getExpenses().find(exp1);

        //if (sqlFactory.getExpenses().findExpenses(exp).equals("") || sqlFactory.getExpenses().findExpenses(exp).equals(idExp)) {
        if (sqlFactory.getExpenses().find(exp).equals("") || sqlFactory.getExpenses().find(exp).equals(idExp)) {
            exp.setIdExpenses(Integer.parseInt(idExp));
            System.out.println(exp);
            sqlFactory.getExpenses().update(exp);
            ArrayList<String[]> res = sqlFactory.getResources().update(exp);
            for(String[] resource: res){
                objTransfer.UniqueSumOp(Integer.parseInt(resource[0]), resource[1], idC);
            }
            return "OK";
        }
        else{
           return "This is already existed";
        }
    }
    public String editRes(Resources res, int idUs, String nameRes){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<String[]> resc = sqlFactory.getResources().findRes(idUs);
        int flag = 0;
        for (String[] resource: resc){
            if(!resource[1].equals(nameRes) && resource[1].equals(res.getResourceName())){
                flag++;
            }
        }
        if (flag == 0) {
            sqlFactory.getResources().update(idUs, nameRes, res);
            return "OK";
        }
        else{
            return "This is already existed";
        }
    }
    public String editOp(Operations op, int idUs, String nameOp){
        Operations op1 = new Operations();
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        op.setIdCompany(idC);
        op1.setOperationName(nameOp);
        op1.setIdCompany(idC);
        //String idOp = sqlFactory.getOperations().findOperations(op1, idC);
        String idOp = sqlFactory.getOperations().find(op1);

        //if (sqlFactory.getOperations().findOperations(op, idC).equals("") || sqlFactory.getOperations().findOperations(op, idC).equals(idOp)) {
        if (sqlFactory.getOperations().find(op).equals("") || sqlFactory.getOperations().find(op).equals(idOp)) {
            op.setIdOperation(Integer.parseInt(idOp));
            sqlFactory.getOperations().update(op);
            return "OK";
        }
        else{
            return "This is already existed";
        }
    }
    public String editPr(Production pr, int idUs, String namePr){
        Production pr1 = new Production();
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        pr.setIdCompany(idC);
        pr1.setProductionName(namePr);
        pr1.setIdCompany(idC);
        //String idPr = sqlFactory.getProduction().findProduction(pr1, idC);
        String idPr = sqlFactory.getProduction().find(pr1);

        //if (sqlFactory.getProduction().findProduction(pr, idC).equals("") || sqlFactory.getProduction().findProduction(pr, idC).equals(idPr)) {
        if (sqlFactory.getProduction().find(pr).equals("") || sqlFactory.getProduction().find(pr).equals(idPr)) {
            pr.setIdProduction(Integer.parseInt(idPr));
            sqlFactory.getProduction().update(pr);
            return "OK";
        }
        else{
           return "This is already existed";
        }
    }
}
