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
 * Class SubjectDAO
 * @author Group2
 */
public class SubjectDAO {
    ConnectionDB connectionDB = ConnectionDB.getInstance();   
    
    /**
     * Class Constructor
     */
    public SubjectDAO(){}
    
    /**
     * Method that returns a list of subjects that by titulation, course and quarter
     * @param titulation
     * @param course
     * @param quarter
     * @return Arraylist Subject
     */
    public ArrayList<Subject> getSubjects(String titulation, String course, String quarter){
        ArrayList<Subject> subjects = new ArrayList();
        Connection con = connectionDB.getConnection();
        int id_titulation = 0;
        try{ 
            PreparedStatement stmtTit = con.prepareStatement("select id_titulation from titulation where name_tit=?");
            stmtTit.setString(1, titulation);
            ResultSet resultadoTit=stmtTit.executeQuery();
            
            while (resultadoTit.next()){
                id_titulation = Integer.parseInt(resultadoTit.getString("id_titulation"));
            }
            PreparedStatement stmtSub = con.prepareStatement("select id_subject, name_subject from subject where id_tit=" 
                    + id_titulation + " and course='" + course +  "' and quarter= '" + quarter + "'");

            ResultSet resultadoSub=stmtSub.executeQuery();
            while (resultadoSub.next()){
                Subject subject = new Subject(); 
                subject.setId_subject(resultadoSub.getInt("id_subject"));
                subject.setName(resultadoSub.getString("name_subject"));
                subjects.add(subject);
            }  
        }catch (SQLException ex) {        
        }
        return subjects;
    }
    
    /**
     * Method that returs a list of courses by titulation and user
     * @param applicant
     * @param id_titulation
     * @return ArrayList String
     */
    public ArrayList<String> getCoursesTitulationUser(String applicant, int id_titulation){
        ArrayList<String> courses = new ArrayList();
        Connection con = connectionDB.getConnection();
        
        try {
            PreparedStatement stmtCour = con.prepareStatement("SELECT DISTINCT course FROM subject WHERE id_subject IN(SELECT id_subject FROM subject_user WHERE dni = ?) AND id_tit = ?");
            stmtCour.setString(1, applicant);
            stmtCour.setInt(2, id_titulation);
            ResultSet courResult=stmtCour.executeQuery();
            
            while (courResult.next()){
                String course = courResult.getString("course");   
                courses.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return courses;
    }
    
    /**
     * Method that returns a list of quarters by user, titulation and course
     * @param applicant
     * @param id_titulation
     * @param course
     * @return ArrayList String
     */
    public ArrayList<String> getQuartersTitulationUser(String applicant, int id_titulation, String course){
        ArrayList<String> quarters = new ArrayList();
        Connection con = connectionDB.getConnection();
        
        try {
            PreparedStatement stmtQuart = con.prepareStatement("SELECT DISTINCT quarter FROM subject WHERE id_subject IN(SELECT id_subject FROM subject_user WHERE dni = ?) AND id_tit = ? AND course = ? ");
            stmtQuart.setString(1, applicant);
            stmtQuart.setInt(2, id_titulation);
            stmtQuart.setString(3, course);
            ResultSet quartResult=stmtQuart.executeQuery();
            
            while (quartResult.next()){
                String quarter = quartResult.getString("quarter");   
                quarters.add(quarter);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quarters;
    }
    
    /**
     * Method that returns a list of years by user.
     * @param applicant
     * @return ArrayList String
     */
    public ArrayList<String> getYearsSubjectUser(String applicant){
    
        ArrayList<String> years = new ArrayList();
        Connection con = connectionDB.getConnection();
        try {
            PreparedStatement rs_stmt = con.prepareStatement("SELECT DISTINCT year FROM subject");
            ResultSet rs_year=rs_stmt.executeQuery();
            while (rs_year.next()){
                String year = rs_year.getString("year");   
                years.add(year);
            }
            
        } catch (SQLException ex) {
        }
        return years;
    }
    
    /**
     * Method which returns a subject by its id.
     * @param id_subject
     * @return String
     */
    public String getSubjectById(int id_subject){
        Connection con = connectionDB.getConnection();
        String nameSubject = null;
        
        try {
            PreparedStatement stmtSub = con.prepareStatement("SELECT name_subject FROM subject WHERE id_subject = ?");
            stmtSub.setInt(1, id_subject);
            ResultSet subResult=stmtSub.executeQuery();
            
            while (subResult.next()){
                nameSubject = subResult.getString("name_subject");   
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nameSubject;
    }
    
    /**
     * Method that close connection
     * @throws Throwable 
     */
    protected void finalize() throws Throwable{
        super.finalize();
    }
}
