/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sourey
 */
    import java.sql.*; 
    import java.sql.DriverManager;
import java.sql.Connection;
    
    class MysqlCon{  
    public static void main(String args[]){  
    try{  
    Class.forName("com.mysql.cj.jdbc.Driver");  
    Connection con=DriverManager.getConnection(  
    "jdbc:mysql://127.0.0.1:/jabadb","root","emli1994");  
    //here sonoo is database name, root is username and password  
    Statement stmt=con.createStatement();  
    ResultSet rs=stmt.executeQuery("select * from emp");  
    while(rs.next())  
    System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
    con.close();  
    }
    catch(Exception e){ 
        System.out.println(e);
    }  
    }  
    }  