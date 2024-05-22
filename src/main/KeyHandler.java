package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, attackPressed, enterPressed;
    public boolean exitDialogue = false;
    GamePanel gp;
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        
        if(gp.gameState == gp.playState){
            playState(code);
        }
        
        if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        
        if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        
        if(gp.gameState == gp.inventoryState){
            inventoryState(code);
        }
        
        if(gp.gameState == gp.optionState){
            optionState(code);
        }
        
        if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        if(gp.gameState == gp.chestState){
            chestState(code);
        }
        if(gp.gameState == gp.saveState){
            saveState(code);
        }
        if(gp.gameState == gp.quitState){
            quitState(code);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
            upPressed= false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed= false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed= false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed= false;
        }
        if(code == KeyEvent.VK_E) {
            ePressed= false;
        }
    }
    
    public void titleState(int code){
        
        if(code == KeyEvent.VK_W) {
            
            if(gp.ui.commandNum > 0){
                gp.playSE(10);
                gp.ui.commandNum--;
            }
            else{
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum < 2){
                gp.playSE(10);
                gp.ui.commandNum++;
            }
            else{
                gp.ui.commandNum = 2;
            }
            
        }
        if(code == KeyEvent.VK_ENTER){
            gp.playSE(11);
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.stopMusic();
                gp.playMusic(0);
                gp.resetGame(true);
                gp.saveLoad.save();
            }
            if(gp.ui.commandNum == 1){
                gp.saveLoad.load();
                gp.stopMusic();
                gp.playMusic(0);
                gp.gameState = gp.playState;
                gp.ui.commandNum = 0;
            }
            if(gp.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }
    
    public void playState(int code){
        if(code == KeyEvent.VK_W) {
            upPressed= true;
        
        }
        if(code == KeyEvent.VK_S) {
            downPressed= true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed= true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed= true;
        }
        if(code == KeyEvent.VK_ESCAPE && gp.debug > 15) {
            gp.debug = 0;
            if(gp.gameState == gp.playState && gp.debug == 0){
                gp.playSE(11);
                gp.gameState = gp.pauseState;
            }
        }
        if(code == KeyEvent.VK_E) {
            ePressed= true;
        }
        if(code == KeyEvent.VK_Q && gp.debug > 15){
            gp.playSE(11);
            gp.debug = 0;
            if(gp.gameState == gp.playState && gp.debug == 0){
                gp.gameState = gp.inventoryState;
            }
        }
        if(code == KeyEvent.VK_Z && gp.debug > 15){
            gp.debug = 0;
            if(gp.map.miniMapOn == false){
                gp.playSE(11);
                gp.map.miniMapOn = true;
            }
            else{
                gp.playSE(11);
                gp.map.miniMapOn = false;
            }
        }
    }
    
    public void pauseState(int code){
        if(code == KeyEvent.VK_W) {
            if(gp.ui.commandNum > 0){
                gp.playSE(10);
                gp.ui.commandNum--;
            }
            else{
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum < 4){
                gp.playSE(10);
                gp.ui.commandNum++;
            }
             else{
                gp.ui.commandNum = 4;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.playSE(11);
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
            }
            if(gp.ui.commandNum == 1){
                gp.gameState = gp.saveState;
                gp.debug = 0;
                gp.saveLoad.save();
            }
            if(gp.ui.commandNum == 2){
                gp.gameState = gp.optionState;
                gp.ui.commandNum = 0;
                gp.debug = 0;
            }
            if(gp.ui.commandNum == 3){
                gp.gameState = gp.quitState;
                gp.debug = 0;
            }
            if(gp.ui.commandNum == 4){
                gp.gameState = gp.quitState;
                gp.debug = 0;
            }
        }
        if(code == KeyEvent.VK_ESCAPE && gp.debug > 15) {
            gp.playSE(11);
            gp.debug = 0;
            if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }
    }
    
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.playSE(11);
            if(exitDialogue == false){
                gp.obj[gp.player.objIndex].speak(gp);
            }
            else{
                gp.gameState = gp.playState;
                exitDialogue = false;
            }
        }
    }
    
    public void inventoryState(int code){
        if(code == KeyEvent.VK_ESCAPE && gp.debug > 0){
            gp.playSE(11);
            gp.gameState = gp.playState;
            gp.ui.slotCol = 0;
            gp.ui.slotRow = 0;
        }
        if(code == KeyEvent.VK_Q && gp.debug > 15){
            gp.debug = 0;
            gp.playSE(11);
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W){
            gp.playSE(10);
            if(gp.ui.slotRow != 0){
                gp.ui.slotRow--;
            }
            else{
                gp.ui.slotRow = 3;
            }
        }
        if(code == KeyEvent.VK_A){
            gp.playSE(10);
            if(gp.ui.slotCol != 0){
                gp.ui.slotCol--;
            }
            else{
                gp.ui.slotCol = 4;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.playSE(10);
            if(gp.ui.slotRow != 3){
                gp.ui.slotRow++;
            }
            else{
                gp.ui.slotRow = 0;
            }
        }
        if(code == KeyEvent.VK_D){
            gp.playSE(10);
            if(gp.ui.slotCol != 4){
                gp.ui.slotCol++;
            }
            else{
                gp.ui.slotCol = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectedItem();
        }
    }
    
    public void optionState(int code){
        
        if(code == KeyEvent.VK_ESCAPE){
            gp.playSE(11);
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_ENTER){
            gp.playSE(11);
            enterPressed = true;
        }
        
        if(code == KeyEvent.VK_W){
            if(gp.ui.commandNum > 0 && gp.debug > 10){
                gp.debug = 0;
                gp.playSE(10);
                gp.ui.commandNum--;
            }
            else if(gp.ui.commandNum == 0 && gp.debug > 10){
                gp.debug = 0;
                gp.playSE(10);
                gp.ui.commandNum = 4;
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.commandNum < 4  && gp.debug > 10){
                gp.debug = 0;
                gp.playSE(10);
                gp.ui.commandNum++;
            }
            else if(gp.ui.commandNum == 4 && gp.debug > 10){
                gp.debug = 0;
                gp.playSE(10);
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.playSE(10);  
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.playSE(10);
                    gp.se.volumeScale--;
                    
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.playSE(10);
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                    gp.playSE(10);
                    gp.se.volumeScale++;
                    
                }
            }
        }
        
    }
    
    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.commandNum > 0){
                gp.playSE(10);
                gp.ui.commandNum--;
            }
            else{
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum < 1){
                gp.playSE(10);
                gp.ui.commandNum++;
            }
            else{
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.stopSE();
            gp.playSE(11);
            if(gp.ui.commandNum == 0){
                gp.player.restoreStatus();
                gp.aSetter.setMonster();
                gp.saveLoad.load();
                gp.playMusic(0);
                gp.gameState = gp.playState;
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.quitState;
                gp.ui.commandNum = 0;
                gp.debug = 0;
            }
        }
    }
    
    public void chestState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.stopSE();
            gp.stopSE();
            gp.playSE(11);
            int chestIndex = gp.cChecker.checkObject(gp.player, true);
            gp.obj[chestIndex].isOpen = false;
            gp.gameState = gp.playState;
            gp.ui.slotCol = 0;
            gp.ui.slotRow = 0;
        }
        if(code == KeyEvent.VK_W){
            gp.playSE(10);
            if(gp.ui.slotRow != 0){
                gp.ui.slotRow--;
            }
            else{
                gp.ui.slotRow = 3;
            }
        }
        if(code == KeyEvent.VK_A){
            gp.playSE(10);
            if(gp.ui.slotCol != 0){
                gp.ui.slotCol--;
            }
            else{
                gp.ui.slotCol = 4;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.playSE(10);
            if(gp.ui.slotRow != 3){
                gp.ui.slotRow++;
            }
            else{
                gp.ui.slotRow = 0;
            }
        }
        if(code == KeyEvent.VK_D){
            gp.playSE(10);
            if(gp.ui.slotCol != 4){
                gp.ui.slotCol++;
            }
            else{
                gp.ui.slotCol = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            int chestIndex = gp.cChecker.checkObject(gp.player, true);
            if(gp.obj[chestIndex].chestInventory.isEmpty() == false){
                gp.player.addItem();
            }
        }
    }
    
    public void saveState(int code){
        if(code == KeyEvent.VK_ENTER && gp.debug > 15){
            gp.playSE(11);
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.playSE(11);
            gp.gameState = gp.pauseState;
        }
    }
    
    public void quitState(int code){
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_A){
            if(gp.ui.saveNum == 1){
                gp.playSE(10);
                gp.ui.saveNum--;
            }
        }
        if (code == KeyEvent.VK_D){
            if(gp.ui.saveNum == 0){
                gp.playSE(10);
                gp.ui.saveNum++;
            }
        }
        if (code == KeyEvent.VK_ENTER && gp.debug > 15){
            gp.playSE(11);
            if(gp.ui.saveNum == 0){
                gp.saveLoad.save();
                if(gp.ui.commandNum == 3){
                    gp.gameState = gp.titleState;
                    gp.ui.commandNum = 0;
                    gp.stopMusic();
                    gp.playMusic(14);
                }
                else if(gp.ui.commandNum == 4 || gp.ui.commandNum == 0){
                    System.exit(0);
                }
            }
            if(gp.ui.saveNum == 1){
                if(gp.ui.commandNum == 3){
                    gp.gameState = gp.titleState;
                    gp.ui.commandNum = 0;
                    gp.stopMusic();
                }
                else if(gp.ui.commandNum == 4 || gp.ui.commandNum == 0){
                    System.exit(0);
                }
            }
        }
    }
}
