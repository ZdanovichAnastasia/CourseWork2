package server.services;

import DB.ConcretFactory;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class CreateLists {
    public CreateLists(){}
    public String[][] createListUsers(){
        //System.out.println("Запрос к БД для получения списка пользователей(таблица users), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Users> listUsers = sqlFactory.getUsers().selectAll();
        var size=0;
        for (Users user: listUsers) {
            String str = user.getRole();
            if (!str.equals("admin")) {
                size++;
            }
        }
        String[][] data = new String[size][6];
        int count = 0;
        for (Users user: listUsers){
            String str = user.getRole();
            if(!str.equals("admin")){
                data[count][0] = user.getName();
                data[count][1] = user.getSurname();
                data[count][2] = user.getPhone();
                data[count][3] = user.getLogin();
                data[count][4] = user.getPassword();
                data[count][5] = user.getRole();
                count++;
            }
        }
        return data;
    }
    public String[][] createListWorks(int idUser){
        //System.out.println("Запрос к БД для получения списка пользователей(таблица users), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Users> listUsers = sqlFactory.getUsers().selectAll();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
        ArrayList<Company> listCompanies = sqlFactory.getCompany().selectAll();
        List<Integer> idCs = new ArrayList();
        for(DataCompany dataCompany: listDataCompany) {
            if(dataCompany.getIdUser() == idUser) {
                idCs.add(dataCompany.getIdCompany());
            }
        }
        var size=0;
        for (Users user: listUsers) {
            String str = user.getRole();
            if (str.equals("Финансовый аналитик")) {
                size++;
            }
        }
        String[][] data = new String[size][6];
        int count = 0;
        for (Users user: listUsers){
            String str = user.getRole();
            if(str.equals("Финансовый аналитик")){
                for(int idc: idCs){
                    for(DataCompany company: listDataCompany){
                        if(company.getIdCompany()==idc){
                            if(user.getIdUser()==company.getIdUser()){
                                data[count][0] = user.getName();
                                data[count][1] = user.getSurname();
                                data[count][2] = user.getPhone();
                                data[count][3] = user.getLogin();
                                data[count][4] = user.getPassword();
                                for(Company company1: listCompanies)
                                    if(company1.getIdCompany()==idc)
                                        data[count][5] = company1.getCompanyName();

                                count++;
                            }
                        }
                    }
                };
            }
        }
        String[][] data1 = new String[count][6];
        for(int i = 0; i<count; i++ ){
            data1[i][0]=data[i][0];
            data1[i][1]=data[i][1];
            data1[i][2]=data[i][2];
            data1[i][3]=data[i][3];
            data1[i][4]=data[i][4];
            data1[i][5]=data[i][5];
        }
        return data1;
    }
    public String[][] createListAllCompanies(){
        //System.out.println("Запрос к БД для получения списка компаний(таблица company), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Company> listCompanies = sqlFactory.getCompany().selectAll();
        String[][] data = new String[listCompanies.size()][3];
        int count = 0;
        for (Company company: listCompanies){
            data[count][0] = company.getCompanyName();
            data[count][1] = company.getCountry();
            data[count][2] = company.getIndustry();
            count++;
        }
        return data;
    }
    public String[][] createListCompanies(int idUser){
        //System.out.println("Запрос к БД для получения списка компаний(таблица company), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Company> listCompanies = sqlFactory.getCompany().selectAll();
        ArrayList<DataCompany> listDataC = sqlFactory.getDataCompany().selectAll();
        String[][] data = new String[listCompanies.size()][3];
        int count = 0;
        for(DataCompany dataC: listDataC) {
            if(idUser == dataC.getIdUser()) {
                int idC = dataC.getIdCompany();
                for(Company company: listCompanies){
                    if(idC==company.getIdCompany()){
                        data[count][0] = company.getCompanyName();
                        data[count][1] = company.getCountry();
                        data[count][2] = company.getIndustry();
                        count++;
                    }
                }
            }
        }
        String[][] data1 = new String[count][3];
        for(int i = 0; i<count; i++ ){
            data1[i][0] = data[i][0];
            data1[i][1] = data[i][1];
            data1[i][2] = data[i][2];
        }
        return data1;
    }
    public String[][] createListExpenses(int idUser){
        //System.out.println("Запрос к БД для получения списка затрат(таблица expenses), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Expenses> listExpenses = sqlFactory.getExpenses().selectAll();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
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
    public String[][] createListResources(int idUser){
        //System.out.println("Запрос к БД для получения списка ресурсов(таблица recources), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
        int idC = -1;
        for(DataCompany company: listDataCompany){
            if(idUser == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String[]> result = sqlFactory.getResources().findCostRes(idC);
        int count = 0;
        String[][] data = new String[result.size()][2];
        for(String[] res: result){
            data[count][0] = res[1];
            data[count][1] = res[2];
            count++;
        }
        return data;
    }
    public String[][] createListOperations(int idUser){
        //System.out.println("Запрос к БД для получения списка операций(таблица operation), клиент: " + clientSocket.getInetAddress().toString());
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        ArrayList<Operations> listOperations = sqlFactory.getOperations().selectAll();
        for(DataCompany company: listCompanies){
            if(idUser == company.getIdUser()){
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
    public String[][] createListProduction(int idUser){
        //System.out.println("Запрос к БД для получения списка объектов затрат(таблица production), клиент: " + clientSocket.getInetAddress().toString());
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        ArrayList<Production> listProduction = sqlFactory.getProduction().selectAll();
        for(DataCompany company: listCompanies){
            if(idUser == company.getIdUser()){
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
    public ArrayList<String> createListDataCompanies(int idUser){
        //System.out.println("Запрос к БД для получения списка компаний(таблица company), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Company> listCompanies = sqlFactory.getCompany().selectAll();
        ArrayList<DataCompany> listDataC = sqlFactory.getDataCompany().selectAll();
        ArrayList<String> data= new ArrayList<>();
        for(DataCompany dataC: listDataC){
            if(idUser == dataC.getIdUser()){
                int idC = dataC.getIdCompany();
                for(Company company: listCompanies){
                    if(idC==company.getIdCompany()){
                        data.add(company.getCompanyName());
                    }
                }
            }
        }
        return data;
    }
    public String[] createListNameRes(int idUser){
        //System.out.println("Запрос к БД для получения названий ресурсов(таблица resourses), клиент: " + clientSocket.getInetAddress().toString());
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<DataCompany> listDataCompany = sqlFactory.getDataCompany().selectAll();
        int idC = -1;
        for(DataCompany company: listDataCompany){
            if(idUser == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String[]> result = sqlFactory.getResources().findCostRes(idC);
        int count = 0;
        String[] data = new String[result.size()];
        for(String[] res: result){
            data[count] = res[1];
            count++;
        }
        return data;
    }
    public ArrayList<String> createListNameOp(int idUser){
        //System.out.println("Запрос к БД для получения названий операций(таблица operations), клиент: " + clientSocket.getInetAddress().toString());
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        ArrayList<Operations> listOperations = sqlFactory.getOperations().selectAll();
        for(DataCompany company: listCompanies){
            if(idUser == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<String> data= new ArrayList<>();
        for(Operations operations: listOperations){
            if(idC == operations.getIdCompany()){
                data.add(operations.getOperationName());
            }
        }
        return data;
    }
}
