package DB;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Connection {
    private static Connection instance;

    protected java.sql.Connection connect;
    protected Statement statement;
    private ResultSet resultSet;
    ArrayList<String[]> masResult;

    public Connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&serverTimezone=UTC", "root", "1234");
            statement = connect.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS kurs");
            statement.close();
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/kurs?useUnicode=true&serverTimezone=UTC", "Sasha", "Sasha2001!");
            statement = connect.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Problem with JDBC Driver");
            e.printStackTrace();
        }
    }

    public void execute(String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public ArrayList<String[]> getArrayResult(String str){
        masResult = new ArrayList<String[]>();
        try {
            resultSet = statement.executeQuery(str);
            int count = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                String[] arrayString = new String[count];
                for (int i = 1;  i <= count; i++)
                    arrayString[i - 1] = resultSet.getString(i);

                masResult.add(arrayString);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return masResult;
    }

    public void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
