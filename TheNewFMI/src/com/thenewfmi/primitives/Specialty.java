/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.primitives;

/**
 *
 * @author SyncCarryOn
 */
public class Specialty {
    int ID;
    String Name;

    public Specialty(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }
    
    @Override
    public String toString(){
        return Name;
    }

    public int getID() {
        return ID;
    }
}
