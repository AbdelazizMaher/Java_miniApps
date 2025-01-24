package databaseapplication;

import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class DataBaseApp extends AnchorPane {

    protected final TextField idText;
    protected final Button newButton;
    protected final Label idButton;
    protected final Button deleteButton;
    protected final Button previousButton;
    protected final Label firstNameButton;
    protected final TextField firstNameText;
    protected final Label lastNameButton;
    protected final TextField lastNameText;
    protected final Label middleNameButton;
    protected final TextField middleNameText;
    protected final Button updateButton;
    protected final Button nextButton;
    protected final Button firstButton;
    protected final Label emailButton;
    protected final TextField emailText;
    protected final Label phoneButton;
    protected final TextField phoneText;
    protected final Button lastButton;

    boolean updateCheck = false;

    public DataBaseApp() {

        idText = new TextField();
        newButton = new Button();
        idButton = new Label();
        deleteButton = new Button();
        previousButton = new Button();
        firstNameButton = new Label();
        firstNameText = new TextField();
        lastNameButton = new Label();
        lastNameText = new TextField();
        middleNameButton = new Label();
        middleNameText = new TextField();
        updateButton = new Button();
        nextButton = new Button();
        firstButton = new Button();
        emailButton = new Label();
        emailText = new TextField();
        phoneButton = new Label();
        phoneText = new TextField();
        lastButton = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        idText.setLayoutX(166.0);
        idText.setLayoutY(40.0);
        idText.setPrefHeight(31.0);
        idText.setPrefWidth(194.0);

        newButton.setLayoutX(64.0);
        newButton.setLayoutY(333.0);
        newButton.setMnemonicParsing(false);
        newButton.setText("New...");
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!newButton.getText().equals("Save")) {
                    newButton.setText("Save");
                    changeEditable(true);
                    clearText();
                } else {
                    User user = new User(Integer.parseInt(idText.getText()), firstNameText.getText(), middleNameText.getText(), lastNameText.getText(), emailText.getText(), phoneText.getText());
                    try {
                        boolean result = DataAccessLayer.insert(user);
                    } catch (SQLException ex) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                    newButton.setText("New...");
                    clearText();
                    changeEditable(false);
                }
            }
        });

        idButton.setLayoutX(59.0);
        idButton.setLayoutY(45.0);
        idButton.setText("ID");

        deleteButton.setLayoutX(208.0);
        deleteButton.setLayoutY(333.0);
        deleteButton.setMnemonicParsing(false);
        deleteButton.setText("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setNewButton();
                if (!idText.getText().isEmpty()) {
                    try {
                        boolean result = DataAccessLayer.delete(Integer.parseInt(idText.getText()));
                        clearText();
                    } catch (SQLException ex) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        });

        previousButton.setLayoutX(349.0);
        previousButton.setLayoutY(333.0);
        previousButton.setMnemonicParsing(false);
        previousButton.setText("Previous");
        previousButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setNewButton();
                try {
                    User user = DataAccessLayer.select(DataAccessLayer.PREVIOUS, Integer.parseInt(idText.getText()));
                    if (user != null) {
                        setText(user);
                    }
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        firstNameButton.setLayoutX(59.0);
        firstNameButton.setLayoutY(92.0);
        firstNameButton.setText("First Name");

        firstNameText.setLayoutX(166.0);
        firstNameText.setLayoutY(87.0);
        firstNameText.setPrefHeight(31.0);
        firstNameText.setPrefWidth(365.0);

        lastNameButton.setLayoutX(59.0);
        lastNameButton.setLayoutY(190.0);
        lastNameButton.setText("Last Name");

        lastNameText.setLayoutX(166.0);
        lastNameText.setLayoutY(185.0);
        lastNameText.setPrefHeight(31.0);
        lastNameText.setPrefWidth(335.0);

        middleNameButton.setLayoutX(59.0);
        middleNameButton.setLayoutY(141.0);
        middleNameButton.setText("Middle Name");

        middleNameText.setLayoutX(166.0);
        middleNameText.setLayoutY(136.0);
        middleNameText.setPrefHeight(31.0);
        middleNameText.setPrefWidth(238.0);

        updateButton.setLayoutX(132.0);
        updateButton.setLayoutY(333.0);
        updateButton.setMnemonicParsing(false);
        updateButton.setText("Update");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setNewButton();

                changeEditable(true);
                changeIdEditable(false);

                try {
                    User user = new User(Integer.parseInt(idText.getText()), firstNameText.getText(), middleNameText.getText(), lastNameText.getText(), emailText.getText(), phoneText.getText());
                    boolean result = DataAccessLayer.update(user);
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        nextButton.setLayoutX(433.0);
        nextButton.setLayoutY(333.0);
        nextButton.setMnemonicParsing(false);
        nextButton.setText("Next");
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setNewButton();
                try {
                    User user = DataAccessLayer.select(DataAccessLayer.NEXT, Integer.parseInt(idText.getText()));
                    if (user != null) {
                        setText(user);
                    }
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        firstButton.setLayoutX(279.0);
        firstButton.setLayoutY(333.0);
        firstButton.setMnemonicParsing(false);
        firstButton.setPrefHeight(31.0);
        firstButton.setPrefWidth(63.0);
        firstButton.setText("First");
        firstButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setNewButton();
                try {
                    User user = DataAccessLayer.selectPos(DataAccessLayer.FIRST);
                    if (user != null) {
                        setText(user);
                    }
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        emailButton.setLayoutX(59.0);
        emailButton.setLayoutY(241.0);
        emailButton.setText("Email");

        emailText.setLayoutX(166.0);
        emailText.setLayoutY(236.0);
        emailText.setPrefHeight(31.0);
        emailText.setPrefWidth(388.0);

        phoneButton.setLayoutX(59.0);
        phoneButton.setLayoutY(287.0);
        phoneButton.setText("Phone");

        phoneText.setLayoutX(166.0);
        phoneText.setLayoutY(282.0);
        phoneText.setPrefHeight(31.0);
        phoneText.setPrefWidth(209.0);

        lastButton.setLayoutX(494.0);
        lastButton.setLayoutY(333.0);
        lastButton.setMnemonicParsing(false);
        lastButton.setText("Last");
        lastButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setNewButton();
                try {
                    User user = DataAccessLayer.selectPos(DataAccessLayer.LAST);
                    if (user != null) {
                        setText(user);
                    }
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        changeEditable(false);
        try {

            User user = DataAccessLayer.selectPos(DataAccessLayer.FIRST);
            if (user != null) {
                setText(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getChildren().add(idText);
        getChildren().add(newButton);
        getChildren().add(idButton);
        getChildren().add(deleteButton);
        getChildren().add(previousButton);
        getChildren().add(firstNameButton);
        getChildren().add(firstNameText);
        getChildren().add(lastNameButton);
        getChildren().add(lastNameText);
        getChildren().add(middleNameButton);
        getChildren().add(middleNameText);
        getChildren().add(updateButton);
        getChildren().add(nextButton);
        getChildren().add(firstButton);
        getChildren().add(emailButton);
        getChildren().add(emailText);
        getChildren().add(phoneButton);
        getChildren().add(phoneText);
        getChildren().add(lastButton);

    }

    void changeEditable(boolean edit) {
        boolean newEdit = !edit;
        idText.setDisable(newEdit);
        firstNameText.setDisable(newEdit);
        middleNameText.setDisable(newEdit);
        lastNameText.setDisable(newEdit);
        emailText.setDisable(newEdit);
        phoneText.setDisable(newEdit);

    }

    void changeIdEditable(boolean edit) {
        boolean newEdit = !edit;
        idText.setDisable(newEdit);
    }

    void clearText() {
        idText.clear();
        firstNameText.clear();
        middleNameText.clear();
        lastNameText.clear();
        emailText.clear();
        phoneText.clear();
    }

    void setText(User user) {
        idText.setText(String.valueOf(user.getId()));
        firstNameText.setText(user.getFirstName());
        middleNameText.setText(user.getMiddleName());
        lastNameText.setText(user.getLastName());
        emailText.setText(user.getEmail());
        phoneText.setText(user.getPhone());
    }

    void setNewButton() {
        if (newButton.getText().equals("Save")) {
            newButton.setText("New...");
        }
    }
}
