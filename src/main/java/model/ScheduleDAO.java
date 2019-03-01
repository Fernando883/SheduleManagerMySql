package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.ConnectionDB;

/**
 * Class ScheduleDAO
 * @author Group2
 */
class ScheduleDAO {
    ConnectionDB connectionDB = ConnectionDB.getInstance();   

    /**
     * Class Constructor
     */
    public ScheduleDAO() {}
    
    /**
     * Method that get list of days for a quarter
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> returnDaysList(String quarter) {
        ArrayList<String> days = new ArrayList();
        String[] daysArray = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        Connection con = connectionDB.getConnection();
        try {

            ResultSet num_class = con.createStatement().executeQuery("select count(id_classroom) from classroom");
            num_class.next();
            int num = num_class.getInt(1);
            for (String day : daysArray) {
                ResultSet numhor = con.createStatement().executeQuery("select count(hour_schedule) from schedule where day_schedule='" + day + "' and quarter='"+quarter+"'");
                numhor.next();
                int houroccup = numhor.getInt(1);
                if (houroccup < (num * 12)) {
                    days.add(day);
                }
            }
        } catch (SQLException ex) {
        }
        return days;
    }
    
    /**
     * Method that get list of hours for a day and quarter 
     * @param day
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> returnHoursList(String day,String quarter){
            
        ArrayList<String> hours = new ArrayList();
        String[] hoursArray = {"8:30-9:30","9:30-10:30","10:30-11:30","11:30-12:30","12:30-13:30","13:30-14:30","15:30-16:30",
                                   "16:30-17:30","17:30-18:30","18:30-19:30","19:30-20:30","20:30-21:30"};
        Connection con = connectionDB.getConnection();
        try{ 
            ResultSet num_class = con.createStatement().executeQuery("select count(id_classroom) from classroom");
            num_class.next();
            int num = num_class.getInt(1);
            for (String hour : hoursArray) {
                ResultSet num_ocup = con.createStatement().executeQuery("select count(hour_schedule) from schedule where hour_schedule='" + hour + "' and day_schedule='" + day + "' and quarter='"+quarter+"'");
                num_ocup.next();
                int houroccup = num_ocup.getInt(1);
                if (houroccup < num) {
                    hours.add(hour);
                }
            }
        } catch (SQLException ex) {
        }
        return hours;
    }
        
    /**
     * Method get list classrooms of day, hour, subject and quarter 
     * @param day
     * @param hour
     * @param subject
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> returnClassList(String day,String hour,String subject,String quarter){
    
        ArrayList<String> classroom = new ArrayList();
        ArrayList<Integer> classroom_ocup = new ArrayList();
        StringBuilder x = new StringBuilder("0");
        Connection con = connectionDB.getConnection();
        try{ 
            ResultSet rs_idsub = con.createStatement().executeQuery("select id_subject from subject where name_subject='" + subject + "'");
            rs_idsub.next();
            int idsub = rs_idsub.getInt(1);
            ResultSet rs_numstud = con.createStatement().executeQuery("select count(*) from subject_user where id_subject=" + idsub);
            rs_numstud.next();
            int numstud = rs_numstud.getInt(1);
            
            ResultSet class_ocup = con.createStatement().executeQuery("select id_classroom from schedule where hour_schedule='" + hour + "' and day_schedule='" + day + "'and quarter='"+quarter+"'");
            
            while(class_ocup.next()){
                //classroom_ocup.add(class_ocup.getInt(1));
                x.append(","+class_ocup.getInt(1));
                System.out.println(class_ocup.getInt(1));
            }
          
            ResultSet class_free = con.createStatement().executeQuery("select * from classroom where id_classroom not in ("+x+")and capacity_classroom>=" + numstud);
            while(class_free.next()){
                classroom.add(class_free.getString("NAME_CLASSROOM"));
            }
        } catch (SQLException ex) {
        }
        return classroom;
    }
       
    /**
     * Method insert Shedule
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
        Connection con = connectionDB.getConnection();
        try {
            ResultSet rs_class = con.createStatement().executeQuery("select id_classroom from classroom where name_classroom='" + classroom + "'");
            rs_class.next();
            int id_classroom = rs_class.getInt(1);
            
            ResultSet rs_subj = con.createStatement().executeQuery("select id_subject from subject where name_subject='" + subject + "'");
            rs_subj.next();
            int id_subject = rs_subj.getInt(1);  
            PreparedStatement stmt = con.prepareStatement("insert into schedule (id_classroom,id_subject,hour_schedule,day_schedule,quarter) values(?,?,?,?,?)");
            stmt.setInt(1,id_classroom);
            stmt.setInt(2,id_subject);
            stmt.setString(3,hour);
            stmt.setString(4,day);
            stmt.setString(5,quarter);
  
            int i = stmt.executeUpdate();
                      System.out.println("Funciona");
            System.out.println(i);
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
    
    /**
     * Method that get Days Ocuppied
     * @param subject
     * @return ArrayList String
     */
    public ArrayList<String> returnOcuppiedDays(String subject){
        
        ArrayList<String> days = new ArrayList();
        Connection con = connectionDB.getConnection();
        try {
            ResultSet rs_idsub = con.createStatement().executeQuery("select id_subject from subject where name_subject='" + subject + "'");
            rs_idsub.next();
            int id_sub = rs_idsub.getInt(1);
            ResultSet rs_days = con.createStatement().executeQuery("select distinct day_schedule from schedule where id_subject=" +id_sub);
            while(rs_days.next()){
                days.add(rs_days.getString("day_schedule"));
            }
        } catch (SQLException ex) {
        }
        System.out.println(days);
        
        return days;
    }
    
    /**
     * Method that get OcuppiedHours for a  subject and day
     * @param subject
     * @param day
     * @return ArrayList String 
     */
    public ArrayList<String> returnOcuppiedHours(String subject, String day){      
        ArrayList<String> hours = new ArrayList();
        Connection con = connectionDB.getConnection();
        try {
            ResultSet rs_idsub = con.createStatement().executeQuery("select id_subject from subject where name_subject='" + subject + "'");
            rs_idsub.next();
            int id_sub = rs_idsub.getInt(1);
            ResultSet rs_hours = con.createStatement().executeQuery("select  hour_schedule from schedule where day_schedule='" + day + "' and id_subject=" + id_sub);
            while(rs_hours.next()){
                hours.add(rs_hours.getString("hour_schedule"));
            }
        } catch (SQLException ex) {
        }
        System.out.println(hours);
        return hours;
    }
    
    /**
     * Method that get OcuppiedClassroom for a day, hour and subject
     * @param day
     * @param hour
     * @param subject
     * @return ArrayList String
     */
    public ArrayList<String> returnOcuppiedClassroom(String day,String hour,String subject){
        ArrayList<String> classroom = new ArrayList();
        Connection con = connectionDB.getConnection();
        try {
            ResultSet rs_idsub = con.createStatement().executeQuery("select id_subject from subject where name_subject='" + subject + "'");
            rs_idsub.next();
            int id_sub = rs_idsub.getInt(1);
            System.out.println(id_sub);
            ResultSet rs_class = con.createStatement().executeQuery("select  id_classroom from schedule where day_schedule='" + day + "' and id_subject = " + id_sub + " and hour_schedule = '" + hour + "'");
            while(rs_class.next()){
                int id_classroom = rs_class.getInt("id_classroom");
                ResultSet rs_nameClass = con.createStatement().executeQuery("select name_classroom from classroom where id_classroom="+ id_classroom);
                rs_nameClass.next();
                classroom.add(rs_nameClass.getString("name_classroom"));  
            }    
        } catch (SQLException ex) {
        }
        System.out.println(classroom);
        return classroom;
    }
    
    /**
     * Method that get Shedules for titulation, course and quarter
     * @param titulation
     * @param course
     * @param quarter
     * @return ArrayList Schedule
     */
    public ArrayList<Schedule> getSchedules(String titulation,String course,String quarter){
        ArrayList<Schedule> schedules = new ArrayList();
        SubjectDAO subjectDao = new SubjectDAO();
        
        ArrayList<Subject> subjects = subjectDao.getSubjects(titulation, course, quarter);        
        
        Connection con = connectionDB.getConnection();
        
        try {
            for (Subject subject : subjects) {
                PreparedStatement stmtSched = con.prepareStatement("SELECT id_classroom, id_subject, hour_schedule, day_schedule FROM schedule WHERE id_subject = ? ");
                stmtSched.setInt(1, subject.getId_subject());
                ResultSet schedResult=stmtSched.executeQuery();

                while (schedResult.next()){
                    Schedule schedule = new Schedule(); 
                    schedule.setClassroom(schedResult.getInt("id_classroom"));
                    schedule.setSubject(schedResult.getInt("id_subject"));
                    schedule.setHour(schedResult.getString("hour_schedule"));
                    schedule.setDay(schedResult.getString("day_schedule"));
                    
                    schedules.add(schedule);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return schedules;
    }
    
    /**
     * Method that updates a shedule
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
        System.out.println(day_old+hour_old+classroom_old+day_new+hour_new+classroom_new+quarter);
        int update = 0;
        Connection con = connectionDB.getConnection();
        
        try {
            ResultSet rs_classNew = con.createStatement().executeQuery("select id_classroom from classroom where name_classroom='" + classroom_new + "'");
            rs_classNew.next();
            int id_classNew = rs_classNew.getInt(1);
            System.out.println("idnuevo "+id_classNew);
            
            ResultSet rs_classOld = con.createStatement().executeQuery("select id_classroom from classroom where name_classroom='" + classroom_old + "'");
            rs_classOld.next();
            int id_classOld = rs_classOld.getInt(1);
            System.out.println("idviejo "+id_classOld);
            
            PreparedStatement ps_update = con.prepareStatement("update schedule set day_schedule=?, hour_schedule=?, quarter=?,id_classroom=? where day_schedule=? "
                    + "and hour_schedule=? and id_classroom=?");
            ps_update.setString(1,day_new);
            ps_update.setString(2,hour_new);
            ps_update.setString(3,quarter);
            ps_update.setInt(4,id_classNew);
            ps_update.setString(5,day_old);
            ps_update.setString(6,hour_old);
            ps_update.setInt(7,id_classOld);
            update = ps_update.executeUpdate();     
        } catch (SQLException ex) {}
        
        return update;    
    }
    
    /**
     * Method that close connection
     * @throws Throwable 
     */
    protected void finalize() throws Throwable{
        super.finalize();
    }
}
