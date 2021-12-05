import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ArrayStorer {
	/**
	 * 
	 */

	public static void saveArrayToFile(String fileLocation, ArrayList<RenderObject> saveArray) {
		String tmp = saveArray.toString();
	    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
	    pw.write(tmp);
	    pw.close();
	}
}
