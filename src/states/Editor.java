package states;

import UI.EditorUI;
import levels.LevelManager;
import levels.Level;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

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

        currentElementValue = 0;
        editorUI.resetEditorUI();
    }

    @Override
    public void update() {
        levelManager.update();
        game.getIngame().getPlayer().updatePosition();
        editorUI.update();
        setCurrentLevel();
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
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (mouseY > editorUI.getOffsetY()) {
                    editorUI.handleMouseClick(mouseX, mouseY);
                }
                else if (mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH){
                    modifyCurrentLevel(mouseX, mouseY);
                }
            }
            else if (SwingUtilities.isRightMouseButton(e)){
                if (mouseY < editorUI.getOffsetY() && mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH) {
                    currentElementType = ElementType.TERRAIN;
                    currentElementValue = EDITOR_ERASE_VALUE;
                    modifyCurrentLevel(mouseX, mouseY);
                }
            }
        }
        else if (editorUI.onExit){
            GameState.setState(GameState.MENU);
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
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (mouseY > editorUI.getOffsetY()) {
                editorUI.handleMouseClick(mouseX, mouseY);
            } else if (mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH){
                modifyCurrentLevel(mouseX, mouseY);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (mouseY < editorUI.getOffsetY() && mouseY >= 0 && mouseX >= 0 && mouseX < PANEL_WIDTH) {
                currentElementType = ElementType.TERRAIN;
                currentElementValue = EDITOR_ERASE_VALUE;
                modifyCurrentLevel(mouseX, mouseY);
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
                levelManager.getCurrentLevel().setLevelState(Level.LevelState.OTHER);
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

    private void modifyCurrentLevel(int mouseX, int mouseY) {
        int[][] data = currentLevel.getTerrainData();

        int col = (int) (mouseX / (TILE_SIZE * LEVEL_SCALE));
        int row = (int) (mouseY / (TILE_SIZE * LEVEL_SCALE));
        data[row][col] = currentElementValue;
    }

    private void setCurrentLevel() {
        currentLevel = game.getIngame().getLevelHandler().getCurrentLevel();
    }
}
