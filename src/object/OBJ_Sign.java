package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sign extends Entity{
    
    public OBJ_Sign(GamePanel gp){
        
        super(gp);
        
        name = "Sign";
        down1 = setup("/objects/sign", gp.tileSize, gp.tileSize);
        
        setDialogue();
        
        solidArea.width += (gp.tileSize*2); 
    }
    
    public void setDialogue(){
        
        dialogues[0] = "Welcome in Revenite Contra Fatum...\nOpen the chest and enter in the dungeon...";
        dialogues[1] = "ATTENTION: this dungeon is full of monster";
        dialogues[2] = "The water refill your life"; 
    }
    
    @Override
    public void speak(GamePanel gp){
        if(reset == false){
            resetDialogue = dialogueIndex;
            reset = true;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if(dialogues[dialogueIndex-1].charAt(dialogues[dialogueIndex-1].length()-1) != '.'){
            gp.keyH.exitDialogue = true;
            dialogueIndex = resetDialogue;
            
        }
    }
}
