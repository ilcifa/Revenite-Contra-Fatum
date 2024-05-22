package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Arrow extends Projectile{
    GamePanel gp;
    
    public OBJ_Arrow(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Arrow";
        speed = 3;
        maxLife = 120;
        life = maxLife;
        attack = 2;
        isDied = true;
        getImage();
    }
    
    public void getImage(){
        up1 = setup("/projectiles/arrow_up", gp.tileSize, gp.tileSize);
        down1 = setup("/projectiles/arrow_down", gp.tileSize, gp.tileSize);
        left1 = setup("/projectiles/arrow_left", gp.tileSize, gp.tileSize);
        right1 = setup("/projectiles/arrow_right", gp.tileSize, gp.tileSize);
    }
}
