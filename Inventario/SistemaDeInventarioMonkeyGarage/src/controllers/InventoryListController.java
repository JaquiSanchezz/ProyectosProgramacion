package controllers;

import models.Product;
import models.Tool;
import models.Transaction;
import utils.Toaster;
import views.FormUpdateInventoryViewMain;
import views.ListInventoryView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryListController {


    private ListInventoryView listInventoryView;
    private Toaster toaster;
    private String model;
    private Transaction transaction;
    private Product product;
    private ArrayList<Product> listProducts;
    private Tool tool;
    private ArrayList<Tool> listTools;

    String[] columnNamesTools = {"Id","Categoria", "Nombre", "Descripción", "Cantidad", "Estado"};
    String[] columnNames = {"Id","Marca", "Nombre", "Costo P/u", "Precio de venta", "Inventario","Entrada", "Salidas", "Valor"};

    public InventoryListController(ListInventoryView listInventoryView, String model) {
        this.listInventoryView = listInventoryView;
        this.model = model;
        product = new Product();
        tool = new Tool();
        transaction = new Transaction();

        this.listInventoryView.setVisible(true);
        toaster = new Toaster(listInventoryView.mainContainer);
        initInfo();
        events();

    }

    private void initInfo() {
        if (model.equals("tool")){
            populateListTools();
        }else{
            populateListProducts();
        }

    }

    public void populateListTools() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNamesTools) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNamesTools);
        listTools = tool.getAllTools();

        for (int i = 0; i < listTools.size(); i++) {
            Tool c = listTools.get(i);
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getCategoryName(), c.getName(), c.getDescription(), c.getQuantity(), c.getStatus()
            });
        }

        listInventoryView.list.setModel(model);


    }

    public void populateListProducts() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        listProducts = product.getAllProducts();
        int entrySize = 0;
        int outSize = 0;
        for (int i = 0; i < listProducts.size(); i++) {
            Product c = listProducts.get(i);

            ArrayList<Transaction> transactionArrayList = transaction.getAllMonthTransactions(c.getId(), "products");


            for (int j = 0; j < transactionArrayList.size(); j++) {

                if (transactionArrayList.get(j).getType().equals("entry")){
                    entrySize += transactionArrayList.get(j).getQuantity();
                }else{
                    outSize += transactionArrayList.get(j).getQuantity();
                }

            }


            float val = c.getQuantity() * Float.valueOf(c.getSelling_price());
            model.addRow(new Object[]{String.valueOf(c.getId()), c.getBrandName(), c.getName(), c.getCost(), c.getSelling_price(), c.getQuantity(), entrySize, outSize, val
            });
        }

        listInventoryView.list.setModel(model);


    }

    private void events() {

        listInventoryView.updateInventoryActiontTools.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.equals("tool")){
                    new InventoryUpdateController(new FormUpdateInventoryViewMain(), "tool");
                }else{
                    new InventoryUpdateController(new FormUpdateInventoryViewMain(), "product");
                }
            }
        });

    }

}
