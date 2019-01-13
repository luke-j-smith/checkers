/*
 * Pause extends Stages and is used to create a popup window
 * that is displayed when the game is paused.
 */
import javafx.application.* ;
import javafx.stage.* ;
import javafx.scene.* ;
import javafx.scene.layout.* ;
import javafx.scene.control.* ;
import javafx.event.* ;


class Pause extends Stage {
  private Scene pausedScene ;
  private GridPane pausedPane ;
  private Label pausedLabel ;
  private Button pausedButton ;


  Pause() {
    setup_paused_popup() ;
    pausedScene = new Scene(pausedPane) ;
    setScene(pausedScene) ;
    setTitle("Game Paused") ;
  }

  // Setup the GridPane used for the paused popup window.
  private void setup_paused_popup() {
    pausedPane = new GridPane() ;

    pausedLabel = new Label("Game is paused!") ;
    pausedButton = new Button("Resume Game") ;

    define_paused_popup_style() ;

    pausedButton.setOnAction(e -> paused_button_click(e)) ;

    return ;
  }

  // Define the GridPane layout/style for the popup window.
  private void define_paused_popup_style() {
    pausedPane.setStyle("-fx-background-color: #5484ad ;") ;

    pausedPane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.PAUSECOL1.value)) ;
    pausedPane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.PAUSECOL2.value)) ;
    pausedPane.getColumnConstraints().add(
      new ColumnConstraints(Symbol.PAUSECOL3.value)) ;

    pausedPane.getRowConstraints().add(
      new RowConstraints(Symbol.PAUSEROW1.value)) ;
    pausedPane.getRowConstraints().add(
      new RowConstraints(Symbol.PAUSEROW1.value)) ;
    pausedPane.getRowConstraints().add(
      new RowConstraints(Symbol.PAUSEROW2.value)) ;
    pausedPane.getRowConstraints().add(
      new RowConstraints(Symbol.PAUSEROW1.value)) ;
    pausedPane.getRowConstraints().add(
      new RowConstraints(Symbol.PAUSEROW1.value)) ;

    pausedPane.add(pausedLabel, 1, 1) ;

    pausedPane.add(pausedButton, 1, 3) ;

    return ;
  }

  // Set the actions taken when resume game button pressed.
  private void paused_button_click(ActionEvent event) {
    close() ;
    return ;
  }

  // Display the paused game popup window.
  void create_paused_popup() {
    show() ;
    return ;
  }

  // Close the paused game popup window.
  void close_paused_popup() {
    close() ;
    return ;
  }

}
