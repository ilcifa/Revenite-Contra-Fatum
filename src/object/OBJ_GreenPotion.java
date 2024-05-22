package object;

import entity.Entity;
import java.awt.Color;
import main.GamePanel;

public class OBJ_GreenPotion extends Entity{
    
    public OBJ_GreenPotion(GamePanel gp) {
        super(gp);
        type = 3;
        
        name = "Green Potion";
        down1 = setup("/objects/GreenPotion", gp.tileSize, gp.tileSize);
        description = "[ "+name+" ]\nIt gives you a bonus\nspeed.";
    }
    
    @Override
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the "+name+"!\nYour speed has been increased.";
        gp.player.speed += 2;
        gp.keyH.exitDialogue = true;
        gp.potionGreenDebug = 0;
        gp.player.GreenPotion--;
    }
    
}
