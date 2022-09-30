import java.io.*;
import java.util.Scanner;

abstract public class Balance
{
    public static void updateBalance(Transaction[] transactions, int index, int AccNo) throws IOException
    {
        try
        {
            BufferedReader inputStream = new BufferedReader(new FileReader( "Accounts.txt"));
            String line = inputStream.readLine();

            for (int i = index; i <= transactions.length-1; i++)
            {
                while (line != null)
                {
                    String[] TEMP = line.split(",");

                    if(AccNo == Integer.parseInt(TEMP[0]))
                    {
                        int bal = Integer.parseInt(TEMP[2]);
                        bal = bal - Transaction.getPrice(transactions[i]);

                        TEMP[2] = String.valueOf(bal);
                        String newline = TEMP[0] + "," + TEMP[1] + "," + TEMP[2];

                        replaceLine(line,newline);
                    }
                    line = inputStream.readLine();
                }
            }

            index += 1;
            Menus.menu(transactions,index,AccNo);

        }
        catch (Exception noFile)
        {
            System.out.println("\nThere is no file to write to. You haven't run this program before or you haven't added a transaction yet.");
            Menus.check();
        }
    }

    public static void replaceLine(String oldLine, String newline) throws IOException
    {
        Scanner sc = new Scanner(new File("Accounts.txt"));
        //instantiating the StringBuffer class
        StringBuilder buffer = new StringBuilder();
        //Reading lines of the file and appending them to StringBuffer
        while (sc.hasNextLine())
        {
            buffer.append(sc.nextLine()).append(System.lineSeparator());
        }

        String fileContents = buffer.toString();
        //closing the Scanner object
        sc.close();

        //Replacing the old line with new line
        fileContents = fileContents.replaceAll(oldLine, newline);
        //instantiating the FileWriter class
        FileWriter writer = new FileWriter("Accounts.txt");
        writer.append(fileContents);
        writer.flush();
    }

    public static void readBalance(Transaction[] transactions, int index, int AccNo) throws IOException
    {
        try
        {
            BufferedReader inputStream = new BufferedReader(new FileReader( "Accounts.txt"));
            String read = inputStream.readLine();

            while (read != null)
            {
                String[] TEMP = read.split(",");

                if(AccNo == Integer.parseInt(TEMP[0]))
                {
                    int bal = Integer.parseInt(TEMP[2]);

                    if (bal >= 0)
                    {
                        System.out.println("\nYour current balance is: £" + bal);
                    }
                    else
                    {
                        System.out.println("\nYour current balance is: £" + bal + " [OVERDRAFT]");
                    }
                }
                read = inputStream.readLine();
            }
            inputStream.close();
            Menus.menu(transactions,index,AccNo);
        }
        catch (Exception noFile)
        {
            System.out.println("\nThere's no file holding your current balance. You haven't run this program before or your file is missing.");
            Menus.check();
        }
    }
}
