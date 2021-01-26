package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddResources extends JDialog {
    private JPanel contentPane;
    private JButton buttonAddR;
    private JButton buttonCancel;
    private JTextField nameRes_tf;
    private JTable Exptable;
    private JButton editButton;
    private JScrollPane scrollExp;
    private JLabel Exp_lb;
    private JLabel name_lb;
    private JButton AddExpButton;
    private JLabel nameRes_lb;
    boolean nameRf = false;

    public AddResources() {
        setContentPane(contentPane);
        setModal(true);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);

        buttonAddR.setActionCommand("addnewResources");
        buttonAddR.addActionListener(Controller.getInstance());
        buttonAddR.setEnabled(false);
        AddExpButton.setActionCommand("addnewExptoResources");
        AddExpButton.addActionListener(Controller.getInstance());
        AddExpButton.setVisible(false);
        AddExpButton.setEnabled(false);
        editButton.setActionCommand("editResources");
        editButton.addActionListener(Controller.getInstance());
        editButton.setVisible(false);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        Exptable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buttonAddR.setEnabled(true);
                AddExpButton.setEnabled(true);
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
        nameRes_tf.getDocument().addDocumentListener(listener_nameR);
    }

    public JTextField getNameRes_tf() {
        return nameRes_tf;
    }

    public JTable getExptable() {
        return Exptable;
    }

    public String[] getSelectedExp() {
        int[] sel1 = Exptable.getSelectedRows();
        String[] sel = new String[sel1.length];
        for (var i = 0; i < sel1.length; i++) {
            sel[i] = (String) Exptable.getValueAt(sel1[i], 0);
        }
        return sel;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getButtonAddR() {
        return buttonAddR;
    }

    public JButton getAddExpButton() {
        return AddExpButton;
    }

    public void setNameRes_tf(String nameRes_tf) {
        this.nameRes_tf.setText(nameRes_tf);
    }

    public JLabel getExp_lb() {
        return Exp_lb;
    }

    public JScrollPane getScrollExp() {
        return scrollExp;
    }

    public JLabel getName_lb() {
        return name_lb;
    }

    DocumentListener listener_nameR = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String name = nameRes_tf.getText().trim();
            if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                nameRes_lb.setText("Поле должно содержать только буквы");
                nameRes_lb.setVisible(true);
                nameRf = false;
            } else {
                nameRes_lb.setVisible(false);
                nameRf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String name = nameRes_tf.getText().trim();
            if (nameRes_tf.getText().trim().equals("")) {
                nameRes_lb.setText("Поле не должно быть пустым");
                nameRes_lb.setVisible(true);
                nameRf = false;
            } else if (name.matches("[a-zA-z\\sа-яА-Я]+") == false) {
                nameRes_lb.setText("Поле должно содержать только буквы");
                nameRes_lb.setVisible(true);
                nameRf = false;
            } else {
                nameRes_lb.setVisible(false);
                nameRf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (nameRes_tf.getText().trim().equals("")) nameRes_lb.setText("Поле не должно быть пустым");
        else if (nameRf == true) {
            flag = true;
        }
        return flag;
    }

}
