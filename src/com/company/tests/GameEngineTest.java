import com.company.GameEngine;
import com.company.Player;
import com.company.CardDeck;
import com.company.CardType;
import com.company.players.RandomPlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameEngineTest {

    /**
     * Check to make sure the generate hand function works properly
     */


    @Test
    public void generateHand() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        playerList.add(new RandomPlayer());
        //playerList.add(new GUIPlayer()); // Having a single GUIPlayer will play the game with a GUI
        GameEngine gameEngine = new GameEngine(playerList);

        List<CardType> hand = new ArrayList<>();
        CardDeck deck = new CardDeck();
        for(int i = 0; i < 8; i++) {
            CardType card = deck.takeCard();
            hand.add(card);
        }
        assertEquals(hand, gameEngine.generateHand());
    }
}