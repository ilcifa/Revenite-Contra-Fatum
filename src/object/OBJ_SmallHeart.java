package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SmallHeart extends Entity{
    
    public OBJ_SmallHeart(GamePanel gp) {
        super(gp);
        
        name = "Small Heart";
        down1 = setup("/objects/Life-3", gp.tileSize/2, gp.tileSize/2);
        
    }
    
    @Override
    public void use(Entity entity){
        gp.ui.showMessage("You receive life!");
        gp.player.life++;
    }
    
}
