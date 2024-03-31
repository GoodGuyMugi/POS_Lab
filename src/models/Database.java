/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;

/**
 *
 * @author D A I S U K E
 */
public class Database {
    public static Connection con;
    public static PreparedStatement s;
    public static ResultSet rs;
    public static void connectionDB(String ip, String database, String username, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database,username, password);

    }
}
