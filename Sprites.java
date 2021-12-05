import java.util.Hashtable;
import javafx.scene.image.Image;

public class Sprites {
	 //All Rats
	protected final static Image BABY_RAT_SPRITE_EAST = new Image("TestTextures/baby-rat-west.png");
	protected final static Image BABY_RAT_SPRITE_NORTH = new Image("TestTextures/baby-rat-north.png");
	protected final static Image BABY_RAT_SPRITE_SOUTH = new Image("TestTextures/baby-rat-south.png");
	protected final static Image BABY_RAT_SPRITE_WEST = new Image("TestTextures/baby-rat-west.png");
	
	protected final Image COOLDOWN_SPRITE_MALE = new Image("Textures/test-sprite-one.png");
    protected final Image COOLDOWN_SPRITE_FEMALE = new Image("Textures/test-sprite-two.png");

    protected final Image ADULT_MALE_RAT_SPRITE_NORTH = new Image("Textures/male-rat-north.png");
    protected final Image ADULT_MALE_RAT_SPRITE_EAST = new Image("Textures/male-rat-east.png");
    protected final Image ADULT_MALE_RAT_SPRITE_SOUTH = new Image("Textures/male-rat-south.png");
    protected final Image ADULT_MALE_RAT_SPRITE_WEST = new Image("Textures/male-rat-west.png");

    protected final Image ADULT_FEMALE_RAT_SPRITE_NORTH = new Image("Textures/female-rat-north.png");
    protected final Image ADULT_FEMALE_RAT_SPRITE_EAST = new Image("Textures/female-rat-east.png");
    protected final Image ADULT_FEMALE_RAT_SPRITE_SOUTH = new Image("Textures/female-rat-south.png");
    protected final Image ADULT_FEMALE_RAT_SPRITE_WEST = new Image("Textures/female-rat-west.png");

    protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH = new Image("Textures/female-rat-pregnant-north.png");
    protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST = new Image("Textures/female-rat-pregnant-east.png");
    protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH = new Image("Textures/female-rat-pregnant-south.png");
    protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST = new Image("Textures/female-rat-pregnant-west.png");
    
    protected final Image BOMB_SPRITE =  new Image("/items/bomb.png");

	
	static Hashtable<String,Image> sprites = new Hashtable<String,Image>();
	
	public Sprites() {
		sprites.put("baby-rat-north", BABY_RAT_SPRITE_NORTH);
		sprites.put("baby-rat-south", BABY_RAT_SPRITE_SOUTH);
		sprites.put("baby-rat-east", BABY_RAT_SPRITE_EAST);
		sprites.put("baby-rat-west", BABY_RAT_SPRITE_WEST);
		
		sprites.put("cooldown-male", COOLDOWN_SPRITE_MALE);
		sprites.put("cooldown-female", COOLDOWN_SPRITE_FEMALE);
		
		sprites.put("male-rat-north", ADULT_MALE_RAT_SPRITE_NORTH);
		sprites.put("male-rat-east", ADULT_MALE_RAT_SPRITE_EAST);
		sprites.put("male-rat-south", ADULT_MALE_RAT_SPRITE_SOUTH);
		sprites.put("male-rat-west", ADULT_MALE_RAT_SPRITE_WEST);
		
		sprites.put("female-rat-north", ADULT_FEMALE_RAT_SPRITE_NORTH);
		sprites.put("female-rat-east", ADULT_FEMALE_RAT_SPRITE_EAST);
		sprites.put("female-rat-south", ADULT_FEMALE_RAT_SPRITE_SOUTH);
		sprites.put("female-rat-west", ADULT_FEMALE_RAT_SPRITE_WEST);
		
		sprites.put("female-pregnant-rat-north", ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH);
		sprites.put("female-pregnant-rat-east", ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST);
		sprites.put("female-pregnant-rat-south", ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH);
		sprites.put("female-pregnant-rat-west", ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST);
		
		sprites.put("bomb1", new Image("bomb_images/bomb1.png"));
		sprites.put("bomb2", new Image("bomb_images/bomb2.png"));
		sprites.put("bomb3", new Image("bomb_images/bomb3.png"));
		sprites.put("bomb4", new Image("bomb_images/bomb4.png"));
		sprites.put("bomb5", new Image("bomb_images/bomb5.png"));
		sprites.put("bomb6", new Image("bomb_images/bomb6.png"));
		sprites.put("bomb7", new Image("bomb_images/bomb7.png"));
		sprites.put("bomb8", new Image("bomb_images/bomb8.png"));
		sprites.put("bomb9", new Image("bomb_images/bomb9.png"));
		sprites.put("bomb10", new Image("bomb_images/bomb10.png"));
		sprites.put("bomb11", new Image("bomb_images/bomb11.png"));
		sprites.put("bomb12", new Image("bomb_images/bomb12.png"));
		sprites.put("bomb13", new Image("bomb_images/bomb13.png"));
		sprites.put("bomb14", new Image("bomb_images/bomb14.png"));
		sprites.put("bomb15", new Image("bomb_images/bomb15.png"));
		sprites.put("bomb16", new Image("bomb_images/bomb16.png"));
		sprites.put("bomb17", new Image("bomb_images/bomb17.png"));
		
		sprites.put("bomb-delayed1", new Image("delayed_bomb_images/bomb1.png"));
		sprites.put("bomb-delayed2", new Image("delayed_bomb_images/bomb2.png"));
		sprites.put("bomb-delayed3", new Image("delayed_bomb_images/bomb3.png"));
		sprites.put("bomb-delayed4", new Image("delayed_bomb_images/bomb4.png"));
		
		sprites.put("explosion", new Image("Textures/explosion.png"));
		
		sprites.put("poison", new Image("/items/poisononlevel.png"));
		
		sprites.put("sprinkler", new Image("/gas_images/Sprinkler.png"));
		sprites.put("sprinkler-broken", new Image("/gas_images/SprinklerBroken.png"));
		sprites.put("sprinkler-1", new Image("/gas_images/Sprinker1.png"));
		sprites.put("sprinkler-2", new Image("/gas_images/Sprinker2.png"));
		sprites.put("sprinkler-3", new Image("/gas_images/Sprinker3.png"));
		sprites.put("sprinkler-4", new Image("/gas_images/Sprinker4.png"));
		sprites.put("sprinkler-5", new Image("/gas_images/Sprinker5.png"));
	}
	
	public Image getImage(String image) {
		return sprites.get(image);
	}
	

}
