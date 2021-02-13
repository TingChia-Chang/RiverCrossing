package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item BEANS = Item.ITEM_0;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    final private Map<Item, GameObject> gameObjectMap;
    private int itemsOnBoat;

    public GameEngine() {
        boatLocation = Location.START;
        itemsOnBoat = 0;
        gameObjectMap = new HashMap<>();
        GameObject wolf = new GameObject("Wolf", Color.CYAN);
        GameObject goose = new GameObject("Goose", Color.CYAN);
        GameObject beans = new GameObject("Beans", Color.CYAN);
        GameObject farmer = new GameObject("Farmer", Color.MAGENTA);

        gameObjectMap.put(WOLF, wolf);
        gameObjectMap.put(GOOSE, goose);
        gameObjectMap.put(BEANS, beans);
        gameObjectMap.put(FARMER, farmer);


    }

    public String getItemLabel(Item id) {
        return gameObjectMap.get(id).label;
    }

    public Location getItemLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    public Color getItemColor(Item id) {
        return gameObjectMap.get(id).getColor();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {
        GameObject object = gameObjectMap.get(id);
        if(itemsOnBoat < 2 && object.getLocation() == boatLocation){
            object.setLocation(Location.BOAT);
            itemsOnBoat++;
        }
    }

    public void unloadBoat(Item id) {
        GameObject object = gameObjectMap.get(id);
        if(object.getLocation() == Location.BOAT) {
            object.setLocation(boatLocation);
            itemsOnBoat--;
        }
    }

    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    public boolean gameIsWon() {
        for(GameObject object : gameObjectMap.values()){
            if(object.getLocation() != Location.FINISH){
                return false;
            }
        }
        return true;
    }

    public boolean gameIsLost() {
        Location gooseLocation = gameObjectMap.get(GOOSE).getLocation();
        Location wolfLocation = gameObjectMap.get(WOLF).getLocation();
        Location beansLocation = gameObjectMap.get(BEANS).getLocation();
        Location farmerLocation = gameObjectMap.get(FARMER).getLocation();

        if (gooseLocation == Location.BOAT || gooseLocation == boatLocation || gooseLocation == farmerLocation){
            return false;
        }

        if (gooseLocation == wolfLocation || gooseLocation == beansLocation){
            return true;
        }

        return false;
    }

    public void resetGame() {
        for(GameObject object : gameObjectMap.values()){
            object.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }

}
