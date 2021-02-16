package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FarmerGameEngine implements GameEngine {

    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item BEANS = Item.ITEM_0;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    final private Map<Item, GameObject> gameObjectMap;

    public FarmerGameEngine() {
        boatLocation = Location.START;
        gameObjectMap = new HashMap<>();
        GameObject wolf = new GameObject("W", Color.CYAN);
        GameObject goose = new GameObject("G", Color.CYAN);
        GameObject beans = new GameObject("B", Color.CYAN);
        GameObject farmer = new GameObject("", Color.MAGENTA);

        gameObjectMap.put(WOLF, wolf);
        gameObjectMap.put(GOOSE, goose);
        gameObjectMap.put(BEANS, beans);
        gameObjectMap.put(FARMER, farmer);


    }

    @Override
    public String getItemLabel(Item id) {
        return gameObjectMap.get(id).getLabel();
    }

    @Override
    public Location getItemLocation(Item id) {
        return gameObjectMap.get(id).getLocation();
    }

    @Override
    public Color getItemColor(Item id) {
        return gameObjectMap.get(id).getColor();
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item id) {
        GameObject object = gameObjectMap.get(id);
        if(getNumOfItemsOnBoat() < 2 && object.getLocation() == boatLocation){
            object.setLocation(Location.BOAT);
        }
    }

    @Override
    public void unloadBoat(Item id) {
        GameObject object = gameObjectMap.get(id);
        if(object.getLocation() == Location.BOAT) {
            object.setLocation(boatLocation);
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if(getItemLocation(FARMER) != Location.BOAT){
            return;
        }
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        for(GameObject object : gameObjectMap.values()){
            if(object.getLocation() != Location.FINISH){
                return false;
            }
        }
        return true;
    }

    @Override
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

    @Override
    public void resetGame() {
        for(GameObject object : gameObjectMap.values()){
            object.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }

    private int getNumOfItemsOnBoat() {
        int itemOnBoat = 0;

        for(Item item : gameObjectMap.keySet()){
            if(getItemLocation(item) == Location.BOAT){
                itemOnBoat++;
            }
        }

        return itemOnBoat;
    }

}
