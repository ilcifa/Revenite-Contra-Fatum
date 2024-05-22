package entity;

import main.GamePanel;

public class Projectile extends Entity{
    
    Entity user;
            
    public Projectile(GamePanel gp) {
        super(gp);
    }
    
    public void set(int worldX, int worldY, String direction, boolean isDied, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.isDied = isDied;
        this.user = user;
        this.life = this.maxLife;
    }
    
    @Override
    public void update(){
        
        if(user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true){
                damagePlayer(attack);
                isDied = true;
            }
        }
        
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        if(collisionOn == true){
            life = 0;
            collisionOn = false;
        }
        
        switch(direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        
        life--;
        
        if(life <= 0){
            isDied = true;
        }
    }
}
