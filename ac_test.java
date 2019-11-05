import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
public class ac_test {

    public static DLB readDictionary() throws FileNotFoundException, IOException {
        File dictionary = new File ("dictionary.txt");
        if (!dictionary.exists()){
            System.out.println("Dictionary does not exist. Please provide a dictionary text file.");
            System.exit(0);
        }
        DLB database = new DLB();
        Scanner reader = new Scanner(dictionary);
        do {
            if (database.add(reader.nextLine()));
        //        System.out.println("Successfully added.");
        //   else
        //        System.out.println("Already in dictionary.");
        } while (reader.hasNextLine());
        reader.close();
        return database;
    }

    public static DLB readHistory() throws FileNotFoundException, IOException {
        File userHistory = new File("user-history.txt");
        if (!userHistory.exists()){
            System.out.println("User history file not found. Created new blank history text file.");
            userHistory.createNewFile();
        }
        Scanner reader = new Scanner(userHistory);
        DLB history = new DLB();
        while (reader.hasNextLine()){
            if (history.add(reader.nextLine()));
        //        System.out.println("Successfully added.");
        //   else
        //        System.out.println("Already in dictionary.");
        }
        reader.close();
        return history;
    }

    public static void testTreeStructure(DLB trie){
        Scanner keyboard = new Scanner(System.in);
        Node currentNode = trie.root;
        boolean keepRunning = true;
        String input = "";
        while (keepRunning){
            System.out.println("Current node: " + trie.returnCharacter(currentNode));
            System.out.println("Next: " + trie.returnCharacter(currentNode.next));
            System.out.println("Child: " + trie.returnCharacter(currentNode.child));
            input = keyboard.nextLine();
            if (input.equals("child")){
                currentNode = currentNode.child;
            }
            if (input.equals("next")){
                currentNode = currentNode.next;
            }
            if (input.equals("stop")){
                keepRunning = false;
            }
        }
        keyboard.close();
    }

    public static void testFindString(DLB trie){
        Scanner keyboard = new Scanner(System.in);
        boolean keepRunning = true;
        String input = "";
        while (keepRunning){
            System.out.println("Enter word:");
            input = keyboard.nextLine();
            if (input.equals("stop")){
                keepRunning = false;
            }
            else if (trie.findString(input) != -1){
                System.out.println("In the dictionary!");
            }
            else {
                System.out.println("Not in the dictionary!");
            }
        }
        keyboard.close();
    }
    
    public static int findEmptyIndex(String[] array){
        for (int i = 0; i < array.length; i++){
            if (array[i] == null)
                return i;
        }
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner keyboard = new Scanner(System.in);
        String[] fullSuggestions = new String[5];
        DLB database = readDictionary();
        DLB history = readHistory();
        long startTime = 0;
        long endTime = 0;
        long averageTime = 0;
        int runs = 0;
        String fullInput = "";
        char input;
        boolean runAgain = true;
    //  RUN BELOW METHODS TO TEST THAT THE PROGRAM IS PLACING THEM CORRECTLY IN THE DLB
    //  testTreeStructure(database);
    //  testFindString(database);

        while (runAgain){
            System.out.print("Enter a character: ");
            input = keyboard.next().charAt(0);
            if (input == '$'){
                System.out.println("WORD COMPLETED: " + fullInput);
                history.add(fullInput);
                File userHistory = new File("user-history.txt");
                FileWriter f = new FileWriter(userHistory, true);
                writer.write(fullInput);
                writer.newLine();
                writer.close();
                f.close();
                fullInput = "";
            }
            else if (input == '1' || input == '2' || input == '3' || input == '4' || input == '5'){
                fullInput = fullSuggestions[Character.getNumericValue(input) - 1];
                System.out.println("WORD COMPLETED: " + fullInput);
                history.add(fullInput);
                File userHistory = new File("user-history.txt");
                FileWriter f = new FileWriter(userHistory, true);
                BufferedWriter writer = new BufferedWriter(f);
                writer.write(fullInput);
                writer.newLine();
                writer.close();
                f.close();
                fullInput = "";
            }
            else if (input == '!'){
                runAgain = false;
                if (runs != 0)
                    System.out.printf("Average time: %.6f s.",(double)averageTime / 1000000000 / runs);
            }
            else {
                startTime = System.nanoTime();
                fullInput += input;
                String[] historySuggestions = null;
                if (history != null){
                    historySuggestions = history.findWeightedSuggestions(fullInput);
                }
                String[] suggestions = database.findSuggestions(fullInput);
                endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                averageTime += totalTime;
                System.out.printf("(%.6f s)\n", (double)totalTime / 1000000000);
                // Print suggestions
                if (historySuggestions != null){
                    for (int i = 1; i <= 5; i++){
                        if (historySuggestions[i - 1] != null)
                        System.out.printf("(%d) %s\n", i, historySuggestions[i - 1]);
                        fullSuggestions[i - 1] = historySuggestions[i - 1];
                    }
                    if (findEmptyIndex(historySuggestions) != -1){
                        for (int i = 1; i <= 5 - findEmptyIndex(historySuggestions); i++){
                            if (suggestions[i - 1] != null){
                                fullSuggestions[i - 1] = suggestions[i - 1];
                            }
                        }
                    }
                }
                else
                    if (suggestions == null)
                        System.out.println("No suggestions found.");
                    else {
                        for (int i = 1; i <= 5; i++){
                            if (suggestions[i - 1] != null){
                                System.out.printf("(%d) %s\n", i, suggestions[i - 1]);
                                fullSuggestions[i - 1] = suggestions[i - 1];
                            }
                        }
                    }
                System.out.println();
                runs++;
            }
        }
    }
}