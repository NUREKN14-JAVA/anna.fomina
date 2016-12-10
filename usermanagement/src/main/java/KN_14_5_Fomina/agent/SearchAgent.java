package KN_14_5_Fomina.agent;

import java.util.Collection;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import KN_14_5_Fomina.db.DaoFactory;
import KN_14_5_Fomina.db.DatabaseException;

public class SearchAgent extends Agent {
    private static final long serialVersionUID = 1L;
    private AID[] aids;
    private SearchGui gui = null;

    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");
        gui = new SearchGui(this);
        gui.setVisible(true);
        
        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);
        try {
            DFService.register(this, description);
        } 
        catch (FIPAException e) {
            e.printStackTrace();
            
        }
        addBehaviour(new TickerBehaviour(this,6000) {

            private static final long serialVersionUID = 1L;

            protected void onTick() {
                DFAgentDescription agentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("searching");
                agentDescription.addServices(serviceDescription);
                try {
                    DFAgentDescription descriptions[] = DFService.search(myAgent, agentDescription);
                    aids = new AID[descriptions.length];
                    for (int i = 0; i < descriptions.length; i++) {
                        DFAgentDescription d = descriptions[i];
                        aids[i] = d.getName();
                    }
                } 
                catch (FIPAException e) {
                    e.printStackTrace();
                }
                
            }
            
        });
        addBehaviour(new RequestServer());
    }

    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        try {
            DFService.deregister(this);
        }
        catch (FIPAException e) {
            e.printStackTrace();
        }
        gui.setVisible(false);
        gui.dispose();
        super.takeDown();
        
    }
    
    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection<?> users = DaoFactory.getInstance().getUserDao().findAll(firstName, lastName);
            if (users.size() > 0) {
                showUsers(users);
            }
            else {
                addBehaviour(new SearchRequstBehaviour(aids, firstName, lastName));
            }
        }
        catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }
    
    protected void showUsers(Collection<?> users) {
        gui.addUsers(users);
    }
}
