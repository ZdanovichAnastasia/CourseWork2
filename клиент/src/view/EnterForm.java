package view;

import controller.Controller;

import javax.swing.*;

public class EnterForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonEnter;
    private JButton buttonRegistration;
    private JTextField textLogin;
    private JPasswordField passwordField1;

    public JTextField getTextLogin() {
        return textLogin;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public EnterForm() {
        setContentPane(contentPane);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        //getRootPane().setDefaultButton(buttonEnter);
        setModal(true);
        Controller.getInstance().initialize(this);
        buttonEnter.addActionListener(Controller.getInstance());
        buttonRegistration.setActionCommand("showRegistrationFrame");
        buttonRegistration.addActionListener(Controller.getInstance());
    }

    public void exit() {
        setDefaultCloseOperation(EnterForm.HIDE_ON_CLOSE);
    }

}
