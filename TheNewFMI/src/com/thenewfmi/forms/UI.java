/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import javax.swing.*;

/**
 *
 * @author Sisi
 */
public class UI extends JPanel{
    private JTextField accField,passField;
    JPanel regGroup, buttonGroup,labels;
    JButton logIn, register;
    JLabel firstLabel;
    Icon iconLog= new ImageIcon("log.png");
    Connection conn;
    
    public UI(Connection con) {
        super(new GridLayout(3,1));        
        
        firstLabel= new JLabel(" The New FMI", iconLog, 2);
        regGroup= new JPanel(new FlowLayout(2));
        labels= new JPanel(new FlowLayout());
        buttonGroup= new JPanel(new FlowLayout(2));
        accField=new JTextField(30);
        passField=new JTextField(30);
        logIn=new JButton("Log in");
        register=new JButton("Register");
        regGroup.add(accField,FlowLayout.LEFT);
        regGroup.add(passField,FlowLayout.CENTER);
        buttonGroup.add(logIn, FlowLayout.LEFT);
        buttonGroup.add(register, FlowLayout.CENTER);
        firstLabel.setFont(firstLabel.getFont().deriveFont(25.0f));        
        labels.add(firstLabel,FlowLayout.LEFT);
        conn = con;
        //sets background
        labels.setBackground(new Color(225,250,182));
        firstLabel.setForeground(new Color(113,62,19));
        regGroup.setBackground(new Color(145,145,145));
        buttonGroup.setBackground(new Color(145,145,145));
        logIn.setBackground(new Color(195,195,195));
        register.setBackground(new Color(195,195,195));
        logIn.setForeground(Color.white);
        register.setForeground(Color.white);
        add(labels);
        add(regGroup);
        add(buttonGroup);
    }

    public JTextField getAccField() {
        return accField;
    }

    public JTextField getPassField() {
        return passField;
    }
    
    
}