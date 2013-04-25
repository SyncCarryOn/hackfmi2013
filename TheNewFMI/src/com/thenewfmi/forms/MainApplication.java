/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.forms;

import com.thenewfmi.currentsession.User;
import com.thenewfmi.listeners.SearchListener;
import com.thenewfmi.schedule.Schedule;
import com.thenewfmi.schedule.UnivercityClass;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author SyncCarryOn
 */
public class MainApplication extends javax.swing.JFrame {

    Icon iconLog = new ImageIcon("banner.png");
    JButton Search = new JButton("Search disciplines");
    JButton Right = new JButton("Next Week");
    JButton Left = new JButton("Previous Week");
    JButton currentWeek = new JButton("Current week");
    JButton saveSchedule = new JButton("Save schedule");
    JButton loadSchedule = new JButton("Load schedule");
    Schedule sc;
    JFileChooser chooser = new JFileChooser();
    
    Icon bannerImg = new ImageIcon("banner.png");
    JLabel banner;
    JPanel bannerPanel;

    /**
     * Creates new form MainApplication
     */
    public MainApplication() {

        initComponents();
        setLayout(null);
        setVisible(true);
        setSize(1300, 600);
        bannerPanel = new JPanel();
        banner = new JLabel(bannerImg);
        bannerPanel.add(banner);

        sc = new Schedule();
        sc.generateBase(User.activeUser);
        sc.CheckReschedules(null);
        //sc.GetExtras();
        add(sc);
        setResizable(false);
        add(Search);
        add(Right);
        add(Left);
        add(currentWeek);
        bannerPanel.setBounds(Schedule.width + 20, Schedule.height, 200, 45);
        add(bannerPanel);
        Search.setBounds(Schedule.width + 20, 0, 200, 45);
        Search.addActionListener(new SearchListener(sc));
        Search.setForeground(Color.white);
        Search.setBackground(new Color(195, 195, 195));

        Right.setBounds(Schedule.width / 2 + 300, Schedule.height + 20, 200, 45);
        Right.setForeground(Color.white);
        Right.setBackground(new Color(195, 195, 195));
        Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.nextWeek();
                sc.updateDates();
                sc.recreateSchedule();
                sc.CheckReschedules(null);
            }
        });
        Left.setBounds(Schedule.width / 2 - 500, Schedule.height + 20, 200, 45);
        Left.setForeground(Color.white);
        Left.setBackground(new Color(195, 195, 195));
        Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.prevWeek();
                sc.updateDates();
                sc.recreateSchedule();
                sc.CheckReschedules(null);
                //sc.GetExtras();
            }
        });
        currentWeek.setBounds(Schedule.width / 2 - 100, Schedule.height + 20, 200, 45);
        currentWeek.setForeground(Color.white);
        currentWeek.setBackground(new Color(195, 195, 195));
        currentWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.weekOfDate(sc.today);
                sc.updateDates();
                sc.recreateSchedule();
                sc.CheckReschedules(null);
                //sc.GetExtras();
            }
        });
        saveSchedule.setBounds(Schedule.width + 20, 50, 200, 50);
        add(saveSchedule);
        saveSchedule.setForeground(Color.white);
        saveSchedule.setBackground(new Color(195, 195, 195));
        saveSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, UnivercityClass> classMap = sc.getMainClasses();
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(JOptionPane.showInputDialog("Enter file name with extension .ser:"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(fout);
                    oos.writeObject(classMap);
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        loadSchedule.setBounds(Schedule.width + 20, 105, 200, 50);
        add(loadSchedule);
        loadSchedule.setForeground(Color.white);
        loadSchedule.setBackground(new Color(195, 195, 195));
        loadSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setMultiSelectionEnabled(false);
                int returnVal = chooser.showOpenDialog(MainApplication.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    FileInputStream fin = null;
                    try {
                        fin = new FileInputStream(chooser.getSelectedFile());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ObjectInputStream oos = null;
                    try {
                        oos = new ObjectInputStream(fin);
                        try {
                            HashMap<String, UnivercityClass> classes = (HashMap<String, UnivercityClass>) oos.readObject();
                            sc.setMainClasses(classes);
                            sc.recreateSchedule();
                            sc.CheckReschedules(null);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        oos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        pack();
    }// </editor-fold>                        
}
