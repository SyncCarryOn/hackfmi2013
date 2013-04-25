/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.izborni;

import com.thenewfmi.currentsession.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author SyncCarryOn
 */
public class OptionalClass {
    int ID;
    int[] reqs = new int[8];
    int group;
    int course;
    String name;
    int startHour;
    int endHour;
    int day;
    String dayText;
    String teacher;
    
    public OptionalClass(ResultSet res) throws SQLException{
        ID = res.getInt("ID");
        
        for (int i = 1; i <= 8; i++) {
            reqs[i-1] = res.getInt(Integer.toString(i));
        }
        group = res.getInt("Group");
        name = res.getString(2);
        teacher = res.getString(7);
        startHour = res.getInt("StartHour");
        endHour = res.getInt("EndHour");
        day = res.getInt("Day");
        String[] dayz = {"Понеделник","Вторник","Сряда","Четвъртък","Петък","Събота","Неделя"};
        dayText = dayz[day-1];
        course = res.getInt(Integer.toString(User.activeUser.getSpecialty()));
    }
    
    @Override
    public String toString(){
        return name + " | " + teacher + " | "+dayText+" от " +startHour+":00 до "+endHour + ":00";
    }

    public int getID() {
        return ID;
    }
    
    
}
