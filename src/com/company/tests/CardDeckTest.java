import com.company.deck.CardDeck;
import com.company.deck.CardType;

import static org.junit.Assert.*;

public class CardDeckTest {

    @org.junit.Test
    public void takeCardTop() {
        CardDeck deck = new CardDeck();
        assertEquals(CardType.Chopsticks, deck.takeCard());
    }

}