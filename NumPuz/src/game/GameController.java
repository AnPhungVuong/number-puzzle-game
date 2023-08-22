package game;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * This controller create UI and Logic game
 * 
 * @author Phung An Vuong
 * @since java 15
 *
 */

public class GameController {

	private int N = 3;
	private final double OFFSET_X = 305;
	private final double WIDTH = 60;

	private BorderPane board;
	private BorderPane[][] tiles;
	private BorderPane rightPane;
	private BorderPane leftPane;
	private Label lbTime;
	private Label lbTimerCount;
	private Label lbMove;
	private Label lbMoveCount;
	private Label lbScore;
	private Label lbScoreCount;
	private Button resetBtn;

	private String boardValue[][];

	private ToggleGroup groupMode = new ToggleGroup();
	private RadioButton rdBtnDesign = new RadioButton("Design");
	private RadioButton rdBtnPlay = new RadioButton("Play");

	private ToggleGroup groupType = new ToggleGroup();
	private RadioButton rdBtnNumber = new RadioButton("Number");
	private RadioButton rdBtnText = new RadioButton("Text");

	Integer dimStr[] = { 3, 4, 5 };

	ComboBox<Integer> cbxDim = new ComboBox<Integer>(FXCollections.observableArrayList(dimStr));

	private Menu menuGame = new Menu("Game");
	private MenuItem menuNew = new MenuItem("New");

	private MenuItem menuExit = new MenuItem("Exit");
	private Menu menuHelp = new Menu("Help");
	private MenuItem menuAbout = new MenuItem("About");
	private MenuBar menuBar = new MenuBar();

	private TextArea txtServerOutput = new TextArea();
	private Button btnOkDialog = new Button("No");
	private Stage stageDialog = new Stage();

	private BorderPane borderPane;

	/**
	 * Default constructor
	 */
	public GameController() {
		borderPane = new BorderPane();
		rightPane = new BorderPane();
		leftPane = new BorderPane();
		lbTime = new Label("Time:");
		lbTimerCount = new Label("0");
		lbMove = new Label("Move:");
		lbMoveCount = new Label("0");
		lbScore = new Label("Score:");
		lbScoreCount = new Label("0");
		resetBtn = new Button("Reset");
		rdBtnDesign.setToggleGroup(groupMode);
		rdBtnPlay.setToggleGroup(groupMode);
		rdBtnDesign.setSelected(true);
		rdBtnNumber.setToggleGroup(groupType);
		rdBtnText.setToggleGroup(groupType);
		rdBtnNumber.setSelected(true);
		cbxDim.setValue(N);
		createUI();
		startNewGame();
		setAction();

	}

	/**
	 * This method set all action listener
	 */
	public void setAction() {
		menuAbout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				displayAbout();
			}
		});

		btnOkDialog.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				stageDialog.close();
			}
		});

		cbxDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				int value = (int) cbxDim.getValue();
				N = value;
				startNewGame();
			}
		});

		rdBtnNumber.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				int value = (int) cbxDim.getValue();
				N = value;
				startNewGame();
			}
		});
		rdBtnText.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				int value = (int) cbxDim.getValue();
				N = value;
				startNewGame();
			}
		});
	}

	/**
	 * Start new game which set up game board
	 */
	public void startNewGame() {
		generateBoardValue();
		createRightPane();
	}

	/**
	 * create game UI
	 * return borderPane
	 */
	public BorderPane createUI() {

		createLeftPane();
		borderPane.setTop(createMenu());
		borderPane.setLeft(leftPane);
		borderPane.setCenter(rightPane);
		return borderPane;
	}

	/**
	 * This method generate board value by on Type (Number or Text)
	 */
	public void generateBoardValue() {
		if (rdBtnNumber.isSelected()) {
			generateNumber(N);
		} else {
			generateCharacter(N);
		}
	}

	/**
	 * This method generate random number fron 1 to (dim*dim -1)
	 * 
	 * @param dim
	 * @return
	 */
	public Set<Integer> generateNumber(int dim) {
		Random randNum = new Random();
		Set<Integer> set = new LinkedHashSet<Integer>();
		while (set.size() < dim * dim - 1) {
			set.add(randNum.nextInt(dim * dim - 1) + 1);
		}
		boardValue = new String[dim][dim];
		Iterator<Integer> value = set.iterator();
		int i = 0, j = 0;

		while (value.hasNext()) {

			boardValue[i][j] = String.valueOf(value.next());
			if (j < dim - 1) {
				j++;
			} else {
				j = 0;
				i++;
			}
		}

		return set;
	}

	/**
	 * This method generate random character for game board
	 * 
	 * @param dim
	 * @return
	 */
	public Set<Character> generateCharacter(int dim) {
		Random randNum = new Random();
		Set<Character> set = new LinkedHashSet<Character>();
		while (set.size() < dim * dim - 1) {
			set.add((char) ('a' + randNum.nextInt(26)));
		}
		boardValue = new String[dim][dim];
		Iterator<Character> value = set.iterator();
		int i = 0, j = 0;

		while (value.hasNext()) {

			boardValue[i][j] = String.valueOf(value.next());
			if (j < dim - 1) {
				j++;
			} else {
				j = 0;
				i++;
			}
		}

		return set;
	}

	/**
	 * Create board
	 */
	public void createRightPane() {

		rightPane.getChildren().clear();
		rightPane.setStyle("-fx-border-color: red");
		setBoard(new BorderPane());
		setTiles(new BorderPane[N][N]);
		int indexXY = 0;
		if (N == 5) {
			indexXY = 60;
		} else if (N == 4) {
			indexXY = 90;
		} else {
			indexXY = 120;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				getTiles()[i][j] = new BorderPane();
				Rectangle r = new Rectangle(WIDTH, WIDTH);
				r.setStroke(Color.GRAY);

				r.setFill(Color.WHITE.deriveColor(1, 1, 1, 0.7));

				r.setOnMouseClicked(e -> {
					// TODO
				});

				StackPane st = new StackPane();
				if (boardValue[i][j] == null) {
					r.setFill(Color.BLACK.deriveColor(1, 1, 1, 0.7));
					st.getChildren().addAll(r);
				} else {
					Label lb = new Label(boardValue[i][j]);
					lb.setFont(new Font("Arial", 30));
					lb.setAlignment(Pos.CENTER);
					lb.setMinWidth(WIDTH);
					st.getChildren().addAll(r, lb);
				}
				getTiles()[i][j].getChildren().addAll(st);

				getTiles()[i][j].setLayoutX(i * WIDTH + indexXY);
				getTiles()[i][j].setLayoutY(j * WIDTH + indexXY);
				getBoard().getChildren().add(getTiles()[i][j]);

			}
		}
		rightPane.getChildren().add(getBoard());

	}

	/**
	 * create menu on top of game
	 * 
	 * @return
	 */
	public BorderPane createMenu() {

		menuNew.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/iconnew.png"))));

		menuExit.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/iconext.png"))));

		menuGame.getItems().add(menuNew);

		menuGame.getItems().add(menuExit);

		menuAbout.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/iconabt.png"))));

		menuHelp.getItems().add(menuAbout);

		menuBar.getMenus().add(menuGame);
		menuBar.getMenus().add(menuHelp);

		return new BorderPane(menuBar);
	}

	/**
	 * create right pane contains score, time, server output
	 */
	public void createLeftPane() {
		// Right content

		leftPane.setMaxHeight(380);
		leftPane.setMinHeight(100);
		leftPane.setMaxWidth(300);

		ImageView imgLogo = new ImageView(new Image(getClass().getResourceAsStream("/img/gamelogo.png")));
		imgLogo.setFitWidth(300);
		leftPane.setTop(imgLogo);

		txtServerOutput.setEditable(false);
		ScrollPane scrollPaneOutputServer = new ScrollPane();
		scrollPaneOutputServer.setContent(txtServerOutput);
		scrollPaneOutputServer.setFitToWidth(true);
		scrollPaneOutputServer.setFitToHeight(true);
		leftPane.setCenter(scrollPaneOutputServer);

		FlowPane flTime = new FlowPane();
		flTime.setAlignment(Pos.CENTER);

		lbTime.setFont(Font.font("sansserif", 15));
		lbTimerCount.setFont(Font.font("sansserif", 15));
		flTime.getChildren().addAll(lbTime, lbTimerCount);
		flTime.setPadding(new Insets(10, 0, 10, 0));

		FlowPane flMove = new FlowPane();
		flMove.setAlignment(Pos.CENTER);
		lbMove.setFont(Font.font("sansserif", 15));
		lbMoveCount.setFont(Font.font("sansserif", 15));
		flMove.getChildren().addAll(lbMove, lbMoveCount);
		flMove.setPadding(new Insets(10, 0, 10, 0));

		lbScore.setFont(Font.font("sansserif", 15));
		lbScoreCount.setFont(Font.font("sansserif", 15));
		FlowPane flScore = new FlowPane();
		flScore.setAlignment(Pos.CENTER);
		flScore.getChildren().addAll(lbScore, lbScoreCount);
		flScore.setPadding(new Insets(10, 0, 10, 0));

		FlowPane flMode = new FlowPane();
		flMode.setAlignment(Pos.CENTER);
		flMode.getChildren().addAll(new Label("Mode"), rdBtnDesign, rdBtnPlay);
		flMode.setHgap(10);

		FlowPane flOption = new FlowPane();
		flOption.setAlignment(Pos.CENTER);
		flOption.getChildren().addAll(new Label("Type"), rdBtnNumber, rdBtnText);
		flOption.setHgap(10);
		flOption.setPadding(new Insets(10, 0, 10, 0));

		FlowPane flDim = new FlowPane();
		flDim.setAlignment(Pos.CENTER);
		flDim.getChildren().addAll(new Label("Select Dim "), cbxDim);
		flDim.setHgap(10);
		flDim.setPadding(new Insets(10, 0, 10, 0));

		GridPane gr = new GridPane();
		gr.setPadding(new Insets(10, 0, 10, 0));
		gr.add(flMode, 0, 0, 2, 1);
		gr.add(flOption, 0, 1, 2, 1);
		gr.add(flDim, 0, 2, 2, 1);
		gr.add(flMove, 0, 3);
		gr.add(flScore, 1, 3);
		gr.add(flTime, 0, 4);
		gr.add(resetBtn, 1, 4);
		leftPane.setBottom(gr);
		leftPane.setStyle("-fx-border-color: green");

	}

	/**
	 * Set time to UI
	 * 
	 * @param time
	 */
	public void setTime(int time) {
		lbTimerCount.setText(String.valueOf(time));
	}

	/**
	 * This method display about dialog
	 */
	public void displayAbout() {
		stageDialog = new Stage();
		stageDialog.initModality(Modality.APPLICATION_MODAL);
		ImageView customImage;
		customImage = new ImageView(
				new Image(getClass().getResourceAsStream("/img/gameabout.png"), 390, 330, false, true));
		stageDialog.setTitle("Algonquin College - Phung An Vuong");
		GridPane messageConfirmGP = new GridPane();
		messageConfirmGP.setPadding(new Insets(10, 10, 10, 10));
		messageConfirmGP.setVgap(5);
		messageConfirmGP.setHgap(5);
		messageConfirmGP.add(customImage, 1, 1);

		btnOkDialog.setText("Ok");
		FlowPane flButtonConfirm = new FlowPane();
		flButtonConfirm.getChildren().addAll(btnOkDialog);
		flButtonConfirm.setAlignment(Pos.CENTER);
		flButtonConfirm.setHgap(10);
		flButtonConfirm.setPadding(new Insets(10, 10, 10, 10));
		messageConfirmGP.add(flButtonConfirm, 1, 3);
		Scene scene = new Scene(messageConfirmGP, 420, 400);
		stageDialog.setScene(scene);
		stageDialog.showAndWait();
	}

	/**
	 * @return the board
	 */
	public BorderPane getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(BorderPane board) {
		this.board = board;
	}

	/**
	 * @return the tiles
	 */
	public BorderPane[][] getTiles() {
		return tiles;
	}

	/**
	 * @param tiles the tiles to set
	 */
	public void setTiles(BorderPane[][] tiles) {
		this.tiles = tiles;
	}

	/**
	 * @return the leftPane
	 */
	public BorderPane getLeftPane() {
		return leftPane;
	}

	/**
	 * @param leftPane the leftPane to set
	 */
	public void setLeftPane(BorderPane leftPane) {
		this.leftPane = leftPane;
	}

	/**
	 * @return the rightPane
	 */
	public BorderPane getRightPane() {
		return rightPane;
	}

	/**
	 * @param rightPane the rightPane to set
	 */
	public void setRightPane(BorderPane rightPane) {
		this.rightPane = rightPane;
	}

	/**
	 * @return the lbTimer
	 */
	public Label getLbTimer() {
		return lbTime;
	}

	/**
	 * @param lbTimer the lbTimer to set
	 */
	public void setLbTimer(Label lbTimer) {
		this.lbTime = lbTimer;
	}

	/**
	 * @return the lbTimerCount
	 */
	public Label getLbTimerCount() {
		return lbTimerCount;
	}

	/**
	 * @param lbTimerCount the lbTimerCount to set
	 */
	public void setLbTimerCount(Label lbTimerCount) {
		this.lbTimerCount = lbTimerCount;
	}

	/**
	 * @return the lbScore
	 */
	public Label getLbScore() {
		return lbScore;
	}

	/**
	 * @param lbScore the lbScore to set
	 */
	public void setLbScore(Label lbScore) {
		this.lbScore = lbScore;
	}

	/**
	 * @return the lbScoreCount
	 */
	public Label getLbScoreCount() {
		return lbScoreCount;
	}

	/**
	 * @param lbScoreCount the lbScoreCount to set
	 */
	public void setLbScoreCount(Label lbScoreCount) {
		this.lbScoreCount = lbScoreCount;
	}

	/**
	 * @param score
	 */
	public void setLbScoreCount(String score) {
		this.lbScoreCount.setText(score);
	}

	/**
	 * @return the resetBtn
	 */
	public Button getResetBtn() {
		return resetBtn;
	}

	/**
	 * @param resetBtn the resetBtn to set
	 */
	public void setResetBtn(Button resetBtn) {
		this.resetBtn = resetBtn;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return N;
	}

	/**
	 * @return the oFFSET
	 */
	public double getOFFSET() {
		return OFFSET_X;
	}

	/**
	 * @return the wIDTH
	 */
	public double getWIDTH() {
		return WIDTH;
	}

	/**
	 * @return the txtServerOutput
	 */
	public TextArea getTxtServerOutput() {
		return txtServerOutput;
	}

	/**
	 * @param txtServerOutput the txtServerOutput to set
	 */
	public void setTxtServerOutput(TextArea txtServerOutput) {
		this.txtServerOutput = txtServerOutput;
	}

	/**
	 * @return the stageDialog
	 */
	public Stage getStageDialog() {
		return stageDialog;
	}

	/**
	 * @param stageDialog the stageDialog to set
	 */
	public void setStageDialog(Stage stageDialog) {
		this.stageDialog = stageDialog;
	}

	/**
	 * @return the borderPane
	 */
	public BorderPane getBorderPane() {
		return borderPane;
	}

	/**
	 * @param borderPane the borderPane to set
	 */
	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

}