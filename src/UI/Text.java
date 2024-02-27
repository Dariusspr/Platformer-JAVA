package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * For rendering appealing text font on the screen
 */
public class Text {
    private String text;
    private final int size;
    private final BufferedImage[] symbols;
    private BufferedImage[] textImg;
    int x, y;

    /**
     * Initializes a Text object with the given parameters.
     * @param text The text to display.
     * @param size The size of each symbol.
     * @param x The x-coordinate of the text.
     * @param y The y-coordinate of the text.
     * @param symbols An array of BufferedImage containing symbols for the text.
     */
    public Text(String text, int size, int x, int y, BufferedImage[] symbols) {
        this.text = text.toLowerCase();
        this.size = size;
        this.x = x;
        this.y = y;
        this.symbols = symbols;
        convertTextToImg();
    }

    /**
     * Change the text to be displayed.
     * @param text The new text.
     */
    public void changeText(String text) {
        this.text = text.toLowerCase();
        convertTextToImg();
    }

    /**
     * Converts the text into images using the provided symbol images.
     */
    private void convertTextToImg() {
        textImg = new BufferedImage[text.length()];
        for (int i = 0; i < textImg.length; i++) {
            textImg[i] = getSymbol(text.charAt(i));
        }
    }

    /**
     * Retrieves the symbol image for a given character.
     * @param c The character.
     * @return The BufferedImage representing the symbol.
     */
    private BufferedImage getSymbol(char c) {
        if (Character.isAlphabetic(c)) {
            return symbols[c-'a'];
        }
        else if (Character.isDigit(c)) {
            return symbols[27 + c-'0'];
        }
        else {
            return switch (c) {
                case '.' -> symbols[37];
                case ',' -> symbols[38];
                case ':' -> symbols[39];
                case '?' -> symbols[40];
                case '!' -> symbols[41];
                case '(' -> symbols[42];
                case ')' -> symbols[43];
                case '+' -> symbols[44];
                case '-' -> symbols[45];
                default -> symbols[26];
            };
        }
    }

    /**
     * Renders the text
     * @param g The Graphics object.
     */
    public void render(Graphics g) {
        int offset = textImg.length / 2 * size;
        for (int i = 0; i < textImg.length; i++) {
            g.drawImage(textImg[i], x + i * size - offset, y, size, size, null);
        }
    }

}
