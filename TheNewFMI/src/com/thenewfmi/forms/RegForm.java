/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.forms;

import com.thenewfmi.primitives.Faculty;
import com.thenewfmi.primitives.Specialty;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Sisi
 */
public class RegForm extends javax.swing.JDialog {

    Connection conn;
    JTextField usrField, fnField, passField, course, flow, group;
    JLabel lUsrField, lFnField, lPassField, lComboSpec, lComboFac, lCourse,
            lFlow, lGroup, clearLabel, heading, banner;
    JButton createAcc, cancel;
    JComboBox comboSpec, comboFac;
    ArrayList<Specialty> specialties;
    ArrayList<Faculty> faculty;
    JPanel usrPanel, fnPanel, passPanel, boxPanel, comboPanel, buttonPanel,
            usrFnPanel, headPanel, comboLabels, comboSel, bannerPanel;
    Icon iconReg = new ImageIcon("reg.png");
    Icon bannerImg = new ImageIcon("banner.png");
    
    public void SetSpecialties(int facID){
        try {
            specialties.clear();
            Statement st = conn.createStatement();
            ResultSet specialtiesRes = st.executeQuery("Select * FROM Specialties WHERE Faculty = " + Integer.toString(facID));
            while(specialtiesRes.next()){
                specialties.add(new Specialty(specialtiesRes.getInt("ID"),specialtiesRes.getString("Name")));
            }
            comboSpec.setModel(new DefaultComboBoxModel(specialties.toArray()));
        } catch (SQLException ex) {
            Logger.getLogger(RegForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public RegForm(JFrame parent, boolean modal, final Connection conn) {
        super(parent, modal);
        setLayout(new GridLayout(7, 1));
        setSize(400, 420);


        headPanel = new JPanel();
        usrPanel = new JPanel(new GridLayout(1, 2));
        fnPanel = new JPanel(new GridLayout(1, 2));
        passPanel = new JPanel(new GridLayout(2, 1));
        boxPanel = new JPanel(new GridLayout(2, 1));
        comboPanel = new JPanel(new FlowLayout());
        bannerPanel = new JPanel(new GridLayout(1, 2));
        comboLabels = new JPanel(new GridLayout(1, 2));
        comboSel = new JPanel(new GridLayout(1, 2));
        buttonPanel = new JPanel(new FlowLayout());
        usrFnPanel = new JPanel(new GridLayout(2, 1));

        clearLabel = new JLabel("                 ");
        banner = new JLabel(bannerImg);
        heading = new JLabel("Register:", iconReg, 2);
        heading.setFont(heading.getFont().deriveFont(25.0f));

        specialties = new ArrayList<Specialty>();
        faculty = new ArrayList<Faculty>();

        lUsrField = new JLabel("                    User name: ");
        usrField = new JTextField(15);

        lFnField = new JLabel("                 Faculty number: ");
        fnField = new JTextField(7);

        lPassField = new JLabel("                                                   Password: ");
        passField = new JTextField(28);

        lCourse = new JLabel("Course: ");
        course = new JTextField(3);

        lFlow = new JLabel("Flow: ");
        flow = new JTextField(3);

        lGroup = new JLabel("Group: ");
        group = new JTextField(3);

        this.conn = conn;

        createAcc = new JButton("Register");
        cancel = new JButton("Cancel");

        createAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statement state = null;
                try {
                    state = conn.createStatement();
                    ResultSet rslt = state.executeQuery("SELECT * FROM accounts WHERE Username = '" + usrField.getText() + "'");
                    rslt.next();
                    if (rslt.getRow() != 0) {
                        JOptionPane.showMessageDialog(rootPane, "Ve4e ima takav akaunt sry bro");
                        
                    } else {
                        state.execute("INSERT INTO accounts(FacultyNum,Username,Password,Specialty,Kurs,Potok,Grupa) VALUES (" + fnField.getText() + ",'" + usrField.getText() + "','" + passField.getText() + "'," + ((Specialty)comboSpec.getSelectedItem()).getID()+ "," + course.getText() + "," + flow.getText() + "," + group.getText() + ")");
                        dispose();
                        //TODO: Exception handling
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RegForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        faculty.add(new Faculty(0, "--------------"));
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM faculties");
            while (res.next()) {
                faculty.add(new Faculty(res.getInt("ID"), res.getString("Name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lComboFac = new JLabel("                    Faculty: ");
        comboFac = new JComboBox(faculty.toArray());
        comboFac.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    SetSpecialties(((Faculty)e.getItem()).getID());
                }
            }
        });
        
        lComboSpec = new JLabel("                    Specialty: ");
        comboSpec = new JComboBox(specialties.toArray());
        
        usrPanel.add(lUsrField);
        fnPanel.add(usrField);
        usrPanel.add(clearLabel);
        fnPanel.add(clearLabel);
        usrPanel.add(lFnField);
        fnPanel.add(fnField);

        headPanel.add(heading);

        passPanel.add(lPassField, FlowLayout.LEFT);
        passPanel.add(passField, FlowLayout.CENTER);

        comboLabels.add(lComboFac);
        comboSel.add(comboFac);
        
        comboLabels.add(lComboSpec);
        comboSel.add(comboSpec);


        boxPanel.add(comboLabels);
        boxPanel.add(comboSel);

        comboPanel.add(lCourse);
        comboPanel.add(course);

        comboPanel.add(lFlow);
        comboPanel.add(flow);

        comboPanel.add(lGroup);
        comboPanel.add(group);

        buttonPanel.add(createAcc);
        buttonPanel.add(clearLabel);
        buttonPanel.add(cancel);

        heading.setForeground(new Color(113, 62, 19));
        headPanel.setBackground(new Color(225, 250, 182));

        lUsrField.setForeground(new Color(113, 62, 19));
        lFnField.setForeground(new Color(113, 62, 19));
        lPassField.setForeground(new Color(113, 62, 19));
        lComboSpec.setForeground(Color.white);
        lComboFac.setForeground(Color.white);
        lCourse.setForeground(Color.white);
        lFlow.setForeground(Color.white);
        lGroup.setForeground(Color.white);
        createAcc.setForeground(Color.white);
        cancel.setForeground(Color.white);

        usrPanel.setBackground(new Color(225, 250, 182));
        comboLabels.setBackground(new Color(145, 145, 145));
        comboSel.setBackground(new Color(145, 145, 145));
        fnPanel.setBackground(new Color(225, 250, 182));
        passPanel.setBackground(new Color(225, 250, 182));
        comboPanel.setBackground(new Color(145, 145, 145));
        boxPanel.setBackground(new Color(145, 145, 145));
        bannerPanel.setBackground(new Color(145, 145, 145));
        buttonPanel.setBackground(new Color(145, 145, 145));
        createAcc.setBackground(new Color(195, 195, 195));
        cancel.setBackground(new Color(195, 195, 195));


        usrFnPanel.add(usrPanel);
        usrFnPanel.add(fnPanel);

        bannerPanel.add(banner);
        bannerPanel.add(clearLabel);

        //specPanel.add(boxPanel);
        //specPanel.add(comboPanel);

        add(headPanel);
        add(usrFnPanel);
        add(passPanel);
        add(boxPanel);
        add(comboPanel);
        add(buttonPanel);
        add(bannerPanel);

    }
}