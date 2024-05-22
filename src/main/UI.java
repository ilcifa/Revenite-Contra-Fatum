package main;

import entity.Entity;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font Garamond, Arial_30;
    BufferedImage keyImage;
    BufferedImage heart_full, heart_minus1, heart_minus2, heart_minus3;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0, saveNum = 0;
    public String currentDialogue = "";
    public int slotCol = 0, slotRow = 0;
    int subState = 0;
    
    public UI(GamePanel gp){
        this.gp = gp;
        
        try {
            
            InputStream is = getClass().getResourceAsStream("/res/font/Garamond.ttf");
            Garamond = Font.createFont(Font.TRUETYPE_FONT, is);
            
        } catch (FontFormatException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Arial_30 = new Font("Arial", Font.PLAIN, 30);
        Entity key = new OBJ_Key(gp);
        keyImage = key.down1;
        
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_minus1 = heart.image2;
        heart_minus2 = heart.image3;
        heart_minus3 = heart.image4;
    }
    
    public void showMessage(String text){
        
        message = text;
        messageOn = true;
    }
    
    public void draw(Graphics2D g2){
        
        this.g2 = g2;
        g2.setFont(Arial_30);
        g2.setColor(Color.white);
        
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        
        if(gp.gameState == gp.playState){
            drawPlayScreen();
        }
        
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        
        if(gp.gameState == gp.dialogueState){
            drawDialogScreen();
        }
        
        if(gp.gameState == gp.inventoryState){
            drawInventory();
        }
        
        if(gp.gameState == gp.optionState){
            drawOptionScreen();
        }
        
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        
        if(gp.gameState == gp.chestState){
            drawChestScreen();
        }
        
        if(gp.gameState == gp.saveState){
            drawSaveScreen();
        }
        
        if(gp.gameState == gp.quitState){
            drawQuitScreen();
        }
        
        if(gameFinished == true){
            
        }
        
    }
    
    public void drawTitleScreen(){
        
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        g2.setFont(Garamond);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        
        String title = "REVENITE";
        int x = getXforCenteredText(title);
        int y = gp.tileSize*2; 
        g2.setColor(Color.gray);
        g2.drawString(title, x+4, y+4);
        g2.setColor(Color.white);
        g2.drawString(title, x, y);
        title = "CONTRA FATUM";
        x = getXforCenteredText(title);
        y = gp.tileSize*4;
        g2.setColor(Color.gray);
        g2.drawString(title, x+4, y+4);
        g2.setColor(Color.white);
        g2.drawString(title, x, y);
        
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*1;
        g2.drawImage(gp.player.right1, x, y, gp.tileSize*2, gp.tileSize*2, null);
        
        g2.setFont(Arial_30);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        
        title = "NEW GAME";
        x = getXforCenteredText(title);
        y += gp.tileSize*3+20;
        g2.drawString(title, x, y);
        if(commandNum == 0){
            g2.drawString(">",x - gp.tileSize, y);
        }
        
        title = "LOAD GAME";
        x = getXforCenteredText(title);
        y += gp.tileSize*1;
        g2.drawString(title, x, y);
        if(commandNum == 1){
            g2.drawString(">",x - gp.tileSize, y);
        }
        
        title = "QUIT";
        x = getXforCenteredText(title);
        y += gp.tileSize*1;
        g2.drawString(title, x, y);
        if(commandNum == 2){
            g2.drawString(">",x - gp.tileSize, y);
        }
    }
    
    public void drawPlayScreen(){
        drawPlayerLife();
        
        int x = gp.tileSize/2+3;
        int y = gp.tileSize*2-20;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x, y, gp.tileSize*3, 10);
        g2.drawRect(x, y+20, gp.tileSize*3, 10);
        
        if(messageOn){

            g2.setColor(Color.white);
            if(gp.map.miniMapOn == true){
                g2.drawRect(gp.tileSize/2+2, gp.tileSize*6-35, 171, 19);
            }
            else{
                g2.drawRect(gp.tileSize/2+2, gp.tileSize*11-15, 171, 19);
            }
            g2.setColor(Color.black);
            if(gp.map.miniMapOn == true){
                g2.fillRect(gp.tileSize/2+3, gp.tileSize*6-34, 170, 18);
            }
            else{
                g2.fillRect(gp.tileSize/2+3, gp.tileSize*11-14, 170, 18);
            }
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.BOLD, 15));
            int textLenght = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();
            if(gp.map.miniMapOn == true){
                g2.drawString(message, gp.tileSize/2+88-textLenght/2, gp.tileSize*6-20);
            }
            else{
                g2.drawString(message, gp.tileSize/2+88-textLenght/2, gp.tileSize*11);
            }  
            messageCounter++;

            if(messageCounter > 60){
                messageCounter = 0;
                messageOn = false;
            }
        }
        drawInventorySlot();
        
        if(gp.player.attack != 2){
            colorBar("Blue");
        }
        if(gp.player.speed != 4){
            colorBar("Green");
        }
        
    }
    public void drawPauseScreen(){
        
        g2.setColor(new Color((float)0.0, (float)0.0, (float)0.0, (float)0.5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        String text = "PAUSE";
        g2.setColor(Color.gray);
        g2.setFont(Garamond);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70));
        
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/10*3;
        
        g2.drawString(text, x+4, y+4);
        
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        
        g2.setFont(Arial_30);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30));
        
        text = "Resume";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
        
        text = "Save";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
        
        text = "Options";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
        
        text = "Menu";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 3){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
        
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 4){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
    }
    
    public void drawDialogScreen(){
        drawPlayerLife();
        
        int x = gp.tileSize*1;
        int y = gp.tileSize*7;
        int width = gp.screenWidth - (gp.tileSize*2);
        int height = gp.tileSize*4;
        
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for(String line : currentDialogue.split("\n")){
           g2.drawString(line, x, y); 
           y += 40;
        }
    }
    
    public void drawInventory(){
        drawPlayerLife();
        
        int frameX = gp.tileSize*1;
        int frameY = gp.tileSize*2;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        frameX = gp.tileSize*12+20;
        frameY = gp.tileSize*2;
        frameWidth = gp.tileSize*6;
        frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;
        
        for(int i = 0; i < gp.player.inventory.size(); i++){
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHight = gp.tileSize*3;
        
        
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        
        int itemIndex = getItemIndexOnSlot();
        
        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHight);
            
            for(String line :  gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
        
    }
    
    public int getItemIndexOnSlot(){
        return slotCol + slotRow*5;
    }
    
    public void drawOptionScreen(){
        
        g2.setColor(new Color((float)0.0, (float)0.0, (float)0.0, (float)0.5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        g2.setColor(Color.white);
        g2.setFont(Garamond);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        switch(subState){
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
        }
        
        gp.keyH.enterPressed = false;

    }
    
    public void options_top(int frameX, int frameY){
        int textX, textY;
        
        String text = "OPTIONS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize+10;
        g2.drawString(text, textX, textY);
        
        g2.setFont(Arial_30);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true && gp.debug > 1){
                if(gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                }
                else if(gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }
        
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
        }
        
        textY += gp.tileSize;
        g2.drawString("Se", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-25, textY);
        }
        
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.pauseState;
                gp.ui.commandNum = 0;
            }
        }
        
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2+34;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX, textY, 24, 24);
        }
        
        
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        
        gp.config.saveConfig();
        
    }
    
    public void options_fullScreenNotification(int frameX, int frameY){
        
        int textX;
        int textY = frameY + gp.tileSize*3;
        g2.setFont(Arial_30);
        g2.setFont(g2.getFont().deriveFont(28F));
        
        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        
        for(String line : currentDialogue.split("\n")){
            textX = getXforCenteredText(line);
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX-25, textY);
        if(gp.keyH.enterPressed == true){
            subState = 0;
            commandNum = 0;
        }
    }
    
    public void options_control(int frameX, int frameY){
        
        int textX;
        int textY;
        
        g2.setFont(Garamond);
        g2.setFont(g2.getFont().deriveFont(28F));
        
        String text = "CONTROL";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        g2.setFont(Arial_30);
        g2.setFont(g2.getFont().deriveFont(28F));
        
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Interact", textX, textY); textY += gp.tileSize;
        g2.drawString("Inventory", textX, textY); textY += gp.tileSize;
        g2.drawString("Use", textX, textY); textY += gp.tileSize;
        g2.drawString("Mini map", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        
        textX = frameX + gp.tileSize*4;
        textY = frameY + gp.tileSize*2;
        
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("Left Mouse", textX, textY); textY += gp.tileSize;
        g2.drawString("E", textX, textY); textY += gp.tileSize;
        g2.drawString("Q", textX, textY); textY += gp.tileSize;
        g2.drawString("Enter", textX, textY); textY += gp.tileSize;
        g2.drawString("Z", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;
        
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX-25, textY);
        if(gp.keyH.enterPressed == true){
            subState = 0;
            commandNum = 0;
        }
        
    }
    
    public void drawSaveScreen(){
        g2.setColor(new Color((float)0.0, (float)0.0, (float)0.0, (float)0.5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        g2.setColor(Color.white);
        g2.setFont(Garamond);
        g2.setFont(g2.getFont().deriveFont(40F));
        
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize*3;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*6;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        int textX, textY;
        
        String text = "SAVE";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize*2;
        g2.drawString(text, textX, textY);
        
        text = "COMPLETED";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(32F));
       
        text = "Back";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        g2.drawString(">", textX-25, textY);
        
    }
    
    public void drawQuitScreen(){
        g2.setColor(new Color((float)0.0, (float)0.0, (float)0.0, (float)0.5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        g2.setColor(Color.white);
        g2.setFont(Arial_30);
        g2.setFont(g2.getFont().deriveFont(36F));
        
        int frameX = gp.tileSize*5;
        int frameY = gp.tileSize*3;
        int frameWidth = gp.tileSize*10;
        int frameHeight = gp.tileSize*6;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        int textX, textY;
        
        String text = "Do you want to save?";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize*2;
        g2.drawString(text, textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        
        text = "SAVE";
        textX = frameX + gp.tileSize*2;
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        if(saveNum == 0){
            g2.drawString(">", textX-25, textY);
        }
        
        text = "NO";
        textX = frameX + gp.tileSize*7;
        g2.drawString(text, textX, textY);
        if(saveNum == 1){
            g2.drawString(">", textX-25, textY);
        }
    }
    
    public void drawGameOverScreen(){
        
        g2.setColor(new Color((float)0.0, (float)0.0, (float)0.0, (float)0.5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        String text = "GAME OVER";
        g2.setColor(Color.gray);
        g2.setFont(new Font("Arial", Font.BOLD, 90));
        
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/10*3;
        
        g2.drawString(text, x+5, y+5);
        
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
        
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">",x - gp.tileSize + 20, y);
        }
    }
    
    public void drawInventorySlot(){
        int x = gp.tileSize * 12+40;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 1+20;
        int height = gp.tileSize * 1+20;
        for(int i = 0; i < 4; i++){
            g2.setColor(Color.BLACK);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRoundRect(x, y, width, height, 35, 35);
            g2.setColor(Color.white);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x+5, y+5, width-10, height-10, 10, 10);
            
            x += gp.tileSize*2-20;
        }
        
        x = gp.tileSize * 12+40;
        g2.drawImage(gp.player.inventory.get(0).down1, x+10, y+10, null);    
        x += gp.tileSize*2-20;
        g2.drawImage(gp.player.inventory.get(1).down1, x+10, y+10, null);
        x += gp.tileSize*2-20;
        for(int i = 0; i < gp.player.inventory.size(); i++){
            if(gp.player.inventory.get(i).name.equals("Key")){
                g2.drawImage(gp.player.inventory.get(i).down2, x+3, y+5, gp.tileSize+10, gp.tileSize+10, null);
            } 
        }
        x += gp.tileSize*2-20;
        for(int i = 0; i < gp.player.inventory.size(); i++){
            if(gp.player.inventory.get(i).name.equals("Red Potion")){
                g2.drawImage(gp.player.inventory.get(i).down1, x+10, y+10, gp.tileSize, gp.tileSize, null);
            }
        }
        
        x = gp.tileSize * 12+40 + (gp.tileSize*2-20)*2;
        if(gp.player.Key > 10){
            g2.setFont(Arial_30);
            g2.drawString(""+gp.player.Key, x+38, y+60);
        }
        else if(gp.player.Key > 1){
            g2.setFont(Arial_30);
            g2.drawString(""+gp.player.Key, x+42, y+60);
        }
        x += gp.tileSize*2-20;
        if(gp.player.RedPotion > 1){
            g2.setFont(Arial_30);
            g2.drawString(""+gp.player.RedPotion, x+42, y+60);
        }
    }
    
    public void drawChestScreen(){
        drawPlayerLife();
        
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        int frameX = gp.screenWidth/2 - frameWidth/2 + gp.tileSize*6;
        int frameY = gp.screenHeight/2 - frameHeight/2 - gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;
        
        int objIndex = gp.cChecker.checkObject(gp.player, true);
        if(!gp.obj[objIndex].chestInventory.isEmpty()){
            for(int i = 0; i < gp.obj[objIndex].chestInventory.size(); i++){
                g2.drawImage(gp.obj[objIndex].chestInventory.get(i).down1, slotX, slotY, null);

                slotX += slotSize;
                if(i == 4 || i == 9 || i == 14){
                    slotX = slotXstart;
                    slotY += slotSize;
                }
            }
        }
        
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHight = gp.tileSize*3;
        
        
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        
        int itemIndex = getItemIndexOnSlot();
        
        if(!gp.obj[objIndex].chestInventory.isEmpty()){
            if(itemIndex < gp.obj[objIndex].chestInventory.size()){
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHight);

                for(String line :  gp.obj[objIndex].chestInventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
        
        if(messageOn){

            g2.setColor(Color.white);
            if(gp.map.miniMapOn == true){
                g2.drawRect(gp.tileSize/2+2, gp.tileSize*7-35, 171, 19);
            }
            else{
                g2.drawRect(gp.tileSize/2+2, gp.tileSize*11-15, 171, 19);
            }
            g2.setColor(Color.black);
            if(gp.map.miniMapOn == true){
                g2.fillRect(gp.tileSize/2+3, gp.tileSize*7-34, 170, 18);
            }
            else{
                g2.fillRect(gp.tileSize/2+3, gp.tileSize*11-14, 170, 18);
            }
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.BOLD, 15));
            int textLenght = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();
            if(gp.map.miniMapOn == true){
                g2.drawString(message, gp.tileSize/2+88-textLenght/2, gp.tileSize*7-20);
            }
            else{
                g2.drawString(message, gp.tileSize/2+88-textLenght/2, gp.tileSize*11);
            }  
            messageCounter++;

            if(messageCounter > 60){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
    
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = 20;
        
        for(int i = 0; i < 3; i++){
            if(gp.player.life >= 4*(i+1)){
                g2.drawImage(heart_full, x, y, null);
            }
            else if(gp.player.life >= 4*(i+1)-1){
                g2.drawImage(heart_minus1, x, y, null);
            }
            else if(gp.player.life >= 4*(i+1)-2){
                g2.drawImage(heart_minus2, x, y, null);
            }
            else if(gp.player.life >= 4*(i+1)-3){
                g2.drawImage(heart_minus3, x, y, null);
            }
            x += gp.tileSize+5;
        }
    }
    
    public void drawSubWindow(int x, int y, int width, int height){
        
        g2.setColor(new Color((float)0.0, (float)0.0, (float)0.0, (float)0.5));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    
    public int getXforCenteredText(String text){
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - lenght/2;
    }
    
    public void colorBar(String color){
        int x = gp.tileSize/2+3;
        int y = gp.tileSize*2-20;
        int width = gp.tileSize*3-3;
        int height = 10-3;
        if(color.equals("Blue")){
            g2.setColor(Color.cyan);
            if(gp.potionBlueDebug < 900){
                g2.fillRect(x+2, y+2, width, height);
            }
            else if(gp.potionBlueDebug < 1800){
                g2.fillRect(x+2, y+2, width/4*3, height);
            }
            else if(gp.potionBlueDebug < 2700){
                g2.fillRect(x+2, y+2, width/4*2, height);
            }
            else if(gp.potionBlueDebug < 3600){
                g2.fillRect(x+2, y+2, width/4*1, height);
            }
        }
        if(color.equals("Green")){
            y += 20;
            g2.setColor(Color.green);
            if(gp.potionGreenDebug < 900){
                g2.fillRect(x+2, y+2, width, height);
            }
            else if(gp.potionGreenDebug < 1800){
                g2.fillRect(x+2, y+2, width/4*3, height);
            }
            else if(gp.potionGreenDebug < 2700){
                g2.fillRect(x+2, y+2, width/4*2, height);
            }
            else if(gp.potionGreenDebug < 3600){
                g2.fillRect(x+2, y+2, width/4*1, height);
            }
        }
    }
}
