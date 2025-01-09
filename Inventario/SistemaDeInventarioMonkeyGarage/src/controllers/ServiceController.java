/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.CategoryTool;
import models.Client;
import models.Service;
import models.User;
import utils.Toaster;
import views.FormCategoryToolView;
import views.FormServiceView;
import views.HomeView;
import views.ListView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Jaqui Sanchez
 */
public class ServiceController {

    private final HomeView homeView;
    private ListView mainView;
    private User user;
    private FormServiceView formView;
    String[] columnNames = {"Id", "Nombre(s)", "Descripción", "Precio"};
    private Service service;
    private ArrayList<Service> listServices;
    private int currentSelected = -1;
    private Toaster toaster;

    public ServiceController(HomeView homeView, ListView mainView, User user) {
        this.homeView = homeView;
        this.mainView = mainView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.mainView.setVisible(true);
        this.formView = new FormServiceView();
        service = new Service();
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

                if (listServices.size() > 0 && currentSelected != -1) {
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
        formView.descField.setText("");
        formView.priceField.setText("");
        populateList();
    }

    public void createClient() {

        String name = formView.nameField.getText();
        String desc = formView.descField.getText();
        String price = formView.priceField.getText();


        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty() && !price.isEmpty()) {

            if (new Service().createService(new Service(name, desc, price))) {
                toaster.success("¡Genial!", "¡Has registrado correctamente el servicio!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar registar el servicio");
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
        String price = formView.priceField.getText();

        formView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !desc.isEmpty() && !price.isEmpty()) {
            Service current = listServices.get(currentSelected);

            if (new Service().updateService(new Service(current.getId(), name, desc, price))) {
                toaster.success("¡Genial!", "¡Has actualizado correctamente el servicio!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar el servicio");
                formView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        Service current = listServices.get(currentSelected);

        if (new Service().deleteService(current.getId())) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente el servicio!");

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
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar el servicio");
            formView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        Service currentClient = listServices.get(currentSelected);
        formView.nameField.setText(currentClient.getName());
        formView.descField.setText(currentClient.getDescription());
        formView.priceField.setText(currentClient.getPrice());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listServices = service.getAllServices();

        for (int i = 0; i < listServices.size(); i++) {
            Service c = listServices.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getDescription(), c.getPrice()
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
        listServices = service.searchServices(mainView.searchField.getText());

        for (int i = 0; i < listServices.size(); i++) {
            Service c = listServices.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getDescription(), c.getPrice()});
        }

        mainView.list.setModel(model);


    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }



}
