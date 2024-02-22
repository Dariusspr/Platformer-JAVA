package objects;


public class Apple extends Fruit{
    private String APPLE_IMG = "assets/Items/Fruits/Apple.png";

    public Apple(float x, float y) {
        super(x, y);
    }

    public void loadAnimations() {
        loadAnimations(APPLE_IMG);
    }
}
