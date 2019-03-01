package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.ConnectionDB;

/**
 * Class ClassroomDAO
 * @author Group2
 */
public class ClassroomDAO {
    ConnectionDB connectionDB = ConnectionDB.getInstance();   
    
    /**
     * Class constructor
     */
    public ClassroomDAO(){}
    
    /**
     * Method that gets Classroom by id
     * @param idClassroom
     * @return Classroom
     */
    public Classroom getClassroomById(int idClassroom){
        Classroom classroom = new Classroom();
        Connection con = connectionDB.getConnection();
        
        try {
            PreparedStatement stmtClass = con.prepareStatement("SELECT name_classroom, building FROM classroom WHERE id_classroom = ?");
            stmtClass.setInt(1, idClassroom);
            ResultSet classResult=stmtClass.executeQuery();
            
            while (classResult.next()){
                classroom.setName(classResult.getString("name_classroom"));
                classroom.setBuilding(classResult.getString("building"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return classroom;
    }
    
    /**
     * Method that closes the connection
     * @throws Throwable 
     */
    protected void finalize() throws Throwable{
        super.finalize();
    }
}
