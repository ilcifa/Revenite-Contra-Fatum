package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;

public class MON_Zombie extends Entity{
    
    public MON_Zombie(GamePanel gp) {
        super(gp);
        
        type = 2;
        name = "Zombie";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 1;
        
        solidArea.width --;
        solidArea.height --;
                
        getImage();
        
    }
    
    public void getImage(){
        
        up1 = setup("/monster/zombie_left1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/zombie_left2", gp.tileSize, gp.tileSize);
        up3 = setup("/monster/zombie_left3", gp.tileSize, gp.tileSize);
        up4 = setup("/monster/zombie_left4", gp.tileSize, gp.tileSize);
        
        down1 = setup("/monster/zombie_right1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/zombie_right2", gp.tileSize, gp.tileSize);
        down3 = setup("/monster/zombie_right3", gp.tileSize, gp.tileSize);
        down4 = setup("/monster/zombie_right4", gp.tileSize, gp.tileSize);
        
        left1 = setup("/monster/zombie_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/zombie_left2", gp.tileSize, gp.tileSize);
        left3 = setup("/monster/zombie_left3", gp.tileSize, gp.tileSize);
        left4 = setup("/monster/zombie_left4", gp.tileSize, gp.tileSize);
        
        right1 = setup("/monster/zombie_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/zombie_right2", gp.tileSize, gp.tileSize);
        right3 = setup("/monster/zombie_right3", gp.tileSize, gp.tileSize);
        right4 = setup("/monster/zombie_right4", gp.tileSize, gp.tileSize);
    }
    
    @Override
    public void update(){
        
        super.update();
        
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        
        if(onPath == false && tileDistance < 3){
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
