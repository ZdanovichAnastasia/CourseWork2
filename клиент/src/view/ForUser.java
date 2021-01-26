package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class ForUser extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton addExpButton;
    private JButton addRButton;
    private JTable tableResources;
    private JButton delRButton;
    private JButton editRButton;
    private JTable tableExpenses;
    private JButton delExpButton;
    private JButton editExpButton;
    private JTable tableOperation;
    private JComboBox nameRes_cb;
    private JTextField driverOp_tf;
    private JButton AddOpButton;
    private JButton delOpButton;
    private JButton EditOpButton;
    private JButton transferOpButton;
    private JTable tableProduction;
    private JTextField driverPt_tf;
    private JButton DelPrButton;
    private JButton AddPrButton;
    private JButton transferPrButton;
    private JButton EditPrButton;
    private JComboBox nameOp_cb;
    private JButton generateReportButton;
    private JButton addExptoRButton;
    private JLabel driverOp_m;
    private JLabel driverObj_m;
    private JComboBox filterExp_cb;
    private JComboBox filterOp_cb;
    private JRadioButton sortExpName_rb;
    private JRadioButton sortExpCost_rb;
    private JRadioButton sortResName_rb;
    private JRadioButton sortResCost_rb;
    private JRadioButton sortOpName_rb;
    private JRadioButton sortOpCost_rb;
    private JRadioButton sortPrName_rb;
    private JRadioButton sortPrProfit_rb;
    private JRadioButton sortPrCost_rb;
    private JRadioButton notSortExp_rb;
    private JRadioButton notSortRes_rb;
    private JRadioButton notSortOp_rb;
    private JRadioButton notSortPr_rb;
    private JTabbedPane tabbedPane1;
    private JTextField searchExp_tf;
    private JButton searchExpButton;
    private JButton cancelSearchExpButton;
    private JLabel searchExp_lb;
    private JTabbedPane tabbedPane2;
    private JTextField searchRes_tf;
    private JButton searchResButton;
    private JButton cancelSearchResButton;
    private JLabel searchRes_lb;
    private JTabbedPane tabbedPane3;
    private JTextField searchOp_tf;
    private JButton searchOpButton;
    private JButton cancelSearchOpButton;
    private JLabel searchOp_lb;
    private JTabbedPane tabbedPane4;
    private JTextField searchPr_tf;
    private JButton searchPrButton;
    private JButton camcelSearchPrButton;
    private JLabel searchPr_lb;
    boolean driverOpf = false;
    boolean driverObjf = false;
    boolean searchExpf = false;
    boolean searchResf = false;
    boolean searchOpf = false;
    boolean searchPrf = false;

    public ForUser() {
        setContentPane(contentPane);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);

        setModal(true);


        Controller.getInstance().initialize(this);

        filterExp_cb.addItem("Не выбрано");
        filterExp_cb.addItem("Прямые");
        filterExp_cb.addItem("Косвенные");

        filterOp_cb.addItem("Не выбрано");
        filterOp_cb.addItem("Основные");
        filterOp_cb.addItem("Обслуживающие");
        filterOp_cb.addItem("Уравленческие");

        addExpButton.setActionCommand("showAddExpFrame");
        addExpButton.addActionListener(Controller.getInstance());
        addRButton.setActionCommand("showAddResourcesFrame");
        addRButton.addActionListener(Controller.getInstance());
        AddOpButton.setActionCommand("showAddOperationsFrame");
        AddOpButton.addActionListener(Controller.getInstance());
        AddPrButton.setActionCommand("showAddProductionFrame");
        AddPrButton.addActionListener(Controller.getInstance());
        generateReportButton.setActionCommand("generateReporter");
        generateReportButton.addActionListener(Controller.getInstance());
        buttonCancel.setActionCommand("exitUser");
        buttonCancel.addActionListener(Controller.getInstance());

        tableExpenses.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delExpButton.setEnabled(true);
                editExpButton.setEnabled(true);
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
        tableResources.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delRButton.setEnabled(true);
                editRButton.setEnabled(true);
                addExptoRButton.setEnabled(true);
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
        tableOperation.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delOpButton.setEnabled(true);
                EditOpButton.setEnabled(true);
                transferOpButton.setEnabled(true);
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
        tableProduction.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DelPrButton.setEnabled(true);
                EditPrButton.setEnabled(true);
                transferPrButton.setEnabled(true);
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

        delExpButton.setActionCommand("delExpenses");
        delExpButton.addActionListener(Controller.getInstance());
        delExpButton.setEnabled(false);
        delRButton.setActionCommand("delResources");
        delRButton.addActionListener(Controller.getInstance());
        delRButton.setEnabled(false);
        delOpButton.setActionCommand("delOperation");
        delOpButton.addActionListener(Controller.getInstance());
        delOpButton.setEnabled(false);
        DelPrButton.setActionCommand("delProduction");
        DelPrButton.addActionListener(Controller.getInstance());
        DelPrButton.setEnabled(false);

        editExpButton.setActionCommand("showEditExpensesframe");
        editExpButton.addActionListener(Controller.getInstance());
        editExpButton.setEnabled(false);
        editRButton.setActionCommand("showEditResourcesframe");
        editRButton.addActionListener(Controller.getInstance());
        editRButton.setEnabled(false);
        EditOpButton.setActionCommand("showEditOperationframe");
        EditOpButton.addActionListener(Controller.getInstance());
        EditOpButton.setEnabled(false);
        EditPrButton.setActionCommand("showEditProductionframe");
        EditPrButton.addActionListener(Controller.getInstance());
        EditPrButton.setEnabled(false);

        addExptoRButton.setActionCommand("showAddExptoRframe");
        addExptoRButton.addActionListener(Controller.getInstance());
        addExptoRButton.setEnabled(false);

        transferOpButton.setActionCommand("transferOperation");
        transferOpButton.addActionListener(Controller.getInstance());
        transferOpButton.setEnabled(false);
        transferPrButton.setActionCommand("transferProduction");
        transferPrButton.addActionListener(Controller.getInstance());
        transferPrButton.setEnabled(false);

        filterOp_cb.setActionCommand("filterOp");
        filterOp_cb.addActionListener(Controller.getInstance());
        filterExp_cb.setActionCommand("filterExp");
        filterExp_cb.addActionListener(Controller.getInstance());

        searchExpButton.setActionCommand("searchExp");
        searchExpButton.addActionListener(Controller.getInstance());
        cancelSearchExpButton.setActionCommand("cancelSearchExp");
        cancelSearchExpButton.addActionListener(Controller.getInstance());
        searchResButton.setActionCommand("searchRes");
        searchResButton.addActionListener(Controller.getInstance());
        cancelSearchResButton.setActionCommand("cancelSearchRes");
        cancelSearchResButton.addActionListener(Controller.getInstance());
        searchOpButton.setActionCommand("searchOp");
        searchOpButton.addActionListener(Controller.getInstance());
        cancelSearchOpButton.setActionCommand("cancelSearchOp");
        cancelSearchOpButton.addActionListener(Controller.getInstance());
        searchPrButton.setActionCommand("searchPr");
        searchPrButton.addActionListener(Controller.getInstance());
        camcelSearchPrButton.setActionCommand("cancelSearchPr");
        camcelSearchPrButton.addActionListener(Controller.getInstance());


        sortExpName_rb.setActionCommand("sortExpName");
        sortExpName_rb.addActionListener(Controller.getInstance());
        sortExpCost_rb.setActionCommand("sortExpSum");
        sortExpCost_rb.addActionListener(Controller.getInstance());

        sortResName_rb.setActionCommand("sortResName");
        sortResName_rb.addActionListener(Controller.getInstance());
        sortResCost_rb.setActionCommand("sortResSum");
        sortResCost_rb.addActionListener(Controller.getInstance());

        sortOpName_rb.setActionCommand("sortOpName");
        sortOpName_rb.addActionListener(Controller.getInstance());
        sortOpCost_rb.setActionCommand("sortOpSum");
        sortOpCost_rb.addActionListener(Controller.getInstance());

        sortPrName_rb.setActionCommand("sortPrName");
        sortPrName_rb.addActionListener(Controller.getInstance());
        sortPrProfit_rb.setActionCommand("sortPrProfit");
        sortPrProfit_rb.addActionListener(Controller.getInstance());
        sortPrCost_rb.setActionCommand("sortPrSum");
        sortPrCost_rb.addActionListener(Controller.getInstance());

        notSortExp_rb.setActionCommand("notSortExp");
        notSortExp_rb.addActionListener(Controller.getInstance());
        notSortExp_rb.setSelected(true);
        notSortRes_rb.setActionCommand("notSortRes");
        notSortRes_rb.addActionListener(Controller.getInstance());
        notSortRes_rb.setSelected(true);
        notSortOp_rb.setActionCommand("notSortOp");
        notSortOp_rb.addActionListener(Controller.getInstance());
        notSortOp_rb.setSelected(true);
        notSortPr_rb.setActionCommand("notSortPr");
        notSortPr_rb.addActionListener(Controller.getInstance());
        notSortPr_rb.setSelected(true);

        driverPt_tf.getDocument().addDocumentListener(listener_driverObj);
        driverOp_tf.getDocument().addDocumentListener(listener_driverOp);

        searchExp_tf.getDocument().addDocumentListener(listener_searchExp);
        searchRes_tf.getDocument().addDocumentListener(listener_searchRes);
        searchOp_tf.getDocument().addDocumentListener(listener_searchOp);
        searchPr_tf.getDocument().addDocumentListener(listener_searchPr);
    }

    public JTable gettableExp() {
        return tableExpenses;
    }

    public JTable gettableR() {
        return tableResources;
    }

    public JTable getTableOp() {
        return tableOperation;
    }

    public JTable getTablePr() {
        return tableProduction;
    }

    public String[] getSelectedExp() {
        int[] sel1 = tableExpenses.getSelectedRows();
        String[] mas = new String[4];
        mas[0] = (String) tableExpenses.getValueAt(sel1[0], 0);
        mas[1] = (String) tableExpenses.getValueAt(sel1[0], 1);
        mas[2] = (String) tableExpenses.getValueAt(sel1[0], 2);
        mas[3] = (String) tableExpenses.getValueAt(sel1[0], 3);
        return mas;
    }

    public String[] getSelectedRes() {
        int[] sel1 = tableResources.getSelectedRows();
        String[] mas = new String[2];
        mas[0] = (String) tableResources.getValueAt(sel1[0], 0);
        mas[1] = (String) tableResources.getValueAt(sel1[0], 1);
        return mas;
    }

    public String[] getSelectedOp() {
        int[] sel1 = tableOperation.getSelectedRows();
        String[] mas = new String[3];
        mas[0] = (String) tableOperation.getValueAt(sel1[0], 0);
        mas[1] = (String) tableOperation.getValueAt(sel1[0], 1);
        mas[2] = (String) tableOperation.getValueAt(sel1[0], 2);
        return mas;
    }

    public String[] getSelectedPr() {
        int[] sel1 = tableProduction.getSelectedRows();
        String[] mas = new String[3];
        mas[0] = (String) tableProduction.getValueAt(sel1[0], 0);
        mas[1] = (String) tableProduction.getValueAt(sel1[0], 1);
        mas[2] = (String) tableProduction.getValueAt(sel1[0], 2);
        return mas;
    }

    public String[] getSelectedExpN() {
        int[] sel1 = tableExpenses.getSelectedRows();
        String[] sel = new String[sel1.length];
        for (var i = 0; i < sel1.length; i++) {
            sel[i] = (String) tableExpenses.getValueAt(sel1[i], 0);
        }
        System.out.println(sel[0]);
        return sel;
    }

    public String[] getSelectedResN() {
        int[] sel1 = tableResources.getSelectedRows();
        String[] sel = new String[sel1.length];
        for (var i = 0; i < sel1.length; i++) {
            sel[i] = (String) tableResources.getValueAt(sel1[i], 0);
        }
        System.out.println(sel[0]);
        return sel;
    }

    public String[] getSelectedOpN() {
        int[] sel1 = tableOperation.getSelectedRows();
        String[] sel = new String[sel1.length];
        for (var i = 0; i < sel1.length; i++) {
            sel[i] = (String) tableOperation.getValueAt(sel1[i], 0);
        }
        System.out.println(sel[0]);
        return sel;
    }

    public String[] getSelectedPrN() {
        int[] sel1 = tableProduction.getSelectedRows();
        String[] sel = new String[sel1.length];
        for (var i = 0; i < sel1.length; i++) {
            sel[i] = (String) tableProduction.getValueAt(sel1[i], 0);
        }
        System.out.println(sel[0]);
        return sel;
    }

    public String getResources() {
        return (String) nameRes_cb.getSelectedItem();
    }

    public JComboBox getNameRes() {
        return nameRes_cb;
    }

    public String getOperation() {
        return (String) nameOp_cb.getSelectedItem();
    }

    public JComboBox getNameOp() {
        return nameOp_cb;
    }

    public JTextField getDriverOp_tf() {
        return driverOp_tf;
    }

    public JTextField getDriverPt_tf() {
        return driverPt_tf;
    }

    public JTextField getSearchExp_tf() {
        return searchExp_tf;
    }

    public JTextField getSearchRes_tf() {
        return searchRes_tf;
    }

    public JTextField getSearchOp_tf() {
        return searchOp_tf;
    }

    public JTextField getSearchPr_tf() {
        return searchPr_tf;
    }

    public JButton getDelExpButton() {
        return delExpButton;
    }

    public JButton getDelRButton() {
        return delRButton;
    }

    public JButton getDelOpButton() {
        return delOpButton;
    }

    public JButton getDelPrButton() {
        return DelPrButton;
    }

    public JButton getEditExpButton() {
        return editExpButton;
    }

    public JButton getEditRButton() {
        return editRButton;
    }

    public JButton getEditOpButton() {
        return EditOpButton;
    }

    public JButton getEditPrButton() {
        return EditPrButton;
    }

    public JButton getAddExptoRButton() {
        return addExptoRButton;
    }

    public JButton getTransferOpButton() {
        return transferOpButton;
    }

    public JButton getTransferPrButton() {
        return transferPrButton;
    }

    public JComboBox getFilterExp_cb() {
        return filterExp_cb;
    }

    public JComboBox getFilterOp_cb() {
        return filterOp_cb;
    }

    DocumentListener listener_driverObj = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            try {
                int x = Integer.parseInt(driverPt_tf.getText().trim());
                if (x <= 0) {
                    driverObj_m.setText("Драйвер не должен быть меньше 1");
                    driverObj_m.setVisible(true);
                    driverObjf = false;
                } else if (x > 100) {
                    driverObj_m.setText("Драйвер не должен быть больше 100");
                    driverObj_m.setVisible(true);
                    driverObjf = false;
                } else {
                    driverObj_m.setVisible(false);
                    driverObjf = true;
                }
            } catch (NumberFormatException ev) {
                driverObj_m.setText("Поле должно содержать только цифры");
                driverObj_m.setVisible(true);
                driverObjf = false;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            try {
                int x = Integer.parseInt(driverPt_tf.getText().trim());
                if (x <= 0) {
                    driverObj_m.setText("Драйвер не должен быть меньше 1");
                    driverObj_m.setVisible(true);
                    driverObjf = false;
                } else if (x > 100) {
                    driverObj_m.setText("Драйвер не должен быть больше 100");
                    driverObj_m.setVisible(true);
                    driverObjf = false;
                } else {
                    driverObj_m.setVisible(false);
                    driverObjf = true;
                }
            } catch (NumberFormatException ev) {
                if (driverPt_tf.getText().trim().equals("")) {
                    driverObj_m.setText("Поле не должно быть пустым");
                } else {
                    driverObj_m.setText("Поле должно содержать только цифры");
                }
                driverObj_m.setVisible(true);
                driverObjf = false;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    public boolean getCorrectdrOP() {
        boolean flag = false;
        if (driverOp_tf.getText().trim().equals("")) driverOp_m.setText("Поле не должно быть пустым");
        else if (driverOpf == true) {
            flag = true;
        }
        return flag;
    }
    DocumentListener listener_driverOp = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            try {
                int x = Integer.parseInt(driverOp_tf.getText().trim());
                if (x <= 0) {
                    driverOp_m.setText("Драйвер не должен быть меньше 1");
                    driverOp_m.setVisible(true);
                    driverOpf = false;
                } else if (x > 100) {
                    driverOp_m.setText("Драйвер не должен быть больше 100");
                    driverOp_m.setVisible(true);
                    driverOpf = false;
                } else {
                    driverOp_m.setVisible(false);
                    driverOpf = true;
                }
            } catch (NumberFormatException ev) {
                driverOp_m.setText("Поле должно содержать только цифры");
                driverOp_m.setVisible(true);
                driverOpf = false;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            try {
                int x = Integer.parseInt(driverOp_tf.getText().trim());
                if (x <= 0) {
                    driverOp_m.setText("Драйвер не должен быть меньше 1");
                    driverOp_m.setVisible(true);
                    driverOpf = false;
                } else if (x > 100) {
                    driverOp_m.setText("Драйвер не должен быть больше 100");
                    driverOp_m.setVisible(true);
                    driverOpf = false;
                } else {
                    driverOp_m.setVisible(false);
                    driverOpf = true;
                }
            } catch (NumberFormatException ev) {
                if (driverOp_tf.getText().trim().equals("")) {
                    driverOp_m.setText("Поле не должно быть пустым");
                } else {
                    driverOp_m.setText("Поле должно содержать только цифры");
                }
                driverOp_m.setVisible(true);
                driverOpf = false;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };


    public boolean getCorrectdrObj() {
        boolean flag = false;
        if (driverPt_tf.getText().trim().equals("")) driverObj_m.setText("Поле не должно быть пустым");
        else if (driverObjf == true) {
            flag = true;
        }
        return flag;
    }

    DocumentListener listener_searchExp = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchExp_tf.getText().trim().equals("")) {
                searchExp_lb.setText("Поле не должно быть пустым");
                searchExp_lb.setVisible(true);
                searchExpf = false;
            } else {
                searchExp_lb.setVisible(false);
                searchExpf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchExp_tf.getText().trim().equals("")) {
                searchExp_lb.setText("Поле не должно быть пустым");
                searchExp_lb.setVisible(true);
                searchExpf = false;
            } else {
                searchExp_lb.setVisible(false);
                searchExpf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrectSearchExp() {
        boolean flag = false;
        if (searchExp_tf.getText().trim().equals("")) searchExp_lb.setText("Поле не должно быть пустым");
        else if (searchExpf == true) {
            flag = true;
        }
        return flag;
    }

    DocumentListener listener_searchRes = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchRes_tf.getText().trim().equals("")) {
                searchRes_lb.setText("Поле не должно быть пустым");
                searchRes_lb.setVisible(true);
                searchResf = false;
            } else {
                searchRes_lb.setVisible(false);
                searchResf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchRes_tf.getText().trim().equals("")) {
                searchRes_lb.setText("Поле не должно быть пустым");
                searchRes_lb.setVisible(true);
                searchResf = false;
            } else {
                searchRes_lb.setVisible(false);
                searchResf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrectSearchRes() {
        boolean flag = false;
        if (searchRes_tf.getText().trim().equals("")) searchRes_lb.setText("Поле не должно быть пустым");
        else if (searchResf == true) {
            flag = true;
        }
        return flag;
    }

    DocumentListener listener_searchOp = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchOp_tf.getText().trim().equals("")) {
                searchOp_lb.setText("Поле не должно быть пустым");
                searchOp_lb.setVisible(true);
                searchOpf = false;
            } else {
                searchOp_lb.setVisible(false);
                searchOpf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchOp_tf.getText().trim().equals("")) {
                searchOp_lb.setText("Поле не должно быть пустым");
                searchOp_lb.setVisible(true);
                searchOpf = false;
            } else {
                searchOp_lb.setVisible(false);
                searchOpf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrectSearchOp() {
        boolean flag = false;
        if (searchOp_tf.getText().trim().equals("")) searchOp_lb.setText("Поле не должно быть пустым");
        else if (searchOpf == true) {
            flag = true;
        }
        return flag;
    }

    DocumentListener listener_searchPr = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (searchPr_tf.getText().trim().equals("")) {
                searchPr_lb.setText("Поле не должно быть пустым");
                searchPr_lb.setVisible(true);
                searchPrf = false;
            } else {
                searchPr_lb.setVisible(false);
                searchPrf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (searchPr_tf.getText().trim().equals("")) {
                searchPr_lb.setText("Поле не должно быть пустым");
                searchPr_lb.setVisible(true);
                searchPrf = false;
            } else {
                searchPr_lb.setVisible(false);
                searchPrf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrectSearchPr() {
        boolean flag = false;
        if (searchPr_tf.getText().trim().equals("")) searchPr_lb.setText("Поле не должно быть пустым");
        else if (searchPrf == true) {
            flag = true;
        }
        return flag;
    }

}
