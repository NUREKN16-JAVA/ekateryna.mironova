package ua.nure.kn.mironova.usermanagement.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn.mironova.usermanagement.User;
import ua.nure.kn.mironova.usermanagement.db.DAOFactory;
import ua.nure.kn.mironova.usermanagement.db.DatabaseException;

public class RequestServer extends CyclicBehaviour {

	@Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else {
                Collection users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        }else{
            block();
        }
    }
	
	 private Collection parseMessage(ACLMessage message) {
	        Collection users = new LinkedList();

	        String content = message.getContent();
	        if(content != null){
	            StringTokenizer tokenizer1 = new StringTokenizer(content, "; ");
	            while (tokenizer1.hasMoreTokens()){
	                String userInfo = tokenizer1.nextToken();
	                StringTokenizer tokenizer2 = new StringTokenizer(userInfo, ", ");
	                String id = tokenizer2.nextToken();
	                String firstName = tokenizer2.nextToken();
	                String lastName = tokenizer2.nextToken();
	                users.add(new User(new Long(id), firstName, lastName, null));
	            }
	        }
	        return users;
	    }

	    private ACLMessage createReply(ACLMessage message) {
	        ACLMessage reply = message.createReply();

	        String content = message.getContent();
	        StringTokenizer tokenizer = new StringTokenizer(content, ", ");
	        if(tokenizer.countTokens() == 2) {
	            String firstName = tokenizer.nextToken();
	            String lastName = tokenizer.nextToken();
	            Collection users = null;
	            try{
	                users = DAOFactory.getInstance().getUserDAO().find(firstName, lastName);
	            }catch (DatabaseException e){
	                e.printStackTrace();
	                users = new ArrayList(0);
	            }

	            StringBuffer buffer = new StringBuffer();
	            for(Iterator iterator = users.iterator(); iterator.hasNext();){
	                User user =(User) iterator.next();
	                buffer.append(user.getId()).append(", ");
	                buffer.append(user.getFirstName()).append(", ");
	                buffer.append(user.getLastName()).append(", ");
	            }
	            reply.setContent(buffer.toString());
	        }
	        return reply;
	    }

}
