package server.services;

import DB.ConcretFactory;
import model.*;

import java.io.File;
import java.util.ArrayList;

public class DeletePar {
    private TransferCosts objTransfer = new TransferCosts();
    public DeletePar(){}

    public void deleteCompany(String nameC, int idOwner){
        Company company = new Company();
        company.setCompanyName(nameC);
        ConcretFactory sqlFactory = new ConcretFactory();
        int idC = Integer.parseInt(sqlFactory.getCompany().findCompany(company,2));
        ArrayList<Expenses> expensesList = sqlFactory.getExpenses().selectAll();
        for(Expenses exp: expensesList){
            if(exp.getIdCompany()==idC)
            {
                String[] idO = sqlFactory.getResOp().findRes(sqlFactory.getResources().findIdExp(String.valueOf(exp.getIdExpenses())), 2);
                for(String id: idO){
                    sqlFactory.getResOp().deleteO(Integer.parseInt(id));
                }
                //sqlFactory.getResources().delete(Integer.parseInt(sqlFactory.getResources().findIdExp(String.valueOf(exp.getIdExpenses()))));
                sqlFactory.getResources().delete(sqlFactory.getResources().findIdExp(String.valueOf(exp.getIdExpenses())));
                sqlFactory.getExpenses().delete(exp.getExpensesName(), idC);
            }
        }
        ArrayList<Operations> operationsList = sqlFactory.getOperations().selectAll();
        for(Operations op: operationsList){
            if(op.getIdCompany() == idC){
                sqlFactory.getOpObj().deleteO(op.getIdOperation());
                sqlFactory.getOperations().delete(op.getOperationName(), idC);
            }
        }
        ArrayList<Production> productionList = sqlFactory.getProduction().selectAll();
        for(Production production: productionList){
            if(production.getIdCompany() == idC){
                sqlFactory.getProduction().delete(String.valueOf(production.getIdProduction()));
            }
        }
        ArrayList<DataCompany> dataCompanyList = sqlFactory.getDataCompany().selectAll();
        for(DataCompany dataCompany: dataCompanyList){
            if(dataCompany.getIdCompany() == idC){
                //sqlFactory.getDataCompany().deleteC(idC);
                sqlFactory.getDataCompany().delete(String.valueOf(idC));
                if(dataCompany.getIdUser() != idOwner) {
                    File file1=new File("D:/УНИВЕР/курсач 3(1)/Отчеты/" + sqlFactory.getUsers().findLoginUser(dataCompany.getIdUser()) + "+" + nameC + ".docx");
                    if(file1.exists()){
                        file1.delete();
                    }
                    //sqlFactory.getUsers().deleteId(dataCompany.getIdUser());
                    sqlFactory.getUsers().delete(String.valueOf(dataCompany.getIdUser()));
                }

            }
        }
        ArrayList<Report> reportList = sqlFactory.getReport().selectAll();
        for(Report report: reportList){
            if(report.getIdCompany() == idC){
                sqlFactory.getReport().delete(String.valueOf(report.getIdReport()));
            }
        }

        sqlFactory.getCompany().delete(nameC);
    }
    public void delOwner(Users user){
        ConcretFactory sqlFactory = new ConcretFactory();
        int idOwner = sqlFactory.getUsers().selectUsers(user).getIdUser();
        ArrayList<DataCompany> dataCompanyList = sqlFactory.getDataCompany().selectAll();
        for(DataCompany dataCompany: dataCompanyList){
            if(dataCompany.getIdUser() == idOwner){
                deleteCompany(sqlFactory.getCompany().findNameCompany(dataCompany.getIdCompany()), idOwner);
            }
        }
        sqlFactory.getUsers().deleteLog(user.getLogin());
    }
    public void delWorkers(Users user){
        ConcretFactory sqlFactory = new ConcretFactory();
        Users user1 = sqlFactory.getUsers().selectUsers(user);
        sqlFactory.getDataCompany().delete(String.valueOf(user1.getIdUser()));
        sqlFactory.getUsers().deleteLog(user1.getLogin());
    }
    public void delExp(String nameExp, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        Expenses expenses = new Expenses();
        expenses.setExpensesName(nameExp);
        expenses.setIdCompany(idC);
        //String idExp = sqlFactory.getExpenses().findExpenses(expenses);
        String idExp = sqlFactory.getExpenses().find(expenses);
        String idRes = sqlFactory.getResources().findIdExp(idExp);
        String[] idO = sqlFactory.getResOp().findRes(idRes, 2);
        ArrayList<String[]> idOp = new ArrayList<>();
        for(String i:idO){
            String[] idn = new String[2];
            idn[0] = i;
            idn[1] = sqlFactory.getOperations().findOperations(Integer.parseInt(i), idC);
            idOp.add(idn);
        }
        if(!idRes.equals(""))
        {
            //sqlFactory.getResOp().deleteR(Integer.parseInt(idRes));
            //sqlFactory.getResources().delete(Integer.parseInt(idRes));
            sqlFactory.getResOp().delete(idRes);
            sqlFactory.getResources().delete(idRes);
        }
        sqlFactory.getExpenses().delete(nameExp, idC);
        for(String[] op: idOp){
            objTransfer.UniqueSumObj(Integer.parseInt(op[0]), op[1], idC);
        }
    }
    public void delRes(String nameRes, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String[]> resList = sqlFactory.getResources().findRes(idUs);
        ArrayList<Integer> idRes = new ArrayList<>();
        ArrayList<String[]> idOp = new ArrayList<>();
        for(String[] res: resList){
            if(res[1].equals(nameRes)) {
                idRes.add(Integer.parseInt(res[0]));
                String[] id = sqlFactory.getResOp().findRes(res[0], 2);
                for(String i:id){
                    String[] idn = new String[2];
                    idn[0] = i;
                    idn[1] = sqlFactory.getOperations().findOperations(Integer.parseInt(i), idC);
                    idOp.add(idn);
                }
                //sqlFactory.getResOp().deleteR(Integer.parseInt(res[0]));
                //sqlFactory.getResources().delete(Integer.parseInt(res[0]));
                sqlFactory.getResOp().delete(res[0]);
                sqlFactory.getResources().delete(res[0]);
            }
        }
        for(String[] op: idOp){
            objTransfer.UniqueSumObj(Integer.parseInt(op[0]), op[1], idC);
        }

    }
    public void delOp(String nameOp, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        Operations operations = new Operations();
        operations.setOperationName(nameOp);
        operations.setIdCompany(idC);
        //String[] idRes = sqlFactory.getResOp().findRes(sqlFactory.getOperations().findOperations(operations, idC), 1);
        //sqlFactory.getResOp().deleteO(Integer.parseInt(sqlFactory.getOperations().findOperations(operations, idC)));
        //sqlFactory.getOpObj().deleteO(Integer.parseInt(sqlFactory.getOperations().findOperations(operations, idC)));
        String[] idRes = sqlFactory.getResOp().findRes(sqlFactory.getOperations().find(operations), 1);
        sqlFactory.getResOp().deleteO(Integer.parseInt(sqlFactory.getOperations().find(operations)));
        sqlFactory.getOpObj().deleteO(Integer.parseInt(sqlFactory.getOperations().find(operations)));
        sqlFactory.getOperations().delete(nameOp, idC);
        for(String id: idRes){
            objTransfer.UniqueSumOp(Integer.parseInt(id), sqlFactory.getResources().findResources(id), idC);
        }
    }
    public void delPr(String namePr, int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        Production pr = new Production();
        pr.setProductionName(namePr);
        pr.setIdCompany(idC);
        //String[] idOp = sqlFactory.getOpObj().findOp(sqlFactory.getProduction().findProduction(pr, idC));
        //sqlFactory.getOpObj().deleteOb(sqlFactory.getProduction().findProduction(pr, idC));
        String[] idOp = sqlFactory.getOpObj().findOp(sqlFactory.getProduction().find(pr));
        //sqlFactory.getOpObj().deleteOb(sqlFactory.getProduction().find(pr));
        sqlFactory.getOpObj().delete(sqlFactory.getProduction().find(pr));
        sqlFactory.getProduction().delete(namePr, idC);
        for(String id: idOp){
            objTransfer.UniqueSumObj(Integer.parseInt(id), sqlFactory.getOperations().findOperations(Integer.parseInt(id), idC),idC);
        }
    }
}
