package BusinessLayer;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
public class WarriorTest {
    private Warrior w;
    @Before
    public void setup(){
        w = new Warrior(0,"itzik",60,28,8,0,0);
    }
    @Test
    public void levelUp() {
        Assert.assertEquals("the test failed",w.LevelUp(),"itzik" + " reached level " + "2" + ": +" + "30" + " Health, +" + "12" + " Attack, +" + "4" + " Defense");
    }
}