/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.CategoryTool;
import models.Service;
import models.Tool;
import models.User;
import utils.Toaster;
import views.FormServiceView;
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
public class ToolController {
    private final HomeView homeView;
    private ListView mainView;
    private User user;
    private CategoryTool categoryTool;
    private FormToolsView formView;
    String[] columnNames = {"Id","Categoria", "Nombre", "Descripción", "Cantidad", "Estado"};
    private Tool tool;
    private ArrayList<Tool> listTools;
    private ArrayList<CategoryTool> categoryTools;
    private int currentSelected = -1;
    private Toaster toaster;

    public ToolController(HomeView homeView, ListView mainView, User user) {
        this.homeView = homeView;
        this.mainView = mainView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.mainView.setVisible(true);
        this.formView = new FormToolsView();
        tool = new Tool();
        categoryTool = new CategoryTool();
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

                if (listTools.size() > 0 && currentSelected != -1) {
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

        categoryTools = categoryTool.getAllCategoryTool();
        DefaultComboBoxModel dml= new DefaultComboBoxModel();
        for (int i = 0; i < categoryTools.size(); i++) {
            dml.addElement(categoryTools.get(i).getName());
        }

        formView.categoryField.setModel(dml);
    }

    public void goToList() {
        currentSelected = -1;
        clearContainer();
        homeView.containerMain.add(mainView);
        homeView.containerMain.revalidate();
        formView.nameField.setText("");
        formView.descField.setText("");
        formView.quantityField.setText("");
        formView.statusField.setSelectedIndex(0);
        populateList();
    }

    public void createClient() {

        String name = formView.nameField.getText();
        String desc = formView.descField.getText();
        String quantity = formView.quantityField.getText();
        int categoryId = categoryTools.get(formView.categoryField.getSelectedIndex()).getId();
        String status = formView.statusField.getSelectedItem().toString();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty() && !quantity.isEmpty()) {

            if (new Tool().createTool(new Tool(categoryId, name, desc, Integer.parseInt(quantity), status))) {
                toaster.success("¡Genial!", "¡Has registrado correctamente la herramienta!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar registar la herramienta");
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
        int categoryId = categoryTools.get(formView.categoryField.getSelectedIndex()).getId();
        String status = formView.statusField.getSelectedItem().toString();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty() && !quantity.isEmpty()) {
            Tool current = listTools.get(currentSelected);

            if (new Tool().updateTool(new Tool(current.getId(), categoryId, name, desc, Integer.parseInt(quantity), status))) {
                toaster.success("¡Genial!", "¡Has actualizado correctamente la herramienta!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar la herramienta");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        Tool current = listTools.get(currentSelected);

        if (new Tool().deleteTool(current.getId())) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente la herramienta!");

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
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar la herramienta");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        Tool currentClient = listTools.get(currentSelected);
        formView.nameField.setText(currentClient.getName());
        formView.descField.setText(currentClient.getDescription());
        formView.quantityField.setText(String.valueOf(currentClient.getQuantity()));
        formView.quantityField.setEnabled(false);
        setListCategory();
        int currentSelected = 0;

        for (int i = 0; i < categoryTools.size(); i++) {
            if (categoryTools.get(i).getId() == currentClient.getCategoryId()){
                currentSelected = i;
            }
        }
        formView.categoryField.setSelectedIndex(currentSelected);
        formView.statusField.setSelectedItem(currentClient.getStatus());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listTools = tool.getAllTools();

        for (int i = 0; i < listTools.size(); i++) {
            Tool c = listTools.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getCategoryName(), c.getName(), c.getDescription(), c.getQuantity(), c.getStatus()
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
        listTools = tool.searchTools(mainView.searchField.getText());

        for (int i = 0; i < listTools.size(); i++) {
            Tool c = listTools.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getCategoryName(), c.getName(), c.getDescription(), c.getQuantity(), c.getStatus()});
        }

        mainView.list.setModel(model);


    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }



}
