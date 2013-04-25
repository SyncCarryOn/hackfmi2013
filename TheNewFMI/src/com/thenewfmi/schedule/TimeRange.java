/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenewfmi.schedule;

/**
 *
 * @author SyncCarryOn
 */
public class TimeRange {
    int start;
    int end;

    public TimeRange(int start, int end) {
        this.start = start;
        this.end = end;
        if(start > end){
            this.start = 0;
            this.end = 0;
        }
    }
    
    public boolean overlaps(TimeRange t){
        return (t.start >= start && t.start < end) || (t.end <= end && t.end > start);
    }
}
