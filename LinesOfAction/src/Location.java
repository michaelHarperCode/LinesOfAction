//Michael Harper, Kyle Monteleone, Nuzhat Hoque
public class Location {
    //Instance variables
    int row;
    int col;

    /**
     * Creates a location based on coordinates.
     * @param r the Row
     * @param c the Column
     */
    public Location(int r, int c){
        row = r;
        col = c;
    }

    /**
     * Returns a String of a location.
     * @return String representing location
     */
    public String toString(){
        return "<"+ row + ", " + col + ">";
    }

    /**
     * Adds two locations
     * @param v the Location that is being added
     * @return a new Location representing the added values.
     */
    public Location add(Location v){
        int x = this.row + v.row;
        int y = this.col + v.col;
        return new Location(x, y);
    }

    /**
     * Subtracts two locations
     * @param v the Location that is being subtracted
     * @return a new Location representing the subtracted values.
     */
    public Location sub(Location v){
        int x = this.row - v.row;
        int y = this.col - v.col;
        return new Location(x, y);
    }

    /**
     * Returns a boolean that determines if a Location is on the board in LinesofAction.
     * @return boolean determining if Location is on playing board
     */
    public boolean isValid(){
        return (row < 8 && row >= 0) && (col < 8 && col >= 0);
    }
}
