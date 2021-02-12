package river;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    public enum Item {
        WOLF, GOOSE, BEANS, FARMER;
    }

    private Location boatLocation;
    final private Map<Item, GameObject> gameObjectMap;
    private int itemsOnBoat;

    public GameEngine() {
        boatLocation = Location.START;
        itemsOnBoat = 0;
        gameObjectMap = new HashMap<>();
        GameObject wolf = new GameObject("Wolf", "Howl");
        GameObject goose = new GameObject("Goose", "Honk");
        GameObject beans = new GameObject("Beans", "");
        GameObject farmer = new GameObject("Farmer", "");

        gameObjectMap.put(Item.WOLF, wolf);
        gameObjectMap.put(Item.GOOSE, goose);
        gameObjectMap.put(Item.BEANS, beans);
        gameObjectMap.put(Item.FARMER, farmer);


    }

    public String getItemName(Item id) {
        return gameObjectMap.get(id).name;
    }

    public Location getItemLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    public String getItemSound(Item id) {
        return gameObjectMap.get(id).getSound();
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
        Location gooseLocation = gameObjectMap.get(Item.GOOSE).getLocation();
        Location wolfLocation = gameObjectMap.get(Item.WOLF).getLocation();
        Location beansLocation = gameObjectMap.get(Item.BEANS).getLocation();
        Location farmerLocation = gameObjectMap.get(Item.FARMER).getLocation();

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
