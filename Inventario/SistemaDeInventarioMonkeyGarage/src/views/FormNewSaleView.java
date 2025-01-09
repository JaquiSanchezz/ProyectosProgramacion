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
public class FormNewSaleView extends javax.swing.JFrame {

    /**
     * Creates new form FormUpdateInventoryViewMain
     */
    public FormNewSaleView() {
        initComponents();
         MaterialUIMovement.add(actionForm, MaterialColors.BLUE_400);
        actionForm.setForeground(Color.white);
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
        jLabel3 = new javax.swing.JLabel();
        quantityField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        productField = new javax.swing.JComboBox<>();
        listContainer = new javax.swing.JPanel();
        actionForm = new RoundedButton("GUARDAR", MaterialColors.BLUE_400);
        jLabel5 = new javax.swing.JLabel();
        typePaymentField = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        clientField = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listProducts = new javax.swing.JTable();
        actionAddProduct = new javax.swing.JButton();
        actionRemoveProduct = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        mainContainer.setPreferredSize(new java.awt.Dimension(780, 800));
        mainContainer.setRequestFocusEnabled(false);

        chatContent.setBackground(new java.awt.Color(255, 255, 255));
        chatContent.setPreferredSize(new java.awt.Dimension(780, 800));

        actionsContainer.setBackground(new java.awt.Color(255, 255, 255));
        actionsContainer.setPreferredSize(new java.awt.Dimension(780, 100));

        jLabel3.setText("CANTIDAD");

        quantityField.setSelectedTextColor(new java.awt.Color(38, 117, 191));

        jLabel4.setText("PRODUCTO");

        productField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout actionsContainerLayout = new javax.swing.GroupLayout(actionsContainer);
        actionsContainer.setLayout(actionsContainerLayout);
        actionsContainerLayout.setHorizontalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsContainerLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(productField, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        actionsContainerLayout.setVerticalGroup(
            actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsContainerLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(actionsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(quantityField)
                    .addComponent(productField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        chatContent.add(actionsContainer);

        listContainer.setBackground(new java.awt.Color(255, 255, 255));
        listContainer.setPreferredSize(new java.awt.Dimension(864, 690));

        jLabel5.setText("TIPO DE PAGO");

        typePaymentField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta" }));

        jLabel6.setText("CLIENTE");

        clientField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        listProducts.setAutoCreateRowSorter(true);
        listProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Producto", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listProducts.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(listProducts);

        actionAddProduct.setText("AGREGAR PRODUCTO");

        actionRemoveProduct.setText("REMOVER PRODUCTO");

        javax.swing.GroupLayout listContainerLayout = new javax.swing.GroupLayout(listContainer);
        listContainer.setLayout(listContainerLayout);
        listContainerLayout.setHorizontalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listContainerLayout.createSequentialGroup()
                .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(listContainerLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listContainerLayout.createSequentialGroup()
                                .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(typePaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(clientField, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(listContainerLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(listContainerLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(actionRemoveProduct)))
                .addGap(221, 221, 221))
            .addGroup(listContainerLayout.createSequentialGroup()
                .addGap(321, 321, 321)
                .addComponent(actionForm, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionAddProduct)
                .addGap(229, 229, 229))
        );
        listContainerLayout.setVerticalGroup(
            listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(actionAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(actionRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(listContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listContainerLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(listContainerLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(typePaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addComponent(actionForm, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
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
            .addComponent(chatContent, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton actionAddProduct;
    public javax.swing.JButton actionForm;
    public javax.swing.JButton actionRemoveProduct;
    private javax.swing.JPanel actionsContainer;
    public javax.swing.JPanel chatContent;
    public javax.swing.JComboBox<String> clientField;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel listContainer;
    public javax.swing.JTable listProducts;
    public javax.swing.JPanel mainContainer;
    public javax.swing.JComboBox<String> productField;
    public javax.swing.JTextField quantityField;
    public javax.swing.JComboBox<String> typePaymentField;
    // End of variables declaration//GEN-END:variables
}