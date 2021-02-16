package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RobotGameEngine implements GameEngine{

    public static final Item SMALLBOT_1 = Item.ITEM_0;
    public static final Item SMALLBOT_2 = Item.ITEM_1;
    public static final Item TALLBOT_1 = Item.ITEM_2;
    public static final Item TALLBOT_2 = Item.ITEM_3;
    private Location boatLocation;
    final private Map<Item, GameObject> gameObjectMap;

    public RobotGameEngine() {
        boatLocation = Location.START;
        gameObjectMap = new HashMap<>();
        GameObject smallbot_1 = new GameObject("S1", Color.CYAN);
        GameObject smallbot_2 = new GameObject("S2", Color.CYAN);
        GameObject tallbot_1 = new GameObject("T1", Color.MAGENTA);
        GameObject tallbot_2 = new GameObject("T2", Color.MAGENTA);

        gameObjectMap.put(SMALLBOT_1, smallbot_1);
        gameObjectMap.put(SMALLBOT_2, smallbot_2);
        gameObjectMap.put(TALLBOT_1, tallbot_1);
        gameObjectMap.put(TALLBOT_2, tallbot_2);
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
        if(object.getLocation() == boatLocation){
            if((id == Item.ITEM_0 || id == Item.ITEM_1) && getNumOfItemsOnBoat() < 2){
                object.setLocation(Location.BOAT);
            } else if ((id == Item.ITEM_2 || id == Item.ITEM_3) && getNumOfItemsOnBoat() == 0) {
                object.setLocation(Location.BOAT);
            }
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
                if(item == Item.ITEM_0 || item == Item.ITEM_1){
                    itemOnBoat++;
                }else{
                    itemOnBoat+=2;
                }
            }
        }

        return itemOnBoat;
    }
}
