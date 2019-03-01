package model;

import java.util.*;

/**
 * User Class
 * @author Group2
 */
public class User {
    private String dni;
    private String pass;
    private String email;
    private String name;
    private String surname;
    private String role;
    private Date birthdate;
    private String address;
    private List<Subject> subjects;

    /**
     * Method that returns user's dni.
     * @return dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * Method that returns user's pass.
     * @return pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * Method that returns user's email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method that returns user's name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method that returns user's surname.
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Method that returns user's role.
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Method that returns user's birthdate.
     * @return birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Method that returns user's address.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method that returns a list of subjects.
     * @return subjects
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Method that sets user's dni.
     * @param dni of user
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Method that sets user's pass.
     * @param pass of user
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Method that sets user's email.
     * @param email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method that sets user's name.
     * @param name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that sets user's surname.
     * @param surname of user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Method that sets user's role.
     * @param role of user
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Method that sets user's birthdate.
     * @param birthdate of user
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Method that sets user's adderss.
     * @param address of user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method that sets a list of subjects.
     * @param subjects is a List of Subject
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * Method wich return a user object in String format.
     * @return String
     */
    @Override
    public String toString() {
        return "User{" + "dni=" + dni + ", pass=" + pass + ", email=" + email + ", name=" + name + ", surname=" + surname + ", role=" + role + ", birthdate=" + birthdate + ", address=" + address + ", subjects=" + subjects + '}';
    }

    /**
     * Class default constructor. 
     */
    public User(){

    }

    /**
     * Class constructor
     * @param dni
     * @param pass
     * @param email
     * @param name
     * @param surname
     * @param role
     * @param birthdate
     * @param address
     * @param subjects 
     */
    public User(String dni, String pass, String email, String name, String surname, String role, Date birthdate, String address, List<Subject> subjects) {
        this.dni = dni;
        this.pass = pass;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.birthdate = birthdate;
        this.address = address;
        this.subjects = subjects;
    }
    
    /**
     * Method that returns if a user has started a Session
     * @param dni
     * @param pass
     * @return boolean
     */
    public boolean returnStartSession(String dni,String pass){
        UserDAO user = new UserDAO();
        boolean ret = user.returnStartSession(dni, pass);
        
        return ret;   
    }
    
    /**
     * Method that returns User Session
     * @param dni
     * @param pass
     * @return User
     */
    public User returnUserSession(String dni,String pass){
        UserDAO user = new UserDAO();
        return user.returnUserSession(dni,pass);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        return true;
    }
    
    
}
