/*
 * Symbol class contains all of the "magic" numbers.
 */
public enum Symbol {

  // Menu window layout:
  MENUCOL(153),
  MENUROWTOP1(128),
  MENUROWTOP2(70),
  MENUROWMIDDLE(50),
  MENUROWBOTTOM2(73),
  MENUROWBOTTOM1(153),
  MENUIMAGE(145),
  MENUTEXTBIG(22),
  MENUTEXTSMALL(16),
  MENUPLAYWIDTH(306),
  MENUPLAYHEIGHT(50),

  // Board tiles:
  LIGHT(0),
  DARK(1),

  // Colour of piece:
  RED(0),
  BLACK(1),
  RED_KING(2),
  BLACK_KING(3),

  // Type of piece:
  MEN(0),
  KING(1),
  STARTING_NUM(24),

  // Size of square in board and border of board:
  TILE(50),
  BORDER(6),

  // Number of squares in width/height of board (ie, 8 x 8):
  SIZE(8),
  BORDERS(2),

  // Paused window layout:
  PAUSECOL1(100),
  PAUSECOL2(125),
  PAUSECOL3(75),
  PAUSEROW1(50),
  PAUSEROW2(25),

  // Win window layout:
  WINCOL1(100),
  WINCOL2(125),
  WINCOL3(75),
  WINROW1(50),
  WINROW2(25),

  // How many squares of size TILE(50) are in the GUI.
  GUI(12),
  GUI_HEIGHT_BUFFER(2),
  GUI_BORDER_BUFF(5),

  // Number of columns and rows in top and bottom panes:
  COLS(3),
  ROWS(3),

  // Number of buttons in bottom right corner.
  BUTS(2),

  // Colour scheme:
  BACKGROUND_R(43),
  BACKGROUND_G(30),
  BACKGROUND_B(22),

  EDGE_R(53),
  EDGE_G(94),
  EDGE_B(59),

  LIGHT_TILE_R(212),
  LIGHT_TILE_G(175),
  LIGHT_TILE_B(129),

  DARK_TILE_R(88),
  DARK_TILE_G(69),
  DARK_TILE_B(44),

  BORDER_R(52),
  BORDER_G(22),
  BORDER_B(0),

  // Animation (ie, mainly rotation) values:
  CLOCK(0),
  ANTICLOCK(1),
  ROTATE180(180),
  TIME(3000),

  // Music:
  REPEAT(2),

  // For the game logic:
  STILLGOING(-1) ;

  int value ;

  private Symbol(int value) {
    this.value = value ;
  }

}

