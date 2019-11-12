package Model;

import java.awt.image.BufferedImage;

public class Stage1 extends Target{

    private int[][] shootCarLines = {
    	{(int) (View.frameWidth-View.frameWidth*1.5), (int)(View.frameHeight * objectHeight1), 2, 60},
        	{View.frameWidth, (int)(View.frameHeight * objectHeight2), -3, 50},
        	{View.frameWidth, (int)(View.frameHeight * objectHeight3), -4, 50}};
	
    private long timeBetweenObject = View.secInNanosec / 2;
    
	public Stage1(int x, int y, int speed, int score, BufferedImage shootImg) {
		super(x, y, speed, score, shootImg);
		// TODO Auto-generated constructor stub
	}

	
	public int[][] getShootLines(){
		return shootCarLines;
	}
	
	public Long getTimeBetweenObject(){
		return timeBetweenObject;
	}
	
}
