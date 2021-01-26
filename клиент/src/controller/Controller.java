package controller;

import view.*;
import model.*;
import model.client.Client;
import view.grafic.Chart;
import view.grafic.Diagrama;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

public class Controller implements ActionListener {
    private static Controller instance;
    private EnterForm objEnter;
    private Client client;
    private UsersRegistration objRegistrarionUsers;
    private ForUser objUsersForm;
    private ForAdmin objAdminForm;
    private ForOwner objOwnerForm;
    private AddCompany objAddCompany;
    private AddProduction objAddProduction;
    private AddOperations objAddOperations;
    private AddExpenses objAddExpenses;
    private AddResources objAddResources;

    private Controller(){
        client = new Client("127.0.0.2", "9006");
    }

    public void initialize(EnterForm obj){
        objEnter = obj;
    }
    public void initialize(ForUser obj){
        objUsersForm = obj;
    }
    public void initialize(ForAdmin obj){ objAdminForm = obj;}
    public void initialize(ForOwner obj){ objOwnerForm = obj;}
    public void initialize(AddProduction obj){ objAddProduction = obj;}
    public void initialize(AddCompany obj){ objAddCompany = obj;}
    public void initialize(AddExpenses obj){ objAddExpenses = obj;}
    public void initialize(AddResources obj){ objAddResources = obj;}
    public void initialize(AddOperations obj){ objAddOperations = obj;}
    public void initialize(UsersRegistration obj) { objRegistrarionUsers = obj; }

    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Вход")) {
            autorization();
        }
        if (e.getActionCommand().equals("Регистрация")){ }
        if (e.getActionCommand().equals("showRegistrationFrame")) {
            objEnter.dispose();
            UsersRegistration formReg = new UsersRegistration();
            formReg.setTitle("Регистрация пользователя");
            formReg.pack();
            formReg.setLocationRelativeTo(null);
            formReg.setVisible(true);
        }
        if (e.getActionCommand().equals("backToAutorization")) {
            objRegistrarionUsers.dispose();
            objEnter.getTextLogin().setText("");
            objEnter.getPasswordField1().setText("");
            objEnter.setVisible(true);
        }
        if (e.getActionCommand().equals("registrationUsers")) {
            if (objRegistrarionUsers.getLogin_tf().getText().equals("") ||
                    objRegistrarionUsers.getPassword_tf().getText().equals(""))
                JOptionPane.showMessageDialog(objRegistrarionUsers, "Заполните все поля правильно!!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Users user = new Users();
                int error = 0;
                user.setName(objRegistrarionUsers.getName_tf().getText());
                user.setSurname(objRegistrarionUsers.getSurname_tf().getText());
                user.setPhone(objRegistrarionUsers.getPhone_tf().getText());
                user.setLogin(objRegistrarionUsers.getLogin_tf().getText());
                user.setPassword(objRegistrarionUsers.getPassword_tf().getText());
                user.setRole("Владелец компании");
                if (error == 0){
                    client.sendMessage("registrationUser");
                    client.sendObject(user);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    }catch(IOException ex){
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This user is already existed"))
                        JOptionPane.showMessageDialog(objRegistrarionUsers, "Такой пользователь уже существует!", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(objRegistrarionUsers, "Пользователь успешно зарегистрирован", "Регистрация пользователя", JOptionPane.PLAIN_MESSAGE);
                        objRegistrarionUsers.dispose();
                        EnterForm dialog = new EnterForm();
                        dialog.setTitle("Авторизация");
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                }
            }
        }

        if (e.getActionCommand().equals("exitUser")) {
            objUsersForm.dispose();
            objEnter.getTextLogin().setText("");
            objEnter.getPasswordField1().setText("");
            objEnter.setVisible(true);
        }
        if (e.getActionCommand().equals("exitOwner")) {
            objOwnerForm.dispose();
            objEnter.getTextLogin().setText("");
            objEnter.getPasswordField1().setText("");
            objEnter.setVisible(true);
        }
        if (e.getActionCommand().equals("exitAdmin")) {
            objAdminForm.dispose();
            objEnter.getTextLogin().setText("");
            objEnter.getPasswordField1().setText("");
            objEnter.setVisible(true);
        }

        if (e.getActionCommand().equals("addnewCompany")) {
            if (objAddCompany.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddCompany, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Company company = new Company();
                int id = getOwnerid();
                company.setCompanyName(objAddCompany.getNamecompany_tf().getText());
                company.setCountry(objAddCompany.getCountry_cb().getSelectedItem().toString());
                company.setIndustry(objAddCompany.getIndustry_cb().getSelectedItem().toString());;
                client.sendMessage("addnewCompany");
                client.sendObject(company);
                client.sendObject(id);

                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddCompany, "Такая компания уже существует!", "Ошибка добавления данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddCompany, "Пользователь успешно добавил компанию", "Добавление данных", JOptionPane.PLAIN_MESSAGE);
                    repaintCompany();
                    repaintnamecompany(3);
                    repaintnamecompany(2);
                    objAddCompany.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("addUserCompany")) {
            if (objRegistrarionUsers.getCorrect()==false)
                JOptionPane.showMessageDialog(objRegistrarionUsers, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Users user = new Users();
                Company company = new Company();

                user.setName(objRegistrarionUsers.getName_tf().getText());
                user.setSurname(objRegistrarionUsers.getSurname_tf().getText());
                user.setPhone(objRegistrarionUsers.getPhone_tf().getText());
                user.setLogin(objRegistrarionUsers.getLogin_tf().getText());
                user.setPassword(objRegistrarionUsers.getPassword_tf().getText());
                company.setCompanyName(objRegistrarionUsers.getCompany());
                user.setRole("Финансовый аналитик");
                client.sendMessage("addUserCompany");
                client.sendObject(user);
                client.sendObject(company);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objRegistrarionUsers, "Такой пользователь уже существует!", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objRegistrarionUsers, "Пользователь успешно зарегистрирован", "Регистрация пользователя", JOptionPane.PLAIN_MESSAGE);
                    repaintWorkers();
                    objRegistrarionUsers.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("addnewExpenses")) {
            if(objAddExpenses.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddExpenses, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Expenses expenses = new Expenses();
                expenses.setExpensesName(objAddExpenses.getNameExp_tf().getText());
                expenses.setExpensesGroup(objAddExpenses.getGroupExp_tf().getText());
                expenses.setSumOfExpenses(objAddExpenses.getSumExp_tf().getText());
                expenses.setExpensesType(objAddExpenses.getTypeExp_cb().getSelectedItem().toString());
                int id = getOwnerid();
                client.sendMessage("addnewExp");
                client.sendObject(expenses);
                client.sendObject(id);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddExpenses, "Такая затрата уже добавлена в компанию!", "Ошибка добавления данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddExpenses, "Данные о затратах успешно добавлены", "Добавление данных", JOptionPane.PLAIN_MESSAGE);
                    repaintExp(1);
                    objAddExpenses.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("addnewResources")) {
            if (objAddResources.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddResources, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Resources resources = new Resources();
                resources.setResourceName(objAddResources.getNameRes_tf().getText());
                int id = getOwnerid();
                client.sendMessage("addnewRes");
                client.sendMessage("0");
                client.sendObject(resources);
                client.sendObject(id);
                String sel[] = objAddResources.getSelectedExp();
                client.sendObject(sel);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddResources, "Такой ресурс уже добавлен в компанию!", "Ошибка добавления данных", JOptionPane.ERROR_MESSAGE);
                else if(mes.equals("Exp is already added")){
                    ArrayList<String> mas = (ArrayList<String>) client.readObject();
                    String str = "Стоимость";
                    for(String s: mas){
                        str += " " + s + ",";
                    }
                    str = str.substring(0, str.length() - 1);
                    str += " уже перенесена на ресурсы";
                    JOptionPane.showMessageDialog(objAddResources, str, "Ошибка перенесения стоимости", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(objAddResources, "Данные о ресурсе успешно добавлены", "Добавление данных", JOptionPane.PLAIN_MESSAGE);
                    repaintResources();
                    repaintnameRes(1);
                    objAddResources.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("addnewExptoResources")) {
            if (objAddResources.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddResources, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Resources resources = new Resources();
                resources.setResourceName(objAddResources.getNameRes_tf().getText());
                int id = getOwnerid();
                client.sendMessage("addnewRes");
                client.sendMessage("1");
                client.sendObject(resources);
                client.sendObject(id);
                String sel[] = objAddResources.getSelectedExp();
                client.sendObject(sel);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddResources, "Такой ресурс уже добавлен в компанию!", "Ошибка добавления данных", JOptionPane.ERROR_MESSAGE);
                else if(mes.equals("Exp is already added")){
                    ArrayList<String> mas = (ArrayList<String>) client.readObject();
                    String str = "Стоимость";
                    for(String s: mas){
                        str += " " + s + ",";
                    }
                    str = str.substring(0, str.length() - 1);
                    str += " уже перенесена на ресурсы";
                    JOptionPane.showMessageDialog(objAddResources, str, "Ошибка перенесения стоимости", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(objAddResources, "Данные о ресурсе успешно добавлены", "Добавление данных", JOptionPane.PLAIN_MESSAGE);
                    repaintResources();
                    repaintnameRes(1);
                    objUsersForm.getDelRButton().setEnabled(false);
                    objUsersForm.getEditRButton().setEnabled(false);
                    objUsersForm.getAddExptoRButton().setEnabled(false);
                    objAddResources.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("addnewOperation")) {
            if (objAddOperations.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddOperations, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Operations operations = new Operations();
                operations.setOperationName(objAddOperations.getNameOp_tf().getText());
                operations.setOperationType(objAddOperations.getTypeOp_cb().getSelectedItem().toString());
                int id = getOwnerid();
                client.sendMessage("addnewOp");
                client.sendObject(operations);
                client.sendObject(id);

                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddOperations, "Такая операция уже добавлена!", "Ошибка добавления данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddOperations, "Данные об операции успешно добавлены", "Добавление данных", JOptionPane.PLAIN_MESSAGE);
                    repaintOperations(1);
                    repaintnameOp(1);
                    objAddOperations.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("addnewProduction")) {
            if (objAddProduction.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddProduction, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Production production = new Production();
                production.setProductionName(objAddProduction.getPrName_tf().getText());
                production.setProductionCol(Integer.parseInt(objAddProduction.getNumPr_tf().getText()));
                production.setProfit(Double.parseDouble(objAddProduction.getProfit_tf().getText()));
                int id = getOwnerid();
                client.sendMessage("addnewPr");
                client.sendObject(production);
                client.sendObject(id);

                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddProduction, "Такой объект затрат уже добавлен!", "Ошибка добавления данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddProduction, "Данные об объекте затрат успешно добавлены", "Добаление данных", JOptionPane.PLAIN_MESSAGE);
                    repaintProduction(1);
                    objAddProduction.dispose();
                }
            }
        }

        if (e.getActionCommand().equals("editUser")) {
            if (objRegistrarionUsers.getCorrect()==false)
                JOptionPane.showMessageDialog(objRegistrarionUsers, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Users user = new Users();
                user.setName(objRegistrarionUsers.getName_tf().getText());
                user.setSurname(objRegistrarionUsers.getSurname_tf().getText());
                user.setPhone(objRegistrarionUsers.getPhone_tf().getText());
                user.setLogin(objRegistrarionUsers.getLogin_tf().getText());
                user.setPassword(objRegistrarionUsers.getPassword_tf().getText());
                client.sendMessage("editWorker");
                client.sendObject(user);
                client.sendObject(user.getLogin());
                JOptionPane.showMessageDialog(objRegistrarionUsers, "Данные о пользователе успешно отредактированы", "Редактирование данных", JOptionPane.PLAIN_MESSAGE);
                repaintWorkers();
                repaintOwner();
                objOwnerForm.getDelCButton().setEnabled(false);
                objOwnerForm.getEditWorkerButton().setEnabled(false);
                objRegistrarionUsers.dispose();
            }
        }
        if (e.getActionCommand().equals("editExpenses")) {
            if(objAddExpenses.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddExpenses, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Expenses expenses = new Expenses();
                expenses.setExpensesName(objAddExpenses.getNameExp_tf().getText());
                expenses.setExpensesGroup(objAddExpenses.getGroupExp_tf().getText());
                expenses.setSumOfExpenses(objAddExpenses.getSumExp_tf().getText());
                expenses.setExpensesType(objAddExpenses.getTypeExp_cb().getSelectedItem().toString());
                int id = getOwnerid();
                client.sendMessage("editExp");
                client.sendObject(expenses);
                client.sendObject(id);
                String[] exp = objUsersForm.getSelectedExp();
                client.sendObject(exp[0]);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddExpenses, "Такая затрата уже добавлена в компанию!", "Ошибка редактирования данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddExpenses, "Данные о затратах успешно изменены", "Редактирование данных", JOptionPane.PLAIN_MESSAGE);
                    repaintExp(1);
                    repaintResources();
                    repaintOperations(1);
                    repaintProduction(1);
                    objUsersForm.getDelExpButton().setEnabled(false);
                    objUsersForm.getEditExpButton().setEnabled(false);
                    objAddExpenses.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("editResources")) {
            if (objAddResources.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddResources, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Resources resources = new Resources();
                resources.setResourceName(objAddResources.getNameRes_tf().getText());
                int id = getOwnerid();
                client.sendMessage("editRes");
                client.sendObject(resources);
                client.sendObject(id);
                String sel[] = objUsersForm.getSelectedRes();
                client.sendObject(sel[0]);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddResources, "Такой ресурс уже добавлен в компанию!", "Ошибка редактирования данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddResources, "Данные о ресурсе успешно изменены", "Редактирование данных", JOptionPane.PLAIN_MESSAGE);
                    repaintResources();
                    repaintnameRes(1);
                    objUsersForm.getDelRButton().setEnabled(false);
                    objUsersForm.getEditRButton().setEnabled(false);
                    objUsersForm.getAddExptoRButton().setEnabled(false);
                    objAddResources.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("editOperation")) {
            if (objAddOperations.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddOperations, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Operations operations = new Operations();
                operations.setOperationName(objAddOperations.getNameOp_tf().getText());
                operations.setOperationType(objAddOperations.getTypeOp_cb().getSelectedItem().toString());
                int id = getOwnerid();
                client.sendMessage("editOp");
                client.sendObject(operations);
                client.sendObject(id);
                String sel[] = objUsersForm.getSelectedOp();
                client.sendObject(sel[0]);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddOperations, "Такая операция уже добавлена!", "Ошибка редактирования данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddOperations, "Данные об операции успешно изменены", "Редактирование данных", JOptionPane.PLAIN_MESSAGE);
                    repaintOperations(1);
                    repaintnameOp(1);
                    objUsersForm.getDelOpButton().setEnabled(false);
                    objUsersForm.getEditOpButton().setEnabled(false);
                    objUsersForm.getTransferOpButton().setEnabled(false);
                    objAddOperations.dispose();
                }
            }
        }
        if (e.getActionCommand().equals("editProduction")) {
            if (objAddProduction.getCorrect()==false)
                JOptionPane.showMessageDialog(objAddProduction, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                Production production = new Production();
                production.setProductionName(objAddProduction.getPrName_tf().getText());
                production.setProductionCol(Integer.parseInt(objAddProduction.getNumPr_tf().getText()));
                production.setProfit(Double.parseDouble(objAddProduction.getProfit_tf().getText()));
                int id = getOwnerid();
                client.sendMessage("editPr");
                client.sendObject(production);
                client.sendObject(id);
                String sel[] = objUsersForm.getSelectedPr();
                client.sendObject(sel[0]);
                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objAddProduction, "Такой объект затрат уже добавлен!", "Ошибка редактирования данных", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAddProduction, "Данные об объекте затрат успешно изменены", "Редактирование данных", JOptionPane.PLAIN_MESSAGE);
                    repaintProduction(1);
                    objUsersForm.getDelPrButton().setEnabled(false);
                    objUsersForm.getEditPrButton().setEnabled(false);
                    objUsersForm.getTransferPrButton().setEnabled(false);
                    objAddProduction.dispose();
                }
            }
        }

        if (e.getActionCommand().equals("transferOperation")) {
            if (objUsersForm.getCorrectdrOP()==false)
                JOptionPane.showMessageDialog(objUsersForm, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                ResOp resOp = new ResOp();
                resOp.setDriverOp(Integer.parseInt(objUsersForm.getDriverOp_tf().getText()));
                String nameres = objUsersForm.getResources();
                String[] nameop = objUsersForm.getSelectedOpN();
                int id = getOwnerid();
                client.sendMessage("transferOp");
                client.sendObject(resOp);
                client.sendObject(nameres);
                client.sendObject(nameop);
                client.sendObject(id);

                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objUsersForm, "Стоимость этого ресурса уже перенесена на выбранную операцию!", "Ошибка перенесения стоимости", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objUsersForm, "Стоимость ресурса успешно перенесена", "Перенесение стоимости", JOptionPane.PLAIN_MESSAGE);
                    repaintOperations(1);
                    repaintProduction(1);
                    objUsersForm.getDelOpButton().setEnabled(false);
                    objUsersForm.getEditOpButton().setEnabled(false);
                    objUsersForm.getTransferOpButton().setEnabled(false);
                }
            }
        }
        if (e.getActionCommand().equals("transferProduction")) {
            if (objUsersForm.getCorrectdrObj()==false)
                JOptionPane.showMessageDialog(objUsersForm, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else{
                OpObj opObj = new OpObj();
                opObj.setDriverObj(Integer.parseInt(objUsersForm.getDriverPt_tf().getText()));
                String nameop = objUsersForm.getOperation();
                String[] namepr = objUsersForm.getSelectedPrN();
                int id = getOwnerid();
                client.sendMessage("transferPr");
                client.sendObject(opObj);
                client.sendObject(nameop);
                client.sendObject(namepr);
                client.sendObject(id);

                String mes = "";
                try {
                    mes = client.readMessage();
                }catch(IOException ex){
                    System.out.println("Error in reading");
                }
                if (mes.equals("This is already existed"))
                    JOptionPane.showMessageDialog(objUsersForm, "Стоимость этой операции уже перенесена на выбранный объект затрат!", "Ошибка перенесения стоимости", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objUsersForm, "Стоимость операции успешно перенесена", "Перенесение стоимости", JOptionPane.PLAIN_MESSAGE);
                    repaintProduction(1);
                    objUsersForm.getDelPrButton().setEnabled(false);
                    objUsersForm.getEditPrButton().setEnabled(false);
                    objUsersForm.getTransferPrButton().setEnabled(false);
                }
            }
        }

        if (e.getActionCommand().equals("showAddCompanyFrame")) {
            AddCompany formaddC = new AddCompany();
            formaddC.setTitle("Добавление компании");
            formaddC.pack();
            formaddC.setLocationRelativeTo(null);
            formaddC.setVisible(true);
        }
        if (e.getActionCommand().equals("showAddCompanyUserFrame")) {
            UsersRegistration formReg = new UsersRegistration();
            formReg.setTitle("Добавление финансового аналитика");
            formReg.pack();
            formReg.setLocationRelativeTo(null);
            formReg.visibleAddUser();
            repaintnamecompany(1);
            if(formReg.getComboBox1().getItemCount() == 0){
                JOptionPane.showMessageDialog(objAdminForm, "У вас нет пока нет компаний, чтобы добавить финансового аналитика", "", JOptionPane.PLAIN_MESSAGE);
                formReg.dispose();
            }
            else {
                formReg.setVisible(true);
            }
        }
        if (e.getActionCommand().equals("showAddExpFrame")) {
            AddExpenses formExp = new AddExpenses();
            formExp.setTitle("Добавление затрат");
            formExp.pack();
            formExp.setLocationRelativeTo(null);
            formExp.setVisible(true);
        }
        if (e.getActionCommand().equals("showAddResourcesFrame")) {
            AddResources formRes= new AddResources();
            formRes.setTitle("Добавление ресурсов");
            formRes.pack();
            formRes.setLocationRelativeTo(null);
            repaintExp(2);
            formRes.setVisible(true);
        }
        if (e.getActionCommand().equals("showAddOperationsFrame")) {
            AddOperations formOp = new AddOperations();
            formOp.setTitle("Добавление операций");
            formOp.pack();
            formOp.setLocationRelativeTo(null);
            formOp.setVisible(true);
        }
        if (e.getActionCommand().equals("showAddProductionFrame")) {
            AddProduction formPr = new AddProduction();
            formPr.setTitle("Добавление объектов затрат");
            formPr.pack();
            formPr.setLocationRelativeTo(null);
            formPr.setVisible(true);
        }

        if (e.getActionCommand().equals("showEditWorkerFrame")) {
            UsersRegistration form = new UsersRegistration();
            form.setTitle("Редактирование данных о пользователе");
            String[] sel = objOwnerForm.getSelectedWorker();
            objRegistrarionUsers.setName_tf(sel[0]);
            objRegistrarionUsers.setSurname_tf(sel[1]);
            //objRegistrarionUsers.setPhone_tf(sel[2]);
            String phone = sel[2];
            if(phone.contains("+7")){
                objRegistrarionUsers.setOperator_cb("Россия");
            }
            if(phone.contains("+48")){
                objRegistrarionUsers.setOperator_cb("Польша");
            }
            if(phone.contains("+380")){
                objRegistrarionUsers.setOperator_cb("Украина");
            }
            if(phone.contains("+218")){
                objRegistrarionUsers.setOperator_cb("Латвия");
            }
            objRegistrarionUsers.setPhone_tf(sel[2]);
            objRegistrarionUsers.setLogin_tf(sel[3]);
            objRegistrarionUsers.setPassword_tf(sel[4]);
            objRegistrarionUsers.setComboBox1(sel[5]);
            form.visibleEditUser();
            repaintnamecompany(1);
            form.pack();
            form.setLocationRelativeTo(null);
            form.setVisible(true);
        }
        if (e.getActionCommand().equals("showEditOwnerFrame")) {
            UsersRegistration form = new UsersRegistration();
            form.setTitle("Редактирование данных о пользователе");
            objRegistrarionUsers.setName_tf(objOwnerForm.getName_jl().getText());
            objRegistrarionUsers.setSurname_tf(objOwnerForm.getSurname_jl().getText());
            //objRegistrarionUsers.setPhone_tf(objOwnerForm.getPhone_jl().getText());
            String phone = objOwnerForm.getPhone_jl().getText();
            if(phone.contains("+7")){
                objRegistrarionUsers.setOperator_cb("Россия");
            }
            if(phone.contains("+48")){
                objRegistrarionUsers.setOperator_cb("Польша");
            }
            if(phone.contains("+380")){
                objRegistrarionUsers.setOperator_cb("Украина");
            }
            if(phone.contains("+218")){
                objRegistrarionUsers.setOperator_cb("Латвия");
            }
            objRegistrarionUsers.setPhone_tf(objOwnerForm.getPhone_jl().getText());
            objRegistrarionUsers.setLogin_tf(objOwnerForm.getLogin_jl().getText());
            objRegistrarionUsers.setPassword_tf(objOwnerForm.getPassword_jl().getText());
            form.visibleEditOwner();
            repaintnamecompany(1);
            form.pack();
            form.setLocationRelativeTo(null);
            form.setVisible(true);
        }
        if (e.getActionCommand().equals("showEditExpensesframe")) {
            AddExpenses formExp = new AddExpenses();
            formExp.setTitle("Редактирование данных о затрате");
            String[] exp = objUsersForm.getSelectedExp();
            objAddExpenses.setNameExp_tf(exp[0]);
            objAddExpenses.setGroupExp_tf(exp[1]);
            objAddExpenses.setSumExp_tf(exp[2]);
            objAddExpenses.setTypeExp_cb(exp[3]);
            objAddExpenses.getEditButton().setVisible(true);
            objAddExpenses.getAddButton().setVisible(false);
            formExp.pack();
            formExp.setLocationRelativeTo(null);
            formExp.setVisible(true);
        }
        if (e.getActionCommand().equals("showEditResourcesframe")) {
            AddResources formRes = new AddResources();
            formRes.setTitle("Редактирование данных о ресурсе");
            String[] res = objUsersForm.getSelectedRes();
            objAddResources.setNameRes_tf(res[0]);
            objAddResources.getEditButton().setVisible(true);
            objAddResources.getButtonAddR().setVisible(false);
            objAddResources.getExp_lb().setVisible(false);
            objAddResources.getScrollExp().setVisible(false);
            formRes.pack();
            formRes.setLocationRelativeTo(null);
            formRes.setVisible(true);
        }
        if (e.getActionCommand().equals("showEditOperationframe")) {
            AddOperations formOp = new AddOperations();
            formOp.setTitle("Редактирование данных об операцие");
            String[] op = objUsersForm.getSelectedOp();
            objAddOperations.setNameOp_tf(op[0]);
            objAddOperations.setTypeOp_cb(op[1]);
            objAddOperations.getEditButton().setVisible(true);
            objAddOperations.getButtonOK().setVisible(false);
            formOp.pack();
            formOp.setLocationRelativeTo(null);
            formOp.setVisible(true);
        }
        if (e.getActionCommand().equals("showEditProductionframe")) {
            AddProduction formPr = new AddProduction();
            formPr.setTitle("Редактирование данных об объекте затрат");
            String[] pr = objUsersForm.getSelectedPr();
            objAddProduction.setPrName_tf(pr[0]);
            objAddProduction.setNumPr_tf(pr[1]);
            objAddProduction.setProfit_tf(pr[2]);
            objAddProduction.getEditButton().setVisible(true);
            objAddProduction.getAddButton().setVisible(false);
            formPr.pack();
            formPr.setLocationRelativeTo(null);
            formPr.setVisible(true);
        }
        if (e.getActionCommand().equals("showAddExptoRframe")) {
            AddResources formRes = new AddResources();
            formRes.setTitle("Добавление затрат в ресурс");
            String[] res = objUsersForm.getSelectedRes();
            objAddResources.setNameRes_tf(res[0]);
            objAddResources.getNameRes_tf().setEnabled(false);
            objAddResources.getButtonAddR().setVisible(false);
            objAddResources.getAddExpButton().setVisible(true);
            formRes.pack();
            formRes.setLocationRelativeTo(null);
            repaintExp(2);
            formRes.setVisible(true);
        }

        if (e.getActionCommand().equals("delUser")) {
            ArrayList<String[]> info = objAdminForm.getSelectedUser();
            for(String[] item: info){
                if(item[5].equals("Финансовый аналитик")){
                    client.sendMessage("delWorkers");
                }
                else{
                    client.sendMessage("delOwner");
                }
                Users user = new Users();
                user.setLogin(item[3]);
                user.setPassword(item[4]);
                client.sendObject(user);
            }
            JOptionPane.showMessageDialog(objAdminForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintUsers();
            repaintAllCompany();
            objAdminForm.getDelButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("delCompany")) {
            String [] sel = objOwnerForm.getSelectedCompanyN();
            int id = getOwnerid();
            for(int i=0; i<sel.length; i++) {
                client.sendMessage("delCompany");
                client.sendObject(sel[i]);
                client.sendObject(id);
            }
            JOptionPane.showMessageDialog(objUsersForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintCompany();
            repaintnamecompany(2);
            repaintnamecompany(3);
            repaintWorkers();
            objOwnerForm.getDelCButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("delWorkers")) {
            int sel[] = objOwnerForm.getSelectedW();
            for(int i=0; i<sel.length; i++){
                int id = getOwnerid();
                client.sendMessage("createListWorks");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                client.sendMessage("delWorkers");
                Users user =new Users();
                user.setLogin(data[sel[i]][3]);
                user.setPassword(data[sel[i]][4]);
                client.sendObject(user);
            }
            JOptionPane.showMessageDialog(objOwnerForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintWorkers();
            objOwnerForm.getDeleteWorkerButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("delExpenses")) {
            String[] sel = objUsersForm.getSelectedExpN();
            int id = getOwnerid();
            for(int i=0; i<sel.length; i++) {
                client.sendMessage("delExp");
                client.sendObject(sel[i]);
                client.sendObject(id);
            }
            JOptionPane.showMessageDialog(objUsersForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintExp(1);
            repaintResources();
            repaintOperations(1);
            repaintProduction(1);
            objUsersForm.getDelExpButton().setEnabled(false);
            objUsersForm.getEditExpButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("delResources")) {
            String[] sel = objUsersForm.getSelectedResN();
            int id = getOwnerid();
            for(int i=0; i<sel.length; i++) {
                client.sendMessage("delRes");
                client.sendObject(sel[i]);
                client.sendObject(id);
            }
            JOptionPane.showMessageDialog(objUsersForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintResources();
            repaintOperations(1);
            repaintProduction(1);
            objUsersForm.getDelRButton().setEnabled(false);
            objUsersForm.getEditRButton().setEnabled(false);
            objUsersForm.getAddExptoRButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("delOperation")) {
            String[] sel = objUsersForm.getSelectedOpN();
            int id = getOwnerid();
            for(int i=0; i<sel.length; i++) {
                client.sendMessage("delOp");
                client.sendObject(sel[i]);
                client.sendObject(id);
            }
            JOptionPane.showMessageDialog(objUsersForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintOperations(1);
            repaintProduction(1);
            objUsersForm.getDelOpButton().setEnabled(false);
            objUsersForm.getEditOpButton().setEnabled(false);
            objUsersForm.getTransferOpButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("delProduction")) {
            String[] sel = objUsersForm.getSelectedPrN();
            int id = getOwnerid();
            for(int i=0; i<sel.length; i++) {
                client.sendMessage("delPr");
                client.sendObject(sel[i]);
                client.sendObject(id);
            }
            JOptionPane.showMessageDialog(objUsersForm, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintProduction(1);
            objUsersForm.getDelPrButton().setEnabled(false);
            objUsersForm.getEditPrButton().setEnabled(false);
            objUsersForm.getTransferPrButton().setEnabled(false);
        }

        if (e.getActionCommand().equals("generateReporter")) {
            int id = getOwnerid();
            client.sendMessage("generateReporter");
            client.sendObject(id);

            JOptionPane.showMessageDialog(objUsersForm, "Отчет успешно сформирован!", "Формирование отчета", JOptionPane.PLAIN_MESSAGE);
            repaintProduction(1);
            objUsersForm.getDelPrButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("openReport")) {
            int id = getOwnerid();
            client.sendMessage("createListDataCompanies");
            client.sendObject(id);
            ArrayList<String> data = (ArrayList<String>) client.readObject();
            if(data.size() == 0){
                JOptionPane.showMessageDialog(objAdminForm, "У вас нет пока нет компаний, чтобы просмотреть отчеты", "", JOptionPane.PLAIN_MESSAGE);
            }
            else{
                JFileChooser fileChooser = new JFileChooser("D:/УНИВЕР/курсач 3(1)/Отчеты");
                fileChooser.getComponent(0).setVisible(false);
                for (int i = 0; i < data.size(); i++) {
                    String name = data.get(i);
                    fileChooser.addChoosableFileFilter(new FileFilter() {
                        public String getDescription() {
                            return name + " (*.docx)";
                        }
                        public boolean accept(File f) {
                            if (f.isDirectory()) {
                                return true;
                            } else {
                                return f.getName().endsWith(name + ".docx");
                            }
                        }
                    });
                }
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setDialogTitle("Выбор директории");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(objOwnerForm);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        System.out.println(fileChooser.getSelectedFile().toString());
                        Desktop.getDesktop().open(new File(fileChooser.getSelectedFile().getPath()));
                        System.out.println(fileChooser.getSelectedFile().toString());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        if (e.getActionCommand().equals("Diagram")) {
            int id = getOwnerid();
            client.sendMessage("createListDataCompanies");
            client.sendObject(id);
            ArrayList<String> data0 = (ArrayList<String>) client.readObject();
            String[] data1 = new String[data0.size()];
            for (int i = 0; i < data0.size(); i++) {
                data1[i] = data0.get(i);
            }
            String nameC = objOwnerForm.getCompanyBox().getSelectedItem().toString();
            client.sendMessage("circleDiagram");
            client.sendObject(data1);
            ArrayList<String[]> data = (ArrayList<String[]>) client.readObject();
            objOwnerForm.getDiagrama().removeAll();
            objOwnerForm.getDiagrama().add(Diagrama.createDemoPanel(data, nameC));
        }


        if (e.getActionCommand().equals("filterUser")) {
            String value = objOwnerForm.getComboFilter().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                repaintWorkers();
            }
            else {
                int id = getOwnerid();
                client.sendMessage("createListWorks");
                client.sendObject(id);
                String[][] data1 = (String[][]) client.readObject();
                int size = 0;
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][5].equals(value)) {
                        size++;
                    }
                }
                int num = 0;

                String[][] data = new String[size][6];
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][5].equals(value)) {
                        data[num][0] = data1[i][0];
                        data[num][1] = data1[i][1];
                        data[num][2] = data1[i][2];
                        data[num][3] = data1[i][3];
                        data[num][4] = data1[i][4];
                        data[num][5] = data1[i][5];
                        num++;
                    }
                }
                String[] columnNames = {
                        "Имя",
                        "Фамилия",
                        "Телефон",
                        "Логин",
                        "Пароль",
                        "Название компании"
                };
                TableModel tableModel = new DefaultTableModel(data, columnNames);
                objOwnerForm.getTableWork().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("filterOp")) {
            String value = objUsersForm.getFilterOp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                repaintOperations(1);
            }
            else{
                int id = getOwnerid();
                client.sendMessage("createListOperations");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[1].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][3];
                for(String[] item: data){
                    if(item[1].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        count++;
                    }
                }
                String[] columnNames = {
                        "Название",
                        "Тип",
                        "Сумма"
                };
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.getTableOp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("filterExp")) {
            String value = objUsersForm.getFilterExp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                repaintExp(1);
            }
            else{
                int id = getOwnerid();
                client.sendMessage("createListExpenses");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[3].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][4];
                for(String[] item: data){
                    if(item[3].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        info[count][3] = item[3];
                        count++;
                    }
                }
                String[] columnNames = {
                        "Название",
                        "Группа",
                        "Сумма",
                        "Тип"
                };
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.gettableExp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("filterRole")) {
            String value = objAdminForm.getFilterRole_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                repaintUsers();
            }
            else {
                client.sendMessage("createListUsers");
                String[][] data1 = (String[][]) client.readObject();
                int size = 0;
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][5].equals(value)) {
                        size++;
                    }
                }
                int num = 0;

                String[][] data = new String[size][6];
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][5].equals(value)) {
                        data[num][0] = data1[i][0];
                        data[num][1] = data1[i][1];
                        data[num][2] = data1[i][2];
                        data[num][3] = data1[i][3];
                        data[num][4] = data1[i][4];
                        data[num][5] = data1[i][5];
                        num++;
                    }
                }
                String[] columnNames = {
                        "Имя",
                        "Фамилия",
                        "Телефон",
                        "Логин",
                        "Пароль",
                        "Роль"
                };
                TableModel tableModel = new DefaultTableModel(data, columnNames);
                objAdminForm.getTable1().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("filterIndustryCountry")) {
            String valueCountry = objAdminForm.getFilterCountry_cb().getSelectedItem().toString();
            String valueIndustry = objAdminForm.getFilterIndustry_cb().getSelectedItem().toString();
            if (valueCountry.equals("Не выбрано") && valueIndustry.equals("Не выбрано")) {
                repaintAllCompany();
            }
            else {
                client.sendMessage("createListAllCompanies");
                String[][] data1 = (String[][]) client.readObject();
                int size = 0;
                String[] columnNames = {
                        "Название",
                        "Страна",
                        "Отрасль"
                };
                if (valueIndustry.equals("Не выбрано")) {
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][1].equals(valueCountry)) {
                            size++;
                        }
                    }
                    int num = 0;

                    String[][] data = new String[size][3];
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][1].equals(valueCountry)) {
                            data[num][0] = data1[i][0];
                            data[num][1] = data1[i][1];
                            data[num][2] = data1[i][2];
                            num++;
                        }
                    }
                    TableModel tableModel = new DefaultTableModel(data, columnNames);
                    objAdminForm.getTableCompany().setModel(tableModel);
                }
                else if (valueCountry.equals("Не выбрано")) {
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][2].equals(valueIndustry)) {
                            size++;
                        }
                    }
                    int num = 0;

                    String[][] data = new String[size][3];
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][2].equals(valueIndustry)) {
                            data[num][0] = data1[i][0];
                            data[num][1] = data1[i][1];
                            data[num][2] = data1[i][2];
                            num++;
                        }
                    }
                    TableModel tableModel = new DefaultTableModel(data, columnNames);
                    objAdminForm.getTableCompany().setModel(tableModel);
                }
                else {
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][2].equals(valueIndustry) && data1[i][1].equals(valueCountry)) {
                            size++;
                        }
                    }
                    int num = 0;

                    String[][] data = new String[size][3];
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][2].equals(valueIndustry) && data1[i][1].equals(valueCountry)) {
                            data[num][0] = data1[i][0];
                            data[num][1] = data1[i][1];
                            data[num][2] = data1[i][2];
                            num++;
                        }
                    }
                    TableModel tableModel = new DefaultTableModel(data, columnNames);
                    objAdminForm.getTableCompany().setModel(tableModel);
                }
            }
        }

        if (e.getActionCommand().equals("searchWorker")) {
            if (objOwnerForm.getCorrect()==false)
                JOptionPane.showMessageDialog(objOwnerForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objOwnerForm.getSearchName_tf().getText();
                int id = getOwnerid();
                client.sendMessage("createListWorks");
                client.sendObject(id);
                String[][] data1 = (String[][]) client.readObject();
                int size = 0;
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][1].equals(value)) {
                        size++;
                    }
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objOwnerForm, "Пользователь с такой фамилией не найден!", "Поиск пользователя", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String[][] data = new String[size][6];
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][1].equals(value)) {
                            data[num][0] = data1[i][0];
                            data[num][1] = data1[i][1];
                            data[num][2] = data1[i][2];
                            data[num][3] = data1[i][3];
                            data[num][4] = data1[i][4];
                            data[num][5] = data1[i][5];
                            num++;
                        }
                    }
                    String[] columnNames = {
                            "Имя",
                            "Фамилия",
                            "Телефон",
                            "Логин",
                            "Пароль",
                            "Название компании"
                    };
                    TableModel tableModel = new DefaultTableModel(data, columnNames);
                    objOwnerForm.getTableWork().setModel(tableModel);

                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchWorker")) {
            repaintWorkers();
        }
        if (e.getActionCommand().equals("searchUser")) {
            if (objAdminForm.getCorrectUser()==false)
                JOptionPane.showMessageDialog(objAdminForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objAdminForm.getSearchUser_tf().getText();
                client.sendMessage("createListUsers");
                String[][] data1 = (String[][]) client.readObject();
                int size = 0;
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][1].equals(value)) {
                        size++;
                    }
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objAdminForm, "Пользователь с такой фамилией не найден!", "Поиск пользователя", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String[][] data = new String[size][6];
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][1].equals(value)) {
                            data[num][0] = data1[i][0];
                            data[num][1] = data1[i][1];
                            data[num][2] = data1[i][2];
                            data[num][3] = data1[i][3];
                            data[num][4] = data1[i][4];
                            data[num][5] = data1[i][5];
                            num++;
                        }
                    }
                    String[] columnNames = {
                            "Имя",
                            "Фамилия",
                            "Телефон",
                            "Логин",
                            "Пароль",
                            "Роль"
                    };
                    TableModel tableModel = new DefaultTableModel(data, columnNames);
                    objAdminForm.getTable1().setModel(tableModel);
                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchUser")) {
            repaintUsers();
        }
        if (e.getActionCommand().equals("searchCompany")) {
            if (objAdminForm.getCorrectCompany()==false)
                JOptionPane.showMessageDialog(objAdminForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objAdminForm.getSearchCompany_tf().getText();
                client.sendMessage("createListAllCompanies");
                String[][] data1 = (String[][]) client.readObject();
                int size = 0;
                for (int i = 0; i < data1.length; i++) {
                    if (data1[i][0].equals(value)) {
                        size++;
                    }
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objAdminForm, "Компания не найдена!", "Поиск компании", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String[][] data = new String[size][6];
                    for (int i = 0; i < data1.length; i++) {
                        if (data1[i][0].equals(value)) {
                            data[num][0] = data1[i][0];
                            data[num][1] = data1[i][1];
                            data[num][2] = data1[i][2];
                            num++;
                        }
                    }
                    String[] columnNames = {
                            "Название",
                            "Страна",
                            "Отрасль"
                    };
                    TableModel tableModel = new DefaultTableModel(data, columnNames);
                    objAdminForm.getTableCompany().setModel(tableModel);
                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchCompany")) {
            repaintAllCompany();
        }

        if (e.getActionCommand().equals("searchExp")) {
            if (objUsersForm.getCorrectSearchExp()==false)
                JOptionPane.showMessageDialog(objUsersForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objUsersForm.getSearchExp_tf().getText();
                int id = getOwnerid();
                client.sendMessage("createListExpenses");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[0].equals(value))
                        size++;
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objUsersForm, "Затрата не найдена!", "Поиск затраты", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int count = 0;
                    String[][] info = new String[size][4];
                    for(String[] item: data){
                        if(item[0].equals(value))
                        {
                            info[count][0] = item[0];
                            info[count][1] = item[1];
                            info[count][2] = item[2];
                            info[count][3] = item[3];
                            count++;
                        }
                    }
                    String[] columnNames = {
                            "Название",
                            "Группа",
                            "Сумма",
                            "Тип"
                    };
                    TableModel tableModel = new DefaultTableModel(info, columnNames);
                    objUsersForm.gettableExp().setModel(tableModel);
                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchExp")) {
            repaintExp(1);
        }
        if (e.getActionCommand().equals("searchRes")) {
            if (objUsersForm.getCorrectSearchRes()==false)
                JOptionPane.showMessageDialog(objUsersForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objUsersForm.getSearchRes_tf().getText();
                int id = getOwnerid();
                client.sendMessage("createListResources");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[0].equals(value))
                        size++;
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objUsersForm, "Ресурс не найден!", "Поиск ресурса", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int count = 0;
                    String[][] info = new String[size][2];
                    for(String[] item: data){
                        if(item[0].equals(value))
                        {
                            info[count][0] = item[0];
                            info[count][1] = item[1];
                            count++;
                        }
                    }
                    String[] columnNames = {
                            "Название",
                            "Сумма"
                    };
                    TableModel tableModel = new DefaultTableModel(info, columnNames);
                    objUsersForm.gettableR().setModel(tableModel);
                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchRes")) {
            repaintResources();
        }
        if (e.getActionCommand().equals("searchOp")) {
            if (objUsersForm.getCorrectSearchOp()==false)
                JOptionPane.showMessageDialog(objUsersForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objUsersForm.getSearchOp_tf().getText();
                int id = getOwnerid();
                client.sendMessage("createListOperations");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[0].equals(value))
                        size++;
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objUsersForm, "Операция не найденв!", "Поиск операции", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int count = 0;
                    String[][] info = new String[size][3];
                    for(String[] item: data){
                        if(item[0].equals(value))
                        {
                            info[count][0] = item[0];
                            info[count][1] = item[1];
                            info[count][2] = item[2];
                            count++;
                        }
                    }
                    String[] columnNames = {
                            "Название",
                            "Тип",
                            "Сумма"
                    };
                    TableModel tableModel = new DefaultTableModel(info, columnNames);
                    objUsersForm.getTableOp().setModel(tableModel);
                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchOp")) {
            repaintOperations(1);
        }
        if (e.getActionCommand().equals("searchPr")) {
            if (objUsersForm.getCorrectSearchPr()==false)
                JOptionPane.showMessageDialog(objUsersForm, "Заполните все поля правильно!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                String value = objUsersForm.getSearchPr_tf().getText();
                int id = getOwnerid();
                client.sendMessage("createListProduction");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[0].equals(value))
                        size++;
                }
                int num = 0;
                if (size == 0) {
                    JOptionPane.showMessageDialog(objUsersForm, "Объект затрат не найден!", "Поиск объекта затрат", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int count = 0;
                    String[][] info = new String[size][4];
                    for(String[] item: data){
                        if(item[0].equals(value))
                        {
                            info[count][0] = item[0];
                            info[count][1] = item[1];
                            info[count][2] = item[2];
                            info[count][3] = item[3];
                            count++;
                        }
                    }
                    String[] columnNames = {
                            "Название",
                            "Количество",
                            "Стоимость ед. продукции",
                            "Сумма расходов"
                    };
                    TableModel tableModel = new DefaultTableModel(info, columnNames);
                    objUsersForm.getTablePr().setModel(tableModel);
                }
            }
        }
        if (e.getActionCommand().equals("cancelSearchPr")) {
            repaintProduction(1);
        }

        if (e.getActionCommand().equals("sortExpName")) {
            int id = getOwnerid();
            client.sendMessage("sortExpList");
            client.sendObject(id);
            client.sendObject("expenses_name");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Группа",
                    "Сумма",
                    "Тип"
            };
            String value = objUsersForm.getFilterExp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                TableModel tableModel = new DefaultTableModel(data, columnNames);
                objUsersForm.gettableExp().setModel(tableModel);
            }
            else{
                int size = 0;
                for(String[] item: data){
                    if(item[3].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][4];
                for(String[] item: data){
                    if(item[3].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        info[count][3] = item[3];
                        count++;
                    }
                }
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.gettableExp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("sortExpSum")) {
            int id = getOwnerid();
            client.sendMessage("sortExpList");
            client.sendObject(id);
            client.sendObject("expenses_summ");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Группа",
                    "Сумма",
                    "Тип"
            };
            String value = objUsersForm.getFilterExp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                TableModel tableModel = new DefaultTableModel(data, columnNames);
                objUsersForm.gettableExp().setModel(tableModel);
            }
            else{
                int size = 0;
                for(String[] item: data){
                    if(item[3].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][4];
                for(String[] item: data){
                    if(item[3].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        info[count][3] = item[3];
                        count++;
                    }
                }
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.gettableExp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("notSortExp")) {
            String value = objUsersForm.getFilterExp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                repaintExp(1);
            }
            else{
                int id = getOwnerid();
                client.sendMessage("createListExpenses");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[3].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][4];
                for(String[] item: data){
                    if(item[3].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        info[count][3] = item[3];
                        count++;
                    }
                }
                String[] columnNames = {
                        "Название",
                        "Группа",
                        "Сумма",
                        "Тип"
                };
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.gettableExp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("sortResName")) {
            int id = getOwnerid();
            client.sendMessage("sortNameResList");
            client.sendObject(id);
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Сумма"
            };
            TableModel tableModel = new DefaultTableModel(data, columnNames);
            objUsersForm.gettableR().setModel(tableModel);
        }
        if (e.getActionCommand().equals("sortResSum")) {
            int id = getOwnerid();
            client.sendMessage("sortSumResList");
            client.sendObject(id);
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Сумма"
            };
            TableModel tableModel = new DefaultTableModel(data, columnNames);
            objUsersForm.gettableR().setModel(tableModel);
        }
        if (e.getActionCommand().equals("notSortRes")) {
            repaintResources();
        }
        if (e.getActionCommand().equals("sortOpName")) {
            int id = getOwnerid();
            client.sendMessage("sortOpList");
            client.sendObject(id);
            client.sendObject("operation_name");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Тип",
                    "Сумма"
            };
            String value = objUsersForm.getFilterOp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                TableModel tableModel = new DefaultTableModel(data, columnNames);
                objUsersForm.getTableOp().setModel(tableModel);
            }
            else{
                int size = 0;
                for(String[] item: data){
                    if(item[1].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][3];
                for(String[] item: data){
                    if(item[1].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        count++;
                    }
                }
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.getTableOp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("sortOpSum")) {
            int id = getOwnerid();
            client.sendMessage("sortOpList");
            client.sendObject(id);
            client.sendObject("operation_cost");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Тип",
                    "Сумма"
            };
            String value = objUsersForm.getFilterOp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                TableModel tableModel = new DefaultTableModel(data, columnNames);
                objUsersForm.getTableOp().setModel(tableModel);
            }
            else{
                int size = 0;
                for(String[] item: data){
                    if(item[1].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][3];
                for(String[] item: data){
                    if(item[1].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        count++;
                    }
                }
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.getTableOp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("notSortOp")) {
            String value = objUsersForm.getFilterOp_cb().getSelectedItem().toString();
            if(value.equals("Не выбрано")){
                repaintOperations(1);
            }
            else{
                int id = getOwnerid();
                client.sendMessage("createListOperations");
                client.sendObject(id);
                String[][] data = (String[][])client.readObject();
                int size = 0;
                for(String[] item: data){
                    if(item[1].equals(value))
                        size++;
                }
                int count = 0;
                String[][] info = new String[size][3];
                for(String[] item: data){
                    if(item[1].equals(value))
                    {
                        info[count][0] = item[0];
                        info[count][1] = item[1];
                        info[count][2] = item[2];
                        count++;
                    }
                }
                String[] columnNames = {
                        "Название",
                        "Тип",
                        "Сумма"
                };
                TableModel tableModel = new DefaultTableModel(info, columnNames);
                objUsersForm.getTableOp().setModel(tableModel);
            }
        }
        if (e.getActionCommand().equals("sortPrName")) {
            int id = getOwnerid();
            client.sendMessage("sortPrList");
            client.sendObject(id);
            client.sendObject("production_name");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Количество",
                    "Стоимость ед. продукции",
                    "Сумма расходов"
            };
            TableModel tableModel = new DefaultTableModel(data, columnNames);
            objUsersForm.getTablePr().setModel(tableModel);
        }
        if (e.getActionCommand().equals("sortPrSum")) {
            int id = getOwnerid();
            client.sendMessage("sortPrList");
            client.sendObject(id);
            client.sendObject("cost");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Количество",
                    "Стоимость ед. продукции",
                    "Сумма расходов"
            };
            TableModel tableModel = new DefaultTableModel(data, columnNames);
            objUsersForm.getTablePr().setModel(tableModel);
        }
        if (e.getActionCommand().equals("sortPrProfit")) {
            int id = getOwnerid();
            client.sendMessage("sortPrList");
            client.sendObject(id);
            client.sendObject("profit");
            String[][] data = (String[][])client.readObject();
            String[] columnNames = {
                    "Название",
                    "Количество",
                    "Стоимость ед. продукции",
                    "Сумма расходов"
            };
            TableModel tableModel = new DefaultTableModel(data, columnNames);
            objUsersForm.getTablePr().setModel(tableModel);
        }
        if (e.getActionCommand().equals("notSortPr")) {
            repaintProduction(1);
        }
    }


    public void autorization(){
        try {
            String msgLogin = objEnter.getTextLogin().getText();
            String msgPassword = objEnter.getPasswordField1().getText();
            if (msgLogin.equals("") || msgPassword.equals(""))
                JOptionPane.showMessageDialog(objEnter,
                        "Заполните все поля!",
                        "Ошибка ввода",
                        JOptionPane.ERROR_MESSAGE);
            Users user = new Users();
            user.setLogin(msgLogin);
            user.setPassword(msgPassword);
            client.sendMessage("enter");
            client.sendObject(user);

            String servMsg = client.readMessage();
            switch(servMsg){
                case "error":{
                    JOptionPane.showMessageDialog(objEnter,
                            "Такой пользователь не зарегистрирован",
                            "Ошибка авторизации",
                            JOptionPane.ERROR_MESSAGE);
                }break;
                case "errorInput":{
                    JOptionPane.showMessageDialog(objEnter,
                            "Проверьте введенные данные",
                            "Ошибка ввода",
                            JOptionPane.ERROR_MESSAGE);
                }break;
                case "ok":{
                    String status = client.readMessage();
                    if (status.equals("admin")) {
                        objEnter.setVisible(false);
                        ForAdmin form = new ForAdmin();

                        repaintUsers();
                        repaintAllCompany();

                        form.setTitle("Меню администратора");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);
                    }
                    if (status.equals("Владелец компании")) {
                        objEnter.setVisible(false);
                        ForOwner formOwner = new ForOwner();

                        repaintOwner();
                        repaintWorkers();
                        repaintCompany();
                        repaintnamecompany(2);
                        repaintnamecompany(3);
                        repaintDiagram();
                        repaintChart();

                        formOwner.setTitle("Меню владельца");
                        formOwner.pack();
                        formOwner.setLocationRelativeTo(null);
                        formOwner.setVisible(true);
                    }
                    if (status.equals("Финансовый аналитик")){
                        objEnter.setVisible(false);

                        ForUser usersForm = new ForUser();
                        repaintExp(1);
                        repaintResources();
                        repaintOperations(1);
                        repaintProduction(1);
                        repaintnameRes(1);
                        repaintnameOp(1);

                        usersForm.setTitle("Меню пользователя");
                        usersForm.pack();
                        usersForm.setLocationRelativeTo(null);
                        usersForm.setVisible(true);
                    }
                }break;

            }
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public void repaintOwner() {
        String msgLogin = objEnter.getTextLogin().getText();
        String msgPassword = objEnter.getPasswordField1().getText();
        client.sendMessage("showOwnerdata");
        Users user = new Users();
        user.setLogin(msgLogin);
        user.setPassword(msgPassword);
        client.sendObject(user);
        int id = (int)client.readObject();
        String name = (String)client.readObject();
        String surname = (String)client.readObject();
        String phone = (String)client.readObject();
        String login = (String)client.readObject();
        String password = (String)client.readObject();
        objOwnerForm.getName_jl().setText(name);
        objOwnerForm.getSurname_jl().setText(surname);
        objOwnerForm.getPhone_jl().setText(phone);
        objOwnerForm.getLogin_jl().setText(login);
        objOwnerForm.getPassword_jl().setText(password);
    }
    public void repaintUsers(){
        client.sendMessage("createListUsers");
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Имя",
                "Фамилия",
                "Телефон",
                "Логин",
                "Пароль",
                "Роль"
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        objAdminForm.getTable1().setModel(tableModel);
    }
    public void repaintWorkers(){
        int id = getOwnerid();
        client.sendMessage("createListWorks");
        client.sendObject(id);
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Имя",
                "Фамилия",
                "Телефон",
                "Логин",
                "Пароль",
                "Название компании"
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        objOwnerForm.getTableWork().setModel(tableModel);
    }
    public void repaintAllCompany(){
        client.sendMessage("createListAllCompanies");
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Название",
                "Страна",
                "Отрасль"
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        objAdminForm.getTableCompany().setModel(tableModel);
    }
    public void repaintCompany(){
        int id = getOwnerid();
        client.sendMessage("createListCompanies");
        client.sendObject(id);
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Название",
                "Страна",
                "Отрасль"
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        objOwnerForm.gettableCompany().setModel(tableModel);
    }
    public void repaintnamecompany(int flag) {
        int id = getOwnerid();
        client.sendMessage("createListDataCompanies");
        client.sendObject(id);
        ArrayList<String> data = (ArrayList<String>) client.readObject();
        String[] data1 = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            data1[i] = data.get(i);
        }
        ComboBoxModel combo = new DefaultComboBoxModel(data1);
        if (flag == 1) objRegistrarionUsers.getComboBox1().setModel(combo);
        if (flag == 2) {
            data1 = new String[data.size()+1];
            data1[0] = "Не выбрано";
            for (int i = 0; i < data.size(); i++) {
                data1[i+1] = data.get(i);
            }
            ComboBoxModel combo1 = new DefaultComboBoxModel(data1);
            objOwnerForm.getComboFilter().setModel(combo1);
        }
        if (flag == 3){
            objOwnerForm.getCompanyBox().setModel(combo);
        }

    }
    public void repaintnameRes(int flag) {
        int id = getOwnerid();
        client.sendMessage("createListNameRes");
        client.sendObject(id);
        String[] data = (String[])client.readObject();
        ComboBoxModel combo = new DefaultComboBoxModel(data);
        if (flag == 1) objUsersForm.getNameRes().setModel(combo);
        if (flag == 2) {
            //data1 = new String[data.size()+1];
            //data1[0] = "Не выбрано";
            //for (int i = 0; i < data.size(); i++) {
               // data1[i+1] = data.get(i);
            //}
            //ComboBoxModel combo1 = new DefaultComboBoxModel(data1);
           // objOwnerForm.getComboFilter().setModel(combo1);
        }

    }
    public void repaintnameOp(int flag) {
        int id = getOwnerid();
        client.sendMessage("createListNameOp");
        client.sendObject(id);
        ArrayList<String> data = (ArrayList<String>) client.readObject();
        String[] data1 = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            data1[i] = data.get(i);
        }
        ComboBoxModel combo = new DefaultComboBoxModel(data1);
        if (flag == 1) objUsersForm.getNameOp().setModel(combo);
        if (flag == 2) {
            //data1 = new String[data.size()+1];
            //data1[0] = "Не выбрано";
            //for (int i = 0; i < data.size(); i++) {
               // data1[i+1] = data.get(i);
            //}
           // ComboBoxModel combo1 = new DefaultComboBoxModel(data1);
           // objOwnerForm.getComboFilter().setModel(combo1);
        }

    }
    public void repaintExp(int flag){
        int id = getOwnerid();
        client.sendMessage("createListExpenses");
        client.sendObject(id);
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Название",
                "Группа",
                "Сумма, тыс. руб.",
                "Тип"
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        if(flag==1) {objUsersForm.gettableExp().setModel(tableModel);}
        if(flag==2) {objAddResources.getExptable().setModel(tableModel);}
    }
    public void repaintResources(){
        int id = getOwnerid();
        client.sendMessage("createListResources");
        client.sendObject(id);
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Название",
                "Сумма, тыс. руб."
        };
//        System.out.println("Re"+ data[0][0]);
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        objUsersForm.gettableR().setModel(tableModel);
    }
    public void repaintOperations(int flag){
        int id = getOwnerid();
        client.sendMessage("createListOperations");
        client.sendObject(id);
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Название",
                "Тип",
                "Сумма, тыс. руб."
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        if(flag==1) {objUsersForm.getTableOp().setModel(tableModel);}
        if(flag==2) {objAddResources.getExptable().setModel(tableModel);}
    }
    public void repaintProduction(int flag){
        int id = getOwnerid();
        client.sendMessage("createListProduction");
        client.sendObject(id);
        String[][] data = (String[][])client.readObject();
        String[] columnNames = {
                "Название",
                "Количество",
                "Стоимость ед. продукции",
                "Сумма расходов, тыс. руб."
        };
        TableModel tableModel = new DefaultTableModel(data, columnNames);
        if(flag==1) {objUsersForm.getTablePr().setModel(tableModel);}
        if(flag==2) {objAddResources.getExptable().setModel(tableModel);}
    }
    public int getOwnerid(){
        String msgLogin = objEnter.getTextLogin().getText();
        String msgPassword = objEnter.getPasswordField1().getText();
        Users user = new Users();
        client.sendMessage("showOwnerdata");
        user = new Users();
        user.setLogin(msgLogin);
        user.setPassword(msgPassword);
        client.sendObject(user);
        int id = (int)client.readObject();
        String name = (String)client.readObject();
        String surname = (String)client.readObject();
        String phone = (String)client.readObject();
        String login = (String)client.readObject();
        String password = (String)client.readObject();
        return id;
    }
    public void repaintChart(){
        int id = getOwnerid();
        client.sendMessage("showChart");
        client.sendObject(id);
        ArrayList<String[]> data3 = (ArrayList<String[]>) client.readObject();
        if(data3.size()>0) {
            objOwnerForm.getChart().removeAll();
            objOwnerForm.getChart().add(Chart.createDemoPanel(data3));
        }
    }
    public void repaintDiagram(){
        int id = getOwnerid();
        client.sendMessage("createListDataCompanies");
        client.sendObject(id);
        ArrayList<String> data0 = (ArrayList<String>) client.readObject();
        String[] data1 = new String[data0.size()];
        for (int i = 0; i < data0.size(); i++) {
            data1[i] = data0.get(i);
        }
        if(data1.length>0) {
            String nameC = data1[0];
            client.sendMessage("circleDiagram");
            client.sendObject(data1);
            ArrayList<String[]> data = (ArrayList<String[]>) client.readObject();
            if(data.size()>0) {
                objOwnerForm.getDiagrama().add(Diagrama.createDemoPanel(data, nameC));
            }
        }
    }
}
