package Model;

import java.awt.image.BufferedImage;

public class Stage3 extends Target{

	private int[][] shootGhostLines = {
    	{(int) (View.frameWidth-View.frameWidth*1.5), (int)(View.frameHeight * objectHeight4), 2, 80},
    		{View.frameWidth, (int)(View.frameHeight * objectHeight5), -3, 70},
    		{View.frameWidth, (int)(View.frameHeight * objectHeight6), -4, 100},
    	{(int) (View.frameWidth-View.frameWidth*1.5), (int)(View.frameHeight * objectHeight7), 3, 90}};
    
    private long timeBetweenObject = View.secInNanosec / 3;
	
	public Stage3(int x, int y, int speed, int score, BufferedImage shootImg) {
		super(x, y, speed, score, shootImg);
		// TODO Auto-generated constructor stub
	}

	public int[][] getShootLines(){
		return shootGhostLines;
	}
	
	public Long getTimeBetweenObject(){
		return timeBetweenObject;
	}
	
}