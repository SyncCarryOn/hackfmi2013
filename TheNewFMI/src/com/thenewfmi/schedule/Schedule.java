/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.schedule;

import com.thenewfmi.currentsession.Reschedules;
import com.thenewfmi.currentsession.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author SyncCarryOn
 */
public class Schedule extends JLayeredPane {

    HashMap<String, UnivercityClass> mainClasses;
    
    HashMap<String, UnivercityClass> rescheduledClasses;
    HashMap<Integer, Integer> timesRescheduled;
    
    ArrayList<JPanel> extras;
    
    DayPane[] dayPane = new DayPane[7];
    Date monday, sunday;
    public Calendar today;
    public static int width = 1010;
    public static int height = 493;
    public static int topBuffer = 45;
    public static int leftBuffer = 30;
    public static int hourheight = 32;
    public static int daywidth = 140;

    public Schedule() {
        mainClasses = new HashMap<>();
        rescheduledClasses = new HashMap<>();
        timesRescheduled = new HashMap<>();
        extras = new ArrayList<>();
        JPanel pPane = new JPanel();
        add(pPane);
        for (int i = 0; i < 7; i++) {
            dayPane[i] = new DayPane();
            add(dayPane[i]);

        }
        setBorder(BorderFactory.createLineBorder(new Color(113, 62, 19), 7, true));
        setBounds(0, 0, width + 7, height + 7);
        setFocusable(true);
        pPane.setBounds(0, 0, leftBuffer, topBuffer);

        Calendar c = Calendar.getInstance();
        today = (Calendar) c.clone();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday = c.getTime();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        sunday = c.getTime();

        updateDates();

        String[] hours = {"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        for (int i = 0; i < 14; i++) {
            JPanel hourPane = new JPanel();
            add(hourPane);
            hourPane.add(new Label(hours[i]));
            hourPane.setBounds(0, i * hourheight + topBuffer, leftBuffer, hourheight);
            hourPane.setBorder(BorderFactory.createLineBorder(new Color(113, 62, 19), 1));
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 14; j++) {
                JPanel gridPane = new JPanel();
                add(gridPane, 1);
                gridPane.setBounds(i * daywidth + leftBuffer, j * hourheight + topBuffer, daywidth, hourheight);
                gridPane.setBorder(BorderFactory.createLineBorder(new Color(195, 195, 195)));
            }
        }
    }

    final public void updateDates() {
        String[] dayz = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        Calendar c = Calendar.getInstance();
        c.setTime(monday);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0; i < 7; i++, c.add(Calendar.DAY_OF_YEAR, 1)) {


            dayPane[i].setBounds(i * daywidth + leftBuffer, 0, daywidth, topBuffer);
            dayPane[i].setBorder(BorderFactory.createLineBorder(new Color(113, 62, 19), 1, true));
            dayPane[i].setBackground(new Color(225, 250, 182));
            dayPane[i].setForeground(new Color(113, 62, 19));
            dayPane[i].setDay(dayz[i]);
            dayPane[i].setDate(df.format(c.getTime()));
            dayPane[i].setFonts(dayPane[i].getFont().deriveFont(12.0f));
            dayPane[i].setFonts(dayPane[i].getFont().deriveFont(Font.ITALIC));
            if (today.get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR) && today.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
                dayPane[i].setBackground(new Color(212, 8, 234));
                dayPane[i].setForeground(Color.white);
                dayPane[i].setFonts(dayPane[i].getFont().deriveFont(14.0f));
                dayPane[i].setFonts(dayPane[i].getFont().deriveFont(Font.BOLD));
            }
        }
    }
    
    public void weekOfDate(Calendar calendar){
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        sunday = calendar.getTime();
    }
    
    public void nextWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(monday);
        cal.add(Calendar.DAY_OF_YEAR, 7);
        monday = cal.getTime();
        cal.setTime(sunday);
        cal.add(Calendar.DAY_OF_YEAR, 7);
        sunday = cal.getTime();
    }

    public void prevWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(monday);
        cal.add(Calendar.DAY_OF_YEAR, -7);
        monday = cal.getTime();
        cal.setTime(sunday);
        cal.add(Calendar.DAY_OF_YEAR, -7);
        sunday = cal.getTime();
    }

    public void generateBase(User acc) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "");
            Statement stmt = con.createStatement();
            ResultSet fromSchedule = stmt.executeQuery("SELECT * FROM schedule WHERE Specialty = " + acc.getSpecialty() + " AND Kurs = " + acc.getKurs() + " AND Potok = " + acc.getPotok() + " AND (Grupa = " + acc.getGrupa() + " OR Grupa = 0)");
            while (fromSchedule.next()) {
                Statement st = con.createStatement();
                ResultSet discipline = st.executeQuery("SELECT * FROM disciplini WHERE ID = " + fromSchedule.getInt("Discipline"));
                discipline.next();
                Statement st1 = con.createStatement();
                ResultSet teacher = st1.executeQuery("SELECT * FROM teachers WHERE ID = " + discipline.getInt("Teacher"));
                teacher.next();
                UnivercityClass pane = new UnivercityClass(discipline.getString("Name"), null, teacher.getString("Name"), fromSchedule.getString("Room"), fromSchedule.getInt("StartHour"), fromSchedule.getInt("EndHour"), fromSchedule.getInt("Day"), true);
                add(pane, 2);
                pane.AutoBounds();
                mainClasses.put(fromSchedule.getString("ID"), pane);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recreateSchedule() {
        Iterator<UnivercityClass> it = rescheduledClasses.values().iterator();
        while (it.hasNext()) {
            UnivercityClass uc = it.next();
            remove(uc);
        }
        rescheduledClasses.clear();
        
        it = mainClasses.values().iterator();
        while (it.hasNext()) {
            it.next().AutoBounds();
        }
        validate();
        repaint();
    }

    public void CheckReschedules(Reschedules r) {
        ResultSet rescheduled;

        try {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "")) {
                Statement st = con.createStatement();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat showDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String query;

                String inList = getClassList(getClasses());
                if (inList != null) {
                    
                    query = "SELECT * FROM reschedules WHERE Date BETWEEN '" + df.format(monday) + "' AND '" + df.format(sunday) + "' AND Class IN " + inList;
                    rescheduled = st.executeQuery(query);
                    while (rescheduled.next()) {
                        String key = rescheduled.getString("Class");
                        UnivercityClass cl = mainClasses.get(key);
                        cl.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        cl.setBackground(new Color(245, 245, 245));

                        ((GridLayout) cl.getLayout()).setRows(((GridLayout) cl.getLayout()).getRows() + 1);
                        cl.addLabelToArrayList((JLabel)cl.add(new JLabel("New date: " + showDateFormat.format(rescheduled.getDate("NewDate")))));
                    }

                    query = "SELECT * FROM reschedules WHERE NewDate BETWEEN '" + df.format(monday) + "' AND '" + df.format(sunday) + "' AND Class IN " + inList;
                    rescheduled = st.executeQuery(query);
                    while (rescheduled.next()) {
                        UnivercityClass cl = UnivercityClass.CreateClassFromID(rescheduled.getString("Class"));
                        cl.setStartHour(rescheduled.getInt("StartHour"));
                        cl.setEndHour(rescheduled.getInt("EndHour"));
                        cl.setRoom(rescheduled.getString("Room"));
                        int weekDay = getWeekDay(rescheduled.getDate("NewDate"));
                        cl.setDay(weekDay);

                        int id = rescheduled.getInt("Class");
                        int reschedules = 0;
                        if (timesRescheduled.get(id) != null) {
                            reschedules = timesRescheduled.get(id);
                            timesRescheduled.put(id, timesRescheduled.get(id) + 1);
                        } else {
                            timesRescheduled.put(id, 1);
                        }
                        AddClass(Integer.toString(id) + Integer.toString(reschedules) + "r", cl);
                        cl.setBorder(BorderFactory.createLineBorder(new Color(205, 135, 195), 3));
                        cl.setBackground(new Color(160, 105, 135));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void GetExtras(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "");
            Statement st = con.createStatement();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String inList = getClassList(getClasses());
            String query = "SELECT e.Type,e.StartHour,e.Room,e.EndHour,e.Date,d.Name FROM extras as e, schedule as s, disciplini as d WHERE e.Class = s.ID AND s.Discipline = d.ID AND e.Date BETWEEN " + df.format(monday) + " AND " + df.format(sunday) + " AND s.ID IN " + inList;
            ResultSet res = st.executeQuery(query);
            
            while(res.next()){
                JPanel pane = new JPanel();
                pane.setBounds(leftBuffer + getWeekDay(res.getDate("Date"))*daywidth, topBuffer + res.getInt("StartHour")*hourheight, daywidth, (res.getInt("EndHour") - res.getInt("StartHour")*hourheight));
                pane.setLayout(new GridLayout(3,1));
                pane.add(new JLabel(res.getString("Name")));
                pane.add(new JLabel(res.getString("Type")));
                pane.add(new JLabel(res.getString("Room")));
                add(pane);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void AddClass(String ID, UnivercityClass which) {
        if (ID.charAt(ID.length() - 1) != 'r') {
            mainClasses.put(ID, which);
        } else {
            rescheduledClasses.put(ID, which);
        }
        add(which, 2);
        which.AutoBounds();
    }

    public Integer[] getClasses() {
        ArrayList<Integer> al = new ArrayList<>();
        for (String key : mainClasses.keySet()) {
            if (key.charAt(key.length() - 1) != 'r') {
                al.add(Integer.parseInt(key));
            }
        }
        Integer[] ret = {};
        return al.toArray(ret);
    }

    public UnivercityClass[] getClassArray(Integer[] ids) {
        ArrayList<UnivercityClass> al = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            al.add(mainClasses.get(Integer.toString(ids[i])));
        }
        UnivercityClass[] uc = {};
        uc = al.toArray(uc);
        return uc;
    }

    public String getClassList(Integer[] ids) {
        String all;
        if (ids.length > 0) {
            all = "(" + ids[0].toString();
        } else {
            return null;
        }
        for (int i = 1; i < ids.length; i++) {
            all += "," + ids[i].toString();
        }
        all += ")";
        return all;
    }

    static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1;
    }

    public HashMap<String, UnivercityClass> getMainClasses() {
        return mainClasses;
    }

    public void setMainClasses(HashMap<String, UnivercityClass> mainClasses) {
        for(UnivercityClass uc: this.mainClasses.values()){
            remove(uc);
        }
        this.mainClasses.clear();
        validate();
        repaint();
        this.mainClasses = mainClasses;
        for(String key: mainClasses.keySet()){
            System.out.println(key);
            AddClass(key, mainClasses.get(key));
        }
    }
    
}
