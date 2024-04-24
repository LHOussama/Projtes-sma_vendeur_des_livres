package com.example.sma_presentation.agents.consomateur;
import com.example.sma_presentation.entities.Livre;
import jade.gui.GuiEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class CosomateurController implements Initializable {
    @FXML
    private TextField chercherLivre;
    private ConsomateurAgent consomateurAgent;
    @FXML
    private Button chercher;

    @FXML
    private TableColumn<Livre, String> clivre;

    @FXML
    private TableColumn<Livre,String> nauteur;

    @FXML
    private TableColumn<Livre, String> nlivre;

    @FXML
    private TableColumn<Livre, Double> prix;
    @FXML
    private TableView<Livre> view;
    @FXML
    private TableColumn<Livre,String> vendeur;
    private ObservableList<Livre>data= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clivre.setCellValueFactory(new PropertyValueFactory<>("id"));
        nlivre.setCellValueFactory(new PropertyValueFactory<>("nomLivre"));
        nauteur.setCellValueFactory(new PropertyValueFactory<>("nomAuteur"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        vendeur.setCellValueFactory(new PropertyValueFactory<>("vendeur"));
        view.setItems(data);
    }
    public void setConsomateurAgent(ConsomateurAgent consomateurAgent) {
        this.consomateurAgent = consomateurAgent;
    }

    public void acheter(ActionEvent actionEvent) {
        String nom_livre=chercherLivre.getText();
        GuiEvent guiEvent=new GuiEvent(this,1);
        guiEvent.addParameter(nom_livre);
        chercherLivre.setText("");
        consomateurAgent.onGuiEvent(guiEvent);
    }
    public void logMessage(Livre livre){
        Platform.runLater(()->{
            data.add(livre);
        });
    }
}
