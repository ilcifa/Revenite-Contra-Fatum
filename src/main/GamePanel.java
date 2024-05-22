package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import main.tile.Map;
import main.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    final int originalTileSize= 16;
    final int scale= 3;
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth= tileSize * maxScreenCol;
    public final int screenHeight= tileSize * maxScreenRow;
    
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 60;
    
    int screenWidth2= screenWidth;
    int screenHeight2= screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    
    int FPS = 60;
    
    public TileManager tileM= new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Map map = new Map(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Thread gameThread;
    
    public Player player = new Player(this, keyH, mouseH);
    public Entity obj[] = new Entity[1000];
    public Entity monster[] = new Entity[1000]; 
    public Entity projectile[] = new Entity[1000];
    
    public ArrayList<Entity> particleList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();
    
    public int debug, potionBlueDebug, potionGreenDebug;
    public int waterCounter = 0;
    
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int inventoryState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int chestState = 7;
    public final int saveState = 8;
    public final int quitState = 9;
    
    public boolean fullScreenOn = false;
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
        
    }
    
    public void setupGame(){
        aSetter.setObject();
        aSetter.setMonster();
        eManager.setup();
        gameState = titleState;
        playMusic(14);
        
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        
        if(fullScreenOn == true){
            setFullScreen();
        }
    }
    
    public void setFullScreen(){
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    
    public void resetGame(boolean restart){
        
        
        player.setDefaultPosition();
        player.restoreStatus();
        //aSetter.setNPC();
        aSetter.setMonster();
        
        if(restart == true){
            player.setDefaultValues();
            aSetter.setObject();
            //aSetter.setInteractiveTile();
        }
    }
    
    public void startGameThread(){
        
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while(gameThread != null){
            currentTime= System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime= currentTime;
            
            if(delta >= 1){
                try {
                    update();
                } catch (IOException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update() throws IOException{
        
        debug++;
        
        if(potionBlueDebug > 3600){
            player.attack= 2;
        }
        if(potionGreenDebug > 3600){
            player.speed= 4;
        }
        
        if(gameState == playState){
            player.update();
            potionBlueDebug++;
            potionGreenDebug++;
            waterCounter++;
            /*for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            */
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].isDied == false && monster[i].dying == false){
                        monster[i].update();
                    }
                    if(monster[i].isDied == true){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            
            for(int i = 0; i < projectile.length; i++){
                if(projectile[i] != null){
                    if(projectile[i].isDied == false){
                        projectile[i].update();
                    }
                    if(projectile[i].isDied == true){
                        projectile[i] = null;
                    }
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).isDied == false){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).isDied == true){
                        particleList.remove(i);
                    }
                }
            }
        }
        if(gameState == pauseState){
            
        }
    }
    
    public void drawToTempScreen(){
        
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            tileM.draw(g2);
            
            /*for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            */
            
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            
            for(int i = 0; i < projectile.length; i++){
                if(projectile[i] != null){
                    entityList.add(projectile[i]);
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }
            
            entityList.add(player);
            
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            entityList.clear();
            
            eManager.draw(g2);
            
            map.drawMiniMap(g2);
            
            ui.draw(g2);    
        }
    }
    
    public void drawToScreen(){
        
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    
    public void playMusic(int i){
        
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        
        music.stop();
    }
    public void playSE(int i){
        
        se.setFile(i);
        se.play();
    }
    public void stopSE(){
        
        se.stop();
    }
}
