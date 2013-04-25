/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.currentsession;

/**
 *
 * @author SyncCarryOn
 */
public class User {
    public static User activeUser;
    
    int specialty;
    int kurs;
    int potok;
    int grupa;

    public User(int specialty, int kurs, int potok, int grupa) {
        this.specialty = specialty;
        this.kurs = kurs;
        this.potok = potok;
        this.grupa = grupa;
    }

    public int getSpecialty() {
        return specialty;
    }

    public int getKurs() {
        return kurs;
    }

    public int getPotok() {
        return potok;
    }

    public int getGrupa() {
        return grupa;
    }
    
    
}
