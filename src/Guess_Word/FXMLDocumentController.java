/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Guess_Word;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import java.util.Scanner;
import java.nio.file.Paths;

public class FXMLDocumentController
{
    int difficulty = 1;
    File wordList = new File ("WordList.txt");
    String tempWord = "";
    Game currGame;
    List<String> easyWords = new ArrayList<String>();
    List<String> interWords = new ArrayList<String>();
    List<String> hardWords = new ArrayList<String>();
    

    //Start Screen
    @FXML
    private Pane pne_StartMenu;

    @FXML
    private Slider sld_Difficulty;

    @FXML
    private Label lbl_Difficulty;


    @FXML
    private Button btn_Load;
    
    
    @FXML
    private ListView ltv_Saves;
    
    @FXML
    private HBox hbx_LoadGame;


    
    //Game Screen
    @FXML
    private Pane pne_Game;

    @FXML
    private Label lbl_MysteryWord;

    @FXML
    private Label lbl_Guesses;

    @FXML
    private Label lbl_GuessedLetters;

    @FXML
    private Button btn_Guess;

    @FXML
    private TextField txt_GuessInput;

    @FXML
    private Button btn_Save;

    
    @FXML
    private Label lbl_InvInputText;
    
    @FXML
    private Label lbl_EndResult;

    @FXML
    private Button btn_Return;
    @FXML
    private Button btn_CreateGame;
    @FXML
    private Button btn_Quit;
    @FXML
    private Button btn_LoadSelected;
    
    
    @FXML
    void AdjustDifficulty(MouseEvent event)
    {
        if(sld_Difficulty.getValue() < (sld_Difficulty.getMax() / 3))
        {
            difficulty = 1;
            lbl_Difficulty.setText("Easy");
        }
        else if(sld_Difficulty.getValue() > (2 * (sld_Difficulty.getMax() / 3)))
        {
            difficulty = 3;
            lbl_Difficulty.setText("Hard");
        }
        else
        {
            {
            difficulty = 2;
            lbl_Difficulty.setText("Intermediate");
        }
        }
    }

    @FXML
    void CreateGame(ActionEvent event)
    {
        Random randomGen = new Random();
        int random = 0;
        System.out.println(random);
        int numGuesses = 0;
        
        if(easyWords.isEmpty() || interWords.isEmpty() || hardWords.isEmpty())
        {
            try(BufferedReader brInput = new BufferedReader(new FileReader(wordList)))
            {
                while((tempWord = brInput.readLine()) != null)
                {
                    if(tempWord.length() == 10)
                    {
                        easyWords.add(tempWord);
                    }
                    if(tempWord.length() == 8)
                    {
                        interWords.add(tempWord);
                    }
                    if(tempWord.length() == 6)
                    {
                        hardWords.add(tempWord);
                    }
                }
            }
            catch(NoSuchElementException | IOException | IllegalStateException e)
            {
                System.out.println("ERROR TEST");
                e.printStackTrace();

                System.exit(1);
            }
        }
        List<String> selectedWords = new ArrayList<String>();
        switch(difficulty)
        {
            case 1:
                selectedWords.addAll(easyWords);
                numGuesses = 10;
                break;
            case 2:
                selectedWords.addAll(interWords);
                numGuesses = 7;
                break;
            case 3:
                selectedWords.addAll(hardWords);
                numGuesses = 4;
                break;
        }
        
        System.out.println(selectedWords.size());
        random = randomGen.nextInt(selectedWords.size());
        System.out.println(random);
        currGame = new Game(selectedWords.get(random), numGuesses);
        
        //First half of UI pane switch
        pne_StartMenu.setVisible(false);
        pne_Game.setDisable(false);
        
        //Set UI elements with the values from their corresponding variables
        lbl_MysteryWord.setText(currGame.getCurrWord());
        lbl_Guesses.setText(Integer.toString(currGame.getNumGuesses()));
        lbl_GuessedLetters.setText(currGame.getGuesses());
        
        //Enable Save button
        btn_Save.setVisible(true);
        btn_Save.setDisable(false);
        
        //Remove Return button
        btn_Return.setDisable(true);
        btn_Return.setVisible(false);
        
        //Removing load game UI elements
        btn_Load.setVisible(true);
        hbx_LoadGame.setVisible(false);
        ltv_Saves.setVisible(false);
        ltv_Saves.getItems().clear();
        ltv_Saves.setDisable(true);
        hbx_LoadGame.setDisable(true);
        
        //Enabling Guess textbox and button
        txt_GuessInput.setDisable(false);
        btn_Guess.setDisable(false);
        
        //Second half of UI pane switch
        pne_StartMenu.setDisable(true);
        pne_Game.setVisible(true);
    }

    @FXML
    void LoadGame(ActionEvent event)
    {
        hbx_LoadGame.setDisable(false);
        btn_Load.setVisible(false);
        ltv_Saves.setDisable(false);
        
        File[] files = new File(".").listFiles();
        for (File file : files)
        {
            if (file.isFile() && file.getName().contains("SAVE_"))
            {
                ltv_Saves.getItems()
                        .add(file.getName());
            }
        }
        hbx_LoadGame.setVisible(true);

        ltv_Saves.setVisible(true);
        
    }
    
    @FXML
    void LoadSelected(ActionEvent event)
    {
        File[] files = new File(".").listFiles();
        List<String> fileList = new ArrayList<String>();
        
        String mystWord = "";
        String currWord = "";
        String guesses = "";
        int numGuesses = 0;
        
        for (File file : files)
        {
            if (file.isFile() && file.getName().contains("SAVE_"))
            {
                fileList.add(file.getName());
            }
        }
        
        try (Scanner input = new Scanner(Paths.get(fileList.get(ltv_Saves.getSelectionModel().getSelectedIndex()))))
        {
            //input.useDelimiter("|");
            while (input.hasNext())
            {
                mystWord = input.nextLine();
                currWord = input.nextLine();
                guesses = input.nextLine();
                numGuesses = input.nextInt();
                System.out.println(numGuesses);
            }
            
            
            
            
            
            
            currGame = new Game(mystWord, currWord, guesses, numGuesses);
            
            //First half of UI pane switch
            pne_StartMenu.setVisible(false);
            pne_Game.setDisable(false);

            //Set UI elements with the values from their corresponding variables
            lbl_MysteryWord.setText(currGame.getCurrWord());
            lbl_Guesses.setText(Integer.toString(currGame.getNumGuesses()));
            lbl_GuessedLetters.setText(currGame.getGuesses());

            //Enable Save button
            btn_Save.setVisible(true);
            btn_Save.setDisable(false);

            //Remove Return button
            btn_Return.setDisable(true);
            btn_Return.setVisible(false);

            //Removing load game UI elements
            btn_Load.setVisible(true);
            hbx_LoadGame.setVisible(false);
            ltv_Saves.setVisible(false);
            ltv_Saves.getItems().clear();
            ltv_Saves.setDisable(true);
            hbx_LoadGame.setDisable(true);
            
            //Enabling Guess textbox and button
            txt_GuessInput.setDisable(false);
            btn_Guess.setDisable(false);

            //Second half of UI pane switch
            pne_StartMenu.setDisable(true);
            pne_Game.setVisible(true);
        }
        catch(NoSuchElementException | IllegalStateException | IOException e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
            
            System.exit(1);
        }
        
        //currGame = new Game();
        ltv_Saves.setDisable(false);
        ltv_Saves.setVisible(true);
    }
    
    
    
  

    @FXML
    void ReturnToStart (ActionEvent event)
    {
        pne_Game.setVisible(false);
        
        lbl_EndResult.setVisible(false);
        
        pne_StartMenu.setDisable(false);
        pne_Game.setDisable(true);
        pne_StartMenu.setVisible(true);
    }
    
    @FXML
    void EnterGuess(ActionEvent event)
    {
        if ((txt_GuessInput.getText().length() != 1)
                || !(Character.isAlphabetic(txt_GuessInput.getText().charAt(0)))
                || lbl_GuessedLetters.getText().toUpperCase().contains(txt_GuessInput.getText().toUpperCase()))
        {
            lbl_InvInputText.setVisible(true);
            return;
        }
        lbl_InvInputText.setVisible(false);
        
        if(!(currGame.guess(txt_GuessInput.getText().charAt(0))))
        {
            return;
        }
        lbl_MysteryWord.setText(currGame.getCurrWord());
        lbl_GuessedLetters.setText(currGame.getGuesses());
        lbl_Guesses.setText(Integer.toString(currGame.getNumGuesses()));
        
        if(currGame.getNumGuesses() == 0)
        {
            if(currGame.getCurrWord().contains("_"))
            {
                lbl_EndResult.setText("YOU LOSE.");
                lbl_EndResult.setVisible(true);
            }
            else
            {
                lbl_EndResult.setText("YOU WIN!");
                lbl_EndResult.setVisible(true);
            }
        }
        else
        {
            if(!currGame.getCurrWord().contains("_"))
            {
                lbl_EndResult.setText("YOU WIN!");
                lbl_EndResult.setVisible(true);
            }
        }
        
        txt_GuessInput.setText("");
    }

    
    @FXML
    void QuitGame(ActionEvent event)
    {
        lbl_MysteryWord.setText(currGame.getMystWord());
        
        btn_Save.setVisible(false);
        btn_Save.setDisable(true);
        
        btn_Return.setDisable(false);
        btn_Return.setVisible(true);
        
        txt_GuessInput.setDisable(true);
        btn_Guess.setDisable(true);
    }

    @FXML
    void SaveGame(ActionEvent event)
    {
        currGame.save();
    }
}
