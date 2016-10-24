/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Customer {
    private String name;
    private long pNr;
    private ArrayList<SavingAccount> list;
            


    
    public Customer(String name, long pNr, ArrayList<SavingAccount> list)
    {
        this.name = name;
        this.pNr = pNr;
        list = new ArrayList();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getpNr()
    {
        return pNr;
    }

    public void setpNr(long pNr)
    {
        this.pNr = pNr;
    }

    public ArrayList<SavingAccount> getList()
    {
        return list;
    }

    public void setList(ArrayList<SavingAccount> list)
    {
        this.list = list;
    }


    
}

    

