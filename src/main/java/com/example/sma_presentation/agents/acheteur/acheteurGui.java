package com.example.sma_presentation.agents.acheteur;
import com.example.sma_presentation.entities.Livre;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;
public class acheteurGui extends GuiAgent {
    List<String>livres;
    private AcheteurContinaire acheteurContinaire;
    List<String> vendeurs=new ArrayList<>();
    private int compteur;
    private List<String> synchronisation;
    private int req=0;
    private Livre livre_min;
    @Override
    protected void setup() {
        if(getArguments().length==1){
            acheteurContinaire=(AcheteurContinaire) getArguments()[0];
        }
        super.setup();
        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,5000) {
            @Override
            protected void onTick() {
                DFAgentDescription dfAgentDescription=new DFAgentDescription();
                ServiceDescription serviceDescription=new ServiceDescription();
                serviceDescription.setType("vente");
                dfAgentDescription.addServices(serviceDescription);
                try {
                    DFAgentDescription [] dfAgentDescriptions= DFService.search(this.getAgent(),dfAgentDescription);
                    for(DFAgentDescription dfAgentDescription1:dfAgentDescriptions){
                        if(vendeurs.isEmpty() || !vendeurs.contains(dfAgentDescription1.getName().getLocalName())){
                            vendeurs.add(dfAgentDescription1.getName().getLocalName());
                            acheteurContinaire.acheteurController.addmessage(dfAgentDescription1.getName().getLocalName());
                            System.out.println(vendeurs.size());
                        }
                    }
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {
                System.out.println("cyclique");
                ACLMessage aclMessage=receive();
                if(aclMessage!=null ) {
                    if (aclMessage.getPerformative() == ACLMessage.REQUEST) {
                        synchronisation=new ArrayList<>();
                        livres=new ArrayList<>();
                        req=0;
                        compteur=0;
                         livre_min=null;
                        System.out.println("request");
                        ACLMessage aclMessage1 = aclMessage.createReply();
                        aclMessage1.setPerformative(ACLMessage.CFP);
                        aclMessage1.setContent(aclMessage.getContent());
                        for (String nom : vendeurs)
                            aclMessage1.addReceiver(new AID(nom, AID.ISLOCALNAME));
                        send(aclMessage1);
                    }
                    else if (aclMessage.getPerformative() == ACLMessage.PROPOSE ) {
                        System.out.println("ppopopopo"+aclMessage.getContent());
                        livres.add(aclMessage.getContent());
                        System.out.println("propose");
                        compteur++;
                        if(compteur==vendeurs.size()){
                            for (String livreString : livres) {
                                if (livreString != null) {
                                    Livre livre = Livre.fromString(livreString);
                                    System.out.println("entree des livres" + livreString);
                                    if (livre_min == null || livre.getPrix() < livre_min.getPrix()) {
                                        livre_min = livre;
                                    }
                                }
                            }
                            if(livre_min!=null || synchronisation.isEmpty()){
                            String destination=livre_min.getVendeur();
                            System.out.println("vendeu vendeur"+destination);
                                synchronisation.forEach(s -> {
                                System.out.println("verfifier les destination" +s);
                            });
                            if(!synchronisation.contains(destination)) {
                                System.out.println("creer propagate");
                                System.out.println("destination a enviyee"+destination);
                                ACLMessage message = aclMessage.createReply();
                                message.clearAllReceiver();
                                message.setPerformative(ACLMessage.PROPAGATE);
                                message.addReceiver(new AID(destination, AID.ISLOCALNAME));
                                message.setContent(livre_min.getNomLivre());
                                send(message);
                                synchronisation.add(destination);
                            }
                            }
                        }
                    }
                    else if(aclMessage.getPerformative()==ACLMessage.ACCEPT_PROPOSAL && req<1){
                        req++;
                        System.out.println("accepte");;
                        ACLMessage message=aclMessage.createReply();
                        message.setPerformative(ACLMessage.INFORM);
                        message.setContent(livre_min.toString());
                        System.out.println("derniere requete"+message.getContent());
                        message.addReceiver(new AID("Consomateur",AID.ISLOCALNAME));
                        send(message);
                    }
                }
                else
                    block();
            }
        });
        addBehaviour(parallelBehaviour);
    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
