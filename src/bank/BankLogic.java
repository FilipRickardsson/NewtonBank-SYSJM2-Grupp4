package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {
    
    /**
     *Returns a list of all customers name and social security number  
     * @return 
     */
    public List<String> getCustomers() {
        List<String> customerPresentation = new ArrayList();
        
        //check variable names
        for(int i = 0; i < customers.size(); i++) {
            customerPresentation.add(customers.get(i).getName() + " " + 
                    customers.get(i).getSsn);
        }
        
        return customerPresentation;
    }
    
    public boolean addCustomer(String name, long ssn) {
        //check variable names 
        for(int i = 0; i < customers.size(); i++) {
            if(ssn == customers.get(i).getSsn()) {
                return false;
            }
        }
        
        //check constructor in Customer-klass
        customers.add(new Customer(name, ssn));
        return true;
    }
    
    public List<String> getCustomer(long pNr) {
        //Returnerar en lista med information om kunden inklusive dess konton. 
        // Förslagsvis är index 0 till namn och personnummer och sedan följer 
        //konton.
    }
}
