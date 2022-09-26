package com.Jonathan;

import java.io.*;

abstract public class Account
{
    public static void verifyAccount(int AccNo , int Pin, Transaction[] transactions, int index) throws IOException
    {
        BufferedReader inputStream = new BufferedReader(new FileReader("Accounts.txt"));

        String line = inputStream.readLine();
        int found = 0;

        while (line != null)
        {
            String[] TEMP = line.split(",");

            if(AccNo == Integer.parseInt(TEMP[0]) && Pin == Integer.parseInt(TEMP[1]))
            {
                found += 1;
            }

            line = inputStream.readLine();
        }

        if (found == 0)
        {
            System.out.println("\nIncorrect Credentials!\n");
            Menus.login(transactions,index);
        }
        else
        {
            Menus.menu(transactions,index, AccNo);
        }
    }

    public static void createAccount() throws IOException
    {
        System.out.println("\n\t---ACCOUNT CREATION---");

        int AccNo = Input.inputInt("\nENTER AN ACCOUNT NUMBER: ");
        int Pin = Input.inputInt("ENTER A PIN: ");
        int Balance = Input.inputInt("STARTING BALANCE: ");

        PrintWriter outputStream = new PrintWriter(new FileWriter("Accounts.txt",true));

        outputStream.println(AccNo + "," + Pin + "," + Balance);
        outputStream.close();

        System.out.println("\nACCOUNT CREATED!");
        Menus.check();
    }
}
