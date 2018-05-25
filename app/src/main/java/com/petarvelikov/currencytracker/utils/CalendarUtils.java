package com.petarvelikov.currencytracker.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

  public static int currentYear() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.YEAR);
  }

  public static int currentMonth() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.MONTH) + 1;
  }

  public static int currentDayOfMonth() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  public static Date dateFromMilliseconds(long milliseconds) {
    return new Date(milliseconds);
  }

  public static long millisecondsFromDate(Date date) {
    return date.getTime();
  }

  public static Date dateFromParts(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);
    return calendar.getTime();
  }
}
