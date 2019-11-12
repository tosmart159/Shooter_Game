package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Target {

    protected final double objectHeight1 = 0.50;
    protected final double objectHeight2 = 0.65;
    protected final double objectHeight3 = 0.78;
    
    protected final double objectHeight4 = 0.30;
    protected final double objectHeight5 = 0.45;
    protected final double objectHeight6 = 0.58;
    protected final double objectHeight7 = 0.75;
    
    protected final double objectHeight8 = 0.25;
    protected final double objectHeight9 = 0.40;
    protected final double objectHeight10 = 0.68;
    protected final double objectHeight11 = 0.80;
    
    private int x;
    private int y;
    private int speed;
    private int score;
    private int [][]shootLines = null;
    private int nextShootLine = 0;
    private long lastShootTime = 0;
    private long timeBetweenObject = 0;
    
    private Random random;
    private BufferedImage shootImg;

    
    public Target(int x, int y, int speed, int score, BufferedImage shootImg)
    {
        this.x = x;
        this.y = y;      
        this.speed = speed;    
        this.score = score;
        this.shootImg = shootImg; 
        random = new Random(); 
    }
    

    public void upDate() // Move shoot object
    {
        x = x+speed;
        
    }
    
    public void upDateLife() // Move Item object
    {
        y = y+speed;

    }

    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(shootImg, x, y, null);
    }
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }
    
    public int getScore(){
    	return score;
    }
    
	public int[][] getShootLines(){
		return shootLines;
	}
	
	public Long getTimeBetweenObject(){
		return timeBetweenObject;
	}
	
	public int getNextShootLine(){
		return nextShootLine;
	}
	
	public void setNextShootLine(){
		nextShootLine++;
	}
	
	public void setReNextShootLine(){
		nextShootLine = 0;
	}
	
	public Long getLastShootTime(){
		return lastShootTime;
	}
	
	public void setLastShootTime(long revalue){
		lastShootTime = revalue;
	}
	
	public double getObjectHeight1(){
		return objectHeight1;
	}
	
	public double getObjectHeight4(){
		return objectHeight4;
	}
	
	public double getObjectHeight7(){
		return objectHeight7;
	}
	
	public double getObjectHeight9(){
		return objectHeight9;
	}
    
}
