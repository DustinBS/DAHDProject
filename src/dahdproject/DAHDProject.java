package dahdproject;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import numberlist.objectlist.Money;
import numberlist.objectlist.Date;

/**
 *
 * @author ngochuong
 */
public class DAHDProject extends Application {

    VBox btnPane;
    ComboBox<String> cboFood;
    ComboBox<String> cboSort;
    TextField txtName;
    TextField txtDate;
    Button btnAdd;
    Button btnOrder;
    Button btnManager;
    Button btnFinish;
    Button btnClear;
    Button btnBack;
    ArrayList<Button> btnManagerBoxes;
    TextArea txtarea;
    TextArea bill;
    TextArea manageCustomer;
    Label label1;
    Label label2;
    Label lblTotal;
    Button b2;
    Stage window;

    private ArrayList<String> foodsOrdered;
    private Orders orders = Orders.getInstace();
    private Money total;

    @Override
    public void start(Stage primaryStage) {
        //controls
        btnBack = new Button("Back");
        btnAdd = new Button("Add");
        btnOrder = new Button("Order Here");
        btnManager = new Button("Manager");
        txtName = new TextField();
        txtDate = new TextField();
        btnFinish = new Button("Finish");
        btnClear = new Button("Clear");
        cboFood = new ComboBox<>();
        cboFood.getItems().addAll(Orders.getFoodOptions());
        cboFood.setMaxWidth(165.0);
        foodsOrdered = new ArrayList<String>();
        btnManagerBoxes = new ArrayList<Button>();

        //sort
        cboSort = new ComboBox<>();
        cboSort.getItems().addAll(
                "Sort by name",
                "Sort by pick-up date",
                "Sort by price"
        );
        cboSort.setPromptText("Sort: ");

        //panes
        btnPane = new VBox();
        GridPane mainPane = centerMain();
        VBox manage = getManager();
        ScrollPane manageScrollPane = new ScrollPane(manage);
        manageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        manageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane rightPane = getRightPane();
        StackPane centerPane = getcenterPane();
        //parent
        BorderPane pane = new BorderPane();
        pane.setCenter(mainPane);
        pane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE,
                CornerRadii.EMPTY, Insets.EMPTY)));

        //wire up buttons
        btnBack.setOnAction(e -> pane.setCenter(mainPane));
        btnOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!rightPane.getChildren().contains(btnBack)) {
                    rightPane.add(btnBack, 2, 17);
                }
                clearSelection();
                pane.setCenter(rightPane);

            }
        });
        btnManager.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                orders = Orders.readData();
                orders.sortByDateCreated();
                String[] orderTokens = orders.toString().split("\n");
                btnManagerBoxes.clear();
                for (String orderToken : orderTokens) {
                    btnManagerBoxes.add(new Button(orderToken));
                }
//                manageCustomer.setText(orders.toString());
                btnPane.getChildren().clear();
                btnPane.getChildren().addAll(btnManagerBoxes);
                cboSort.getSelectionModel().clearSelection();
                if (!manage.getChildren().contains(btnBack)) {
                    manage.getChildren().add(btnBack);
                }
                pane.setCenter(manage);
            }
        });
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (cboFood.getSelectionModel().isEmpty()
                        || txtName.getText().isEmpty()
                        || txtDate.getText().isEmpty()) {
                    bill.appendText("Please make a selection. \n");
                } else {
                    if (bill.getText().contains("Please make a selection.")) {
                        bill.clear();
                    }
                    try {
                        Date pickUp = new Date(txtDate.getText());
                        bill.appendText(cboFood.getValue() + " ");
                        bill.appendText(txtName.getText() + " ");
                        bill.appendText(pickUp + " \n");
                        foodsOrdered.add(cboFood.getValue());
                        total = Orders.getTotal(foodsOrdered);
                        lblTotal.setText(total.toString());
                    } catch (IllegalArgumentException ex) {
                        bill.appendText(ex.getMessage() + "\n");
                    }
                }
            }
        });
        btnClear.setOnAction(e -> {
            clearSelection();
        });
        btnFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (cboFood.getSelectionModel().isEmpty()
                        || txtName.getText().isEmpty()
                        || txtDate.getText().isEmpty()) {
                    bill.appendText("Please make a selection. \n");
                }
                try {
                    orders.addOrder(foodsOrdered, txtName.getText(), new Date(txtDate.getText()));
                } catch (IllegalArgumentException ex) {
                    bill.appendText(ex.getMessage());
                }
                clearSelection();
                Orders.saveData();
            }
        });
        cboSort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int mode = cboSort.getSelectionModel().getSelectedIndex();
                switch (mode) {
                    case 0: // name
                        orders.sortByName();
                        break;
                    case 1: // date
                        orders.sortByDate();
                        break;
                    case 2: // price
                        orders.sortByTotal();
                        break;
                }
//                manageCustomer.setText(orders.toString());
                String[] orderTokens = orders.toString().split("\n");
                btnManagerBoxes.clear();
                for (String orderToken : orderTokens) {
                    btnManagerBoxes.add(new Button(orderToken));
                }
                btnPane.getChildren().clear();
                btnPane.getChildren().addAll(btnManagerBoxes);
            }
        });
        //scene
        Scene scene = new Scene(pane);
        Scene scene1 = new Scene(rightPane);
        Scene scene3 = new Scene(manage);
        //stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("D-Catering");
        primaryStage.setWidth(750);
        primaryStage.setHeight(600);
        primaryStage.setX(200);
        primaryStage.setY(200);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    StackPane getcenterPane() {

        StackPane centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER_LEFT);

        return centerPane;
    }

    GridPane centerMain() {
        GridPane mainPane = new GridPane();
        label2 = new Label();
        Image image = new Image(getClass().getResourceAsStream("/images/catering-dinner.jpg"));
        label2.setGraphic(new ImageView(image));
        Label label = new Label("Welcome to D-Catering");
        label.setTextFill(Color.CHOCOLATE);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setMargin(label, new Insets(20, 20, 40, 50));

        mainPane.add(btnManager, 2, 4);
        mainPane.add(btnOrder, 4, 4);
        mainPane.add(label, 3, 3);
        mainPane.add(label2, 3, 2);
        label.setFont(new Font("Times New Roman", 30));
        btnManager.setFont(new Font("Times New Roman", 18));
        btnOrder.setFont(new Font("Times New Roman", 18));

        return mainPane;

    }

    VBox getManager() {
        VBox manage = new VBox();
        manageCustomer = new TextArea();
        manage.setPadding(new Insets(10));
        manageCustomer.setPrefSize(500, 200);
//        manage.getChildren().addAll(cboSort, manageCustomer);
        manage.getChildren().addAll(cboSort, btnPane);
        manage.setMargin(cboSort, new Insets(10, 10, 10, 200));
        manage.setMargin(btnBack, new Insets(10, 10, 10, 250));
        return manage;
    }

    GridPane getRightPane() {
        GridPane rightPane = new GridPane();
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(20, 10, 20, 0));
        rightPane.setBackground(new Background(
                new BackgroundFill(Color.AZURE, CornerRadii.EMPTY,
                        Insets.EMPTY)));
        rightPane.setHgap(10);
        rightPane.setVgap(10);

        rightPane.add(new Label("Food:"), 0, 0);
        rightPane.add(cboFood, 1, 0);
        rightPane.add(new Label("Name:"), 0, 1);
        rightPane.add(new Label("Pick-up date:"), 0, 2);
        rightPane.add(new Label("Total:"), 0, 3);
        rightPane.add(txtName, 1, 1);
        rightPane.add(txtDate, 1, 2);
        lblTotal = new Label("_________________________");
        rightPane.add(lblTotal, 1, 3);

        bill = new TextArea();
        bill.setPrefColumnCount(5);
        bill.setPrefRowCount(5);
        bill.setMaxSize(2100, 2100);
        GridPane.setColumnSpan(bill, 5);
        GridPane.setRowSpan(bill, 12);
        rightPane.add(bill, 0, 5);
        rightPane.add(btnFinish, 0, 17);
        rightPane.add(btnClear, 1, 17);
        GridPane.setHalignment(btnFinish, HPos.LEFT);
        rightPane.add(btnAdd, 3, 3);
        return rightPane;
    }

    void clearSelection() {
        cboFood.getSelectionModel().clearSelection();
        txtName.clear();
        txtDate.clear();
        bill.clear();
        foodsOrdered.clear();
        total = new Money();
        lblTotal.setText("_________________________");
    }
}
