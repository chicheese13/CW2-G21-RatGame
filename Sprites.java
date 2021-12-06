import java.util.Hashtable;
import javafx.scene.image.Image;

public class Sprites {
	// All Rats
	protected final static Image BABY_RAT_SPRITE_EAST = new Image(
			"TestTextures/rat-model-east.png");
	protected final static Image BABY_RAT_SPRITE_NORTH = new Image(
			"TestTextures/rat-model-north.png");
	protected final static Image BABY_RAT_SPRITE_SOUTH = new Image(
			"TestTextures/rat-model-south.png");
	protected final static Image BABY_RAT_SPRITE_WEST = new Image(
			"TestTextures/rat-model-west.png");

	protected final Image COOLDOWN_SPRITE_FEMALE_NORTH = new Image(
			"Textures/rat-female-exhausted-north.png");
	protected final Image COOLDOWN_SPRITE_FEMALE_EAST = new Image(
			"Textures/rat-female-exhausted-east.png");
	protected final Image COOLDOWN_SPRITE_FEMALE_SOUTH = new Image(
			"Textures/rat-female-exhausted-south.png");
	protected final Image COOLDOWN_SPRITE_FEMALE_WEST = new Image(
			"Textures/rat-female-exhausted-west.png");

	protected final Image COOLDOWN_SPRITE_MALE_NORTH = new Image(
			"Textures/rat-exhausted-male-north.png");
	protected final Image COOLDOWN_SPRITE_MALE_EAST = new Image(
			"Textures/rat-exhausted-male-east.png");
	protected final Image COOLDOWN_SPRITE_MALE_SOUTH = new Image(
			"Textures/rat-exhausted-male-south.png");
	protected final Image COOLDOWN_SPRITE_MALE_WEST = new Image(
			"Textures/rat-exhausted-male-west.png");

	protected final Image ADULT_MALE_RAT_SPRITE_NORTH = new Image(
			"Textures/rat-male-north.png");
	protected final Image ADULT_MALE_RAT_SPRITE_EAST = new Image(
			"Textures/rat-male-east.png");
	protected final Image ADULT_MALE_RAT_SPRITE_SOUTH = new Image(
			"Textures/rat-male-south.png");
	protected final Image ADULT_MALE_RAT_SPRITE_WEST = new Image(
			"Textures/rat-male-west.png");

	protected final Image ADULT_FEMALE_RAT_SPRITE_NORTH = new Image(
			"Textures/rat-female-north.png");
	protected final Image ADULT_FEMALE_RAT_SPRITE_EAST = new Image(
			"Textures/rat-female-east.png");
	protected final Image ADULT_FEMALE_RAT_SPRITE_SOUTH = new Image(
			"Textures/rat-female-south.png");
	protected final Image ADULT_FEMALE_RAT_SPRITE_WEST = new Image(
			"Textures/rat-female-west.png");

	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH = new Image(
			"Textures/rat-pregnant-north.png");
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST = new Image(
			"Textures/rat-pregnant-east.png");
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH = new Image(
			"Textures/rat-pregnant-south.png");
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST = new Image(
			"Textures/rat-pregnant-west.png");

	protected final Image BOMB_SPRITE = new Image("/items/bomb.png");

	static Hashtable<String, Image> sprites = new Hashtable<String, Image>();

	public Sprites() {
		sprites.put("baby-rat-north", BABY_RAT_SPRITE_NORTH);
		sprites.put("baby-rat-south", BABY_RAT_SPRITE_SOUTH);
		sprites.put("baby-rat-east", BABY_RAT_SPRITE_EAST);
		sprites.put("baby-rat-west", BABY_RAT_SPRITE_WEST);

		sprites.put("cooldown-male-north", COOLDOWN_SPRITE_MALE_NORTH);
		sprites.put("cooldown-male-south", COOLDOWN_SPRITE_MALE_SOUTH);
		sprites.put("cooldown-male-east", COOLDOWN_SPRITE_MALE_EAST);
		sprites.put("cooldown-male-west", COOLDOWN_SPRITE_MALE_WEST);

		sprites.put("cooldown-female-north", COOLDOWN_SPRITE_FEMALE_NORTH);
		sprites.put("cooldown-female-south", COOLDOWN_SPRITE_FEMALE_SOUTH);
		sprites.put("cooldown-female-east", COOLDOWN_SPRITE_FEMALE_EAST);
		sprites.put("cooldown-female-west", COOLDOWN_SPRITE_FEMALE_WEST);

		sprites.put("male-rat-north", ADULT_MALE_RAT_SPRITE_NORTH);
		sprites.put("male-rat-east", ADULT_MALE_RAT_SPRITE_EAST);
		sprites.put("male-rat-south", ADULT_MALE_RAT_SPRITE_SOUTH);
		sprites.put("male-rat-west", ADULT_MALE_RAT_SPRITE_WEST);

		sprites.put("female-rat-north", ADULT_FEMALE_RAT_SPRITE_NORTH);
		sprites.put("female-rat-east", ADULT_FEMALE_RAT_SPRITE_EAST);
		sprites.put("female-rat-south", ADULT_FEMALE_RAT_SPRITE_SOUTH);
		sprites.put("female-rat-west", ADULT_FEMALE_RAT_SPRITE_WEST);

		sprites.put("female-pregnant-rat-north",
				ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH);
		sprites.put("female-pregnant-rat-east",
				ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST);
		sprites.put("female-pregnant-rat-south",
				ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH);
		sprites.put("female-pregnant-rat-west",
				ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST);

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

		sprites.put("bomb-delayed1",
				new Image("delayed_bomb_images/bomb1.png"));
		sprites.put("bomb-delayed2",
				new Image("delayed_bomb_images/bomb2.png"));
		sprites.put("bomb-delayed3",
				new Image("delayed_bomb_images/bomb3.png"));
		sprites.put("bomb-delayed4",
				new Image("delayed_bomb_images/bomb4.png"));

		sprites.put("explosion", new Image("Textures/explosion.png"));

		sprites.put("poison", new Image("/items/poisononlevel.png"));

		sprites.put("sprinkler", new Image("gas_images/Sprinkler.png"));
		sprites.put("sprinkler-broken",
				new Image("gas_images/SprinklerBroken.png"));
		sprites.put("sprinkler1", new Image("gas_images/Sprinkler1.png"));
		sprites.put("sprinkler2", new Image("gas_images/Sprinkler2.png"));
		sprites.put("sprinkler3", new Image("gas_images/Sprinkler3.png"));
		sprites.put("sprinkler4", new Image("gas_images/Sprinkler4.png"));
		sprites.put("sprinkler5", new Image("gas_images/Sprinkler5.png"));

		sprites.put("gas", new Image("Textures/gas.png"));

		sprites.put("death-rat-north",
				new Image("Textures/death-rat-north.png"));
		sprites.put("death-rat-east", new Image("Textures/death-rat-east.png"));
		sprites.put("death-rat-south",
				new Image("Textures/death-rat-south.png"));
		sprites.put("death-rat-west", new Image("Textures/death-rat-west.png"));

		sprites.put("no-entry1",
				new Image("noentrysign_images/noentrysign1.png"));
		sprites.put("no-entry2",
				new Image("noentrysign_images/noentrysign2.png"));
		sprites.put("no-entry3",
				new Image("noentrysign_images/noentrysign3.png"));
		sprites.put("no-entry4",
				new Image("noentrysign_images/noentrysign4.png"));
		sprites.put("no-entry5",
				new Image("noentrysign_images/noentrysign5.png"));

		sprites.put("sterilisation",
				new Image("/items/sterilisationonlevel.png"));
		sprites.put("tr", new Image("Textures/tr.png"));
		sprites.put("sterile-radius", new Image("Textures/sterile-radius.png"));

		sprites.put("male-sex-change",
				new Image("items/malesexchangeronlevel.png"));
		sprites.put("female-sex-change",
				new Image("items/femalesexchangeronlevel.png"));

	}

	public Image getImage(String image) {
		return sprites.get(image);
	}

}
