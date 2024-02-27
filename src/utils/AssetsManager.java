package utils;

import java.awt.image.BufferedImage;

import static utils.Constants.Fruit.FRUIT_ANIMATION_LENGTH;
import static utils.Constants.Fruit.FRUIT_ANIM_SIZE;
import static utils.Constants.Game.TILE_INIT_SIZE;
import static utils.Constants.LevelManager.TERRAIN_HEIGHT;
import static utils.Constants.LevelManager.TERRAIN_WIDTH;
import static utils.Constants.Player.*;
import static utils.Constants.Player.PLAYER_ANIM_SIZE;
import static utils.Constants.UI.*;
import static utils.Constants.UI.Editor.SELECTOR_FRAME_COUNT;
import static utils.Constants.UI.Editor.SELECTOR_INIT_SIZE;
import static utils.Constants.UI.Text.*;
import static utils.LoadSave.loadImage;

public class AssetsManager {
    private final String PLAYER_CHARACTER = "MaskDude";
    private final String[] PLAYER_ANIM_IMG_PATH = {
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/Idle.png", "assets/MainCharacters/" + PLAYER_CHARACTER + "/Hit.png",
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/Run.png", "assets/MainCharacters/" + PLAYER_CHARACTER + "/Jump.png",
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/Fall.png", "assets/MainCharacters/" + PLAYER_CHARACTER + "/DoubleJump.png",
            "assets/MainCharacters/" + PLAYER_CHARACTER + "/WallJump.png", "assets/MainCharacters/Appear.png",
            "assets/MainCharacters/Disappear.png" };
    private final String APPLE_IMG_PATH = "assets/Items/Fruits/Apple.png";
    private final String BANNER_IMG_PATH = "assets/Menu/Banner.png";
    private final String PLAY_BUTTON_PATH = "assets/Buttons/PlayButton.png";
    private final String EDIT_BUTTON_PATH =  "assets/Buttons/EditButton.png";
    private final String EXIT_BUTTON_PATH =  "assets/Buttons/ExitButton.png";
    private final String RESTART_BUTTON_PATH =  "assets/Buttons/RestartButton.png";
    private final String TEXT_IMG_BLACK = "assets/Menu/Text/Text (Black) (8x10).png";
    private final String TEXT_IMG_WHITE = "assets/Menu/Text/Text (White) (8x10).png";
    private final String GRID_IMG_PATH = "assets/Background/grid.png";
    private final String TERRAIN_IMG_PATH = "assets/Terrain/Terrain.png";
    private final String SELECTOR_IMG_PATH = "assets/Menu/Selector.png";
    private String BACKGROUND_IMG_PATH = "assets/Background/background_blue.png";

    private BufferedImage playerImg;
    private BufferedImage[][] playerAnimations;
    private BufferedImage appleImg;
    private BufferedImage[] appleAnimations;
    private BufferedImage bannerImg;
    private BufferedImage[] playButtonAnimations;
    private BufferedImage[] editButtonAnimations;
    private BufferedImage[] exitButtonAnimations;
    private BufferedImage[] restartButtonAnimations;
    private BufferedImage[] blackText;
    private BufferedImage[] whiteText;
    private BufferedImage gridImg;
    private BufferedImage terrainImg;
    private BufferedImage[] terrainArray;
    private BufferedImage[] selectorAnimations;
    private BufferedImage backgroundImg;

    public AssetsManager() {
        loadBannerImg();
        loadTexts();
        loadBackground();
        loadGrid();
        loadTerrain();

        loadAppleAnimations();
        loadPlayerAnimations();
        loadButtonAnimations();
        loadSelectorAnimations();
    }

    private void loadGrid() {
        gridImg = loadImage(GRID_IMG_PATH);
    }

    private void loadBackground() {
        backgroundImg = loadImage(BACKGROUND_IMG_PATH);
    }

    private void loadTerrain() {
        terrainImg = loadImage(TERRAIN_IMG_PATH);

        terrainArray = new BufferedImage[TERRAIN_HEIGHT * TERRAIN_WIDTH];
        for (int row = 0; row < TERRAIN_HEIGHT; row++) {
            for (int col = 0; col < TERRAIN_WIDTH; col++) {
                terrainArray[row * TERRAIN_WIDTH + col] = terrainImg.getSubimage(col * TILE_INIT_SIZE, row * TILE_INIT_SIZE, TILE_INIT_SIZE, TILE_INIT_SIZE);
            }
        }
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
        playerImg = playerAnimations[0][0];
    }

    private void loadSelectorAnimations() {

        BufferedImage img = loadImage(SELECTOR_IMG_PATH);
        selectorAnimations = new BufferedImage[SELECTOR_FRAME_COUNT];

        for (int i = 0; i < SELECTOR_FRAME_COUNT; i++) {
            selectorAnimations[i] = img.getSubimage(SELECTOR_INIT_SIZE * i, 0, SELECTOR_INIT_SIZE, SELECTOR_INIT_SIZE);
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
        appleImg = appleAnimations[0];
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

    public BufferedImage[][] getPlayerAnimations() {
        return playerAnimations;
    }
    public BufferedImage getPlayerImg() {
        return playerImg;
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
    public BufferedImage[] getAppleAnimations() {
        return appleAnimations;
    }
    public BufferedImage getBannerImg() {
        return bannerImg;
    }
    public BufferedImage[] getWhiteText() {
        return whiteText;
    }
    public BufferedImage[] getBlackText() {
        return blackText;
    }
    public BufferedImage getTerrainImg() {
        return terrainImg;
    }
    public BufferedImage[] getTerrainArray() {
        return terrainArray;
    }
    public BufferedImage getBackgroundImg() {
        return backgroundImg;
    }
    public BufferedImage getGridImg() {
        return gridImg;
    }
    public BufferedImage[] getSelectorAnimations() {
        return selectorAnimations;
    }
    public BufferedImage getAppleImg() {
        return appleImg;
    }

}
