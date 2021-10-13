package ua.com.alevel.separator;

import java.util.Arrays;
import java.util.Scanner;

public class Numbers {
    public void numbers () {
        int exit = 0;
       do {
            menu();
            Scanner buffer = new Scanner(System.in); //creation of new object 'buffer' of type "Scanner"
            String userInput = buffer.nextLine();//invoking input command & saving value to "String" variable 'userInput'
           switch (userInput) {
               case "Q", "q" -> exit = 1;
           }
            //System.out.println(userInput);
            char[] inputCharArray = userInput.toCharArray();//converting var 'userInput' to "char" type array 'inputCharArray'
            /*System.out.println(inputCharArray.length);
            for(int d=0;d<inputCharArray.length;d++)
            {
                System.out.println(inputCharArray[d]);
            }*/
                int[] transferArray = new int[inputCharArray.length];//creating int array for transfer
                for (int i = 0; i < inputCharArray.length; i++)//transferring "char" array 'inputCharArray' into "int" array 'transferArray'
                {
                    if (inputCharArray[i] <= '9' && inputCharArray[i] >= '0')//'9'&'0' here have their values from ASCII standard (57 & 48 respectively)
                    {
                        transferArray[i] = Character.getNumericValue(inputCharArray[i]) + 1;//decreasing values to its decimal range & racing by 1 to separate '0' from empty elements
                        //System.out.println(transferArray[i]);
                    } else {
                        transferArray[i] = 0; //any other symbols becomes an empty elements (equals '0')
                        //System.out.println(transferArray[i]);
                    }
                }
                int size = 1, finalElementValue = 0, coefficient, arraySum = 0; // initializing of variables 'size' (used for array size change),
                // 'finalElementValue' (for assigning value to array's element ),
                // 'coefficient' (for a bits` depth increment, when rebuilding whole numbers from array),
                // 'arraySum' (sum of array elements to check numbers presence).
                int[] reconstructedNumbersArray = new int[size];//array for reconstructed numbers
                for(int i = 0; i < transferArray.length; i++) {
                    //System.out.println(transferArray[i]);
                    arraySum += transferArray[i];
                    //System.out.println(arraySum);
                }
                //System.out.println(arraySum);
                if (arraySum == 0)//checking sum of 'transferArray' array elements
                {
                    System.out.println( System.lineSeparator() +
                                        "No numbers in input string" + System.lineSeparator());
                    menu();
                } else {
                    int[] reversedArray = new int[transferArray.length];    //array for extracting numbers from 'transferArray'
                    for (int i = 0; i < transferArray.length; i++) //reversing elements count to ease calculations
                    {
                        reversedArray[i] = transferArray[transferArray.length - i - 1];
                        // System.out.println(reversedArray[i]);
                    }

                    for (int i = 0, multiplier = 0; i < reversedArray.length; i++) //sum array elements into complete numbers
                    {
                        // System.out.println(reversedArray[i]); // Element to be processed
                        if (reversedArray[i] != 0) // statement to separate numbers form each other
                        {
                            ++multiplier;// multiplier for 'coefficient' variable
                            // System.out.println("'finalElementValue' before FOR "+finalElementValue+" 'reversedArray[i]' before FOR "+reversedArray[i]);
                            int j;//bad way to initialize loop var. 'cause of previously initialized 'coeficient'
                            for (j = 0, coefficient = 1; j < (multiplier - 1); j++) {
                                coefficient *= 10;
                            }
                            // System.out.println("after FOR (k)&(m) "+k+" "+m);
                            finalElementValue += ((reversedArray[i] - 1) * coefficient); //resulting number
                            // System.out.println("after FOR (finalElementValue) "+finalElementValue);

                        } else if (finalElementValue != 0) //check for empty elements
                        {
                            //System.out.println("'Else's' 'finalElementValue' "+finalElementValue);
                            reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++); // creating 'reconstructedNumbersArray' array by copying values from
                            // existing 'reconstructedNumbersArray' array (I assume, it will be deleted
                            // after this operation) and with size defined by
                            // incremented variable 'size'.
                            reconstructedNumbersArray[size - 2] = finalElementValue; // assigning value of variable 'finalElementValue' to an array element in position
                            // defined by subtraction of 2 (1 for count difference and 1 to leave
                            // last element of an array empty) from variable 'size'.
                            finalElementValue = 0;  // reset the value of the variable 'finalElementValue'.
                            multiplier = 0; // reset the value of the variable 'multiplier'.
                        }
                        if (reversedArray[i] == 1) // check for '0' digit case
                        {
                            if (i == reversedArray.length - 1) {
                                reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++);
                                // existing 'reconstructedNumbersArray' array (I assume, it will be deleted
                                // after this operation) and with size defined by
                                // incremented variable 'size'
                                reconstructedNumbersArray[size - 2] = finalElementValue; // assigning value of variable 'finalElementValue' to an array element in position
                                // defined by subtraction of 2 (1 for count difference and 1 to leave
                                // last element of an array empty) from variable 'size'.
                                finalElementValue = 0;  // reset userInput value of the variable 'finalElementValue'.
                                multiplier = 0; // reset userInput value of the variable 'multiplier'.
                            } else if (reversedArray[i+1] == 0)
                            {
                                reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++); // creating 'reconstructedNumbersArray' array by copying values from
                                // existing 'reconstructedNumbersArray' array (I assume, it will be deleted
                                // after this operation) and with size defined by
                                // incremented variable 'size'
                                reconstructedNumbersArray[size - 2] = finalElementValue; // assigning value of variable 'finalElementValue' to an array element in position
                                // defined by subtraction of 2 (1 for count difference and 1 to leave
                                // last element of an array empty) from variable 'size'.
                                finalElementValue = 0;  // reset userInput value of the variable 'finalElementValue'.
                                multiplier = 0; // reset userInput value of the variable 'multiplier'.
                        }
                        }
                    }

                }
                if (finalElementValue != 0) //for cases when string starting up with number
                {
                    reconstructedNumbersArray = Arrays.copyOf(reconstructedNumbersArray, size++);
                    reconstructedNumbersArray[size - 2] = finalElementValue;
                }
                //System.out.println("Last one 'finalElementValue' "+ finalElementValue);
                //System.out.println("Last one element "+ reconstructedNumbersArray[reconstructedNumbersArray.length-1]);
                //System.out.println("First one element "+ reconstructedNumbersArray[0]);
                int[] finalArray = new int[reconstructedNumbersArray.length]; //final array
                for (int i = 0; i < reconstructedNumbersArray.length; i++) //reverting numbers back to initial order
                {
                    finalArray[i] = reconstructedNumbersArray[reconstructedNumbersArray.length - i - 1];
                }
                int finalArraySum = 0;
                for (int i = 0; i < finalArray.length; i++)
                {
                    finalArraySum += finalArray[i];
                }
                System.out.println( System.lineSeparator() + "Sum of numbers equals to: " + finalArraySum);
        } while (exit != 1);
    }
    private static void menu() {
        System.out.print( System.lineSeparator() +
                            "Make your input and press \"Enter\". Input \"Q\" (or \"q\") to exit." +
                            System.lineSeparator() + "Your input: " );
    }
}