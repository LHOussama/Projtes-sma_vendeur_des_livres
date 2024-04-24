package com.example.sma_presentation.continaires;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
public class mainContinaire {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        Profile profile=new ProfileImpl();
        profile.setParameter(Profile.GUI,"true");
        AgentContainer agentContainer=runtime.createMainContainer(profile);
        agentContainer.start();
    }
}
