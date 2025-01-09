/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.Brand;
import models.CategoryTool;
import models.Client;
import models.User;
import utils.Toaster;
import views.*;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Jaqui Sanchez
 */
public class CategoryController {

    private final HomeView homeView;
    private ListView mainView;
    private User user;
    private FormCategoryToolView formView;
    String[] columnNames = {"Id", "Nombre(s)", "Descripción"};
    private CategoryTool categoryTool;
    private ArrayList<CategoryTool> listCategoryTools;
    private int currentSelected = -1;
    private Toaster toaster;

    public CategoryController(HomeView homeView, ListView mainView, User user) {
        this.homeView = homeView;
        this.mainView = mainView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.mainView.setVisible(true);
        this.formView = new FormCategoryToolView();
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
                formView.actionDeleteClient.setVisible(false);
                homeView.containerMain.add(formView);
                homeView.containerMain.revalidate();

            }
        });

        mainView.editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = mainView.list.getSelectedRow();

                if (listCategoryTools.size() > 0 && currentSelected != -1) {
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

    public void goToList() {
        currentSelected = -1;
        clearContainer();
        homeView.containerMain.add(mainView);
        homeView.containerMain.revalidate();
        formView.nameField.setText("");
        formView.descFiled.setText("");
        populateList();
    }

    public void createClient() {

        String name = formView.nameField.getText();
        String desc = formView.descFiled.getText();


        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty()) {

            if (new CategoryTool().createCategoryTool(new CategoryTool(name, desc))) {
                toaster.success("¡Genial!", "¡Has registrado correctamente la categoria!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar registar la categoria");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void updateClient() {

        String name = formView.nameField.getText();
        String desc = formView.descFiled.getText();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty()) {
            CategoryTool current = listCategoryTools.get(currentSelected);

            if (new CategoryTool().updateCategoryTool(new CategoryTool(current.getId(), name, desc))) {
                toaster.success("¡Genial!", "¡Has actualizado correctamente la categoria!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar la categoria");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        CategoryTool current = listCategoryTools.get(currentSelected);

        if (new Client().deleteClient(new Client(current.getId()))) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente la categoria!");

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
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar la categoria");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        CategoryTool currentClient = listCategoryTools.get(currentSelected);
        formView.nameField.setText(currentClient.getName());
        formView.descFiled.setText(currentClient.getDescription());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listCategoryTools = categoryTool.getAllCategoryTool();

        for (int i = 0; i < listCategoryTools.size(); i++) {
            CategoryTool c = listCategoryTools.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getDescription()});
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
        listCategoryTools = categoryTool.searchCategoryTools(mainView.searchField.getText());

        for (int i = 0; i < listCategoryTools.size(); i++) {
            CategoryTool c = listCategoryTools.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getDescription()});
        }

        mainView.list.setModel(model);


    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }



}
