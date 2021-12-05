import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.io.*;

public class test {
	private static ArrayList<TestClass> renderRats = new ArrayList<TestClass>();
	
	public static void main(String[] args) {
		fetchArrayFromFile();
	}
	
	public static void saveArrayToFile(ArrayList<TestClass> saveArray, String saveLocation) {
		try{
			
		    FileOutputStream writeData = new FileOutputStream("src/rats.ser");
		    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

		    writeStream.writeObject(saveArray);
		    writeStream.flush();
		    writeStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void fetchArrayFromFile() {
		try{
		    FileInputStream readData = new FileInputStream("src/rats.ser");
		    ObjectInputStream readStream = new ObjectInputStream(readData);

		    ArrayList<RenderObject> renderRats2 = (ArrayList<RenderObject>) readStream.readObject();
		    readStream.close();
		    System.out.println(renderRats2.get(0).getSprite());
		}catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
