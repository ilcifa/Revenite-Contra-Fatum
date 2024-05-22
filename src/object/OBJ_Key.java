package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
    
    public OBJ_Key(GamePanel gp){
        
        super(gp);
        
        type = 3;
        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        down2 = setup("/objects/keyVertical", gp.tileSize, gp.tileSize);
        
        description = "[ "+name+" ]\nIt opens a door.";
        
    }
}
