package server.services;

import DB.ConcretFactory;
import model.DataCompany;
import model.Expenses;
import model.Operations;
import model.Production;

import java.util.ArrayList;

public class Sorts {
    public Sorts(){}
    public String[][] sortExpList(int idUser, String field) {
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
        ArrayList<Expenses> listExpenses = sqlFactory.getExpenses().sort(field);
        int idC = -1;
        for(DataCompany company: listDataCompany){
            if(idUser == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        int size=0;
        for (Expenses exp: listExpenses){
            if(exp.getIdCompany()==idC){
                size++;
            }
        }
        int count = 0;
        String[][] data = new String[size][4];
        for (Expenses exp: listExpenses){

            if(exp.getIdCompany()==idC) {
                data[count][0] = exp.getExpensesName();
                data[count][1] = exp.getExpensesGroup();
                data[count][2] = String.valueOf(exp.getSumOfExpenses());
                data[count][3] = exp.getExpensesType();
                count++;
            }
        }
        return data;
    }
    public String[][] sortNameResList(int idUser) {
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
        int idC = -1;
        for(DataCompany company: listDataCompany){
            if(idUser == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String[]> result = sqlFactory.getResources().sortName(idC);
        int count = 0;
        String[][] data = new String[result.size()][2];
        for(String[] res: result){
            data[count][0] = res[1];
            data[count][1] = res[2];
            count++;
        }
        return data;
    }
    public String[][] sortSumResList(int idUser){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
        int idC = -1;
        for(DataCompany company: listDataCompany){
            if(idUser == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String[]> result = sqlFactory.getResources().findCostRes(idC);
        for(int i = result.size()-1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(Double.parseDouble(result.get(j)[2])>Double.parseDouble(result.get(j+1)[2])){
                    String tmp = result.get(j)[2];
                    result.get(j)[2] = result.get(j+1)[2];
                    result.get(j+1)[2] = tmp;

                }
            }
        }
        int count = 0;
        String[][] data = new String[result.size()][2];
        for (String[] res : result) {
            data[count][0] = res[1];
            data[count][1] = res[2];
            count++;
        }
        return data;
    }
    public String[][] sortOpList(int idUs, String field){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Operations> listOperations = sqlFactory.getOperations().sort(field);
        int idC = -1;

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        int size = 0;
        for(Operations operations: listOperations){
            if(idC == operations.getIdCompany()){
                size++;
            }
        }
        int count = 0;
        String[][] data = new String[size][3];
        for(Operations operations: listOperations){
            if(idC == operations.getIdCompany()){
                data[count][0] = operations.getOperationName();
                data[count][1] = operations.getOperationType();
                double sum = sqlFactory.getOperations().getSum(String.valueOf(operations.getIdOperation()));
                data[count][2] = String.valueOf(sum);
                //data[count][2] = String.valueOf(operations.getOperationCost());
                count++;
            }
        }
        return data;
    }
    public String[][] sortPrList(int idUs, String field){
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        ArrayList<Production> listProduction = sqlFactory.getProduction().sort(field);
        int idC = -1;
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        int size = 0;
        for(Production production: listProduction){
            if(idC == production.getIdCompany()){
                size++;
            }
        }
        int count = 0;
        String[][] data = new String[size][4];
        for(Production production: listProduction){
            if(idC == production.getIdCompany()){
                data[count][0] = production.getProductionName();
                data[count][1] = String.valueOf(production.getProductionCol());
                data[count][2] = String.valueOf(production.getProfit());
                double sum = sqlFactory.getProduction().getSum(String.valueOf(production.getIdProduction()));
                data[count][3] = String.valueOf(sum);
                count++;
            }
        }
        return data;
    }
}
