package com.example.sma_presentation.agents.vendeurs;
import com.example.sma_presentation.HelloApplication;
import com.example.sma_presentation.entities.Livre;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class VendeurGui extends Application {
    public boolean isVerification() {
        return verification;
    }
    private List<Livre> Livre;

    public List<Livre> getLivre() {
        return Livre;
    }

    public void setLivre(List<com.example.sma_presentation.entities.Livre> livre) {
        Livre = livre;
    }
    public void setVerification(boolean verification) {
        this.verification = verification;
    }
    private boolean verification = true;
    private VendeurAgent vendeurAgent;
    public void setVendeurAgent(VendeurAgent vendeurAgent) {
        this.vendeurAgent = vendeurAgent;
    }
    public void startContinaire(String name) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent(name, "com.example.sma_presentation.agents.vendeurs.VendeurAgent", new Object[]{this});
        agentController.start();
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vendeur.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        VendeurController vendeurController=fxmlLoader.getController();
        vendeurController.setVendeurGui(this);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
        public static void main (String[]args){
            launch();
        }
    }
