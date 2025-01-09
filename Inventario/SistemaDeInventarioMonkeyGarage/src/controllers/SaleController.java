package controllers;

import models.*;
import utils.Toaster;
import views.FormNewSaleView;
import views.ListInventoryView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SaleController {


    private FormNewSaleView formNewSaleView;
    private Toaster toaster;
    private Transaction transaction;
    private Product product;
    private Client client;
    private ArrayList<Product> listProducts;
    private ArrayList<SelectedProduct> listProductsSelected;
    private ArrayList<Client> listClients;
    private TransactionSale transactionSale;
    private TransactionSaleProduct transactionSaleProduct;

    String[] columnNames = {"Id", "Producto", "Precio p/u",  "Cantidad", "Total"};
    private DefaultTableModel model;

    public SaleController(FormNewSaleView formNewSaleView) {
        this.formNewSaleView = formNewSaleView;
        product = new Product();
        client = new Client();
        transaction = new Transaction();
        transactionSale = new TransactionSale();
        transactionSaleProduct = new TransactionSaleProduct();
        listProductsSelected = new ArrayList<>();

        this.formNewSaleView.setVisible(true);
        toaster = new Toaster(formNewSaleView.mainContainer);
        initInfo();
        events();

    }

    private void initInfo() {
        populateList();
        configTable();
    }

    public void populateList() {
        DefaultComboBoxModel dml= new DefaultComboBoxModel();
        listProducts = product.getAllProducts();
        for (Product listProduct : listProducts) {
            dml.addElement(listProduct.getName());
        }
        formNewSaleView.productField.setModel(dml);

        DefaultComboBoxModel dmlClient= new DefaultComboBoxModel();
        listClients = client.getAllClients();

        for (Client listClient : listClients) {
            dmlClient.addElement(listClient.getName()+" "+listClient.getLast_name()+" - "+listClient.getEmail());
        }
        formNewSaleView.clientField.setModel(dmlClient);
    }



    public void configTable() {
        model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
       formNewSaleView.listProducts.setModel(model);


    }
    private void events(){

        formNewSaleView.actionAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToList();

            }
        });

        formNewSaleView.actionRemoveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItemToList();
            }
        });

        formNewSaleView.actionForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                processSale();


            }
        });

    }


    private void addItemToList() {
        String quantityStr = formNewSaleView.quantityField.getText();
        Product currentSelected = listProducts.get(formNewSaleView.productField.getSelectedIndex());

        if (!quantityStr.isEmpty()){

            if (Integer.parseInt(quantityStr) <= currentSelected.getQuantity()){

                boolean exist = false;
                int index = 0;
                for (int i = 0; i < listProductsSelected.size(); i++) {

                    if (listProductsSelected.get(i).getProduct().getId() == currentSelected.getId()){
                        exist = true;
                        index = i;
                    }
                }
                float total = Integer.parseInt(quantityStr) * Float.valueOf(currentSelected.getSelling_price());
               if (exist){
                   listProductsSelected.get(index).setQuantity(Integer.parseInt(quantityStr));
                   listProductsSelected.get(index).setTotal(total);
                   model.removeRow(index);
                   model.addRow(new Object[]{String.valueOf(currentSelected.getId()), currentSelected.getName(), currentSelected.getSelling_price(), quantityStr, String.valueOf(total)});
               }else{
                   listProductsSelected.add(new SelectedProduct(
                           Integer.parseInt(quantityStr),
                           currentSelected,
                           total
                   ));
                   model.addRow(new Object[]{String.valueOf(currentSelected.getId()), currentSelected.getName(),currentSelected.getSelling_price(), quantityStr, String.valueOf(total)});
                   formNewSaleView.listProducts.repaint();
               }


            }else{
                toaster.info("¡Ups!.", "La cantidad no debe superar el inventario");
            }


        }else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
        }
    }

    private void removeItemToList() {

        if (listProductsSelected.size() > 0){
            int currentSelected = formNewSaleView.listProducts.getSelectedRow();;

            if (currentSelected != -1){
                model.removeRow(currentSelected);
                listProductsSelected.remove(currentSelected);
                formNewSaleView.listProducts.repaint();
            }


        }


    }

    private void processSale() {

        if (listProductsSelected.size() > 0){

            int totalProducts = 0;
            float total = 0;

            for (SelectedProduct selectedProduct : listProductsSelected) {
                totalProducts += selectedProduct.getQuantity();
                total += selectedProduct.getTotal();
            }

            int clientId = listClients.get(formNewSaleView.clientField.getSelectedIndex()).getId();
            String paymentType = formNewSaleView.typePaymentField.getSelectedItem().toString();

           if (transactionSale.createTransaction(new TransactionSale(
                   clientId,
                   totalProducts,
                   String.valueOf(total),
                   "Pagado",
                   paymentType
           ))){

               TransactionSale currentSale = transactionSale.getLastSale();

               for (SelectedProduct selectedProduct : listProductsSelected) {
                   transactionSaleProduct.createTransactionSalesProducts(new TransactionSaleProduct(
                           currentSale.getId(),
                           selectedProduct.getProduct().getId(),
                           selectedProduct.getProduct().getSelling_price(),
                           selectedProduct.getQuantity()
                   ));
                   Product currentProduct = selectedProduct.getProduct();
                   currentProduct.setQuantity(currentProduct.getQuantity() - selectedProduct.getQuantity());
                   product.updateProduct(currentProduct);
                   transaction.createTransaction(new Transaction(
                           currentProduct.getId(),
                           "outgoing",
                           "products",
                           currentProduct.getQuantity()
                   ));


               }

               listProductsSelected = new ArrayList<>();
               formNewSaleView.quantityField.setText("");
               for (int i = 0; i < model.getRowCount(); i++) {
                   model.removeRow(i);
               }
               formNewSaleView.listProducts.repaint();

               toaster.success("¡Genial!", "¡Has realizado una nueva venta!");
           }else{

               toaster.info("¡Ups!.", "Ocurrio un error al intentar guardar la información");

           }





        }else{
            toaster.info("¡Ups!.", "Debes seleccionar al menos un producto");
        }


    }
}
