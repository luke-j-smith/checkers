/*
 * The Piece class extends Rectangle and is used to create
 * the red and black pieces for each player and is done
 * by loading an image - which image depends on the 'player'
 * input. Like Tile, i and j are used to set the location.
 * Wen a piece reaches the end of the board, it is promoted
 * to a king. This is achieved by changing the image.
 */
import javafx.scene.shape.Rectangle ;
import javafx.scene.paint.* ;
import javafx.scene.image.* ;


class Piece extends Rectangle {
  private int colour, type ;


  Piece(int player, int i, int j) {
    set_size() ;
    set_position(i, j) ;

    switch(player) {
      case 0 :
        Image red = new Image("./images/men_red.png") ;
        setFill(new ImagePattern(red)) ;
        colour = Symbol.RED.value ;
        type = Symbol.MEN.value ;
        break ;
      case 1 :
        Image black = new Image("./images/men_black.png") ;
        setFill(new ImagePattern(black)) ;
        colour = Symbol.BLACK.value ;
        type = Symbol.MEN.value ;
        break ;
      default :
        throw new Error("Unexpected input for colour of " +
          "piece in Piece().") ;
    }

    return ;
  }

  // Define the height and width of a single piece.
  private void set_size() {
    setWidth(Symbol.TILE.value) ;
    setHeight(Symbol.TILE.value) ;

    return ;
  }

  // Define the position of the piece on the board.
  private void set_position(int i, int j) {
    // Symbol.BORDER.value buffs value to create a border.
    setY(Symbol.BORDER.value + (i * Symbol.TILE.value)) ;
    setX(Symbol.BORDER.value + (j * Symbol.TILE.value)) ;

    return ;
  }

  // Used to change the piece to a king - changes image.
  void promote_piece_to_king() {
    if (type != Symbol.MEN.value) { return ; }

    if (colour == Symbol.RED.value) {
      Image rKing = new Image("./images/king_red.png") ;
      setFill(new ImagePattern(rKing)) ;
      type = Symbol.KING.value ;
    }

    else if (colour == Symbol.BLACK.value) {
      Image bKing = new Image("./images/king_black.png") ;
      setFill(new ImagePattern(bKing)) ;
      type = Symbol.KING.value ;
    }

    else {
      throw new Error("Unexpected input for colour of " +
        "piece in Piece promote_piece_to_king().") ;
    }

    return ;
  }

}


