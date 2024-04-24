package com.example.sma_presentation.agents.vendeurs;
import com.example.sma_presentation.entities.Livre;
import jade.wrapper.StaleProxyException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class VendeurController implements Initializable {
    private VendeurGui vendeurGui;
    private  List<Livre> list_Livres=new ArrayList<>();
    public void setVendeurGui(VendeurGui vendeurGui) {
        this.vendeurGui = vendeurGui;
    }
    @FXML
    private TableColumn<Livre, String> c_id;
    @FXML
    private TableColumn<Livre, String> c_n_auteur;
    @FXML
    private TableColumn<Livre, String> c_n_livre;
    @FXML
    private TableColumn<Livre, Double> c_prix;
    @FXML
    private Button envoyer;
    @FXML
    private TextField n_auteur;
    @FXML
    private TextField n_livre;
    @FXML
    private TextField n_vendeur;
    @FXML
    private TextField p_livre;
    @FXML
    private TableView<Livre> view;
    private ObservableList<Livre> livres= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        c_n_livre.setCellValueFactory(new PropertyValueFactory<>("nomLivre"));
        c_n_auteur.setCellValueFactory(new PropertyValueFactory<>("nomAuteur"));
        c_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        view.setItems(livres);
    }
    public void send(ActionEvent actionEvent) throws StaleProxyException {
        if(vendeurGui.isVerification()){
            String nom=n_vendeur.getText();
            vendeurGui.startContinaire(nom);
            vendeurGui.setVerification(false);
        }
        Livre livre=new Livre(n_livre.getText(),n_auteur.getText(),Double.parseDouble(p_livre.getText()),n_vendeur.getText());
        Platform.runLater(() -> {
            livres.add(livre);
            list_Livres.add(livre);
            n_vendeur.setDisable(true);
            viderChamps();
            vendeurGui.setLivre(list_Livres);
        });
    }
    public void viderChamps(){
        n_auteur.setText("");
        n_livre.setText("");
        p_livre.setText("");
    }
}
