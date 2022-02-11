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
}