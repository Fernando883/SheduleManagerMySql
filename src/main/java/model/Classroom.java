package model;

import java.util.List;

/**
 * Class Classroom
 * @author Group2
 */
public class Classroom {
    private int id_classroom;
    private String name;
    private String type;
    private int capacity;
    private String building;
    private List<Schedule> schedules;
    
    /**
     * Class constructor method
     * @param id_classroom
     * @param name
     * @param type
     * @param capacity
     * @param building
     * @param schedules 
     */
    public Classroom(int id_classroom, String name, String type, int capacity, String building, List<Schedule> schedules) {
        this.id_classroom = id_classroom;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.building = building;
        this.schedules = schedules;
    }

    /**
     * Default Class Constructor
     */
    public Classroom() {}
    
    /**
     * Method that returns a Classroom object in String format.
     * @return String
     */
    @Override
    public String toString() {
        return "Aula{" + "id_classroom=" + id_classroom + ", name=" + name + ", type=" + type + ", capacity=" + capacity + ", building=" + building + ", schedules=" + schedules + '}';
    }
    
    
    /**
     * Method that gets classroom's id.
     * @return id_classroom
     */
    public int getId_classroom() {
        return id_classroom;
    }
    
   /**
    * Method that sets id_classroom
    * @param id_classroom 
    */
    public void setId_classroom(int id_classroom) {
        this.id_classroom = id_classroom;
    }
    
    /**
     * Method that gets Classroom's name.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Method that sets Name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Method that gets Classroom's type.
     * @return type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Method that sets Classroom's type.
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Method that gets Classroom's capacity.
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Method that sets Classroom's capacity.
     * @param capacity 
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    /**
     * Method that gets Classroom's building.
     * @return building
     */
    public String getBuilding() {
        return building;
    }
    
    /**
     * Method that sets Classroom's building.
     * @param building 
     */
    public void setBuilding(String building) {
        this.building = building;
    }
    
    /**
     * Method that gets a list of schedules.
     * @return schedules
     */
    public List<Schedule> getSchedules() {
        return schedules;
    }
    
    /**
     * Method that sets a list of schedules.
     * @param schedules 
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    /**
     * Method that compares a classroom with another one.
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Classroom other = (Classroom) obj;
        if (this.id_classroom != other.id_classroom) {
            return false;
        }
        return true;
    }
   
    /**
     * Method that gets a classroom by idClassroom
     * @param idClassroom
     * @return Classroom
     */
    public Classroom returnClassroomById(int idClassroom){
        ClassroomDAO classroomDao = new ClassroomDAO();
        return classroomDao.getClassroomById(idClassroom);
    }
    
       
}
