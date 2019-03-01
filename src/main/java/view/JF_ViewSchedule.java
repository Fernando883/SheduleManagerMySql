package view;

import controller.Controller;
import controller.ControllerLogin;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.Request;
import model.Subject;
import model.Titulation;
import model.User;
import model.Schedule;
import model.Classroom;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class JF_ViewSchedule (window)
 * @author Group2
 */
public class JF_ViewSchedule extends javax.swing.JFrame {

    /**
     * Creates new form JF_ViewSchedule
     */
    String titulo[] ={"Titulación", "Curso","Cuatrimestre","Asignatura","Día","Hora","Aula"};
    DefaultTableModel tableJSchedule = new DefaultTableModel(null,titulo);
    User user = null;
    String name_user;
    
    
    public JF_ViewSchedule() {
        InputMap map = new InputMap();
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");    
        
        initComponents();       
        
        jButtonLogout.setToolTipText("Cerrar sesión");
        fillTreeSchedule();
    }
    
     public JF_ViewSchedule(User user) {
        InputMap map = new InputMap();
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "pressed");
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released"); 
        
        initComponents();     
        
//        jTabbedPane.removeTabAt(0);
        switch(user.getRole()){
            case "Alumno":
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(1);
            break;
            
            case "Profesor":
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(3);
            break;
            
            case "Jefe de estudios":
                jTabbedPane.removeTabAt(1);
                jTabbedPane.removeTabAt(2);
                jTabbedPane.removeTabAt(2);
            break;
            
            case "Administrador":
                jTabbedPane.removeTabAt(2);
                jTabbedPane.removeTabAt(2);
                jTabbedPane.removeTabAt(2);
                jTabbedPane.removeTabAt(2);
            break;
        }
        
        
        jButtonLogout.setToolTipText("Cerrar sesión");
        this.user = user;
        jLabelWelcomeTitle.setText("GESTOR DE HORARIOS UMA - BIENVENIDO/A "+user.getName()+ " " + user.getSurname());
        fillTreeSchedule();
        
    }
    
    private void fillTreeSchedule(){
        DefaultTreeModel model = (DefaultTreeModel) jTreeLeftPanel.getModel();
        Controller controller = new Controller();
        
        ArrayList<Titulation> titulations = controller.getTitulationsAndSubjects(user.getDni());
        
        for (Titulation titulation : titulations) {            
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.add(new DefaultMutableTreeNode(titulation.getName()));
            int id_titulation = titulation.getId_titulation();
            
            root = root.getNextNode();
            ArrayList<String> courses = controller.getCoursesTitulationUser(user.getDni(), id_titulation);
            for (String course : courses) {
                root.add(new DefaultMutableTreeNode(course));
                
                root = root.getNextNode();
                ArrayList<String> quarters = controller.getQuartersTitulationUser(user.getDni(), id_titulation, course);
                
                for (String quarter : quarters) {
                    root.add(new DefaultMutableTreeNode(quarter));
                }
            }
        }
    }
    
    private void printSchedule() throws FileNotFoundException, DocumentException {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeLeftPanel.getLastSelectedPathComponent();
        Controller controller = new Controller();
        String titulation = null;
        String quarter = null;
        String course = null;
        
        if(node == null)
            return;
        if(node.getLevel() == 3){
            quarter = (String)node.getUserObject();
            
            node = node.getPreviousNode();
            course = (String)node.getUserObject();
            
            node = node.getPreviousNode();
            titulation = (String)node.getUserObject();
        }
        
        ArrayList<Schedule> schedules = controller.getSchedules(quarter, course, titulation);

        FileOutputStream archivo = new FileOutputStream("horarioPDF.pdf");
        Document document = new Document(PageSize.A2.rotate());
        PdfWriter.getInstance(document, archivo);

        document.open();

        Font title = new Font();
        Font subTitle = new Font();
        Font body = new Font();
        title.setSize(28);
        subTitle.setSize(22);
        body.setSize(20);

        Paragraph pTitle = new Paragraph("Horario " + titulation + " - Curso: " + course + " - Cuatrimestre: " + quarter, title);
        document.add(pTitle);

        Paragraph separator = new Paragraph("\n \n \n \n \n ");
        document.add(separator);


        PdfPTable table = new PdfPTable(5);
        table.setWidths(new int[]{ 2, 2, 3, 1, 1 });
        table.setWidthPercentage(100);
        PdfPCell cell;

        // row 1, cell 1
        cell = new PdfPCell(new Phrase("Día", subTitle));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        // row 1, cell 2
        cell = new PdfPCell(new Phrase("Hora", subTitle));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        // row 1, cell 3
        cell = new PdfPCell(new Phrase("Asignatura", subTitle));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        // row 1, cell 4
        cell = new PdfPCell(new Phrase("Edificio", subTitle));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        // row 1, cell 5
        cell = new PdfPCell(new Phrase("Aula", subTitle));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        for (Schedule schedule : schedules) {
            int idSubject = schedule.getSubject();
            int idClassroom = schedule.getClassroom();
            String subjectName = controller.getSubjectById(idSubject);
            Classroom classroom = controller.getClassroomById(idClassroom);
            String day = schedule.getDay();

            cell = new PdfPCell(new Phrase(schedule.getDay(), body));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(schedule.getHour(), body));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(subjectName, body));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(classroom.getBuilding(), body));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(classroom.getName(), body));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        document.add(table);

        document.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLeft = new javax.swing.JPanel();
        jSeparatorLeftPanel = new javax.swing.JSeparator();
        jPanelMenu = new javax.swing.JPanel();
        jScrollPaneLeftPanel = new javax.swing.JScrollPane();
        jTreeLeftPanel = new javax.swing.JTree();
        jLabelLogoImg = new javax.swing.JLabel();
        jPanelRight = new javax.swing.JPanel();
        jPanelRTop = new javax.swing.JPanel();
        jLabelWelcomeTitle = new javax.swing.JLabel();
        jButtonLogout = new javax.swing.JButton();
        jPanelRTab = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelViewSchedule = new javax.swing.JPanel();
        jPanelDownloadButton = new javax.swing.JPanel();
        jButtonDownloadPDF = new javax.swing.JButton();
        jScrollPaneViewSchedule = new javax.swing.JScrollPane();
        jTableSchedule = new javax.swing.JTable();
        jPanelMakeSchedule = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jComboBoxYear = new javax.swing.JComboBox();
        jComboBoxTit = new javax.swing.JComboBox();
        jComboBoxCourse = new javax.swing.JComboBox();
        jComboBoxQuarter = new javax.swing.JComboBox();
        jComboBoxSubject = new javax.swing.JComboBox();
        jComboBoxDay = new javax.swing.JComboBox();
        jComboBoxHour = new javax.swing.JComboBox();
        jComboBoxClassroom = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablesSetSchedule = new javax.swing.JTable();
        jPanelUpdateSchedule = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jComboBoxYearMod = new javax.swing.JComboBox();
        jComboBoxTitMod = new javax.swing.JComboBox();
        jComboBoxCourseMod = new javax.swing.JComboBox();
        jComboBoxQuarterMod = new javax.swing.JComboBox();
        jComboBoxSubMod = new javax.swing.JComboBox();
        jComboBoxDayMod = new javax.swing.JComboBox();
        jComboBoxHourMod = new javax.swing.JComboBox();
        jComboBoxClassMod = new javax.swing.JComboBox();
        jButtonSel = new javax.swing.JButton();
        jPanelMod = new javax.swing.JPanel();
        jScrollPaneMod = new javax.swing.JScrollPane();
        jTableMod = new javax.swing.JTable();
        jToolBar3 = new javax.swing.JToolBar();
        jComboBoxDaySel = new javax.swing.JComboBox();
        jComboBoxHourSel = new javax.swing.JComboBox();
        jComboBoxClassSel = new javax.swing.JComboBox();
        jButtonMod = new javax.swing.JButton();
        jLabelModified = new javax.swing.JLabel();
        jPanelSendRequest = new javax.swing.JPanel();
        jLabelSubject = new javax.swing.JLabel();
        jTextFieldReqSubj = new javax.swing.JTextField();
        jLabelReqText = new javax.swing.JLabel();
        jLabelRequest = new javax.swing.JLabel();
        jButtonSendRequest = new javax.swing.JButton();
        jLabelReqSend = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaResq = new javax.swing.JTextArea();
        jPanelViewRequest = new javax.swing.JPanel();
        jScrollPanelViewRequest = new javax.swing.JScrollPane();
        jTableRequests = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneMessageRequest = new javax.swing.JTextPane();
        jPanelAdminRequest = new javax.swing.JPanel();
        jScrollPanelViewRequest1 = new javax.swing.JScrollPane();
        jTableRequests1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPaneMessageRequest1 = new javax.swing.JTextPane();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        JLabelRequestMod = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1280, 800));
        setName("jf_view_schedule"); // NOI18N

        jPanelLeft.setBackground(new java.awt.Color(255, 255, 255));

        jPanelMenu.setBackground(new java.awt.Color(255, 255, 255));

        jTreeLeftPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTreeLeftPanel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Titulación");
        jTreeLeftPanel.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeLeftPanel.setCellRenderer(null);
        jTreeLeftPanel.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeLeftPanelValueChanged(evt);
            }
        });
        jScrollPaneLeftPanel.setViewportView(jTreeLeftPanel);

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneLeftPanel)
                .addContainerGap())
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addComponent(jScrollPaneLeftPanel)
                .addContainerGap())
        );

        jLabelLogoImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLogoImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/master_inftel.png"))); // NOI18N

        javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
        jPanelLeft.setLayout(jPanelLeftLayout);
        jPanelLeftLayout.setHorizontalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelLogoImg, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jSeparatorLeftPanel))
                .addContainerGap())
        );
        jPanelLeftLayout.setVerticalGroup(
            jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogoImg, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorLeftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelRight.setBackground(new java.awt.Color(255, 255, 255));

        jPanelRTop.setBackground(new java.awt.Color(255, 255, 255));
        jPanelRTop.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabelWelcomeTitle.setFont(new java.awt.Font("Arial Unicode MS", 0, 18)); // NOI18N
        jLabelWelcomeTitle.setText("GESTOR DE HORARIOS UMA - BIENVENIDO/A ");

        jButtonLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout2.jpg"))); // NOI18N
        jButtonLogout.setBorderPainted(false);
        jButtonLogout.setMaximumSize(new java.awt.Dimension(31, 31));
        jButtonLogout.setMinimumSize(new java.awt.Dimension(31, 31));
        jButtonLogout.setPreferredSize(new java.awt.Dimension(31, 31));
        jButtonLogout.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.jpg"))); // NOI18N
        jButtonLogout.setRequestFocusEnabled(false);
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRTopLayout = new javax.swing.GroupLayout(jPanelRTop);
        jPanelRTop.setLayout(jPanelRTopLayout);
        jPanelRTopLayout.setHorizontalGroup(
            jPanelRTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelWelcomeTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanelRTopLayout.setVerticalGroup(
            jPanelRTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelWelcomeTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanelRTab.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPaneMousePressed(evt);
            }
        });

        jPanelViewSchedule.setBackground(new java.awt.Color(255, 255, 255));

        jPanelDownloadButton.setBackground(new java.awt.Color(255, 255, 255));

        jButtonDownloadPDF.setText("Descargar PDF");
        jButtonDownloadPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownloadPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDownloadButtonLayout = new javax.swing.GroupLayout(jPanelDownloadButton);
        jPanelDownloadButton.setLayout(jPanelDownloadButtonLayout);
        jPanelDownloadButtonLayout.setHorizontalGroup(
            jPanelDownloadButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDownloadButtonLayout.createSequentialGroup()
                .addContainerGap(886, Short.MAX_VALUE)
                .addComponent(jButtonDownloadPDF)
                .addContainerGap())
        );
        jPanelDownloadButtonLayout.setVerticalGroup(
            jPanelDownloadButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDownloadButtonLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jButtonDownloadPDF)
                .addContainerGap())
        );

        jTableSchedule.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTableSchedule.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"
            }
        ));
        jTableSchedule.setMinimumSize(new java.awt.Dimension(90, 300));
        jTableSchedule.setRowHeight(40);
        jScrollPaneViewSchedule.setViewportView(jTableSchedule);

        javax.swing.GroupLayout jPanelViewScheduleLayout = new javax.swing.GroupLayout(jPanelViewSchedule);
        jPanelViewSchedule.setLayout(jPanelViewScheduleLayout);
        jPanelViewScheduleLayout.setHorizontalGroup(
            jPanelViewScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDownloadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelViewScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPaneViewSchedule, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE))
        );
        jPanelViewScheduleLayout.setVerticalGroup(
            jPanelViewScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelViewScheduleLayout.createSequentialGroup()
                .addGap(0, 709, Short.MAX_VALUE)
                .addComponent(jPanelDownloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelViewScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelViewScheduleLayout.createSequentialGroup()
                    .addComponent(jScrollPaneViewSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 54, Short.MAX_VALUE)))
        );

        jTabbedPane.addTab("Horario", jPanelViewSchedule);

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);

        jComboBoxYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        jComboBoxYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxYearItemStateChanged(evt);
            }
        });
        jToolBar1.add(jComboBoxYear);

        jComboBoxTit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBoxTit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Titulación" }));
        jComboBoxTit.setToolTipText("Titulación");
        jComboBoxTit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTitItemStateChanged(evt);
            }
        });
        jToolBar1.add(jComboBoxTit);

        jComboBoxCourse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Curso" }));
        jComboBoxCourse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxCourseItemStateChanged(evt);
            }
        });
        jToolBar1.add(jComboBoxCourse);

        jComboBoxQuarter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cuatrimestre" }));
        jComboBoxQuarter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxQuarterItemStateChanged(evt);
            }
        });
        jToolBar1.add(jComboBoxQuarter);

        jComboBoxSubject.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Asignatura" }));
        jComboBoxSubject.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxSubjectItemStateChanged(evt);
            }
        });
        jComboBoxSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSubjectActionPerformed(evt);
            }
        });
        jToolBar1.add(jComboBoxSubject);

        jComboBoxDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Día" }));
        jComboBoxDay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDayItemStateChanged(evt);
            }
        });
        jComboBoxDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDayActionPerformed(evt);
            }
        });
        jToolBar1.add(jComboBoxDay);

        jComboBoxHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hora" }));
        jComboBoxHour.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxHourItemStateChanged(evt);
            }
        });
        jToolBar1.add(jComboBoxHour);

        jComboBoxClassroom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aula" }));
        jComboBoxClassroom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClassroomActionPerformed(evt);
            }
        });
        jToolBar1.add(jComboBoxClassroom);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add-icon.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jToolBar1.add(jButton1);

        jTablesSetSchedule.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTablesSetSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Titulación", "Curso", "Cuatrimestre", "Asignatura", "Día", "Hora", "Aula"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTablesSetSchedule);
        if (jTablesSetSchedule.getColumnModel().getColumnCount() > 0) {
            jTablesSetSchedule.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelMakeScheduleLayout = new javax.swing.GroupLayout(jPanelMakeSchedule);
        jPanelMakeSchedule.setLayout(jPanelMakeScheduleLayout);
        jPanelMakeScheduleLayout.setHorizontalGroup(
            jPanelMakeScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
            .addGroup(jPanelMakeScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMakeScheduleLayout.setVerticalGroup(
            jPanelMakeScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMakeScheduleLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Crear Horario", jPanelMakeSchedule);

        jToolBar2.setBorder(null);
        jToolBar2.setRollover(true);

        jComboBoxYearMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        jComboBoxYearMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxYearModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxYearMod);

        jComboBoxTitMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Titulación" }));
        jComboBoxTitMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTitModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxTitMod);

        jComboBoxCourseMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Curso" }));
        jComboBoxCourseMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxCourseModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxCourseMod);

        jComboBoxQuarterMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cuatrimestre" }));
        jComboBoxQuarterMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxQuarterModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxQuarterMod);

        jComboBoxSubMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Asignatura" }));
        jComboBoxSubMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxSubModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxSubMod);

        jComboBoxDayMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Día" }));
        jComboBoxDayMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDayModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxDayMod);

        jComboBoxHourMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hora" }));
        jComboBoxHourMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxHourModItemStateChanged(evt);
            }
        });
        jToolBar2.add(jComboBoxHourMod);

        jComboBoxClassMod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aula" }));
        jToolBar2.add(jComboBoxClassMod);

        jButtonSel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/select-icon.png"))); // NOI18N
        jButtonSel.setBorder(null);
        jButtonSel.setFocusable(false);
        jButtonSel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonSelMousePressed(evt);
            }
        });
        jToolBar2.add(jButtonSel);

        jTableMod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Año", "Titulación", "Curso", "Cuatrimestre", "Asignatura", "Día", "Hora", "Aula"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneMod.setViewportView(jTableMod);

        jToolBar3.setBorder(null);
        jToolBar3.setRollover(true);

        jComboBoxDaySel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nuevo Día" }));
        jComboBoxDaySel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxDaySelItemStateChanged(evt);
            }
        });
        jToolBar3.add(jComboBoxDaySel);

        jComboBoxHourSel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nuevo Hora" }));
        jComboBoxHourSel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxHourSelItemStateChanged(evt);
            }
        });
        jToolBar3.add(jComboBoxHourSel);

        jComboBoxClassSel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nueva Aula" }));
        jToolBar3.add(jComboBoxClassSel);

        jButtonMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update-icon.png"))); // NOI18N
        jButtonMod.setBorder(null);
        jButtonMod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonModMousePressed(evt);
            }
        });
        jToolBar3.add(jButtonMod);

        jLabelModified.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabelModified.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelModLayout = new javax.swing.GroupLayout(jPanelMod);
        jPanelMod.setLayout(jPanelModLayout);
        jPanelModLayout.setHorizontalGroup(
            jPanelModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneMod)
            .addGroup(jPanelModLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelModLayout.createSequentialGroup()
                        .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanelModLayout.createSequentialGroup()
                        .addComponent(jLabelModified, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanelModLayout.setVerticalGroup(
            jPanelModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelModLayout.createSequentialGroup()
                .addComponent(jScrollPaneMod, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabelModified, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelUpdateScheduleLayout = new javax.swing.GroupLayout(jPanelUpdateSchedule);
        jPanelUpdateSchedule.setLayout(jPanelUpdateScheduleLayout);
        jPanelUpdateScheduleLayout.setHorizontalGroup(
            jPanelUpdateScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUpdateScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanelMod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelUpdateScheduleLayout.setVerticalGroup(
            jPanelUpdateScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUpdateScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(347, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Modificar Horario", jPanelUpdateSchedule);

        jPanelSendRequest.setToolTipText("");

        jLabelSubject.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelSubject.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSubject.setText(" Asunto");

        jTextFieldReqSubj.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextFieldReqSubj.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabelReqText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabelRequest.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelRequest.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelRequest.setText(" Solicitud");

        jButtonSendRequest.setText("Enviar Solicitud");
        jButtonSendRequest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSendRequestMouseClicked(evt);
            }
        });

        jLabelReqSend.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabelReqSend.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jTextAreaResq.setColumns(20);
        jTextAreaResq.setRows(5);
        jScrollPane3.setViewportView(jTextAreaResq);

        javax.swing.GroupLayout jPanelSendRequestLayout = new javax.swing.GroupLayout(jPanelSendRequest);
        jPanelSendRequest.setLayout(jPanelSendRequestLayout);
        jPanelSendRequestLayout.setHorizontalGroup(
            jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSendRequestLayout.createSequentialGroup()
                .addGroup(jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSendRequestLayout.createSequentialGroup()
                        .addGap(633, 633, 633)
                        .addComponent(jLabelReqText, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                    .addGroup(jPanelSendRequestLayout.createSequentialGroup()
                        .addGroup(jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSendRequestLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldReqSubj, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabelRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                                        .addComponent(jLabelSubject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSendRequestLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonSendRequest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelReqSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelSendRequestLayout.setVerticalGroup(
            jPanelSendRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSendRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSubject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldReqSubj, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRequest)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabelReqSend, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSendRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelReqText)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Enviar solicitud", jPanelSendRequest);

        jTableRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Asunto", "Estado"
            }
        ));
        jTableRequests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableRequestsMousePressed(evt);
            }
        });
        jScrollPanelViewRequest.setViewportView(jTableRequests);

        jScrollPane1.setViewportView(jTextPaneMessageRequest);

        javax.swing.GroupLayout jPanelViewRequestLayout = new javax.swing.GroupLayout(jPanelViewRequest);
        jPanelViewRequest.setLayout(jPanelViewRequestLayout);
        jPanelViewRequestLayout.setHorizontalGroup(
            jPanelViewRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelViewRequestLayout.createSequentialGroup()
                .addComponent(jScrollPanelViewRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
        );
        jPanelViewRequestLayout.setVerticalGroup(
            jPanelViewRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanelViewRequest, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );

        jTabbedPane.addTab("Ver solicitudes", jPanelViewRequest);

        jTableRequests1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Asunto", "Estado"
            }
        ));
        jTableRequests1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableRequests1MousePressed(evt);
            }
        });
        jScrollPanelViewRequest1.setViewportView(jTableRequests1);

        jScrollPane4.setViewportView(jTextPaneMessageRequest1);

        jButton2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton2.setText("ACEPTAR");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton3.setText("DENEGAR");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });

        JLabelRequestMod.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanelAdminRequestLayout = new javax.swing.GroupLayout(jPanelAdminRequest);
        jPanelAdminRequest.setLayout(jPanelAdminRequestLayout);
        jPanelAdminRequestLayout.setHorizontalGroup(
            jPanelAdminRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdminRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAdminRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAdminRequestLayout.createSequentialGroup()
                        .addComponent(jScrollPanelViewRequest1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
                    .addGroup(jPanelAdminRequestLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(JLabelRequestMod, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelAdminRequestLayout.setVerticalGroup(
            jPanelAdminRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdminRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAdminRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPanelViewRequest1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAdminRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLabelRequestMod, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(185, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Administrar solicitudes", jPanelAdminRequest);

        javax.swing.GroupLayout jPanelRTabLayout = new javax.swing.GroupLayout(jPanelRTab);
        jPanelRTab.setLayout(jPanelRTabLayout);
        jPanelRTabLayout.setHorizontalGroup(
            jPanelRTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        jPanelRTabLayout.setVerticalGroup(
            jPanelRTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );

        jTabbedPane.getAccessibleContext().setAccessibleName("Horario");

        javax.swing.GroupLayout jPanelRightLayout = new javax.swing.GroupLayout(jPanelRight);
        jPanelRight.setLayout(jPanelRightLayout);
        jPanelRightLayout.setHorizontalGroup(
            jPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelRTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelRTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelRightLayout.setVerticalGroup(
            jPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRightLayout.createSequentialGroup()
                .addComponent(jPanelRTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelRTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        ControllerLogin controller = new ControllerLogin();
        controller.closeSession(this);
        
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jTableRequestsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRequestsMousePressed
        Controller controllerRequest = new Controller();
        int row = Integer.parseInt((String)jTableRequests.getValueAt(jTableRequests.getSelectedRow(),0));
        String message = controllerRequest.getSelectedRequest(row);
        jTextPaneMessageRequest.setText(message);
    }//GEN-LAST:event_jTableRequestsMousePressed

    private void jTabbedPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMousePressed
        //Para ver solicitudes
        Controller controllerRequest = new Controller();
        ArrayList<Request> request = controllerRequest.getAllRequest(user.getDni()); 
        String titulo[] ={"Id","Asunto", "Estado"};
        DefaultTableModel m = new DefaultTableModel(null,titulo);
        String fila[] = new String[3];

        for(Request o: request){
            fila[1]=o.getSubject();
            fila[2]=o.getState();
            fila[0]=Integer.toString(o.getId_request());
            m.addRow(fila);
        }
        jTableRequests.setModel(m);
        
         //Para crea horario, carga pestaña año
        Controller controllerYear = new Controller();
        ArrayList<String> yearList = controllerYear.getAllYears(user.getDni());
        Vector comboBoxItemsYears = new Vector();
        comboBoxItemsYears.add("Año");
        for (String year : yearList) {
          comboBoxItemsYears.add(year);
        }    
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsYears);
        jComboBoxYear.setModel(model);
        jComboBoxYearMod.setModel(model);
        
        //PARA CREAR ADMINISTRADOR DE SOLICITUDES
        Controller controllerAdminRequest = new Controller();
        ArrayList<Request> Adminrequest = controllerAdminRequest.getAllAdminRequest();
        String tituloAdmin[] ={"Id","Asunto", "Estado"};
        DefaultTableModel admin = new DefaultTableModel(null,tituloAdmin);
        String fila1[] = new String[3];

        for(Request e: Adminrequest){
            fila1[1]=e.getSubject();
            fila1[2]=e.getState();
            fila1[0]=Integer.toString(e.getId_request());
            admin.addRow(fila1);
        }
        jTableRequests1.setModel(admin);
        
        
        //Para crea horario
        Controller controller = new Controller();
        ArrayList<Titulation> titList = controller.getAllTitulations();
        
        Vector comboBoxItems = new Vector();
        
        comboBoxItems.add("Titulación");
        for (Titulation tit : titList) {
            comboBoxItems.add(tit.getName());
        }
        
        final DefaultComboBoxModel modelTit = new DefaultComboBoxModel(comboBoxItems);
        jComboBoxTit.setModel(modelTit);
    }//GEN-LAST:event_jTabbedPaneMousePressed

    private void jComboBoxTitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTitItemStateChanged
        Vector comboBoxItemsCourse = new Vector();
        Vector comboBoxItemsSubject = new Vector();
        Vector comboBoxItemsQuarter = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        
        comboBoxItemsCourse.add("Curso");
        comboBoxItemsCourse.add("1º");
        comboBoxItemsCourse.add("2º");
        comboBoxItemsCourse.add("3º");
        comboBoxItemsCourse.add("4º");
        comboBoxItemsSubject.add("Asignaturas");
        final DefaultComboBoxModel modelSubject = new DefaultComboBoxModel(comboBoxItemsSubject);
        jComboBoxSubject.setModel(modelSubject);
        comboBoxItemsQuarter.add("Cuatrimestre");
        final DefaultComboBoxModel modelQuarter = new DefaultComboBoxModel(comboBoxItemsQuarter);
        jComboBoxQuarter.setModel(modelQuarter);
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDay.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHour.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassroom.setModel(modelClass);
        final DefaultComboBoxModel modelCourse = new DefaultComboBoxModel(comboBoxItemsCourse);
        jComboBoxCourse.setModel(modelCourse);  
    }//GEN-LAST:event_jComboBoxTitItemStateChanged

    private void jComboBoxCourseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxCourseItemStateChanged
        Vector comboBoxItemsSubject = new Vector();
        Vector comboBoxItemsQuarter = new Vector();
        Vector comboBoxItemsCourse = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        
        comboBoxItemsQuarter.add("Cuatrimestre");
        comboBoxItemsQuarter.add("Primero");
        comboBoxItemsQuarter.add("Segundo");
        comboBoxItemsSubject.add("Asignaturas");
        final DefaultComboBoxModel modelSubject = new DefaultComboBoxModel(comboBoxItemsSubject);
        jComboBoxSubject.setModel(modelSubject);
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDay.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHour.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassroom.setModel(modelClass);
                  
        final DefaultComboBoxModel modelQuarter = new DefaultComboBoxModel(comboBoxItemsQuarter);
        jComboBoxQuarter.setModel(modelQuarter);
    }//GEN-LAST:event_jComboBoxCourseItemStateChanged

    private void jComboBoxQuarterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxQuarterItemStateChanged
        Controller controller = new Controller();
        String titulation = (String) jComboBoxTit.getSelectedItem();
        String course = (String) jComboBoxCourse.getSelectedItem();
        String quarter = (String) jComboBoxQuarter.getSelectedItem();
        ArrayList<Subject> subList = controller.getSubjectsTitulation(titulation, course, quarter);
        
        Vector comboBoxItems = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        
        comboBoxItems.add("Asignatura");
            for (Subject subject : subList) {
                comboBoxItems.add(subject.getName());
            }
            comboBoxItemsDay.add("Día");
            final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
            jComboBoxDay.setModel(modelDay);
            comboBoxItemsHour.add("Hora");
            final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
            jComboBoxHour.setModel(modelHour);
            comboBoxItemsClass.add("Aula");
            final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
            jComboBoxClassroom.setModel(modelClass);
            final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
            jComboBoxSubject.setModel(model);      
    }//GEN-LAST:event_jComboBoxQuarterItemStateChanged

    private void jComboBoxSubjectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxSubjectItemStateChanged
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItems = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        String quarter = (String)  jComboBoxQuarter.getSelectedItem();
        
        Controller controller = new Controller();
        ArrayList<String> days = controller.getAvailableDays(quarter); 
        
        comboBoxItemsDay.add("Día");
        for (String day : days) {
            comboBoxItemsDay.add(day);
        }
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHour.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassroom.setModel(modelClass);  
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDay.setModel(model);
    }//GEN-LAST:event_jComboBoxSubjectItemStateChanged

    private void jComboBoxDayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDayItemStateChanged
        Controller controller = new Controller();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        String day = (String) jComboBoxDay.getSelectedItem();
        String quarter = (String) jComboBoxQuarter.getSelectedItem();
        ArrayList<String> hours = controller.getAvailableHours(day,quarter);
        
        comboBoxItemsHour.add("Hora");
        for (String hour : hours) {
            comboBoxItemsHour.add(hour);
        }
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHour.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassroom.setModel(modelClass);   
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHour.setModel(model);
    }//GEN-LAST:event_jComboBoxDayItemStateChanged

    private void jComboBoxHourItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxHourItemStateChanged
        Controller controller = new Controller();
        Vector comboBoxItemsClass = new Vector();
        String day = (String) jComboBoxDay.getSelectedItem();
        String hour = (String) jComboBoxHour.getSelectedItem();
        String subject = (String) jComboBoxSubject.getSelectedItem();
        String quarter = (String) jComboBoxQuarter.getSelectedItem();
        ArrayList<String> classroom = controller.getAvailableClassroom(day,hour,subject,quarter);
        
        comboBoxItemsClass.add("Aula");
        for (String classfree : classroom) {
           comboBoxItemsClass.add(classfree);
        }
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassroom.setModel(modelClass);        
    }//GEN-LAST:event_jComboBoxHourItemStateChanged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
       Controller controller = new Controller(); 
       String titulation = (String) jComboBoxTit.getSelectedItem();
       String course = (String) jComboBoxCourse.getSelectedItem();
       String quarter = (String) jComboBoxQuarter.getSelectedItem();
       String subject = (String) jComboBoxSubject.getSelectedItem();
       String day = (String) jComboBoxDay.getSelectedItem();
       String hour = (String) jComboBoxHour.getSelectedItem();
       String classroom = (String) jComboBoxClassroom.getSelectedItem(); 
       String year = (String) jComboBoxYear.getSelectedItem();
       controller.setSchedule(titulation,course,quarter,subject,day,hour,classroom,year);
       
       String titulo[] ={"Titulación","Curso","Cuatrimestre","Asignatura","Día","Hora","Aula"};
       String fila[] ={titulation,course,quarter,subject,day,hour,classroom};
       
       tableJSchedule.addRow(fila);
       jTablesSetSchedule.setModel(tableJSchedule);
       
       
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButtonSendRequestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSendRequestMouseClicked
        // Enviar solicitud
        Controller controllerRequest = new Controller();
        boolean ok;
        ok = controllerRequest.setRequest(user.getDni(),jTextFieldReqSubj.getText(), jTextAreaResq.getText());
        if (ok){
            jLabelReqSend.setText("Solicitud enviada correctamente"); 
        }else{
            jLabelReqSend.setText("Error al enviar solicitud");
        }
    }//GEN-LAST:event_jButtonSendRequestMouseClicked

    private void jTableRequests1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRequests1MousePressed
        Controller controllerRequest = new Controller();
        int row= Integer.parseInt((String)jTableRequests1.getValueAt(jTableRequests1.getSelectedRow(),0));
        String message = controllerRequest.getSelectedRequest(row);
        jTextPaneMessageRequest1.setText(message);
    }//GEN-LAST:event_jTableRequests1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        int fila = jTableRequests1.getSelectedRow();
        if(fila>=0){
            Controller controllerAdminRequest = new Controller();
            controllerAdminRequest.SetAdminRequest(fila,"aceptada");
            
            JLabelRequestMod.setText("La solicitud ha sido aceptada");
            
            jTabbedPaneMousePressed(evt);
            
        }
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        int fila = jTableRequests1.getSelectedRow();
        if(fila>=0){
            Controller controllerAdminRequest = new Controller();
            controllerAdminRequest.SetAdminRequest(fila,"denegada");
            
            JLabelRequestMod.setText("La solicitud ha sido denegada");
            
            jTabbedPaneMousePressed(evt);
            
        }
    }//GEN-LAST:event_jButton3MousePressed

    private void jComboBoxYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxYearItemStateChanged
       Vector comboBoxItemsYear = new Vector();
        Vector comboBoxItemsCourse = new Vector();
        Vector comboBoxItemsSubject = new Vector();
        Vector comboBoxItemsQuarter = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        Vector comboBoxItemsTit = new Vector();

        Controller controller = new Controller();
        ArrayList<Titulation> titList = controller.getAllTitulations();

        comboBoxItemsTit.add("Titulación");
        for (Titulation tit : titList) {
            comboBoxItemsTit.add(tit.getName());
        }
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsTit);
        jComboBoxTit.setModel(model);
        comboBoxItemsSubject.add("Asignaturas");
        final DefaultComboBoxModel modelSubject = new DefaultComboBoxModel(comboBoxItemsSubject);
        jComboBoxSubject.setModel(modelSubject);
        comboBoxItemsQuarter.add("Cuatrimestre");
        final DefaultComboBoxModel modelQuarter = new DefaultComboBoxModel(comboBoxItemsQuarter);
        jComboBoxQuarter.setModel(modelQuarter);
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDay.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHour.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassroom.setModel(modelClass);  
        comboBoxItemsCourse.add("Curso");
        final DefaultComboBoxModel modelCourse = new DefaultComboBoxModel(comboBoxItemsCourse);
        jComboBoxCourse.setModel(modelCourse);  
    }//GEN-LAST:event_jComboBoxYearItemStateChanged

    private void jComboBoxYearModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxYearModItemStateChanged
       Vector comboBoxItemsYear = new Vector();
        Vector comboBoxItemsCourse = new Vector();
        Vector comboBoxItemsSubject = new Vector();
        Vector comboBoxItemsQuarter = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        Vector comboBoxItemsTit = new Vector();

        Controller controller = new Controller();
        ArrayList<Titulation> titList = controller.getAllTitulations();

        comboBoxItemsTit.add("Titulación");
        for (Titulation tit : titList) {
            comboBoxItemsTit.add(tit.getName());
        }
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsTit);
        jComboBoxTitMod.setModel(model);
        comboBoxItemsSubject.add("Asignaturas");
        final DefaultComboBoxModel modelSubject = new DefaultComboBoxModel(comboBoxItemsSubject);
        jComboBoxSubMod.setModel(modelSubject);
        comboBoxItemsQuarter.add("Cuatrimestre");
        final DefaultComboBoxModel modelQuarter = new DefaultComboBoxModel(comboBoxItemsQuarter);
        jComboBoxQuarterMod.setModel(modelQuarter);
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDayMod.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);  
        comboBoxItemsCourse.add("Curso");
        final DefaultComboBoxModel modelCourse = new DefaultComboBoxModel(comboBoxItemsCourse);
        jComboBoxCourseMod.setModel(modelCourse); 
    }//GEN-LAST:event_jComboBoxYearModItemStateChanged

    private void jComboBoxTitModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTitModItemStateChanged
        Vector comboBoxItemsCourse = new Vector();
        Vector comboBoxItemsSubject = new Vector();
        Vector comboBoxItemsQuarter = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        
        comboBoxItemsCourse.add("Curso");
        comboBoxItemsCourse.add("1º");
        comboBoxItemsCourse.add("2º");
        comboBoxItemsCourse.add("3º");
        comboBoxItemsCourse.add("4º");
        comboBoxItemsSubject.add("Asignaturas");
        final DefaultComboBoxModel modelSubject = new DefaultComboBoxModel(comboBoxItemsSubject);
        jComboBoxSubMod.setModel(modelSubject);
        comboBoxItemsQuarter.add("Cuatrimestre");
        final DefaultComboBoxModel modelQuarter = new DefaultComboBoxModel(comboBoxItemsQuarter);
        jComboBoxQuarterMod.setModel(modelQuarter);
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDayMod.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);
        
        final DefaultComboBoxModel modelCourse = new DefaultComboBoxModel(comboBoxItemsCourse);
        jComboBoxCourseMod.setModel(modelCourse);  
    }//GEN-LAST:event_jComboBoxTitModItemStateChanged

    private void jComboBoxCourseModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxCourseModItemStateChanged
        Vector comboBoxItemsSubject = new Vector();
        Vector comboBoxItemsQuarter = new Vector();
        Vector comboBoxItemsCourse = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
         
        comboBoxItemsQuarter.add("Cuatrimestre");
        comboBoxItemsQuarter.add("Primero");
        comboBoxItemsQuarter.add("Segundo");
        comboBoxItemsSubject.add("Asignaturas");
        final DefaultComboBoxModel modelSubject = new DefaultComboBoxModel(comboBoxItemsSubject);
        jComboBoxSubMod.setModel(modelSubject);
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDayMod.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);
                  
        final DefaultComboBoxModel modelQuarter = new DefaultComboBoxModel(comboBoxItemsQuarter);
        jComboBoxQuarterMod.setModel(modelQuarter);
    }//GEN-LAST:event_jComboBoxCourseModItemStateChanged

    private void jComboBoxQuarterModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxQuarterModItemStateChanged
        Controller controller = new Controller();
        String titulation = (String) jComboBoxTitMod.getSelectedItem();
        String course = (String) jComboBoxCourseMod.getSelectedItem();
        String quarter = (String) jComboBoxQuarterMod.getSelectedItem();
        ArrayList<Subject> subList = controller.getSubjectsTitulation(titulation, course, quarter);
        
        Vector comboBoxItemsSub = new Vector();
        Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        
        comboBoxItemsSub.add("Asignatura");
        for (Subject subject : subList) {
            comboBoxItemsSub.add(subject.getName());
        }
        comboBoxItemsDay.add("Día");
        final DefaultComboBoxModel modelDay = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDayMod.setModel(modelDay);
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);
        final DefaultComboBoxModel modelSub = new DefaultComboBoxModel(comboBoxItemsSub);
        jComboBoxSubMod.setModel(modelSub);
    }//GEN-LAST:event_jComboBoxQuarterModItemStateChanged

    private void jComboBoxSubModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxSubModItemStateChanged
                Vector comboBoxItemsDay = new Vector();
        Vector comboBoxItems = new Vector();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        String subject = (String) jComboBoxSubMod.getSelectedItem();
        Controller controller = new Controller();
        ArrayList<String> days = controller.getOccupiedDays(subject); 
        
        comboBoxItemsDay.add("Día");
        for (String day : days) {
            comboBoxItemsDay.add(day);
        }
        comboBoxItemsHour.add("Hora");
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);  
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsDay);
        jComboBoxDayMod.setModel(model);
    }//GEN-LAST:event_jComboBoxSubModItemStateChanged

    private void jComboBoxDayModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDayModItemStateChanged
        Controller controller = new Controller();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        String day = (String) jComboBoxDayMod.getSelectedItem();
        String subject = (String) jComboBoxSubMod.getSelectedItem();
        ArrayList<String> hours = controller.getOccupiedHours(subject,day);
        
        comboBoxItemsHour.add("Hora");
        for (String hour : hours) {
            comboBoxItemsHour.add(hour);
        }
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(modelHour);
        comboBoxItemsClass.add("Aula");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);   
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourMod.setModel(model);
    }//GEN-LAST:event_jComboBoxDayModItemStateChanged

    private void jComboBoxHourModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxHourModItemStateChanged
        Controller controller = new Controller();
        Vector comboBoxItemsClass = new Vector();
        String day = (String) jComboBoxDayMod.getSelectedItem();
        String hour = (String) jComboBoxHourMod.getSelectedItem();
        String subject = (String) jComboBoxSubMod.getSelectedItem();
        ArrayList<String> classroom = controller.getOcuppiedClassroom(day,hour,subject);
        
        comboBoxItemsClass.add("Aula");
        for (String classfree : classroom) {
           comboBoxItemsClass.add(classfree);
        }
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassMod.setModel(modelClass);
    }//GEN-LAST:event_jComboBoxHourModItemStateChanged

    private void jButtonDownloadPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDownloadPDFActionPerformed
        try {
            printSchedule();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JF_ViewSchedule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(JF_ViewSchedule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonDownloadPDFActionPerformed

    private void jTreeLeftPanelValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeLeftPanelValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeLeftPanel.getLastSelectedPathComponent();
        Controller controller = new Controller();
        
        if(node == null)
            return;
        if(node.getLevel() == 3){
            String quarter = (String)node.getUserObject();
            
            node = node.getPreviousNode();
            String course = (String)node.getUserObject();
            
            node = node.getPreviousNode();
            String titulation = (String)node.getUserObject();
            
            controller.loadSchedule(jTableSchedule, quarter, course, titulation);
        }        
    }//GEN-LAST:event_jTreeLeftPanelValueChanged

    private void jButtonSelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelMousePressed
        if (jComboBoxClassMod.getSelectedIndex() > 0) {
            Controller controller = new Controller();
            String titulation = (String) jComboBoxTitMod.getSelectedItem();
            String course = (String) jComboBoxCourseMod.getSelectedItem();
            String quarter = (String) jComboBoxQuarterMod.getSelectedItem();
            String subject = (String) jComboBoxSubMod.getSelectedItem();
            String day = (String) jComboBoxDayMod.getSelectedItem();
            String hour = (String) jComboBoxHourMod.getSelectedItem();
            String classroom = (String) jComboBoxClassMod.getSelectedItem();
            String year = (String) jComboBoxYearMod.getSelectedItem();
            controller.setSchedule(titulation, course, quarter, subject, day, hour, classroom, year);

            String titulo[] = {"Titulación", "Curso", "Cuatrimestre", "Asignatura", "Día", "Hora", "Aula"};
            String fila[] = {titulation, course, quarter, subject, day, hour, classroom};
            DefaultTableModel tableJMod = new DefaultTableModel(null,titulo);
            tableJMod.addRow(fila);
            jTableMod.setModel(tableJMod);
            
            Vector comboBoxItemsDay = new Vector();
            Vector comboBoxItems = new Vector();
            Vector comboBoxItemsHour = new Vector();
            Vector comboBoxItemsClass = new Vector();
            
            Controller controller_days = new Controller();
            ArrayList<String> days = controller_days.getAvailableDays(quarter);

            comboBoxItemsDay.add("Día Nuevo");
            for (String d : days) {
                comboBoxItemsDay.add(d);
            }
            comboBoxItemsHour.add("Hora Nueva");
            final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
            jComboBoxHourSel.setModel(modelHour);
            comboBoxItemsClass.add("Aula Nueva");
            final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
            jComboBoxClassSel.setModel(modelClass);
            final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsDay);
            jComboBoxDaySel.setModel(model);

        }
    }//GEN-LAST:event_jButtonSelMousePressed

    private void jComboBoxDaySelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxDaySelItemStateChanged
        Controller controller = new Controller();
        Vector comboBoxItemsHour = new Vector();
        Vector comboBoxItemsClass = new Vector();
        String day = (String) jComboBoxDaySel.getSelectedItem();
        String quarter = (String) jComboBoxQuarterMod.getSelectedItem();
        ArrayList<String> hours = controller.getAvailableHours(day,quarter);
        
        comboBoxItemsHour.add("Hora Nueva");
        for (String hour : hours) {
            comboBoxItemsHour.add(hour);
        }
        final DefaultComboBoxModel modelHour = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourSel.setModel(modelHour);
        comboBoxItemsClass.add("Aula Nueva");
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassSel.setModel(modelClass);   
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItemsHour);
        jComboBoxHourSel.setModel(model);
    }//GEN-LAST:event_jComboBoxDaySelItemStateChanged

    private void jComboBoxHourSelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxHourSelItemStateChanged
        Controller controller = new Controller();
        Vector comboBoxItemsClass = new Vector();
        String day = (String) jComboBoxDaySel.getSelectedItem();
        String hour = (String) jComboBoxHourSel.getSelectedItem();
        String subject = (String) jComboBoxSubMod.getSelectedItem();
        String quarter = (String) jComboBoxQuarterMod.getSelectedItem();
        ArrayList<String> classroom = controller.getAvailableClassroom(day,hour,subject,quarter);
        
        comboBoxItemsClass.add("Aula Nueva");
        for (String classfree : classroom) {
           comboBoxItemsClass.add(classfree);
        }
        final DefaultComboBoxModel modelClass = new DefaultComboBoxModel(comboBoxItemsClass);
        jComboBoxClassSel.setModel(modelClass);   
    }//GEN-LAST:event_jComboBoxHourSelItemStateChanged

    private void jButtonModMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonModMousePressed
        Controller controller = new Controller();
        String day_new = (String) jComboBoxDaySel.getSelectedItem();
        String hour_new = (String) jComboBoxHourSel.getSelectedItem();
        String subject = (String) jComboBoxSubMod.getSelectedItem();
        String quarter = (String) jComboBoxQuarterMod.getSelectedItem();
        String classroom_new = (String) jComboBoxClassSel.getSelectedItem();
        String day_old = (String) jComboBoxDayMod.getSelectedItem();
        String hour_old = (String) jComboBoxHourMod.getSelectedItem();
        String classroom_old = (String) jComboBoxClassMod.getSelectedItem();
        int update = controller.updateSchedule(day_old,hour_old,classroom_old,day_new,hour_new,classroom_new,quarter);
        if(update > 0){
            jLabelModified.setText("La modificación se ha realizado de forma correcta");
        }
        else{
            jLabelModified.setText("La modificación no se ha podido realizar");
        }
        
    }//GEN-LAST:event_jButtonModMousePressed

    private void jComboBoxSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSubjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSubjectActionPerformed

    private void jComboBoxDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDayActionPerformed

    private void jComboBoxClassroomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClassroomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxClassroomActionPerformed
    
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
            java.util.logging.Logger.getLogger(JF_ViewSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_ViewSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_ViewSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_ViewSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JF_ViewSchedule().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelRequestMod;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonDownloadPDF;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonMod;
    private javax.swing.JButton jButtonSel;
    private javax.swing.JButton jButtonSendRequest;
    private javax.swing.JComboBox jComboBoxClassMod;
    private javax.swing.JComboBox jComboBoxClassSel;
    private javax.swing.JComboBox jComboBoxClassroom;
    private javax.swing.JComboBox jComboBoxCourse;
    private javax.swing.JComboBox jComboBoxCourseMod;
    private javax.swing.JComboBox jComboBoxDay;
    private javax.swing.JComboBox jComboBoxDayMod;
    private javax.swing.JComboBox jComboBoxDaySel;
    private javax.swing.JComboBox jComboBoxHour;
    private javax.swing.JComboBox jComboBoxHourMod;
    private javax.swing.JComboBox jComboBoxHourSel;
    private javax.swing.JComboBox jComboBoxQuarter;
    private javax.swing.JComboBox jComboBoxQuarterMod;
    private javax.swing.JComboBox jComboBoxSubMod;
    private javax.swing.JComboBox jComboBoxSubject;
    private javax.swing.JComboBox jComboBoxTit;
    private javax.swing.JComboBox jComboBoxTitMod;
    private javax.swing.JComboBox jComboBoxYear;
    private javax.swing.JComboBox jComboBoxYearMod;
    private javax.swing.JLabel jLabelLogoImg;
    private javax.swing.JLabel jLabelModified;
    private javax.swing.JLabel jLabelReqSend;
    private javax.swing.JLabel jLabelReqText;
    private javax.swing.JLabel jLabelRequest;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JLabel jLabelWelcomeTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAdminRequest;
    private javax.swing.JPanel jPanelDownloadButton;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelMakeSchedule;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelMod;
    private javax.swing.JPanel jPanelRTab;
    private javax.swing.JPanel jPanelRTop;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelSendRequest;
    private javax.swing.JPanel jPanelUpdateSchedule;
    private javax.swing.JPanel jPanelViewRequest;
    private javax.swing.JPanel jPanelViewSchedule;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPaneLeftPanel;
    private javax.swing.JScrollPane jScrollPaneMod;
    private javax.swing.JScrollPane jScrollPaneViewSchedule;
    private javax.swing.JScrollPane jScrollPanelViewRequest;
    private javax.swing.JScrollPane jScrollPanelViewRequest1;
    private javax.swing.JSeparator jSeparatorLeftPanel;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableMod;
    private javax.swing.JTable jTableRequests;
    private javax.swing.JTable jTableRequests1;
    private javax.swing.JTable jTableSchedule;
    private javax.swing.JTable jTablesSetSchedule;
    private javax.swing.JTextArea jTextAreaResq;
    private javax.swing.JTextField jTextFieldReqSubj;
    private javax.swing.JTextPane jTextPaneMessageRequest;
    private javax.swing.JTextPane jTextPaneMessageRequest1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JTree jTreeLeftPanel;
    // End of variables declaration//GEN-END:variables
}
