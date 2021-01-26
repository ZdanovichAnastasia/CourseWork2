package DB;

import model.Users;

import java.util.ArrayList;

public class TableUsers implements TablesInterface{
    private static TableUsers instance;
    private Connection Connection;

    private TableUsers() {
        Connection = Connection.getInstance();
    }
    public static synchronized TableUsers getInstance() {
        if (instance == null) {
            instance = new TableUsers();
        }
        return instance;
    }
    @Override
    public void create(){
        String str ="CREATE TABLE IF NOT EXISTS users (\n" +
                "  idUsers INT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  surname VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  phone VARCHAR(45) NULL DEFAULT NULL,\n" +
                "  login VARCHAR(45) NOT NULL,\n" +
                "  password VARCHAR(45) NOT NULL,\n" +
                "  role VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`idUsers`),\n" +
                "  UNIQUE INDEX `idUsers_UNIQUE` (`idUsers` ASC) VISIBLE);";
        Connection.execute(str);
    };

    @Override
    public String find(Object obj) {
        Users users = (Users)obj;
        String str = "SELECT * From users Where login = '" + users.getLogin() +
                "' and password = '" + users.getPassword() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String status = "";
        for (String[] item: result)
            status = item[6];
        return status;
    }

    public String findLoginUser(int id) {
        String str = "SELECT * From users Where idUsers = '" + id + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        String status = "";
        for (String[] item: result)
            status = item[4];
        return status;
    }
    @Override
    public void insert(Object obj) {
        Users users = (Users)obj;
        String str = "INSERT INTO users (name, surname, phone, login, password, role) VALUES('" + users.getName()
                + "', '" + users.getSurname() + "', '" + users.getPhone() + "', '" + users.getLogin()
                + "', '" + users.getPassword() + "', '" + users.getRole() + "')";
        Connection.execute(str);
    }

    public Users selectUsers(Users obj) {
        String str = "SELECT * From users Where login = '" + obj.getLogin() +
                "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        Users user = new Users();
        for (String[] items: result){
            user.setIdUser(Integer.parseInt(items[0]));
            user.setName(items[1]);
            user.setSurname(items[2]);
            user.setPhone(items[3]);
            user.setLogin(items[4]);
            user.setPassword(items[5]);
        }
        return user;
    }

    public ArrayList<Users> selectAll() {
        String str = "SELECT * From users";
        ArrayList<String[]> result = Connection.getArrayResult(str);
        ArrayList<Users> listUsers = new ArrayList<>();
        for (String[] items: result){
            Users user = new Users();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setName(items[1]);
            user.setSurname(items[2]);
            user.setPhone(items[3]);
            user.setLogin(items[4]);
            user.setPassword(items[5]);
            user.setRole(items[6]);
            listUsers.add(user);
        }
        return listUsers;
    }
    //@Override
    public void update(Users obj, String log){
        String str = "UPDATE users SET name = '"+obj.getName()+"', surname = '"+obj.getSurname()+"', phone = '"+obj.getPhone()+
                "' WHERE login = '" + log + "'";
        Connection.execute(str);
    }

    public void deleteLog(String log) {
        String str = "DELETE FROM users WHERE  login = '" + log+"'";
        Connection.execute(str);
    }

    @Override
    public void update(Object obj, String id) {

    }

    @Override
    public void delete(String id) {
        String str = "DELETE FROM users WHERE idUsers = '" + id+"'";
        Connection.execute(str);
    }
}

