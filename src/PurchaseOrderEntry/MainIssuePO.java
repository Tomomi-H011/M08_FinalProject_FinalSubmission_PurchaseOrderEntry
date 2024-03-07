/**
* Assignment: SDEV200_M08_FinalProject_FinalSubmission
* File: MainIssuePO.java
* Version: 1.1
* Date: 3/7/2024
* Author: Tomomi Hobara
* Description: This program is a Purchase Order entry application for an automotive dealership. 
                It issues two types of Purchase Orders. Standandard POs are issued for ordering physical items such as 
                automotive parts, office supplies, and uniforms. Service POs are for ordering 
                services such as maintenance, housekeeping, consultation, and advertisement services.
                The program allows users to review a list of POs that are in 'Pending' or 'Issued' status.
                For pending POs, users can change the line item quantity and confirm the POs by clicking the 'Issue PO' button.
                When a PO is issued, the PO status, line item quantity, and line total are updated in the underlying MySQL database.
* Variables: 
    -tvPoHeader: TableView for displaying PO headers
    -tvLineItem: TableView for displaying PO line items. The quantity column is editable for standard POs (ordering physical items).
    -btnPoList: Button for querying the PO Header table and displaying a Tableview of POs
    -btnLogout: Button for exiting the program
    -cboPoStatus: ComboBox for filtering the PO TableView by PO status ('Pending' vs. 'Issued')
    -btnClear: Button to clear PO details displayed in the Line Item TableView and other Texts
    -btnShowPo: Button for displaying line items in a TableView form.
* Note: 
    - Login username = 'user', password = 'password123'
*/

package PurchaseOrderEntry;

import javafx.scene.control.TableColumn;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class MainIssuePO extends Application {
    // Fields for the PO stage
    // Menu pane
    private Button btnPoList = new Button("PO List");
    private Button btnLogout = new Button("Logout");

    // PO header pane - Purchase Order class
    private Label lblPoNo = new Label("PO No: ");
    private Label lblOrderDate = new Label("Order Date: ");
    private Label lblOrderType = new Label("Order Type: ");
    private Label lblOrderTotal = new Label("Order Total: ");
    private Label lblPoVendorId = new Label("Vendor ID: ");
    private Label lblPoStatus = new Label("Status");
    private Label lblPoCategory = new Label("Category");
    private Text txtPoNo = new Text("");  // Data from DB
    private Text txtOrderDate = new Text("");
    private Text txtOrderType = new Text("");
    private Text txtOrderTotal = new Text("$");
    private Text txtPoVendorId = new Text("");
    private Text txtPoStatus = new Text("");
    private Text txtPoCategory = new Text("");

    // PO header pane - Vendor class
    private Label lblVendorName = new Label("Vendor Name: ");
    private Label lblAddress = new Label("Address: ");
    private Label lblPhone = new Label("Phone: ");
    private Text txtVendorName = new Text("");
    private Text txtAddress = new Text("");
    private Text txtPhone = new Text("");

    // Set up PO line item TableView  // To Do: Replace Object in the bracket to the actual obj type
    private TableView<ObservableList<String>> tvLineItem = new TableView<>();
    private TableColumn<ObservableList<String>, String> tcLineNo = new TableColumn<>("Line No");
    private TableColumn<ObservableList<String>, String> tcItemNo = new TableColumn<>("Item No");
    private TableColumn<ObservableList<String>, String> tcItemName = new TableColumn<>("Item Name");
    private TableColumn<ObservableList<String>, String> tcUnitPrice = new TableColumn<>("Unit Price");
    private TableColumn<ObservableList<String>, String> tcQuantity = new TableColumn<>("Quantity");
    private TableColumn<ObservableList<String>, String> tcLineTotal = new TableColumn<>("Line Total");
    private TableColumn<ObservableList<String>, String> tcUom = new TableColumn<>("UOM");

    // PO button pane 
    private Text txtUpdateResult = new Text("");
    private Button btnClear = new Button("Clear");
    private Button btnIssuePo = new Button("Issue PO");

    // Fields for the second stage (stageSelectPo)
    // PO Header Treeview
    private TableView<ObservableList<String>> tvPoHeader = new TableView<>();
    private TableColumn<ObservableList<String>, String> tcPoNo = new TableColumn<>("PO No"); 
    private TableColumn<ObservableList<String>, String> tcOrderType = new TableColumn<>("Order Type");
    private TableColumn<ObservableList<String>, String> tcCategory = new TableColumn<>("Category");
    private TableColumn<ObservableList<String>, String> tcPoVendor = new TableColumn<>("Vendor");
    private TableColumn<ObservableList<String>, String> tcOrderTotal = new TableColumn<>("Total $");
    private TableColumn<ObservableList<String>, String> tcPoStatus = new TableColumn<>("PO Status");
    
    // Other nodes for the second stage
    private Label lblForCboPoStatus = new Label("PO Status");
    private ComboBox<String> cboPoStatus = new ComboBox<>();
    private Button btnShowPo = new Button("Show PO");
    private Button btnClose = new Button("Close");

    // Nodes for the login stage
    private TextField tfUsername = new TextField();
    private PasswordField pfPassword = new PasswordField();


    public static void main(String[] args) {
        Application.launch(args);
    }

/* Create a login stage */
    @Override
    public void start(Stage stageLogIn){
        // Create a pane for entering login information
        GridPane paneForLogIn = new GridPane();
        paneForLogIn.setPadding(new Insets(40));
        paneForLogIn.setHgap(10);
        paneForLogIn.setVgap(10);


        Label lblUsername = new Label("User name: ");
        Label lblPassword = new Label("Password: ");

      

        paneForLogIn.addRow(0, lblUsername, tfUsername);
        paneForLogIn.addRow(1, lblPassword, pfPassword);


        // Create a pane for login buttons
        VBox paneForLogInButtons = new VBox();
        paneForLogInButtons.setPadding(new Insets(20));
        paneForLogInButtons.setAlignment(Pos. CENTER);
        paneForLogInButtons.setSpacing(20);

        Button btnLogIn = new Button("Login");
        Button btnCancel = new Button("Cancel");  

        btnLogIn.setPrefWidth(100);
        btnCancel.setPrefWidth(100);

        paneForLogInButtons.getChildren().addAll(btnLogIn, btnCancel);

        // A pane for combining login textfields and buttons
        VBox paneForLogInScane = new VBox();
        paneForLogInScane.getChildren().addAll(paneForLogIn, paneForLogInButtons);

        // Create a scene and place it in the stage
        Scene sceneLogIn = new Scene(paneForLogInScane, 350, 300);

        stageLogIn.setScene(sceneLogIn);
        stageLogIn.setTitle("Purchase Order Entry - Login");
        stageLogIn.show();

        // Close the stage when the Cancel button is clicked
        btnCancel.setOnAction(e -> {
            stageLogIn.close();
        });

        // Check for login information and open the po
        btnLogIn.setOnAction(e -> {
            if (checkLogin()){
                startPoEntry();
            }
            else {

            }
            
        });
    }

    /*Initialize the database and opens the primary stage.*/

    public void startPoEntry() {
        //Initialize MySQL database
        Database.initializeDB();

        // Create a VBox for the menu
        VBox paneForMenu = new VBox();
        paneForMenu.setPadding(new Insets(10));
        paneForMenu.setAlignment(Pos.TOP_CENTER);
        paneForMenu.setSpacing(20);
        paneForMenu.getChildren().addAll(btnPoList, btnLogout);
        btnPoList.setPrefWidth(90);
        btnLogout.setPrefWidth(90);

        // Create a VBox for the PO header - Status
        VBox paneForPoStatus = new VBox();
        paneForPoStatus.setMinWidth(150);
        paneForPoStatus.setPadding(new Insets(10));
        paneForPoStatus.setSpacing(5);
        paneForPoStatus.setAlignment(Pos.BASELINE_LEFT);
        paneForPoStatus.getChildren().addAll(lblPoStatus, txtPoStatus, lblOrderTotal, txtOrderTotal);
        


        // Create a GridPane for the PO header - PO
        GridPane paneForPOHeaderDetail = new GridPane();
        paneForPOHeaderDetail.setMinWidth(300);
        paneForPOHeaderDetail.setPadding(new Insets(10));
        paneForPOHeaderDetail.setVgap(5);
        paneForPOHeaderDetail.setHgap(5);
        paneForPOHeaderDetail.setAlignment(Pos.BASELINE_LEFT);
        paneForPOHeaderDetail.addRow(0, lblPoNo, txtPoNo);
        paneForPOHeaderDetail.addRow(1, lblOrderDate, txtOrderDate);
        paneForPOHeaderDetail.addRow(2, lblOrderType, txtOrderType);
        paneForPOHeaderDetail.addRow(3, lblPoCategory, txtPoCategory);


        // Create a GridPane for the PO header - Vendor
        GridPane paneForVendor = new GridPane();
        paneForVendor.setMinWidth(350);
        paneForVendor.setPadding(new Insets(10));
        paneForVendor.setVgap(5);
        paneForVendor.setHgap(5);
        paneForVendor.setAlignment(Pos.BASELINE_LEFT);
        paneForVendor.addRow(0, lblPoVendorId, txtPoVendorId);
        paneForVendor.addRow(1, lblVendorName,txtVendorName);
        paneForVendor.addRow(2, lblAddress, txtAddress);
        paneForVendor.addRow(3, lblPhone, txtPhone);
   

        // HBox to group PO header panes
        HBox paneForPoHeader = new HBox();
        paneForPoHeader.getChildren().addAll(paneForPoStatus, paneForPOHeaderDetail, paneForVendor);

        // Pane for PO Item TreeView
        Pane paneForPOItem = new Pane();
        paneForPOItem.setPadding(new Insets(10));
        paneForPOItem.getChildren().add(tvLineItem);

        // Format the TreeView and columns
        tvLineItem.setMinHeight(400);
        tvLineItem.setMaxHeight(400);
        tcLineNo.setPrefWidth(60);
        tcItemNo.setPrefWidth(100);
        tcItemName.setPrefWidth(200);
        tcUnitPrice.setPrefWidth(100);
        tcQuantity.setPrefWidth(100);
        tcLineTotal.setPrefWidth(100);
        tcUom.setPrefWidth(50);
        
        // Clear existing columns
        tvLineItem.getColumns().clear();

        // Map the columns
        tcLineNo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        tcItemNo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        tcItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        tcQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        tcUnitPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        tcUom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));
        tcLineTotal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(7)));

        // Set up a TableView for the PO Item
        tvLineItem.getColumns().addAll(tcLineNo, tcItemNo, tcItemName, tcQuantity, tcUnitPrice, tcLineTotal, tcUom);
    
        // HBox for update text
        HBox paneForUpdateText = new HBox();
        paneForUpdateText.setPadding(new Insets(20));
        paneForUpdateText.setAlignment(Pos.CENTER);
        paneForUpdateText.getChildren().add(txtUpdateResult);

        // HBox for buttons at the bottom
        HBox paneForButtons = new HBox();
        paneForButtons.setPadding(new Insets(10));
        paneForButtons.setSpacing(15);
        paneForButtons.setAlignment(Pos.BASELINE_CENTER);
        paneForButtons.getChildren().addAll(btnClear, btnIssuePo);
        btnIssuePo.setDisable(true);  // Disable btnIssuePo by default. Only enable it when Pending POs are selected.
        btnIssuePo.setStyle("-fx-background-color: #d3d3d3; -fx-opacity: 1;");


        // VBox to combine PO header, Items, and button panes
        VBox paneForPO = new VBox();
        paneForPO.getChildren().addAll(paneForPoHeader, paneForPOItem, paneForUpdateText, paneForButtons);

        // Create a Hbox to place all the panes
        HBox root = new HBox();
        root.getChildren().addAll(paneForMenu, paneForPO);
       

        // Create a scene and place it in the stage
        Scene scene = new Scene(root, 900, 700);
        Stage poMainStage = new Stage();
        poMainStage.setScene(scene);
        poMainStage.setTitle("Purchase Order Entry");
        poMainStage.show();

        // Set up event handlers
        btnPoList.setOnAction(e -> {
            // Reset txtUpdateResult
            txtUpdateResult.setText("");

            // Reset cboPoStatus before opening the selectPoStage
            cboPoStatus.getSelectionModel().clearSelection();
            cboPoStatus.setValue("");

            selectPoStage();  // Open the TableView of pending POs
            fillTableViewPOHeader(tvPoHeader);  // Fill the TableView with pending POs
        });

        // Exit the program when btnLogout is clicked
        btnLogout.setOnAction(e -> {
            System.exit(0);
        });
    }
    
    // Create the stage for selecting and opening a PO detail
    private void selectPoStage() {

        //Pane for cboPoStatus
        GridPane paneForPoStatus = new GridPane();
        paneForPoStatus.setPadding(new Insets(20, 10, 40, 30));
        paneForPoStatus.setAlignment(Pos.BASELINE_LEFT);
        paneForPoStatus.setHgap(5);
        paneForPoStatus.setVgap(5);
        paneForPoStatus.addRow(0, lblForCboPoStatus);
        paneForPoStatus.addRow(1, cboPoStatus);
        cboPoStatus.setPrefWidth(120);
        cboPoStatus.getItems().clear();  // Clear existing items in the ComboBox
        cboPoStatus.getItems().addAll("Pending", "Issued");  // Add values to the ComboBox
        cboPoStatus.setValue("");  // Set default

        // Pane for pendingPO TableView
        HBox paneForPoHeader = new HBox();
        paneForPoHeader.setPadding(new Insets(10));
        paneForPoHeader.setAlignment(Pos.CENTER);
        paneForPoHeader.getChildren().add(tvPoHeader);

        // Format the TableView and columns
        tvPoHeader.setMinHeight(400);
        tvPoHeader.setMaxHeight(400);
        tcPoNo.setPrefWidth(80);
        tcOrderType.setPrefWidth(80);
        tcCategory.setPrefWidth(130);
        tcPoVendor.setPrefWidth(140);
        tcOrderTotal.setPrefWidth(130);
        tcPoStatus.setPrefWidth(90);

        //Pane for Buttons
        HBox paneForPoButtons = new HBox();
        paneForPoButtons.setPadding(new Insets(10));
        paneForPoButtons.setSpacing(10);
        paneForPoButtons.setAlignment(Pos.CENTER);
        paneForPoButtons.getChildren().addAll(btnShowPo, btnClose);

        // Clear existing columns
        tvPoHeader.getColumns().clear();

        // Map the columns
        tcPoNo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        tcOrderType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        tcCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        tcPoVendor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        tcOrderTotal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1))); //To do: switch to total/add to resultset
        tcPoStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));

        // Set up a TableView for the PO Item
        tvPoHeader.getColumns().addAll(tcPoNo, tcOrderType, tcCategory, tcPoVendor, tcOrderTotal, tcPoStatus);


        // Create a Hbox to place all the panes
        VBox rootSelectPo = new VBox();
        rootSelectPo.getChildren().addAll(paneForPoStatus, paneForPoHeader, paneForPoButtons);
       

        // Create a scene and place it in the stage
        Scene sceneSelectPo = new Scene(rootSelectPo, 700, 600);
        Stage stageSelectPo = new Stage();
        stageSelectPo.setScene(sceneSelectPo);
        stageSelectPo.setTitle("Select Purchase Order");
        stageSelectPo.show();


        //Set up an event handler for cboPoStatus
        cboPoStatus.setOnAction(e -> {
            String selectedPoStatus = cboPoStatus.getSelectionModel().getSelectedItem();

            ObservableList <ObservableList<String>> dataPoHeader =  Database.queryPoHeader(selectedPoStatus);
            
            // Clear existing rows from the TableView
            tvPoHeader.getItems().clear();

            // Rebuild rows in the TableView based on the filter (Pending vs Issued)
            for (ObservableList<String> row: dataPoHeader) {
                tvPoHeader.getItems().add(row);
            }
        });


        // Set up an event handler for btnClose 
        btnClose.setOnAction(e -> {
            stageSelectPo.close();
        });

        // Set up an event handler for btnShowPo
        btnShowPo.setOnAction(e -> {
            // Get the value of PoNo from the selected row from the TableView, tvPoHeader
            String tempPoStatus = showPo();
            stageSelectPo.close();
            // Reset nodes
            cboPoStatus.setValue("");; // Clear the selected item
            btnIssuePo.setDisable(true);  // Disable btnIssuePo by default. Only enable it when Pending POs are selected.
            btnIssuePo.setStyle("-fx-background-color: #d3d3d3; -fx-opacity: 1;");

            // Change the quantity column to editable when the PO is a standard order and in 'Pending' status.
            if ("Pending".equals(tempPoStatus) && "Standard".equals(txtOrderType.getText())) {
                makeColumnEditable(tvLineItem);
            }
            if ("Pending".equals(tempPoStatus)) {
                btnIssuePo.setDisable(false);
                btnIssuePo.setStyle("");
            }
        });


        // Clear all data from the primary stage
        btnClear.setOnAction(e -> {
            clearPoDetails();
        });

        // Issue PO by updating Order Total and poStatus in tblPoHeader and Quantity and Line Total in tblLineItem
        btnIssuePo.setOnAction(e -> {
            
            if (checkQuantity()) {  // Check if the quantities are valid
                // Update Order Total and PoStatus in tblPoHeader
                String UpdatePoStatusResult = Database.updatePoStatus(txtPoNo.getText());

                if (UpdatePoStatusResult.equals("Updated")) {

                    txtPoStatus.setText("Issued");  // Update the PoStatus in the PO header area

                    // Update Quantity and Line Total in tblLineItem
                    // Get all the rows from the line item tableview
                    ObservableList<ObservableList<String>> items = tvLineItem.getItems();
                    for (ObservableList<String> item : items) {
                        String poLineNo = item.get(1);
                        String quantity = item.get(4);
                        String lineTotal = item.get(7);

                        System.out.println("poLineNo: " + poLineNo + " qty: "+ quantity + " LineTotal: " + lineTotal);

                        boolean lineItemUpdateSuccessful = Database.updateLineItem(txtPoNo.getText(), poLineNo, quantity, lineTotal);

                        if (lineItemUpdateSuccessful) {
                            System.out.println("Update successful for poLineNo: " + poLineNo);
                        } 
                        else {
                            // false returned by SQLException
                            System.out.println("Update failed for poLineNo: " + poLineNo);
                        }
                    }
                    clearPoDetails();  // Clear PO details after it has been issued
                    txtUpdateResult.setText("PO has been issued");  // Show the message above btnIssuePo

                }
                else{
                    txtUpdateResult.setText("Error issuing PO");
                }
            }
        });

    }



    // Called by btnPoList. Create a list of PO Headers in a TableView on the secondary stage, selectPoStage
    private ObservableList<ObservableList<String>> fillTableViewPOHeader(TableView<ObservableList<String>> tvPoHeader) {
        
        ObservableList <ObservableList<String>> dataPoHeader = Database.queryPoHeader("");

        // Clear existing rows from the TableView
        tvPoHeader.getItems().clear();

        // Add data to the TableView
        for (ObservableList<String> row: dataPoHeader) {
            tvPoHeader.getItems().add(row);
        }

        return dataPoHeader;
    }

    // Display the PO details based on the selected row on the TableView, tvPoHeader
    private String showPo() {
        // Create a SelectionModel
        TableView.TableViewSelectionModel<ObservableList<String>> selectionModel = tvPoHeader.getSelectionModel();

        String poNo = "";  // Used for querying the tblPoItem tble
        String poVendorId = "";  // Used for querying the tblVendor table
        String tempPoStatus = "";  // Used for making the Quantity column editable.

        ObservableList<String> selectedPoHeaderRow = selectionModel.getSelectedItem();  // This list will be used to fill PO Header on the primary stage

        // Check if a row is selected
        if (!selectionModel.isEmpty()) {
            
            // Get the PoNo and poVendor from the selected row
            poNo = selectedPoHeaderRow.get(0);
            poVendorId = selectedPoHeaderRow.get(4);
            tempPoStatus = selectedPoHeaderRow.get(5);

            for (String value : selectedPoHeaderRow) {  // To do delete
                System.out.println("Value: " + value);
            }

            System.out.println("Selected PoNo: " + poNo);
        } 
        else {
            // Show Alert if no row is selected before btnShowPo is pressed
            System.out.println("No row selected.");
            Alert noSelectedRowAlert = new Alert(Alert.AlertType.ERROR);
            noSelectedRowAlert.setTitle("Error: No row selected");
            noSelectedRowAlert.setHeaderText(null);
            noSelectedRowAlert.setContentText("Please select a row.");
            noSelectedRowAlert.showAndWait();
            return tempPoStatus = "";
        }

        // Pass the PoNo and run a query
        ObservableList <ObservableList<String>> dataPoItem =  Database.queryPoItem(poNo);
        ObservableList <String> dataPoVendor = Database.queryPoVendor(poVendorId);

        // Fill the tvLineItem and update text fields
        fillLineItemTableView(dataPoItem);
        updateVendorTextFields(dataPoVendor);
        fillPoHeaderTextFields(selectedPoHeaderRow);
        return tempPoStatus;

    }

    // Fill the Line Item TableView on the primary stage
    private void fillLineItemTableView(ObservableList<ObservableList<String>> dataPoItem) {

        // Clear existing rows from the TableView
        tvLineItem.getItems().clear();

        // Add data to the TableView
        for (ObservableList<String> row : dataPoItem) {
            tvLineItem.getItems().add(row);
        }

        tvLineItem.setEditable(false);  // Make tvLineItem non editable by default // Change the Quantity column to editable after showPo() runs

        // Calculate and update the OrderTotal
        calculateOrderTotal();
    }

    // Fill text fields for vendor details
    private void updateVendorTextFields(ObservableList<String> dataPoVendor) {
        txtVendorName.setText(dataPoVendor.get(1));
        txtAddress.setText(dataPoVendor.get(2));
        txtPhone.setText(dataPoVendor.get(3));
    }

    // Fill text fields for PO Header
    private void fillPoHeaderTextFields(ObservableList<String> selectedPoHeaderRow) {
        txtPoNo.setText(selectedPoHeaderRow.get(0));
        txtOrderDate.setText(selectedPoHeaderRow.get(1));
        txtOrderType.setText(selectedPoHeaderRow.get(2));
        txtPoCategory.setText(selectedPoHeaderRow.get(3));
        txtPoVendorId.setText(selectedPoHeaderRow.get(4));
        txtPoStatus.setText(selectedPoHeaderRow.get(5));
    }

    // Calculate the grand total of a PO based on its line item totals
    private void calculateOrderTotal() {
        double orderTotal = 0.0;
    
        // Iterate through the items in the TableView
        for (ObservableList<String> item : tvLineItem.getItems()) {
            
            double lineTotal = Double.parseDouble(item.get(7));  
            orderTotal += lineTotal;
        }
    
        // Update the txtOrderTotal
        txtOrderTotal.setText("$" + String.format("%.2f", orderTotal));
    }



    private void makeColumnEditable(TableView<ObservableList<String>> tvLineItem){
        
        tvLineItem.setEditable(true);
        tcQuantity.setCellFactory(TextFieldTableCell.forTableColumn());  // Make the Quantity column editable when the PoStatus is Pending

        // Set up an event handler to the Quantity column to update the Line Total column automatically
        tcQuantity.setOnEditCommit( e -> {
            try {
                // Get the new quantity
                String newQuantity = e.getNewValue();

                // Update the Quantity
                ObservableList<String> editedItem = e.getRowValue();
                editedItem.set(4, newQuantity);

                // Recalculate the line total
                double unitPrice = Double.parseDouble(editedItem.get(5));
                double quantity = Double. parseDouble(newQuantity);
                double lineTotal =unitPrice * quantity;

                // Update the Observable list
                editedItem.set(7, String.format("%.2f", lineTotal));

                // Update the TableView
                tvLineItem.refresh();

                // Update the txtOrderTotal when quantity and line total are changed
                calculateOrderTotal();

                System.out.println(quantity);
                System.out.println(lineTotal);
            }
            catch (NumberFormatException ex) {
                System.out.println("Caught NumberFormatException. Showing alert.");
                showAlert();
            }
        });
    }
    
    // Clear displayed PO details (line item data) from the second stage
    private void clearPoDetails() {
        // Clear text fields
        txtPoNo.setText("");
        txtOrderDate.setText("");
        txtOrderType.setText("");
        txtOrderTotal.setText("$");
        txtPoVendorId.setText("");
        txtPoStatus.setText("");
        txtPoCategory.setText("");
        txtVendorName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");

        // Clear items from the TableView
        tvLineItem.getItems().clear();
    }


    // Check if entered line item quantities are valid numbers. 
    private boolean checkQuantity() {
        Double quantityDouble = null;
        ObservableList<ObservableList<String>> items = tvLineItem.getItems();
        for (ObservableList<String> item : items) {
            String quantityString = item.get(4);
            try {
                quantityDouble = Double.parseDouble(quantityString);
            }
            catch (NumberFormatException e) {
                System.out.println("NumberFormatException for invalid quantity: " + quantityDouble);
                showAlert();  // Show an alert window if it is non-numeric or less than or equal to zero.
                return false;
            }
            if (quantityDouble <= 0) {  
                showAlert();
                System.out.println("NumberFormatException for invalid quantity: " + quantityDouble);
                return false;
            }
        }
        return true;
    }

    // Show an alert if the entered quantities are invalid
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid quantity");
        alert.setHeaderText(null);
        alert.setContentText("Please enter numeric value.");
        alert.showAndWait();
    }

    // Check for username and password
    private boolean checkLogin() {
        String usernameInput = tfUsername.getText();
        String passwordInput = pfPassword.getText();
        tfUsername.clear();
        pfPassword.clear();

        if ("user".equals(usernameInput) && "password123".equals(passwordInput)) {
            return true;    
        }
        else {
            Alert alertLogin = new Alert(Alert.AlertType.ERROR);
            alertLogin.setTitle("Invalid login");
            alertLogin.setHeaderText(null);
            alertLogin.setContentText("Please re-enter username and password.");
            alertLogin.showAndWait();
            tfUsername.clear();
            pfPassword.clear();
            return false;
        }
    }
} 
