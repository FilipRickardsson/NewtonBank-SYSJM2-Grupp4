package bank;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {

    List<Customer> costumers;
    private int accountNbrCounter;

    public BankLogic() {
        costumers = new ArrayList();
        accountNbrCounter = 1001;
    }

    public boolean changeCustomer(String name, long ssn) {
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getSsn == ssn) {
                costumers.get(i).setName(name);
                return true;
            }
        }
        return false;
    }

    // TODO Add saldo + interest in info
    public List<String> removeCustomer(long ssn) {
        List<String> info = new ArrayList();
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getSsn == ssn) {
                ArrayList accounts = costumers.get(i).getAccounts();
                for (int j = 0; j < accounts.size(); j++) {
                    info.add(accounts.get(j).toString()); // Change/Add here
                }
            }
        }
        return info;
    }

    public int addSavingsAccount(long ssn) {
        int accountNbr = -1;
        for (int i = 0; i < costumers.size(); i++) {
            if (costumers.get(i).getSsn == ssn) {
                accountNbr = accountNbrCounter;
                costumers.get(i).getAccounts.add(new SavingsAccount(accountNbr));
                accountNbrCounter++;
            }
        }
        return accountNbr;
    }

}
