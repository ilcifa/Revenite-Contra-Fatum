package object;

import entity.Entity;
import java.awt.Color;
import main.GamePanel;

public class OBJ_BluePotion extends Entity{
    
    public OBJ_BluePotion(GamePanel gp) {
        super(gp);
        type = 3;
        
        name = "Blue Potion";
        down1 = setup("/objects/BluePotion", gp.tileSize, gp.tileSize);
        description = "[ "+name+" ]\nIt gives you a bonus\nattack.";
    }
    
    @Override
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the "+name+"!\nYour attack has been increased.";
        gp.player.attack += 2;
        gp.keyH.exitDialogue = true;
        gp.potionBlueDebug = 0;
        gp.player.BluePotion--;
    }
    
}
