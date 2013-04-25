/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.listeners;

import com.thenewfmi.currentsession.User;
import com.thenewfmi.forms.MainApplication;
import com.thenewfmi.forms.UI;
import com.thenewfmi.schedule.Schedule;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author SyncCarryOn
 */
public class LoginListener implements ActionListener {
    JFrame loginFrame;
    UI userInt;
    Connection conn;
    
    public LoginListener(UI usri, JFrame loginFrame, Connection conn) {
        userInt = usri;
        this.conn = conn;
        this.loginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result;
            String accname = userInt.getAccField().getText();
            result = stmt.executeQuery("SELECT * from accounts WHERE username = '" + accname + "'");
            result.next();
            
            if(result.getRow() == 0){
                JOptionPane.showMessageDialog(null, "No such account exists.");
                return;
            }
            String targetpass = result.getString("Password");
            
            if(userInt.getPassField().getText().compareTo(targetpass) == 0){
                
                int specialnost = result.getInt("Specialty");
                int kurs = result.getInt("Kurs");
                int potok = result.getInt("Potok");
                int grupa = result.getInt("Grupa");
                User acc = new User(specialnost,kurs,potok,grupa);
                User.activeUser = acc;
                
                loginFrame.dispose();
                conn.close();
                
                MainApplication app = new MainApplication();
                
            }
            else{
                JOptionPane.showMessageDialog(null, "Wrong password.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
