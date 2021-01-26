package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class AddCompany extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton addCButton;
    private JTextField namecompany_tf;
    private JComboBox country_cb;
    private JComboBox industry_cb;
    private JLabel nameCompany_lb;
    boolean nameCf = false;

    public AddCompany() {
        setContentPane(contentPane);
        setModal(true);

        String[] country = {"Беларусь", "Россия", "Польша", "Украина", "Латвия"};
        String[] industry = {"Образование", "Медицина", "Строительство", "Пищевая промышленность", "Электротехника", "Информационные технологии"
                , "Металурсгия", "Судностроение", "Розничная торговля", "Станкостроение", "Транспорт и связь", "Финансовая деятельность", "Производство машин и оборудования"
                , "Сельское хозяйство", "Гостиницы и рестораны"};
        ComboBoxModel combo1 = new DefaultComboBoxModel(country);
        country_cb.setModel(combo1);
        ComboBoxModel combo2 = new DefaultComboBoxModel(industry);
        industry_cb.setModel(combo2);

        Controller.getInstance().initialize(this);
        addCButton.setActionCommand("addnewCompany");
        addCButton.addActionListener(Controller.getInstance());
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        namecompany_tf.getDocument().addDocumentListener(listener_nameC);
    }

    public JTextField getNamecompany_tf() {
        return namecompany_tf;
    }

    public JComboBox getCountry_cb() {
        return country_cb;
    }

    public JComboBox getIndustry_cb() {
        return industry_cb;
    }

    DocumentListener listener_nameC = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (namecompany_tf.getText().trim().equals("")) {
                nameCompany_lb.setText("Поле не должно быть пустым");
                nameCompany_lb.setVisible(true);
                nameCf = false;
            } else {
                nameCompany_lb.setVisible(false);
                nameCf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (namecompany_tf.getText().trim().equals("")) {
                nameCompany_lb.setText("Поле не должно быть пустым");
                nameCompany_lb.setVisible(true);
                nameCf = false;
            } else {
                nameCompany_lb.setVisible(false);
                nameCf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (namecompany_tf.getText().trim().equals("")) nameCompany_lb.setText("Поле не должно быть пустым");
        else if (nameCf == true) {
            flag = true;
        }
        return flag;
    }

}
