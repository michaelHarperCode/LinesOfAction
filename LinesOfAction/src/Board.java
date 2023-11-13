//Michael Harper, Kyle Monteleone, Nuzhat Hoque
public class Board {

    private char[][] Board;
    private static Location[] Compass = {
            new Location(1, 0), //N 0,-1
            new Location(1, 1), //NE 1,-1
            new Location(0, 1), //E 1, 0
            new Location(-1, 1), //SE
            new Location(-1, 0), // S 0 1
            new Location(-1, -1), //SW -1 1
            new Location(0, -1), //W -1 0
            new Location(1, -1), //NW
    };
    private boolean[][] legalMoves;
    private char player = 'b';

    /**
     * Initializes the Board to an 8 x 8 checkerboard, with characters that define each checkers position on the board.
     * For the initial configuration of the game.
     */
    public Board() {
        Board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i >= 1 && i <= 6) && (j == 0 || j == 7)) {
                    Board[i][j] = 'b'; //black colored pieces
                } else if ((i == 0 || i == 7) && (j >= 1 && j <= 6)) {
                    Board[i][j] = 'w'; //white colored pieces
                } else {
                    Board[i][j] = 'n'; //blank squares on Board
                }
            }

        }
        legalMoves = new boolean[8][8];
    }

    /**
     * Initializes the Board for a given configuration. (for testing)
     * @param setup the String that determines the configuration
     * @param row The length of the board
     * @param col The width of the board
     */
    public Board(String setup, int row, int col){
        Board = new char[row][col];
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Board[i][j] = setup.charAt(k);
                k++;
            }
        }
    }

    /**
     * Returns the char representing the color of a checker.
     * @param x the Row
     * @param y the Column
     * @return the char
     */
    public char getColor(int x, int y) {
        return Board[x][y];
    }

    /**
     * Returns the current player color
     * @return the player color, as a char
     */
    public char getPlayer() { return player;}

    /**
     * Returns legal moves for a given location.
     * @param x the Row
     * @param y the Column
     * @return the Legal and Illegal Moves for the location, as a boolean.
     */
    public boolean getLegalMoves(int x, int y) {return legalMoves[x][y];}

    /**
     * Changes the player color after turns.
     */
    public void switchColor(){
        if(player == 'b'){
            player = 'w';
        } else{
            player = 'b';
        }
    }

    /**
     * Makes all positions in legalMoves false.
     */
    public void clearLegalMoves(){
        legalMoves = new boolean[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                legalMoves[i][j] = false;
            }
        }
    }

    /**
     * Returns a count of all pieces in all Lines of Action
     * @param currentPosition The selected piece
     * @param direction One of 8 cardinal directions
     * @return piece count
     */
    public int countAllPiecesInLine(Location currentPosition, Location direction) {
        int counter = 1;
        Location temp = currentPosition;
        Location temp2 = currentPosition;
        while (temp.isValid()) { //going in one direction
            //as long as its a piece count it
            if (Board[temp.row][temp.col] != 'n' && temp != currentPosition) {
                counter++;
            }
            temp = temp.add(direction);
        }
        while (temp2.isValid()) { //going in opposite direction
            if (Board[temp2.row][temp2.col] != 'n' && temp2 != currentPosition) {
                counter++;
            }
            temp2 = temp2.sub(direction);
        }
        return counter;

    }

    /**
     * Sets a position to true in legalMoves if the move is valid in a given direction.
     * @param currentPosition The position of the selected piece
     * @param direction one of eight cardinal directions
     */
    public void computeValid(Location currentPosition, Location direction){
        int counter =  countAllPiecesInLine(currentPosition, direction);
        int i = 0;
        Location temp = currentPosition;
        while(i < counter){
            //If off the board OR if the board piece at that location is opposite player and not blank
            if(!temp.isValid() || Board[temp.row][temp.col] != 'n' && Board[temp.row][temp.col] != player) {
                return;
            }
            temp = temp.add(direction);
            i++;
        }
        //If on the board AND not landing on own piece
        if (temp.isValid() && Board[temp.row][temp.col] != player) {
            legalMoves[temp.row][temp.col] = true;
        }
    }

    /**
     * Computes legal moves for the input location.
     * @param currentPosition the input location
     */
    public void legalMove(Location currentPosition) {
        clearLegalMoves(); //reset every single value to false
        for(int i = 0; i < Compass.length; i++){
            computeValid(currentPosition, Compass[i]); // at least one legal
        }
    }

    /**
     * Allows piece to be selected with a mouse click.
     * @return the Location of the mouse click/selected piece
     */
    public Location selectPiece(){
        if(player == 'w'){
            StdOut.println("White: Select a Piece");
        }
        else{
            StdOut.println("Black: Select a Piece");
        }
        while (!StdDraw.isMousePressed()){
            //do nothing
        }

        int x = (int)Math.round(StdDraw.mouseX());
        int y = (int)Math.round(StdDraw.mouseY());

        while (StdDraw.isMousePressed()){
            //do nothing
        }

        if (Board[x][y] != player) {
            if (player == 'w') {
                StdOut.println("White: Illegal Selection");
            }
            else{
                StdOut.println("Black: Illegal Selection");
            }
            return selectPiece();
        } else {
            return new Location(x, y);
        }

    }

    /**
     * Allows the destination to be chosen, repeating as needed until a valid destination is chosen.
     * @param piece the result of selectPiece.
     */
    public void selectDestination(Location piece) {
        if(player == 'w'){
            StdOut.println("White: Select a Destination");
        }
        else{
            StdOut.println("Black: Select a Destination");
        }

        while (!StdDraw.isMousePressed()) {
            //do nothing
        }
        int x = (int) Math.round(StdDraw.mouseX());
        int y = (int) Math.round(StdDraw.mouseY());
//
        while (StdDraw.isMousePressed()) {
            //do nothing
        }

        if (legalMoves[x][y]) {
            Board[x][y] = player; //moves piece to destination
            Board[piece.row][piece.col] = 'n'; //makes old location vacant
            switchColor(); //changes turn
        }
        else if ((Board[x][y] == Board[piece.row][piece.col])) { //to unselect selected piece
            clearLegalMoves();
            drawBoard();
            selectPiece();
        }

        else if (!legalMoves[x][y]) { //if destination is not a legal move
            if(player == 'w'){
                StdOut.println("White: Illegal Move");
            }
            else{
                StdOut.println("Black: Illegal Move");
            }
            selectDestination(piece);
        }
            }

    /**
     * Draws the board, with updates throughout the game
     */
    public void drawBoard() {

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-0.5, Board.length - .5);

        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.filledSquare(x, y, 0.5);
                StdDraw.setPenColor(StdDraw.BLACK);
                //Highlights legal moves
                if(legalMoves[x][y]){
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    StdDraw.filledSquare(x, y, 0.5);
                }
                //Draws black and white pieces
                if (Board[x][y] == 'b') {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.filledCircle(x, y, 0.35);
                } else if (Board[x][y] == 'w') {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledCircle(x, y, 0.35);
                }
                //Draws grid
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.square(x, y, 0.5);
            }
        }
        StdDraw.show();
    }

    /**
     * Draws red circle around the selected piece.
     * @param selected the Location of the selected piece
     */
    public void drawSelected(Location selected){
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.01);
        StdDraw.circle(selected.row, selected.col, 0.35);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.show();
    }

    /**
     * Runs the game in LinesofAction.java; holds together everything
     */
    public void executeGame() {
        int won = 0;
        StdOut.println("Welcome to Lines of Action!");
        while (won != 1) {
            drawBoard();
            Location selected = selectPiece();
            legalMove(selected);
            drawBoard();
            drawSelected(selected);
            selectDestination(selected);
            clearLegalMoves();
            if (hasWon('w')) {
                won = 1;
            }
            if (hasWon('b')) {
                won = 1;
            }
        }
        drawBoard();
        if (hasWon('w') && !hasWon('b')) {
            StdOut.println("White won!");
        } else if (!hasWon('w') && hasWon('b')) {
            StdOut.println("Black won!");
        } else {
            StdOut.println("It's a tie!");
        }
    }

    /**
     * Counts the number of pieces for a given color
     * @param color black or white; color being counted
     * @return
     */
    public int pieceCount(char color) {
        int c = 0;
        for (int i = 0; i < Board.length; ++i) {
            for (int j = 0; j < Board.length; ++j) {
                if (color == Board[i][j]) {
                    ++c;
                }
            }
        }
        return c;
    }

    /**
     * Finds first instance of a given color on the Board, finds like neighbors and repeats recursively until no more like neighbors are found
     * @param piece the Location of a piece
     * @param connected a boolean array of other like-pieces
     * @param color the color that is being percolated
     */
    public void percolates(Location piece, boolean[][] connected, char color){
        connected[piece.row][piece.col] = true;
        for(int i = 0; i < Compass.length; i++){
            Location l = piece.add(Compass[i]);
            //still within bounds; piece at board location equals color; not already visited
            if(l.isValid() && Board[l.row][l.col] == color
                    && !connected[l.row][l.col]){
                percolates(l, connected, color);
            }
        }
    }

    /**
     * Determines if a given color has met the conditions to win the game.
     * @param color the color that is being looked at
     * @return boolean determining game win
     */
    public boolean hasWon(char color){
        Location piece = null;
        outerloop:
        for(int i = 0; i < Board.length; i++){
            for(int j = 0; j < Board.length; j++){
                //find one piece of given color
                if(Board[i][j] == color) {
                    piece = new Location(i, j);
                    break outerloop; //break out of both loops
                }
            }
        }
        //array of booleans all false
        boolean[][] connected = new boolean[Board.length][Board.length];
        percolates(piece, connected, color);
        //count #true in the array
        int counter = 0;
        for(int i = 0; i < Board.length; i++){
            for(int j = 0; j < Board.length; j++){
                if(connected[i][j]){
                   counter++;
                }
            }
        }
        //count number of given color pieces
        return pieceCount(color) == counter;
    }
}
