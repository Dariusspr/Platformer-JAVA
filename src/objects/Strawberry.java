package objects;

import static utils.Constants.Fruit.STRAWBERRY_POINTS;

public class Strawberry extends  Fruit{
    private String STRAWBERRY_IMG = "assets/Items/Fruits/Strawberry.png";

    public Strawberry(float x, float y) {
        super(x, y, STRAWBERRY_POINTS);
    }

    public void loadAnimations() {
        loadAnimations(STRAWBERRY_IMG);
    }
}
