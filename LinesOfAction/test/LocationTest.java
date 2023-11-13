//Michael Harper, Kyle Monteleone, Nuzhat Hoque
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void add() {
        Location a = new Location(1, 2);
        Location b = new Location(4, 6);
        assertEquals("<5, 8>", a.add(b).toString());
    }

    @Test
    void sub() {
        Location a = new Location(1, 2);
        Location b = new Location(4, 6);
        assertEquals("<-3, -4>", a.sub(b).toString());
    }

    @Test
    void asAString(){
        Location a = new Location(1, 2);
        assertEquals("<1, 2>", a.toString());
    }

    @Test
    void isItValid(){
        Location a = new Location(1, 2);
        Location b = new Location(9, 6);
        assertEquals(true, a.isValid());
        assertEquals(false, b.isValid());
    }
}