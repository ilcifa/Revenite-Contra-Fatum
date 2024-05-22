package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{
    
    public OBJ_Shield(GamePanel gp) {
        super(gp);
        
        name = "Shield";
        down1 = setup("/objects/Shield", gp.tileSize, gp.tileSize);
        
        description = "[ "+name+" ]\nMade by Wood.\nResistent.";
    }
    
}
