package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class AddExpenses extends JDialog {
    private JPanel contentPane;
    private JTextField nameExp_tf;
    private JTextField sumExp_tf;
    private JTextField groupExp_tf;
    private JComboBox typeExp_cb;
    private JButton addButton;
    private JButton cancelButton;
    private JButton editButton;
    private JLabel nameExp_lb;
    private JLabel groupExp_lb;
    private JLabel sumExp_lb;
    boolean nameEf = false;
    boolean groupEf = false;
    boolean sumEf = false;

    public AddExpenses() {
        setContentPane(contentPane);
        setModal(true);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);

        addButton.setActionCommand("addnewExpenses");
        addButton.addActionListener(Controller.getInstance());
        editButton.setActionCommand("editExpenses");
        editButton.addActionListener(Controller.getInstance());
        editButton.setVisible(false);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        typeExp_cb.addItem("Прямые");
        typeExp_cb.addItem("Косвенные");

        nameExp_tf.getDocument().addDocumentListener(listener_nameE);
        groupExp_tf.getDocument().addDocumentListener(listener_groupE);
        sumExp_tf.getDocument().addDocumentListener(listener_sumE);
    }

    public JTextField getNameExp_tf() {
        return nameExp_tf;
    }

    public JTextField getGroupExp_tf() {
        return groupExp_tf;
    }

    public JTextField getSumExp_tf() {
        return sumExp_tf;
    }

    public JComboBox getTypeExp_cb() {
        return typeExp_cb;
    }

    public void setNameExp_tf(String nameExp_tf) {
        this.nameExp_tf.setText(nameExp_tf);
    }

    public void setTypeExp_cb(String typeExp_cb) {
        this.typeExp_cb.setSelectedItem(typeExp_cb);
    }

    public void setGroupExp_tf(String groupExp_tf) {
        this.groupExp_tf.setText(groupExp_tf);
    }

    public void setSumExp_tf(String sumExp_tf) {
        this.sumExp_tf.setText(sumExp_tf);
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    DocumentListener listener_nameE = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String name = nameExp_tf.getText().trim();
            if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                nameExp_lb.setText("Поле должно содержать только буквы");
                nameExp_lb.setVisible(true);
                nameEf = false;
            } else {
                nameExp_lb.setVisible(false);
                nameEf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String name = nameExp_tf.getText().trim();
            if (nameExp_tf.getText().trim().equals("")) {
                nameExp_lb.setText("Поле не должно быть пустым");
                nameExp_lb.setVisible(true);
                nameEf = false;
            } else if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                nameExp_lb.setText("Поле должно содержать только буквы");
                nameExp_lb.setVisible(true);
                nameEf = false;
            } else {
                nameExp_lb.setVisible(false);
                nameEf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_groupE = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (groupExp_tf.getText().trim().equals("")) {
                groupExp_lb.setText("Поле не должно быть пустым");
                groupExp_lb.setVisible(true);
                groupEf = false;
            } else {
                groupExp_lb.setVisible(false);
                groupEf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (groupExp_tf.getText().trim().equals("")) {
                groupExp_lb.setText("Поле не должно быть пустым");
                groupExp_lb.setVisible(true);
                groupEf = false;
            } else {
                groupExp_lb.setVisible(false);
                groupEf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_sumE = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            try {
                double x = Double.parseDouble(sumExp_tf.getText().trim());
                if (x <= 0) {
                    sumExp_lb.setText("Сумма не должна быть меньше 1");
                    sumExp_lb.setVisible(true);
                    sumEf = false;
                } else {
                    sumExp_lb.setVisible(false);
                    sumEf = true;
                }
            } catch (NumberFormatException ev) {
                sumExp_lb.setText("Поле должно содержать только цифры");
                sumExp_lb.setVisible(true);
                sumEf = false;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            try {
                double x = Double.parseDouble(sumExp_tf.getText().trim());
                if (x <= 0) {
                    sumExp_lb.setText("Сумма не должна быть меньше 1");
                    sumExp_lb.setVisible(true);
                    sumEf = false;
                } else {
                    sumExp_lb.setVisible(false);
                    sumEf = true;
                }
            } catch (NumberFormatException ev) {
                if (sumExp_tf.getText().trim().equals("")) {
                    sumExp_lb.setText("Поле не должно быть пустым");
                } else {
                    sumExp_lb.setText("Поле должно содержать только цифры");
                }
                sumExp_lb.setVisible(true);
                sumEf = false;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (nameExp_tf.getText().trim().equals("")) nameExp_lb.setText("Поле не должно быть пустым");
        if (groupExp_tf.getText().trim().equals("")) groupExp_lb.setText("Поле не должно быть пустым");
        if (sumExp_tf.getText().trim().equals("")) sumExp_lb.setText("Поле не должно быть пустым");
        else if (nameEf == true && groupEf == true && sumEf == true) {
            flag = true;
        }
        return flag;
    }

}
