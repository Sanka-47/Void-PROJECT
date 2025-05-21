package model;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MySQL2 implements Serializable {

    public String ip;
    public String port;
    public String pw;
    public String un;
    public String dbname;

    public String dump;
    public String path;

    private static Connection connection;

    public static void createConnection() throws Exception {
        if (connection == null) {

            FileInputStream inputStream = new FileInputStream("dbinfo.ser");

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            MySQL2 db = (MySQL2) objectInputStream.readObject();
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + db.ip + ":" + db.port + "/" + db.dbname, db.un, db.pw);
//KALINDUprado47
        }
    }

    public static ResultSet executeSearch(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeQuery(query);
    }

    public static Integer executeIUD(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeUpdate(query);
    }

}
