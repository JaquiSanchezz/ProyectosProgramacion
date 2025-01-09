/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

//import Vista.VentanaAdministrador;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import models.ProvidedService;
import models.Service;
import models.TransactionSale;
import models.User;
import views.*;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaqui Sanchez
 */
public class HomeController {

    private final HomeView homeView;
    private User user;
    private HomeListView homeListView;
    private ClientsView clientsView;
    private ClientController clientController;

    private UsersView usersView;
    private UserController userController;

    private BrandsView brandsView;
    private BrandsController brandsController;

    private ListView categoryTools;
    private CategoryController categoryController;

    private ListView servicesView;
    private ServiceController serviceController;

    private ListView toolsView;
    private ToolController toolController;

    private ListView productsView;
    private ProductController productController;
    private TransactionSale transactionSale;
    private ProvidedService providedService;
    String[] columnNamesLastServices= {"Id", "Cliente", "Servicio", "Costo", "Fecha entrega", "Fecha limite", "Nota"};
    String[] columnNamesLastSales = {"Id", "Cliente", "Productos", "Total", "Fecha"};

    public HomeController(HomeView homeView, User user) {
        this.homeView = homeView;
        this.user = user;
        this.homeView.setVisible(true);
        homeListView = new HomeListView();
        transactionSale = new TransactionSale();
        providedService = new ProvidedService();
        init();
        events();

    }

    public void init() {
        this.homeView.containerMain.setLayout(new BorderLayout());
        loadInitView();
    }

    private void loadInitView(){
        clearContainer();
        homeView.containerMain.add(homeListView);
        homeView.containerMain.revalidate();
        populateTableLastSales();
        populateTableLastServices();
    }
    public void events() {
        homeView.homeAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadInitView();

            }
        });

        homeView.clientsAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                clientsView = new ClientsView();
                clientController = new ClientController(homeView, clientsView, user);
                homeView.containerMain.add(clientsView);
                homeView.containerMain.revalidate();

            }
        });

        homeView.usersAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                usersView = new UsersView();
                userController = new UserController(homeView, usersView, user);
                homeView.containerMain.add(usersView);
                homeView.containerMain.revalidate();

            }
        });

        homeView.brandsAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                brandsView = new BrandsView();
                brandsController = new BrandsController(homeView, brandsView, user);
                homeView.containerMain.add(brandsView);
                homeView.containerMain.revalidate();

            }
        });

        homeView.categoriesAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                categoryTools = new ListView();
                categoryController = new CategoryController(
                        homeView, categoryTools, user);
                homeView.containerMain.add(categoryTools);
                homeView.containerMain.revalidate();


            }
        });

        homeView.servicesAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                servicesView = new ListView();
                serviceController = new ServiceController(
                        homeView, servicesView, user);
                homeView.containerMain.add(servicesView);
                homeView.containerMain.revalidate();

            }
        });

        homeView.toolsAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                toolsView = new ListView();
                toolController = new ToolController(
                        homeView, toolsView, user);
                homeView.containerMain.add(toolsView);
                homeView.containerMain.revalidate();

            }
        });

        homeView.productsAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearContainer();
                productsView = new ListView();
                productController = new ProductController(
                        homeView, productsView, user);
                homeView.containerMain.add(productsView);
                homeView.containerMain.revalidate();

            }
        });

        homeView.btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePreferences();
                new LoginController(new LoginView());
            }
        });


        homeListView.newSaleAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SaleController(new FormNewSaleView());
            }
        });

        homeListView.newServiceAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProvideServiceController(new FormNewServiceView());
            }
        });

       homeListView.updateInventoryActiontTools.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new InventoryListController(new ListInventoryView(), "tool");

           }
       });

        homeListView.updateInventoryActionProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryListController(new ListInventoryView(), "product");

            }
        });

    }

    public void deletePreferences() {

        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        final String PREF_NAME = "email";
        prefs.put(PREF_NAME, "default");

    }

    private void populateTableLastSales(){

        ArrayList<TransactionSale> transactionSales = transactionSale.getLastTransactions();


        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNamesLastSales) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNamesLastSales);


        for (int i = 0; i < transactionSales.size(); i++) {
            TransactionSale c = transactionSales.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getClientName()+" "+c.getClientLastName(), c.getQuantity(), c.getTotal(), c.getCreated_at()
            });
        }

        homeListView.listLastSales.setModel(model);


    }

    private void populateTableLastServices(){
        ArrayList<ProvidedService> providedServices = providedService.getLastProvidedServices();
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNamesLastServices) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNamesLastServices);

        for (int i = 0; i < providedServices.size(); i++) {
            ProvidedService c = providedServices.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getClientName()+" "+c.getClientLastName(), c.getServiceName(), c.getCurrentPrice(), c.getDelivered_date(), c.getLimit_date(), c.getNote()
            });
        }

        homeListView.listExpirations.setModel(model);
    }

    public void clearContainer() {
        homeView.containerMain.removeAll();
        homeView.containerMain.repaint();
    }
}
