

// 
// User textreader, bufferedreader, textwriter, and bufferedwriter to read file


package processor;

import java.io.BufferedReader;
// import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
// import java.io.FileWriter;
import java.io.IOException;
// import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class Main{

    public static void main(String[] args)
            throws FileNotFoundException, IOException {
       
        String textFilePath = args[0];

        HashMap<String, Integer> uniqueWordsMap = new HashMap<>();

        // Instantiate a file reader 
        FileReader fReader = new FileReader(textFilePath);

        // Instantiate a buffered reader
        BufferedReader bReader = new BufferedReader(fReader);

        while (true) {
            String line = bReader.readLine();

            if (line == null) {
                break;
            }

            String transformedLine = line.replaceAll("\\p{Punct}", "").toLowerCase().trim();

            String[] words =  transformedLine.split(" ");

            for (String word : words) {
                if (!word.isEmpty()) {
                    if (uniqueWordsMap.containsKey(word)) {
                        uniqueWordsMap.put(word, uniqueWordsMap.get(word) + 1);
                    }
    
                    else {
                        uniqueWordsMap.put(word, 1);
                    }
                }
                
            }

            //lines.add(tranßsformedLine);
        }

        bReader.close();

        int index = 1;

        for (String uniqueWord : uniqueWordsMap.keySet()) {
            System.out.printf("%d. %s: %d\n", index, uniqueWord, uniqueWordsMap.get(uniqueWord));
            index ++;
        }

        // Path to stopwords file
        String stopwordsPath = "stopwords";

        // Instantiate a file reader 
        FileReader fReader2 = new FileReader(stopwordsPath);

        // Instantiate a buffered reader
        BufferedReader bReader2 = new BufferedReader(fReader2);

        // Instantiate an empty ArrayList for stopwords
        ArrayList<String> stopwords = new ArrayList<>();
        
        while (true) {
            String stopword = bReader2.readLine();

            if (stopword == null) {
                break;
            }

            stopwords.add(stopword);
        }

        bReader2.close();

        /*
         *The issue with the code is that you are modifying the uniqueWordsMap (specifically, removing elements) while iterating over its key set. 
         In Java, this can lead to a ConcurrentModificationException because you are changing the structure of the map while iterating over it.
         
        for (String uniqueWord : uniqueWordsMap.keySet()) {
            if (stopwords.contains(uniqueWord)) {
                uniqueWordsMap.remove(uniqueWord);
            }
        } 
         
         */


        Iterator<String> iterator = uniqueWordsMap.keySet().iterator();

        while (iterator.hasNext()) {
            String uniqueWord = iterator.next();
            if (stopwords.contains(uniqueWord)) {
                iterator.remove();
            }
        }
        


        int index2 = 1;

        for (String uniqueWord : uniqueWordsMap.keySet()) {
            System.out.printf("%d. %s: %d\n", index2, uniqueWord, uniqueWordsMap.get(uniqueWord));
            index2 ++;
        }

        TreeMap<String, Integer> treeMap = new TreeMap<>(uniqueWordsMap);

        int index3 = 1;

        for (String uniqueWord : treeMap.keySet()) {
            System.out.printf("%d. %s: %d\n", index3, uniqueWord, treeMap.get(uniqueWord));
            index3 ++;
        }

    }


} 
