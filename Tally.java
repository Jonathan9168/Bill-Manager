import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

final public class Tally
{
    public static void tallySpend(Transaction[] transactions, int index, int AccNo) throws IOException
    {
        try
        {
            BufferedReader inputStream = new BufferedReader(new FileReader( AccNo + "Transactions.csv"));
            String line = inputStream.readLine();

            int spent = 0;

            while (line != null)
            {
                String[] split = line.split(",");
                spent = spent + Integer.parseInt(split[2]);
                line = inputStream.readLine();
            }

            System.out.println("\nYou have spent £" + spent);
            Menus.menu(transactions,index,AccNo);
        }
        catch (Exception noFile)
        {
            System.out.println("\nThere is no file to read transactions from. Add a transaction or start over.");
            Menus.check();
        }
    }
}
