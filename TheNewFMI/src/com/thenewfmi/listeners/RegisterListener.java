/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.listeners;

import com.thenewfmi.forms.RegForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JFrame;

/**
 *
 * @author SyncCarryOn
 */
public class RegisterListener implements ActionListener {
    Connection conn;
    JFrame parent;
    public RegisterListener(JFrame parent, boolean modal, Connection conn) {
        this.conn = conn;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RegForm createacc = new RegForm(parent, true, conn);
        createacc.setVisible(true);
    }
}
