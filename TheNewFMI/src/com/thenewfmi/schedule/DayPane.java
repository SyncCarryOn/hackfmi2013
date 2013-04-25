/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.schedule;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author SyncCarryOn
 */
public class DayPane extends JPanel{
    JLabel day, date;
    
    public DayPane(){
        day = new JLabel();
        date = new JLabel();
        setLayout(new GridLayout(2,1));
        add(day);
        add(date);
    }
    
    public void setDay (String day){
        this.day.setText(day);
    }
    
    public void setDate (String date){
        this.date.setText(date);
    }
    
    public void setFonts(Font font){
        super.setFont(font);
        day.setFont(font);
        date.setFont(font);
    }
}
