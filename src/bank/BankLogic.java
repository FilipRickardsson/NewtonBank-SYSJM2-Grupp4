package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {

    List<Customer> costumers;
    private int accountNbrCounter;

    public BankLogic() {
        costumers = new ArrayList<Customer>;
        accountNbr = 1001;
    }

    public boolean changeCustomer(String name, long pNr) {
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getPNr == pNr) {
                costumers.get(i).setName(name);
                return true;
            }
        }
        return false;
    }

    // TODO Add saldo + interest
    public List<String> removeCustomer(long pNr) {
        List<String> info = new ArrayList();
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getPNr == pNr) {
                for (int j = 0; j < costumers.; j++) {
                    costumer(i).get
                }
            }
        }
        
    }

    public int addSavingsAccount(long pNr) {
        int currentAccountNbr = -1;
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getPNr == pNr) {
                costumers.get(i).getAccounts.add(new SavingsAccount(/*Insert inparameters here*/));
                currentAccountNbr = accountNbrCounter;
                accountNbrCounter++;
            }
        }
        return currentAccountNbr;
    }

}
