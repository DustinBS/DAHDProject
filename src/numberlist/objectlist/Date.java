package numberlist.objectlist;

import java.io.Serializable;
import java.time.LocalDate;
import numberlist.IndexRangeException;

/**
 * This class represents a single Date object in the form "mm/dd/yyyy".
 *
 * @author Divyashna Chandra
 * @version 03/19/2020
 */
public class Date implements Copiable, Comparable<Date>, Serializable {

    //fields
    int day;
    int month;
    int year;

    /**
     * Full Constructor. Creates a new Date object using month day and year.
     *
     * @param month the month of the year
     * @param day the day of the month
     * @param year the year of delivery
     */
    public Date(int month, int day, int year) {
        try {
            int[] checkedDate = check(month, day, year);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Checks validations of the string being entered in the right form
     * (mm/dd/yyyy)
     *
     * @param date the date entered by the user in String form (mm/dd/yyyy)
     */
    public Date(String date) {
        if (!date.trim().isEmpty()) {
            String[] dateTokens = date.trim().split("/");
            if (dateTokens.length == 3) {
                try {
                    int dMonth = Integer.valueOf(dateTokens[0]);
                    int dDay = Integer.valueOf(dateTokens[1]);
                    int dYear = Integer.valueOf(dateTokens[2]);
                    try {
                        int[] checkedDate = check(dMonth, dDay, dYear);
                    } catch (IllegalArgumentException ex) {
                        throw ex;
                    }
                    this.month = dMonth;
                    this.day = dDay;
                    this.year = dYear;
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Date is not a parsable integer");
                }
            } else {
                throw new IllegalArgumentException("Date is not in the form mm/dd/yyyy.");
            }
        } else {
            throw new IllegalArgumentException("Date is empty.");
        }
    }

    public int[] check(int month, int day, int year) {
        int[] result = new int[3];
        int maxDays = 0;
        boolean isLeapYear = false;
        if (year < LocalDate.now().getYear()) {
            throw new IllegalArgumentException(year + " has passed!");
        }
        result[2] = year;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                maxDays = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDays = 30;
                break;
            case 2:
                if (year % 4 == 0) {
                    if (year % 100 == 0) {
                        if (year % 400 == 0) {
                            isLeapYear = true;
                        }
                    } else {
                        isLeapYear = true;
                    }
                }
                if (isLeapYear) {
                    maxDays = 29;
                } else {
                    maxDays = 28;
                }
                break;
            default:
                throw new IllegalArgumentException("Not a valid month.");
        }
        result[0] = month;
        if (day > maxDays) {
            throw new IllegalArgumentException("Month " + month + " only has "
                    + maxDays + " days!");
        }
        result[1] = day;
        return result;
    }

    /**
     * Provides access to the day portion of the date.
     *
     * @return the day portion of the Date object.
     */
    public int getDay() {
        return day;
    }

    /**
     * Provides access to the month portion of the date
     *
     * @return the month portion of the Date object.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Provides access to the year portion of the date
     *
     * @return the year potion of the Date object.
     */
    public int getYear() {
        return year;
    }

    /**
     * Provides a string representation of the current Date object in the form
     * mm/dd/yyyy
     *
     * @return the string representation of the Date object.
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Returns a deepCopy of Date object: the same day, month and year value as
     * the caller.
     *
     * @return the Date copy of the caller.
     */
    @Override
    public Date makeDeepCopy() {
        return new Date(day, month, year);
    }

    /**
     * Creates a new Date object based on the current date.
     *
     * @return the current date
     */
    public static Date getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        int year = currentDate.getYear();
        return new Date(month, day, year);
    }

    /**
     * Compares two Date objects based on the year, month and day.
     *
     * @param t the object to compare to
     * @return 1 if this object is greater than the other; 0 if equal; -1 if
     * lesser
     */
    @Override
    public int compareTo(Date t) {
        if (day > t.day || month > t.month || year > t.year) {
            return 1;
        } else if (day == t.day || month == t.month || year == t.year) {
            return 0;
        } else {
            return -1;
        }
    }

}
