package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class Schedule
 * @author Group2
 */
public class Schedule {

    private int classroom;
    private int subject;
    private int year;
    private String day;
    private String hour;
    private String quarter;

    /**
     * Class constructor
     * @param classroom
     * @param subject
     * @param year
     * @param day
     * @param hour
     * @param quarter
     */
    public Schedule(int classroom, int subject, int year, String day, String hour, String quarter) {
        this.classroom = classroom;
        this.subject = subject;
        this.year = year;
        this.day = day;
        this.hour = hour;
        this.quarter = quarter;
    }

    /**
     * Default Class Constructor
     */
    public Schedule() {}

    @Override
    public String toString() {
        return "Schedule{" + "classroom=" + classroom + ", subject=" + subject + ", year=" + year + ", day=" + day + ", hour=" + hour + ", quarter=" + quarter + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.classroom;
        hash = 47 * hash + this.subject;
        hash = 47 * hash + this.year;
        hash = 47 * hash + Objects.hashCode(this.day);
        hash = 47 * hash + Objects.hashCode(this.hour);
        hash = 47 * hash + Objects.hashCode(this.quarter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Schedule other = (Schedule) obj;
        if (this.classroom != other.classroom) {
            return false;
        }
        if (this.subject != other.subject) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.day, other.day)) {
            return false;
        }
        if (!Objects.equals(this.hour, other.hour)) {
            return false;
        }
        if (!Objects.equals(this.quarter, other.quarter)) {
            return false;
        }
        return true;
    }
    
    /**
     * Method that get Classroom
     * @return int
     */
    public int getClassroom() {
        return classroom;
    }
    
    /**
     * Method that set classroom
     * @param classroom 
     */
    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }
    
    /**
     * Method that get Subject
     * @return int
     */
    public int getSubject() {
        return subject;
    }
    
    /**
     * Method that set Subject
     * @param subject 
     */
    public void setSubject(int subject) {
        this.subject = subject;
    }
    
    /**
     * Method that get Year
     * @return int
     */
    public int getYear() {
        return year;
    }
    
    /**
     * Method that set Year
     * @param year 
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    /**
     * Method that get day
     * @return String
     */
    public String getDay() {
        return day;
    }
    
    /**
     * Method that set day
     * @param day 
     */
    public void setDay(String day) {
        this.day = day;
    }
    
    /**
     * Method that get hour
     * @return String
     */
    public String getHour() {
        return hour;
    }
    
    /**
     * Method that set hour
     * @param hour 
     */
    public void setHour(String hour) {
        this.hour = hour;
    }
    
    /**
     * Method that get quarter
     * @return quarter
     */
    public String getQuarter() {
        return quarter;
    }
    
    /**
     * Method that set quarter
     * @param quarter 
     */
    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
    
    /**
     * Method that get a list of days for a quarter
     * @param quarter
     * @return 
     */
    public ArrayList<String> returnDaysList(String quarter){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<String> days =scheduleDAO.returnDaysList(quarter);
        return days;
    }
    
    /**
     * Method that get a list of hours for a day and quarter
     * @param day
     * @param quarter
     * @return 
     */
    public ArrayList<String> returnHourList(String day,String quarter){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<String> hours =scheduleDAO.returnHoursList(day,quarter);
        return hours;
    }
    
    /**
     * Method that get a list of classrooms for a day
     * @param day
     * @param hour
     * @param subject
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> returnClassList(String day,String hour,String subject,String quarter){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<String> classroom =scheduleDAO.returnClassList(day,hour,subject,quarter);
        return classroom;
    }
    
    /**
     * Method that set a completed schedule
     * @param titulation
     * @param course
     * @param quarter
     * @param subject
     * @param day
     * @param hour
     * @param classroom
     * @param year 
     */
    public void insertSchedule(String titulation,String course,String quarter,String subject,String day,String hour,String classroom,String year){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        scheduleDAO.insertSchedule(titulation,course,quarter,subject,day,hour,classroom,year);

    }
    
    /**
     * Method that returns a list of ocuppied days
     * @param subject
     * @return ArrayList String
     */
    public ArrayList<String> returnOcuppiedDays(String subject){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<String> days = scheduleDAO.returnOcuppiedDays(subject);
        return days;

    }
    
    /**
     * Method that get a list of ocuppied hours
     * @param subject
     * @param day
     * @return ArrayList String
     */
    public ArrayList<String> returnOcuppiedHours(String subject, String day){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<String> hours = scheduleDAO.returnOcuppiedHours(subject,day);
        return hours;
    }
    
    /**
     * Method that returns a list of ocuppied classrooms
     * @param day
     * @param hour
     * @param subject
     * @return ArrayList String
     */
    public ArrayList<String> returnOcuppiedClassroom(String day,String hour,String subject){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<String> classroom = scheduleDAO.returnOcuppiedClassroom(day,hour,subject);
        return classroom;
    }
    
    /**
     * Method that returns a list of schedules by quarter, course and titulation
     * @param quarter
     * @param course
     * @param titulation
     * @return ArrayList Schedule
     */
    public ArrayList<Schedule> returnSchedules(String quarter, String course, String titulation){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<Schedule> schedules = scheduleDAO.getSchedules(titulation,course,quarter);
        
        return schedules;
    }
    
    /**
     * Method that return if a schedule has been updated
     * @param day_old
     * @param hour_old
     * @param classroom_old
     * @param day_new
     * @param hour_new
     * @param classroom_new
     * @param quarter
     * @return int
     */
    public int updateSchedule(String day_old,String hour_old,String classroom_old,String day_new,String hour_new,String classroom_new,String quarter){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        int update = scheduleDAO.updateSchedule(day_old,hour_old,classroom_old,day_new,hour_new,classroom_new,quarter);
        return update;
    
    }
}
