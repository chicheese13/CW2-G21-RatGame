import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ArrayStorage implements Serializable {
	static ArrayList<RenderObject> renderRats;
	ArrayList<RenderObject> renderItems = new ArrayList<RenderObject>();
	ArrayList<RenderObject> renderTempTiles = new ArrayList<RenderObject>();
	
	public ArrayStorage() {
		renderRats = new ArrayList<RenderObject>();
	}
	
	public void addElement(RenderObject element) {
		renderRats.add(element);
	}
	
	public static void saveArrayToFile(String saveLocation) {
		try{
			
		    FileOutputStream writeData = new FileOutputStream(saveLocation+"/rats.ser");
		    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
		    
		    writeStream.writeObject(renderRats);
		    writeStream.flush();
		    writeStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void removeElement() {
		
	}
	
	public ArrayList<RenderObject> fetchArray(String arrayDesired){
		if (arrayDesired == "rat") {
			return renderRats;
		} else if (arrayDesired == "item") {
			return renderItems;
		} else if (arrayDesired == "temp-tile") {
			return renderTempTiles;
		}
		return null;
	}	
}
