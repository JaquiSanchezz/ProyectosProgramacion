/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialBorders;
import mdlaf.utils.MaterialColors;
import utils.RoundedButton;
import utils.RoundedTextField;


public class UsersView extends javax.swing.JPanel {

    /**
     * Creates new form ChatView
     */
    public UsersView() {
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

        mainContainer = new javax.swing.JPanel();
        chatContent = new javax.swing.JPanel();
        actionsContainer = new javax.swing.JPanel();
        addAction = new javax.swing.JButton();
        editAction = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        searchAction = new javax.swing.JButton();
        listContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(864, 800));
        setPreferredSize(new java.awt.Dimension(780, 800));

        mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        mainContainer.setPreferredSize(new java.awt.Dimension(780, 800));
        mainContainer.setRequestFocusEnabled(false);

        chatContent.setBackground(new java.awt.Color(255, 255, 255));
        chatContent.setPreferredSize(new java.awt.Dimension(780, 800));

        actionsContainer.setBackground(new java.awt.Color(255, 255, 255));
        actionsContainer.setPreferredSize(new java.awt.Dimension(780, 100));

        addAction.setText("AGREGAR");

        editAction.setText("EDITAR");

        searchField.setPreferredSize(new java.awt.Dimension(64, 23));
        searchField.setSelectedTextColor(new java.awt.Color(38, 117, 191));

        searchAction.setText("BUSCAR");
        searchAction.setPreferredSize(new java.awt.Dimension(82, 23));

        javax.swing.GroupLayout actionsContainerLayout = new javax.swing.GroupLayout(actionsContainer);
        actionsContainer.setLayout(actionsContainerLayout);
        actionsContainerLayout.setHorizontalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsContainerLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchAction, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(addAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editAction, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        actionsContainerLayout.setVerticalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsContainerLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchAction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addAction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editAction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        chatContent.add(actionsContainer);

        listContainer.setBackground(new java.awt.Color(255, 255, 255));
        listContainer.setPreferredSize(new java.awt.Dimension(864, 690));

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
        list.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (list.getColumnModel().getColumnCount() > 0) {
            list.getColumnModel().getColumn(0).setResizable(false);
            list.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        javax.swing.GroupLayout listContainerLayout = new javax.swing.GroupLayout(listContainer);
        listContainer.setLayout(listContainerLayout);
        listContainerLayout.setHorizontalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainerLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        listContainerLayout.setVerticalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainerLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
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
            .addComponent(chatContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsContainer;
    public javax.swing.JButton addAction;
    public javax.swing.JPanel chatContent;
    public javax.swing.JButton editAction;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable list;
    private javax.swing.JPanel listContainer;
    public javax.swing.JPanel mainContainer;
    public javax.swing.JButton searchAction;
    public javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}