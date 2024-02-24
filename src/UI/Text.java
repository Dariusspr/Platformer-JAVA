package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.LoadSave.loadImage;
import  static utils.Constants.UI.Text.*;

public class Text {
    private String text;
    private int size;
    private BufferedImage[] symbols;
    private BufferedImage[] textImg;
    int x, y;

    public Text(String text, int size, int x, int y, BufferedImage[] symbols) {
        this.text = text.toLowerCase();
        this.size = size;
        this.x = x;
        this.y = y;
        this.symbols = symbols;
        convertTextToImg();
    }

    public void updateText(String text) {
        this.text = text.toLowerCase();
        convertTextToImg();
    }

    private void convertTextToImg() {
        textImg = new BufferedImage[text.length()];
        for (int i = 0; i < textImg.length; i++) {
            textImg[i] = getSymbol(text.charAt(i));
        }
    }

    private BufferedImage getSymbol(char c) {
        if (Character.isAlphabetic(c)) {
            return symbols[c-'a'];
        }
        else if (Character.isDigit(c)) {
            return symbols[27 + c-'0'];
        }
        else {
            switch (c) {
                case '.':
                    return symbols[37];
                case ',':
                    return symbols[38];
                case ':':
                    return symbols[39];
                case '?':
                    return symbols[40];
                case '!':
                    return symbols[41];
                case '(':
                    return symbols[42];
                case ')':
                    return symbols[43];
                case '+':
                    return symbols[44];
                case '-':
                    return symbols[45];
                default:
                    return symbols[26];
            }
        }
    }

    public void render(Graphics g) {
        int offset = textImg.length / 2 * size;
        for (int i = 0; i < textImg.length; i++) {
            g.drawImage(textImg[i], x + i * size - offset, y, size, size, null);
        }
    }

}
