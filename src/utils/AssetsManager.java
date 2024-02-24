package utils;

import java.awt.image.BufferedImage;

import static utils.Constants.Fruit.FRUIT_ANIMATION_LENGTH;
import static utils.Constants.Fruit.FRUIT_ANIM_SIZE;
import static utils.Constants.Player.*;
import static utils.Constants.Player.PLAYER_ANIM_SIZE;
import static utils.Constants.UI.*;
import static utils.Constants.UI.Text.*;
import static utils.LoadSave.loadImage;

public class AssetsManager {
    private final String PLAYER_CHARACTER = "MaskDude";
    final String[] PLAYER_ANIM_IMG_PATH = {
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/Idle.png", "assets/MainCharacters/" + PLAYER_CHARACTER + "/Hit.png",
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/Run.png", "assets/MainCharacters/" + PLAYER_CHARACTER + "/Jump.png",
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/Fall.png", "assets/MainCharacters/" + PLAYER_CHARACTER + "/DoubleJump.png",
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/WallJump.png", "assets/MainCharacters/Appear.png",
            "assets/MainCharacters/Disappear.png" };
    private BufferedImage[][] playerAnimations;


    private String APPLE_IMG_PATH = "assets/Items/Fruits/Apple.png";
    private BufferedImage[] appleAnimations;

    private final String BANNER_IMG_PATH = "assets/Menu/Banner.png";
    private BufferedImage bannerImg;

    public static final String PLAY_BUTTON_PATH = "assets/Buttons/PlayButton.png";
    public static final String EDIT_BUTTON_PATH =  "assets/Buttons/EditButton.png";
    public static final String EXIT_BUTTON_PATH =  "assets/Buttons/ExitButton.png";
    public static final String RESTART_BUTTON_PATH =  "assets/Buttons/RestartButton.png";
    private BufferedImage[] playButtonAnimations;
    private BufferedImage[] editButtonAnimations;
    private BufferedImage[] exitButtonAnimations;
    private BufferedImage[] restartButtonAnimations;


    public static String TEXT_IMG_BLACK = "assets/Menu/Text/Text (Black) (8x10).png";
    public static String TEXT_IMG_WHITE = "assets/Menu/Text/Text (White) (8x10).png";
    private BufferedImage[] blackText;
    private BufferedImage[] whiteText;


    public AssetsManager() {
        loadPlayerAnimations();
        loadAppleAnimations();
        loadBannerImg();
        loadButtonAnimations();
        loadTexts();
    }

    public BufferedImage[][] getPlayerAnimations() {
        return playerAnimations;
    }

    private void loadPlayerAnimations() {
        BufferedImage[] img = loadPlayerImages();
        playerAnimations = new BufferedImage[PLAYER_ANIM_COUNT][PLAYER_ANIM_MAX_FRAMES];

        for (int i = 0; i < img.length; i++)
        {
            for (int j = 0; j < PLAYER_ANIM_LENGTH[i]; j++)
            {
                if (i >= PLAYER_APPEAR) {
                    playerAnimations[i][j] =  img[i].getSubimage(j * PLAYER_ANIM_APPEAR_SIZE, 0 , PLAYER_ANIM_APPEAR_SIZE, PLAYER_ANIM_APPEAR_SIZE);
                }
                else {
                    playerAnimations[i][j] =  img[i].getSubimage(j * PLAYER_ANIM_SIZE, 0 , PLAYER_ANIM_SIZE, PLAYER_ANIM_SIZE);
                }
            }
        }
    }

    private BufferedImage[] loadPlayerImages() {
        BufferedImage[] img = new BufferedImage[PLAYER_ANIM_IMG_PATH.length];

        for (int i = 0; i < PLAYER_ANIM_IMG_PATH.length; i++) {
            img[i] = loadImage(PLAYER_ANIM_IMG_PATH[i]);
        }

        return img;
    }

    private void loadAppleAnimations() {
        BufferedImage img = loadImage(APPLE_IMG_PATH);
        appleAnimations = new BufferedImage[FRUIT_ANIMATION_LENGTH];
        for (int i = 0; i < FRUIT_ANIMATION_LENGTH; i++)
        {
            appleAnimations[i] =  img.getSubimage(i * FRUIT_ANIM_SIZE, 0 , FRUIT_ANIM_SIZE, FRUIT_ANIM_SIZE);
        }

    }

    private void loadBannerImg() {
        bannerImg = loadImage(BANNER_IMG_PATH);
    }

    private void loadButtonAnimations() {
        playButtonAnimations =  loadButtonAnimations(PLAY_BUTTON_PATH);
        editButtonAnimations =  loadButtonAnimations(EDIT_BUTTON_PATH);
        exitButtonAnimations =  loadButtonAnimations(EXIT_BUTTON_PATH);
        restartButtonAnimations =  loadButtonAnimations(RESTART_BUTTON_PATH);
    }

    private BufferedImage[] loadButtonAnimations(String path) {
        BufferedImage img = loadImage(path);
        BufferedImage[] buttonAnimations = new BufferedImage[BUTTON_ANIM_LENGTH];

        for (int i = 0; i < BUTTON_ANIM_LENGTH; i++)
        {
            buttonAnimations[i]=  img.getSubimage(i * BUTTON_ANIM_WIDTH, 0 , BUTTON_ANIM_WIDTH, BUTTON_ANIM_HEIGHT);
        }
        return buttonAnimations;
    }

    public BufferedImage[] getAppleAnimations() {
        return appleAnimations;
    }

    public BufferedImage getBannerImg() {
        return bannerImg;
    }

    public BufferedImage[] getPlayButtonAnimations() {
        return playButtonAnimations;
    }
    public BufferedImage[] getRestartButtonAnimations() {
        return restartButtonAnimations;
    }
    public BufferedImage[] getEditButtonAnimations() {
        return editButtonAnimations;
    }
    public BufferedImage[] getExitButtonAnimations() {
        return exitButtonAnimations;
    }

    private void loadTexts() {
        blackText = loadText(TEXT_IMG_BLACK);
        whiteText = loadText(TEXT_IMG_WHITE);
    }

    private BufferedImage[] loadText(String path) {
        BufferedImage img = loadImage(path);
        BufferedImage[] symbols = new BufferedImage[SYMBOL_COUNT];
        int index = 0;
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                if (row == 2 && col == 7 || row == 4 && col == 9) {
                    break;
                }
                symbols[index] = img.getSubimage(col * WIDTH,row * HEIGHT, WIDTH, HEIGHT);
                index++;
            }
        }
        return symbols;
    }

    public BufferedImage[] getWhiteText() {
        return whiteText;
    }

    public BufferedImage[] getBlackText() {
        return blackText;
    }



}
