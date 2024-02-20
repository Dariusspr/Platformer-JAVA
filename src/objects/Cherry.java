package objects;

import static utils.Constants.Fruit.CHERRY_POINTS;

public class Cherry extends Fruit{
    private String CHERRY_IMG = "assets/Items/Fruits/Cherries.png";

    public Cherry(float x, float y) {
        super(x, y, CHERRY_POINTS);
    }

    public void loadAnimations() {
        loadAnimations(CHERRY_IMG);
    }
}
