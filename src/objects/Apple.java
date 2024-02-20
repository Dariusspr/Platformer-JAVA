package objects;
import static utils.Constants.Fruit.*;


public class Apple extends Fruit{
    private String APPLE_IMG = "assets/Items/Fruits/Apple.png";

    public Apple(float x, float y) {
        super(x, y, APPLE_POINTS);
    }

    public void loadAnimations() {
        loadAnimations(APPLE_IMG);
    }
}
