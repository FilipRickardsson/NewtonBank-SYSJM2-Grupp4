package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {
    
    /**
     *Returns a list of all customers name and social security number  
     * @return 
     */
    public List<String> getCustomers() {
        //returnerar en lista med bankens kunders namn och personnumer
        List<String> customerPresentation = new ArrayList();
        
        //check variable names
        for(int i = 0; i < customers.size(); i++) {
            customerPresentation.add(customers.get(i).getName() + " " + 
                    customers.get(i).getsSn);
        }
        
        return customerPresentation;
    }
    
    public boolean addCustomer(String name, long pNr) {
        //skapar en ny kund om det inte redan finns en med samma personummer, 
        //returnerar då true annars false
    }
    
    public List<String> getCustomer(long pNr) {
        //Returnerar en lista med information om kunden inklusive dess konton. 
        // Förslagsvis är index 0 till namn och personnummer och sedan följer 
        //konton.
    }
}
