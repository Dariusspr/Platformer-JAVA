package objects;

import static utils.Constants.Fruit.BANANA_POINTS;

public class Banana extends Fruit{

        private String BANANA_IMG = "assets/Items/Fruits/Bananas.png";

        public Banana(float x, float y) {
            super(x, y, BANANA_POINTS);
        }

        public void loadAnimations() {
            loadAnimations(BANANA_IMG);
        }
}
