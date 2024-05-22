package main.tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Map extends TileManager{
    
    GamePanel gp;
    BufferedImage worldMap;
    public boolean miniMapOn = true;
    
    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }
    
    public void createWorldMap(){
        
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;
        
        worldMap = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)worldMap.createGraphics();
        
        int col = 0, row = 0;
        
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            
            int tileNum = mapTileNum[col][row];
            if(tile[tileNum] != tile[3]){
                int x = gp.tileSize*col;
                int y = gp.tileSize*row;
                g2.drawImage(tile[tileNum].image, x, y, null);
            }
            
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    
    public void drawMiniMap(Graphics2D g2){
        
        if(miniMapOn == true && gp.gameState == gp.playState){
            int width = 350;
            int height = 350;
            int x = 20-gp.tileSize;
            int y = gp.tileSize*5;
            
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(x+55, y+55, width-111, height-111);
            g2.drawImage(worldMap, x, y, width, height, null);
            
            double scale = (double)(gp.tileSize * gp.maxWorldCol)/width;
            int playerX = (int)(x + gp.player.worldX/scale);
            int playerY = (int)(y + gp.player.worldY/scale);
            int playerSize = (int)(gp.tileSize/3);
            g2.drawImage(gp.player.right1, playerX-6, playerY-6, playerSize, playerSize, null);
            
        }
    }
}
