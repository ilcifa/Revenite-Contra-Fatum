package main;

public class EventHandler {
    
    GamePanel gp;
    EventRect eventRect[][];
    
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    
    public EventHandler(GamePanel gp){
        this.gp = gp;
        
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        
        int col = 0, row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 0;
            eventRect[col][row].y = 0;
            eventRect[col][row].width = 48;
            eventRect[col][row].height = 48;
            eventRect[col][row].eventRectDefaultX= eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY= eventRect[col][row].y;
            
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    
    public void checkEvent(){
        
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        
        if(canTouchEvent == true){
            /*if(hit(x, y, dir) == true){
                evento(col, row);
            }*/
            int col = 0, row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                
                int tileNum = gp.tileM.mapTileNum[col][row];
                if(gp.tileM.tile[tileNum] == gp.tileM.tile[7]){
                    if(hit(col,row,"any") == true){
                        waterLife(col, row);
                    }
                }
                
                col++;
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            
            if(hit(32,29,"any") == true){
                lightingOn(32,29);
            }
            if(hit(33,29,"any") == true){
                lightingOn(33,29);
            }
            
            if(hit(32,27,"any") == true){
                lightingOff(32,27);
            }
            if(hit(33,27,"any") == true){
                lightingOff(33,27);
            }
            
            if(hit(14,20,"any") == true){
                checkpoint(14,20);
            }
            if(hit(15,20,"any") == true){
                checkpoint(15,20);
            }
            
            if(hit(37,44,"any") == true){
                checkpoint(37,44);
            }
            if(hit(37,45,"any") == true){
                checkpoint(37,45);
            }
            
            if(hit(43,36,"any") == true){
                checkpoint(43,36);
            }
            if(hit(43,37,"any") == true){
                checkpoint(43,37);
            }
            
            if(hit(46,22,"any") == true){
                checkpoint(46,22);
            }
            if(hit(47,22,"any") == true){
                checkpoint(47,22);
            }
            
            if(hit(34,16,"any") == true){
                checkpoint(34,16);
            }
            if(hit(34,17,"any") == true){
                checkpoint(34,17);
            }
            
            if(hit(32,29,"any") == true){
                checkpoint(32,29);
            }
            if(hit(33,29,"any") == true){
                checkpoint(33,29);
            }
            
        }
        
    }
    public boolean hit(int col,int row, String reqDirection){
        
        boolean hit = false;
        
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
        
        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        
        return hit;
    }
    
    /*public void evento(int col, int row){
        contenuto
        eventRect[col][row].eventDone = true; (se si ripete una sola volta)
        canTouchEvent = false; (solo se si può fare quando vado e torno)
    }*/
    
    public void waterLife(int col, int row){
        if(gp.waterCounter > 45){
            gp.player.life++;
            gp.playSE(13);
            gp.waterCounter = 0;
            canTouchEvent = true;
        }
    }
    public void checkpoint(int col, int row){
        gp.saveLoad.save();
        gp.ui.showMessage("CHECKPOINT");
    }
    
    public void lightingOn(int col, int row){
        gp.player.lighting = true;
    }
    public void lightingOff(int col, int row){
        gp.player.lighting = false;
    }
    
}
