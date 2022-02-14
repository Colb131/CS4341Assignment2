import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
  public static ArrayList<Float> read(String arg) {
    try {
      ArrayList<Float> floatList = new ArrayList<Float>();	
      File myObj = new File(arg);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        //System.out.println(data);
        float dataAsFloat = Float.parseFloat(data);
        floatList.add(dataAsFloat);
      }
      myReader.close();
      return floatList;
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      return null;
    }
  }
  
  public static ArrayList<TowerPiece> read2(String arg) {
	    try {
	      ArrayList<TowerPiece> pieceList = new ArrayList<TowerPiece>();	
	      File myObj = new File(arg);
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        String[] onePieceData = data.split("\t");
	        String[] cleanData = {};
	        for (String s : onePieceData)
	        {
	        	
	        	s.replaceAll("\t", "");
	        	s.replaceAll("\\\\s", "");
	        	s.replaceAll("\s+","");
	        	
	        	//System.out.print(s + "---");
	        }
	        //System.out.println(data);
	        TowerPiece dataAsPiece = new TowerPiece();
	        dataAsPiece.type = onePieceData[0];
	        dataAsPiece.width = Integer.parseInt(onePieceData[1]);
	        dataAsPiece.strength = Integer.parseInt(onePieceData[2]);
	        dataAsPiece.cost = Integer.parseInt(onePieceData[3]);
	        pieceList.add(dataAsPiece);
	        //System.out.print("\n");
	      }
	      
	      /*for(TowerPiece e : pieceList)
	      {
	    	  System.out.print(e.type + "\t");
	    	  System.out.print(e.width + "\t");
	    	  System.out.print(e.strength + "\t");
	    	  System.out.print(e.cost + "\t");
	      }*/
	      myReader.close();
	      return pieceList;
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	      return null;
	    }
	  }
}