package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class ConnectFile {
	private ArrayList<String> arrayreadscore;
	private ArrayList<Integer> arraysort;
	private ArrayList<String> arrayresult;
	
	public ArrayList readScoreFile(int stage) {
		FileReader read;
		try {
			String file = "";
			if(stage == 1){
				file = "highscore_stage1.txt";
			}
			else if(stage == 2){
				file = "highscore_stage2.txt";
			}
			else if(stage == 3){
				file = "highscore_stage3.txt";
			}
			
			read = new FileReader(file);
			BufferedReader buffread = new BufferedReader(read);
			arrayreadscore = new <String>ArrayList();
			arraysort = new <Integer>ArrayList();
			arrayresult = new <String>ArrayList();
			String text;
			for(text = buffread.readLine(); text != null; text = buffread.readLine()){
				String[] splited = text.split("\\s+");
				String score = splited[2];
				
				arrayreadscore.add(text);
				arraysort.add(Integer.parseInt(score));
			}
			
			Collections.sort(arraysort,Collections.reverseOrder());
			
			int count = 0;
			while(count<arraysort.size()){
				
				for(int i = 0;i<arrayreadscore.size();i++){
					String[] splited = arrayreadscore.get(i).split("\\s+");
					String score = splited[2];
					if(Integer.parseInt(score) == arraysort.get(count)){
						arrayresult.add(arrayreadscore.get(i));
					}
				}
				count++;
			}

		} catch (FileNotFoundException e) {
			System.err.println("file not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayresult;
	}

	
	public void writeFile(int stage ,String name, int score) { 
		try{
			String file = "";
			if(stage == 1){
				file = "highscore_stage1.txt";
			}
			else if(stage == 2){
				file = "highscore_stage2.txt";
			}
			else if(stage == 3){
				file = "highscore_stage3.txt";
			}
			
			FileWriter write = new FileWriter(file,true);
			PrintWriter output = new PrintWriter(new BufferedWriter(write));
			output.println(name + " : "+ score);
			output.flush();
		}
		catch (FileNotFoundException e){
			System.err.println("file not found");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}

}
