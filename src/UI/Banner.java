package UI;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.LoadSave.loadImage;

public class Banner {
    private BufferedImage bannerImg;
    private int x, y;
    private int width, height;
    Text text;
    public Banner(String rawText, int x, int y, int width, int height, BufferedImage bannerImg, Game game) {
        this.x =x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.bannerImg = bannerImg;
        text = new Text(rawText, (int) (width * 0.05f), (int) (x + 0.5f * width), y + height / 2, game.getAssetsManager().getBlackText());
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
    }
}
