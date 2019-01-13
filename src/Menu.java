/*
 * Menu does NOT extend Stage, however is used to create and
 * provide a Stage that is used as the main menu. The
 * menu window allows users to enter their names and the
 * desired time limits. If closed then the game ends.
 */
import javafx.application.* ;
import javafx.stage.* ;
import javafx.scene.* ;
import javafx.scene.layout.* ;
import javafx.scene.control.* ;
import javafx.event.* ;
import javafx.scene.image.* ;
import javafx.scene.text.* ;
import javafx.scene.paint.* ;


class Menu {
  private Stage menuStage = new Stage () ;
  private Scene menuScene ;
  private GridPane menu ;
  private Label player1, player2, playerTime ;
  private TextField name1, name2, time ;
  private Button play ;
  private Image rMen, bMen, rKing, bKing ;
  private String p1Name, p2Name, p1Time, p2Time, gameTime ;
  // The default time limit per player is 10 mins.
  private static final String DEFAULT_TIME_LIMIT = "10:00" ;
  private static final String TITLE = "Checkers" ;


  Menu() {
    setup_menu() ;
    menuScene = new Scene(menu) ;
    menuStage.setScene(menuScene) ;
    menuStage.setTitle(TITLE) ;
  }

  // Set up the stage used for the starting menu.
  private void setup_menu() {
    menu = create_menu_pane() ;

    return ;
  }

  // Build the GridPane used to hold the menu nodes.
  private GridPane create_menu_pane() {
    GridPane pane = new GridPane() ;

    create_menu_nodes() ;

    set_menu_structure(pane) ;

    return pane ;
  }

  // Create all of the nodes used in the starting menu.
  private void create_menu_nodes() {
    setup_menu_text() ;

    setup_menu_textfield() ;

    setup_menu_play_button() ;

    setup_menu_images() ;

    return ;
  }

  // Create the menu text and set the colour, font and size.
  private void setup_menu_text() {
    player1 = new Label("Player One:") ;
    player2 = new Label("Player Two:") ;
    playerTime = new Label("Time Per Player:") ;

    player1.setTextFill(Color.web("#2b1e16")) ;
    player1.setFont(Font.font("Verdana",
      Symbol.MENUTEXTBIG.value)) ;

    player2.setFont(Font.font("Verdana",
      Symbol.MENUTEXTBIG.value)) ;
    player2.setTextFill(Color.web("#2b1e16")) ;

    playerTime.setTextFill(Color.web("#2b1e16")) ;
    playerTime.setFont(Font.font("Verdana",
      Symbol.MENUTEXTSMALL.value)) ;

    return ;
  }

  // Create the textfields and prompts used in the menu.
  private void setup_menu_textfield() {
    name1 = new TextField("") ;
    name2 = new TextField("") ;
    time = new TextField("") ;

    name1.setPromptText("Enter Player 1 Name") ;
    name2.setPromptText("Enter Player 2 Name") ;
    time.setPromptText("Enter Positive Integer") ;

    return ;
  }

  // Create the button used in the menu.
  private void setup_menu_play_button() {
    play = new Button("Play") ;

    play.setMinWidth(Symbol.MENUPLAYWIDTH.value) ;
    play.setMaxWidth(Symbol.MENUPLAYWIDTH.value) ;
    play.setMinHeight(Symbol.MENUPLAYHEIGHT.value) ;
    play.setMaxHeight(Symbol.MENUPLAYHEIGHT.value) ;

    play.setOnAction(e-> play_button_click(e)) ;

    return ;
  }

  // Setup the actions for when play button is pressed.
  private void play_button_click(ActionEvent event) {
    Button button = (Button) event.getSource() ;

    set_player_names() ;

    set_timers() ;

    menuStage.close() ;

    return ;
  }

  // Set the player names as entered in the menu window.
  private void set_player_names() {
    String one = name1.getText() ;
    String two = name2.getText() ;

    if (one.equals("")) { one = "Player One" ; }

    if (two.equals("")) { two = "Player Two" ; }

    p1Name = one ;
    p2Name = two ;

    return ;
  }

  // Set the game timer and individual player timers.
  private void set_timers() {
    set_game_timer() ;

    set_player_timers() ;

    return ;
  }

  // PLACE HOLDER - Set the game timer, starting from 00:00.
  private void set_game_timer() {
    gameTime = "00:00" ;

    return ;
  }
  // PLACE HOLDER - Set the player timers from menu time.
  private void set_player_timers() {
    String input = time.getText() ;

    if (string_is_positive_integer(input)) {
      p1Time = p2Time = input + ":00" ;
    }
    // If time entered in not an int. set limit to default.
    else { p1Time = p2Time = DEFAULT_TIME_LIMIT ; }

    return ;
  }

  // Check to see if the time entered in menu is an integer.
  private boolean string_is_positive_integer(String input) {
    int ints = 0, length = input.length() ;

    if (length == 0) { return false ; }

    for (int i = 0 ; i < length ; i++) {
      if (Character.isDigit(input.charAt(i))) { ints++ ; }
    }

    if (length != ints) { return false ; }

    return true ;
  }

  // Create the button used in the menu.
  private void setup_menu_images() {
    rMen = new Image("./images/men_red.png",
      Symbol.MENUIMAGE.value, Symbol.MENUIMAGE.value, false,
      false) ;
    bMen = new Image("./images/men_black.png",
      Symbol.MENUIMAGE.value, Symbol.MENUIMAGE.value, false,
      false) ;
    rKing = new Image("./images/king_red.png",
      Symbol.MENUIMAGE.value, Symbol.MENUIMAGE.value, false,
      false) ;
    bKing = new Image("./images/king_black.png",
      Symbol.MENUIMAGE.value, Symbol.MENUIMAGE.value, false,
      false) ;

    return ;
  }

  // Define the structure of the menu window.
  private void set_menu_structure(GridPane pane) {
    pane.setStyle("-fx-background-color: #355e3b ;") ;

    define_menu_rows_and_columns(pane) ;

    pane.add(new ImageView(rKing), 0, 0) ;
    pane.add(new ImageView(bKing), 3, 0) ;
    pane.add(player1, 1, 2) ;
    pane.add(player2, 1, 3) ;
    pane.add(playerTime, 1, 4) ;
    pane.add(name1, 2, 2) ;
    pane.add(name2, 2, 3) ;
    pane.add(time, 2, 4) ;
    pane.add(play, 1, 5) ;
    pane.add(new ImageView(bMen), 0, 7) ;
    pane.add(new ImageView(rMen), 3, 7) ;

    return ;
  }

  // Define the number of rows and columns in menu window.
  private void define_menu_rows_and_columns(GridPane pane) {
    pane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.MENUCOL.value)) ;
    pane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.MENUCOL.value)) ;
    pane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.MENUCOL.value)) ;
    pane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.MENUCOL.value)) ;

    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWTOP1.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWTOP2.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWMIDDLE.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWMIDDLE.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWMIDDLE.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWMIDDLE.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWBOTTOM2.value)) ;
    pane.getRowConstraints().add(
      new RowConstraints(Symbol.MENUROWBOTTOM1.value)) ;

    return ;
  }

  // Provide the user entries as string for the game window.
  String[] get_user_entries() {
    String entries[] = {p1Name, p2Name, p1Time, p2Time,
      gameTime} ;
    String entriesCopy[] = entries ;

    return entriesCopy ;
  }

  // Provide the main menu Stage created in the class.
  Stage display_menu() {
    Stage menuCopy = menuStage ;

    return menuCopy ;
  }

}
