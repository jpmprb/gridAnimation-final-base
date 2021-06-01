package pt.ipbeja.estig.gridanim.gui;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.ipbeja.estig.gridanim.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The anim main view
 *
 * @author João Paulo Barros
 * @version 2021/06/01
 */
public class GameBoard extends Application implements View {
    private static final double TRANSLATION_TIME_IN_MILLIS = 100;

    private final String ICON_FILE = "/resources/images/background100.png";
    private Model model;

    private Button startButton;
    private Pane pane;
    private Map<Mobile, GameImage> movingImages;
    private static Map<KeyCode, Direction> directionMap = new HashMap<>();

    static { // maps JavaFX key code to model direction
        directionMap.put(KeyCode.UP, Direction.UP);
        directionMap.put(KeyCode.DOWN, Direction.DOWN);
        directionMap.put(KeyCode.LEFT, Direction.LEFT);
        directionMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }

    //private GameImage heroImage;

    @Override
    public void start(Stage stage) {
        this.createModel();

        Scene scnMain = this.createScene();

        stage.setTitle("Grid Anim");
        stage.setScene(scnMain);
        stage.setOnCloseRequest((e) -> {
            System.exit(0);
        });
        stage.show();
    }

    /**
     * Creates de model and adds monsters
     */
    private void createModel() {
        this.model = new Model(this);
        // add monsters
        for (int i = 1; i <= 10; i++) {
            System.out.println(model.addNewMonster(i + ""));
        }
    }

    /**
     * Creates GUI Scene
     * @return the created scene
     */
    private Scene createScene() {
        VBox vbxMain = new VBox();

        this.startButton = new Button("Start");
        this.startButton.setOnAction(e -> {
            this.model.moveMonsters(Long.MAX_VALUE);
            this.startButton.setDisable(true);
        });
        Pane gridUI = this.createGridUI();
        vbxMain.getChildren().addAll(this.startButton, gridUI);

        Scene scene = new Scene(vbxMain);
        this.setKeyHandle(scene);

        return scene;
    }

    /**
     * Set keyboard handler
     * @param scene the scene
     */
    void setKeyHandle(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            model.moveHeroInDirection(directionMap.get(event.getCode()));
        });
    }

    /**
     * Create grid of images and monsters in a pane
     * @return the pane with the images added
     */
    private Pane createGridUI() {
        this.pane = new Pane();
        for (int line = 0; line < Model.N_LINES; line++) {
            for (int col = 0; col < Model.N_COLS; col++) {
                Position pos = new Position(line, col);
                GameImage pi = new GameImage("background100", pos);
                this.pane.getChildren().add(pi); // add to gui
            }
        }
        this.addMonsterImages(pane);
        return pane;
    }

    /**
     * Add monsters to GUI
     * @param pane the pane to add the monsters into
     */
    private void addMonsterImages(Pane pane) {
        this.movingImages = new HashMap<>();
        List<Monster> monsters = this.model.getMonsters();
        for (Monster m : monsters) {
            Position pos = m.getPos();
            GameImage pi = new GameImage(m.getName(), pos);
            this.pane.getChildren().add(pi); // add to gui
            this.movingImages.put(m, pi); // add to map of moving images
        }
    }

    /**
     * Update GUI after movement of Mobile object
     *
     * @param mobile object to move
     * @param endPos position after moving
     */
    public void updateMove(Mobile mobile, Position endPos) {
        Platform.runLater(() -> {
            GameImage imageToMove = this.movingImages.get(mobile);
            if (imageToMove != null) {
                TranslateTransition tt =
                        new TranslateTransition(Duration.millis(TRANSLATION_TIME_IN_MILLIS), imageToMove);
                int beginLine = mobile.getPos().getLine();
                int beginCol = mobile.getPos().getCol();
                int endLine = endPos.getLine();
                int endCol = endPos.getCol();
                int dCol = endCol - beginCol;
                int dLine = endLine - beginLine;

                tt.setByX(dCol * GameImage.SIDE_SIZE);
                tt.setByY(dLine * GameImage.SIDE_SIZE);
                tt.setOnFinished(e -> {
                    imageToMove.updatePosition(dCol, dLine);
                    model.moveMobile(mobile, endPos); // update the model
                });
                tt.play();
            }
        });
    }

    /**
     * Start program
     *
     * @param args currently not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
