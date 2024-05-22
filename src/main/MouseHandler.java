package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    
    public boolean attackPressed;
    GamePanel gp;
    
    public MouseHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        int code = e.getButton();
        
        if(gp.gameState == gp.playState){
            if(code == MouseEvent.BUTTON1){
                attackPressed = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        int code = e.getButton();
        
        if(gp.gameState == gp.playState){
            if(code == MouseEvent.BUTTON1){
                attackPressed = false;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
}

