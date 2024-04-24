package com.example.sma_presentation.agents.vendeurs;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
public class VendeurAgent extends Agent {
    protected VendeurGui vendeurGui;
    private int cfp=0;
    @Override
    protected void setup() {
        super.setup();
        if(getArguments().length==1){
            vendeurGui=(VendeurGui) getArguments()[0];
            vendeurGui.setVendeurAgent(this);
        }
        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription dfAgentDescription=new DFAgentDescription();
                dfAgentDescription.setName(getAID());
                ServiceDescription serviceDescription=new ServiceDescription();
                serviceDescription.setType("vente");
                serviceDescription.setName("livres");
                dfAgentDescription.addServices(serviceDescription);
                try {
                    DFService.register(myAgent,dfAgentDescription);
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
                if(aclMessage!=null) {
                    if(aclMessage.getPerformative()==ACLMessage.CFP) {
                         cfp=0;
                        System.out.println("CFP");
                        String nomLivre=aclMessage.getContent();
                        ACLMessage aclMessage1=aclMessage.createReply();
                        aclMessage1.clearAllReceiver();
                        aclMessage1.addReceiver(new AID("acheteur",AID.ISLOCALNAME));
                        aclMessage1.setPerformative(ACLMessage.PROPOSE);
                        vendeurGui.getLivre().forEach(livre -> {
                            if(livre.getNomLivre().equals(nomLivre)){
                                aclMessage1.setContent(livre.toString());
                                System.out.println(livre.toString());
                            }
                    });
                    send(aclMessage1);
                    }
                    else if (aclMessage.getPerformative() == ACLMessage.PROPAGATE && cfp<1 ){
                        System.out.println("PROPAGATE");
                        ACLMessage message=aclMessage.createReply();
                        message.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        message.setContent(aclMessage.getContent());
                        System.out.println("derniere requetes"+aclMessage.getContent());
                        send(message);
                        cfp++;

                    }else
                        System.out.println("blocker blocker");
                }
                else
                    block();
            }
        });
        addBehaviour(parallelBehaviour);
    }
}
