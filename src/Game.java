/*
 * Game is used to create and manage the GUI and
 * animations. Initially a window with the starting menu
 * is displayed, allowing users to enter their names and
 * desired time limit. The the main game window with the
 * checkers and board is created. This class manages the
 * visual side of the program. When the game is paused or
 * the appropriate popup windows are displayed (and the
 * music is paused or changed to clapping, respectively).
 */
import javafx.application.* ;
import javafx.stage.* ;
import javafx.scene.* ;
import javafx.scene.layout.* ;
import javafx.scene.paint.* ;
import javafx.geometry.* ;
import javafx.scene.shape.Rectangle ;
import javafx.scene.text.Text ;
import javafx.scene.control.* ;
import javafx.event.* ;
import javafx.util.* ;
import javafx.animation.* ;
import java.net.URL ;
import javafx.scene.media.Media ;
import javafx.scene.media.MediaPlayer ;


public class Game extends Application {
  Checkers checkers = new Checkers() ;
  Menu mainMenu = new Menu() ;
  Pause pausePop = new Pause() ;
  private Stage menuStage ;
  private Scene gameScene ;
  private BorderPane game ;
  private Pane left, right ;
  private TilePane top, bottom ;
  private StackPane board ;
  private Group pieces = new Group() ;
  private String p1Name, p2Name, p1Time, p2Time, gameTime ;
  private boolean upwards = true, paused = false ;
  private URL mainSong =
    getClass().getResource(
    "./music/Funky_Jazzy_Loop.wav") ;
  private Media theme = new Media(mainSong.toString()) ;
  private MediaPlayer themeMusic = new MediaPlayer(theme) ;
  private URL applause =
    getClass().getResource(
    "./music/People_Applause_short.wav") ;
  private Media winClap = new Media(applause.toString()) ;
  private MediaPlayer winMusic = new MediaPlayer(winClap) ;
  private static final String GAME_TITLE = "Checkers" ;  

  // Handles the stages that are displayed and closed.
  @Override
  public void start(Stage gameStage) {
    menuStage = mainMenu.display_menu() ;
    menuStage.show() ;

    menuStage.setOnCloseRequest(menuEvent -> {
      Platform.exit() ;
    }) ;

    menuStage.setOnHidden(menuEvent -> {
      set_game_information() ;
      setup_game_panes() ;
      gameScene = new Scene(game) ;
      setup_main_stage(gameStage) ;
      gameStage.setScene(gameScene) ;

      gameStage.setOnCloseRequest(e -> {
        pausePop.close_paused_popup() ;
      }) ;

      pausePop.setOnHidden(e -> {
        update_game_paused() ;
        themeMusic.play() ;
      }) ;

      gameStage.show() ;

      themeMusic.setCycleCount(MediaPlayer.INDEFINITE) ;
      themeMusic.play() ;
    }) ;
  }

  //  From the user entries in menu, set the game info.
  private void set_game_information() {
    String entries[] = mainMenu.get_user_entries() ;

    if (entries[0] != null && !entries[0].isEmpty()) {
      p1Name = entries[0] ;
    }
    else { p1Name = "Player One" ; }

    if (entries[1] != null && !entries[1].isEmpty()) {
      p2Name = entries[1] ;
    }
    else { p2Name = "Player Two" ; }

    if (entries[2] != null && !entries[2].isEmpty()) {
      p1Time = entries[2] ;
    }
    else { p1Time = "10:00" ; }

    if (entries[3] != null && !entries[3].isEmpty()) {
      p2Time = entries[3] ;
    }
    else { p2Time = "10:00" ; }

    if (entries[4] != null && !entries[4].isEmpty()) {
      gameTime = entries[4] ;
    }
    else { gameTime = "00:00" ; }

    return ;
  }

  // Create all the different panes for the game window.
  private void setup_game_panes() {
    game = setup_main_game_pane() ;
    left = setup_game_vertical_panes() ;
    right = setup_game_vertical_panes() ;
    top = setup_game_top_pane() ;
    bottom = setup_game_bottom_pane() ;
    board = setup_game_board_pane() ;

    game.setLeft(left) ;
    game.setRight(right) ;
    game.setTop(top) ;
    game.setBottom(bottom) ;
    game.setCenter(board) ;

    return ;
  }

  // Create the BorderPane for layout of GUI in game window.
  private BorderPane setup_main_game_pane() {
    BorderPane pane = new BorderPane() ;

    pane.setBackground(
      new Background(
        new BackgroundFill(Color.rgb(
          Symbol.BACKGROUND_R.value,
          Symbol.BACKGROUND_B.value,
          Symbol.BACKGROUND_G.value,
        1), CornerRadii.EMPTY, Insets.EMPTY))) ;

    return pane ;
  }

  // Create the left and right panes in the game window.
  private Pane setup_game_vertical_panes() {
    Pane pane = new Pane() ;
    Rectangle side = new Rectangle(
    (Symbol.BORDERS.value * Symbol.TILE.value),
      ((Symbol.SIZE.value * Symbol.TILE.value) +
    (Symbol.BORDERS.value * Symbol.BORDER.value)),
    Color.rgb(
      Symbol.EDGE_R.value,
      Symbol.EDGE_G.value,
      Symbol.EDGE_B.value,
    1)) ;

    pane.getChildren().add(side) ;

    return pane ;
  }

  // Create top pane and each of the 9 inner panes for game.
  private TilePane setup_game_top_pane() {
    String topDisplay[] = {p1Name, gameTime, p2Name, p1Time,
      "", p2Time, "0", "", "0"} ;
    TilePane pane = new TilePane() ;
    StackPane inner[] = new StackPane[9] ;
    pane.setPrefColumns(Symbol.COLS.value) ;
    pane.setPrefRows(Symbol.ROWS.value) ;

    for (int i = 0 ; i < 9 ; i++) {
      if (topDisplay[i] == null) {
        throw new Error("String: " + i + "for the top " +
          "pane is null.") ;
      }

      inner[i] = setup_inner_stack_text(topDisplay[i]) ;

      if (inner[i] == null) {
        throw new Error("String: " + i + " for top pane " +
          "is null.") ;
      }

      pane.getChildren().add(inner[i]) ;
    }

    return pane ;
  }

  // Create a StackPane with a string in the center.
  private StackPane setup_inner_stack_text(String input) {
    StackPane inner = new StackPane() ;
    Text display = new Text(input) ;

    Rectangle rectangle = new Rectangle(
    ((4 * Symbol.TILE.value) + (2 * Symbol.BORDER.value)/3),
    ((2 * Symbol.TILE.value)/3), Color.rgb(
      Symbol.EDGE_R.value,
      Symbol.EDGE_G.value,
      Symbol.EDGE_B.value,
    1)) ;

    inner.getChildren().addAll(rectangle, display) ;

    return inner ;
  }

  // Create top pane and each of the 9 inner panes for game.
  private TilePane setup_game_bottom_pane() {
    String bottomDisplay[] = {"",  "", "", "", "", "",
      "GAME_TITLE", ""} ;
    String buttonName[] = {"Rotate Board", "Pause Game"} ;
    TilePane pane = new TilePane() ;
    StackPane inner[] = new StackPane[8] ;
    HBox bottomRight = new HBox() ;
    pane.setPrefColumns(Symbol.COLS.value) ;
    pane.setPrefRows(Symbol.ROWS.value) ;

    for (int i = 0 ; i < 8 ; i++) {
      if (bottomDisplay[i] == null) {
        throw new Error("String: " + i + "for the bottom " +
          "pane is null.") ;
      }

      inner[i] = setup_inner_stack_text(bottomDisplay[i]) ;

      if (inner[i] == null) {
        throw new Error("Inner pane: " + i + " for bottom" +
          " pane is null.") ;
      }

      pane.getChildren().add(inner[i]) ;
    }

    bottomRight = setup_bottom_right_pane(buttonName) ;

    if (bottomRight == null) {
      throw new Error("Bottom right inner pane for bottom" +
        " pane is null.") ;
    }

    pane.getChildren().add(bottomRight) ;

    return pane ;
  }

  // Use HBox for buttons bottom right of GUI of game window
  private HBox setup_bottom_right_pane(String bName[]) {
    HBox bottomRight = new HBox() ;
    StackPane buttons[] = new StackPane[Symbol.BUTS.value] ;

    for (int i = 0 ; i < Symbol.BUTS.value ; i++) {
      if (bName[i] == null) {
        throw new Error("Bottom button String is null.") ;
      }

      buttons[i] = setup_inner_stack_button(bName[i]) ;

      if (buttons[i] == null) {
        throw new Error("Inner pane: " + i + " for button" +
          " in bottom right corner of bottom pane.") ;
      }

      bottomRight.getChildren().add(buttons[i]) ;
    }

    return bottomRight ;
  }

  // Create StackPane with button called 'input' in center.
  private StackPane setup_inner_stack_button(String input){
    StackPane inner = new StackPane() ;
    Button button = new Button(input) ;

    button.setOnAction(e-> game_button_click(e)) ;

    Rectangle rectangle = new Rectangle(
    ((2 * Symbol.TILE.value) + Symbol.BORDER.value/3),
    ((2 * Symbol.TILE.value)/3), Color.rgb(
      Symbol.EDGE_R.value,
      Symbol.EDGE_G.value,
      Symbol.EDGE_B.value,
    1)) ;

    inner.getChildren().addAll(rectangle, button) ;

    return inner ;
  }

  // Operation to be performed when button is pressed.
  private void game_button_click(ActionEvent event) {
    Button button = (Button) event.getSource() ;
    String command = button.getText() ;

    if (command.equals("Rotate Board")) {
      rotate_board_and_pieces() ;
      update_board_orientation() ;
    }

    else if (command.equals("Pause Game")) {
      if (paused == true) { return ; }
      update_game_paused() ;
      pausePop.create_paused_popup() ;
      themeMusic.pause() ;
    }

    else {
      throw new Error("Unexpected button pressed (name).") ;
    }

    return ;
  }

  // Rotate board and all pieces 180 about their center.
  private void rotate_board_and_pieces() {
    int board_rotate = Symbol.ROTATE180.value ;

    if (upwards == false) {
      board_rotate *= -1 ;
    }

    RotateTransition surface =
      new RotateTransition(Duration.millis(
        Symbol.TIME.value),
      board) ;
    surface.setByAngle(board_rotate) ;
    surface.setCycleCount(1) ;
    surface.setAutoReverse(true) ;

    ParallelTransition rotate =
      new ParallelTransition(surface) ;
    rotate.setAutoReverse(true) ;

    for (Node piece : pieces.getChildren()) {
      RotateTransition checker =
        new RotateTransition(Duration.millis(
          Symbol.TIME.value),
        piece) ;
      checker.setByAngle(-board_rotate) ;
      checker.setCycleCount(1) ;
      checker.setAutoReverse(true) ;

      rotate.getChildren().add(checker) ;
    }

    rotate.play() ;

    return ;
  }

  // Update orientation of the board to keep track of it.
  private void update_board_orientation() {
    if (upwards == true) { upwards = false ; }
    else { upwards = true ; }

    return ;
  }

  // Toggle field used to track whether the game is paused.
  private void update_game_paused() {
    if (paused == false) { paused = true ; }
    else { paused = false ; }

    return ;
  }

  // Use StackPane to create board with a border for game.
  private StackPane setup_game_board_pane() {
    StackPane board = new StackPane() ;
    Rectangle border = new Rectangle(
    ((Symbol.TILE.value * Symbol.SIZE.value) +
      (Symbol.BORDERS.value * Symbol.BORDER.value)),
    ((Symbol.TILE.value * Symbol.SIZE.value) +
      (Symbol.BORDERS.value * Symbol.BORDER.value)),
    Color.rgb(
      Symbol.BORDER_R.value,
      Symbol.BORDER_G.value,
      Symbol.BORDER_B.value,
    1)) ;
    Pane tiles = setup_board_tiles() ;

    board.getChildren().addAll(border, tiles) ;

    return board ;
  }

  // Create a pane consisting of the board tiles.
  private Pane setup_board_tiles() {
    Pane tiles = new Pane() ;
    Group boardTiles = new Group() ;
    tiles.setPrefSize((Symbol.TILE.value *
      Symbol.TILE.value), (Symbol.TILE.value *
      Symbol.TILE.value)) ;

    for (int i = 0 ; i < Symbol.SIZE.value ; i++) {
      for (int j = 0 ; j < Symbol.SIZE.value ; j++) {
        Tile tile = define_and_gen_board_tile(i, j) ;

        boardTiles.getChildren().add(tile) ;
      }
    }

    tiles.getChildren().addAll(boardTiles) ;

    setup_pieces(tiles) ;

    return tiles ;
  }

  // Based on position, generate appropriate tile.
  private Tile define_and_gen_board_tile(int i, int j) {
    Tile tile ;

    if ((i % 2) == 1) {
      if ((j % 2) == 1) {
        tile = create_single_tile(Symbol.LIGHT.value, i, j);
      }
      else {
        tile = create_single_tile(Symbol.DARK.value, i, j) ;
      }
    }

    else {
      if ((j % 2) == 0) {
        tile = create_single_tile(Symbol.LIGHT.value, i, j);
      }
      else {
        tile = create_single_tile(Symbol.DARK.value, i, j) ;
      }
    }

    if (tile == null) {
      throw new Error("Tile not generated.") ;
    }

    return tile ;
  }

  // Create a single tile for the board using Tile().
  private Tile create_single_tile(int type, int i, int j) {
    Tile tile = new Tile(type, i, j) ;

    if (tile == null) {
      throw new Error("Unable to create board tile.") ;
    }

    return tile ;
  }

  // Add all the pieces in their starting positions to board
  private void setup_pieces(Pane tiles) {
    int blackSide[] = {0, 1, 2}, redSide[] = {5, 6, 7} ;

    for (int j = 0 ; j < Symbol.SIZE.value ; j++) {
      for (int i : blackSide) {
        define_and_generate_piece(Symbol.BLACK.value, i, j);
      }
      for (int i : redSide) {
        define_and_generate_piece(Symbol.RED.value, i, j) ;
      }
    }

    tiles.getChildren().addAll(pieces) ;

    return ;
  }

  // Based on position, generate appropriate piece.
  private void define_and_generate_piece(int player,
  int i, int j) {
    if (((i % 2) == 1) && ((j % 2) == 0)){
      Piece piece = create_piece(player, i, j) ;

      pieces.getChildren().add(piece) ;
    }

    else if (((i % 2) == 0) && ((j % 2) == 1)){
      Piece piece = create_piece(player, i, j) ;

      pieces.getChildren().add(piece) ;
    }

    return ;
  }

  // Create a single tile for the board using Piece().
  private Piece create_piece(int type, int i, int j) {
    double coords[] = new double[2] ;
    int posOld[] = new int[2], posNew[] = new int[2] ;
    Piece piece = new Piece(type, i, j) ;

    if (piece == null) {
      throw new Error("Unable to create board tile.") ;
    }

    posOld[0] = posNew[0] = j ;
    posOld[0] = posNew[1] = i ;

    piece.setOnMousePressed(e -> {
      posOld[0] = posNew[0] ;
      posOld[1] = posNew[1] ;
    }) ;

    piece.setOnMouseDragged(e -> {
      int calibration = (((2 * Symbol.TILE.value) +
      Symbol.BORDER.value) + (Symbol.TILE.value/2)) ;

      coords[0] = e.getSceneY() - calibration ;
      coords[1] = e.getSceneX() - calibration ;

      if (upwards == true) {
        piece.setY(coords[0]) ;
        piece.setX(coords[1]) ;

        calculate_position_on_grid(coords, posNew) ;
      }
    }) ;

   // When the piece is placed...
    piece.setOnMouseReleased(e -> {
      // Check to see if put in an appropriate square...
      if (checkers.make_move(posOld, posNew) == false) {
        update_piece_location(piece, posOld) ;
        posNew[0] = posOld[0] ;
        posNew[1] = posOld[1] ;
      }
      // If it is, then check if attack, if king or if won.
      else {
        if (checkers.piece_was_taken() == true) {
          delete_taken_piece() ;
        }

        if (checkers.check_if_becomes_king(posNew[1],
        posNew[0])) {
          pieces.getChildren().remove(piece) ;
          piece.promote_piece_to_king() ;
          pieces.getChildren().add(piece) ;
        }

        check_if_game_finished() ;
      }
    }) ;


    return piece ;
  }

  // Determine which square on board from pixels.
  private void calculate_position_on_grid(double coords[],
  int posNew[]) {

    posNew[1] = (int) (coords[0] / Symbol.TILE.value) ;

    if (posNew[1] < 0) { posNew[1] = 0 ; }
    else if (posNew[1] > 7) { posNew[1] = 7 ; }

    posNew[0] = (int) (coords[1] / Symbol.TILE.value) ;

    if (posNew[0] < 0) { posNew[0] = 0 ; }
    else if (posNew[0] > 7) { posNew[0] = 7 ; }

    return ;
  }

  // Update the position of the piece on the screen.
  private void update_piece_location(Piece piece,
  int pos[]) {
    piece.setY(Symbol.BORDER.value +
      (pos[1] * Symbol.TILE.value)) ;

    piece.setX(Symbol.BORDER.value +
      (pos[0] * Symbol.TILE.value)) ;

    return ;
  }

  // If a piece was taken, remove it from the screen.
  private void delete_taken_piece() {
    int removePos[] = checkers.get_last_taken() ;

    if ((removePos[0] == -1) || (removePos[1] == -1)) {
      throw new Error("Error with grid position of piece " +
        "to be taken from checkers.get_last_taken().") ;
    }

    for (Node delete : pieces.getChildren()) {
      Bounds boundsInScene =
        delete.localToScene(delete.getBoundsInLocal()) ;

      if (piece_to_remove(boundsInScene.getMinY(),
      boundsInScene.getMinX(), removePos)) {
        pieces.getChildren().remove(delete) ;
        return ;
      }
    }

    return ;
  }

  // Find which piece that was taken using coordinates.
  private boolean piece_to_remove(double row, double col,
  int remove[]) {
    boolean height = false, width = false ;
    int buffer = (Symbol.TILE.value / 2) ;
    int border = ((2 * Symbol.TILE.value) +
      Symbol.BORDER.value) ;
    int y = (remove[1] * Symbol.TILE.value) + border ;
    int x = (remove[0] * Symbol.TILE.value) + border ;

    if ((row > y - buffer) && (row < y + buffer)) {
      height = true ;
    }

    if ((col > x - buffer) && (col < x + buffer)) {
      width = true ;
    }

    if ((height == true) && (width == true)) {
      return true ;
    }

    return false ;
  }

  // Using Checkers, check to see if the game is finished.
  private void check_if_game_finished() {
    int winner = checkers.check_if_player_has_won() ;

    if (winner == Symbol.STILLGOING.value) { return ; }

    if (winner == Symbol.RED.value) {
      themeMusic.stop() ;
      winMusic.setCycleCount(Symbol.REPEAT.value) ;
      winMusic.play() ;
      Win p1winner = new Win(p1Name) ;
    }

    else if (winner == Symbol.BLACK.value) {
      themeMusic.stop() ;
      winMusic.setCycleCount(Symbol.REPEAT.value) ;
      winMusic.play() ;
      Win p2winner = new Win(p2Name) ;
    }

    return ;
  }

  // Set the stage for the game window and give it a title.
  private void setup_main_stage(Stage stage) {
    String gameTitle = "GAME_TITLE" ;

    int height = ((Symbol.GUI.value * Symbol.TILE.value) +
      (Symbol.GUI_BORDER_BUFF.value * Symbol.BORDER.value) +
      Symbol.GUI_HEIGHT_BUFFER.value) ;
    int width = ((Symbol.GUI.value * Symbol.TILE.value) +
      (Symbol.BORDERS.value * Symbol.BORDER.value)) ;

    stage.setMinWidth(width) ;
    stage.setMinHeight(height) ;
    stage.setMaxWidth(width) ;
    stage.setMaxHeight(height) ;

    stage.setTitle(gameTitle) ;

    return ;
  }

}
