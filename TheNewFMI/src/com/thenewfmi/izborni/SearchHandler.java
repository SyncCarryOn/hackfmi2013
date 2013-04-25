/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.izborni;

import com.thenewfmi.schedule.TimeRange;
import com.thenewfmi.schedule.TimeTaken;
import com.thenewfmi.schedule.UnivercityClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SyncCarryOn
 */
public class SearchHandler {

    ArrayList<OptionalClass> allOptional;
    ArrayList<OptionalClass> filtered;
    private int groupfilter = 0;
    private int dayfilter = 0;
    private int coursefilter = 0;
    private UnivercityClass[] interruptfilter = null;

    final public void generateOptional(String classes) {
        allOptional = new ArrayList<>();
        String query = "Select sc.ID, d.Name, sc.Room, sc.Day, sc.StartHour, sc.EndHour,  t.Name, req.1, req.2, req.3, req.4, req.5, req.6, req.7, req.8, req.Group From teachers as t, schedule as sc, disciplini as d, requirements as req where sc.Discipline = d.ID and sc.Req = req.ID and d.Teacher = t.ID";
        if(classes != null){
            query += " and sc.ID not in " + classes;
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fmi_schedules", "root", "");
            Statement st = con.createStatement();
            ResultSet allOpt = st.executeQuery(query);
            while (allOpt.next()) {
                allOptional.add(new OptionalClass(allOpt));
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SearchHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SearchHandler(String classes) {
        generateOptional(classes);
    }

    public ArrayList<OptionalClass> getAllOptional() {
        return allOptional;
    }

    public void setGroupfilter(int groupfilter) {
        this.groupfilter = groupfilter;
    }

    public void setDayfilter(int dayfilter) {
        this.dayfilter = dayfilter;
    }

    public void setCoursefilter(int coursefilter) {
        this.coursefilter = coursefilter;
    }

    public void setInterruptfilter(UnivercityClass[] interruptfilter) {
        this.interruptfilter = interruptfilter;
    }

    public ArrayList<OptionalClass> getFiltered() {
        return filtered;
    }

    public void ApplyFilters() {
        filtered = (ArrayList<OptionalClass>) allOptional.clone();
        ArrayList<OptionalClass> mediator;
        if (groupfilter != 0) {
            mediator = new ArrayList<>();
            for (OptionalClass oc : filtered) {
                if (oc.group == groupfilter) {
                    mediator.add(oc);
                }
            }
            filtered = mediator;
        }

        if (dayfilter != 0) {
            mediator = new ArrayList<>();
            for (OptionalClass oc : filtered) {
                if (oc.day == dayfilter) {
                    mediator.add(oc);
                }
            }
            filtered = mediator;
        }

        if (coursefilter != 0) {
            mediator = new ArrayList<>();
            for (OptionalClass oc : filtered) {
                if (oc.course >= coursefilter) {
                    mediator.add(oc);
                }
            }
            filtered = mediator;
        }

        if (interruptfilter != null) {
            mediator = new ArrayList<>();
            TimeTaken tt = new TimeTaken(interruptfilter);
            for (OptionalClass oc : filtered) {
                boolean add = true;
                for (int i = 0; i < tt.taken[oc.day - 1].size(); i++) {
                    if (((TimeRange) tt.taken[oc.day - 1].get(i)).overlaps(new TimeRange(oc.startHour, oc.endHour))) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    mediator.add(oc);
                }
            }
            filtered = mediator;
        }
    }
}
