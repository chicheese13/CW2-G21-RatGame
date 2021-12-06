
public class Explosion extends RenderObject {

    private int tickCounter;
    private int TICK_DURATION = 15;

    public Explosion(Position position, Level currentLevel) {
        this.renderSprite = "explosion";
        this.tickCounter = 0;
        this.objectPosition = position;
        this.currentLevel = currentLevel;
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        this.tickCounter++;
        if (this.tickCounter == TICK_DURATION) {
            System.out.println("TEST");
            this.currentLevel.despawnTempTile(this);
        }
    }

    public void collision(Object collidedObject) {
        ((RenderObject) collidedObject).collision(this);
    }

}
