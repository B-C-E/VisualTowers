package towers;

import java.util.Scanner;


//This is a utility class made by B-C-E
//(This sort of GetInput thing comes up a lot)
//Gets input from the user, and forces them to retype their input if it is bad


//@TODO - Add a list of all the methods
public class GetInput
{
    //gets the user to input yes or no
    //returns one of the characters (y,n)
    public static char getYN()
    {
        Scanner scn_kkb = new Scanner(System.in);//make a scanner
        while (true)
        {
            String input = scn_kkb.nextLine() + " ";
            if (Character.toLowerCase(input.charAt(0)) == 'y')//if they typed yes
            {
                return 'y';
            } else if (Character.toLowerCase(input.charAt(0)) == 'n')//if they typed no
            {
                return 'n';
            } else//if they gave bad input
            {
                System.out.println("Please type Y or N.");
            }

            //No good input? Loop!
        }//end of loop
    }//end of getYN

    //from a range of longs [inclusive], select one
    public static long getRangeLong(long min, long max)
    {
        Scanner scn_kkb = new Scanner(System.in);
        while (true)
        {
            String input = scn_kkb.nextLine() + " ";
            String goodInput = "";

            //if negative
            if (input.charAt(0) == '-')
            {
                goodInput += "-";
            }

            for (int i = 0; i < input.length(); i++)//go through each character of the input
            {
                if (!(input.charAt(i) < 48 || input.charAt(i) > 57))//if the character is not a number, don't add it
                {
                    //otherwise, do add it
                    goodInput += input.charAt(i);
                }

            }//end of going through each character of input and removing non numbers


            if (goodInput != "")
            {
                goodInput = shortenString(goodInput, (Long.MAX_VALUE + "").length() - 1);
                long numb = Long.parseLong(goodInput);
                if (numb >= min && numb <= max)
                {
                    return numb;
                }
                //No good input? Loop!
            }
            System.out.println("Please type a number between " + min + " and " + max);
        }//end of loop
    }//end of getRangeLong


    //gets a char
    public static char getChar()
    {
        Scanner scn_kkb = new Scanner(System.in);
        return (scn_kkb.nextLine() + " ").toLowerCase().charAt(0);
    }//end of getChar

    //gets a char from a --> z
    public static char getLetter()
    {
        Scanner scn_kkb = new Scanner(System.in);
        while (true)
        {
            char ch = (scn_kkb.nextLine() + " ").toLowerCase().charAt(0);
            if (ch >= 97 && ch <= 122)
            {
                return ch;
            }
            System.out.println("Please type a letter.");
        }

    }//end of getChar


    //from a range of ints [inclusive], select one
    public static int getRangeInt(int min, int max)
    {
        Scanner scn_kkb = new Scanner(System.in);
        while (true)
        {
            String input = scn_kkb.nextLine() + " ";
            String goodInput = "";

            //if negative
            if (input.charAt(0) == '-')
            {
                goodInput += "-";
            }

            for (int i = 0; i < input.length(); i++)//go through each character of the input
            {
                if (!(input.charAt(i) < 48 || input.charAt(i) > 57))//if the character is not a number, don't add it
                {
                    //if it is, do
                    goodInput += input.charAt(i);
                }

            }//end of going through each character of input and removing non numbers


            if (goodInput != "")
            {
                goodInput = shortenString(goodInput, (Integer.MAX_VALUE + "").length() - 1);
                int numb = Integer.parseInt(goodInput);
                if (numb >= min && numb <= max)
                {
                    return numb;
                }
                //No good input? Loop!
            }
            System.out.println("Please type a number between " + min + " and " + max);
        }//end of loop
    }//end of getRangeInt

    //from a range of doubles [inclusive], select one
    public static double getRangeDouble(double min, double max)
    {
        Scanner scn_kkb = new Scanner(System.in);
        while (true)
        {
            String input = scn_kkb.nextLine() + " ";
            String goodInput = "";

            //if negative
            if (input.charAt(0) == '-')
            {
                goodInput += "-";
            }

            int periodCount = 0;

            for (int i = 0; i < input.length(); i++)//go through each character of the input
            {
                if (!(input.charAt(i) < 48 || input.charAt(i) > 57))
                //if the character is not a number, don't add it
                {
                    //if it is, do
                    goodInput += input.charAt(i);
                }

                //if it's the first found period
                if ((periodCount == 0 && input.charAt(i) == '.'))
                {
                    goodInput += ".";
                    periodCount++;
                }

            }//end of going through each character of input and removing non numbers


            if (goodInput != "")
            {
                goodInput = shortenString(goodInput, (Double.MAX_VALUE + "").length() - 1);
                double numb = Double.parseDouble(goodInput);
                if (numb >= min && numb <= max)
                {
                    return numb;
                }
                //No good input? Loop!
            }
            System.out.println("Please type a number between " + min + " and " + max);
        }//end of loop
    }//end of getRangeDouble


    //from a selection of char options, get the user to choose one
    //if you run this with only one option, you will get an error
    public static char get(char... options)
    {
        Scanner scn_kkb = new Scanner(System.in);//make a scanner
        while (true)
        {
            String input = scn_kkb.nextLine();//get input from user
            input = input.toLowerCase();//convert to lower case

            //for all options, see if the user inputed that option
            for (int i = 0; i < options.length; i++)
            {
                if (input.charAt(0) == options[i])
                {
                    return options[i];
                }
            }//end of loop through all options

            //explain to the user what the program wants
            //Ex: Please type [a] or [b] or [c] or [d]
            System.out.print("Please type [" + options[0]);
            for (int i = 1; i < options.length - 1; i++)
            {
                System.out.print("], [" + options[i]);
            }
            System.out.println("] or [" + options[options.length - 1] + "]");

            //No good input? Loop!
        }//end of loop
    }//end of get


    //gets the user to input a string with only lower case letters (and no numbers,spaces, etc)
    //and minimum size minSize
    public static String getCleanString(int minSize)
    {

        Scanner scn_kkb = new Scanner(System.in);//scanner
        while (true)
        {
            String input = scn_kkb.nextLine().toLowerCase();//get an input string from user
            String toReturn = "";//this will be returned at the end
            for (int i = 0; i < input.length(); i++)//go through each character of the input
            {
                if (input.charAt(i) >= 97 && input.charAt(i) <= 122)//if the character is a letter, add it to the return
                {                                                   // string
                    toReturn += input.charAt(i);

                }
            }//end of going through each character of input

            if (toReturn.length() >= minSize)//if they gave some sort of good input
            {
                return toReturn;
            }//end of if they gave some sort of good input


            //explain what they did wrong
            if (toReturn.length() == 1)
            {
                System.out.println("Please input a string containing at least " + minSize + " letters.");
            } else
            {
                System.out.println("Please input a string containing only letters.");
            }
            //No good input? Loop!
        }//end of loop
    }//end of getCleanString

    //gets a string from the user
    public static String getString()
    {
        Scanner scn_kkb = new Scanner(System.in);
        return scn_kkb.nextLine();
    }//end of getString


    //#####################################################//
    //                                                    #//
    //                                                    #//
    //                  private methods                   #//
    //                                                    #//
    //                                                    #//
    //                                                    #//
    //#####################################################//

    //uses substring
    private static String shortenString(String str, int targetLength)
    {
        if (str.length() > targetLength)
        {
            return str.substring(0, targetLength);
        }
        return str;
    }//end of shortenString

    //gets a string of length >=1. Automatically adds a space.
    private static String getStringOnePlus()
    {
        Scanner scn_kkb = new Scanner(System.in);
        return scn_kkb.nextLine() + " ";
    }//end of getStringOnePlus

}//end of getInput
