package main;

import entity.Entity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import main.tile.Tile;
import monster.MON_Bat;
import monster.MON_Skeleton;
import monster.MON_Zombie;
import object.OBJ_BluePotion;
import object.OBJ_Chest;
import object.OBJ_Door1_left;
import object.OBJ_Door1_right;
import object.OBJ_Door2_down;
import object.OBJ_Door2_up;
import object.OBJ_GreenPotion;
import object.OBJ_Key;
import object.OBJ_RedPotion;
import object.OBJ_Sign;
import object.OBJ_SmallHeart;

public class AssetSetter {
    
    GamePanel gp;
    
    public int map[][];
    public Tile[] currentTile;
    ArrayList<Entity> loot = new ArrayList<>();
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
        
        map= new int[gp.maxWorldCol][gp.maxWorldRow];
        currentTile= new Tile[80];
        
    }
    
    public void setObject(){
        
        int i = setDoor();
        gp.obj[i]= new OBJ_Sign(gp);
        gp.obj[i].worldX = 14 * gp.tileSize;
        gp.obj[i].worldY = 10 * gp.tileSize;
        gp.obj[i].dialogueIndex = 0;
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 15 * gp.tileSize;
        gp.obj[i].worldY = 11 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        loot.add(new OBJ_RedPotion(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 20 * gp.tileSize;
        gp.obj[i].worldY = 43 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        loot.add(new OBJ_BluePotion(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 42 * gp.tileSize;
        gp.obj[i].worldY = 48 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        loot.add(new OBJ_GreenPotion(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 37 * gp.tileSize;
        gp.obj[i].worldY = 11 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
        
        gp.obj[i]= new OBJ_Sign(gp);
        gp.obj[i].worldX = 38 * gp.tileSize;
        gp.obj[i].worldY = 10 * gp.tileSize;
        gp.obj[i].dialogueIndex = 2;
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 40 * gp.tileSize;
        gp.obj[i].worldY = 24 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        loot.add(new OBJ_RedPotion(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 22 * gp.tileSize;
        gp.obj[i].worldY = 27 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
        
        gp.obj[i]= new OBJ_Chest(gp);
        gp.obj[i].worldX = 20 * gp.tileSize;
        gp.obj[i].worldY = 39 * gp.tileSize;
        loot.add(new OBJ_Key(gp));
        gp.obj[i].fillChest(loot);
        loot.clear();
        i++;
    }
    
    public void setMonster(){
        
        int i = 0;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 14 * gp.tileSize;
        gp.monster[i].worldY = 23 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 16 * gp.tileSize;
        gp.monster[i].worldY = 28 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 15 * gp.tileSize;
        gp.monster[i].worldY = 37 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 14 * gp.tileSize;
        gp.monster[i].worldY = 41 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 20 * gp.tileSize;
        gp.monster[i].worldY = 44 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 26 * gp.tileSize;
        gp.monster[i].worldY = 45 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Bat(gp);
        gp.monster[i].worldX = 32 * gp.tileSize;
        gp.monster[i].worldY = 45 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Bat(gp);
        gp.monster[i].worldX = 32 * gp.tileSize;
        gp.monster[i].worldY = 46 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 38 * gp.tileSize;
        gp.monster[i].worldY = 47 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 39 * gp.tileSize;
        gp.monster[i].worldY = 36 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 40 * gp.tileSize;
        gp.monster[i].worldY = 29 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Bat(gp);
        gp.monster[i].worldX = 38 * gp.tileSize;
        gp.monster[i].worldY = 25 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 44 * gp.tileSize;
        gp.monster[i].worldY = 46 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 45 * gp.tileSize;
        gp.monster[i].worldY = 40 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 45 * gp.tileSize;
        gp.monster[i].worldY = 38 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 47 * gp.tileSize;
        gp.monster[i].worldY = 26 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Bat(gp);
        gp.monster[i].worldX = 47 * gp.tileSize;
        gp.monster[i].worldY = 30 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Bat(gp);
        gp.monster[i].worldX = 47 * gp.tileSize;
        gp.monster[i].worldY = 42 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 44 * gp.tileSize;
        gp.monster[i].worldY = 28 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 26 * gp.tileSize;
        gp.monster[i].worldY = 15 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 29 * gp.tileSize;
        gp.monster[i].worldY = 18 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 23 * gp.tileSize;
        gp.monster[i].worldY = 24 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 25 * gp.tileSize;
        gp.monster[i].worldY = 26 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 23 * gp.tileSize;
        gp.monster[i].worldY = 14 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 23 * gp.tileSize;
        gp.monster[i].worldY = 34 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Skeleton(gp);
        gp.monster[i].worldX = 29 * gp.tileSize;
        gp.monster[i].worldY = 37 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 22 * gp.tileSize;
        gp.monster[i].worldY = 36 * gp.tileSize;
        i++;
        gp.monster[i]= new MON_Zombie(gp);
        gp.monster[i].worldX = 31 * gp.tileSize;
        gp.monster[i].worldY = 32 * gp.tileSize;
        i++;
    }
    
    public int setDoor(){
        try{
            InputStream is = getClass().getResourceAsStream("/res/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(",");
                    int num = Integer.parseInt(numbers[col]);
                    
                    map[col][row] = num;
                    col++;
                   
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
         
        }catch(Exception e){
            e.printStackTrace();
        }
        
        int i=0;
        for(int row = 0; row < 59; row++){
            for(int col = 0; col < 59; col++){
                
                if(map[col][row] == 8){
                    if(map[col][row] == map[col][row+1]){
                        gp.obj[i]= new OBJ_Door2_up(gp);
                        gp.obj[i].worldX = col * gp.tileSize;
                        gp.obj[i].worldY = row * gp.tileSize;
                        i++;
                        gp.obj[i]= new OBJ_Door2_down(gp);
                        gp.obj[i].worldX = col * gp.tileSize;
                        gp.obj[i].worldY = (row+1) * gp.tileSize;
                        i++;
                    
                    
                    }
                    if(map[col][row] == map[col+1][row]){
                        gp.obj[i]= new OBJ_Door1_left(gp);
                        gp.obj[i].worldX = col * gp.tileSize;
                        gp.obj[i].worldY = row * gp.tileSize;
                        i++;
                        gp.obj[i]= new OBJ_Door1_right(gp);
                        gp.obj[i].worldX = (col+1) * gp.tileSize;
                        gp.obj[i].worldY = row * gp.tileSize;
                        i++;
                    
                    
                    }
                }  
            }
        }
        return i+1;
    }
}
