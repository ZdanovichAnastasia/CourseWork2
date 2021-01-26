package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class AddProduction extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton buttonCancel;
    private JTextField prName_tf;
    private JTextField numPr_tf;
    private JButton editButton;
    private JTextField profit_tf;
    private JLabel prName_lb;
    private JLabel profit_lb;
    private JLabel numPr_lb;
    boolean namePf = false;
    boolean profitPf = false;
    boolean numPf = false;

    public AddProduction() {
        setContentPane(contentPane);
        setModal(true);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        addButton.setActionCommand("addnewProduction");
        addButton.addActionListener(Controller.getInstance());
        editButton.setActionCommand("editProduction");
        editButton.addActionListener(Controller.getInstance());
        editButton.setVisible(false);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        prName_tf.getDocument().addDocumentListener(listener_nameP);
        profit_tf.getDocument().addDocumentListener(listener_profitP);
        numPr_tf.getDocument().addDocumentListener(listener_numP);
    }

    public JTextField getNumPr_tf() {
        return numPr_tf;
    }

    public JTextField getPrName_tf() {
        return prName_tf;
    }

    public JTextField getProfit_tf() {
        return profit_tf;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setNumPr_tf(String numPr_tf) {
        this.numPr_tf.setText(numPr_tf);
    }

    public void setPrName_tf(String prName_tf) {
        this.prName_tf.setText(prName_tf);
    }

    public void setProfit_tf(String profit_tf) {
        this.profit_tf.setText(profit_tf);
    }

    DocumentListener listener_nameP = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String name = prName_tf.getText().trim();
            if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                prName_lb.setText("Поле должно содержать только буквы");
                prName_lb.setVisible(true);
                namePf = false;
            } else {
                prName_lb.setVisible(false);
                namePf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String name = prName_tf.getText().trim();
            if (prName_tf.getText().trim().equals("")) {
                prName_lb.setText("Поле не должно быть пустым");
                prName_lb.setVisible(true);
                namePf = false;
            } else if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                prName_lb.setText("Поле должно содержать только буквы");
                prName_lb.setVisible(true);
                namePf = false;
            } else {
                prName_lb.setVisible(false);
                namePf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_profitP = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            try {
                double x = Double.parseDouble(profit_tf.getText().trim());
                if (x <= 0) {
                    profit_lb.setText("Стоимость не должна быть меньше 1");
                    profit_lb.setVisible(true);
                    profitPf = false;
                } else {
                    profit_lb.setVisible(false);
                    profitPf = true;
                }
            } catch (NumberFormatException ev) {
                profit_lb.setText("Поле должно содержать только цифры");
                profit_lb.setVisible(true);
                profitPf = false;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            try {
                double x = Double.parseDouble(profit_tf.getText().trim());
                if (x <= 0) {
                    profit_lb.setText("Стоимость не должна быть меньше 1");
                    profit_lb.setVisible(true);
                    profitPf = false;
                } else {
                    profit_lb.setVisible(false);
                    profitPf = true;
                }
            } catch (NumberFormatException ev) {
                if (profit_tf.getText().trim().equals("")) {
                    profit_lb.setText("Поле не должно быть пустым");
                } else {
                    profit_lb.setText("Поле должно содержать только цифры");
                }
                profit_lb.setVisible(true);
                profitPf = false;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_numP = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            try {
                int x = Integer.parseInt(numPr_tf.getText().trim());
                if (x <= 0) {
                    numPr_lb.setText("Количество не должно быть меньше 1");
                    numPr_lb.setVisible(true);
                    numPf = false;
                } else if (x > 1000000000) {
                    numPr_lb.setText("Количество не может быть больше 1 000 000 000");
                    numPr_lb.setVisible(true);
                    numPf = false;
                } else {
                    numPr_lb.setVisible(false);
                    numPf = true;
                }
            } catch (NumberFormatException ev) {
                numPr_lb.setText("Поле должно содержать только цифры");
                numPr_lb.setVisible(true);
                numPf = false;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            try {
                int x = Integer.parseInt(numPr_tf.getText().trim());
                if (x <= 0) {
                    numPr_lb.setText("Количество не должно быть меньше 1");
                    numPr_lb.setVisible(true);
                    numPf = false;
                } else if (x > 1000000000) {
                    numPr_lb.setText("Количество не может быть больше 1 000 000 000");
                    numPr_lb.setVisible(true);
                    numPf = false;
                } else {
                    numPr_lb.setVisible(false);
                    numPf = true;
                }
            } catch (NumberFormatException ev) {
                if (numPr_tf.getText().trim().equals("")) {
                    numPr_lb.setText("Поле не должно быть пустым");
                } else {
                    numPr_lb.setText("Поле должно содержать только цифры");
                }
                numPr_lb.setVisible(true);
                numPf = false;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (prName_tf.getText().trim().equals("")) prName_lb.setText("Поле не должно быть пустым");
        if (profit_tf.getText().trim().equals("")) profit_lb.setText("Поле не должно быть пустым");
        if (numPr_tf.getText().trim().equals("")) numPr_lb.setText("Поле не должно быть пустым");
        else if (namePf == true && profitPf == true && numPf == true) {
            flag = true;
        }
        return flag;
    }

}
