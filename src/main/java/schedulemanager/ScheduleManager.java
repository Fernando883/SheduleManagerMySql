/*
 * //////////////////////////    SCHEDULE MANAGER    //////////////////////////
 *
 * App Description: This desktop app is intended for users of a University Faculty.
 *          You can loggin as a student, professor, director or admin.
 *          According to your kind of user, you'll be able to view, create or
 *          update your schedules.
 */

package schedulemanager;

import view.JF_Login;

/**
 *
 * @author Group2
 */
public class ScheduleManager {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            JF_Login view= new JF_Login();
                   
            public void run() {
                view.setVisible(true);
            }
        });
    }
    
}
