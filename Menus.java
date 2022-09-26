package com.Jonathan;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

abstract public class Menus
{
    public static void check() throws IOException
    {
        String answer = Input.inputStrNL("Do you have an account?(Yes/No)").toLowerCase();
        Transaction[] transactions = {};
        int index = 0;

        if (answer.equals("yes"))
        {
            login(transactions,index);
        }
        else if (answer.equals("no"))
        {
            Account.createAccount();
        }
        else
        {
            System.out.println("\nPlease Type Yes/No\n");
            check();
        }
    }

    public static void login(Transaction [] transactions, int index) throws IOException
    {
        try
        {
            System.out.println("\t---LOGIN---");
            int AccNo = Input.inputInt("ACCOUNT NUMBER: ");
            int Pin = Input.inputInt("PIN: ");

            Account.verifyAccount(AccNo,Pin,transactions,index);
        }
        catch (Exception e)
        {
            System.out.println("\nNo Accounts Exist Yet...");
            check();
        }
    }

    public static void menu(Transaction[] transactions,int index, int AccNo)
    {
        try
        {
            String option = Input.inputStrNL("\n[A] Add Transaction \n[B] View Balance \n[C] View All Transactions \n[D] View Spend Total \n[E] Remove Transaction \n[F] Logout \n[Q] Quit").toUpperCase();

            switch (option) {
                case "A" -> {
                    String item = Input.inputStr("\nItem Name:");
                        int price = Input.inputInt("Item Price:");
                    LocalDate unDate = LocalDate.now();  //Get Date
                    DateTimeFormatter modDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Create Format Object
                    String date = unDate.format(modDate); //Store Formatted Date
                    Transaction t = new Transaction(item,price,date);
                    transactions = Transaction.transactionAppend(transactions,t);
                    Transaction.writeTransaction(transactions,index,AccNo);
                }
                case "B" -> Balance.readBalance(transactions,index,AccNo);
                case "C" -> Transaction.viewAllTransactions(transactions,index,AccNo);
                case "D" -> Tally.tallySpend(transactions,index,AccNo);
                case "E" -> Transaction.removeTransaction(transactions,index,AccNo);
                case "F" -> check();
                case "Q" -> Input.quit();
                default -> {
                    System.out.println("\nPlease Type A,B,C,D,E,F,Q");
                    menu(transactions,index,AccNo);
                }
            }
        }
        catch (Exception notInt)
        {
            System.out.println("\nPlease only enter integers for item price.");
            menu(transactions,index, AccNo);
        }
    }
}
