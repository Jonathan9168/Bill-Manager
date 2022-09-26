package com.Jonathan;

import java.io.*;
import java.util.Arrays;

public class Transaction
{
    /*What a transaction object can hold*/
    private final String item;
    private final int price;
    private final String date;

    public Transaction(String item, int price, String date)
    {
        this.item = item;
        this.price = price;
        this.date = date;
    }

    /*Reading of current transactions from csv file*/
    public static void viewAllTransactions(Transaction[] transactions, int index, int AccNo) throws IOException {
        try
        {
            BufferedReader inputStream = new BufferedReader(new FileReader( AccNo +"Transactions.csv"));
            String item = inputStream.readLine();

            System.out.print("\n");
            System.out.println("\t---TRANSACTIONS---");

            while (item != null)
            {
                System.out.println(item);
                item = inputStream .readLine();
            }

            inputStream.close();

            Menus.menu(transactions,index, AccNo);
        }
        catch (Exception noFile)
        {
            System.out.println("\nThere is no file to write to. I suspect that you've never run this program before.");
            Menus.check();
        }
    }

    /*Add a new transaction to current sessions array of transactions*/
    public static Transaction[] transactionAppend(Transaction[] transactions, Transaction t)
    {
        transactions = Arrays.copyOf(transactions, transactions.length + 1);
        transactions[transactions.length - 1] = t;

        return transactions;
    }

    /*Store a transaction to transaction file.True passed as argument to not overwrite previous transactions.*/
    public static void writeTransaction(Transaction[] transaction, int index, int AccNo) throws IOException
    {
        PrintWriter outputStream = new PrintWriter(new FileWriter(AccNo + "Transactions.csv", true));

        for (int i = index; i <= transaction.length - 1; i++)
        {
            outputStream.println(getDate(transaction[i]) + "," + getItem(transaction[i]) + "," + getPrice(transaction[i]));
        }

        outputStream.close();

        Balance.updateBalance(transaction,index,AccNo);
    }

    public static void removeTransaction(Transaction[] transactions,int index, int AccNo) throws IOException
    {
        BufferedReader inputStream = new BufferedReader(new FileReader(AccNo + "Transactions.csv"));
        String line = inputStream.readLine();

        BufferedReader inputStream1 = new BufferedReader(new FileReader("Accounts.txt"));
        String read = inputStream1.readLine();

        FileWriter fw = new FileWriter(AccNo + "temp.csv",true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        File old = new File(AccNo + "Transactions.csv");
        File updated = new File(AccNo + "temp.csv");

        String search = Input.inputStr("What is the name of the item you want to delete?: ");

        int found = 0;
        int balance = 0;
        String oldLine = null;
        String newLine = null;

        while (read != null)
        {
            String[] TEMP = read.split(",");

            if(AccNo == Integer.parseInt(TEMP[0]))
            {
                balance = Integer.parseInt(TEMP[2]);
                oldLine = read;
                newLine = read;
            }
            read = inputStream1.readLine();
        }

        while(line != null)
        {
            if (line.contains(search))
            {
                String[] arr = line.split(",");
                balance = balance + Integer.parseInt(arr[2]);

                assert newLine != null;
                String[] arr1 = newLine.split(",");
                arr[2] = String.valueOf(balance);

                newLine = arr1[0] + "," + arr1[1] + "," + arr[2];
                Balance.replaceLine(oldLine,newLine);

                found +=1;
            }
            else
                {
                    pw.println(line);
            }
            line = inputStream.readLine();
        }

        pw.flush();
        pw.close();
        bw.close();
        fw.close();
        inputStream.close();
        inputStream1.close();

        if (old.delete())
        {
            File temp = new File(AccNo + "Transactions.csv");
            if (found == 0)
            {
                if(updated.renameTo(temp))
                {
                    System.out.println("\nThat item doesn't exist...");
                }
            }
            else if(found > 0)
            {
                if(updated.renameTo(temp))
                {
                    System.out.println("\nRemoval Successful.");
                }
            }
        }
        else
        {
            old.delete();
            System.out.println("\nI don't think you have made any transactions...");
        }
        Menus.menu(transactions,index,AccNo);
    }

    public static String getItem(Transaction t) {return t.item;}
    public static int getPrice(Transaction t) {return t.price;}
    public static String getDate(Transaction t) {return t.date;}
}
