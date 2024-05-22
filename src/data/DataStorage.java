package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable{
    
    int maxLife;
    int life;
    int attack;
    int key;
    int redPotion;
    int bluePotion;
    int greenPotion;
    int worldX;
    int worldY;
    String direction;
    boolean lighting;
    
    ArrayList<String> itemNames = new ArrayList<>();
    
    String mapObjectNames[];
    int mapObjectWorldX[];
    int mapObjectWorldY[];
    String mapObjectLootNames[][];
}
