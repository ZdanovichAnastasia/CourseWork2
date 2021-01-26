package model.client;

import view.EnterForm;

public class Main {

    public static void main(String[] args) {
        EnterForm dialog = new EnterForm();
        dialog.setTitle("Авторизация");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
