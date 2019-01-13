/*
 * Checkers handles all of the logic used in the program.
 * In state[][] red pieces are represented by 1 (and 2 for
 * kings) and black pieces by -1 (and -2 for kings).
 * All testing is at the bottom.
 */
import java.io.* ;


class Checkers {
  private int state[][] =
    new int[Symbol.SIZE.value][Symbol.SIZE.value] ;
  private int turn ;
  private String scores[] = new String[2] ;
  private boolean pieceTaken ;
  private int[] lastTaken = new int[2] ;


  Checkers() {
    setup_initial_board() ;
    turn = 0 ;
    scores[0] = scores[1] = "0" ;
    pieceTaken = false ;
    lastTaken[0] = lastTaken[1] = -1 ;
  }

  // Define the initial state of the board for the game.
  private void setup_initial_board() {
    set_board_to_be_empty(state) ;

    setup_starting_black_side() ;

    setup_starting_red_side() ;

    return ;
  }

  // Set an 8x8 int array to be filled with 0 (ie, empty).
  private void set_board_to_be_empty(int board[][]) {
    for (int i = 0 ; i < Symbol.SIZE.value ; i++) {
      for (int j = 0 ; j < Symbol.SIZE.value ; j++) {
        board[i][j] = 0 ;
      }
    }

    return ;
  }

  // Set up the starting black positions (ie, as -1).
  private void setup_starting_black_side() {
    int blackRows[] = {0, 1, 2} ;

    for (int i : blackRows) {
      for (int j = 0 ; j < Symbol.SIZE.value ; j++) {
        if ((i % 2) == 0) {
          if ((j % 2) == 1) { state[i][j] = -1 ; }
        }
        else {
          if ((j % 2) == 0) { state[i][j] = -1 ; }
        }
      }
    }

    return ;
  }

  // Set up the starting red positions (ie, as 1).
  private void setup_starting_red_side() {
    int redRows[] = {5, 6, 7} ;

    for (int i : redRows) {
      for (int j = 0 ; j < Symbol.SIZE.value ; j++) {
        if ((i % 2) == 0) {
          if ((j % 2) == 1) { state[i][j] = 1 ; }
        }
        else {
          if ((j % 2) == 0) { state[i][j] = 1 ; }
        }
      }
    }

    return ;
  }

  // When a turn is over, increase the turn counter.
  private void players_turn_over() {
    turn++ ;

    return ;
  }

  // Based on the turn counter, determine which players turn
  private int get_which_players_turn() {
    if ((turn % 2) == 0) { return 0 ; }

    return 1 ;
  }

  /*
   * For a piece moved from a position to a new one,
   * determine if that move was valid in the current state.
   */
  boolean make_move(int from[], int to[]) {
    if (check_correct_player_moved(from) == false) {
      return false ;
    }

    int moves[][] = possible_moves(from) ;

    if (moves[to[1]][to[0]] == 1) {
      update_board_state(from, to) ;

      players_turn_over() ;

      return true ;
    }

    return false ;
  }

  // Using turn and colour of piece, check if correct player
  private boolean check_correct_player_moved(int from[]) {
    if (get_which_players_turn() == 0) {
      if (state[from[1]][from[0]] <= 0) { return false ; }
    }

    else if (get_which_players_turn() == 1) {
      if (state[from[1]][from[0]] >= 0) { return false ; }
    }

    else {
      throw new Error("Unexpected output from " +
        "get_which_players_turn().") ;
    }

    return true ;
  }

  // For a moved piece, determine all possible new positions
  private int[][] possible_moves(int from[]) {
    int[][] moves =
      new int[Symbol.SIZE.value][Symbol.SIZE.value] ;
    int row = from[1], col = from[0] ;
    int direction = get_move_search_direction() ;

    set_board_to_be_empty(moves) ;

    switch(check_peice_type(row, col)) {
      case 0 :
        check_moves_for_men(row, col, direction, moves) ;
        break ;
      case 1 :
        check_moves_for_king(row, col, moves) ;
        break ;
      default : throw new Error("Unexpected return value " +
        "from check_peice_type().") ;
    }

    return moves ;
  }

  // Determine which direction the piece should be moving.
  private int get_move_search_direction() {
    int direction ;

    switch(get_which_players_turn()) {
      case 0 : direction = -1 ; break ;
      case 1 : direction = 1 ; break ;
      default : throw new Error("Unexpected return value " +
        "from get_which_players_turn().") ;
    }

    return direction ;
  }

  // Determine if the piece is a man or a king.
  private int check_peice_type(int row, int col) {
    if (Math.abs(state[row][col]) == 1) {
      return Symbol.MEN.value ;
    }

    else if (Math.abs(state[row][col]) == 2) {
      return Symbol.KING.value ;
    }

    else {
      throw new Error("Cannot move from an empty square.") ;
    }
  }

  /*
   * If the piece is a man, then check the possible moves
   * in the the appropriate direction.
   */
  private void check_moves_for_men(int row, int col,
  int direction, int[][] moves) {
    check_for_passive_diagonal(row, col, direction, moves) ;

    check_for_attacking_move(row, col, direction, moves) ;
    return ;
  }

  // Check for any normal 'moving' moves.
  private void check_for_passive_diagonal(int row, int col,
  int direction, int[][] moves) {
    int nextRow = row + direction ;

    if (row_out_of_bounds(nextRow)) { return ; }

    if (left_empty(nextRow, col)) {
      moves[nextRow][(col - 1)] = 1 ;
    }

    if (right_empty(nextRow, col)) {
      moves[nextRow][(col + 1)] = 1 ;
    }

    return ;
  }

  // Check to see if the row being check is on the board.
  private boolean row_out_of_bounds(int row) {
    if ((row < 0) || (row > 7)) { return true ; }

    return false ;
  }

  // Check to see if square in the left column is empty.
  private boolean left_empty(int nextRow, int col) {
    if (((col - 1) >= 0) &&
    (square_empty(nextRow, (col - 1)))) {
      return true ;
    }

    return false ;
  }

  // Check to see if square in the right column is empty.
  private boolean right_empty(int nextRow, int col) {
    if (((col + 1) <= 7) &&
    (square_empty(nextRow, (col + 1)))) {
      return true ;
    }

    return false ;
  }

  // Check to see if a given square is empty.
  private boolean square_empty(int row, int col) {
    if (state[row][col] == 0) { return true ; }

    return false ;
  }

  // Check for any attacking (ie, tacking) moves.
  private void check_for_attacking_move(int row, int col,
  int direction, int[][] moves) {
    int nextRow = row + direction ;

    if (row_out_of_bounds(nextRow)) { return ; }

    if (opponent_in_left(nextRow, col)) {
      check_for_passive_diagonal(nextRow, (col - 1),
        direction, moves) ;
    }

    if (opponent_in_right(nextRow, col)) {
      check_for_passive_diagonal(nextRow, (col + 1),
        direction, moves) ;
    }

    return ;
  }

  // Check if opponent in the square in the left column.
  private boolean opponent_in_left(int nextRow, int col) {
    if (((col - 1) >= 0) &&
    (opponent_in_square(nextRow, (col - 1)))) {
      return true ;
    }

    return false ;
  }

  // Check if opponent in the square in the right column.
  private boolean opponent_in_right(int nextRow, int col) {
    if (((col + 1) <= 7) &&
    (opponent_in_square(nextRow, (col + 1)))) {
      return true ;
    }

    return false ;
  }

  // Check to see if a given square has opponent in.
  private boolean opponent_in_square(int row, int col) {
    if (get_which_players_turn() == 0) {
      if (state[row][col] < 0) {
        return true ;
      }
    }

    else if (get_which_players_turn() == 1) {
      if (state[row][col] > 0) {
        return true ;
      }
    }

    else {
      throw new Error("Unexpected return value from " +
        "get_which_players_turn().") ;
    }

    return false ;
  }

   // If the piece is a king, then check the possible moves
  private void check_moves_for_king(int row, int col,
  int[][] moves) {
    check_for_passive_diagonal(row, col, 1, moves) ;
    check_for_passive_diagonal(row, col, -1, moves) ;

    check_for_attacking_move(row, col, 1, moves) ;
    check_for_attacking_move(row, col, -1, moves) ;

    return ;
  }

  // For a valid move, update the state of the board.
  private void update_board_state(int from[], int to[]) {
    int oldRow = from[1], oldCol = from[0] ;
    int newRow = to[1], newCol = to[0] ;
    int changeY = oldRow - newRow ;
    int changeX = oldCol - newCol ;
    double moveDistance = Math.sqrt((Math.pow(changeX, 2) +
      Math.pow(changeY, 2))) ;

    state[newRow][newCol] = state[oldRow][oldCol] ;
    state[oldRow][oldCol] = 0 ;

    // Nb. sqrt(1^2 + 1^2) ~ 1.42
    if (moveDistance > 1.42) {
      update_game_for_take(oldRow, oldCol, changeY,
        changeX) ;
    }

    return ;
  }

  // If move is a take, update the state accordingly.
  private void update_game_for_take(int oldRow, int oldCol,
  int changeY, int changeX) {
    int takenRow = oldRow - (changeY / 2) ;
    int takenCol = oldCol - (changeX / 2) ;

    if (state[takenRow][takenCol] == -1) {
      update_player_score(Symbol.RED.value, 1) ;
    }
    else if (state[takenRow][takenCol] == -2) {
      update_player_score(Symbol.RED.value, 2) ;
    }
    else if (state[takenRow][takenCol] == 1) {
      update_player_score(Symbol.BLACK.value, 1) ;
    }
    else if (state[takenRow][takenCol] == 2) {
      update_player_score(Symbol.BLACK.value, 2) ;
    }

    state[takenRow][takenCol] = 0 ;

    pieceTaken = true ;
    lastTaken[1] = takenRow ;
    lastTaken[0] = takenCol ;

    return ;
  }

  // When a piece is taken, update the score.
  private void update_player_score(int player, int value) {
    if ((player != Symbol.RED.value) &&
    (player > Symbol.BLACK.value)) {
      throw new Error("Unexpected input for player type " +
        "in update_player_score().") ;
    }

    int update = Integer.parseInt(scores[player]) + value ;

    scores[player] = Integer.toString(update) ;

    return ;
  }

  // Return 2 strings containing each of the players scores.
  String get_player_score(int player) {
    if ((player != Symbol.RED.value) &&
    (player > Symbol.BLACK.value)) {
      throw new Error("Unexpected input for player type " +
        "in get_player_score().") ;
      }

    String scoreCopy = scores[player] ;

    return scoreCopy ;
  }

  // Check whether a piece was taken in the last move.
  boolean piece_was_taken() {
    boolean wasTaken ;

    wasTaken = pieceTaken ;

    pieceTaken = false ;

    return wasTaken ;
  }

  // If a piece was taken, get the taken pieces position.
  int[] get_last_taken() {
    int taken[] = new int[2] ;
    taken[0] = lastTaken[0] ;
    taken[1] = lastTaken[1] ;

    lastTaken[0] = lastTaken[1] = -1 ;

    return taken ;
  }

  // Check to see if a player has won (ie, no pieces left).
  int check_if_player_has_won() {
    int redLeft = 0, blackLeft = 0 ;

    for (int i = 0 ; i < 8 ; i++) {
      for (int j = 0 ; j < 8 ; j++) {
        if ((state[i][j] == 1) || (state[i][j] == 2)) {
          redLeft++ ;
        }
        if ((state[i][j] == -1) || (state[i][j] == -2)) {
          blackLeft++ ;
        }
      }
    }

    if (blackLeft == 0) { return Symbol.RED.value ; }

    if (redLeft == 0) { return Symbol.BLACK.value ; }

    return Symbol.STILLGOING.value ;
  }

  // Check to see if a piece should be promoted to king.
  boolean check_if_becomes_king(int row, int col) {
    if ((state[row][col] == 1) && (row == 0)) {
      state[row][col] = 2 ;
      return true ;
    }

    if ((state[row][col] == -1) && (row == 7)) {
      state[row][col] = -2 ;
      return true ;
    }

    return false ;
  }

  public static void main(String[] args) {
    boolean testing = false ;
    assert (testing = true) ;
    Checkers program = new Checkers() ;
    if (testing) {
      program.tests() ;
    }
  }

  // All testing.
  private void tests() {
    test_set_board_to_be_empty() ;
    test_setup_initial_board() ;
    test_player_turn_over() ;
    test_get_which_players_turn() ;
    test_check_correct_player_moved() ;
    test_get_move_search_direction() ;
    test_check_peice_type() ;
    test_row_out_of_bounds() ;
    test_square_empty() ;
    test_left_empty() ;
    test_right_empty() ;
    test_check_for_passive_diagonal() ;
    test_opponent_in_square() ;
    test_opponent_in_left() ;
    test_opponent_in_right() ;
    test_update_player_score() ;
    test_get_player_score() ;
    test_piece_was_taken() ;
    test_get_last_taken() ;
    test_check_if_player_has_won() ;
    test_check_if_becomes_king() ;

    return ;
  }

  // Facilitates single line testing.
  private int op(String in) {
    String[] words = in.split(" ") ;

    if (words[0].equals("get_which_players_turn")) {
      turn = Integer.parseInt(words[1]) ;
      int player = get_which_players_turn() ;
      turn = 0 ;
      return player ;
    }

    else if (words[0].equals("check_correct_player_moved")){
      int from[] = new int[2] ;
      from[0] = Integer.parseInt(words[1]) ;
      from[1] = Integer.parseInt(words[2]) ;
      turn = Integer.parseInt(words[3]) ;
      if (check_correct_player_moved(from) == true) {
        turn = 0 ;
        return 1 ;
      }
      turn = 0 ;
      return 0 ;
    }

    else if (words[0].equals("check_peice_type")) {
      int row = Integer.parseInt(words[1]) ;
      int col = Integer.parseInt(words[2]) ;
      return check_peice_type(row, col) ;
    }

    else if (words[0].equals("get_move_search_direction")) {
      turn = Integer.parseInt(words[1]) ;
      int direction = get_move_search_direction() ;
      turn = 0 ;
      return direction ;
    }

    else if (words[0].equals("check_for_passive_diagonal")){
      int moves[][] = new int[8][8] ;
      set_board_to_be_empty(moves) ;
      int row = Integer.parseInt(words[1]) ;
      int col = Integer.parseInt(words[2]) ;
      int direction = Integer.parseInt(words[3]) ;
      int m1Row = Integer.parseInt(words[4]) ;
      int m1Col = Integer.parseInt(words[5]) ;
      int m1 = Integer.parseInt(words[6]) ;
      int m2Row = Integer.parseInt(words[7]) ;
      int m2Col = Integer.parseInt(words[8]) ;
      int m2 = Integer.parseInt(words[9]) ;
      int count = 0 ;

      check_for_passive_diagonal(row, col, direction,
        moves);

      for (int i = 0 ; i < 8 ; i++) {
        for (int j = 0 ; j < 8 ; j++) {
          if ((i == m1Row) && (j == m1Col)) {
            if (moves[i][j] == m1) { count++ ; }
          }
          else if ((i == m2Row) && (j == m2Col)) {
            if (moves[i][j] == m2) { count++ ; }
          }
          else {
            if (moves[i][j] == 0) { count++ ; }
          }
        }
      }

      if (count != (8 * 8)) { return 0 ; }

      return 1 ;
    }

    else if (words[0].equals("opponent_in_square")) {
      turn = Integer.parseInt(words[1]) ;
      int row = Integer.parseInt(words[2]) ;
      int col = Integer.parseInt(words[3]) ;
      int returnValue = 0 ;

      if (opponent_in_square(row, col) == true) {
        returnValue = 1 ;
      }

      turn = 0 ;

      return returnValue ;
    }

    else if (words[0].equals("opponent_in_left")) {
      turn = Integer.parseInt(words[1]) ;
      int row = Integer.parseInt(words[2]) ;
      int col = Integer.parseInt(words[3]) ;
      int returnValue = 0 ;

      if (opponent_in_left(row, col) == true) {
        returnValue = 1 ;
      }

      turn = 0 ;

      return returnValue ;
    }

    else if (words[0].equals("opponent_in_right")) {
      turn = Integer.parseInt(words[1]) ;
      int row = Integer.parseInt(words[2]) ;
      int col = Integer.parseInt(words[3]) ;
      int returnValue = 0 ;

      if (opponent_in_right(row, col) == true) {
        returnValue = 1 ;
      }

      turn = 0 ;

      return returnValue ;
    }

    else if (words[0].equals("check_if_player_has_won")) {
      int startingState[][] = new int[8][8] ;
      startingState = state ;
      int rowStart = Integer.parseInt(words[1]) ;
      int rowEnd = Integer.parseInt(words[2]) ;

      for (int i = rowStart ; i < rowEnd + 1 ; i++) {
        for (int j = 0 ; j < 8 ; j++) {
          state[i][j] = 0 ;
        }
      }

      int returnValue = check_if_player_has_won() ;
      state = startingState ;

      return returnValue ;
    }

    else if (words[0].equals("check_if_becomes_king")) {
      int startingState[][] = new int[8][8] ;
      startingState = state ;
      int row = Integer.parseInt(words[1]) ;
      int col = Integer.parseInt(words[2]) ;
      int oldPiece = Integer.parseInt(words[3]) ;
      int newPiece = Integer.parseInt(words[4]) ;
      state[row][col] = oldPiece ;
      int returnValue = 0 ;

      if (check_if_becomes_king(row, col) == true) {
        if (state[row][col] == newPiece) {returnValue = 1 ;}
      }

      state = startingState ;

      return returnValue ;
    }

    else if (words[0].equals("update_player_score")) {
      int player = Integer.parseInt(words[1]) ;
      int value = Integer.parseInt(words[2]) ;
      int returnValue = 0 ;

      update_player_score(player, value) ;

      if (scores[player].equals(words[2])) {
        returnValue = 1 ;
      }

      scores[0] = scores[1] = "0" ;

      return returnValue ;
    }

    else if (words[0].equals("get_player_score")) {
      int player = Integer.parseInt(words[1]) ;
      scores[player] = words[2] ;
      int returnValue = 0 ;

      if (scores[player].equals(get_player_score(player))) {
        returnValue = 1 ;
      }

      scores[0] = scores[1] = "0" ;

      return returnValue ;
    }

    else if (words[0].equals("get_last_taken")) {
      lastTaken[0] = Integer.parseInt(words[1]) ;
      lastTaken[1] = Integer.parseInt(words[2]) ;
      int taken0 = lastTaken[0], taken1 = lastTaken[1] ;
      int taken[] = get_last_taken() ;
      int returnValue = 0 ;

      if ((taken0 == taken[0]) && (taken1 == taken[1])
      && (lastTaken[0] == -1) && (lastTaken[1] == -1)) {
        returnValue = 1 ;
      }

      return returnValue ;
    }

    return -1 ;
  }

  private void test_set_board_to_be_empty() {
    int empty[][] = new int[8][8] ;

    set_board_to_be_empty(empty) ;

    for (int i = 0 ; i < 8 ; i++) {
      for (int j = 0 ; j < 8 ; j++) {
        assert(empty[i][j] == 0) ;
      }
    }

    return ;
  }

  private void test_setup_initial_board() {
    assert(state[0][0] == 0) ;
    assert(state[0][1] == -1) ;
    assert(state[0][2] == 0) ;
    assert(state[0][3] == -1) ;
    assert(state[0][4] == 0) ;
    assert(state[0][5] == -1) ;
    assert(state[0][6] == 0) ;
    assert(state[0][7] == -1) ;

    assert(state[1][0] == -1) ;
    assert(state[1][1] == 0) ;
    assert(state[1][2] == -1) ;
    assert(state[1][3] == 0) ;
    assert(state[1][4] == -1) ;
    assert(state[1][5] == 0) ;
    assert(state[1][6] == -1) ;
    assert(state[1][7] == 0) ;

    assert(state[2][0] == 0) ;
    assert(state[2][1] == -1) ;
    assert(state[2][2] == 0) ;
    assert(state[2][3] == -1) ;
    assert(state[2][4] == 0) ;
    assert(state[2][5] == -1) ;
    assert(state[2][6] == 0) ;
    assert(state[2][7] == -1) ;

    for (int i = 3 ; i < 5 ; i++) {
      for (int j = 0 ; j < 8 ; j++) {
        assert(state[i][j] == 0) ;
      }
    }

    assert(state[5][0] == 1) ;
    assert(state[5][1] == 0) ;
    assert(state[5][2] == 1) ;
    assert(state[5][3] == 0) ;
    assert(state[5][4] == 1) ;
    assert(state[5][5] == 0) ;
    assert(state[5][6] == 1) ;
    assert(state[5][7] == 0) ;

    assert(state[6][0] == 0) ;
    assert(state[6][1] == 1) ;
    assert(state[6][2] == 0) ;
    assert(state[6][3] == 1) ;
    assert(state[6][4] == 0) ;
    assert(state[6][5] == 1) ;
    assert(state[6][6] == 0) ;
    assert(state[6][7] == 1) ;

    assert(state[7][0] == 1) ;
    assert(state[7][1] == 0) ;
    assert(state[7][2] == 1) ;
    assert(state[7][3] == 0) ;
    assert(state[7][4] == 1) ;
    assert(state[7][5] == 0) ;
    assert(state[7][6] == 1) ;
    assert(state[7][7] == 0) ;

    return ;
  }

  private void test_player_turn_over() {
    assert(turn == 0) ;
    players_turn_over() ;
    assert(turn == 1) ;
    players_turn_over() ;
    assert(turn == 2) ;
    players_turn_over() ;
    assert(turn == 3) ;

    turn = 0 ;
    assert(turn == 0) ;

    return ;
  }

  private void test_get_which_players_turn() {
    assert(op("get_which_players_turn 0") == 0) ;
    assert(op("get_which_players_turn 1") == 1) ;
    assert(op("get_which_players_turn 2") == 0) ;
    assert(op("get_which_players_turn 3") == 1) ;
    assert(op("get_which_players_turn 4") == 0) ;
    assert(op("get_which_players_turn 5") == 1) ;

    return ;
  }

  private void test_check_correct_player_moved() {
    assert(op("check_correct_player_moved 1 0 0") == 0) ;
    assert(op("check_correct_player_moved 0 5 0") == 1) ;

    assert(op("check_correct_player_moved 1 0 1") == 1) ;
    assert(op("check_correct_player_moved 0 5 1") == 0) ;

    assert(op("check_correct_player_moved 0 0 2") == 0) ;
    assert(op("check_correct_player_moved 7 7 3") == 0) ;

    assert(op("check_correct_player_moved 7 6 2") == 1) ;
    assert(op("check_correct_player_moved 3 2 3") == 1) ;

    return ;
  }

  private void test_get_move_search_direction() {
    assert(op("get_move_search_direction 0") == -1) ;
    assert(op("get_move_search_direction 1") == 1) ;
    assert(op("get_move_search_direction 2") == -1) ;
    assert(op("get_move_search_direction 3") == 1) ;

    return ;
  }

  private void test_check_peice_type() {
    assert(op("check_peice_type 0 1") == Symbol.MEN.value) ;
    assert(op("check_peice_type 5 0") == Symbol.MEN.value) ;
    assert(op("check_peice_type 6 7") == Symbol.MEN.value) ;
    assert(op("check_peice_type 2 3") == Symbol.MEN.value) ;

    state[0][0] = -2 ; state[7][7] = 2 ;
    assert(op("check_peice_type 0 0") == Symbol.KING.value);
    assert(op("check_peice_type 7 7") == Symbol.KING.value);
    state[0][0] = 0 ; state[7][7] = 0 ;

    return ;
  }

  private void test_row_out_of_bounds() {
    assert(row_out_of_bounds(-1) == true) ;
    assert(row_out_of_bounds(0) == false) ;
    assert(row_out_of_bounds(1) == false) ;
    assert(row_out_of_bounds(2) == false) ;
    assert(row_out_of_bounds(3) == false) ;
    assert(row_out_of_bounds(4) == false) ;
    assert(row_out_of_bounds(5) == false) ;
    assert(row_out_of_bounds(6) == false) ;
    assert(row_out_of_bounds(7) == false) ;
    assert(row_out_of_bounds(8) == true) ;

    return ;
  }

  private void test_square_empty() {
    assert(square_empty(0, 0) == true) ;
    assert(square_empty(0, 1) == false) ;
    assert(square_empty(0, 2) == true) ;
    assert(square_empty(0, 3) == false) ;
    assert(square_empty(0, 4) == true) ;
    assert(square_empty(0, 5) == false) ;
    assert(square_empty(0, 6) == true) ;
    assert(square_empty(0, 7) == false) ;

    assert(square_empty(7, 0) == false) ;
    assert(square_empty(7, 1) == true) ;
    assert(square_empty(7, 2) == false) ;
    assert(square_empty(7, 3) == true) ;
    assert(square_empty(7, 4) == false) ;
    assert(square_empty(7, 5) == true) ;
    assert(square_empty(7, 6) == false) ;
    assert(square_empty(7, 7) == true) ;

    return ;
  }

  private void test_left_empty() {
    assert(left_empty(1, 1) == false) ;
    assert(left_empty(3, 1) == true) ;
    assert(left_empty(3, 7) == true) ;
    assert(left_empty(1, 7) == false) ;

    assert(left_empty(4, 2) == true) ;
    assert(left_empty(5, 5) == false) ;
    assert(left_empty(6, 6) == false) ;
    assert(left_empty(4, 6) == true) ;

    return ;
  }

  private void test_right_empty() {
    assert(right_empty(4, 6) == true) ;
    assert(right_empty(5, 5) == false) ;
    assert(right_empty(5, 1) == false) ;
    assert(right_empty(4, 2) == true) ;

    assert(right_empty(1, 0) == true) ;
    assert(right_empty(2, 0) == false) ;
    assert(right_empty(2, 6) == false) ;
    assert(right_empty(1, 6) == true) ;

    return ;
  }

  private void test_check_for_passive_diagonal() {
    assert(op("check_for_passive_diagonal " +
    "5 0 -1 4 1 1 4 0 0") == 1) ;
    assert(op("check_for_passive_diagonal " +
    "6 3 -1 5 2 0 5 4 0") == 1) ;
    assert(op("check_for_passive_diagonal " +
    "5 6 -1 4 5 1 4 7 1") == 1) ;
    assert(op("check_for_passive_diagonal " +
    "5 4 -1 4 3 1 4 5 1") == 1) ;


    assert(op("check_for_passive_diagonal " +
    "0 3 1 1 2 0 1 4 0") == 1) ;
    assert(op("check_for_passive_diagonal " +
    "1 6 1 2 5 0 2 7 0") == 1) ;
    assert(op("check_for_passive_diagonal " +
    "2 1 1 3 0 1 3 2 1") == 1) ;
    assert(op("check_for_passive_diagonal " +
    "2 5 1 3 4 1 3 6 1") == 1) ;

    return ;
  }

  private void test_opponent_in_square() {
    assert(op("opponent_in_square 0 0 1") == 1) ;
    assert(op("opponent_in_square 1 0 1") == 0) ;

    assert(op("opponent_in_square 0 0 0") == 0) ;
    assert(op("opponent_in_square 1 0 0") == 0) ;

    assert(op("opponent_in_square 2 6 7") == 0) ;
    assert(op("opponent_in_square 1 6 7") == 1) ;

    return ;
  }

  private void test_opponent_in_left() {
    assert(op("opponent_in_left 0 0 2") == 1) ;
    assert(op("opponent_in_left 1 5 5") == 1) ;
    assert(op("opponent_in_left 0 3 3") == 0) ;
    assert(op("opponent_in_left 1 4 3") == 0) ;
    assert(op("opponent_in_left 2 5 7") == 0) ;
    assert(op("opponent_in_left 3 0 2") == 0) ;

    return ;
  }

  private void test_opponent_in_right() {
    assert(op("opponent_in_right 0 0 0") == 1) ;
    assert(op("opponent_in_right 1 5 5") == 1) ;
    assert(op("opponent_in_right 0 3 3") == 0) ;
    assert(op("opponent_in_right 1 4 3") == 0) ;
    assert(op("opponent_in_right 2 5 5") == 0) ;
    assert(op("opponent_in_right 3 0 0") == 0) ;

    return ;
  }

  private void test_update_player_score() {
    assert(op("update_player_score 0 1") == 1) ;
    assert(op("update_player_score 0 2") == 1) ;
    assert(op("update_player_score 1 1") == 1) ;
    assert(op("update_player_score 1 2") == 1) ;

    return ;
  }

  private void test_get_player_score() {
    assert(op("get_player_score 0 1") == 1) ;
    assert(op("get_player_score 0 2") == 1) ;
    assert(op("get_player_score 1 1") == 1) ;
    assert(op("get_player_score 1 2") == 1) ;

    return ;
  }

  private void test_piece_was_taken() {
    assert(pieceTaken == false) ;

    return ;
  }

  private void test_get_last_taken() {
    assert(op("get_last_taken 0 0") == 1) ;
    assert(op("get_last_taken 5 4") == 1) ;
    assert(op("get_last_taken 2 6") == 1) ;
    assert(op("get_last_taken 7 1") == 1) ;

    return ;
  }

  private void test_check_if_player_has_won() {
    assert(op("check_if_player_has_won 0 0") == -1) ;
    assert(op("check_if_player_has_won 5 7") == 1) ;
    assert(op("check_if_player_has_won 0 2") == 0) ;

    return ;
  }

  private void test_check_if_becomes_king() {
    assert(op("check_if_becomes_king 0 0 1 2") == 1) ;
    assert(op("check_if_becomes_king 1 0 1 2") == 0) ;
    assert(op("check_if_becomes_king 0 0 -1 -2") == 0) ;
    assert(op("check_if_becomes_king 7 0 -1 -2") == 1) ;
    assert(op("check_if_becomes_king 6 0 -1 -2") == 0) ;
    assert(op("check_if_becomes_king 7 0 1 2") == 0) ;

    return ;
  }

}

