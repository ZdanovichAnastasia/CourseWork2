package server;
import DB.ConcretFactory;
import java.io.*;
import model.*;
import server.services.*;

import java.net.Socket;

public class Worker implements Runnable {
    private CreateLists objCreateLists = new CreateLists();
    private AddPar objAddPar = new AddPar();
    private DeletePar objDelPar = new DeletePar();
    private EditPar objEditPar = new EditPar();
    private TransferCosts objTransfer = new TransferCosts();
    private Sorts objSorts = new Sorts();
    private Results objResults = new Results();
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            sois = new ObjectInputStream(clientSocket.getInputStream());
            soos = new ObjectOutputStream(clientSocket.getOutputStream());
            GenerateBD();
            while (true) {
                String choice = sois.readObject().toString();
                switch(choice){
                    case "enter":{
                        Users user = (Users)sois.readObject();
                        ConcretFactory sqlFactory = new ConcretFactory();
                        String status = sqlFactory.getUsers().find(user);
                        if (status == "")
                            soos.writeObject("error");
                        else {
                            soos.writeObject("ok");
                            soos.writeObject(status);
                        }
                    }break;
                    case "registrationUser":{
                        System.out.println("Запрос к БД на проверку пользователя(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        Users user = (Users)sois.readObject();

                        ConcretFactory sqlFactory = new ConcretFactory();

                        if (sqlFactory.getUsers().find(user).equals("")) {
                            soos.writeObject("OK");
                            sqlFactory.getUsers().insert(user);
                        }
                        else{
                            soos.writeObject("This user is already existed");
                        }
                    }break;

                    case "createListUsers":{
                        System.out.println("Запрос к БД для получения списка пользователей(таблица users), клиент: " + clientSocket.getInetAddress().toString());
                        soos.writeObject(objCreateLists.createListUsers());
                    }break;
                    case "createListWorks":{
                        System.out.println("Запрос к БД для получения списка пользователей(таблица users), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListWorks(idUser));
                    }break;
                    case "createListAllCompanies":{
                        soos.writeObject(objCreateLists.createListAllCompanies());
                        System.out.println("Запрос к БД для получения списка компаний(таблица company), клиент: " + clientSocket.getInetAddress().toString());
                    }break;
                    case "createListCompanies":{
                        System.out.println("Запрос к БД для получения списка компаний(таблица company), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListCompanies(idUser));
                    }break;
                    case "createListExpenses":{
                        System.out.println("Запрос к БД для получения списка затрат(таблица expenses), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListExpenses(idUser));
                    }break;
                    case "createListResources":{
                        System.out.println("Запрос к БД для получения списка ресурсов(таблица recources), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListResources(idUser));
                    }break;
                    case "createListOperations":{
                        System.out.println("Запрос к БД для получения списка операций(таблица operation), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListOperations(idUser));
                    }break;
                    case "createListProduction":{
                        System.out.println("Запрос к БД для получения списка объектов затрат(таблица production), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListProduction(idUser));
                    }break;
                    case "createListDataCompanies":{
                        System.out.println("Запрос к БД для получения списка компаний(таблица company), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListDataCompanies(idUser));
                    }break;
                    case "createListNameRes":{
                        System.out.println("Запрос к БД для получения названий ресурсов(таблица resourses), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListNameRes(idUser));
                    }break;
                    case "createListNameOp":{
                        System.out.println("Запрос к БД для получения названий операций(таблица operations), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objCreateLists.createListNameOp(idUser));
                    }break;

                    case "sortExpList":{
                        System.out.println("Запрос к БД для получения отсортированного списка затрат(таблица expenses), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        String field = (String) sois.readObject();
                        soos.writeObject(objSorts.sortExpList(idUser, field));
                    }break;
                    case "sortNameResList":{
                        System.out.println("Запрос к БД для получения отсортированного списка ресурсов(таблица recources), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objSorts.sortNameResList(idUser));
                    }break;
                    case "sortSumResList":{
                        System.out.println("Запрос к БД для получения отсортированного списка ресурсов(таблица recources), клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objSorts.sortSumResList(idUser));
                    }break;
                    case "sortOpList":{
                        System.out.println("Запрос к БД для получения отсортированного списка операций(таблица operation), клиент: " + clientSocket.getInetAddress().toString());
                        int idUs = (int) sois.readObject();
                        String field = (String) sois.readObject();
                        soos.writeObject(objSorts.sortOpList(idUs, field));
                    }break;
                    case "sortPrList":{
                        System.out.println("Запрос к БД для получения отсортированного списка объектов затрат(таблица production), клиент: " + clientSocket.getInetAddress().toString());
                        int idUs = (int) sois.readObject();
                        String field = (String) sois.readObject();
                        soos.writeObject(objSorts.sortPrList(idUs, field));
                    }break;

                    case "addnewCompany":{
                        System.out.println("Запрос к БД на добавление данных о компании(таблица company), клиент: " + clientSocket.getInetAddress().toString());
                        Company company = (Company) sois.readObject();
                        int idUser = (int) sois.readObject();
                        soos.writeObject(objAddPar.addnewCompany(company, idUser));
                    }break;
                    case "addUserCompany":{
                        System.out.println("Запрос к БД на добавление данных о пользователе(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        Users user = (Users)sois.readObject();
                        Company company = (Company) sois.readObject();
                        soos.writeObject(objAddPar.addUserCompany(user, company));
                    }break;
                    case "addnewExp":{
                        System.out.println("Запрос к БД на добавление затрат (таблица Expenses), клиент: " + clientSocket.getInetAddress().toString());
                        Expenses exp = (Expenses) sois.readObject();
                        int idUs = (int) sois.readObject();
                        soos.writeObject(objAddPar.addnewExp(exp, idUs));
                    }break;
                    case "addnewRes":{
                        System.out.println("Запрос к БД на добавление ресурсов (таблица Resources), клиент: " + clientSocket.getInetAddress().toString());
                        int fl = Integer.parseInt((String) sois.readObject());
                        Resources res = (Resources) sois.readObject();
                        int idUs = (int) sois.readObject();
                        String [] sel = (String[]) sois.readObject();

                        String mess = objAddPar.addnewRes(fl, res, idUs, sel);
                        soos.writeObject(mess);
                        if(mess.equals("Exp is already added")){
                            soos.writeObject(objAddPar.getRes(idUs, sel, res));
                        }

                    }break;
                    case "addnewOp":{
                        System.out.println("Запрос к БД на добавление операций (таблица operation), клиент: " + clientSocket.getInetAddress().toString());
                        Operations op = (Operations) sois.readObject();
                        int idUs = (int) sois.readObject();
                        soos.writeObject(objAddPar.addnewOp(op, idUs));
                    }break;
                    case "addnewPr":{
                        System.out.println("Запрос к БД на добавление объектов затрат (таблица production), клиент: " + clientSocket.getInetAddress().toString());
                        Production pr = (Production) sois.readObject();
                        int idUs = (int) sois.readObject();
                        soos.writeObject(objAddPar.addnewPr(pr, idUs));
                    }break;

                    case "editWorker":{
                        System.out.println("Запрос к БД на редактирование данных о пользователе(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        Users user = (Users)sois.readObject();
                        String login = (String) sois.readObject();
                        objEditPar.editWorker(user, login);
                    }break;
                    case "editExp":{
                        System.out.println("Запрос к БД на редактирование затрат (таблица Expenses), клиент: " + clientSocket.getInetAddress().toString());
                        Expenses exp = (Expenses) sois.readObject();
                        int idUs = (int) sois.readObject();
                        String nameExp = (String) sois.readObject();
                        soos.writeObject(objEditPar.editExp(exp, idUs, nameExp));
                    }break;
                    case "editRes":{
                        System.out.println("Запрос к БД на редактирование ресурсов (таблица Resources), клиент: " + clientSocket.getInetAddress().toString());
                        Resources res = (Resources) sois.readObject();
                        int idUs = (int) sois.readObject();
                        String nameRes = (String) sois.readObject();
                        soos.writeObject(objEditPar.editRes(res, idUs, nameRes));
                    }break;
                    case "editOp":{
                        System.out.println("Запрос к БД на редактирование операций (таблица operation), клиент: " + clientSocket.getInetAddress().toString());
                        Operations op = (Operations) sois.readObject();
                        int idUs = (int) sois.readObject();
                        String nameOp = (String) sois.readObject();
                        soos.writeObject(objEditPar.editOp(op, idUs, nameOp));
                    }break;
                    case "editPr":{
                        System.out.println("Запрос к БД на редактирование объектов затрат (таблица production), клиент: " + clientSocket.getInetAddress().toString());
                        Production pr = (Production) sois.readObject();
                        int idUs = (int) sois.readObject();
                        String namePr = (String) sois.readObject();
                        soos.writeObject(objEditPar.editPr(pr,idUs,namePr));
                    }break;

                    case "transferOp":{
                        System.out.println("Запрос к БД на перенсение стоимости ресурсов (таблица resop), клиент: " + clientSocket.getInetAddress().toString());
                        ResOp resOp = (ResOp) sois.readObject();
                        String nameRes = (String) sois.readObject();
                        String [] nameOp = (String[]) sois.readObject();
                        int idUs = (int) sois.readObject();

                        soos.writeObject(objTransfer.transferOp(resOp, nameRes, nameOp, idUs));
                    }break;
                    case "transferPr":{
                        System.out.println("Запрос к БД на перенсение стоимости операции (таблица opobj), клиент: " + clientSocket.getInetAddress().toString());
                        OpObj opObj = (OpObj) sois.readObject();
                        String nameOp = (String) sois.readObject();
                        String [] nameObj = (String[]) sois.readObject();
                        int idUs = (int) sois.readObject();

                        soos.writeObject(objTransfer.transferPr(opObj, nameOp, nameObj, idUs));
                    }break;

                    case "delCompany":{
                        System.out.println("Запрос к БД на удаление компании(таблица Company), клиент: " + clientSocket.getInetAddress().toString());
                        String nameC = (String) sois.readObject();
                        int idOwner = (int) sois.readObject();
                        objDelPar.deleteCompany(nameC, idOwner);
                    }break;
                    case "delOwner":{
                        System.out.println("Запрос к БД на удаление пользователя(таблица users), клиент: " + clientSocket.getInetAddress().toString());
                        Users user = (Users) sois.readObject();
                        objDelPar.delOwner(user);
                    }break;
                    case "delWorkers":{
                        System.out.println("Запрос к БД на удаление пользователя(таблица users), клиент: " + clientSocket.getInetAddress().toString());
                        Users user = (Users) sois.readObject();
                        objDelPar.delWorkers(user);
                    }break;
                    case "delExp":{
                        System.out.println("Запрос к БД на удаление затрат(таблица expenses), клиент: " + clientSocket.getInetAddress().toString());
                        String nameExp = (String) sois.readObject();
                        int idUs = (int) sois.readObject();
                        objDelPar.delExp(nameExp, idUs);
                    }break;
                    case "delRes":{
                        System.out.println("Запрос к БД на удаление ресурсов(таблица resources), клиент: " + clientSocket.getInetAddress().toString());
                        String nameRes = (String) sois.readObject();
                        int idUs = (int) sois.readObject();
                        objDelPar.delRes(nameRes, idUs);
                    }break;
                    case "delOp":{
                        System.out.println("Запрос к БД на удаление операций(таблица operation), клиент: " + clientSocket.getInetAddress().toString());
                        String nameOp = (String) sois.readObject();
                        int idUs = (int) sois.readObject();
                        objDelPar.delOp(nameOp, idUs);
                    }break;
                    case "delPr":{
                        System.out.println("Запрос к БД на удаление объектов затрат(таблица production), клиент: " + clientSocket.getInetAddress().toString());
                        String namePr = (String) sois.readObject();
                        int idUs = (int) sois.readObject();
                        objDelPar.delPr(namePr, idUs);
                    }break;

                    case "generateReporter":{
                        System.out.println("Запрос для формирования отчета, клиент: " + clientSocket.getInetAddress().toString());
                        int idUs = (int) sois.readObject();
                        objResults.generateReporter(idUs);
                    }break;
                    case "circleDiagram":{
                        System.out.println("Запрос для построения диаграммы, клиент: " + clientSocket.getInetAddress().toString());
                        String[] mas = (String[]) sois.readObject();
                        soos.writeObject(objResults.circleDiagram(mas));
                    }break;
                    case "showChart":{
                        System.out.println("Запросна получение данных для просмотра графика, клиент: " + clientSocket.getInetAddress().toString());
                        int idUser = (int)sois.readObject();
                        soos.writeObject(objResults.showChart(idUser));
                    }break;

                    case "showOwnerdata":{
                        Users user = (Users)sois.readObject();
                        System.out.println("Запрос к БД для получения данных о пользователе(таблица Users), клиент: " + clientSocket.getInetAddress().toString());
                        ConcretFactory sqlFactory1 = new ConcretFactory();
                        Users listUsers = sqlFactory1.getUsers().selectUsers(user);
                        soos.writeObject(listUsers.getIdUser());
                        soos.writeObject(listUsers.getName());
                        soos.writeObject(listUsers.getSurname());
                        soos.writeObject(listUsers.getPhone());
                        soos.writeObject(listUsers.getLogin());
                        soos.writeObject(listUsers.getPassword());
                    }break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void GenerateBD(){
        ConcretFactory sqlFactory1 = new ConcretFactory();
        sqlFactory1.getCompany().create();
        sqlFactory1.getUsers().create();
        sqlFactory1.getDataCompany().create();
        sqlFactory1.getExpenses().create();
        sqlFactory1.getOperations().create();
        sqlFactory1.getProduction().create();
        sqlFactory1.getResources().create();
        sqlFactory1.getResOp().create();
        sqlFactory1.getOpObj();
        sqlFactory1.getReport().create();
    }
}
