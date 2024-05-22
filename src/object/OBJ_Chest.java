package object;

import entity.Entity;
import java.util.ArrayList;
import main.GamePanel;

public class OBJ_Chest extends Entity{
    
    public OBJ_Chest(GamePanel gp){
        
        super(gp);
        
        name = "Chest";
        down1 = setup("/objects/chest1", gp.tileSize, gp.tileSize);
        down2 = setup("/objects/chest2", gp.tileSize, gp.tileSize);
        
        collision = true;
        
        chestInventory = new ArrayList<>();
        
    }
    
    @Override
    public void fillChest(ArrayList<Entity> loot){
        for(int i = 0; i < loot.size(); i++){
            chestInventory.add(loot.get(i));
        }
    }
}
