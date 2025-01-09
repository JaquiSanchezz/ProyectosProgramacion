/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.Client;
import models.User;
import utils.Toaster;
import views.ClientsView;
import views.FormClientView;
import views.FormUserView;
import views.HomeView;
import views.UsersView;

/**
 *
 * @author Jaqui Sanchez
 */
public class UserController {

    private HomeView homeView = null;
    private UsersView userView;
    private User user;
    private FormUserView formUserView;
    String[] columnNames = {"Id", "Nombre(s)", "Apellidos", "Correo electronico", "Usuario"};
    private ArrayList<User> listUsers;
    private int currentSelected = -1;
    private Toaster toaster;

    public UserController(HomeView homeView, UsersView userView, User user) {
        this.homeView = homeView;
        this.userView = userView;
        this.user = user;
        init();
        events();
    }

    public void init() {
        this.userView.setVisible(true);
        this.formUserView = new FormUserView();
        toaster = new Toaster(homeView.containerMain);
        populateList();
    }

    public void events() {

        userView.addAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = -1;
                clearContainer();
                formUserView.actionDeleteClient.setVisible(false);
                homeView.containerMain.add(formUserView);
                homeView.containerMain.revalidate();

            }
        });

        userView.editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = userView.list.getSelectedRow();

                if (listUsers.size() > 0 && currentSelected != -1) {
                    populateForm();
                    clearContainer();
                    formUserView.actionDeleteClient.setVisible(true);
                    homeView.containerMain.add(formUserView);
                    homeView.containerMain.revalidate();

                }

            }
        });

        userView.searchAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSelected = -1;
                populateListFromSearch();
            }
        });

        //Form Events
        formUserView.backAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToList();
            }
        });

        formUserView.actionDeleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        formUserView.actionForm.addActionListener(new ActionListener() {
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
        homeView.containerMain.add(userView);
        homeView.containerMain.revalidate();
        formUserView.nameField.setText("");

        formUserView.lastNameField.setText("");
        formUserView.emailField.setText("");
        formUserView.usernameField.setText("");
        populateList();
    }

    public void createClient() {

        String name = formUserView.nameField.getText();
        String last_name = formUserView.lastNameField.getText();
        String email = formUserView.emailField.getText();
        String username = formUserView.usernameField.getText();
        String password = formUserView.passwordField.getText();
        String confirmPassword = formUserView.passwordConfirmField.getText();

        formUserView.actionDeleteClient.setEnabled(false);
        if (!name.isEmpty() && !last_name.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty() &&  !confirmPassword.isEmpty() && (password.equals(confirmPassword))) {

            if (new User().registerUser(new User(name, last_name, email, username, password))) {
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
                formUserView.actionDeleteClient.setEnabled(true);
            }

        } else if (!password.equals(confirmPassword)) {
            toaster.info("¡Ups!.", "Las contraseñas no coinciden");
            formUserView.actionDeleteClient.setEnabled(true);
        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
            formUserView.actionDeleteClient.setEnabled(true);
        }

    }

    public void updateClient() {

        String name = formUserView.nameField.getText();
        String last_name = formUserView.lastNameField.getText();
        String email = formUserView.emailField.getText();
        String username = formUserView.usernameField.getText();
        String password = formUserView.passwordField.getText();
        String confirmPassword = formUserView.passwordConfirmField.getText();

        formUserView.actionDeleteClient.setEnabled(false);

        if (!password.isEmpty() || !confirmPassword.isEmpty()) {

            if (!name.isEmpty() && !last_name.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty() &&  !confirmPassword.isEmpty() && (password.equals(confirmPassword))) {
                User current = listUsers.get(currentSelected);

                if (new User().updateUserAll(new User(current.getId(), name, last_name, email, username, password))) {
                    toaster.success("¡Genial!", "¡Has actualizado correctamente al usuario!");

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
                    toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar al usuario");
                    formUserView.actionDeleteClient.setEnabled(true);
                }

            } else if (!password.equals(confirmPassword)) {
                toaster.info("¡Ups!.", "Las contraseñas no coinciden");
                formUserView.actionDeleteClient.setEnabled(true);
            } else {
                toaster.info("¡Ups!.", "Debes llenar los campos");
                formUserView.actionDeleteClient.setEnabled(true);
            }

        } else {

            if (!name.isEmpty() && !last_name.isEmpty() && !email.isEmpty() && !username.isEmpty()) {
                User current = listUsers.get(currentSelected);

                if (new User().updateUserWithOutPassword(new User(current.getId(), name, last_name, email, username))) {
                    toaster.success("¡Genial!", "¡Has actualizado correctamente al usuario!");

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
                    toaster.info("¡Ups!.", "Ocurrio un error al intentar actualizar al usuario");
                    formUserView.actionDeleteClient.setEnabled(true);
                }

            } else {
                toaster.info("¡Ups!.", "Debes llenar los campos");
                formUserView.actionDeleteClient.setEnabled(true);
            }

        }

    }

    public void deleteClient() {
        System.err.println("Eliminado");
        User current = listUsers.get(currentSelected);

        if (new User().deleteUser(current.getId())) {
            toaster.success("¡Genial!", "¡Has eliminado correctamente al usuario!");

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
            toaster.info("¡Ups!.", "Ocurrio un error al intentar eliminar al usuario");
            formUserView.actionDeleteClient.setEnabled(true);
        }

    }

    public void populateForm() {

        User currentClient = listUsers.get(currentSelected);
        formUserView.nameField.setText(currentClient.getName());

        formUserView.lastNameField.setText(currentClient.getLast_name());
        formUserView.emailField.setText(currentClient.getEmail());
        formUserView.usernameField.setText(currentClient.getUsername());

    }

    public void populateList() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listUsers = user.getAllUsers();

        for (int i = 0; i < listUsers.size(); i++) {
            User c = listUsers.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getLast_name(), c.getEmail(), c.getUsername()});
        }

        userView.list.setModel(model);
        userView.list.getColumnModel().getColumn(0).setMaxWidth(50);
        userView.list.getColumnModel().getColumn(1).setMaxWidth(200);
        userView.list.getColumnModel().getColumn(2).setMaxWidth(200);
        userView.list.getColumnModel().getColumn(3).setMaxWidth(250);
        userView.list.getColumnModel().getColumn(4).setMaxWidth(150);

    }

    public void populateListFromSearch() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listUsers = user.searchUsers(userView.searchField.getText());

        for (int i = 0; i < listUsers.size(); i++) {
            User c = listUsers.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getName(), c.getLast_name(), c.getEmail(), c.getUsername()});
        }

        userView.list.setModel(model);
        userView.list.getColumnModel().getColumn(0).setMaxWidth(50);
        userView.list.getColumnModel().getColumn(1).setMaxWidth(200);
        userView.list.getColumnModel().getColumn(2).setMaxWidth(200);
        userView.list.getColumnModel().getColumn(3).setMaxWidth(250);
        userView.list.getColumnModel().getColumn(4).setMaxWidth(150);

    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }

}
