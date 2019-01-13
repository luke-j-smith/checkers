/*
 * The Tile class extends Rectangle and is used to create
 * the light and dark squares that form the playing board.
 * Once created a Tile does not need to be altered. colour
 * determines whether the tile is light or dark, and i and j
 * are integers used to set its location on the board.
 */
import javafx.scene.shape.Rectangle ;
import javafx.scene.paint.* ;
import javafx.scene.control.* ;


class Tile extends Rectangle {


  Tile(int colour, int i, int j) {
    set_size() ;

    set_position(i, j) ;

    if (colour == Symbol.LIGHT.value) {
      setFill(Color.rgb(
        Symbol.LIGHT_TILE_R.value,
        Symbol.LIGHT_TILE_G.value,
        Symbol.LIGHT_TILE_B.value,
      1)) ;
    }

    else if (colour == Symbol.DARK.value) {
      setFill(Color.rgb(
        Symbol.DARK_TILE_R.value,
        Symbol.DARK_TILE_G.value,
        Symbol.DARK_TILE_B.value,
      1)) ;
    }

    else {
      throw new Error("Unexpected input for Tile() colour");
    }

    return ;
  }

  // Define the height and width of a single tile.
  private void set_size() {
    setWidth(Symbol.TILE.value) ;
    setHeight(Symbol.TILE.value) ;

    return ;
  }

  // Define the position of the tile on the board.
  private void set_position(int i, int j) {
    // Symbol.BORDER.value buffs value to create a border.
    setY(Symbol.BORDER.value + (i * Symbol.TILE.value)) ;
    setX(Symbol.BORDER.value + (j * Symbol.TILE.value)) ;

    return ;
  }

}



