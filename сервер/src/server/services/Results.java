package server.services;

import DB.ConcretFactory;
import model.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Results {
    public Results(){}
    public void generateReporter(int idUs){
        int idC = -1;
        ConcretFactory sqlFactory = new ConcretFactory();

        ArrayList<DataCompany> listCompanies = sqlFactory.getDataCompany().selectAll();
        for(DataCompany company: listCompanies){
            if(idUs == company.getIdUser()){
                idC = company.getIdCompany();
            }
        }
        ArrayList<Users> listUsers = sqlFactory.getUsers().selectAll();
        String logUs = "";
        for(Users users: listUsers){
            if(idUs == users.getIdUser()){
                logUs = users.getLogin();
            }
        }
        ArrayList<Production> listProduction = sqlFactory.getProduction().selectAll();
        ArrayList<Company> companyList = sqlFactory.getCompany().selectAll();
        String nameC = "";
        for(Company company: companyList){
            if(idC == company.getIdCompany()){
                nameC = company.getCompanyName();
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
        try {
            XWPFDocument docxModel = new XWPFDocument();
            XWPFParagraph bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(25);
            paragraphConfig.setText(
                    "Отчет об анализе затрат компании " + nameC
            );
            XWPFTable table = docxModel.createTable(count+1, 6);
            table.getRow(0).getCell(0).addParagraph().createRun().setText("Название");
            table.getRow(0).getCell(1).addParagraph().createRun().setText("Расход на ед. пр.");
            table.getRow(0).getCell(2).addParagraph().createRun().setText("Прибыль за ед. пр.");
            table.getRow(0).getCell(3).addParagraph().createRun().setText("Продажи");
            table.getRow(0).getCell(4).addParagraph().createRun().setText("Полный расход");
            table.getRow(0).getCell(5).addParagraph().createRun().setText("Полная прибыль");
            double fullExp = 0;
            double fullProfit = 0;
            for(int i = 1; i <= data.length; i++ ){
                table.getRow(i).getCell(0).addParagraph().createRun().setText(data[i-1][0]);
                table.getRow(i).getCell(1).addParagraph().createRun().setText(data[i-1][3]);
                table.getRow(i).getCell(2).addParagraph().createRun().setText(data[i-1][2]);
                table.getRow(i).getCell(3).addParagraph().createRun().setText(data[i-1][1]);
                table.getRow(i).getCell(4).addParagraph().createRun().setText(String.valueOf(Double.parseDouble(data[i-1][3])*Integer.parseInt(data[i-1][1])));
                table.getRow(i).getCell(5).addParagraph().createRun().setText(String.valueOf(Double.parseDouble(data[i-1][2])*Integer.parseInt(data[i-1][1])));
                fullExp += Double.parseDouble(data[i-1][3])*Integer.parseInt(data[i-1][1]);
                fullProfit += Double.parseDouble(data[i-1][2])*Integer.parseInt(data[i-1][1]);
            }
            XWPFParagraph bodyParagraph2 = docxModel.createParagraph();
            bodyParagraph2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun paragraphConclusion = bodyParagraph2.createRun();
            paragraphConclusion.setFontSize(14);
            paragraphConclusion.addBreak();
            paragraphConclusion.setText("В результату проведенного анализа затрат методом \"Pre ABC-Costing\" выяснилось следующее:");
            paragraphConclusion.addBreak();
            for(int i = 1; i <= data.length; i++ ) {
                paragraphConclusion.setText("Объект затрат: "+data[i-1][0]+" имеет полную стоимость затрат = "+Double.parseDouble(data[i-1][3])*Integer.parseInt(data[i-1][1])+
                        " и полную прибыль = "+Double.parseDouble(data[i-1][2])*Integer.parseInt(data[i-1][1]));
                double income = Double.parseDouble(data[i-1][2])*Integer.parseInt(data[i-1][1]) - Double.parseDouble(data[i-1][3])*Integer.parseInt(data[i-1][1]);
                if(income >= 0){
                    paragraphConclusion.setText(". Реализация этой продукции окупаема и выручка = " + income + ";");
                    paragraphConclusion.addBreak();
                }
                else{
                    paragraphConclusion.setText(". Реализация этой продукции не окупается и выручка = " + income + ";");
                    paragraphConclusion.addBreak();
                }
            }
            paragraphConclusion.setText("На основании данных приведенных выше полная прибыль компании = " +fullProfit+", а полная стоимость затрат компании = "+fullExp+". В итоге выручка компании от реализации продукции = "+(fullProfit-fullExp)+".");
            paragraphConclusion.addBreak();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            paragraphConclusion.setText("Дата: "+ simpleDateFormat.format(date));
            try {
                FileOutputStream outputStream = new FileOutputStream("D:/УНИВЕР/курсач 3(1)/Отчеты/" + logUs + "+" + nameC + ".docx");
                docxModel.write(outputStream);
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            Report report = new Report();
            report.setIdCompany(idC);
            report.setDate(date);
            report.setIncome(fullProfit-fullExp);
            if(
                    sqlFactory.getReport().find(report).equals("")){
                sqlFactory.getReport().insert(report);
                DataCompany dataCompany1 = new DataCompany();
                dataCompany1.setIdCompany(idC);
                int idOwner = Integer.parseInt(sqlFactory.getDataCompany().find(dataCompany1));
                ArrayList<DataCompany> dataCompanyList = sqlFactory.getDataCompany().selectAll();
                for(DataCompany dataCompany: dataCompanyList){
                    if(dataCompany.getIdUser()==idOwner)
                    {
                        Report rep = new Report();
                        rep.setIdCompany(dataCompany.getIdCompany());
                        rep.setDate(date);
                        if(sqlFactory.getReport().find(rep).equals("")){
                            ArrayList<Report> findDataRep = sqlFactory.getReport().selectReports(dataCompany.getIdCompany());
                            rep.setIncome(findDataRep.get(findDataRep.size()-1).getIncome());
                            sqlFactory.getReport().insert(rep);
                        }
                    }
                }
            }
            else{
                sqlFactory.getReport().update(report, sqlFactory.getReport().find(report));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String[]> circleDiagram(String[] mas){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Production> listProduction = sqlFactory.getProduction().selectAll();
        ArrayList<String[]> data1 = new ArrayList<>();
        for (String data: mas){
            Company company = new Company();
            company.setCompanyName(data);
            int idC = Integer.parseInt(sqlFactory.getCompany().findCompany(company,2));
            for(Production production: listProduction){
                if(idC == production.getIdCompany()){
                    String[] arr = new  String[3];
                    arr[0] = data;
                    arr[1] = production.getProductionName();
                    arr[2] = String.valueOf(production.getCostProduction());
                    data1.add(arr);
                }
            }
        }
        return data1;
    }
    public ArrayList<String[]> showChart(int idUser){
        ConcretFactory sqlFactory = new ConcretFactory();
        ArrayList<Company> listCompanies = sqlFactory.getCompany().selectAll();
        ArrayList<DataCompany> listDataC = sqlFactory.getDataCompany().selectAll();
        ArrayList<String> companyName = new ArrayList<>();
        ArrayList<Integer> companyId = new ArrayList<>();
        for(DataCompany dataC: listDataC) {
            if(idUser == dataC.getIdUser()) {
                int idC = dataC.getIdCompany();
                for(Company company: listCompanies){
                    if(idC==company.getIdCompany()){
                        companyName.add(company.getCompanyName());
                        companyId.add(company.getIdCompany());
                    }
                }
            }
        }
        ArrayList<Report> reportList = sqlFactory.getReport().selectAll();
        ArrayList<String[]> data = new ArrayList<>();
        for(int i = 0; i < companyName.size(); i++){
            for(Report report: reportList){
                if(report.getIdCompany()==companyId.get(i)){
                    String[] mas = new String[3];
                    mas[0] = companyName.get(i);
                    mas[1] = String.valueOf(report.getIncome());
                    mas[2] = report.getDate();
                    data.add(mas);
                }
            }
        }
        return data;
    }
}
