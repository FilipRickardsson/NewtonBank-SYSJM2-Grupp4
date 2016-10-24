package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {
    
    /**
     *Returns a list of all customers name and social security number.  
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
    
    /**
     * Creates a new customer and adds it to the Customer-list. Returns true if
     * created and false if not.
     * @param name
     * @param ssn
     * @return 
     */
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
    
    /**
     * Returns a list containing information about the customer matching the
     * given social security number.
     * @param ssn
     * @return 
     */
    public List<String> getCustomer(long ssn) {
        ArrayList<String> customerInformation = new ArrayList();
        
        //check variable names 
        for(int i = 0; i < customers.size(); i++) {
            if(ssn == customers.get(i).getSsn()) {
                customerInformation.add(customers.get(i).getName() + " " + 
                    customers.get(i).getSsn);
                for(int j = 0; j < customers.get(i).getAccounts().size(); j++) {
                    customerInformation.add(customers.get(i).getAccounts()
                            .get(j).toString);
                }
            }
        }
        
        return customerInformation;
    }
}
