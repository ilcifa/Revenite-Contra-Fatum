package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon extends Entity{
    
    public OBJ_Weapon(GamePanel gp) {
        super(gp);
        
        name = "Sword";
        down1 = setup("/objects/Sword", gp.tileSize, gp.tileSize);
        description = "[ "+name+" ]\nAn old Sworld.\nDamage = 2.";
    }
    
}
