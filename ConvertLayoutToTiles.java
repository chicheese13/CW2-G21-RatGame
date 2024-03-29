import java.util.ArrayList;
import java.math.*;

/**
 * @version 1.0
 * @author Dylan Lewis
 * 
 *  ConverLayoutToTiles class, which decodes string input and converts it into tiles
 *
 */
public class ConvertLayoutToTiles {

	/**
	 * Creating arrays for the tiles and afterTiles. 
	 * The afterTiles are second layer and are covering whatsever lays below them.
	 */
	private RenderTile[][] tiles;

	private ArrayList<RenderTile> afterTiles = new ArrayList<>();
	private ArrayList<Position> afterTilesPositon = new ArrayList<>();

	/**
	 * Constructor, which initiates converting string to tiles
	 * 
	 * @param tiles
	 */
	public ConvertLayoutToTiles(char[][] tiles) {
		this.tiles = convertTiles(tiles);
	}

	public RenderTile[][] getTiles() {
		return this.tiles;
	}

	/**
	 * Method which systematically appends new strings based on tile directions
	 * accessibility to decode neccesary tile sprite. 
	 * It constructs the final lookout of the level
	 * 
	 * @param tileArray
	 * @return
	 */
	public RenderTile[][] convertTiles(char[][] tileArray) {
		RenderTile[][] returnArray = new RenderTile[tileArray.length][tileArray[0].length];
		// probably organise the images into groups, top, left, bottom, right.
		// presume that G = Grass, P = Path and T = Tunnel.

		for (int y = 0; y < tileArray.length; y++) {
			for (int x = 0; x < tileArray[y].length; x++) {
				String fetchString;
				System.out.println("x " + x);
				System.out.println("y " + y);
				boolean isBottom = false;
				boolean isTop = false;
				boolean isLeft = false;
				boolean isRight = false;
				boolean isPath = false;
				boolean isGrass = false;
				boolean isTunnel = false;
				// check top
				// check left
				// check right
				// check bottom

				if (tileArray[y][x] == 'T') {
					isTunnel = true;
				}

				System.out.println("TESTING");
				System.out.println(tileArray[y][x]);
				if (tileArray[y][x] == 'G') {
					isGrass = true;
					System.out.println("GRASSSSSS");
				}

				if (tileArray[y][x] == 'P') {
					isPath = true;
				}

				// checks if the current tile is tunnel or Path, if so we can
				// check the surrounding tiles.
				if (tileArray[y][x] == 'P') {
					// check top
					if (y > 1 && (tileArray[y - 1][x] == 'T' || tileArray[y - 1][x] == 'P')) {
						isTop = true;
					}
					// check left
					if (x > 0 && (tileArray[y][x - 1] == 'T' || tileArray[y][x - 1] == 'P')) {
						isLeft = true;
					}
					// check right
					if (x < tileArray[y].length - 1 && (tileArray[y][x + 1] == 'T' || tileArray[y][x + 1] == 'P')) {

						isRight = true;
					}

					if (y < tileArray.length - 1 && (tileArray[y + 1][x] == 'T' || tileArray[y + 1][x] == 'P')) {
						isBottom = true;
					}
				}

				// tunnels have special rendering.
				if (tileArray[y][x] == 'T') {
					// check the top, left and right to see if its tunnel.
					if (y > 1 && (tileArray[y - 1][x] == 'T')) {
						isTop = true;
					}
					// check left
					if (x > 0 && (tileArray[y][x - 1] == 'T')) {
						isLeft = true;
					}
					// check right
					if (x < tileArray[y].length - 1 && (tileArray[y][x + 1] == 'T')) {
						isRight = true;
					}
					if (y < tileArray.length - 1 && (tileArray[y + 1][x] == 'T')) {
						isBottom = true;
					}
				}

				// get the tile.
				if (isGrass) {
					returnArray[y][x] = new RenderTile("Textures/grass.png");
				} else {
					fetchString = "Textures/";
					String left = "left";
					String right = "right";
					String top = "top";
					String bottom = "bottom";

					// top, bottom, left, right, .png
					if (isTop) {
						fetchString = fetchString + top;
					}

					if (isBottom) {
						fetchString = fetchString + bottom;
					}

					if (isLeft) {
						fetchString = fetchString + left;
					}

					if (isRight) {
						fetchString = fetchString + right;
					}

					if (isTunnel) {
						fetchString = fetchString + "tunnel";
						afterTiles.add(new RenderTile(fetchString + "tr.png"));
						afterTilesPositon.add(new Position(new BigDecimal(x), new BigDecimal(y)));
					}

					fetchString = fetchString + ".png";
					System.out.println(fetchString);
					returnArray[y][x] = new RenderTile(fetchString);
				}
			}
		}
		return returnArray;
	}

	public ArrayList<RenderTile> getAfterList() {
		return this.afterTiles;
	}

	public ArrayList<Position> getAfterPositionList() {
		return this.afterTilesPositon;
	}
}
