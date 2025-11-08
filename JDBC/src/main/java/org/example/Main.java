package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.*;

public class Main {
    public static void main(String[] args){
        String url = "jdbc:mysql://127.0.0.1:3306/learning_springboot?user=root";
        String user = "root";
        String password = "admin";
        String query = "select * from employee;";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error in loading class");
            e.printStackTrace();
            return;
        }

        try(Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(query)){

            System.out.println("Connected...!");
            while(res.next()){
                String name = res.getString("name");
                System.out.print(name+"\t\t");
                String role = res.getString("role");
                System.out.print(role+"\t\t");
                String sal = res.getString("salary");
                System.out.println(sal);
            }
            System.out.println("Query Executed...!");
        }
        catch(SQLException e){
            System.err.println("DataBase Error");
            e.printStackTrace();
        }
        System.out.println("Connection Closed...!");
    }
}