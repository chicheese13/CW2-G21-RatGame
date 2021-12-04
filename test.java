import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;
import java.io.*;

public class test {
	private static ArrayList<TestClass> renderRats = new ArrayList<TestClass>();
	
	public static void main(String[] args) {
		renderRats.add(new TestClass("helo", "hi", "bye"));
		renderRats.add(new TestClass("helo1", "hi", "bye"));
		renderRats.add(new TestClass("helo2", "hi", "bye"));
		renderRats.add(new TestClass("helo3", "hi", "bye"));
		saveArrayToFile(renderRats, "src/tstArray/");
		fetchArrayFromFile();
	}
	
	public static void saveArrayToFile(ArrayList<TestClass> saveArray, String saveLocation) {
		try{
			
		    FileOutputStream writeData = new FileOutputStream("src/tstArray/rats.ser");
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
		    FileInputStream readData = new FileInputStream("src/tstArray/rats.ser");
		    ObjectInputStream readStream = new ObjectInputStream(readData);

		    ArrayList<TestClass> renderRats2 = (ArrayList<TestClass>) readStream.readObject();
		    readStream.close();
		    System.out.println(renderRats2.get(0).getProfile());
		}catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
