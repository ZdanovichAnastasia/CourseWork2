package server.services;

import DB.ConcretFactory;
import model.*;

import java.util.ArrayList;

public class AddPar {
    public AddPar(){}
    public String addnewCompany(Company company, int idUser){
        DataCompany dataCompany = new DataCompany();
        dataCompany.setIdUser(idUser);
        ConcretFactory sqlFactory = new ConcretFactory();

        if (sqlFactory.getCompany().findCompany(company, 1).equals("")) {
            sqlFactory.getCompany().insert(company);
            String comp = sqlFactory.getCompany().findCompany(company, 2);
            dataCompany.setIdCompany(Integer.parseInt(comp));
            sqlFactory.getDataCompany().insert(dataCompany);
            return "OK";
        }
        else{
            return "This is already existed";
        }
    }
    public String addUserCompany(Users user, Company company){
        DataCompany dataCompany = new DataCompany();
        ConcretFactory sqlFactory = new ConcretFactory();

        //if (sqlFactory.getUsers().findUser(user).equals("")) {
        if (sqlFactory.getUsers().find(user).equals("")) {
            sqlFactory.getUsers().insert(user);
            String idC = sqlFactory.getCompany().findCompany(company, 2);
            dataCompany.setIdCompany(Integer.parseInt(idC));
            Users us = sqlFactory.getUsers().selectUsers(user);
            dataCompany.setIdUser(us.getIdUser());
            sqlFactory.getDataCompany().insert(dataCompany);
            return "OK";
        }
        else{
            return  "This is already existed";
        }
    }
    public String addnewExp(Expenses exp, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        exp.setIdCompany(idC);

        //if (sqlFactory.getExpenses().findExpenses(exp).equals("")) {
        if (sqlFactory.getExpenses().find(exp).equals("")) {
            sqlFactory.getExpenses().insert(exp);
            return "OK";
        }
        else{
            return "This is already existed";
        }
    }
    public String addnewRes(int fl, Resources res, int idUs, String[] sel){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        ArrayList<Expenses> listExpenses = sqlFactory.getExpenses().selectAll();

        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        int flag = 0;
        if (fl == 0){
            for (Expenses expenses : listExpenses) {
                if (idC == expenses.getIdCompany()) {
                    if (sqlFactory.getResources().findResources(res, expenses.getIdExpenses()).equals("")) {
                    } else flag++;
                }
            }
        }

        if (flag == 0) {
            ArrayList<String> mas = getRes(idUs, sel, res);
            if(mas.size() == 0){
                return "OK";
            }
            else{
                return "Exp is already added";
            }
        }
        else{
            return "This is already existed";
        }
    }
    public ArrayList<String> getRes(int idUs, String[] sel, Resources res){
        ConcretFactory sqlFactory = new ConcretFactory();
        int idC = -1;
        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        ArrayList<Expenses> listExpenses = sqlFactory.getExpenses().selectAll();

        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String> mas = new ArrayList<>();
        for (Expenses expenses : listExpenses) {
            if (idC == expenses.getIdCompany()) {
                for(var i = 0; i< sel.length; i++){
                    if(sel[i].equals(expenses.getExpensesName())){
                        if(sqlFactory.getResources().findIdExp(String.valueOf(expenses.getIdExpenses())).equals("")){
                            Resources res1 = res;
                            res1.setExpId(expenses.getIdExpenses());
                            res1.setResourceCost(Double.parseDouble(expenses.getSumOfExpenses()));
                            sqlFactory.getResources().insert(res1);
                        }
                        else{
                            mas.add(sel[i]);
                        }

                    }
                }
            }
        }
        return mas;
    }
    public String addnewOp(Operations op, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        op.setIdCompany(idC);

        //if (sqlFactory.getOperations().findOperations(op, idC).equals("")) {
        if (sqlFactory.getOperations().find(op).equals("")) {
            sqlFactory.getOperations().insert(op);
            return "OK";
        }
        else{
            return "This is already existed";
        }
    }
    public String addnewPr(Production pr, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        pr.setIdCompany(idC);

        //if (sqlFactory.getProduction().findProduction(pr, idC).equals("")) {
        if (sqlFactory.getProduction().find(pr).equals("")) {
            sqlFactory.getProduction().insert(pr);
            return "OK";
        }
        else{
            return  "This is already existed";
        }
    }
}
