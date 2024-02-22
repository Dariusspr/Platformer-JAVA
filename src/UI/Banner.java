package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Load.loadImage;

public class Banner {
    private final String BANNER_IMG = "assets/Menu/Banner.png";
    private BufferedImage bannerImg;
    private int x, y;
    private int width, height;
    Text text;
    public Banner(String rawText, int x, int y, int width, int height) {
        this.x =x;
        this.y = y;
        this.width = width;
        this.height = height;

        bannerImg = loadImage(BANNER_IMG);
        text = new Text(rawText, (int) (width * 0.05f), (int) (x + 0.5f * width), y + height / 2, 'b');
    }

    public void changeBannerText(String text) {
        this.text.updateText(text);
    }

    public void render(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);

        g.drawImage(bannerImg, x, y, width, height, null);
        text.render(g);
        //g.drawString(rawText, x + width / 2 - 50, y + height / 2); // TODO: text from img alpha
    }
}
