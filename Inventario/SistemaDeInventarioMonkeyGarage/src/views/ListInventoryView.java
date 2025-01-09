package views;

import java.awt.Color;
import javax.swing.JFrame;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;
import utils.RoundedButton;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Jaqui Sanchez
 */
public class ListInventoryView extends javax.swing.JFrame {

    /**
     * Creates new form FormUpdateInventoryViewMain
     */
    public ListInventoryView() {
        initComponents();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainContainer = new javax.swing.JPanel();
        chatContent = new javax.swing.JPanel();
        actionsContainer = new javax.swing.JPanel();
        updateInventoryActiontTools = new javax.swing.JButton();
        listContainer = new javax.swing.JPanel();
        listContainer1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        mainContainer.setPreferredSize(new java.awt.Dimension(780, 800));
        mainContainer.setRequestFocusEnabled(false);

        chatContent.setBackground(new java.awt.Color(255, 255, 255));
        chatContent.setPreferredSize(new java.awt.Dimension(780, 800));

        actionsContainer.setBackground(new java.awt.Color(255, 255, 255));
        actionsContainer.setPreferredSize(new java.awt.Dimension(780, 100));

        updateInventoryActiontTools.setText("AGREGAR");

        javax.swing.GroupLayout actionsContainerLayout = new javax.swing.GroupLayout(actionsContainer);
        actionsContainer.setLayout(actionsContainerLayout);
        actionsContainerLayout.setHorizontalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsContainerLayout.createSequentialGroup()
                .addContainerGap(674, Short.MAX_VALUE)
                .addComponent(updateInventoryActiontTools)
                .addGap(24, 24, 24))
        );
        actionsContainerLayout.setVerticalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsContainerLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(updateInventoryActiontTools, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        chatContent.add(actionsContainer);

        listContainer.setBackground(new java.awt.Color(255, 255, 255));
        listContainer.setPreferredSize(new java.awt.Dimension(864, 690));

        listContainer1.setBackground(new java.awt.Color(255, 255, 255));
        listContainer1.setPreferredSize(new java.awt.Dimension(864, 690));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        list.setAutoCreateRowSorter(true);
        list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Apellidos", "Correo electronico", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        list.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(list);

        javax.swing.GroupLayout listContainer1Layout = new javax.swing.GroupLayout(listContainer1);
        listContainer1.setLayout(listContainer1Layout);
        listContainer1Layout.setHorizontalGroup(
            listContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainer1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        listContainer1Layout.setVerticalGroup(
            listContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainer1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout listContainerLayout = new javax.swing.GroupLayout(listContainer);
        listContainer.setLayout(listContainerLayout);
        listContainerLayout.setHorizontalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 864, Short.MAX_VALUE)
            .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listContainerLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(listContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        listContainerLayout.setVerticalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
            .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listContainerLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(listContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        chatContent.add(listContainer);

        javax.swing.GroupLayout mainContainerLayout = new javax.swing.GroupLayout(mainContainer);
        mainContainer.setLayout(mainContainerLayout);
        mainContainerLayout.setHorizontalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContainerLayout.createSequentialGroup()
                .addComponent(chatContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainContainerLayout.setVerticalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chatContent, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsContainer;
    public javax.swing.JPanel chatContent;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable list;
    private javax.swing.JPanel listContainer;
    private javax.swing.JPanel listContainer1;
    public javax.swing.JPanel mainContainer;
    public javax.swing.JButton updateInventoryActiontTools;
    // End of variables declaration//GEN-END:variables
}
