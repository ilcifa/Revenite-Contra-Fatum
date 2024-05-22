package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Lighting {
    
    GamePanel gp;
    BufferedImage darknessFilter;
    
    public Lighting(GamePanel gp, int circleSize){
        
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
        
        int centerX = gp.player.screenX +(gp.tileSize/2);
        int centerY = gp.player.screenY +(gp.tileSize/2);
        
        Color color[] = new Color[5];
        float fraction[] = new float[5];
        
        color[0] = new Color(0,0,0,0f);
        color[1] = new Color(0,0,0,0.25f);
        color[2] = new Color(0,0,0,0.50f);
        color[3] = new Color(0,0,0,0.75f);
        color[4] = new Color(0,0,0,0.95f);
        
        fraction[0] = 0f;
        fraction[1] = 0.25f;
        fraction[2] = 0.5f;
        fraction[3] = 0.75f;
        fraction[4] = 1f;
        
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);
        g2.setPaint(gPaint);
        
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        
        g2.dispose();
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
