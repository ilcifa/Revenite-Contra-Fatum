package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
    
    public OBJ_Heart(GamePanel gp){
        
        super(gp);
        
        name = "Heart";
        image = setup("/objects/Life", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/Life-1", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/Life-2", gp.tileSize, gp.tileSize);
        image4 = setup("/objects/Life-3", gp.tileSize, gp.tileSize);
        
    }
}
