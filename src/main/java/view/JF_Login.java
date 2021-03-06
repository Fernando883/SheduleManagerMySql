package view;

import controller.ControllerLogin;

/**
 * Class JF_Login (window)
 * @author Group2
 */
public class JF_Login extends javax.swing.JFrame {
    /**
     * Creates new form JF_Login
     */
    public JF_Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jLabelPass = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButtonSignIn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelHead = new javax.swing.JLabel();
        jLabelicon = new javax.swing.JLabel();
        jButtonEs = new javax.swing.JButton();
        jButtonEn = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabelFooter = new javax.swing.JLabel();
        jLabelWarning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion de Horarios");
        setBounds(new java.awt.Rectangle(450, 250, 600, 300));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabelName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/Login"); // NOI18N
        jLabelName.setText(bundle.getString("JF_Login.name")); // NOI18N

        jLabelPass.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabelPass.setText(bundle.getString("JF_Login.password")); // NOI18N

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                enter2(evt);
            }
        });

        jButtonSignIn.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButtonSignIn.setText(bundle.getString("JF_Login.entrar")); // NOI18N
        jButtonSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignInActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabelHead.setBackground(new java.awt.Color(255, 255, 255));
        jLabelHead.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabelHead.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHead.setText(bundle.getString("JF_Login.head")); // NOI18N

        jLabelicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login-2.png"))); // NOI18N

        jButtonEs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/españa.jpg"))); // NOI18N
        jButtonEs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEsActionPerformed(evt);
            }
        });

        jButtonEn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ingles.jpg"))); // NOI18N
        jButtonEn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabelicon)
                .addGap(48, 48, 48)
                .addComponent(jLabelHead, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jButtonEs, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonEn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelicon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEn, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEs, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelHead.getAccessibleContext().setAccessibleName(bundle.getString("JF_Login.head")); // NOI18N

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                enter(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        jLabelFooter.setForeground(new java.awt.Color(204, 204, 204));
        jLabelFooter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFooter.setText(bundle.getString("JF_Login.footer")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelFooter.getAccessibleContext().setAccessibleName(bundle.getString("JF_Login.footer")); // NOI18N

        jLabelWarning.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabelWarning.setForeground(new java.awt.Color(255, 0, 0));
        jLabelWarning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSignIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(jLabelPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                            .addComponent(jTextField1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jButtonSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jLabelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabelName.getAccessibleContext().setAccessibleName(bundle.getString("JF_Login.name")); // NOI18N
        jLabelName.getAccessibleContext().setAccessibleDescription(bundle.getString("JF_Login.name")); // NOI18N
        jLabelPass.getAccessibleContext().setAccessibleName(bundle.getString("JF_Login.password")); // NOI18N
        jButtonSignIn.getAccessibleContext().setAccessibleName(bundle.getString("JF_Login.entrar")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    private void jButtonEsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEsActionPerformed
        ControllerLogin controllerLogin = new ControllerLogin();
        controllerLogin.actionPerformedEs(evt,jLabelName,jLabelPass,jButtonSignIn,jLabelFooter);
    }//GEN-LAST:event_jButtonEsActionPerformed

    private void jButtonEnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnActionPerformed
        ControllerLogin controllerLogin = new ControllerLogin();
        controllerLogin.actionPerformed(evt,jLabelName,jLabelPass,jButtonSignIn,jLabelFooter);
    }//GEN-LAST:event_jButtonEnActionPerformed

    private void jButtonSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignInActionPerformed
        String name = jTextField1.getText();
        String pass = jPasswordField1.getText();
        ControllerLogin controllerSignIn = new ControllerLogin();
        controllerSignIn.actionPerformedSignIn(name,pass,jLabelWarning, this);
        jPasswordField1.setText("");
    }//GEN-LAST:event_jButtonSignInActionPerformed

    private void enter(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_enter
        if(evt.getKeyCode() == evt.VK_ENTER){ 
            String name = jTextField1.getText();
            String pass = jPasswordField1.getText();
            ControllerLogin controllerSignIn = new ControllerLogin();
            controllerSignIn.actionPerformedSignIn(name,pass,jLabelWarning, this);
            jPasswordField1.setText("");
        } 
    }//GEN-LAST:event_enter

    private void enter2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_enter2
        if(evt.getKeyCode() == evt.VK_ENTER){ 
            String name = jTextField1.getText();
            String pass = jPasswordField1.getText();
            ControllerLogin controllerSignIn = new ControllerLogin();
            controllerSignIn.actionPerformedSignIn(name,pass,jLabelWarning, this);
            jPasswordField1.setText("");
        } 
    }//GEN-LAST:event_enter2
    
    
    
    /**
     * @param args the command line arguments
     */   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JF_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JF_Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEn;
    private javax.swing.JButton jButtonEs;
    private javax.swing.JButton jButtonSignIn;
    private javax.swing.JLabel jLabelFooter;
    private javax.swing.JLabel jLabelHead;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPass;
    private javax.swing.JLabel jLabelWarning;
    private javax.swing.JLabel jLabelicon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
