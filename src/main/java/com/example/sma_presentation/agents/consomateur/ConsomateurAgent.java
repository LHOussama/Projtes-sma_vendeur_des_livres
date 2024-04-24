package com.example.sma_presentation.agents.consomateur;
import com.example.sma_presentation.entities.Livre;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
public class ConsomateurAgent extends GuiAgent {
    private ConsomateurContinaire consomateurContinaire;

    @Override
    protected void setup() {
        consomateurContinaire=(ConsomateurContinaire) getArguments()[0];
        consomateurContinaire.setConsomateurAgent(this);
        super.setup();
        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null ){
                    if(message.getPerformative()==ACLMessage.INFORM){
                        System.out.println(message.getContent());
                        Livre livre= Livre.fromString(message.getContent());
                        livre=new Livre(livre.getId(), livre.getNomLivre(), livre.getNomAuteur(), livre.getPrix(), livre.getVendeur());
                        consomateurContinaire.getCons().logMessage(livre);
                    }
                }else{
                    block();
                }
            }
        });
        addBehaviour(parallelBehaviour);
    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        if(guiEvent.getType()==1){
           String message=guiEvent.getParameter(0).toString();
            ACLMessage aclMessage=new ACLMessage();
            aclMessage.setPerformative(ACLMessage.REQUEST);
            aclMessage.setContent(message);
            aclMessage.addReceiver(new AID("Acheteur",AID.ISLOCALNAME));
            send(aclMessage);
        }
    }
}
