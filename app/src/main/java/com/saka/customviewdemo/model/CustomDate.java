package com.saka.customviewdemo.model;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class CustomDate {
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    private int year;
    private int month;
    private int day;
    private int dayOfWeek;

    public CustomDate() {
    }

    /**
     * 获取当前的日期
     * @return
     */
    public CustomDate getCurrentDate() {
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new CustomDate(year, month, day);
    }

    public CustomDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        calendar.set(year, month, day);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取上个月的天数
     * @return
     */
    public int getLastMonthDays() {
        return this.getDaysOfMonth(this.year, this.month - 1);
    }

    /**
     * 获取第一天是星期几
     *
     * @return
     */
    public int getFirstDayOfWeek() {
        calendar.set(this.year, this.month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取最后一天是星期几
     *
     * @return
     */
    public int getLastDayOfWeek() {
        calendar.set(this.year, this.month, getTotalDayOfMonth());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取这个月总共的天数
     * @return
     */
    public int getTotalDayOfMonth() {
        return this.getDaysOfMonth(year, month);
    }

    public int getTotalWeekOfMonth() {
        return calendar.getMaximum(Calendar.WEEK_OF_MONTH);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "CustomDate{" +
                "year=" + year +
                ", month=" + (getMonth() + 1) +
                ", day=" + day +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }

    /**
     * 获取年中每月的天数
     * @param year
     * @param month
     * @return
     */
    private int getDaysOfMonth(int year, int month) {
        if (month > 11) {
            month = 0;
            year += 1;
        } else if (month < 0) {
            month = 11;
            year -= 1;
        }

        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int daysOfMonth = 0;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            arr[1] = 29;
        }
        daysOfMonth = arr[month];
        return daysOfMonth;
    }
}
