/*package com.example.sma_presentation.agents.acheteur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
public class acheteurController implements Initializable {
        @FXML
        private TableColumn<String,String> c_vendeurs;
        private ObservableList<String> data= FXCollections.observableArrayList();
        @FXML
        private TableView<String> view;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c_vendeurs.setCellValueFactory(new PropertyValueFactory<>(""));
        data.add("tester");
        view.setItems(data);
    }
    public void addmessage(String message){
        System.out.println(message);
        System.out.println("render");
        Platform.runLater(() -> {
            data.add(message);
        });
    }

}*/
package com.example.sma_presentation.agents.acheteur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class acheteurController implements Initializable {
    @FXML
    private ListView<String> vendeurs;
    private ObservableList<String> data= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vendeurs.setItems(data);
    }
    public void addmessage(String message){
        Platform.runLater(() -> {
            data.add(message);
        });
    }
}
