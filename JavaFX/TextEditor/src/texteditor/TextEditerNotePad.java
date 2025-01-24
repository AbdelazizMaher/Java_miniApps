package texteditor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TextEditerNotePad extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu fileMenuBar;
    protected final MenuItem newMenu;
    protected final MenuItem openMenu;
    protected final MenuItem saveMenu;
    protected final MenuItem exitMenu;
    protected final Menu editMenuBar;
    protected final MenuItem cutMenu;
    protected final MenuItem copyMenu;
    protected final MenuItem pasteMenu;
    protected final MenuItem deleteMenu;
    protected final MenuItem selectAllMenu;
    protected final Menu helpMenuBar;
    protected final MenuItem aboutMenu;
    protected final TextArea textArea;

    public TextEditerNotePad(Stage myStage) {

        menuBar = new MenuBar();
        fileMenuBar = new Menu();
        newMenu = new MenuItem();
        openMenu = new MenuItem();
        saveMenu = new MenuItem();
        exitMenu = new MenuItem();
        editMenuBar = new Menu();
        cutMenu = new MenuItem();
        copyMenu = new MenuItem();
        pasteMenu = new MenuItem();
        deleteMenu = new MenuItem();
        selectAllMenu = new MenuItem();
        helpMenuBar = new Menu();
        aboutMenu = new MenuItem();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        fileMenuBar.setMnemonicParsing(false);
        fileMenuBar.setText("File");

        newMenu.setMnemonicParsing(false);
        newMenu.setText("New");
        newMenu.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.clear();
            }
        });

        openMenu.setMnemonicParsing(false);
        openMenu.setText("Open");
        openMenu.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileInputStream fis = null;
                DataInputStream dis = null;

                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(myStage);
                if (file != null) {
                    try {
                        fis = new FileInputStream(file);
                        dis = new DataInputStream(fis);
                        textArea.setText(dis.readUTF());
                        //byte[] inData = new byte[fis.available()];
                        //fis.read(inData);
                        //textArea.setText(new String(inData));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            //fis.close();
                            dis.close();
                        } catch (IOException ex) {
                            Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        });

        saveMenu.setMnemonicParsing(false);
        saveMenu.setText("Save");
        saveMenu.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileOutputStream fos = null;
                DataOutputStream dos = null;

                FileChooser fc = new FileChooser();
                File file = fc.showSaveDialog(myStage);
                if (file != null) {
                    try {
                        fos = new FileOutputStream(file);
                        dos = new DataOutputStream(fos);
                        dos.writeUTF(textArea.getText());
                        dos.close();
                        //byte[] outData = textArea.getText().getBytes();
                        //fos.write(outData);
                        //fos.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        exitMenu.setMnemonicParsing(false);
        exitMenu.setText("Exit");
        exitMenu.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        exitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog<ButtonType> dialog = new Dialog<>();
                DialogPane about = new DialogPane();

                String contentText = new String("Do you want to save the file");
                dialog.getDialogPane().setContentText(contentText);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    FileOutputStream fos = null;
                    DataOutputStream dos = null;

                    FileChooser fc = new FileChooser();
                    File file = fc.showSaveDialog(myStage);
                    if (file != null) {
                        try {
                            fos = new FileOutputStream(file);
                            dos = new DataOutputStream(fos);
                            dos.writeUTF(textArea.getText());
                            dos.close();
                            //byte[] outData = textArea.getText().getBytes();
                            //fos.write(outData);
                            //fos.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(TextEditerNotePad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    Platform.exit();
                }
            }
        });

        editMenuBar.setMnemonicParsing(false);
        editMenuBar.setText("Edit");

        cutMenu.setMnemonicParsing(false);
        cutMenu.setText("Cut");
        cutMenu.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        cutMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.cut();
            }
        });

        copyMenu.setMnemonicParsing(false);
        copyMenu.setText("Copy");
        copyMenu.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copyMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.copy();
            }
        });

        pasteMenu.setMnemonicParsing(false);
        pasteMenu.setText("Paste");
        pasteMenu.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        pasteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.paste();
            }
        });

        deleteMenu.setMnemonicParsing(false);
        deleteMenu.setText("Delete");
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pos = textArea.getCaretPosition();
                IndexRange selectedRange = textArea.getSelection();
                textArea.deleteText(selectedRange);
            }
        });

        selectAllMenu.setMnemonicParsing(false);
        selectAllMenu.setText("Select All");
        selectAllMenu.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        selectAllMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.selectAll();
            }
        });

        helpMenuBar.setMnemonicParsing(false);
        helpMenuBar.setText("Help");

        aboutMenu.setMnemonicParsing(false);
        aboutMenu.setText("About");
        setTop(menuBar);
        aboutMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog<String> dialog = new Dialog<>();
                DialogPane about = new DialogPane();

                String contentText = new String("This is the first version of the NotePad");
                dialog.getDialogPane().setContentText(contentText);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
            }
        });

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);
        setCenter(textArea);

        fileMenuBar.getItems().add(newMenu);
        fileMenuBar.getItems().add(openMenu);
        fileMenuBar.getItems().add(saveMenu);
        fileMenuBar.getItems().add(exitMenu);
        menuBar.getMenus().add(fileMenuBar);
        editMenuBar.getItems().add(cutMenu);
        editMenuBar.getItems().add(copyMenu);
        editMenuBar.getItems().add(pasteMenu);
        editMenuBar.getItems().add(deleteMenu);
        editMenuBar.getItems().add(selectAllMenu);
        menuBar.getMenus().add(editMenuBar);
        helpMenuBar.getItems().add(aboutMenu);
        menuBar.getMenus().add(helpMenuBar);

    }
}
