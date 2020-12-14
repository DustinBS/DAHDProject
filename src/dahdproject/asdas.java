//private Button[] buttons;
//    ComboBox<String> cboFood;
//    ComboBox<String> cboSort;
//    TextField[] txtF;
//    Button btnAdd;
//    Button btnOrder;
//    Button btnManager;
//    Button btnFinish;
//    Button btnClear;
//    Button btnMainPage;
//    TextArea txtarea;
//    TextArea bill;
//    TextArea manageCustomer;
//    Label label1;
//    Label label2;
//    Button b2;
//    Stage window;
//
//    private ArrayList<String> foodsOrdered;
//    private Orders orders = Orders.getInstace();
//    private Money total;
//
//    @Override
//    public void start(Stage primaryStage) {
//        //controls
//        btnMainPage = new Button("Back");
//        btnAdd = new Button("Add");
//        btnOrder = new Button("Order Here");
//        btnManager = new Button("Manager");
//        txtF = new TextField[4];
//        for (int i = 0; i < 4; i++) {
//            txtF[i] = new TextField();
//        }
//
//        btnFinish = new Button("Finish");
//        btnClear = new Button("Clear");
//        cboFood = new ComboBox<>();
//        cboFood.getItems().addAll(Orders.getFoodOptions());
//        cboFood.setMaxWidth(165.0);
//        foodsOrdered = new ArrayList<String>();
//        //sort
//        cboSort = new ComboBox<>();
//        cboSort.getItems().addAll(
//                "Sort by name",
//                "Sort by date",
//                "Sort by price"
//        );
//        cboSort.setPromptText("Sort: ");
//
//        //panes
//        HBox topPane = getTopPane();
//        HBox mainPane = centerMain();
//        VBox manage = getManager();
//        ScrollPane manageScrollPane = new ScrollPane(manage);
//        manageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        manageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//
//        GridPane rightPane = getRightPane();
//        StackPane centerPane = getcenterPane();
//        //parent
//        BorderPane pane = new BorderPane();
//        pane.setTop(topPane);
//        pane.setCenter(mainPane);
//        pane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE,
//                CornerRadii.EMPTY, Insets.EMPTY)));
//
//        //wire up buttons
//        btnMainPage.setOnAction(e -> pane.setCenter(mainPane));
//        btnOrder.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                if (!rightPane.getChildren().contains(btnMainPage)) {
//                    rightPane.add(btnMainPage, 2, 17);
//                }
//                cboFood.getSelectionModel().clearSelection();
//                for (TextField textField : txtF) {
//                    textField.clear();
//                }
//                bill.clear();
//                pane.setCenter(rightPane);
//            }
//        });
//        btnManager.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                if (!manage.getChildren().contains(btnMainPage)) {
//                    manage.getChildren().add(btnMainPage);
//                }
//                orders = Orders.readData();
//                manageCustomer.setText(orders.toString());
//                cboSort.getSelectionModel().clearSelection();
//                pane.setCenter(manage);
//            }
//        });
//        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                if (cboFood.getValue() == null) {
//                    bill.appendText("Please make a selection. \n");
//                } else {
//                    bill.appendText(cboFood.getValue() + " ");
//                    bill.appendText(txtF[1].getText() + " ");
//                    bill.appendText(txtF[2].getText() + " \n");
//                    foodsOrdered.add(cboFood.getValue());
//                    total = Orders.getTotal(foodsOrdered);
//                    txtF[3].setText(total.toString());
//                }
//            }
//        });
//        btnClear.setOnAction(e -> {
//            bill.clear();
//            total = new Money();
//        });
//        btnFinish.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                orders.addOrder(foodsOrdered, txtF[1].getText(), new Date(txtF[2].getText()));
//                for (TextField textField : txtF) {
//                    textField.clear();
//                }
//                bill.clear();
//                Orders.saveData();
//            }
//        });
//        cboSort.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                int mode = cboSort.getSelectionModel().getSelectedIndex();
//                switch (mode) {
//                    case 0: // name
//                        orders.sortByName();
//                        break;
//                    case 1: // date
//                        orders.sortByDate();
//                        break;
//                    case 2: // price
//                        orders.sortByTotal();
//                        break;
//                }
//                manageCustomer.setText(orders.toString());
//            }
//        });
//        //scene
//        Scene scene = new Scene(pane);
//        Scene scene1 = new Scene(rightPane); // Stage scene1 line 55?
//        Scene scene3 = new Scene(manage);
//        scene.setFill(Color.AQUA);
//        //stage
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Dustin is cool");
//        primaryStage.setWidth(600);
//        primaryStage.setHeight(500);
//        primaryStage.setX(200);
//        primaryStage.setY(200);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }
//
//    StackPane getcenterPane() {
//
//        StackPane centerPane = new StackPane();
//        centerPane.setAlignment(Pos.CENTER_LEFT);
//
//        return centerPane;
//    }
//
//    HBox getTopPane() {
//        HBox topPane = new HBox();
//        label1 = new Label();
//        Image image = new Image(getClass().getResourceAsStream("/images/gfood.jpg"));
//        label1.setGraphic(new ImageView(image));
//        label1.setAlignment(Pos.CENTER_LEFT);
//        topPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//        topPane.getChildren().add(label1);
//
//        return topPane;
//    }
//
//    HBox centerMain() {
//        HBox mainPane = new HBox();
//        mainPane.setAlignment(Pos.CENTER);
//        mainPane.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
//        mainPane.getChildren().addAll(btnManager, btnOrder);
//
//        HBox.setMargin(btnManager, new Insets(20, 10, 30, 10));
//        HBox.setMargin(btnOrder, new Insets(20, 30, 30, 20));
//
//        return mainPane;
//    }
//
//    VBox getManager() {
//        VBox manage = new VBox();
//        manageCustomer = new TextArea();
//        manage.setPadding(new Insets(10));
//        manageCustomer.setPrefSize(500, 200);
//        manage.getChildren().addAll(cboSort, manageCustomer);
//        manage.setMargin(cboSort, new Insets(10, 10, 10, 200));
//        manage.setMargin(btnMainPage, new Insets(10, 10, 10, 250));
//        return manage;
//    }
//
//    GridPane getRightPane() {
//        GridPane rightPane = new GridPane();
//        rightPane.setAlignment(Pos.CENTER);
//        rightPane.setPadding(new Insets(20, 10, 20, 0));
//        rightPane.setBackground(new Background(
//                new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY,
//                        Insets.EMPTY)));
//        rightPane.setHgap(10);
//        rightPane.setVgap(10);
//
//        rightPane.add(new Label("Food:"), 0, 0);
//        rightPane.add(cboFood, 1, 0);
//        rightPane.add(new Label("Name:"), 0, 1);
//        rightPane.add(new Label("Pick-up date:"), 0, 2);
//        rightPane.add(new Label("Total:"), 0, 3);
//        for (int i = 1; i < 4; i++) {
//            rightPane.add(txtF[i], 1, i);
//        }
//        txtF[3].setDisable(true);
//        bill = new TextArea();
//        bill.setPrefColumnCount(5);
//        bill.setPrefRowCount(5);
//        bill.setMaxSize(2100, 2100);
//        GridPane.setColumnSpan(bill, 5);
//        GridPane.setRowSpan(bill, 12);
//        rightPane.add(bill, 0, 5);
//        rightPane.add(btnFinish, 0, 17);
//        rightPane.add(btnClear, 1, 17);
//        GridPane.setHalignment(btnFinish, HPos.LEFT);
//        rightPane.add(btnAdd, 3, 3);
//        return rightPane;
//    }
//
//}