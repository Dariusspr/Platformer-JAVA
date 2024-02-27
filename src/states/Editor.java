package states;

import UI.EditorUI;
import levels.LevelManager;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import static utils.Constants.Fruit.FRUIT_ANIM_SIZE;
import static utils.Constants.Game.*;
import static utils.Constants.UI.Editor.*;


public class Editor extends State implements StateHandler{

    private Level currentLevel;

    private LevelManager levelManager;
    private EditorUI editorUI;


    private ElementType currentElementType = ElementType.TERRAIN;
    private int currentElementValue = 0;

    public Editor(Game game, LevelManager levelManager) {
        super(game);
        this.levelManager = levelManager;
        editorUI = new EditorUI(this);
        currentLevel = this.levelManager.getCurrentLevel();
    }
    @Override
    public void render(Graphics g) {
        game.getIngame().render(g, 0);
        editorUI.render(g);
    }

    public void resetEditor() {
        setCurrentLevel();
        currentElementValue = 0;
        editorUI.resetEditorUI();
    }

    @Override
    public void update() {
        levelManager.update();
        editorUI.update();
        currentElementValue = editorUI.getCurrentElementValue();
        currentElementType = editorUI.getCurrentElementType();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!editorUI.isInMenu()) {
            if (mouseY > editorUI.getOffsetY()) {
                editorUI.handleMouseClick(mouseX, mouseY);
                return;
            }

            // Delete tile
        if (SwingUtilities.isRightMouseButton(e)) {
            if (mouseY < editorUI.getOffsetY() && mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH) {
                // Delete fruit
                game.getIngame().getLevelManager().deleteFruit((int) (mouseX / LEVEL_SCALE) , (int) (mouseY / LEVEL_SCALE));
                // Delete terrain
                currentElementType = ElementType.TERRAIN;
                currentElementValue = EDITOR_ERASE_VALUE;
                modifyTerrain(mouseX, mouseY);
            }
        }

            // Place tile
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH){
                switch (currentElementType) {
                    case TERRAIN -> {
                        modifyTerrain(mouseX, mouseY);
                    }
                    case FRUIT -> {
                        game.getIngame().getLevelManager().createFruit((int) (mouseX / LEVEL_SCALE - FRUIT_ANIM_SIZE / 2 / LEVEL_SCALE),
                                                                        (int) (mouseY / LEVEL_SCALE - FRUIT_ANIM_SIZE / 2 / LEVEL_SCALE));
                    }
                    case PLAYER -> {
                        // change player pos
                        game.getIngame().getLevelManager().getCurrentLevel().setPlayerPos((int) (mouseX / LEVEL_SCALE), (int) (mouseY / LEVEL_SCALE));
                        game.getIngame().getPlayer().reset();
                    }
                }
            }
        }

        }
        else if (editorUI.onExit){
            GameState.setState(GameState.MENU);
            levelManager.saveFruitData();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (editorUI.isInMenu()) {
            editorUI.onExit = editorUI.exitButton.onButton(e.getX(), e.getY());
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!editorUI.isInMenu()) {
            if (mouseY > editorUI.getOffsetY()) {
                editorUI.handleMouseClick(mouseX, mouseY);
                return;
            }

            // Delete tile
            if (SwingUtilities.isRightMouseButton(e)) {
                if (mouseY < editorUI.getOffsetY() && mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH) {

                    // Delete fruit
                    game.getIngame().getLevelManager().deleteFruit((int) (mouseX / LEVEL_SCALE) , (int) (mouseY / LEVEL_SCALE));

                    // Delete terrain
                    currentElementType = ElementType.TERRAIN;
                    currentElementValue = EDITOR_ERASE_VALUE;
                    modifyTerrain(mouseX, mouseY);
                }
            }

            // Place tile
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH){
                    switch (currentElementType) {
                        case TERRAIN -> {
                            modifyTerrain(mouseX, mouseY);
                        }
                        case FRUIT -> {
                            game.getIngame().getLevelManager().createFruit((int) (mouseX / LEVEL_SCALE - FRUIT_ANIM_SIZE / 2 / LEVEL_SCALE),
                                    (int) (mouseY / LEVEL_SCALE - FRUIT_ANIM_SIZE / 2 / LEVEL_SCALE));
                        }
                        case PLAYER -> {
                            // change player pos
                            game.getIngame().getLevelManager().getCurrentLevel().setPlayerPos((int) (mouseX / LEVEL_SCALE), (int) (mouseY / LEVEL_SCALE));
                            game.getIngame().getPlayer().reset();
                        }
                    }
                }
            }

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F1:
                GameState.setState(GameState.INGAME);
                game.getIngame().setLastTimeCheck();
                levelManager.getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
                levelManager.saveFruitData();
                break;
            case KeyEvent.VK_ESCAPE:
                if (editorUI.isInMenu()) {
                    editorUI.setInMenu(false);
                }
                else {
                    editorUI.setInMenu(true);
                }
                break;
        }
    }

    private void modifyTerrain(int mouseX, int mouseY) {
        // Delete terrain tile
        int[][] data = currentLevel.getTerrainData();
        int col = (int) (mouseX / (TILE_SIZE * LEVEL_SCALE));
        int row = (int) (mouseY / (TILE_SIZE * LEVEL_SCALE));
        data[row][col] = currentElementValue;
    }

    private void setCurrentLevel() {
        currentLevel = game.getIngame().getLevelManager().getCurrentLevel();
    }
}
