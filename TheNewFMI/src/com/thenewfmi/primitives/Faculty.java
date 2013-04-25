/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.primitives;

/**
 *
 * @author SyncCarryOn
 */
public class Faculty {
    private int ID;
    private String Name;

    public Faculty(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }
    
    public String toString(){
        return Name;
    }

    public int getID() {
        return ID;
    }
    
    
}
