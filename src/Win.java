/*
 * Win extends Stages and is used to create a popup window
 * that is displayed when the game is won. The constructor
 * is passed a String containing the winner's name.
 */
import javafx.application.* ;
import javafx.stage.* ;
import javafx.scene.* ;
import javafx.scene.layout.* ;
import javafx.scene.control.* ;
import javafx.event.* ;


class Win extends Stage {
  private Scene winScene ;
  private GridPane winPane ;
  private Label winnerLabel, congrats ;
  private Button endGameButton ;
  private String winner = new String() ;

  // Create the popup window with the winners name.
  Win(String player) {
    if (player != null && !player.isEmpty()) {
      winner = player ;
    }
    else { winner = "Somebody" ; }

    setup_win_popup() ;

    winScene = new Scene(winPane) ;
    setScene(winScene) ;
    setTitle(winner + " Has Won!") ;

    // When closed, finish and close the application.
    setOnCloseRequest(e -> {
      Platform.exit() ;
    }) ;

    show() ;
  }

  // Setup the GridPane used for the winner popup window.
  private void setup_win_popup() {
    winPane = new GridPane() ;

    winnerLabel = new Label(winner + " Has Won!") ;
    congrats = new Label("Congratulations :)") ;
    endGameButton = new Button("Close Game") ;

    define_win_popup_style() ;

    endGameButton.setOnAction(e ->
      end_game_button_click(e)) ;

    return ;
  }

  // Define the layout/style for the winner popup window.
  private void define_win_popup_style() {
    winPane.setStyle("-fx-background-color: #d5ae38 ;") ;

    winPane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.WINCOL1.value)) ;
    winPane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.WINCOL2.value)) ;
    winPane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.WINCOL3.value)) ;

    winPane.getRowConstraints().add(
      new RowConstraints(Symbol.WINROW1.value)) ;
    winPane.getRowConstraints().add(
      new RowConstraints(Symbol.WINROW1.value)) ;
    winPane.getRowConstraints().add(
      new RowConstraints(Symbol.WINROW1.value)) ;
    winPane.getRowConstraints().add(
      new RowConstraints(Symbol.WINROW2.value)) ;
    winPane.getRowConstraints().add(
      new RowConstraints(Symbol.WINROW1.value)) ;
    winPane.getRowConstraints().add(
      new RowConstraints(Symbol.WINROW1.value)) ;

    winPane.add(winnerLabel, 1, 1) ;
    winPane.add(congrats, 1, 2) ;
    winPane.add(endGameButton, 1, 4) ;

    return ;
  }

  // Set the actions taken when "End Game" button pressed.
  private void end_game_button_click(ActionEvent event) {
    Platform.exit() ;

    return ;
  }

}
