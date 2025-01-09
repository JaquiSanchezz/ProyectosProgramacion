/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.Brand;
import models.Client;
import models.User;
import utils.Toaster;
import views.BrandsView;
import views.ClientsView;
import views.FormBrandView;
import views.FormClientView;
import views.HomeView;

/**
 *
 * @author Jaqui Sanchez
 */
public class BrandsController {
    
    
    private final HomeView homeView;
    private BrandsView mainView;
    private User user;
    private FormBrandView formView;
    String[] columnNames = {"Id", "Nombre(s)"};
    private Brand brand;
    private ArrayList<Brand> listBrands;
    private int currentSelected = -1;
    private Toaster toaster;

    public BrandsController(HomeView homeView, BrandsView mainView, User user) {
        this.homeView = homeView;
        this.mainView = mainView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.mainView.setVisible(true);
        this.formView = new FormBrandView();
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
                formView.actionDeleteClient.setVisible(false);
                homeView.containerMain.add(formView);
                homeView.containerMain.revalidate();

            }
        });

        mainView.editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = mainView.list.getSelectedRow();

                if (listBrands.size() > 0 && currentSelected != -1) {
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
        populateList();
    }

    public void createClient() {

        String name = formView.nameField.getText();


        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() ) {

            if (new Brand().createBrand(new Brand(name))) {
                toaster.success("¡Genial!", "¡Has registrado correctamente la marca!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar registar la marca");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void updateClient() {

        String name = formView.nameField.getText();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() ) {
            Brand current = listBrands.get(currentSelected);

            if (new Brand().updateBrand(new Brand(current.getId(), name))) {
                toaster.success("¡Genial!", "¡Has actualizado correctamente la marca!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar la marca");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        Brand current = listBrands.get(currentSelected);

        if (new Client().deleteClient(new Client(current.getId()))) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente la marca!");

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
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar la marca");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        Brand currentClient = listBrands.get(currentSelected);
        formView.nameField.setText(currentClient.getName());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listBrands = brand.getAllBrands();

        for (int i = 0; i < listBrands.size(); i++) {
            Brand c = listBrands.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName()});
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
        listBrands = brand.searchBrands(mainView.searchField.getText());

        for (int i = 0; i < listBrands.size(); i++) {
            Brand c = listBrands.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName()});
        }

        mainView.list.setModel(model);


    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }
    
    
}
