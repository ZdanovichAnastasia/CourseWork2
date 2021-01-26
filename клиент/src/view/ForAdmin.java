package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.ArrayList;

public class ForAdmin extends JDialog {
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JButton delButton;
    private JTable tableCompany;
    private JButton buttonExit;
    private JTabbedPane tabbedPane2;
    private JTextField searchUser_tf;
    private JComboBox filterRole_cb;
    private JButton searchUserButton;
    private JButton cancelSeachUserButton;
    private JLabel searchUser_lb;
    private JTabbedPane tabbedPane3;
    private JTextField searchCompany_tf;
    private JComboBox filterCountry_cb;
    private JComboBox filterIndustry_cb;
    private JButton searchCompanyButton;
    private JButton cancelSearchCompanyButton;
    private JLabel searchCompany_lb;
    boolean nameSUserf = false;
    boolean nameSCompanyf = false;

    public ForAdmin() {
        setContentPane(contentPane);
        setModal(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        //setUndecorated(true);

        String[] country = {"Не выбрано", "Беларусь", "Россия", "Польша", "Украина", "Латвия"};
        String[] industry = {"Не выбрано", "Образование", "Медицина", "Строительство", "Пищевая промышленность", "Электротехника", "Информационные технологии"
                , "Металурсгия", "Судностроение", "Розничная торговля", "Станкостроение", "Транспорт и связь", "Финансовая деятельность", "Производство машин и оборудования"
                , "Сельское хозяйство", "Гостиницы и рестораны"};
        ComboBoxModel combo1 = new DefaultComboBoxModel(country);
        filterCountry_cb.setModel(combo1);
        ComboBoxModel combo2 = new DefaultComboBoxModel(industry);
        filterIndustry_cb.setModel(combo2);
        filterRole_cb.addItem("Не выбрано");
        filterRole_cb.addItem("Владелец компании");
        filterRole_cb.addItem("Финансовый аналитик");

        table1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delButton.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        Controller.getInstance().initialize(this);
        delButton.setActionCommand("delUser");
        delButton.addActionListener(Controller.getInstance());
        delButton.setEnabled(false);
        buttonExit.setActionCommand("exitAdmin");
        buttonExit.addActionListener(Controller.getInstance());

        searchUserButton.setActionCommand("searchUser");
        searchUserButton.addActionListener(Controller.getInstance());
        cancelSeachUserButton.setActionCommand("cancelSearchUser");
        cancelSeachUserButton.addActionListener(Controller.getInstance());
        searchCompanyButton.setActionCommand("searchCompany");
        searchCompanyButton.addActionListener(Controller.getInstance());
        cancelSearchCompanyButton.setActionCommand("cancelSearchCompany");
        cancelSearchCompanyButton.addActionListener(Controller.getInstance());

        filterRole_cb.setActionCommand("filterRole");
        filterRole_cb.addActionListener(Controller.getInstance());
        filterCountry_cb.setActionCommand("filterIndustryCountry");
        filterCountry_cb.addActionListener(Controller.getInstance());
        filterIndustry_cb.setActionCommand("filterIndustryCountry");
        filterIndustry_cb.addActionListener(Controller.getInstance());

        searchUser_tf.getDocument().addDocumentListener(listener_nameSUser);
        searchCompany_tf.getDocument().addDocumentListener(listener_nameSCompany);
    }

    public JTable getTable1() {
        return table1;
    }

    public JTable getTableCompany() {
        return tableCompany;
    }

    public ArrayList<String[]> getSelectedUser() {
        int[] sel1 = table1.getSelectedRows();
        ArrayList<String[]> result = new ArrayList<>();
        for (var i = 0; i < sel1.length; i++) {
            String[] sel = new String[6];
            sel[0] = (String) table1.getValueAt(sel1[i], 0);
            sel[1] = (String) table1.getValueAt(sel1[i], 1);
            sel[2] = (String) table1.getValueAt(sel1[i], 2);
            sel[3] = (String) table1.getValueAt(sel1[i], 3);
            sel[4] = (String) table1.getValueAt(sel1[i], 4);
            sel[5] = (String) table1.getValueAt(sel1[i], 5);
            result.add(sel);
        }
        return result;
    }

    public JButton getDelButton() {
        return delButton;
    }

    public JComboBox getFilterRole_cb() {
        return filterRole_cb;
    }

    public JComboBox getFilterCountry_cb() {
        return filterCountry_cb;
    }

    public JComboBox getFilterIndustry_cb() {
        return filterIndustry_cb;
    }

    public JTextField getSearchCompany_tf() {
        return searchCompany_tf;
    }

    public JTextField getSearchUser_tf() {
        return searchUser_tf;
    }

    DocumentListener listener_nameSUser = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchUser_tf.getText().trim().equals("")) {
                searchUser_lb.setText("Поле не должно быть пустым");
                searchUser_lb.setVisible(true);
                nameSUserf = false;
            } else {
                searchUser_lb.setVisible(false);
                nameSUserf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchUser_tf.getText().trim().equals("")) {
                searchUser_lb.setText("Поле не должно быть пустым");
                searchUser_lb.setVisible(true);
                nameSUserf = false;
            } else {
                searchUser_lb.setVisible(false);
                nameSUserf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrectUser() {
        boolean flag = false;
        if (searchUser_tf.getText().trim().equals("")) searchUser_lb.setText("Поле не должно быть пустым");
        else if (nameSUserf == true) {
            flag = true;
        }
        return flag;
    }

    DocumentListener listener_nameSCompany = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchCompany_tf.getText().trim().equals("")) {
                searchCompany_lb.setText("Поле не должно быть пустым");
                searchCompany_lb.setVisible(true);
                nameSCompanyf = false;
            } else {
                searchCompany_lb.setVisible(false);
                nameSCompanyf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchCompany_tf.getText().trim().equals("")) {
                searchCompany_lb.setText("Поле не должно быть пустым");
                searchCompany_lb.setVisible(true);
                nameSCompanyf = false;
            } else {
                searchCompany_lb.setVisible(false);
                nameSCompanyf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrectCompany() {
        boolean flag = false;
        if (searchCompany_tf.getText().trim().equals("")) searchCompany_lb.setText("Поле не должно быть пустым");
        else if (nameSCompanyf == true) {
            flag = true;
        }
        return flag;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
