package Model;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends Keysetting {
    
    public static int frameWidth;
    public static int frameHeight;

    private Timer timer;
    private static int stop;
    public static int seconds;

    public static final long secInNanosec = 1000000000L;
    public static final long milisecInNanosec = 1000000L;

    private final int GAME_FPS = 90;
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING
    	, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, STAGE1, STAGE2, STAGE3, HIGHSCORE}
    
    public static GameState gameState;
    
    private long gameTime;
    private long lastTime;
    
    private GameManagement game;
    
    private BufferedImage cityhighscoreImg; 
    private BufferedImage spacehighscoreImg;
    private BufferedImage ghosthighscoreImg; 
    private BufferedImage shooterGameMenuImg;   
    private BufferedImage optionImg;
    private BufferedImage clockImg;
    private BufferedImage noteImg;
    private int confirmDialog = 2;
    
    private BufferedImage carLine1Img;
    private BufferedImage carLine2Img;
    private BufferedImage carLine3Img;
    
    private BufferedImage spaceLine1Img;
    private BufferedImage spaceLine2Img;
    private BufferedImage spaceLine3Img;
    private BufferedImage spaceLine4Img;
    
    private BufferedImage ghostLine1Img;
    private BufferedImage ghostLine3Img;
    private BufferedImage ghostLine5Img;
    private BufferedImage ghostLine7Img;
    
    private Font fontfortime;
    private Font fontfortitle;
    private Font fontforhighscore;
    
    private JButton bthighscore = new JButton("High Score");
    private String name;
    
    private int okname = 0;
    
    private ConnectFile connectfile;
    
    public View ()
    {
        super();
        
        gameState = GameState.VISUALIZING;
        
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    
    private void initiaLize()
    {
    	seconds = 61;
    	stop = 0;
    }
   
    private void loadContent()
    {
        try
        {
            URL shootTheDuckMenuImgUrl = this.getClass().getResource("/resources/background_main.jpg");
            shooterGameMenuImg = ImageIO.read(shootTheDuckMenuImgUrl);
            
            URL optionImgUrl = this.getClass().getResource("/resources/background_option.jpeg");
            optionImg = ImageIO.read(optionImgUrl);
            
            URL clockImgUrl = this.getClass().getResource("/resources/Clock1.png");
            clockImg = ImageIO.read(clockImgUrl);
            
            URL noteImgUrl = this.getClass().getResource("/resources/Note1.png");
            noteImg = ImageIO.read(noteImgUrl);
            
            URL fontUrl = this.getClass().getResource("/resources/Font2.ttf");
			fontfortime = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
			fontfortime = fontfortime.deriveFont(Font.BOLD,50);
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(fontfortime);
	        
	        URL fontUrl2 = this.getClass().getResource("/resources/KRR_HutSha.ttf");
			fontfortitle = Font.createFont(Font.TRUETYPE_FONT, fontUrl2.openStream());
			fontfortitle = fontfortitle.deriveFont(Font.BOLD,75);
		    GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge2.registerFont(fontfortitle);

	        URL fontUrl3 = this.getClass().getResource("/resources/Font1.ttf");
	        fontforhighscore = Font.createFont(Font.TRUETYPE_FONT, fontUrl3.openStream());
	        fontforhighscore = fontforhighscore.deriveFont(Font.BOLD,45);
		    GraphicsEnvironment ge3 = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge3.registerFont(fontforhighscore);
		    
            URL carLine1ImgUrl = this.getClass().getResource("/resources/CarOldRedRight.png");
            carLine1Img = ImageIO.read(carLine1ImgUrl);
            
            URL carLine2ImgUrl = this.getClass().getResource("/resources/CarOrangeLeft.png");
            carLine2Img = ImageIO.read(carLine2ImgUrl);
            
            URL carLine3ImgUrl = this.getClass().getResource("/resources/CarGreenLeft.png");
            carLine3Img = ImageIO.read(carLine3ImgUrl);
            
            URL spaceLine1ImgUrl = this.getClass().getResource("/resources/UfoMonster.png");
            spaceLine1Img = ImageIO.read(spaceLine1ImgUrl);
            
            URL spaceLine2ImgUrl = this.getClass().getResource("/resources/UfoMonster2.png");
            spaceLine2Img = ImageIO.read(spaceLine2ImgUrl);
            
            URL spaceLine3ImgUrl = this.getClass().getResource("/resources/WhiteRocket.png");
            spaceLine3Img = ImageIO.read(spaceLine3ImgUrl);
            
            URL spaceLine4ImgUrl = this.getClass().getResource("/resources/Ufo.png");
            spaceLine4Img = ImageIO.read(spaceLine4ImgUrl);
            
            URL ghostLine1ImgUrl = this.getClass().getResource("/resources/ghost1.png");
            ghostLine1Img = ImageIO.read(ghostLine1ImgUrl);
            
            URL ghostLine3ImgUrl = this.getClass().getResource("/resources/ghost3.png");
            ghostLine3Img = ImageIO.read(ghostLine3ImgUrl);
            
            URL ghostLine5ImgUrl = this.getClass().getResource("/resources/ghost5.png");
            ghostLine5Img = ImageIO.read(ghostLine5ImgUrl); 
            
            URL ghostLine7ImgUrl = this.getClass().getResource("/resources/ghost7.png");
            ghostLine7Img = ImageIO.read(ghostLine7ImgUrl);
        	
            URL cityhighscoreImgUrl = this.getClass().getResource("/resources/city_highscore_background.png");
            cityhighscoreImg = ImageIO.read(cityhighscoreImgUrl);
            
            URL spacehighscoreImgUrl = this.getClass().getResource("/resources/space_highscore_background.jpg");
            spacehighscoreImg = ImageIO.read(spacehighscoreImgUrl);
            
            URL ghosthighscoreImgUrl = this.getClass().getResource("/resources/ghost_highscore_background.jpg");
            ghosthighscoreImg = ImageIO.read(ghosthighscoreImgUrl);
            
        	connectfile = new ConnectFile();
        }
        catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FontFormatException e) {
        	e.printStackTrace();
        }
    }
    
    public void setMouseDisappear(Boolean boo){
    	if(boo){
    		BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
       	 	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
       	 	this.setCursor(blankCursor);
    	}
    	else{
    		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	}
    }
    
    public void setTime(){
    	timer = new Timer();
		timer.schedule(new TimerTask() {
	          public void run() {
	        	  if(stop == 1){
	            	  timer.cancel();
	                  timer.purge();
	                  
	              }else{
	            	  if (seconds > 0) {
		                  System.out.println(seconds + " seconds remaining");
		                  seconds--;
		               }
	              }
	        }
		}, 0,1000);
    }
    
    private void GameLoop()
    {
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                	setMouseDisappear(true);
                    gameTime += System.nanoTime() - lastTime;
                    game.updateGame(gameTime, mousePosition());
                    lastTime = System.nanoTime();
                    if(seconds==0){
                    	gameState = GameState.GAMEOVER;
                    }
                break;
                case GAMEOVER:
                	setMouseDisappear(true);
                	stop = 1;
                break;
                case MAIN_MENU:
                	setMouseDisappear(true);
                break;
                case OPTIONS:
                	setMouseDisappear(false);
                break;
                case GAME_CONTENT_LOADING:
                	///
                break;
                case STARTING:

                    initiaLize();
                    loadContent();
                	gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
 
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();
                        gameState = GameState.STARTING;
                    }
                    
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
                case STAGE1:
                	setMouseDisappear(false);
                break;
            }
            
            repaint();
            
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec;
            
            if (timeLeft < 10) 
                timeLeft = 10;
            try {
            	
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition());
                
                g2d.drawImage(noteImg, (int) (frameWidth* 0.41), (int) (frameHeight*0.08), noteImg.getWidth(),noteImg.getHeight() , null);
                g2d.drawImage(clockImg, (int) (frameWidth* 0.43), (int) (frameHeight*0.12), clockImg.getWidth(),clockImg.getHeight() , null);
                
                g2d.setColor(Color.RED);
                g2d.setFont(fontfortime);
                g2d.drawString(" : "+ seconds, (int) (frameWidth* 0.48), (int) (frameHeight*0.19));
 
            break;
            case GAMEOVER:
                game.drawGameOver(g2d, mousePosition());
                g2d.drawImage(noteImg, (int) (frameWidth* 0.41), (int) (frameHeight*0.08), noteImg.getWidth(),noteImg.getHeight() , null);
                g2d.drawImage(clockImg, (int) (frameWidth* 0.43), (int) (frameHeight*0.12), clockImg.getWidth(),clockImg.getHeight() , null);
                
                g2d.setColor(Color.RED);
                g2d.setFont(fontfortime);
                g2d.drawString(" : "+ seconds, (int) (frameWidth* 0.48), (int) (frameHeight*0.19));
                
              
            break;
            case MAIN_MENU:
                g2d.drawImage(shooterGameMenuImg, 0, 0, frameWidth, frameHeight, null);
            break;
            case OPTIONS:
            	g2d.drawImage(optionImg, 0, 0, frameWidth, frameHeight, null);
            break;
            case GAME_CONTENT_LOADING:
                g2d.setColor(Color.white);
                g2d.setFont(fontfortime);
                g2d.drawString("GAME is LOADING...", frameWidth /2 -150, frameHeight / 2);
            break;
            case STAGE1:
            	g2d.drawImage(carLine1Img, frameWidth/2 - 200, frameHeight/2 - 200, carLine1Img.getWidth(), carLine1Img.getHeight(), null);
            	g2d.drawImage(carLine2Img, frameWidth/2 - 200, frameHeight/2 - 100, carLine2Img.getWidth(), carLine2Img.getHeight(), null);
            	g2d.drawImage(carLine3Img, frameWidth/2 - 200, frameHeight/2 - 0, carLine3Img.getWidth(), carLine3Img.getHeight(), null);
            	g2d.setColor(Color.YELLOW);
                g2d.setFont(fontfortime);
                g2d.drawString(" :  60 POINT", frameWidth/2, frameHeight - 430);
                g2d.drawString(" :  50 POINT", frameWidth/2, frameHeight - 330);
                g2d.drawString(" :  50 POINT", frameWidth/2, frameHeight - 230);
                g2d.setColor(Color.WHITE);
                g2d.drawString("PRESS ENTER TO START", frameWidth /2 - 190, frameHeight - 100);
   
            break;
            case STAGE2:
            	g2d.drawImage(spaceLine1Img, frameWidth/2 - 170, frameHeight/2 - 230, spaceLine1Img.getWidth(), spaceLine1Img.getHeight(), null);
            	g2d.drawImage(spaceLine2Img, frameWidth/2 - 200, frameHeight/2 - 150, spaceLine2Img.getWidth(), spaceLine2Img.getHeight(), null);
            	g2d.drawImage(spaceLine3Img, frameWidth/2 - 210, frameHeight/2 - 50, spaceLine3Img.getWidth(), spaceLine3Img.getHeight(), null);
            	g2d.drawImage(spaceLine4Img, frameWidth/2 - 200, frameHeight/2 - -70, spaceLine4Img.getWidth(), spaceLine4Img.getHeight(), null);
            	g2d.setColor(Color.YELLOW);
                g2d.setFont(fontfortime);
                g2d.drawString(" :  100 POINT", frameWidth/2, frameHeight - 480);
                g2d.drawString(" :  80 POINT", frameWidth/2, frameHeight - 380);
                g2d.drawString(" :  80 POINT", frameWidth/2, frameHeight - 280);
                g2d.drawString(" :  70 POINT", frameWidth/2, frameHeight - 160);
                g2d.setColor(Color.WHITE);
                g2d.drawString("PRESS ENTER TO START", frameWidth /2 - 190, frameHeight - 50);
            break;
            case STAGE3:
            	g2d.drawImage(ghostLine7Img, frameWidth/2 - 165, frameHeight/2 - 270, ghostLine7Img.getWidth(), ghostLine7Img.getHeight(), null);
            	g2d.drawImage(ghostLine5Img, frameWidth/2 - 170, frameHeight/2 - 160, ghostLine5Img.getWidth(), ghostLine5Img.getHeight(), null);
            	g2d.drawImage(ghostLine1Img, frameWidth/2 - 170, frameHeight/2 - 50, ghostLine1Img.getWidth(), ghostLine1Img.getHeight(), null);
            	g2d.drawImage(ghostLine3Img, frameWidth/2 - 170, frameHeight/2 - -55, ghostLine3Img.getWidth(), ghostLine3Img.getHeight(), null);
            	g2d.setColor(Color.YELLOW);
                g2d.setFont(fontfortime);
                g2d.drawString(" :  100 POINT", frameWidth/2 - 50, frameHeight - 480);
                g2d.drawString(" :  90 POINT", frameWidth/2-50, frameHeight - 380);
                g2d.drawString(" :  80 POINT", frameWidth/2-50, frameHeight - 270);
                g2d.drawString(" :  70 POINT", frameWidth/2-50, frameHeight - 160);
                g2d.setColor(Color.WHITE);
                g2d.drawString("PRESS ENTER TO START", frameWidth /2 - 190, frameHeight - 50);
            break;
            case HIGHSCORE:
            	if(game.getStage() == 1){
            		g2d.drawImage(cityhighscoreImg, 0, 0, View.frameWidth, View.frameHeight, null);
            		ArrayList arrayscore = connectfile.readScoreFile(game.getStage());
            		g2d.setFont(fontforhighscore);
            		int Height = 180;
            		for(int i = 0;i < 5;i++){
              			g2d.setColor(Color.CYAN);
            			g2d.drawString(arrayscore.get(i)+" POINT", frameWidth/2-210, Height);
            			g2d.setColor(Color.BLUE);
            			g2d.drawString(arrayscore.get(i)+" POINT", frameWidth/2-212, Height-2);
            			Height = Height+60;
            		}

            		g2d.setColor(Color.BLACK);
            		g2d.setFont(fontfortitle);
              		g2d.drawString("City Stage High Score", frameWidth/2 - 340, frameHeight - 480);
              		g2d.setColor(Color.YELLOW);
              		g2d.drawString("City Stage High Score", frameWidth/2 - 343, frameHeight - 483);
            	}
            	else if(game.getStage() == 2){
            		g2d.drawImage(spacehighscoreImg, 0, 0, View.frameWidth, View.frameHeight, null);
            		ArrayList arrayscore = connectfile.readScoreFile(game.getStage());
            		g2d.setFont(fontforhighscore);
            		int Height = 180;
            		for(int i = 0;i < 5;i++){
              			g2d.setColor(Color.BLUE);
            			g2d.drawString(arrayscore.get(i)+" POINT", frameWidth/2-210, Height);
            			g2d.setColor(Color.cyan);
            			g2d.drawString(arrayscore.get(i)+" POINT", frameWidth/2-212, Height-2);
            			Height = Height+60;
            		}

            		g2d.setColor(Color.BLACK);
            		g2d.setFont(fontfortitle);
              		g2d.drawString("Space Stage High Score", frameWidth/2 - 380, frameHeight - 480);
              		g2d.setColor(Color.YELLOW);
              		g2d.drawString("Space Stage High Score", frameWidth/2 - 383, frameHeight - 483);
            	}
            	else if(game.getStage() == 3){
               		g2d.drawImage(ghosthighscoreImg, 0, 0, View.frameWidth, View.frameHeight, null);
            		ArrayList arrayscore = connectfile.readScoreFile(game.getStage());
            		g2d.setFont(fontforhighscore);
            		int Height = 180;
            		for(int i = 0;i < 5;i++){
              			g2d.setColor(Color.BLUE);
            			g2d.drawString(arrayscore.get(i)+" POINT", frameWidth/2-210, Height);
            			g2d.setColor(Color.cyan);
            			g2d.drawString(arrayscore.get(i)+" POINT", frameWidth/2-212, Height-2);
            			Height = Height+60;
            		}

            		g2d.setColor(Color.BLACK);
            		g2d.setFont(fontfortitle);
              		g2d.drawString("HALLOWEEN Stage High Score", frameWidth/2 - 440, frameHeight - 480);
              		g2d.setColor(Color.YELLOW);
              		g2d.drawString("HALLOWEEN Stage High Score", frameWidth/2 - 443, frameHeight - 483);
            	}
            	g2d.setColor(Color.black);
                g2d.setFont(fontfortime);
                g2d.drawString("Press space or enter to restart.", View.frameWidth / 2 - 280, (int)(View.frameHeight * 0.85));
                g2d.drawString("Press ESC for back to main menu.", View.frameWidth / 2 - 280, (int)(View.frameHeight * 0.95));
                g2d.setColor(Color.red);
                g2d.drawString("Press space or enter to restart.", View.frameWidth / 2 - 282, (int)(View.frameHeight * 0.85)-2);
                g2d.drawString("Press ESC for back to main menu.", View.frameWidth / 2 - 282, (int)(View.frameHeight * 0.95)-2);
            break;
        }
    }
    
    private void newGame(int stage)
    {
    	initiaLize();
    	setTime();
        gameTime = 0;
        lastTime = System.nanoTime();
        game = new GameManagement(stage);
    }
    
    private void restartGame()
    {
        gameTime = 0;
        lastTime = System.nanoTime();
        initiaLize();
        setTime();
        game.restartGame();

        gameState = GameState.PLAYING;
    }
    
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
   
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        switch (gameState)
        {
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                	gameState = GameState.MAIN_MENU;
                }
                else if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
                	connectfile.writeFile(game.getStage(),name, game.getTotalScore());
                    gameState = GameState.HIGHSCORE;
                }
            break;
            case PLAYING:
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                	gameState = GameState.MAIN_MENU;
            		stop = 1;
            	}
            	
            break;	
            case MAIN_MENU:
            	if(e.getKeyCode() == KeyEvent.VK_ENTER){
            		gameState = GameState.OPTIONS;
            		inputName();
            	}
            	else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                	confirmDialog = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit Game?",null, JOptionPane.YES_NO_OPTION);
            	if(confirmDialog == JOptionPane.YES_OPTION)
            		System.exit(0);
            	
            break;
            case OPTIONS:
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                	gameState = GameState.MAIN_MENU;
            	if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		gameState = GameState.OPTIONS;
            	else if(e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1)
            		gameState = GameState.STAGE1;
            	else if(e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2)
            		gameState = GameState.STAGE2;
            	else if(e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3)
            		gameState = GameState.STAGE3;
            break;
            case STAGE1:
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                	gameState = GameState.MAIN_MENU;
            	else if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		newGame(1);
            break;
            case STAGE2:
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                	gameState = GameState.MAIN_MENU;
            	else if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		newGame(2);
            break;
            case STAGE3:
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                	gameState = GameState.MAIN_MENU;
            	else if(e.getKeyCode() == KeyEvent.VK_ENTER)
            		newGame(3);
            break;
            case HIGHSCORE:
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                	gameState = GameState.MAIN_MENU;
              	else if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
            		restartGame();
            break;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        switch (gameState)
        {
            case MAIN_MENU:
                //if(e.getButton() == MouseEvent.BUTTON1)
                   // newGame();
            break;
        }
    }
    
    public void inputName(){
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/TargetBlue.png"));
     	String[] options = {"OK"};
     	JPanel panel = new JPanel();
     	JLabel lbl = new JLabel("Enter Your name: ");
     	JTextField txt = new JTextField(10);
     	panel.add(lbl);
     	panel.add(txt);
     	int selectedOption = JOptionPane.showOptionDialog(null, panel, "Shooter Games", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options , options[0]);
     	if(selectedOption == 0)
     	{
     	    name = txt.getText();
     	  	name = name.replace(" ", "");
     	  	name.trim();
     	  	
     	  	if(name.equals("")){
     	  		name = "No-Name";
     	  	}
     	}
    }
    

    
}
