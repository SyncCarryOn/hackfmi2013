/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.forms;

import com.thenewfmi.listeners.LoginListener;
import com.thenewfmi.listeners.RegisterListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author SyncCarryOn
 */
public class LoginForm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "");
            JFrame f = new JFrame();
            UI userInt = new UI(conn);
            f.add(userInt);
            f.setSize(350, 200);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            f.setResizable(false);
            userInt.logIn.addActionListener(new LoginListener(userInt, f, conn));
            userInt.register.addActionListener(new RegisterListener(f, true, conn));
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
