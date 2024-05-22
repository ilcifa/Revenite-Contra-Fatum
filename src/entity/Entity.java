package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_SmallHeart;

public class Entity {
    
    public GamePanel gp;
    
    public int worldX, worldY;
    public int speed;
    
    public BufferedImage up1, up2, up3, up4;
    public BufferedImage down1, down2, down3, down4;
    public BufferedImage left1, left2, left3, left4;
    public BufferedImage right1, right2, right3, right4;
    
    public BufferedImage attackUp1, attackUp2;
    public BufferedImage attackDown1, attackDown2;
    public BufferedImage attackLeft1, attackLeft2;
    public BufferedImage attackRight1, attackRight2;
    public boolean attacking = false;
    
    public BufferedImage died;
    public boolean isDied = false, dying = false;
    public boolean onPath = false;
    int dyingCounter;
    
    public String direction = "down";
    
    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    
    public int actionLockCounter;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    
    public String dialogues[] = new String[100];
    public int dialogueIndex = 0;
    public int resetDialogue;
    public boolean reset = false;
    
    public boolean knockBack = false;
    public int defaultSpeed;
    public int knockBackCounter = 0;
    
    public BufferedImage image, image2, image3, image4;
    public String name;
    public boolean collision = false;
    public int type; //0 = player, 1 = npc, 2 = monster, 3 = consumable
    
    public int maxLife, life;
    
    public Entity currentWeapon;
    public Entity currentShield;
    public String description = "";
    
    public int attack;
    
    public Projectile projectile;
    public int shotAvailableCounter;
    
    public boolean isOpen = false;
    public ArrayList<Entity> chestInventory = new ArrayList<>();
    
    public Entity(GamePanel gp){
        this.gp = gp;
    }
    
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            
            switch(direction){
                case "up":
                    if(spriteNum == 1){ image = up1; }
                    if(spriteNum == 2){ image = up2; }
                    if(spriteNum == 3){ image = up3; }
                    if(spriteNum == 4){ image = up4; }
                    break;
                    
                case "down":
                    if(spriteNum == 1){ image = down1; }
                    if(spriteNum == 2){ image = down2; }
                    if(spriteNum == 3){ image = down3; }
                    if(spriteNum == 4){ image = down4; }
                    break;
                    
                case "left":
                    if(spriteNum == 1){ image = left1; }
                    if(spriteNum == 2){ image = left2; }
                    if(spriteNum == 3){ image = left3; }
                    if(spriteNum == 4){ image = left4; }
                    break;
                    
                case "right":
                    if(spriteNum == 1){ image = right1; }
                    if(spriteNum == 2){ image = right2; }
                    if(spriteNum == 3){ image = right3; }
                    if(spriteNum == 4){ image = right4; }
                    break;
            }
            
            if(invincible == true){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            }
            if(dying == true){
                dyingAnimation(g2);
            }
            
            if(isOpen == true){
                image = down2;
            }
            
            if(name.equals("Bat")){
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize/2, null);
            }
            else{
                g2.drawImage(image, screenX, screenY, null);
            }
            
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    
    public void damagePlayer(int attack){
        if(gp.player.invincible == false){
            gp.playSE(9);
            gp.player.life -= attack;
            gp.player.invincible = true;
        }
    }
    
    public void dyingAnimation(Graphics2D g2){
        
        dyingCounter++;
        if(dyingCounter < 5){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 5 && dyingCounter <= 10){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 10 && dyingCounter <= 15){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 15 && dyingCounter <= 20){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 20 && dyingCounter <= 25){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 25 && dyingCounter <= 30){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 30 && dyingCounter <= 35){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 35 && dyingCounter <= 40){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 40){
            isDied = true;
        }
    }
    
    public void changeAlpha(Graphics2D g2, float alphaValue){
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    public BufferedImage setup(String imageName, int width, int height){
        
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res"+ imageName +".png"));
            image = uTool.scaleImage(image, width, height);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return image;
    }
    
    public void setAction(){}
    
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        //gp.cChecker.checkEntity(this,gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        
        if(this.type == 2 && contactPlayer == true){
            damagePlayer(attack);
        }
    }
    
    public void update(){
        
        if(knockBack == true){
            checkCollision();
            
            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false){
                switch(gp.player.direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            } 
            
            knockBackCounter++;
            if(knockBackCounter == 5){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            
        }
        else{
            setAction();
            checkCollision();
        
            if(collisionOn == false){
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
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
        
        if(invincible == true){
            invincibleCounter ++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }
    public void checkDrop(){
        
        int i = new Random().nextInt(100)+1;
        
        if(i <= 25){
            dropItem(new OBJ_SmallHeart(gp));
        }
    }
    
    public void dropItem(Entity droppedItem){
        
        for(int i = 0; i< gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }
    
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    
    public void searchPath(int goalCol, int goalRow){
        
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;
        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        
        if(gp.pFinder.search() == true){
            
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            
            int currentX = worldX;
            int currentY = worldY;
            
            if(currentY > nextY){
                direction = "up";
            }
            else if(currentY < nextY){
                direction = "down";
            }
            else if(currentX > nextX && currentY == nextY){
                direction = "left";
            }
            else if(currentX < nextX && currentY == nextY){
                direction = "right";
            }
            checkCollision();
            if(collisionOn == true){
                if(gp.pFinder.pathList.size() > 1){
                    int nextNextX, nextNextY;
                    nextNextX = gp.pFinder.pathList.get(1).col * gp.tileSize;
                    nextNextY = gp.pFinder.pathList.get(1).row * gp.tileSize;

                    if(nextY > nextNextY){
                        direction = "up";
                        if(nextX < nextNextX){
                            direction = "right";
                        }
                        if(nextX > nextNextX){
                            direction = "left";
                        }

                    }
                    else if(nextY < nextNextY){
                        direction = "down";
                        if(nextX < nextNextX){
                            direction = "right";
                        }
                        if(nextX > nextNextX){
                            direction = "left";
                        }

                    }
                    else if(nextX > nextNextX){
                        worldX--;
                        direction = "left";
                        if(nextY < nextNextY){
                            direction = "down";
                        }
                        if(nextY > nextNextY){
                            direction = "up";
                        }

                    }
                    else if(nextX < nextNextX && nextY == nextNextY){
                        worldX++;
                        direction = "right";
                        if(nextY < nextNextY){
                            direction = "down";
                        }
                        if(nextY > nextNextY){
                            direction = "up";
                        }

                    }
                }
                
                if(Math.abs(currentX - nextX) > 1 && Math.abs(currentY - nextY) > 1){
                    if(Math.abs(currentX - nextX) > 1){
                        if(currentY > nextY){
                            direction = "up";
                        }
                        if(currentY < nextY){
                            direction = "down";
                        }
                    }
                    if(Math.abs(currentY - nextY) > 1){
                        if(currentX > nextX){
                            direction = "left";
                        }
                        if(currentX < nextX){
                            direction = "right";
                        }
                    }
                }
                if(Math.abs(currentX - nextX) == 1 || Math.abs(currentY - nextY) == 1){
                    if(Math.abs(currentX - nextX) > 1){
                        if(currentY > nextY){
                            worldY--;
                        }
                        if(currentY < nextY){
                            worldY++;
                        }
                    }
                    if(Math.abs(currentY - nextY) > 1){
                        if(currentX > nextX){
                            worldX-= 2;
                        }
                        if(currentX < nextX){
                            worldX++;
                        }
                    }
                }
            }
        }
    }
    
    public void damageReaction(){}
    public void speak(GamePanel gp){}
    public void use(Entity entity){}
    public void fillChest(ArrayList<Entity> loot){}
}
