/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.forms;

import com.thenewfmi.izborni.OptionalClass;
import com.thenewfmi.izborni.SearchHandler;
import com.thenewfmi.primitives.Specialty;
import com.thenewfmi.schedule.Schedule;
import com.thenewfmi.schedule.UnivercityClass;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SearchForm extends JFrame {

    JLabel tickLabel, groupLabel, courseLabel, dayLabel, heading;
    JButton addButton;
    JComboBox groupCombo, dayCombo;
    JCheckBox notInterrupting;
    JTextField courseField;
    JScrollPane listOfCourses;
    JPanel tickPanel, filterPanel, scrollPanel, buttonPanel, headPanel, upPanel, downPanel, bannerPanel;
    JList resultsList;
    JLabel clearLabel;
    Icon iconSearch = new ImageIcon("search.png");
    Icon bannerImg = new ImageIcon("banner.png");
    JLabel banner;
    SearchHandler results;
    Schedule sc;

    public SearchForm(Schedule sched) {

        setLayout(new GridLayout(3, 1));


        sc = sched;
        results = new SearchHandler(sc.getClassList(sc.getClasses()));
        heading = new JLabel("Search", iconSearch, 2);
        tickPanel = new JPanel(new FlowLayout());
        filterPanel = new JPanel(new GridLayout(2, 2));
        upPanel = new JPanel(new GridLayout(3, 1));
        downPanel = new JPanel(new GridLayout(2, 1));
        scrollPanel = new JPanel();
        headPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout());
        bannerPanel = new JPanel(new GridLayout(1, 2));
        resultsList = new JList(new DefaultListModel<OptionalClass>());
        banner = new JLabel(bannerImg);


        tickLabel = new JLabel("not interrupting schedule");
        groupLabel = new JLabel("                                Group:");
        courseLabel = new JLabel("Course:");
        dayLabel = new JLabel("                                  Day:");
        clearLabel = new JLabel("                                                              ");

        addButton = new JButton("                  Add                   ");

        groupCombo = new JComboBox();
        dayCombo = new JComboBox();

        notInterrupting = new JCheckBox();

        courseField = new JTextField(2);

        listOfCourses = new JScrollPane(resultsList);

        filterPanel.add(groupLabel);
        filterPanel.add(dayLabel);
        filterPanel.add(groupCombo);
        filterPanel.add(dayCombo);

        tickPanel.add(courseLabel);
        tickPanel.add(courseField);
        tickPanel.add(notInterrupting);
        tickPanel.add(tickLabel);

        // listOfCourses.add(resultsList);
        //resultsList.add(listOfCourses);


        buttonPanel.add(clearLabel);
        buttonPanel.add(addButton);

        headPanel.add(heading);

        headPanel.setBackground(new Color(225, 250, 182));
        heading.setForeground(new Color(113, 62, 19));
        heading.setFont(heading.getFont().deriveFont(25.0f));
        tickPanel.setBackground(new Color(145, 145, 145));
        filterPanel.setBackground(new Color(225, 250, 182));
        scrollPanel.setBackground(new Color(145, 145, 145));
        buttonPanel.setBackground(new Color(145, 145, 145));
        notInterrupting.setBackground(new Color(145, 145, 145));
        addButton.setBackground(new Color(195, 195, 195));
        tickLabel.setForeground(Color.white);
        groupLabel.setForeground(new Color(113, 62, 19));
        dayLabel.setForeground(new Color(113, 62, 19));
        courseLabel.setForeground(Color.white);
        resultsList.setForeground(Color.white);
        resultsList.setBackground(new Color(145, 145, 145));
        resultsList.setBorder(BorderFactory.createLineBorder(new Color(195, 195, 195)));
        bannerPanel.setBackground(new Color(145, 145, 145));


        addButton.setForeground(Color.white);


        upPanel.add(headPanel);
        upPanel.add(filterPanel);
        upPanel.add(tickPanel);

        bannerPanel.add(clearLabel);
        bannerPanel.add(banner);




        //downPanel.add(listOfCourses);
        downPanel.add(buttonPanel);
        downPanel.add(bannerPanel);


        add(upPanel);
        //add(resultsList);
        add(listOfCourses);
        add(downPanel);

        //add(bannerPanel);

        setSize(700, 550);
        setResizable(false);

        Connection con;
        ArrayList<Specialty> groups = new ArrayList<>();
        groups.add(new Specialty(0, "Без критерий"));
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "");
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM groups");
            while (res.next()) {
                groups.add(new Specialty(res.getInt("ID"), res.getString("Name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Specialty> days = new ArrayList<>();
        days.add(new Specialty(0, "Всички"));
        days.add(new Specialty(1, "Понеделник"));
        days.add(new Specialty(2, "Вторник"));
        days.add(new Specialty(3, "Сряда"));
        days.add(new Specialty(4, "Четвъртък"));
        days.add(new Specialty(5, "Петък"));
        days.add(new Specialty(6, "Събота"));
        days.add(new Specialty(7, "Неделя"));

        groupCombo.setModel(new DefaultComboBoxModel(groups.toArray()));
        dayCombo.setModel(new DefaultComboBoxModel(days.toArray()));
        courseField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int course;
                try {
                    course = Integer.parseInt(courseField.getText());
                } catch (Exception exc) {
                    course = 0;
                }
                results.setCoursefilter(course);
                results.ApplyFilters();
                ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                for (OptionalClass oc : results.getFiltered()) {
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                int course;
                try {
                    course = Integer.parseInt(courseField.getText());
                } catch (Exception exc) {
                    course = 0;
                }
                results.setCoursefilter(course);
                results.ApplyFilters();
                ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                for (OptionalClass oc : results.getFiltered()) {
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                int course;
                try {
                    course = Integer.parseInt(courseField.getText());
                } catch (Exception exc) {
                    course = 0;
                }
                results.setCoursefilter(course);
                results.ApplyFilters();
                ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                for (OptionalClass oc : results.getFiltered()) {
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                }
            }
        });
        groupCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    results.setGroupfilter(((Specialty) e.getItem()).getID());
                    results.ApplyFilters();
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                    for (OptionalClass oc : results.getFiltered()) {
                        ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                    }
                }
            }
        });
        dayCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    results.setDayfilter(((Specialty) e.getItem()).getID());
                    results.ApplyFilters();
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                    for (OptionalClass oc : results.getFiltered()) {
                        ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                    }
                }
            }
        });

        for (OptionalClass oc : results.getAllOptional()) {
            ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
        }

        notInterrupting.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    results.setInterruptfilter(null);
                } else {
                    results.setInterruptfilter(sc.getClassArray(sc.getClasses()));
                }
                results.ApplyFilters();
                ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                for (OptionalClass oc : results.getFiltered()) {
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List selected = resultsList.getSelectedValuesList();
                for (int i = 0; i < selected.size(); i++) {
                    String id = Integer.toString(((OptionalClass) selected.get(i)).getID());
                    sc.AddClass(id, UnivercityClass.CreateClassFromID(id));

                    results.generateOptional(sc.getClassList(sc.getClasses()));
                    if (notInterrupting.isSelected()) {
                        results.setInterruptfilter(sc.getClassArray(sc.getClasses()));
                    }
                    results.ApplyFilters();
                    ((DefaultListModel<OptionalClass>) resultsList.getModel()).clear();
                    for (OptionalClass oc : results.getFiltered()) {
                        ((DefaultListModel<OptionalClass>) resultsList.getModel()).addElement(oc);
                    }
                }
            }
        });
    }
}