package bank;

public class BankLogic {
    
    public int addCreditAccount(long pNr){
        int accountNumber = 1001;
        
        for (int i = 0; i<Customer.size(); i++)
        {
            if (pNr == customer.get[i].get(pNr)){
        CreditAccount ca = new CreditAccount(pNR);
        
        accountNumber++;
        break;
            }
            else
                accountNumber = -1;
                    }
                return accountNumber;
    }
    public arrayList<transactions>getTransactions(long pNr, int accountID)
}
