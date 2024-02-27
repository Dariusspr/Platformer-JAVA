package UI;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Banner class represents a banner with text.
 */
public class Banner {
    private final BufferedImage bannerImg;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    Text text;
    /**
     * Constructs a banner
     * @param rawText The raw text to be displayed on the banner.
     * @param x The x-coordinate of the banner.
     * @param y The y-coordinate of the banner.
     * @param width The width of the banner.
     * @param height The height of the banner.
     * @param bannerImg The image of the banner.
     * @param game The Game object.
     */
    public Banner(String rawText, int x, int y, int width, int height, BufferedImage bannerImg, Game game) {
        this.x =x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.bannerImg = bannerImg;
        text = new Text(rawText, (int) (width * 0.05f), (int) (x + 0.5f * width), y + height / 2, game.getAssetsManager().getBlackText());
    }

    /**
     * Changes the text displayed on the banner.
     * @param text The new text to be displayed.
     */
    public void changeBannerText(String text) {
        this.text.changeText(text);
    }

    /**
     * Renders the banner.
     * @param g The Graphics object.
     */
    public void render(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);

        g.drawImage(bannerImg, x, y, width, height, null);
        text.render(g);
    }
}
