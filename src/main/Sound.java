package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    
    Clip clip;
    URL soundURL[]= new URL[30];
    FloatControl fc;
    int volumeScale = 4;
    float volume;
    
    public Sound(){
        
        try{    
            soundURL[0] = getClass().getResource("/res/sound/soundtrack.wav");
            soundURL[1] = getClass().getResource("/res/sound/key.wav");
            soundURL[2] = getClass().getResource("/res/sound/door.wav");
            soundURL[3] = getClass().getResource("/res/sound/doorLocked.wav");
            soundURL[4] = getClass().getResource("/res/sound/died.wav");
            soundURL[5] = getClass().getResource("/res/sound/swordMiss.wav");
            soundURL[6] = getClass().getResource("/res/sound/swordHit.wav");
            soundURL[7] = getClass().getResource("/res/sound/Potion.wav");
            soundURL[8] = getClass().getResource("/res/sound/PotionDrink.wav");
            soundURL[9] = getClass().getResource("/res/sound/hit.wav");
            soundURL[10] = getClass().getResource("/res/sound/switchMenu.wav");
            soundURL[11] = getClass().getResource("/res/sound/selectMenu.wav");
            soundURL[12] = getClass().getResource("/res/sound/chestOpen.wav");
            soundURL[13] = getClass().getResource("/res/sound/lifeRestore.wav");
            soundURL[14] = getClass().getResource("/res/sound/titletrack.wav");
            soundURL[15] = getClass().getResource("/res/sound/arrow.wav");
        } catch(Exception e){}
        
    }
    
    public void setFile(int i){
        
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
            
        }catch(Exception e){  
        }
    }
    public void play(){
        
        try{
            clip.start();
        } catch(Exception e){  
        }
    }
    public void loop(){
        
        try{
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e){  
        }
    }
    public void stop(){
        
        try{
            clip.stop();
        }catch(Exception e){  
        }
    }
    
    public void checkVolume(){
        
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 5f; break;
        }
        fc.setValue(volume);
    }
}
