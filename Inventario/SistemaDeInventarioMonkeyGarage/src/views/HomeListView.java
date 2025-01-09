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


public class HomeListView extends javax.swing.JPanel {

    /**
     * Creates new form ChatView
     */
    public HomeListView() {
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
        newSaleAction = new javax.swing.JButton();
        newServiceAction = new javax.swing.JButton();
        updateInventoryActiontTools = new javax.swing.JButton();
        updateInventoryActionProducts = new javax.swing.JButton();
        listContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLastSales = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listExpirations = new javax.swing.JTable();

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

        newSaleAction.setText("NUEVA VENTA");

        newServiceAction.setText("NUEVO SERVICIO");

        updateInventoryActiontTools.setText("INVENTARIO H.");

        updateInventoryActionProducts.setText("INVENTARIO PRODUCTOS");

        javax.swing.GroupLayout actionsContainerLayout = new javax.swing.GroupLayout(actionsContainer);
        actionsContainer.setLayout(actionsContainerLayout);
        actionsContainerLayout.setHorizontalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsContainerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(newSaleAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newServiceAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateInventoryActionProducts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateInventoryActiontTools)
                .addGap(25, 25, 25))
        );
        actionsContainerLayout.setVerticalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsContainerLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(newSaleAction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newServiceAction, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateInventoryActiontTools, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateInventoryActionProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        chatContent.add(actionsContainer);

        listContainer.setBackground(new java.awt.Color(255, 255, 255));
        listContainer.setPreferredSize(new java.awt.Dimension(864, 690));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        listLastSales.setAutoCreateRowSorter(true);
        listLastSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Cliente", "Producto", "Total", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listLastSales.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(listLastSales);
        listLastSales.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (listLastSales.getColumnModel().getColumnCount() > 0) {
            listLastSales.getColumnModel().getColumn(0).setResizable(false);
            listLastSales.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("PROXIMOS VENCIMIENTOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("ÚLTIMAS VENTAS");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        listExpirations.setAutoCreateRowSorter(true);
        listExpirations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Cliente", "Servicio", "Precio", "Fecha de entrega", "Fecha limite", "Nota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(listExpirations);
        listExpirations.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (listExpirations.getColumnModel().getColumnCount() > 0) {
            listExpirations.getColumnModel().getColumn(0).setResizable(false);
            listExpirations.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        javax.swing.GroupLayout listContainerLayout = new javax.swing.GroupLayout(listContainer);
        listContainer.setLayout(listContainerLayout);
        listContainerLayout.setHorizontalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainerLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listContainerLayout.createSequentialGroup()
                    .addContainerGap(69, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(65, 65, 65)))
        );
        listContainerLayout.setVerticalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(284, 284, 284)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
            .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listContainerLayout.createSequentialGroup()
                    .addGap(59, 59, 59)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(401, Short.MAX_VALUE)))
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
    public javax.swing.JPanel chatContent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel listContainer;
    public javax.swing.JTable listExpirations;
    public javax.swing.JTable listLastSales;
    public javax.swing.JPanel mainContainer;
    public javax.swing.JButton newSaleAction;
    public javax.swing.JButton newServiceAction;
    public javax.swing.JButton updateInventoryActionProducts;
    public javax.swing.JButton updateInventoryActiontTools;
    // End of variables declaration//GEN-END:variables
}