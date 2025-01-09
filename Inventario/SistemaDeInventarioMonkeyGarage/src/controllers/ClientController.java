/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Client;
import models.User;
import utils.RoundedButton;
import utils.Toaster;
import views.FormClientView;
import views.ClientsView;
import views.HomeView;

/**
 *
 * @author Jaqui Sanchez
 */
public class ClientController {

    private final HomeView homeView;
    private ClientsView clientsView;
    private User user;
    private FormClientView formClientView;
    String[] columnNames = {"Id", "Nombre(s)", "Apellidos", "Correo electronico", "Telefono"};
    private Client client;
    private ArrayList<Client> listClients;
    private int currentSelected = -1;
    private Toaster toaster;

    public ClientController(HomeView homeView, ClientsView clientsView, User user) {
        this.homeView = homeView;
        this.clientsView = clientsView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.clientsView.setVisible(true);
        this.formClientView = new FormClientView();
        client = new Client();
        toaster = new Toaster(homeView.containerMain);
        populateList();
    }

    public void events() {

        clientsView.addAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = -1;
                clearContainer();
                formClientView.actionDeleteClient.setVisible(false);
                homeView.containerMain.add(formClientView);
                homeView.containerMain.revalidate();

            }
        });

        clientsView.editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = clientsView.list.getSelectedRow();

                if (listClients.size() > 0 && currentSelected != -1) {
                    populateForm();
                    clearContainer();
                    formClientView.actionDeleteClient.setVisible(true);
                    homeView.containerMain.add(formClientView);
                    homeView.containerMain.revalidate();

                }

            }
        });
        
        
        clientsView.searchAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = -1;
                populateListFromSearch();
            }
        });
        
        
        //Form Events
        formClientView.backAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToList();
            }
        });

        formClientView.actionDeleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        formClientView.actionForm.addActionListener(new ActionListener() {
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
        homeView.containerMain.add(clientsView);
        homeView.containerMain.revalidate();
        formClientView.nameField.setText("");

        formClientView.lastNameField.setText("");
        formClientView.emailField.setText("");
        formClientView.phoneField.setText("");
        populateList();
    }

    public void createClient() {

        String name = formClientView.nameField.getText();
        String last_name = formClientView.lastNameField.getText();
        String email = formClientView.emailField.getText();
        String phone = formClientView.phoneField.getText();

        formClientView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !last_name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {

            if (new Client().createClient(new Client(name, last_name, email, phone))) {
                toaster.success("¡Genial!", "¡Has registrado correctamente al cliente!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar registar al cliente");
                formClientView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formClientView.actionDeleteClient.setEnabled(true);
        }

    }

    public void updateClient() {

        String name = formClientView.nameField.getText();
        String last_name = formClientView.lastNameField.getText();
        String email = formClientView.emailField.getText();
        String phone = formClientView.phoneField.getText();

        formClientView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !last_name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
            Client current = listClients.get(currentSelected);

            if (new Client().updateClient(new Client(current.getId(), name, last_name, email, phone))) {
                toaster.success("¡Genial!", "¡Has actualizado correctamente al cliente!");

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
                toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar al cliente");
                formClientView.actionDeleteClient.setEnabled(true);
            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formClientView.actionDeleteClient.setEnabled(true);
        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        Client current = listClients.get(currentSelected);

        if (new Client().deleteClient(new Client(current.getId()))) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente al cliente!");

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
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar al cliente");
            formClientView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        Client currentClient = listClients.get(currentSelected);
        formClientView.nameField.setText(currentClient.getName());

        formClientView.lastNameField.setText(currentClient.getLast_name());
        formClientView.emailField.setText(currentClient.getEmail());
        formClientView.phoneField.setText(currentClient.getPhone());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listClients = client.getAllClients();

        for (int i = 0; i < listClients.size(); i++) {
            Client c = listClients.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getLast_name(), c.getEmail(), c.getPhone()});
        }

        clientsView.list.setModel(model);
        clientsView.list.getColumnModel().getColumn(0).setMaxWidth(50);
        clientsView.list.getColumnModel().getColumn(1).setMaxWidth(200);
        clientsView.list.getColumnModel().getColumn(2).setMaxWidth(200);
        clientsView.list.getColumnModel().getColumn(3).setMaxWidth(250);
        clientsView.list.getColumnModel().getColumn(4).setMaxWidth(150);

    }
    
    public void populateListFromSearch() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listClients = client.searchClients(clientsView.searchField.getText());

        for (int i = 0; i < listClients.size(); i++) {
            Client c = listClients.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getLast_name(), c.getEmail(), c.getPhone()});
        }

        clientsView.list.setModel(model);
        clientsView.list.getColumnModel().getColumn(0).setMaxWidth(50);
        clientsView.list.getColumnModel().getColumn(1).setMaxWidth(200);
        clientsView.list.getColumnModel().getColumn(2).setMaxWidth(200);
        clientsView.list.getColumnModel().getColumn(3).setMaxWidth(250);
        clientsView.list.getColumnModel().getColumn(4).setMaxWidth(150);

    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }

}
