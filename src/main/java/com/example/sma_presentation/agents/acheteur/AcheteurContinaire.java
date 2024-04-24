package com.example.sma_presentation.agents.acheteur;
import com.example.sma_presentation.HelloApplication;
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
public class AcheteurContinaire extends Application {
    acheteurController acheteurController;
    public acheteurController getAcheteurController() {
        return acheteurController;
    }
    public void setAcheteurController(acheteurController acheteurController) {
        this.acheteurController = acheteurController;
    }

    public  void startContinaire() throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        Profile profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer= runtime.createAgentContainer(profile);
        AgentController agentController=agentContainer.createNewAgent("acheteur","com.example.sma_presentation.agents.acheteur.acheteurGui",new Object[]{this});
        agentController.start();
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("achetteur.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        acheteurController=fxmlLoader.getController();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        startContinaire();

    }

    public static void main(String[] args) {
        launch();
    }
}
