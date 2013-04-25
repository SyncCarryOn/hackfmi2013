/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.schedule;

import java.util.ArrayList;

/**
 *
 * @author SyncCarryOn
 */
public class TimeTaken {
    public ArrayList[] taken = new ArrayList[7];
    
    public TimeTaken(UnivercityClass[] classes){
        for (int i = 0; i < 7; i++) {
            taken[i] = new ArrayList();
        }
        for (int i = 0; i < classes.length; i++) {
            taken[classes[i].getDay()-1].add(new TimeRange(classes[i].getStartHour(), classes[i].getEndHour()));
        }
    }
}
