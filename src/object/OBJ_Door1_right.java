package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door1_right extends Entity{
    
    public OBJ_Door1_right(GamePanel gp){
        
        super(gp);
        
        name = "Door1_right";
        down1 = setup("/objects/door1_right1", gp.tileSize, gp.tileSize);
        
        collision= true;
        
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
    }
}
