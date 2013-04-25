/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.listeners;

import com.thenewfmi.forms.SearchForm;
import com.thenewfmi.schedule.Schedule;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SyncCarryOn
 */
public class SearchListener implements ActionListener{
    Schedule sc;

    public SearchListener(Schedule sc) {
        this.sc = sc;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        SearchForm form = new SearchForm(sc);
        form.setVisible(true);
    }
    
}
