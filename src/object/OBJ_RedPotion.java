package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RedPotion extends Entity{
    
    public OBJ_RedPotion(GamePanel gp) {
        super(gp);
        type = 3;
        
        name = "Red Potion";
        down1 = setup("/objects/RedPotion", gp.tileSize, gp.tileSize);
        description = "[ "+name+" ]\nIt gives you a full\nheart.";
    }
    
    @Override
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the "+name+"!\nYour life has been recovered by a heart.";
        gp.player.life += 4;
        gp.player.RedPotion--;
        gp.keyH.exitDialogue = true;
    }
    
}
