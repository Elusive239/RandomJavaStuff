package com.company;

import java.util.Random;
import java.util.Scanner;

public class GameBoard {
    private int BoardSize;
    private Panel[][] panels;
    private Boolean twoPlayers;
    private Boolean playerTurn;
    private int playerOneWins;
    private int playerTwoWins;

    //Constructor for board object
    public GameBoard() {
        BoardSize = 3;
        panels = new Panel[BoardSize][BoardSize];
        Scanner in = new Scanner(System.in);
        twoPlayers = oneOrTwoPlayers(in);
        playerTurn = true;
        BoardSetup();
        Play(in);
    }

    private void Play(Scanner in) {
        while (true) {
            System.out.println("Player 1 wins: "+getPlayerOneWins() + "\n" + "Player 2 wins: "+getPlayerTwoWins());
            while (true) {
                if (playerTurn || twoPlayers)
                    System.out.println(this.toString());
                SetSelected(in);

                if (playerTurn && twoPlayers) {
                    System.out.println("It's player ones turn!");
                } else if (twoPlayers && !playerTurn) System.out.println("It's player twos turn!");
                else if (playerTurn && !twoPlayers) System.out.println("It's your turn!");

                if (CheckTie())
                    break;
                if (CheckSplit("X"))
                    break;
                if (CheckSplit("O"))
                    break;
            }

            System.out.println("Do you wish to play again?");
            if (in.nextLine().contains("no"))
                System.exit(0);
            else
                BoardReset();
        }
    }

    private boolean CheckTie() {
        int temp = 0;
        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                if (panels[i][j].getBeenSelected() == true)
                    break;
                else temp++;
            }
        }
        if (temp == BoardSize * BoardSize) {
            System.out.println("It's a tie! Nobody wins!");
            BoardReset();
            return true;
        }
        return false;
    }

    private boolean CheckSplit(String player) {
        for (int i = 0; i < BoardSize; i++) {
            if (panels[i][0].getTextToDisplay() == player && panels[i][1].getTextToDisplay() == player && panels[i][2].getTextToDisplay() == player) {
                Win(player);
                return true;
            }
        }

        for (int i = 0; i < BoardSize; i++) {
            if (panels[0][i].getTextToDisplay() == player && panels[1][i].getTextToDisplay() == player && panels[2][i].getTextToDisplay() == player) {
                Win(player);
                return true;
            }
        }

        if (panels[0][0].getTextToDisplay() == player && panels[1][1].getTextToDisplay() == player && panels[2][2].getTextToDisplay() == player) {
            Win(player);
            return true;
        }

        if (panels[0][2].getTextToDisplay() == player && panels[1][1].getTextToDisplay() == player && panels[2][0].getTextToDisplay() == player) {
            Win(player);
            return true;
        }

        return false;
    }

    private void Win(String player) {
        if (player.equals("O")) {
            increasePlayerTwoWins();
        } else if (player.equals("X")) {
            increasePlayerOneWins();
        }
    }

    private void SetSelected(Scanner in) {
        boolean test = false;
        int response = 0;
        while (!test) {

            if (twoPlayers || playerTurn)
                response = PlayerInput(in);
            else response = RandomPlayerInput();

            for (int i = 0; i < BoardSize; i++) {
                for (int j = 0; j < BoardSize; j++) {
                    if (!panels[i][j].getBeenSelected() && Integer.parseInt(panels[i][j].getTextToDisplay()) == response) {
                        panels[i][j].setBeenSelected(true);
                        test = true;
                        if (playerTurn) {
                            playerTurn = false;
                            panels[i][j].setTextToDisplay("X");
                        } else if (!playerTurn) {
                            playerTurn = true;
                            panels[i][j].setTextToDisplay("O");
                        }
                    }
                }
            }
            if (!test && playerTurn)
                System.out.println("Please only select a number that isn't taken, and is between one and nine!");
        }
    }

    //asks if there are two players or not
    private Boolean oneOrTwoPlayers(Scanner in) {
        System.out.println("One or Two players?");
        while (true) {
            String temp = in.nextLine();
            if (temp.contains("one"))
                return false;
            else if (temp.contains("two"))
                return true;
            else System.out.println("Please only select one or two.");
        }
    }

    //Creates and adds the panels to the Board
    private void BoardSetup() {
        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                String temp = "";
                if (i == 0)
                    temp = String.valueOf(i + j + 1);
                if (i == 1)
                    temp = String.valueOf(j + 4);
                if (i == 2)
                    temp = String.valueOf(j + 7);

                panels[i][j] = new Panel(temp);
            }
        }
    }

    //instead of creating new objects, just reset the old ones because well
    //its better than creating tons of panel objects after playing this over and over..
    public void BoardReset() {
        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                String temp = "";
                if (i == 0)
                    temp = String.valueOf(i + j + 1);
                if (i == 1)
                    temp = String.valueOf(j + 4);
                if (i == 2)
                    temp = String.valueOf(j + 7);

                panels[i][j].setTextToDisplay(temp);
            }
        }
    }

    //prints out the board
    public String toString() {
        String out = "";
        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                out += panels[i][j].getTextToDisplay() + " ";
            }
            out += "\n";
        }
        return out;
    }

    //player input
    private int PlayerInput(Scanner in) {//for all players input
        System.out.println("Type the number to the corresponding place you wish to place your X or O!");
        return Integer.parseInt(in.nextLine());
    }

    //"ai" player input
    private int RandomPlayerInput() {//for one player games
        return new Random().nextInt(9) + 1;
    }

    public int getPlayerOneWins() {
        return this.playerOneWins;
    }

    public int getPlayerTwoWins() {
        return this.playerTwoWins;
    }

    public void increasePlayerOneWins() {
        System.out.println("Player one won!");
        this.playerOneWins++;
    }

    public void increasePlayerTwoWins() {
        System.out.println("Player two won!");
        this.playerTwoWins++;
    }
}
