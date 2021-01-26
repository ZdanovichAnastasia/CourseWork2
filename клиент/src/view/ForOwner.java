package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class ForOwner extends JFrame {
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JLabel name_jl;
    private JLabel phone_jl;
    private JLabel login_jl;
    private JLabel surname_jl;
    private JLabel password_jl;
    private JTextField searchName_tf;
    private JTable tableWork;
    private JTable tableCompany;
    private JComboBox companyBox;
    private JComboBox comboFilter;
    private JButton EditOwnerButton;
    private JButton AddWorkerButton;
    private JButton DelCompanyButton;
    private JButton AddCompanyButton;
    private JButton DeleteWorkerButton;
    private JButton openReportDocxButton;
    private JButton editWorkerButton;
    private JButton canselSearchButton;
    private JButton searchButton;
    private JButton buttonExit;
    private JPanel diagrama;
    private JPanel Chart;
    private JLabel searchName_lb;
    boolean nameSf = false;
    private JFileChooser fileChooser = null;

    public ForOwner() {
        setContentPane(contentPane);
        Border solidBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

        name_jl.setBorder(solidBorder);
        phone_jl.setBorder(solidBorder);
        login_jl.setBorder(solidBorder);
        surname_jl.setBorder(solidBorder);
        password_jl.setBorder(solidBorder);

        Controller.getInstance().initialize(this);

        fileChooser = new JFileChooser("D:/УНИВЕР/курсач 3(1)/Отчеты");
        fileChooser.getComponent(0).setVisible(false);

        AddWorkerButton.setActionCommand("showAddCompanyUserFrame");
        AddWorkerButton.addActionListener(Controller.getInstance());
        editWorkerButton.setActionCommand("showEditWorkerFrame");
        editWorkerButton.addActionListener(Controller.getInstance());
        editWorkerButton.setEnabled(false);
        AddCompanyButton.setActionCommand("showAddCompanyFrame");
        AddCompanyButton.addActionListener(Controller.getInstance());
        buttonExit.setActionCommand("exitOwner");
        buttonExit.addActionListener(Controller.getInstance());
        openReportDocxButton.setActionCommand("openReport");
        openReportDocxButton.addActionListener(Controller.getInstance());
        comboFilter.setActionCommand("filterUser");
        comboFilter.addActionListener(Controller.getInstance());
        searchButton.setActionCommand("searchWorker");
        searchButton.addActionListener(Controller.getInstance());
        canselSearchButton.setActionCommand("cancelSearchWorker");
        canselSearchButton.addActionListener(Controller.getInstance());
        EditOwnerButton.setActionCommand("showEditOwnerFrame");
        EditOwnerButton.addActionListener(Controller.getInstance());
        companyBox.setActionCommand("Diagram");
        companyBox.addActionListener(Controller.getInstance());
        DelCompanyButton.setActionCommand("delCompany");
        DelCompanyButton.addActionListener(Controller.getInstance());
        DelCompanyButton.setEnabled(false);
        DeleteWorkerButton.setActionCommand("delWorkers");
        DeleteWorkerButton.addActionListener(Controller.getInstance());
        DeleteWorkerButton.setEnabled(false);

        tableCompany.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DelCompanyButton.setEnabled(true);
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
        tableWork.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DeleteWorkerButton.setEnabled(true);
                editWorkerButton.setEnabled(true);
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

        searchName_tf.getDocument().addDocumentListener(listener_nameS);
    }

    public JTable gettableCompany() {
        return tableCompany;
    }

    public int[] getSelectedW() {
        int[] sel;
        sel = tableWork.getSelectedRows();
        System.out.println(sel[0]);
        return sel;
    }

    public String[] getSelectedCompanyN() {
        int[] sel1 = tableCompany.getSelectedRows();
        String[] sel = new String[sel1.length];
        for (var i = 0; i < sel1.length; i++) {
            sel[i] = (String) tableCompany.getValueAt(sel1[i], 0);
        }
        System.out.println(sel[0]);
        return sel;
    }

    public String[] getSelectedWorker() {
        int[] sel1 = tableWork.getSelectedRows();
        String[] mas = new String[6];
        mas[0] = (String) tableWork.getValueAt(sel1[0], 0);
        mas[1] = (String) tableWork.getValueAt(sel1[0], 1);
        mas[2] = (String) tableWork.getValueAt(sel1[0], 2);
        mas[3] = (String) tableWork.getValueAt(sel1[0], 3);
        mas[4] = (String) tableWork.getValueAt(sel1[0], 4);
        mas[5] = (String) tableWork.getValueAt(sel1[0], 5);
        return mas;
    }

    public JButton getDelCButton() {
        return DelCompanyButton;
    }

    public JButton getDeleteWorkerButton() {
        return DeleteWorkerButton;
    }

    public JButton getEditWorkerButton() {
        return editWorkerButton;
    }

    public JTable getTableWork() {
        return tableWork;
    }

    public JLabel getName_jl() {
        return name_jl;
    }

    public JLabel getSurname_jl() {
        return surname_jl;
    }

    public JLabel getPhone_jl() {
        return phone_jl;
    }

    public JLabel getLogin_jl() {
        return login_jl;
    }

    public JLabel getPassword_jl() {
        return password_jl;
    }

    public JTextField getSearchName_tf() {
        return searchName_tf;
    }

    public JComboBox getComboFilter() {
        return comboFilter;
    }

    public JComboBox getCompanyBox() {
        return companyBox;
    }

    public JPanel getDiagrama() {
        return diagrama;
    }

    public JPanel getChart() {
        return Chart;
    }

    DocumentListener listener_nameS = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchName_tf.getText().trim().equals("")) {
                searchName_lb.setText("Поле не должно быть пустым");
                searchName_lb.setVisible(true);
                nameSf = false;
            } else {
                searchName_lb.setVisible(false);
                nameSf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchName_tf.getText().trim().equals("")) {
                searchName_lb.setText("Поле не должно быть пустым");
                searchName_lb.setVisible(true);
                nameSf = false;
            } else {
                searchName_lb.setVisible(false);
                nameSf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (searchName_tf.getText().trim().equals("")) searchName_lb.setText("Поле не должно быть пустым");
        else if (nameSf == true) {
            flag = true;
        }
        return flag;
    }

}
