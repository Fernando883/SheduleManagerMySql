package model;

import java.util.*;

/**
 * Titulation Class 
 * @author Group2
 */
public class Titulation {
    private int id_titulation;
    private String name;
    private ArrayList<Subject> subjects;

    /**
     * Class default constructor
     */
    public Titulation() {}

    /**
     * Class constructor
     * @param id_titulation
     * @param name
     * @param subjects 
     */
    public Titulation(int id_titulation, String name, ArrayList<Subject> subjects) {
        this.id_titulation = id_titulation;
        this.name = name;
        this.subjects = subjects;
    }

    /**
     * Method that returns titulation's id.
     * @return id_titulation.
     */
    public int getId_titulation() {
        return id_titulation;
    }
    
    /**
     * Method that returns titulation's name.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Method that returns a Arraylist of subjects.
     * @return subjects
     */
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Method that sets titulation's id.
     * @param id_titulation of titulation
     */
    public void setId_titulation(int id_titulation) {
        this.id_titulation = id_titulation;
    }

    /**
     * Method that sets titulation's name.
     * @param name of titulation
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that sets titulation's subejcts.
     * @param subjects of titulation
     */
    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * Method that returns a titulation object in String format.
     * @return String
     */
    @Override
    public String toString() {
        return "Titulation{" + "id_titulation=" + id_titulation + ", name=" + name + ", subjects=" + subjects + '}';
    }
    
    /**
     * Method that returns a list of all titulation
     * @return ArrayList Titulation
     */
    public ArrayList<Titulation> returnsTitulationList(){
        TitulationDAO titulationDao = new TitulationDAO();
        return titulationDao.getTitulations();
    }
    
    /**
     * Method that returns a list of Titulations of a specific user
     * @param applicant
     * @return ArrayList Titulation
     */
    public ArrayList<Titulation> returnsTitulationSubjectList(String applicant){
        TitulationDAO titulationDao = new TitulationDAO();
        return titulationDao.getTitulationsSubjects(applicant);
    }
    
}
