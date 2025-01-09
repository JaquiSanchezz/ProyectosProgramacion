package controllers;

import models.Product;
import models.Tool;
import models.Transaction;
import utils.Toaster;
import views.FormUpdateInventoryViewMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryUpdateController {

    private FormUpdateInventoryViewMain formUpdateInventoryViewMain;
    private Toaster toaster;
    private String model;
    private Transaction transaction;
    private Product product;
    private ArrayList<Product> listProduct;
    private Tool tool;
    private ArrayList<Tool> listTools;

    public InventoryUpdateController(FormUpdateInventoryViewMain formUpdateInventoryViewMain, String model) {
        this.formUpdateInventoryViewMain = formUpdateInventoryViewMain;
        this.model = model;
        product = new Product();
        tool = new Tool();
        transaction = new Transaction();

        this.formUpdateInventoryViewMain.setVisible(true);
        toaster = new Toaster(formUpdateInventoryViewMain.mainContainer);
        populateOptions();
        events();
    }

    private void populateOptions() {
        DefaultComboBoxModel dml= new DefaultComboBoxModel();

        if (model.equals("tool")){
            listTools = tool.getAllTools();
            for (int i = 0; i < listTools.size(); i++) {
                dml.addElement(listTools.get(i).getName());
            }

        }else{
            listProduct = product.getAllProducts();
            for (int i = 0; i < listProduct.size(); i++) {
                dml.addElement(listProduct.get(i).getName());
            }
        }
        formUpdateInventoryViewMain.listItems.setModel(dml);

    }

    private void events() {
        formUpdateInventoryViewMain.actionForm.addActionListener(e -> {
            if (model.equals("tool")){
                updateInventoryTool();
            }else{
                updateInventoryProduct();
            }
        });
    }

    private void updateInventoryTool(){
        Tool current = listTools.get(formUpdateInventoryViewMain.listItems.getSelectedIndex());
        String newValueTxt = formUpdateInventoryViewMain.quantityField.getText();
        if (!newValueTxt.isEmpty()){
            int currentValue = current.getQuantity();
            int newValue = currentValue + Integer.parseInt(newValueTxt);
            current.setQuantity(newValue);
            if (tool.updateTool(current)){
                transaction.createTransaction(new Transaction(current.getId(), "entry", "tools", Integer.parseInt(newValueTxt)));
                toaster.success("¡Genial!", "¡Has actualizado correctamente el inventario!");
            }else{
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar el inventario");
            }
        }else{
            toaster.info("¡Ups!.", "Debes llenar los campos");
        }



    }
    private void updateInventoryProduct(){
        Product current = listProduct.get(formUpdateInventoryViewMain.listItems.getSelectedIndex());
        String newValueTxt = formUpdateInventoryViewMain.quantityField.getText();
        if (!newValueTxt.isEmpty()){
            int currentValue = current.getQuantity();
            int newValue = currentValue + Integer.parseInt(newValueTxt);
            current.setQuantity(newValue);

            if (product.updateProduct(current)){
                transaction.createTransaction(new Transaction(current.getId(), "entry", "products", Integer.parseInt(newValueTxt)));
                toaster.success("¡Genial!", "¡Has actualizado correctamente el inventario!");
            }else{
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar el inventario");
            }

        }else{
            toaster.info("¡Ups!.", "Debes llenar los campos");
        }

    }

}
