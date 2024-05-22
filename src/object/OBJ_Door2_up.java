package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door2_up extends Entity{
    
    public OBJ_Door2_up(GamePanel gp){
        
        super(gp);
        
        name = "Door2_up";
        down1 = setup("/objects/door2_up1", gp.tileSize, gp.tileSize);
        
        collision= true;
        
        solidArea.x = 15;
        solidArea.y = 0;
        solidArea.width = 18;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
