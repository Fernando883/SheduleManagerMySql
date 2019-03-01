package model;

import java.util.*;

/**
 * Class Request
 * @author Group2
 */
public class Request {
    
    private int id_request;
    private String applicant;
    private String message;
    private String state;
    private String subject;
    private List<User> users;
    
    /**
     * Method which gets request's id.
     * @return id_request
     */
    public int getId_request() {
        return id_request;
    }
    
    /**
     * Method which gets applicant's dni.
     * @return apliccant
     */
    public String getApplicant() {
        return applicant;
    }
    
    /**
     * Method which gets request's message.
     * @return message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Method which gets request's state.
     * @return state
     */
    public String getState() {
        return state;
    }
    
    /**
     * Method which gets request's subject.
     * @return subject
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Method which returns a list of user.
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }
    
    /**
     * Method which sets request's id.
     * @param id_request of request 
     */
    public void setId_request(int id_request) {
        this.id_request = id_request;
    }
     
    /**
     * Method which sets request's applicant.
     * @param applicant of request 
     */
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    
    /**
     * Method which sets request's message.
     * @param message of request 
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method which sets request's state.
     * @param state of request 
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * Method which sets request's subject.
     * @param subject of request 
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * Method which sets a list of users.
     * @param users is a List of user.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    /**
     * Method becomes the object to string
     * @return String
     */
    @Override
    public String toString() {
        return "Request{" + "id_request=" + id_request + ", applicant=" + applicant + ", message=" + message + ", state=" + state + ", subject=" + subject + ", users=" + users + '}';
    }
    
    /**
     * Class constructor.
     * @param id_request
     * @param applicant
     * @param message
     * @param state
     * @param subject
     * @param users 
     */
    public Request(int id_request, String applicant, String message, String state, String subject, List<User> users) {
        this.id_request = id_request;
        this.applicant = applicant;
        this.message = message;
        this.state = state;
        this.subject = subject;
        this.users = users;
    }
    
    /**
     * Default Class Constructor
     */
    public Request(){}
    
    /**
     * Method which gets a list of Requests for a user through the RequestDAO
     * @param applicant
     * @return ArrayList Request
     */
    public ArrayList<Request> returnListRequest(String applicant){
        RequestDAO requestDAO = new RequestDAO();
        ArrayList<Request> list = requestDAO.returnListRequest(applicant);
        return list;
    }
    
    /**
     * Method that gets a list of Requests for all user through the RequestDAO
     * @return ArrayList Request
     */
    public ArrayList<Request> returnListRequest1(){
        RequestDAO requestDAO = new RequestDAO();
        ArrayList<Request> list = requestDAO.returnListRequest1();
        return list;
    }
    
    /**
     * Method that sets the Request through the RequestDAO
     * @param fila
     * @param status 
     */
    public void SetAdminRequest(int fila,String status){
        RequestDAO requestAdminDAO = new RequestDAO();
        requestAdminDAO.SetAdminRequest(fila,status);
    }
    
    /**
     * Method that gets the selected message by user through the RequestDAO
     * @param row
     * @return String
     */
    public String getSelectedMessage(int row){
        RequestDAO requestDAO = new RequestDAO();
        Request requests =requestDAO.returnMessageRequest(row);
        return requests.getMessage();
    }
    
    /**
     * Method that gets if the request was setting through the RequestDAO
     * @param user
     * @param subject
     * @param text
     * @return boolean
     */
     public boolean setRequest(String user, String subject, String text){
        RequestDAO requestDAO = new RequestDAO();
        boolean ok = requestDAO.insertRequest(user,subject,text);
        return ok;
    }
}
