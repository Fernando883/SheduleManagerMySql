package controller;

import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import model.User;
import persistence.ConnectionDB;
import view.JF_Login;
import view.JF_ViewSchedule;

/**
 * Login Controller Class
 * @author Group2
 */
public class ControllerLogin implements ActionListener{
    public static JF_Login vistauser;
    ConnectionDB connectionDB = ConnectionDB.getInstance();

    /**
     * Method change language to English 
     * @param e
     * @param name
     * @param password
     * @param signin
     * @param footer 
     */
    public void actionPerformed(ActionEvent e,JLabel name,JLabel password,JButton signin,JLabel footer) {
        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/Login", locale);
        name.setText(bundle.getString("JF_Login.name"));
        password.setText(bundle.getString("JF_Login.password"));
        signin.setText(bundle.getString("JF_Login.entrar"));
        footer.setText(bundle.getString("JF_Login.footer"));
    }
    
    /**
     * Method change language to Spanish
     * @param e
     * @param name
     * @param password
     * @param signin
     * @param footer 
     */
    public void actionPerformedEs(ActionEvent e,JLabel name,JLabel password,JButton signin,JLabel footer) {
        Locale locale = new Locale("es", "ES");
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/Login", locale);
        name.setText(bundle.getString("JF_Login.name"));
        password.setText(bundle.getString("JF_Login.password"));
        signin.setText(bundle.getString("JF_Login.entrar"));
        footer.setText(bundle.getString("JF_Login.footer"));
    }
    
    /**
     * Method for logging a user
     * @param dni
     * @param pass
     * @param jLabelWarning
     * @param login 
     */
    public void actionPerformedSignIn(String dni,String pass, JLabel jLabelWarning, JF_Login login) {
        User user = new User();
        if(user.returnStartSession(dni, pass)){
            user = user.returnUserSession(dni,pass);
            JF_ViewSchedule api = new JF_ViewSchedule(user);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    vistauser = login;
                    api.setVisible(true);
                    login.setVisible(false);
                }
            }); 
        }else{
            jLabelWarning.setText("El usuario o contraseña introducido es incorrecto");
        }
        
    }
    
    /**
     * Method to close a user's session
     * @param section 
     */
    public void closeSession(JF_ViewSchedule section){
        section.setAlwaysOnTop(false);
        if(JOptionPane.showConfirmDialog (null, new Object[]{"¿Desea cerrar la sesión?"},"JOPtion", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            section.setVisible(false);
            ControllerLogin.vistauser.setVisible(true);
            connectionDB.deleteInstance();
        }
        else{
        
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
