package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

public class GameEngineTest {

    GameEngine engine;
    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
    }

    public void transport(Item item) {
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("Farmer", engine.getItemName(Item.FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.FARMER));
        Assert.assertEquals("", engine.getItemSound(Item.FARMER));
        /* TODO Check getters for wolf, goose, and beans */
        Assert.assertEquals("Wolf", engine.getItemName(Item.WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals("Howl", engine.getItemSound(Item.WOLF));

        Assert.assertEquals("Goose", engine.getItemName(Item.GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals("Honk", engine.getItemSound(Item.GOOSE));

        Assert.assertEquals("Beans", engine.getItemName(Item.BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals("", engine.getItemSound(Item.BEANS));
    }

    @Test
    public void testMidTransport() {
        /*
         * TODO Transport the goose to the other side, unload it, and check that it has
         * the appropriate location
         */
        engine.loadBoat(Item.FARMER);
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        transport(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {
        engine.loadBoat(Item.FARMER);
        // transport the goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        /*
         * TODO The steps above are the first two steps in a winning game, complete the
         * steps and check that the game is won.
         */
        // transport the wolf
        //upload the wolf
        transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        transport(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());


        // transport the goose
        transport(Item.GOOSE);
        engine.unloadBoat(Item.FARMER);
        Assert.assertTrue(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void testLosingGame() {
        engine.loadBoat(Item.FARMER);
        // transport the goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        /*
         * TODO Once you transport the goose, go back alone, pick up the wolf, transport
         * the wolf, then go back alone again. After you go back alone the second time,
         * check that the game is lost.
         */
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport wolf
        transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //leave wolf and goose alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {
        engine.loadBoat(Item.FARMER);
        // transport the goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location wolfLoc = engine.getItemLocation(Item.WOLF);
        Location gooseLoc = engine.getItemLocation(Item.GOOSE);
        Location beansLoc = engine.getItemLocation(Item.BEANS);
        Location farmerLoc = engine.getItemLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(wolfLoc, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals(gooseLoc, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals(beansLoc, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals(farmerLoc, engine.getItemLocation(Item.FARMER));
    }
}
