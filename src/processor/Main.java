package processor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Main{

    public static void main(String[] args)
            throws FileNotFoundException, IOException {
        
        // Check that text file path is provided in the command line
        if (args.length == 0) {
            System.out.println("Instruction: Include text file path in command line.");
            System.exit(0);
        }
        
        // Get the text file path provided in the command line
        String textFilePath = args[0];    

        // Instantiate a file reader 
        FileReader fReader = new FileReader(textFilePath);

        // Instantiate a buffered reader
        BufferedReader bReader = new BufferedReader(fReader);

        // Instantiate a hashmap to store unique words
        Map<String, Integer> uniqueWordsMap = new HashMap<>();

        // Start a while loop to transform each line in the file
        while (true) {

            // Read line
            String line = bReader.readLine();

            // If reach end (no more lines), break from loop
            if (line == null) {
                break;
            }

            // Remove punctuations, transform to lowercase, and trim whitespaces
            String transformedLine = line.replaceAll("\\p{Punct}", "").toLowerCase().trim();

            // Split the line into words by spaces in between each word
            String[] words =  transformedLine.split(" ");

            // Start a while loop to count the occurence of each unique word 
            for (String word : words) {
                // Ensure that empty strings are not included
                if (!word.isEmpty()) {
                    
                    // If word is already added to hashmap, get the value of the key and ++ to the value
                    if (uniqueWordsMap.containsKey(word)) {
                        uniqueWordsMap.put(word, uniqueWordsMap.get(word) + 1);
                    }
                    // Else, add the word as a ket to the hashmap and allocate the value, 1
                    else {
                        uniqueWordsMap.put(word, 1);
                    }
                }
            }
        }
        // Close the reader stream
        bReader.close();

        // Path to stopwords file
        String stopwordsPath = "stopwords";

        // Instantiate a file reader 
        FileReader fReader2 = new FileReader(stopwordsPath);

        // Instantiate a buffered reader
        BufferedReader bReader2 = new BufferedReader(fReader2);

        // Instantiate an empty ArrayList for stopwords (HashSet as the values should be unique)
        Set<String> stopwords = new HashSet<>();
        
        while (true) {
            String stopword = bReader2.readLine();

            if (stopword == null) {
                break;
            }

            stopwords.add(stopword);
        }

        bReader2.close();

        // Instantiate iterator for unique words map 
        Iterator<String> iterator = uniqueWordsMap.keySet().iterator();

        while (iterator.hasNext()) {
            String uniqueWord = iterator.next();
            // Remove keys from map if it is a stopword
            if (stopwords.contains(uniqueWord)) {
                iterator.remove();
            }
        }
        
        uniqueWordsMap.entrySet()
            .stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .forEach(word -> System.out.println(word));
    }
} 
