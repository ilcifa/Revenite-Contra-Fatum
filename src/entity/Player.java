package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import object.OBJ_Shield;
import object.OBJ_Weapon;

public class Player extends Entity{
    
    KeyHandler keyH;
    MouseHandler mouseH;
    
    public int screenX;
    public int screenY;
    public int objIndex, monsterIndex, projectileIndex, chestIndex;
    
    public int defaultAttack;
    public int Key, RedPotion, BluePotion, GreenPotion;
    
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    
    public boolean lighting = false;
    
    public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH){
        
        super(gp);
        
        type = 0;
        this.keyH= keyH;
        this.mouseH = mouseH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 3;
        solidArea.y = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 42;
        solidArea.height = 21;
        
        attackArea.width = 30;
        attackArea.height = 30;
        
        currentWeapon = new OBJ_Weapon(gp);
        currentShield = new OBJ_Shield(gp);
        
        setDefaultValues();
    }
    
    public void setDefaultValues(){
        
        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 12;
        defaultSpeed = 4;
        speed = defaultSpeed;
        isDied = false;
        
        Key= 0;
        RedPotion= 0;
        BluePotion= 0;
        GreenPotion= 0;
        
        defaultAttack = 2;
        attack = defaultAttack;
        maxLife = 12;
        life = maxLife;
        lighting = false;
        
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    
    public void setDefaultPosition(){
        direction = "right";
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 2;
    }
    
    public void restoreStatus(){
        isDied = false;
        life = maxLife;
        invincible = false;
        attacking = false;
        knockBack = false;
        
    }
    
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    
    public void getPlayerImage(){
        
        up1= setup("/player/PrimaryKnight_up1", gp.tileSize, gp.tileSize);
        up2= setup("/player/PrimaryKnight_up2", gp.tileSize, gp.tileSize);
        up3= setup("/player/PrimaryKnight_up3", gp.tileSize, gp.tileSize);
        up4= setup("/player/PrimaryKnight_up4", gp.tileSize, gp.tileSize);
        
        down1= setup("/player/PrimaryKnight_down1", gp.tileSize, gp.tileSize);
        down2= setup("/player/PrimaryKnight_down2", gp.tileSize, gp.tileSize);
        down3= setup("/player/PrimaryKnight_down3", gp.tileSize, gp.tileSize);
        down4= setup("/player/PrimaryKnight_down4", gp.tileSize, gp.tileSize);
        
        left1= setup("/player/PrimaryKnight_left1", gp.tileSize+12, gp.tileSize);
        left2= setup("/player/PrimaryKnight_left2", gp.tileSize+12, gp.tileSize);
        left3= setup("/player/PrimaryKnight_left3", gp.tileSize+12, gp.tileSize);
        left4= setup("/player/PrimaryKnight_left4", gp.tileSize+12, gp.tileSize);
        
        right1= setup("/player/PrimaryKnight_right1", gp.tileSize, gp.tileSize);
        right2= setup("/player/PrimaryKnight_right2", gp.tileSize, gp.tileSize);
        right3= setup("/player/PrimaryKnight_right3", gp.tileSize, gp.tileSize);
        right4= setup("/player/PrimaryKnight_right4", gp.tileSize, gp.tileSize);
        
        died= setup("/player/PrimaryKnight_died", gp.tileSize, gp.tileSize);
    }
    
    public void getPlayerAttackImage(){
        attackUp1= setup("/player/PrimaryKnightAttack_up1", gp.tileSize+12, gp.tileSize+12);
        attackUp2= setup("/player/PrimaryKnightAttack_up2", gp.tileSize+12, gp.tileSize+12);
        attackDown1= setup("/player/PrimaryKnightAttack_down1", gp.tileSize+12, gp.tileSize+18);
        attackDown2= setup("/player/PrimaryKnightAttack_down2", gp.tileSize+12, gp.tileSize+18);
        attackLeft1= setup("/player/PrimaryKnightAttack_left1", gp.tileSize+18, gp.tileSize+12);
        attackLeft2= setup("/player/PrimaryKnightAttack_left1", gp.tileSize+18, gp.tileSize+12);
        attackRight1= setup("/player/PrimaryKnightAttack_right1", gp.tileSize+12, gp.tileSize+12);
        attackRight2= setup("/player/PrimaryKnightAttack_right1", gp.tileSize+12, gp.tileSize+12);
    }
    
    @Override
    public void update(){
        
        if(attacking == true){
            attacking();
        }
        
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.ePressed == true){
            
            if(keyH.upPressed == true){
                direction= "up";
            }
            if(keyH.downPressed == true){
                direction= "down";
            }
            if(keyH.leftPressed == true){
                direction= "left";
            }
            if(keyH.rightPressed == true){
                direction= "right";
            }
            
            collisionOn = false;
            gp.cChecker.checkTile(this);
            objIndex= gp.cChecker.checkObject(this, true);
            monsterIndex= gp.cChecker.checkEntity(this, gp.monster);
            projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);
            
            try {
                pickUpObject(objIndex);
                contactMonster(monsterIndex);
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            gp.eHandler.checkEvent();
            
            if(collisionOn == false && keyH.ePressed == false){
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if (spriteNum != 4){
                    spriteNum++;
                }
                else{
                    spriteNum = 1;
                }
                spriteCounter = 0;

            }
        }
        else{
            spriteNum = 1;
        }
        
        chestIndex= gp.cChecker.checkObject(this, true);
        if(chestIndex != 999){
            if(gp.obj[chestIndex].name.equals("Chest")){
                gp.ui.showMessage("OPEN CHEST: E");
                if(keyH.ePressed == true){
                    gp.obj[chestIndex].isOpen = true;
                    gp.playSE(12);
                    gp.gameState = gp.chestState;
                }
            }
        }
            
        if(life > maxLife){
            life = maxLife;
        }
        if(life <= 0){
            isDied = true;
            gp.stopMusic();
            gp.playSE(4);
            gp.ui.commandNum= 0;
            gp.gameState = gp.gameOverState;
            
        }
        
        if(invincible == true){
            invincibleCounter ++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        if(mouseH.attackPressed == true){
            attacking = true;
        }
        
        
    }
    
    public void attacking(){
        
        spriteCounter++;
        
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            switch(direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);
            
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    
    public void pickUpObject(int i) throws IOException{
        
        if(i != 999){
            
            String objectName = gp.obj[i].name;
            
            if(gp.obj[i].type == 3){    
                if(inventory.size() != maxInventorySize){
                    inventory.add(gp.obj[i]);
                    gp.ui.showMessage("You got a "+objectName+"!");
                    if(objectName.equals("Key")){
                        gp.playSE(1);
                        Key++;
                    }
                    if(objectName.equals("Red Potion")){
                        gp.playSE(7);
                        RedPotion++;
                    }
                    if(objectName.equals("Blue Potion")){
                        gp.playSE(7);
                        BluePotion++;
                    }
                    if(objectName.equals("Green Potion")){
                        gp.playSE(7);
                        GreenPotion++;
                    }
                    gp.obj[i] = null;
                }
                else{
                    gp.ui.showMessage("Inventory is full!");
                }
            }
            if(objectName.equals("Small Heart")){
                gp.playSE(13);
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }
            
            switch(objectName){
                case "Door1_left":
                    if(Key>0){
                        gp.playSE(2);
                        gp.obj[i]= null;
                        gp.obj[i+1]= null;
                        Key--;
                        gp.ui.showMessage("You opened the door!");
                        for(int j=0; j<inventory.size(); j++){
                            if(inventory.get(j).name.equals("Key")){
                                inventory.remove(j);
                                break;
                            }
                        }
                    }
                    else{
                        gp.ui.showMessage("The door is locked!");
                        if(gp.debug > 45){
                            gp.debug = 0;
                            gp.playSE(3);
                        }
                        
                    }
                    break;
                case "Door1_right":
                    if(Key>0){
                        gp.playSE(2);
                        gp.obj[i]= null;
                        gp.obj[i-1]= null;
                        Key--;
                        gp.ui.showMessage("You opened the door!");
                        for(int j=0; j<inventory.size(); j++){
                            if(inventory.get(j).name.equals("Key")){
                                inventory.remove(j);
                                break;
                            }
                        }
                    }
                    else{
                        gp.ui.showMessage("The door is locked!");
                        if(gp.debug > 45){
                            gp.debug = 0;
                            gp.playSE(3);
                        }
                    }
                    break;
                case "Door2_up":
                    if(Key>0){
                        gp.playSE(2);
                        gp.obj[i]= null;
                        gp.obj[i+1]= null;
                        
                        Key--;
                        gp.ui.showMessage("You opened the door!");
                        for(int j=0; j<inventory.size(); j++){
                            if(inventory.get(j).name.equals("Key")){
                                inventory.remove(j);
                                break;
                            }
                        }
                    }
                    else{
                        gp.ui.showMessage("The door is locked!");
                        if(gp.debug > 45){
                            gp.debug = 0;
                            gp.playSE(3);
                        }
                    }
                    break;
                case "Door2_down":
                    if(Key>0){
                        gp.playSE(2);
                        gp.obj[i]= null;
                        gp.obj[i-1]= null;
                        Key--;
                        gp.ui.showMessage("You opened the door!");
                        for(int j=0; j<inventory.size(); j++){
                            if(inventory.get(j).name.equals("Key")){
                                inventory.remove(j);
                                break;
                            }
                        }
                    }
                    else{
                        gp.ui.showMessage("The door is locked!");
                        if(gp.debug > 45){
                            gp.debug = 0;
                            gp.playSE(3);
                        }
                    }
                    break;
                case "Sign":
                    gp.ui.showMessage("READ SIGN: E");
                    if(keyH.ePressed == true){
                        gp.gameState = gp.dialogueState;
                        gp.obj[i].speak(gp);
                        break;
                    }
                    
            }
        }
        
    }
    
    public void contactMonster(int i){
            
        if(i != 999){
            
            if(invincible == false && gp.monster[i].dying == false){
                
                life -= gp.monster[i].attack;
                gp.playSE(9);
                
                invincible = true;
            }
        }
    }
    
    public void damageMonster(int i, int attack){
        
        if(i != 999){
            if(gp.debug > 30){
                gp.playSE(6);
                gp.debug = 0;
            }
            if(gp.monster[i].invincible == false){
                
                gp.monster[i].life -= attack;
                generateParticle(this, gp.monster[i]);
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                knockBack(gp.monster[i]);
                
                if(gp.monster[i].life <= 0){
                    switch(gp.monster[i].name){
                        case "Zombie":
                            gp.ui.showMessage("You killed a zombie!");
                            break;
                        case "Skeleton":
                            gp.ui.showMessage("You killed a skeleton!");
                            break;
                        case "Bat":
                            gp.ui.showMessage("You killed a bat!");
                            break;
                    }
                    gp.monster[i].dying = true;
                }
            }
        }
        else{
            if(gp.debug > 30){
                gp.playSE(5);
                gp.debug = 0;
            }
        }
    }
    
    public void damageProjectile(int i){
        
        if(i != 999){
            Entity projectile = gp.projectile[i];
            projectile.isDied = true;
            generateParticle(this,projectile);
        }
    }
    
    public void selectedItem(){
        
        int itemIndex = gp.ui.getItemIndexOnSlot();
        
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            
            if(selectedItem.type == 3){
                if(selectedItem.name.equals("Red Potion") || selectedItem.name.equals("Blue Potion") || selectedItem.name.equals("Green Potion")){
                    gp.playSE(8);
                }
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    
    public void addItem(){
        
        int itemIndex = gp.ui.getItemIndexOnSlot();
        
        if(itemIndex < gp.obj[chestIndex].chestInventory.size()){
            Entity selectedItem = gp.obj[chestIndex].chestInventory.get(itemIndex);
            
            if(selectedItem.type == 3){
                if(inventory.size() != maxInventorySize){
                    inventory.add(selectedItem);
                    if(selectedItem.name.equals("Key")){
                        gp.playSE(1);
                        Key++;
                    }
                    if(selectedItem.name.equals("Red Potion")){
                        gp.playSE(7);
                        RedPotion++;
                    }
                    if(selectedItem.name.equals("Blue Potion")){
                        gp.playSE(1);
                        BluePotion++;
                    }
                    if(selectedItem.name.equals("Green Potion")){
                        gp.playSE(1);
                        GreenPotion++;
                    }
                    gp.obj[chestIndex].chestInventory.remove(itemIndex);
                }
                else{
                    gp.ui.showMessage("Inventory is full!");
                }
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        if(isDied == false){
            switch(direction){
                case "up":
                    if(gp.keyH.ePressed == false){
                        if(attacking == false){
                            if(spriteNum == 1){ image = up1; }
                            if(spriteNum == 2){ image = up2; }
                            if(spriteNum == 3){ image = up3; }
                            if(spriteNum == 4){ image = up4; }
                        }
                        if(attacking == true){
                            tempScreenX = screenX-9;
                            tempScreenY = screenY-9;
                            if(spriteNum == 1){ image = attackUp1; }
                            if(spriteNum == 2){ image = attackUp2; }
                        }
                    }
                    else{
                        image = up1;
                    }
                    break;
                    
                case "down":
                    if(gp.keyH.ePressed == false){
                        if(attacking == false){
                            if(spriteNum == 1){ image = down1; }
                            if(spriteNum == 2){ image = down2; }
                            if(spriteNum == 3){ image = down3; }
                            if(spriteNum == 4){ image = down4; }
                        }
                        if(attacking == true){
                            tempScreenX = screenX+3;
                            tempScreenY = screenY+6;
                            if(spriteNum == 1){ image = attackDown1; }
                            if(spriteNum == 2){ image = attackDown2; }
                        }
                    }
                    else{
                        image = down1;
                    }
                    break;
                    
                case "left":
                    if(gp.keyH.ePressed == false){
                        if(attacking == false){
                            if(spriteNum == 1){ image = left1; }
                            if(spriteNum == 2){ image = left2; }
                            if(spriteNum == 3){ image = left3; }
                            if(spriteNum == 4){ image = left4; }
                        }
                        if(attacking == true){
                            tempScreenX = screenX-3;
                            tempScreenY = screenY-12;
                            if(spriteNum == 1){ image = attackLeft1; }
                            if(spriteNum == 2){ image = attackLeft2; }
                        }
                    }
                    else{
                        image = left1;
                    }
                    break;
                    
                case "right":
                    if(gp.keyH.ePressed == false){
                        if(attacking == false){
                            if(spriteNum == 1){ image = right1; }
                            if(spriteNum == 2){ image = right2; }
                            if(spriteNum == 3){ image = right3; }
                            if(spriteNum == 4){ image = right4; }
                        }
                        if(attacking == true){
                            tempScreenX = screenX+3;
                            tempScreenY = screenY-15;
                            if(spriteNum == 1){ image = attackRight1; }
                            if(spriteNum == 2){ image = attackRight2; }
                        }
                    }
                    else{
                        image = right1;
                    }
                    break;
            }
        }
        else{
            image = died;
        }
        
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        }
        int width = gp.tileSize;
        int height = gp.tileSize;
        
        if(attacking == true){
            if("up".equals(direction) || "right".equals(direction)){
                width += 12;
                height += 12;
            }
            if("down".equals(direction)){
                width += 12;
                height += 18;
            }
            if("left".equals(direction)){
                width += 18;
                height += 12;
            }
        }
        else if("left".equals(direction)){
            width += 12;
        }
        
        g2.drawImage(image, tempScreenX, tempScreenY, width, height, null);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
    }
    
    @Override
    public Color getParticleColor(){
        Color color = null;
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        if(gp.monster[monsterIndex] != null){
            if(gp.monster[monsterIndex].name.equals("Zombie")){
                color = new Color(116,156,76);
            }
            if(gp.monster[monsterIndex].name.equals("Skeleton")){
                color = new Color(252,244,204);
            }
            if(gp.monster[monsterIndex].name.equals("Bat")){
                color = new Color(188,107,218);
            }
        }
        else{
            color = new Color(136,78,4);
        }
        return color;
    }
    @Override
    public int getParticleSize(){
        int size = 6;
        return size;
    }
    @Override
    public int getParticleSpeed(){
        int particleSpeed = 2;
        return particleSpeed;
    }
    @Override
    public int getParticleMaxLife(){
        int particleMaxLife = 10;
        return particleMaxLife;
    }
    
    public void knockBack(Entity entity){
        
        entity.direction = direction;
        entity.speed += 10;
        entity.knockBack = true;
    }
}
