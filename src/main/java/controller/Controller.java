package controller;

import model.Request;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Classroom;
import model.Schedule;
import model.Subject;
import model.Titulation;

/**
 * Main Controller Class
 * @author Group2
 */
public class Controller implements ActionListener{
    Request modeloRequest;
    Titulation titulationModel;
    Subject subjectModel;
    Schedule scheduleModel;
    Classroom classroomModel;
    
    /**
     * Class Construct method
     */
    public Controller(){
        modeloRequest = new Request();
        titulationModel = new Titulation();
        subjectModel = new Subject();
        scheduleModel = new Schedule();
        classroomModel = new Classroom();
    }         
    
    /**
     * Method get all request of a user
     * @param applicant
     * @return ArrayList Request
     */
    public ArrayList<Request> getAllRequest(String applicant){
        ArrayList<Request> lista = modeloRequest.returnListRequest(applicant);
        return lista;
    }
    
    /**
     * Method get all request of all users
     * @return ArrayList Request
     */
    public ArrayList<Request> getAllAdminRequest(){
        ArrayList<Request> lista = modeloRequest.returnListRequest1();
        return lista;
    }
    
    /**
     * Method set Request
     * @param fila
     * @param status 
     */
    public void SetAdminRequest(int fila,String status){
        modeloRequest.SetAdminRequest(fila,status);
    }
    
    /**
     * Method get the Request select
     * @param row
     * @return String
     */
    public String getSelectedRequest(int row){
       return modeloRequest.getSelectedMessage(row);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Method get all Titulation
     * @return ArrayList Titulation
     */
    public ArrayList<Titulation> getAllTitulations(){
        ArrayList<Titulation> titulationsList = titulationModel.returnsTitulationList();
        return titulationsList;
    }
    
    /**
     * Method get Subjects of TItulation for titulation, course and quarter
     * @param titulation
     * @param course
     * @param quarter
     * @return ArrayList Subject
     */
    public ArrayList<Subject> getSubjectsTitulation(String titulation, String course, String quarter){
        ArrayList<Subject> subjectList = subjectModel.returnSubjectList(titulation, course, quarter);
        return subjectList;
    }
    
    /**
     * Method get Available Days fot quarter
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> getAvailableDays(String quarter){
        ArrayList<String> daysList = scheduleModel.returnDaysList(quarter);
        return daysList;
    }
    
    /**
     * Method get Available Hours for day and  quarter
     * @param day
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> getAvailableHours(String day,String quarter){
        ArrayList<String> hourList = scheduleModel.returnHourList(day,quarter);
        return hourList;
    }
    
    /**
     * Method get Available Classroom for day, hour, subject and quarter
     * @param day
     * @param hour
     * @param subject
     * @param quarter
     * @return ArrayList String
     */
    public ArrayList<String> getAvailableClassroom(String day,String hour,String subject,String quarter){
        ArrayList<String> classList = scheduleModel.returnClassList(day,hour,subject,quarter);
        return classList;
    }
    
    /**
     * Method set Schedule for titulation, course, quarter, subject, day, hour, classroom, year
     * @param titulation
     * @param course
     * @param quarter
     * @param subject
     * @param day
     * @param hour
     * @param classroom
     * @param year 
     */
    public void setSchedule(String titulation,String course,String quarter,String subject,String day,String hour,String classroom,String year){
        scheduleModel.insertSchedule(titulation,course,quarter,subject,day,hour,classroom,year);

    }
    
    /**
     * Method get list of Titulation for a user
     * @param applicant
     * @return ArrayList Titulation
     */
    public ArrayList<Titulation> getTitulationsAndSubjects(String applicant) {
        ArrayList<Titulation> titulationsSubjectsList = titulationModel.returnsTitulationSubjectList(applicant);
        return titulationsSubjectsList;
    }
    
    /**
     * Method get list of Courses for user and titulation
     * @param applicant
     * @param id_titulation
     * @return ArrayList String
     */
    public ArrayList<String> getCoursesTitulationUser(String applicant, int id_titulation){
        ArrayList<String> courses = subjectModel.returnCoursesTitulationUser(applicant, id_titulation);
        return courses;
    }
    
    /**
     * Method get list of quarter for user, id_titulation and course
     * @param applicant
     * @param id_titulation
     * @param course
     * @return ArrayList String
     */
    public ArrayList<String> getQuartersTitulationUser(String applicant, int id_titulation, String course){
        ArrayList<String> quarters = subjectModel.returnQuartersTitulationUser(applicant, id_titulation, course);
        return quarters;
    }
    
    /**
     * Method return true if the request if correct else false
     * @param applicant
     * @param subject
     * @param text
     * @return boolean
     */
    public boolean setRequest(String applicant,String subject, String text){
        boolean ok;
        ok = modeloRequest.setRequest(applicant, subject, text);
        return ok;
    }
    
    /**
     * Method get list of years for a users
     * @param applicant
     * @return ArrayList String
     */
    public ArrayList<String> getAllYears(String applicant){
       ArrayList<String> years = subjectModel.returnYearsSubjectUser(applicant);
       return years;
    }
    
    /**
     * Method get list of days
     * @param subject
     * @return ArrayList String
     */
    public ArrayList<String> getOccupiedDays(String subject){
        ArrayList<String> days = scheduleModel.returnOcuppiedDays(subject);
        return days;
    }
    
    /**
     * Method get list of hours for subject and day
     * @param subject
     * @param day
     * @return ArrayList String
     */
    public ArrayList<String> getOccupiedHours(String subject,String day){
        ArrayList<String> hours = scheduleModel.returnOcuppiedHours(subject,day);
        return hours;
    }
    
    /**
     * Method get list of classroom for day, hour and subject
     * @param day
     * @param hour
     * @param subject
     * @return ArrayList String
     */
    public ArrayList<String> getOcuppiedClassroom(String day,String hour,String subject){
        ArrayList<String> classroom = scheduleModel.returnOcuppiedClassroom(day,hour,subject);
        return classroom;
    }
    /**
     * Method load Schedule for a user
     * @param jTableSchedule
     * @param quarter
     * @param course
     * @param titulation 
     */
    public void loadSchedule(JTable jTableSchedule, String quarter, String course, String titulation){
        ArrayList<Schedule> schedules = scheduleModel.returnSchedules(quarter, course, titulation);
        String title[] = {"Día", "Hora", "Asignatura", "Edificio", "Aula"};
        DefaultTableModel m = new DefaultTableModel(null,title);
        String row[] = new String[5];
        String hour;
        
        for (Schedule schedule : schedules) {
            int idSubject = schedule.getSubject();
            int idClassroom = schedule.getClassroom();
            String subjectName = subjectModel.returnSubjectById(idSubject);
            Classroom classroom = classroomModel.returnClassroomById(idClassroom);
            String day = schedule.getDay();
            row[0] = schedule.getDay();
            row[1] = schedule.getHour();
            row[2] = subjectName;
            row[3] = classroom.getBuilding();
            row[4] = classroom.getName();
            
            m.addRow(row); 
        }
        
        jTableSchedule.setModel(m);
    }
    
    /**
     * Method update Schedule for a day_old, hour_old, classroom_old and quarter
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
        int  update = scheduleModel.updateSchedule(day_old,hour_old,classroom_old,day_new,hour_new,classroom_new,quarter);
        return update;
    
    }
    
    /**
     * Method get list of schedules for quarter, course and titulation
     * @param quarter
     * @param course
     * @param titulation
     * @return ArrayList Schedule
     */
    public ArrayList<Schedule> getSchedules(String quarter, String course, String titulation){
        ArrayList<Schedule> schedules = scheduleModel.returnSchedules(quarter, course, titulation);
        return schedules;
    }    
    
    /**
     * Method get subjectName for a idSubject
     * @param idSubject
     * @return String
     */
    public String getSubjectById(int idSubject){
        String subjectName = subjectModel.returnSubjectById(idSubject);
        return subjectName;
    }    
    
    /**
     * Method get classroom for a idClassroom
     * @param idClassroom
     * @return Classroom
     */
    public Classroom getClassroomById(int idClassroom){
        Classroom classroom = classroomModel.returnClassroomById(idClassroom);
        return classroom;
    }
    
}
