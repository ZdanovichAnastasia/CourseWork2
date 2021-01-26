package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class AddOperations extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameOp_tf;
    private JComboBox typeOp_cb;
    private JButton editButton;
    private JLabel nameOp_lb;
    boolean nameOf = false;

    public AddOperations() {
        setContentPane(contentPane);
        setModal(true);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);

        buttonOK.setActionCommand("addnewOperation");
        buttonOK.addActionListener(Controller.getInstance());
        editButton.setActionCommand("editOperation");
        editButton.addActionListener(Controller.getInstance());
        editButton.setVisible(false);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        typeOp_cb.addItem("Основные");
        typeOp_cb.addItem("Обслуживающие");
        typeOp_cb.addItem("Уравленческие");
        nameOp_tf.getDocument().addDocumentListener(listener_nameOp);
    }

    public JTextField getNameOp_tf() {
        return nameOp_tf;
    }

    public JComboBox getTypeOp_cb() {
        return typeOp_cb;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    public void setNameOp_tf(String nameOp_tf) {
        this.nameOp_tf.setText(nameOp_tf);
    }

    public void setTypeOp_cb(String typeOp_cb) {
        this.typeOp_cb.setSelectedItem(typeOp_cb);
    }

    DocumentListener listener_nameOp = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String name = nameOp_tf.getText().trim();
            if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                nameOp_lb.setText("Поле должно содержать только буквы");
                nameOp_lb.setVisible(true);
                nameOf = false;
            } else {
                nameOp_lb.setVisible(false);
                nameOf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String name = nameOp_tf.getText().trim();
            if (nameOp_tf.getText().trim().equals("")) {
                nameOp_lb.setText("Поле не должно быть пустым");
                nameOp_lb.setVisible(true);
                nameOf = false;
            } else if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                nameOp_lb.setText("Поле должно содержать только буквы");
                nameOp_lb.setVisible(true);
                nameOf = false;
            } else {
                nameOp_lb.setVisible(false);
                nameOf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (nameOp_tf.getText().trim().equals("")) nameOp_lb.setText("Поле не должно быть пустым");
        else if (nameOf == true) {
            flag = true;
        }
        return flag;
    }

}

