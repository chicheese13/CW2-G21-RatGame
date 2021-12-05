
public class FemaleSexChange extends CollideItem {
	
	


	public FemaleSexChange(Position objectPosition, Level currentLevel) {
		this.renderSprite = "female-sex-change";
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		this.currentLevel.spawnSound("placeItem");
	}

//	public void ratToF(rat)

	public void ratToF(NormalRat collidedObject) {
		//runs rat gender change method in rat classes
		collidedObject.changeToFemale();
	}
	
	public void tick() {
		
	}
	
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			ratToF((NormalRat) collidedObject);
			this.currentLevel.spawnSound("FemaleSexChange");
			this.currentLevel.despawnItem(this);
		}
	}
}
