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

/**
 * The Editor class represents the state where the user can edit levels.
 * It allows the user to modify terrain, add or remove fruits, and set the player's position.
 */
public class Editor extends State implements StateHandler{

    private Level currentLevel;

    private final LevelManager levelManager;
    private final EditorUI editorUI;


    private ElementType currentElementType = ElementType.TERRAIN;
    private int currentElementValue = 0;
    /**
     * Constructs an Editor object
     * @param game The game object.
     * @param levelManager The level manager object.
     */
    public Editor(Game game, LevelManager levelManager) {
        super(game);
        this.levelManager = levelManager;
        editorUI = new EditorUI(this);
        currentLevel = this.levelManager.getCurrentLevel();
    }

    /**
     * Resets the editor to its initial state.
     */
    public void resetEditor() {
        setCurrentLevel();
        currentElementValue = 0;
        editorUI.resetEditorUI();
    }

    /**
     * Modifies the terrain at the specified mouse coordinates.
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     */
    private void modifyTerrain(int mouseX, int mouseY) {
        int[][] data = currentLevel.getTerrainData();
        int col = (int) (mouseX / (TILE_SIZE * LEVEL_SCALE));
        int row = (int) (mouseY / (TILE_SIZE * LEVEL_SCALE));
        data[row][col] = currentElementValue;
    }

    /**
     * Sets the current level(the one that is being edited) to the currently active level in the game.
     */
    private void setCurrentLevel() {
        currentLevel = game.getIngame().getLevelManager().getCurrentLevel();
    }


    /**
     * Renders ui and level that is being edited
     * @param g The graphics context.
     */
    @Override
    public void render(Graphics g) {
        game.getIngame().render(g, 0);
        editorUI.render(g);
    }

    /**
     * Updates level that is being edited, editor ui and selected element
     */
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

    /**
     * Handles mouse input during level editing.
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     * @param e The mouse event.
     */
    public void handleLevelEditingInput(int mouseX, int mouseY, MouseEvent e) {
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
                    case TERRAIN:
                        modifyTerrain(mouseX, mouseY);
                        break;
                    case FRUIT:
                        game.getIngame().getLevelManager().createFruit((int) (mouseX / LEVEL_SCALE - FRUIT_ANIM_SIZE / 2 / LEVEL_SCALE),
                                (int) (mouseY / LEVEL_SCALE - FRUIT_ANIM_SIZE / 2 / LEVEL_SCALE));
                        break;
                    case PLAYER:
                        // change player pos
                        game.getIngame().getLevelManager().getCurrentLevel().setPlayerPos((int) (mouseX / LEVEL_SCALE), (int) (mouseY / LEVEL_SCALE));
                        game.getIngame().getPlayer().reset();
                        break;
                }
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!editorUI.isInMenu()) {
            handleLevelEditingInput(mouseX, mouseY, e);
        }
        else if (editorUI.onExit){
            GameState.setState(GameState.MENU);
            levelManager.saveFruitData();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!editorUI.isInMenu()) {
            handleLevelEditingInput(mouseX, mouseY, e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (editorUI.isInMenu()) {
            editorUI.onExit = editorUI.exitButton.onButton(e.getX(), e.getY());
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
                levelManager.getCurrentLevel().setLevelState(Level.LevelState.PLAYING);
                levelManager.saveFruitData();
                break;
            case KeyEvent.VK_ESCAPE:
                editorUI.setInMenu(!editorUI.isInMenu());
                break;
        }
    }
}
