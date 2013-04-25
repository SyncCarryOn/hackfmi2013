/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.schedule;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author SyncCarryOn
 */
public class UnivercityClass extends JPanel implements Serializable{

    private String name;
    private String acronym;
    private String teacher;
    private String room;
    private int startHour;
    private int endHour;
    private int day;
    private boolean zadaljitelen;
    private ArrayList<JLabel> labels;

    public UnivercityClass(String name, String acronym, String Teacher, String room, int startHour, int endHour, int day, boolean zadaljitelen) {
        this.name = name;
        this.acronym = acronym;
        this.teacher = Teacher;
        this.room = room;
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
        this.zadaljitelen = zadaljitelen;
        this.labels = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    void AutoBounds() {
        setBounds((day - 1) * Schedule.daywidth + Schedule.leftBuffer, (startHour - 7) * Schedule.hourheight + Schedule.topBuffer, Schedule.daywidth, (endHour - startHour) * Schedule.hourheight);
        setBorder(BorderFactory.createLineBorder(new Color(195, 195, 195), 3));
        setBackground(Color.WHITE);
        for(JLabel label: labels){
            remove(label);
        }
        labels.clear();
        
        setLayout(new GridLayout(3,1));
        
        labels.add((JLabel)add(new JLabel(name)));
        labels.add((JLabel)add(new JLabel(room)));
        labels.add((JLabel)add(new JLabel(teacher)));
        this.validate();
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getDay() {
        return day;
    }
    
    public void addLabelToArrayList(JLabel label){
        labels.add(label);
    }
    
    public boolean isZadaljitelen() {
        return zadaljitelen;
    }
    
     public static UnivercityClass CreateClassFromID(String id) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "");
            Statement st = con.createStatement();
            ResultSet Class = st.executeQuery("SELECT * FROM schedule WHERE ID = " + id);
            Class.next();
            Statement st1 = con.createStatement();
            ResultSet discipline = st1.executeQuery("SELECT * FROM disciplini WHERE ID = " + Class.getString("Discipline"));
            discipline.next();
            Statement st2 = con.createStatement();
            ResultSet teacher = st2.executeQuery("SELECT * FROM teachers WHERE ID = " + discipline.getInt("Teacher"));
            teacher.next();
            return new UnivercityClass(discipline.getString("Name"), null, teacher.getString("Name"), Class.getString("Room"), Class.getInt("StartHour"), Class.getInt("EndHour"), Class.getInt("Day"), !Class.getBoolean("Optional"));

        } catch (SQLException ex) {
            Logger.getLogger(UnivercityClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
