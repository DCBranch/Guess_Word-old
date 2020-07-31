/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Guess_Word;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 * @author DCoreB
 */
public class Game {
    private char [] mysteryWord;
    private char [] currentWord;
    private List<Character> guesses = new ArrayList<Character>();
    private int numGuesses = 0;
    private Object[] args;
    
    //New game constructor
    public Game(String word, int numGuesses)
    {
        System.out.println(word);
        
        mysteryWord = word.toCharArray();
        //word.getChars(0, word.length() - 1, mysteryWord, 0);
        System.out.println(new String(mysteryWord));
        
        currentWord = new char [word.length()];
        //Fills the currentWord array with placeholder underscores
        for (int count = 0; count < word.length(); count++)
        {
                currentWord[count] = '_';
        }
        
        this.numGuesses = numGuesses;
    }
    
    //Load game constructor
    public Game(String word, String curr, String guesses, int numGuesses)
    {
        mysteryWord = word.toCharArray();
        currentWord = curr.toCharArray();
        this.guesses = guesses.chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.toList());
        this.numGuesses = numGuesses;
    }
    
    //Saves this game's info into a text file
    public void save()
    {
        //Uses the local date and time as the name of the filename
        LocalDateTime currTime = LocalDateTime.now();
        String saveFileName = "SAVE_" + currTime.toString();
        saveFileName = saveFileName.replace('.', '_');
        saveFileName = saveFileName.replace(':', '_');
        
        try
        {
            FileWriter saveFile = new FileWriter (saveFileName, true);
            
            BufferedWriter buffReader = new BufferedWriter(saveFile);
        }
        catch(NoSuchElementException | IOException | IllegalStateException e)
        {
            System.out.println("ERROR TEST");
            e.printStackTrace();
            
            System.exit(1);
        }
        //Adds new save data to string meant for save file. Format:  "mysteryWord|currentWordCharArray|lettersAlreadyGuessed|numberOfGuessesLeft return"
        String saveFileStr = new String(mysteryWord) + "\n" + new String(currentWord) + "\n" + guesses.toString().replaceAll("[,\\s\\[\\]]", "") + "\n" + Integer.toString(numGuesses);
        
        //Tries to read the current save file and write in the save data string
        try(BufferedWriter brInput = new BufferedWriter(new FileWriter(saveFileName)))
        {
            brInput.append(saveFileStr);
            brInput.newLine();
        }
        catch(NoSuchElementException | IOException | IllegalStateException e)
        {
            System.out.println("ERROR TEST");
            e.printStackTrace();
            
            System.exit(1);
        }
        
        
    }
    
    /*Checks to see if the guess is in the mystery word, decrements numGuesses, and replaces _s with
     the character if it's in the mystery word. If there's no guesses left, it returns false and makes
    no changes to the class.*/
    public boolean guess(char guess)
    {
        boolean found = false;
        
        if(numGuesses==0 || (new String(currentWord).contentEquals(new String(mysteryWord))))
            return false;
        
        //Loojs for the first unchanged value in the array and makes it the guess letter
        guesses.add(guess);
        
        //Replaces _s in the currentWord with the guess letter if it belongs in said spot
        for(int count = 0; count < mysteryWord.length; count++)
            if(Character.toUpperCase(guess) == Character.toUpperCase(mysteryWord[count]))
            {
                currentWord[count] = mysteryWord[count];
                found = true;
            }
        
        if(!found)
            numGuesses--;
        
        return true;
    }
    
    public String getMystWord()
    {
        return new String(mysteryWord);
    }
    
    public int getNumGuesses()
    {
        return numGuesses;
    }
    
    public String getCurrWord()
    {
        return new String(currentWord);
    }
    
    public String getGuesses()
    {
        return guesses.toString();
    }
}
