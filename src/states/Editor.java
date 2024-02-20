package states;

import UI.EditorUI;
import levels.LevelHandler;
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

    private LevelHandler levelHandler;
    private EditorUI editorUI;

    private int currentElementType = 0;
    private int currentElementValue = 0;

    public Editor(Game game, LevelHandler levelHandler) {
        super(game);
        this.levelHandler = levelHandler;
        editorUI = new EditorUI();
        currentLevel = this.levelHandler.getCurrentLevel();
    }
    @Override
    public void render(Graphics g) {
        game.getIngame().renderCustomOffset(g, 0);
       editorUI.render(g);
    }


    @Override
    public void update() {
        levelHandler.update();
        game.getIngame().getPlayer().updatePosition();
        editorUI.update();
        updateCurrentLevel();
        currentElementValue = editorUI.getCurrentElementValue();
        currentElementType = editorUI.getCurrentElementType();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!editorUI.isMenu()) {
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
                    currentElementType = 0;
                    currentElementValue = EDITOR_ERASE_VALUE;
                    modifyCurrentLevel(mouseX, mouseY);
                }
            }
        }
        else {
            if (editorUI.onExit) {
               GameState.setState(GameState.MENU);
            }
            else if (editorUI.onSave) {

            }
         }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (editorUI.isMenu()) {
            editorUI.onSave = editorUI.saveButton.onButton(e.getX(), e.getY());
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
                currentElementType = 0;
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
                levelHandler.getCurrentLevel().setLevelState(Level.LevelState.OTHER);
                break;
            case KeyEvent.VK_ESCAPE:
                if (editorUI.isMenu()) {
                    editorUI.setMenu(false);
                }
                else {
                    editorUI.setMenu(true);
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

    private void updateCurrentLevel() {
        currentLevel = game.getIngame().getLevelHandler().getCurrentLevel();
    }
}
