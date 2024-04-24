package com.example.sma_presentation.agents.consomateur;
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
public class ConsomateurContinaire extends Application {
    private CosomateurController cons;

    public CosomateurController getCons() {
        return cons;
    }

    public void setCons(CosomateurController cons) {
        this.cons = cons;
    }

    public ConsomateurAgent getConsomateurAgent() {
        return consomateurAgent;
    }

    private ConsomateurAgent consomateurAgent;
    public void setConsomateurAgent(ConsomateurAgent consomateurAgent) {
        this.consomateurAgent = consomateurAgent;
    }
    public void startContinaire() throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        Profile profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer= runtime.createAgentContainer(profile);
        AgentController agentController=agentContainer.createNewAgent("Consomateur","com.example.sma_presentation.agents.consomateur.ConsomateurAgent",new Object[]{this});
        agentController.start();
    }
    @Override
    public void start(Stage stage) throws Exception {
        startContinaire();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Consomateur.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        cons=(CosomateurController)fxmlLoader.getController();
        cons.setConsomateurAgent(this.consomateurAgent);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
