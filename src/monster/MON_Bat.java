package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;

public class MON_Bat extends Entity{
    
    public MON_Bat(GamePanel gp) {
        super(gp);
        
        type = 2;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 1;
        
        solidArea.width --;
        solidArea.height --;
        
        getImage();
        
    }
    
    public void getImage(){
        
        up1 = setup("/monster/bat_left1", gp.tileSize, gp.tileSize/2);
        up2 = setup("/monster/bat_left2", gp.tileSize, gp.tileSize/2);
        up3 = setup("/monster/bat_left1", gp.tileSize, gp.tileSize/2);
        up4 = setup("/monster/bat_left2", gp.tileSize, gp.tileSize/2);
        
        down1 = setup("/monster/bat_right1", gp.tileSize, gp.tileSize/2);
        down2 = setup("/monster/bat_right2", gp.tileSize, gp.tileSize/2);
        down3 = setup("/monster/bat_right1", gp.tileSize, gp.tileSize/2);
        down4 = setup("/monster/bat_right2", gp.tileSize, gp.tileSize/2);
        
        left1 = setup("/monster/bat_left1", gp.tileSize, gp.tileSize/2);
        left2 = setup("/monster/bat_left2", gp.tileSize, gp.tileSize/2);
        left3 = setup("/monster/bat_left1", gp.tileSize, gp.tileSize/2);
        left4 = setup("/monster/bat_left2", gp.tileSize, gp.tileSize/2);
        
        right1 = setup("/monster/bat_right1", gp.tileSize, gp.tileSize/2);
        right2 = setup("/monster/bat_right2", gp.tileSize, gp.tileSize/2);
        right3 = setup("/monster/bat_right1", gp.tileSize, gp.tileSize/2);
        right4 = setup("/monster/bat_right2", gp.tileSize, gp.tileSize/2);
    }
    
    @Override
    public void update(){
        
        super.update();
        
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        
        if(onPath == false && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            
            if(i > 50){
                onPath = true;
            }
        }
    }
    
    @Override
    public void setAction(){
        
        if(onPath == true){
            
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            
            searchPath(goalCol,goalRow);
        }
        else{
            actionLockCounter++;
        
            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if(i <= 25){
                    direction = "up";
                }
                if(i > 25 && i <= 50){
                    direction = "down";
                }
                if(i > 50 && i <= 75){
                    direction = "left";
                }
                if(i > 75 && i <= 100){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    
    @Override
    public void damageReaction(){
        
        actionLockCounter = 0;
        onPath = true;
    }
}
