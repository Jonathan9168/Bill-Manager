package com.Jonathan;

import java.util.Scanner;

final public class Input
{
    public static int inputInt(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);

        return Integer.parseInt(scanner.nextLine());
    }

    public static String inputStr(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);

        return scanner.nextLine();
    }

    public static String inputStrNL(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);

        return scanner.nextLine();
    }

    public static void quit(){System.exit(0);}

}
