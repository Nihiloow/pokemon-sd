package com.example.pslikemyversion.engine;

import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import javafx.util.Pair; // Import Pair utility for storing move selection

import java.util.Scanner;

public class CombatSystem {
    private Pokemon player1;
    private Pokemon player2;
    private Scanner scanner = new Scanner(System.in);

    public CombatSystem(Pokemon player1, Pokemon player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void start() {
        while (player1.getHp().getRealStat() > 0 && player2.getHp().getRealStat() > 0) {
            System.out.println("Current HP:");
            System.out.println(player1.getName() + " HP: " + player1.getHp().getRealStat());
            System.out.println(player2.getName() + " HP: " + player2.getHp().getRealStat());

            // Player 1's turn
            Pair<Pokemon, Move> moveChoice1 = chooseMove(player1);
            if (moveChoice1 != null) {
                moveChoice1.getKey().attack(moveChoice1.getValue());
            }

            displayStatus();
            if (player2.getHp().getRealStat() <= 0) break;

            // Player 2's turn
            Pair<Pokemon, Move> moveChoice2 = chooseMove(player2);
            if (moveChoice2 != null) {
                moveChoice2.getKey().attack(moveChoice2.getValue());
            }

            displayStatus();
        }

        if (player1.getHp().getRealStat() <= 0) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println(player1.getName() + " wins!");
        }
    }

    private Pair<Pokemon, Move> chooseMove(Pokemon pokemon) {
        System.out.println("Choose a move for " + pokemon.getName() + ":");
        int index = 0;
        for (Move move : pokemon.getMoves()) {
            System.out.println(index + ". " + move.getName());
            index++;
        }

        int choice = scanner.nextInt();
        if (choice >= 0 && choice < pokemon.getMoves().size()) {
            return new Pair<>(pokemon, pokemon.getMoves().get(choice));
        } else {
            System.out.println("Invalid move. Skipping turn.");
            return null;
        }
    }

    private void displayStatus() {
        System.out.println(player1.getName() + " HP: " + player1.getHp().getRealStat());
        System.out.println(player2.getName() + " HP: " + player2.getHp().getRealStat());
    }
}