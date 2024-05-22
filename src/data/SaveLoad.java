package data;

import entity.Entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import main.GamePanel;
import object.OBJ_BluePotion;
import object.OBJ_Chest;
import object.OBJ_Door1_left;
import object.OBJ_Door1_right;
import object.OBJ_Door2_down;
import object.OBJ_Door2_up;
import object.OBJ_GreenPotion;
import object.OBJ_Key;
import object.OBJ_RedPotion;
import object.OBJ_Shield;
import object.OBJ_Sign;
import object.OBJ_Weapon;

public class SaveLoad {
    
    GamePanel gp;
    
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    
    public Entity getObject(String itemName){
        
        Entity obj = null;
        
        switch(itemName){
            case "Sword": obj = new OBJ_Weapon(gp); break;
            case "Shield": obj = new OBJ_Shield(gp); break;
            case "Red Potion": obj = new OBJ_RedPotion(gp); break;
            case "Blue Potion": obj = new OBJ_BluePotion(gp); break;
            case "Green Potion": obj = new OBJ_GreenPotion(gp); break;
            case "Key": obj = new OBJ_Key(gp); break;
            case "Door1_left": obj = new OBJ_Door1_left(gp); break;
            case "Door1_right": obj = new OBJ_Door1_right(gp); break;
            case "Door2_up": obj = new OBJ_Door2_up(gp); break;
            case "Door2_down": obj = new OBJ_Door2_down(gp); break;
            case "Chest": obj = new OBJ_Chest(gp); break;
            case "Sign": obj = new OBJ_Sign(gp); break;
        }
        
        return obj;
    } 
    
    public void save(){
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("src/res/save/save.dat")));
            
            DataStorage ds = new DataStorage();
            
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.attack = gp.player.defaultAttack;
            ds.key = gp.player.Key;
            ds.redPotion = gp.player.RedPotion;
            ds.bluePotion = gp.player.BluePotion;
            ds.greenPotion = gp.player.GreenPotion;
            ds.worldX = gp.player.worldX;
            ds.worldY = gp.player.worldY;
            ds.direction = gp.player.direction;
            ds.lighting = gp.player.lighting;
            
            for(int i = 0; i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
            }
            
            ds.mapObjectNames = new String[gp.obj.length];
            ds.mapObjectWorldX = new int[gp.obj.length];
            ds.mapObjectWorldY = new int[gp.obj.length];
            ds.mapObjectLootNames = new String[gp.obj.length][20];
            
            for(int i = 0; i < gp.obj.length; i++){
                
                if(gp.obj[i] == null){
                    ds.mapObjectNames[i] = "NA";
                }
                else{
                    ds.mapObjectNames[i] = gp.obj[i].name;
                    ds.mapObjectWorldX[i] = gp.obj[i].worldX;
                    ds.mapObjectWorldY[i] = gp.obj[i].worldY;
                    for(int j = 0; j < 20; j++){
                        if(j >= gp.obj[i].chestInventory.size()){
                            ds.mapObjectLootNames[i][j] = "NA";
                        }
                        else{
                            ds.mapObjectLootNames[i][j] = gp.obj[i].chestInventory.get(j).name;
                        }
                    }
                }
            }
            
            oos.writeObject(ds);
            
        } catch (IOException e) {
            System.out.println("Save exception!");
        }
    }
    public void load(){
        
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("src/res/save/save.dat")));
            
            DataStorage ds = (DataStorage)ois.readObject();
            
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.attack = ds.attack;
            gp.player.Key = ds.key;
            gp.player.RedPotion = ds.redPotion;
            gp.player.BluePotion = ds.bluePotion;
            gp.player.GreenPotion = ds.greenPotion;
            gp.player.worldX = ds.worldX;
            gp.player.worldY = ds.worldY;
            gp.player.direction = ds.direction;
            gp.player.lighting = ds.lighting;
            
            gp.player.inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++){
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
            }
            
            for(int i = 0; i < gp.obj.length; i++){
                
                if(ds.mapObjectNames[i].equals("NA")){
                    gp.obj[i] = null;
                }
                else{
                    gp.obj[i] = getObject(ds.mapObjectNames[i]);
                    gp.obj[i].worldX = ds.mapObjectWorldX[i];
                    gp.obj[i].worldY = ds.mapObjectWorldY[i];
                    if (ds.mapObjectNames[i].equals("Chest")){
                        for(int j = 0; j < 20; j++){
                            if(ds.mapObjectLootNames[i][j].equals("NA") == false){
                                gp.obj[i].chestInventory.add(getObject(ds.mapObjectLootNames[i][j]));
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
            }
            
        } catch (IOException e){
            System.out.println("Load exception!");
        } catch (ClassNotFoundException ex) {
            System.out.print("Not found!");
        }
    }
}
