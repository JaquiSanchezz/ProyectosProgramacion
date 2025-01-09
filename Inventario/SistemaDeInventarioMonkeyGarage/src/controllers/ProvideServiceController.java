package controllers;

import models.*;
import utils.Toaster;
import views.FormNewSaleView;
import views.FormNewServiceView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProvideServiceController {


    private FormNewServiceView formNewServiceView;
    private Toaster toaster;

    private Service service;
    private Client client;
    private ArrayList<Service> listService;
    private ArrayList<SelectedServices> listServicesSelected;
    private ArrayList<Client> listClients;
    private TransactionProvidedService transactionProvidedService;
    private ProvidedService providedService;
    String[] columnNames = {"Id", "Servicio", "Costo",  "Cantidad", "Fecha limite", "Nota", "Total"};
    private DefaultTableModel model;

    public ProvideServiceController(FormNewServiceView formNewServiceView) {
        this.formNewServiceView = formNewServiceView;
        service = new Service();
        client = new Client();
        transactionProvidedService = new TransactionProvidedService();
        providedService = new ProvidedService();
        listServicesSelected = new ArrayList<>();

        this.formNewServiceView.setVisible(true);
        toaster = new Toaster(formNewServiceView.mainContainer);
        initInfo();
        events();

    }

    private void initInfo() {
        populateList();
        configTable();
    }

    public void populateList() {
        DefaultComboBoxModel dml= new DefaultComboBoxModel();
        listService = service.getAllServices();
        for (Service ls : listService) {
            dml.addElement(ls.getName());
        }
        formNewServiceView.serviceField.setModel(dml);

        DefaultComboBoxModel dmlClient= new DefaultComboBoxModel();
        listClients = client.getAllClients();

        for (Client listClient : listClients) {
            dmlClient.addElement(listClient.getName()+" "+listClient.getLast_name()+" - "+listClient.getEmail());
        }
        formNewServiceView.clientField.setModel(dmlClient);
    }

    public void configTable() {
        model = new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        formNewServiceView.listProducts.setModel(model);
    }

    private void events(){

        formNewServiceView.actionAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToList();

            }
        });

        formNewServiceView.actionRemoveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItemToList();
            }
        });

        formNewServiceView.actionForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                processSale();


            }
        });

    }

    private void addItemToList() {
        String quantityStr = formNewServiceView.quantityField.getText();
        Service currentSelected = listService.get(formNewServiceView.serviceField.getSelectedIndex());
        String limitDate = formNewServiceView.limitDateField.getText();
        String note = formNewServiceView.noteField.getText();

        if (!quantityStr.isEmpty() && !limitDate.isEmpty() && !note.isEmpty()){


                boolean exist = false;
                int index = 0;
                for (int i = 0; i < listServicesSelected.size(); i++) {

                    if (listServicesSelected.get(i).getService().getId() == currentSelected.getId()){
                        exist = true;
                        index = i;
                    }
                }
                float total = Integer.parseInt(quantityStr) * Float.valueOf(currentSelected.getPrice());
                if (exist){
                    listServicesSelected.get(index).setQuantity(Integer.parseInt(quantityStr));
                    listServicesSelected.get(index).setLimit_date(limitDate);
                    listServicesSelected.get(index).setNote(note);
                    listServicesSelected.get(index).setTotal(total);
                    model.removeRow(index);
                    model.addRow(new Object[]{String.valueOf(currentSelected.getId()), currentSelected.getName(), currentSelected.getPrice(), quantityStr, limitDate, note,String.valueOf(total)});
                }else{
                    listServicesSelected.add(new SelectedServices(
                            Integer.parseInt(quantityStr),
                            currentSelected,
                            total,
                            note,
                            limitDate,
                            limitDate
                    ));
                    model.addRow(new Object[]{String.valueOf(currentSelected.getId()), currentSelected.getName(), currentSelected.getPrice(), quantityStr, limitDate, note,String.valueOf(total)});
                    formNewServiceView.listProducts.repaint();
                }





        }else {
            toaster.info("¡Ups!.", "Debes llenar los campos");
        }
    }

    private void removeItemToList() {

        if (listServicesSelected.size() > 0){
            int currentSelected = formNewServiceView.listProducts.getSelectedRow();;

            if (currentSelected != -1){
                model.removeRow(currentSelected);
                listServicesSelected.remove(currentSelected);
                formNewServiceView.listProducts.repaint();
            }


        }


    }

    private void processSale() {

        if (listServicesSelected.size() > 0){

            int totalProducts = 0;
            float total = 0;

            for (SelectedServices selectedService : listServicesSelected) {
                totalProducts += selectedService.getQuantity();
                total += selectedService.getTotal();
            }

            int clientId = listClients.get(formNewServiceView.clientField.getSelectedIndex()).getId();
            String paymentType = formNewServiceView.typePaymentField.getSelectedItem().toString();

            if (transactionProvidedService.createTransaction(new TransactionProvidedService(
                    clientId,
                    totalProducts,
                    String.valueOf(total),
                    "Pendiente",
                    "Pagado",
                    paymentType
            ))){

                TransactionProvidedService currentSale = transactionProvidedService.getLastTransaction();

                for (SelectedServices selectedService : listServicesSelected) {
                    providedService.createProvidedService(new ProvidedService(
                            "",
                            clientId,
                            selectedService.getService().getId(),
                            currentSale.getId(),
                            selectedService.getService().getPrice(),
                            selectedService.getNote(),
                            selectedService.getDelivered_date(),
                            selectedService.getLimit_date()
                    ));


                }

                listServicesSelected = new ArrayList<>();
                formNewServiceView.quantityField.setText("");
                formNewServiceView.limitDateField.setText("");
                formNewServiceView.noteField.setText("");
                for (int i = 0; i < model.getRowCount(); i++) {
                    model.removeRow(i);
                }
                formNewServiceView.listProducts.repaint();

                toaster.success("¡Genial!", "¡Has realizado una nueva venta de servicios!");
            }else{

                toaster.info("¡Ups!.", "Ocurrio un error al intentar guardar la información");

            }





        }else{
            toaster.info("¡Ups!.", "Debes seleccionar al menos un producto");
        }


    }

}
