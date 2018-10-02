import com.company.CardDeck;
import com.company.CardType;

import static org.junit.Assert.*;

public class CardDeckTest {

    @org.junit.Test
    public void takeCardTop() {
        CardDeck deck = new CardDeck();
        assertEquals(CardType.Chopsticks, deck.takeCard());
    }

}