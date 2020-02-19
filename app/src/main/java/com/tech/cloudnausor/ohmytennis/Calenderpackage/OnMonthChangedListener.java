package com.tech.cloudnausor.ohmytennis.Calenderpackage;


import java.util.Calendar;

public interface OnMonthChangedListener {

    /**
     * Called after change of month
     * @param monthCalendar Calendar of focused month
     */
    void onMonthChanged(Calendar monthCalendar);
}