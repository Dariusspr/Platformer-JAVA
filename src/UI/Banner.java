package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Load.loadImage;

public class Banner {
    private final String BANNER_IMG = "assets/Menu/Banner.png";
    private BufferedImage bannerImg;
    private int x, y;
    private int width, height;
    String text;
    public Banner(String text, int x, int y, int width, int height) {
        this.x =x;
        this.y = y;
        this.text = text;
        this.width = width;
        this.height = height;

        bannerImg = loadImage(BANNER_IMG);
    }

    public void render(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);

        g.drawImage(bannerImg, x, y, width, height, null);
        g.drawString(text, x + width / 2 - 50, y + height / 2); // TODO: text from img alpha
    }
}
