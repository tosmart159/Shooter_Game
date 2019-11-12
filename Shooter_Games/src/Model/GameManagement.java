package Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class GameManagement {
	
    private Random random;
    private Font font;
    private Font fontResult;
    private int stage = 1;
    private ArrayList<Target> arrayShoot;
    private ArrayList<Target> arrayItem;
    private int lifePoint;
    private int killedPoint;
    private int shootsPoint;
    private int missPoint;
    private int score;
    private int totalScore;
    
    private int totalLifeScore;
    private int totalMissScore;
    
    private long lastTimeShoot;    
    private long timeBetweenShots;
    private long timeBetweenObject;

    
    private BufferedImage backgroundCityImg;
    private BufferedImage backgroundSpaceImg;
    private BufferedImage backgroundGhostImg;
    private BufferedImage lifeImg;
    private BufferedImage grassImg;
    
    private BufferedImage carLine1Img;
    private BufferedImage carLine2Img;
    private BufferedImage carLine3Img;
    
    private BufferedImage spaceLine1Img;
    private BufferedImage spaceLine2Img;
    private BufferedImage spaceLine3Img;
    private BufferedImage spaceLine4Img;
    
    private BufferedImage ghostLine1Img;
    private BufferedImage ghostLine2Img;
    private BufferedImage ghostLine3Img;
    private BufferedImage ghostLine4Img;
    private BufferedImage ghostLine5Img;
    private BufferedImage ghostLine6Img;
    private BufferedImage ghostLine7Img;
    
    private BufferedImage targetBlackImg;
    private BufferedImage targetBlueImg;
    private BufferedImage targetRedImg;
    
    private BufferedImage setRemoveObjectImg = null;
    private BufferedImage setPositiontoClick = null;
    
    private int targetImgMiddleWidth;
    private int targetImgMiddleHeight;
    
    private int timeItem;
    private int startItem;
    
    private Target targetstage1;
    private Target targetstage2;
    private Target targetstage3;
    private Target pluslife;
    private Color textcolor;
    private View view;
    
    public GameManagement(int stage)
    {
        View.gameState = View.GameState.GAME_CONTENT_LOADING;
        this.stage = stage;
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                initialize();
                loadContent();
                View.gameState = View.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    

    
    private void initialize()	// Set variables and objects for the game.
    {
        random = new Random();        
        font = new Font("monospaced", Font.BOLD, 18);
        arrayShoot = new ArrayList<Target>();
        arrayItem = new ArrayList<Target>(); 
        lifePoint = 30;
        killedPoint = 0;
        score = 0;
        shootsPoint = 0;
        startItem = 0;
        timeItem = random.nextInt(55-(25+1))+25;
        lastTimeShoot = 0;
        timeBetweenShots = View.secInNanosec / 10;
 
    }
    
    private void loadContent()  // Set image
    {
        try
        {
            URL backgroundCityImgUrl = this.getClass().getResource("/resources/background_city3.jpg");
            backgroundCityImg = ImageIO.read(backgroundCityImgUrl);
            
            URL backgroundSpaceImgUrl = this.getClass().getResource("/resources/background_space.jpg");
            backgroundSpaceImg = ImageIO.read(backgroundSpaceImgUrl);
            
            URL backgroundGhostImgUrl = this.getClass().getResource("/resources/background_ghost.jpg");
            backgroundGhostImg = ImageIO.read(backgroundGhostImgUrl);

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
            
            URL ghostLine2ImgUrl = this.getClass().getResource("/resources/ghost2.png");
            ghostLine2Img = ImageIO.read(ghostLine2ImgUrl);
            
            URL ghostLine3ImgUrl = this.getClass().getResource("/resources/ghost3.png");
            ghostLine3Img = ImageIO.read(ghostLine3ImgUrl);
            
            URL ghostLine4ImgUrl = this.getClass().getResource("/resources/ghost4.png");
            ghostLine4Img = ImageIO.read(ghostLine4ImgUrl);
            
            URL ghostLine5ImgUrl = this.getClass().getResource("/resources/ghost5.png");
            ghostLine5Img = ImageIO.read(ghostLine5ImgUrl);
            
            URL ghostLine6ImgUrl = this.getClass().getResource("/resources/ghost6.png");
            ghostLine6Img = ImageIO.read(ghostLine6ImgUrl);
            
            URL ghostLine7ImgUrl = this.getClass().getResource("/resources/ghost7.png");
            ghostLine7Img = ImageIO.read(ghostLine7ImgUrl);

            URL targetBlackImgUrl = this.getClass().getResource("/resources/TargetRed.png");
            targetBlackImg = ImageIO.read(targetBlackImgUrl);
            targetImgMiddleWidth = targetBlackImg.getWidth() / 5;
            targetImgMiddleHeight = targetBlackImg.getHeight() / 5;
            
            URL fontUrl = this.getClass().getResource("/resources/Font2.ttf");
            fontResult = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
            fontResult = fontResult.deriveFont(Font.BOLD,40);
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(fontResult);
	        
	        
            URL lifeImgUrl = this.getClass().getResource("/resources/life.png");
            lifeImg = ImageIO.read(lifeImgUrl);
            pluslife = new Target(random.nextInt(700)+100,-150,3,0,lifeImg);
            arrayItem.add(pluslife);
            
            targetstage1 = new Stage1(0,0,0,0, backgroundCityImg);
            targetstage2 = new Stage2(0,0,0,0, backgroundSpaceImg);
            targetstage3 = new Stage3(0,0,0,0, backgroundGhostImg);
            
        }
        catch (IOException ex) {
            Logger.getLogger(GameManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FontFormatException e) {
        	e.printStackTrace();
        }
    }
    
    
    public void restartGame()
    {
        arrayShoot.clear();
        arrayItem.clear();
        
        lifePoint = 30;
        killedPoint = 0;
        score = 0;
        shootsPoint = 0;
        pluslife = new Target(random.nextInt(700)+100,-150,3,0,lifeImg);
        arrayItem.add(pluslife);  
        targetstage1 = new Stage1(0,0,0,0, backgroundCityImg);
        targetstage2 = new Stage2(0,0,0,0, backgroundSpaceImg);
        targetstage3 = new Stage3(0,0,0,0, backgroundGhostImg);
        
        targetstage1.setLastShootTime(0);
        targetstage2.setLastShootTime(0);
        targetstage3.setLastShootTime(0);
        startItem = 0;
        timeItem = random.nextInt(55-(25+1))+25;
        lastTimeShoot = 0;
    }
    
    public void setStage(){		//Set shoot line each stage
    
    	int[][] shootlinestage1 = targetstage1.getShootLines();
    	int[][] shootlinestage2 = targetstage2.getShootLines();
    	int[][] shootlinestage3 = targetstage3.getShootLines();
    	
    	if(stage == 1){
    		if(targetstage1.getNextShootLine() == 0){
                arrayShoot.add(new Target(shootlinestage1[targetstage1.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage1[targetstage1.getNextShootLine()][1], shootlinestage1[targetstage1.getNextShootLine()][2],
                		shootlinestage1[targetstage1.getNextShootLine()][3], carLine1Img));
        	}
        	else if(targetstage1.getNextShootLine() == 1){
                arrayShoot.add(new Target(shootlinestage1[targetstage1.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage1[targetstage1.getNextShootLine()][1], shootlinestage1[targetstage1.getNextShootLine()][2],
                		shootlinestage1[targetstage1.getNextShootLine()][3], carLine2Img));
        	}
        	else if(targetstage1.getNextShootLine() == 2){
                arrayShoot.add(new Target(shootlinestage1[targetstage1.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage1[targetstage1.getNextShootLine()][1], shootlinestage1[targetstage1.getNextShootLine()][2],
                		shootlinestage1[targetstage1.getNextShootLine()][3], carLine3Img));
        	}
    		targetstage1.setNextShootLine();
            if(targetstage1.getNextShootLine() >= targetstage1.getShootLines().length)
            	targetstage1.setReNextShootLine();
            
    	}
    	else if(stage == 2){
    		if(targetstage2.getNextShootLine() == 0){
                arrayShoot.add(new Target(shootlinestage2[targetstage2.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage2[targetstage2.getNextShootLine()][1], shootlinestage2[targetstage2.getNextShootLine()][2],
                		shootlinestage2[targetstage2.getNextShootLine()][3], spaceLine1Img));
        	}
        	else if(targetstage2.getNextShootLine() == 1){
                arrayShoot.add(new Target(shootlinestage2[targetstage2.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage2[targetstage2.getNextShootLine()][1], shootlinestage2[targetstage2.getNextShootLine()][2],
                		shootlinestage2[targetstage2.getNextShootLine()][3], spaceLine3Img));
        	}
        	else if(targetstage2.getNextShootLine() == 2){
                arrayShoot.add(new Target(shootlinestage2[targetstage2.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage2[targetstage2.getNextShootLine()][1], shootlinestage2[targetstage2.getNextShootLine()][2],
                		shootlinestage2[targetstage2.getNextShootLine()][3], spaceLine2Img));
        	}
        	else if(targetstage2.getNextShootLine() == 3){
                arrayShoot.add(new Target(shootlinestage2[targetstage2.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage2[targetstage2.getNextShootLine()][1], shootlinestage2[targetstage2.getNextShootLine()][2],
                		shootlinestage2[targetstage2.getNextShootLine()][3], spaceLine4Img));
        	}
    		
    		targetstage2.setNextShootLine();
            if(targetstage2.getNextShootLine() >= targetstage2.getShootLines().length)
            	targetstage2.setReNextShootLine();
    	}
    	else if(stage == 3){
    		if(targetstage3.getNextShootLine() == 0){
                arrayShoot.add(new Target(shootlinestage3[targetstage3.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage3[targetstage3.getNextShootLine()][1], shootlinestage3[targetstage3.getNextShootLine()][2],
                		shootlinestage3[targetstage3.getNextShootLine()][3], ghostLine1Img));
        	}
        	else if(targetstage3.getNextShootLine() == 1){
                arrayShoot.add(new Target(shootlinestage3[targetstage3.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage3[targetstage3.getNextShootLine()][1], shootlinestage3[targetstage3.getNextShootLine()][2],
                		shootlinestage3[targetstage3.getNextShootLine()][3], ghostLine3Img));
        	}
        	else if(targetstage3.getNextShootLine() == 2){
                arrayShoot.add(new Target(shootlinestage3[targetstage3.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage3[targetstage3.getNextShootLine()][1], shootlinestage3[targetstage3.getNextShootLine()][2],
                		shootlinestage3[targetstage3.getNextShootLine()][3], ghostLine7Img));
        	}
        	else if(targetstage3.getNextShootLine() == 3){
                arrayShoot.add(new Target(shootlinestage3[targetstage3.getNextShootLine()][0] + random.nextInt(200),
                		shootlinestage3[targetstage3.getNextShootLine()][1], shootlinestage3[targetstage3.getNextShootLine()][2],
                		shootlinestage3[targetstage3.getNextShootLine()][3], ghostLine5Img));
        	}
    		
    		targetstage3.setNextShootLine();
            if(targetstage3.getNextShootLine() >= targetstage3.getShootLines().length)
            	targetstage3.setReNextShootLine();
    	}
    
    	
    }
    
    public void updateGame(long gameTime, Point mousePosition)
    {

        if(stage == 1){
        	setPositiontoClick = carLine1Img;
        	setRemoveObjectImg = carLine2Img;
        	timeBetweenObject = targetstage1.getTimeBetweenObject();
        }
        else if(stage == 2){
        	setPositiontoClick = spaceLine3Img;
        	setRemoveObjectImg = spaceLine3Img;
        	timeBetweenObject = targetstage2.getTimeBetweenObject();
        }
        else if(stage == 3){
        	setPositiontoClick = ghostLine1Img;
        	setRemoveObjectImg = ghostLine1Img;
        	timeBetweenObject = targetstage3.getTimeBetweenObject();
        }
    	
        if(System.nanoTime() - targetstage1.getLastShootTime() >= timeBetweenObject)
        {
        	setStage();
            targetstage1.setLastShootTime(System.nanoTime());
        }
        
        for(int i = 0; i < arrayShoot.size(); i++)
        {
            // Move shoot object.
            arrayShoot.get(i).upDate();
            
            // Checks if shoot object leaves the screen and remove it
            if(arrayShoot.get(i).getY() == (int)(View.frameHeight * targetstage1.getObjectHeight1()) ||
            	arrayShoot.get(i).getY() == (int)(View.frameHeight * targetstage1.getObjectHeight4()) ||
            	arrayShoot.get(i).getY() == (int)(View.frameHeight * targetstage1.getObjectHeight7()) ||
            	arrayShoot.get(i).getY() == (int)(View.frameHeight * targetstage1.getObjectHeight9())) {
            	
            	if(arrayShoot.get(i).getX() > View.frameWidth)
                {
                	arrayShoot.remove(i);
                    lifePoint--;
                }
            }
            else{
            	 if(arrayShoot.get(i).getX() < -setRemoveObjectImg.getWidth())
                 {
                 	arrayShoot.remove(i);
                     lifePoint--;
                 }
            }
                       
        }
    

        if(Keysetting.mouseButtonState(MouseEvent.BUTTON1))
        {
            if(System.nanoTime() - lastTimeShoot >= timeBetweenShots)
            {
                shootsPoint++;
                
                // Set Position to click shoot object
                for(int i = 0; i < arrayShoot.size(); i++)
                {
                    if(new Rectangle(arrayShoot.get(i).getX(), arrayShoot.get(i).getY() , setPositiontoClick.getWidth()-30
                    		, setPositiontoClick.getHeight()-35).contains(mousePosition))
                    {
                        killedPoint++;
                        score += arrayShoot.get(i).getScore();
                        arrayShoot.remove(i);
                        
                        break;
                    }
                }
                
                lastTimeShoot = System.nanoTime();
                for(int i = 0; i < arrayItem.size(); i++)
                {
                	if(new Rectangle(pluslife.getX(), pluslife.getY() , setPositiontoClick.getWidth()-30, setPositiontoClick.getHeight()-35).contains(mousePosition))
                    {
        
                    	lifePoint = lifePoint+5;
                    	arrayItem.remove(i);
                    	break;
                    }
                }
                
            }
        }
        
        missPoint = shootsPoint-killedPoint;
        
        // When lifePoint < 0, the game ends.
        if(lifePoint <= 0){
            View.gameState = View.GameState.GAMEOVER;
        }
        
        if(View.seconds == timeItem){
            startItem = 1;
        }
   
        if(startItem == 1){
        	for(int i = 0; i < arrayItem.size(); i++){
        		arrayItem.get(i).upDateLife();
        	}
        }
    }
    
    public void calculateScore(){
    	totalLifeScore = lifePoint*100;
    	totalMissScore = missPoint*30;
    	totalScore = (score+totalLifeScore)-totalMissScore;
    }
    
    public void Draw(Graphics2D g2d, Point mousePosition)  // Draw Object
    {
    	if(stage == 1){
    		g2d.drawImage(backgroundCityImg, 0, 0, View.frameWidth, View.frameHeight, null);
    		textcolor = Color.blue;
    	}
    	else if(stage == 2){
    		g2d.drawImage(backgroundSpaceImg, 0, 0, View.frameWidth, View.frameHeight, null);
    		textcolor = Color.MAGENTA;
    	}
    	else if(stage == 3){
    		g2d.drawImage(backgroundGhostImg, 0, 0, View.frameWidth, View.frameHeight, null);
    		textcolor = Color.GREEN;
    	}
        
        for(int i = 0; i < arrayShoot.size(); i++)
        {
            arrayShoot.get(i).Draw(g2d);
        }
        
        for(int i = 0; i < arrayItem.size(); i++)
        {
        	 arrayItem.get(0).Draw(g2d);
        }
       
        //pluslife.Draw(g2d);
        
        //g2d.drawImage(grassImg, 0, Framework.frameHeight - grassImg.getHeight(), Framework.frameWidth, grassImg.getHeight(), null);
        
        g2d.drawImage(targetBlackImg, mousePosition.x - targetImgMiddleWidth, mousePosition.y - targetImgMiddleHeight, null);
        
        g2d.setFont(font);
        g2d.setColor(textcolor);
        
        g2d.drawString("LIFE: " + lifePoint, 240, 21);
        g2d.drawString("KILLS: " + killedPoint, 370, 21);
        g2d.drawString("MISS: " + missPoint, 510, 21);
       //g2d.drawString("SHOOTS: " + shootsPoint, 499, 21);
        g2d.drawString("SCORE: " + score, 640, 21);
    }
    
    public void drawGameOver(Graphics2D g2d, Point mousePosition)
    {
    	calculateScore();
        Draw(g2d, mousePosition);

        g2d.setColor(Color.BLACK);
        g2d.setFont(fontResult);
        g2d.drawString("Game Over", View.frameWidth / 2 - 68, (int)(View.frameHeight * 0.32) + 2);
        g2d.drawString("Press enter to next >>>>>.", View.frameWidth / 2 - 188, (int)(View.frameHeight * 0.82) + 2);
        g2d.drawString("SCORE : " + score, View.frameWidth / 2 - 188, (int)(View.frameHeight * 0.42+2));
        g2d.drawString("LIFE BONUS : " + lifePoint +" x 100 = "+totalLifeScore, View.frameWidth / 2 - 188, (int)(View.frameHeight * 0.52+2));
        g2d.drawString("MISSED SHOT : " + missPoint +" x 30 = -"+totalMissScore, View.frameWidth / 2 - 188, (int)(View.frameHeight * 0.62+2));
        g2d.drawString("TOTAL SCORE : " + totalScore, View.frameWidth / 2 - 188, (int)(View.frameHeight * 0.72+2));
        
        g2d.setColor(Color.red);
        g2d.setFont(fontResult);
        g2d.drawString("Game Over", View.frameWidth / 2 - 70, (int)(View.frameHeight * 0.32));
        g2d.drawString("Press enter to next >>>>>.", View.frameWidth / 2 - 190, (int)(View.frameHeight * 0.82));
        
        
        g2d.setColor(Color.green);
        g2d.setFont(fontResult);
        g2d.drawString("SCORE : " + score, View.frameWidth / 2 - 190, (int)(View.frameHeight * 0.42));
        g2d.drawString("LIFE BONUS : " + lifePoint +" x 100 = "+totalLifeScore, View.frameWidth / 2 - 190, (int)(View.frameHeight * 0.52));
        g2d.drawString("MISSED SHOT : " + missPoint +" x 30 = -"+totalMissScore, View.frameWidth / 2 - 190, (int)(View.frameHeight * 0.62));
        g2d.drawString("TOTAL SCORE : " + totalScore, View.frameWidth / 2 - 190, (int)(View.frameHeight * 0.72));   

    }
    
    public int getTotalScore(){
    	return totalScore;
    }
    
    public int getStage(){
    	return stage;
    }
}
