import javafx.stage.Stage;

public class Main {
	private static Stage mainStage = new Stage();
	
	public static void main(String[] args) {
		
		RatGameApp mainMenu = new RatGameApp(mainStage);
		try {
			mainMenu.startMenu();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
