package Model;

import java.awt.image.BufferedImage;

public class Stage2 extends Target{
	
	private int[][] shootSpaceLines = {
		{View.frameWidth, (int)(View.frameHeight * objectHeight8), -6, 100},
	{(int) (View.frameWidth-View.frameWidth*1.5), (int)(View.frameHeight * objectHeight9), 2, 80},
		{View.frameWidth, (int)(View.frameHeight * objectHeight10), -2, 80},
		{View.frameWidth, (int)(View.frameHeight * objectHeight11), -3, 70}};

    private long timeBetweenObject = View.secInNanosec / 2;
    
	public Stage2(int x, int y, int speed, int score, BufferedImage shootImg) {
		super(x, y, speed, score, shootImg);
		// TODO Auto-generated constructor stub
	}
	
	
	public int[][] getShootLines(){
		return shootSpaceLines;
	}
	
	public Long getTimeBetweenObject(){
		return timeBetweenObject;
	}

}