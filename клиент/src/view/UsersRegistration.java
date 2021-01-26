package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFormattedTextField;

public class UsersRegistration extends JFrame {
    private JPanel contentPane;
    private JTextField login_tf;
    private JTextField password_tf;
    private JButton buttonRegistration;
    private JButton buttonCanselRegistration;
    private JTextField name_tf;
    private JTextField surname_tf;
    private JFormattedTextField phone_jtf;
    private JComboBox comboBox1;
    private JLabel nameC;
    private JButton AddUserButton;
    private JButton CAddButton;
    private JLabel name_lb;
    private JLabel surname_lb;
    private JLabel login_lb;
    private JLabel password_lb;
    private JLabel phone_lb;
    private JButton EditUserButton;
    private JComboBox operator_cb;
    boolean namef = false;
    boolean surnamef = false;
    boolean phonef = false;
    boolean loginf = false;
    boolean passwordf = false;
    private  String value;

    public UsersRegistration() {
        setContentPane(contentPane);

        comboBox1.setVisible(false);
        nameC.setVisible(false);

        Controller.getInstance().initialize(this);

        operator_cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                value = operator_cb.getSelectedItem().toString();
                MaskFormatter phoneFormatter;
                try {
                    if(value.equals("Беларусь")) {
                        phoneFormatter = new MaskFormatter("+375-##-###-##-##");
                        phoneFormatter.setPlaceholderCharacter('0');
                        changeMask(phoneFormatter);
                    }
                    else if(value.equals("Россия")) {
                        phoneFormatter = new MaskFormatter("+7-###-###-##-##");
                        phoneFormatter.setPlaceholderCharacter('0');
                        changeMask(phoneFormatter);
                    }
                    else if(value.equals("Польша")) {
                        phoneFormatter = new MaskFormatter("+48-###-###-###");
                        phoneFormatter.setPlaceholderCharacter('0');
                        changeMask(phoneFormatter);
                    }
                    else if(value.equals("Украина")) {
                        phoneFormatter = new MaskFormatter("+380-##-###-##-##");
                        phoneFormatter.setPlaceholderCharacter('0');
                        changeMask(phoneFormatter);
                    }
                    else if(value.equals("Латвия")) {
                        phoneFormatter = new MaskFormatter("+218-##-###-##-##");
                        phoneFormatter.setPlaceholderCharacter('0');
                        changeMask(phoneFormatter);
                    }
                    phone_jtf.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonRegistration.setActionCommand("registrationUsers");
        buttonRegistration.addActionListener(Controller.getInstance());
        AddUserButton.setVisible(false);
        CAddButton.setVisible(false);
        CAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        AddUserButton.setActionCommand("addUserCompany");
        AddUserButton.addActionListener(Controller.getInstance());
        buttonCanselRegistration.setActionCommand("backToAutorization");
        buttonCanselRegistration.addActionListener(Controller.getInstance());
        EditUserButton.setActionCommand("editUser");
        EditUserButton.addActionListener(Controller.getInstance());
        EditUserButton.setVisible(false);

        name_tf.getDocument().addDocumentListener(listener_name);
        surname_tf.getDocument().addDocumentListener(listener_surname);
        login_tf.getDocument().addDocumentListener(listener_login);
        password_tf.getDocument().addDocumentListener(listener_password);

    }

    public JTextField getName_tf() {
        return name_tf;
    }

    public JTextField getSurname_tf() {
        return surname_tf;
    }

    public JTextField getPhone_tf() {
        return phone_jtf;
    }

    public JTextField getLogin_tf() {
        return login_tf;
    }

    public JTextField getPassword_tf() {
        return password_tf;
    }

    public void setName_tf(String name_tf) {
        this.name_tf.setText(name_tf);
    }

    public void setSurname_tf(String surname_tf) {
        this.surname_tf.setText(surname_tf);
    }

    public void setPhone_tf(String phone_tf) {
        this.phone_jtf.setText(phone_tf);
    }

    public void setLogin_tf(String login_tf) {
        this.login_tf.setText(login_tf);
    }

    public void setPassword_tf(String password_tf) {
        this.password_tf.setText(password_tf);
    }

    public void setComboBox1(String comboBox1) {
        this.comboBox1.setSelectedItem(comboBox1);
    }
    public void setOperator_cb (String operator_cb) {
        this.operator_cb.setSelectedItem(operator_cb);
    }

    public String getCompany() {
        return (String) comboBox1.getSelectedItem();
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public void visibleEditOwner(){
        login_tf.setEnabled(false);
        password_tf.setEnabled(false);
        buttonRegistration.setVisible(false);
        buttonCanselRegistration.setVisible(false);
        EditUserButton.setVisible(true);
    }
    public void visibleEditUser(){
        login_tf.setEnabled(false);
        password_tf.setEnabled(false);
        comboBox1.setVisible(true);
        nameC.setVisible(true);
        buttonRegistration.setVisible(false);
        buttonCanselRegistration.setVisible(false);
        EditUserButton.setVisible(true);
        comboBox1.setEnabled(false);
    }
    public void visibleAddUser(){
        comboBox1.setVisible(true);
        nameC.setVisible(true);
        buttonRegistration.setVisible(false);
        buttonCanselRegistration.setVisible(false);
        AddUserButton.setVisible(true);
        CAddButton.setVisible(true);
    }


    public JButton getEditUserButton() {
        return EditUserButton;
    }

    DocumentListener listener_name = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String name = name_tf.getText().trim();
            if (name.matches("[a-zA-zа-яА-Я]+") == false) {
                name_lb.setText("Поле должно содержать только буквы");
                name_lb.setVisible(true);
                namef = false;
            } else {
                name_lb.setVisible(false);
                namef = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String name = name_tf.getText().trim();
            if (name_tf.getText().trim().equals("")) {
                name_lb.setText("Поле не должно быть пустым");
                name_lb.setVisible(true);
                namef = false;
            } else if (name.matches("[a-zA-zа-яА-Я]+") == false) {
                name_lb.setText("Поле должно содержать только буквы");
                name_lb.setVisible(true);
                namef = false;
            } else {
                name_lb.setVisible(false);
                namef = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_surname = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String name = surname_tf.getText().trim();
            if (name.matches("[a-zA-zа-яА-Я]+") == false) {
                surname_lb.setText("Поле должно содержать только буквы");
                surname_lb.setVisible(true);
                surnamef = false;
            } else {
                surname_lb.setVisible(false);
                surnamef = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String name = surname_tf.getText().trim();
            if (surname_tf.getText().trim().equals("")) {
                surname_lb.setText("Поле не должно быть пустым");
                surname_lb.setVisible(true);
                surnamef = false;
            } else if (name.matches("[a-zA-zа-яА-Я]+") == false) {
                surname_lb.setText("Поле должно содержать только буквы");
                surname_lb.setVisible(true);
                surnamef = false;
            } else {
                surname_lb.setVisible(false);
                surnamef = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_login = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (login_tf.getText().trim().equals("")) {
                login_lb.setText("Поле не должно быть пустым");
                login_lb.setVisible(true);
                loginf = false;
            } else {
                login_lb.setVisible(false);
                loginf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (login_tf.getText().trim().equals("")) {
                login_lb.setText("Поле не должно быть пустым");
                login_lb.setVisible(true);
                loginf = false;
            } else {
                login_lb.setVisible(false);
                loginf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };
    DocumentListener listener_password = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (password_tf.getText().trim().equals("")) {
                password_lb.setText("Поле не должно быть пустым");
                password_lb.setVisible(true);
                passwordf = false;
            } else {
                password_lb.setVisible(false);
                passwordf = true;
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (password_tf.getText().trim().equals("")) {
                password_lb.setText("Поле не должно быть пустым");
                password_lb.setVisible(true);
                passwordf = false;
            } else {
                password_lb.setVisible(false);
                passwordf = true;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    public boolean getCorrect() {
        boolean flag = false;
        if (name_tf.getText().trim().equals("")) name_lb.setText("Поле не должно быть пустым");
        if (surname_tf.getText().trim().equals("")) surname_lb.setText("Поле не должно быть пустым");
        if (phone_jtf.getText().trim().equals("")) phone_lb.setText("Поле не должно быть пустым");
        if (login_tf.getText().trim().equals("")) login_lb.setText("Поле не должно быть пустым");
        if (password_tf.getText().trim().equals("")) password_lb.setText("Поле не должно быть пустым");
        else if (namef == true && surnamef == true && loginf == true && passwordf == true) {
            flag = true;
        }
        return flag;
    }
    public void changeMask(MaskFormatter phoneFormatter){
        phone_jtf.setFormatterFactory(new DefaultFormatterFactory(phoneFormatter));
        phone_jtf.setValue(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        phone_jtf = new JFormattedTextField();
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("+375-##-###-##-##");
            phoneFormatter.setPlaceholderCharacter('0');
            phone_jtf.setFormatterFactory(new DefaultFormatterFactory(phoneFormatter));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}