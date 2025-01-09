/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.*;
import utils.Toaster;
import views.FormProductView;
import views.FormToolsView;
import views.HomeView;
import views.ListView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Jaqui Sanchez
 */
public class ProductController {

    private final HomeView homeView;
    private ListView mainView;
    private User user;
    private Brand brand;
    private FormProductView formView;
    String[] columnNames = {"Id","Marca", "Nombre", "Descripción", "Costo", "Precio de venta", "Cantidad", "Tipo de unidad"};
    private Product product;
    private ArrayList<Product> listProducts;
    private ArrayList<Brand> brands;
    private int currentSelected = -1;
    private Toaster toaster;

    public ProductController(HomeView homeView, ListView mainView, User user) {
        this.homeView = homeView;
        this.mainView = mainView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.mainView.setVisible(true);
        this.formView = new FormProductView();
        product = new Product();
        brand = new Brand();
        toaster = new Toaster(homeView.containerMain);
        populateList();
    }

    public void events() {

        mainView.addAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = -1;
                clearContainer();
                setListCategory();
                homeView.containerMain.add(formView);
                homeView.containerMain.revalidate();

            }
        });

        mainView.editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = mainView.list.getSelectedRow();

                if (listProducts.size() > 0 && currentSelected != -1) {
                    populateForm();
                    clearContainer();
                    formView.actionDeleteClient.setVisible(true);

                    homeView.containerMain.add(formView);
                    homeView.containerMain.revalidate();

                }

            }
        });


        mainView.searchAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = -1;
                populateListFromSearch();
            }
        });


        //Form Events
        formView.backAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToList();
            }
        });

        formView.actionDeleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        formView.actionForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentSelected != -1) {
                    updateClient();
                } else {
                    createClient();
                }

            }
        });

    }

    private void setListCategory(){
        formView.actionDeleteClient.setVisible(false);

        brands = brand.getAllBrands();
        DefaultComboBoxModel dml= new DefaultComboBoxModel();
        for (int i = 0; i < brands.size(); i++) {
            dml.addElement(brands.get(i).getName());
        }

        formView.brandField.setModel(dml);
    }

    public void goToList() {
        currentSelected = -1;
        clearContainer();
        homeView.containerMain.add(mainView);
        homeView.containerMain.revalidate();
        formView.nameField.setText("");
        formView.descField.setText("");
        formView.costField.setText("");
        formView.sellingPriceField.setText("");
        formView.quantityField.setText("");
        formView.brandField.setSelectedIndex(0);
        formView.unitField.setSelectedIndex(0);
        populateList();
    }

    public void createClient() {

        String name = formView.nameField.getText();
        String desc = formView.descField.getText();
        String quantity = formView.quantityField.getText();
        String cost = formView.costField.getText();
        String sellingPrice = formView.sellingPriceField.getText();
        int brandId = brands.get(formView.brandField.getSelectedIndex()).getId();
        String unit = formView.unitField.getSelectedItem().toString();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty() && !quantity.isEmpty() && !cost.isEmpty() && !sellingPrice.isEmpty()) {


            if (new Product().createProduct(new Product(brandId, name, desc,cost,sellingPrice,  Integer.parseInt(quantity), unit))) {
                toaster.success("¡Genial!", "¡Has registrado correctamente el producto!");

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            this.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        goToList();

                    }
                }.start();

            } else {
                toaster.info("¡Ups!.", "Ocurrio un error al intentar registar el producto");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void updateClient() {

        String name = formView.nameField.getText();
        String desc = formView.descField.getText();
        String quantity = formView.quantityField.getText();
        String cost = formView.costField.getText();
        String sellingPrice = formView.sellingPriceField.getText();
        int brandId = brands.get(formView.brandField.getSelectedIndex()).getId();
        String unit = formView.unitField.getSelectedItem().toString();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty() && !quantity.isEmpty()) {
            Product current = listProducts.get(currentSelected);

            if (new Product().updateProduct(new Product(current.getId(), brandId, name, desc, cost, sellingPrice, Integer.parseInt(quantity), unit))) {
                toaster.success("¡Genial!", "¡Has actualizado correctamente el producto!");

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            this.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        goToList();

                    }
                }.start();

            } else {
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar el producto");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        Product current = listProducts.get(currentSelected);

        if (new Product().deleteProduct(current.getId())) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente el producto!");

            new Thread() {
                @Override
                public void run() {
                    try {
                        this.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    goToList();

                }
            }.start();

        } else {
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar el producto");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        Product currentClient = listProducts.get(currentSelected);
        formView.nameField.setText(currentClient.getName());
        formView.descField.setText(currentClient.getDescription());
        formView.quantityField.setText(String.valueOf(currentClient.getQuantity()));
        formView.costField.setText(currentClient.getCost());
        formView.sellingPriceField.setText(currentClient.getSelling_price());
        formView.quantityField.setEnabled(false);
        setListCategory();
        int currentSelected = 0;

        for (int i = 0; i < brands.size(); i++) {
            if (brands.get(i).getId() == currentClient.getBrandId()){
                currentSelected = i;
            }
        }
        formView.brandField.setSelectedIndex(currentSelected);
        formView.unitField.setSelectedItem(currentClient.getUnit());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listProducts = product.getAllProducts();

        for (int i = 0; i < listProducts.size(); i++) {
            Product c = listProducts.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getBrandName(), c.getName(), c.getDescription(), c.getCost(), c.getSelling_price(), c.getQuantity(), c.getUnit()
            });
        }

        mainView.list.setModel(model);


    }

    public void populateListFromSearch() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listProducts = product.searchProducts(mainView.searchField.getText());

        for (int i = 0; i < listProducts.size(); i++) {
            Product c = listProducts.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getBrandName(), c.getName(), c.getDescription(), c.getCost(), c.getSelling_price(), c.getQuantity(), c.getUnit()
            });
        }

        mainView.list.setModel(model);


    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }


}
