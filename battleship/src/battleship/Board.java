package battleship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private final int SIDE_LENGTH = 10;
    private final Set<Integer> marked, owners;
    private final Map<Integer, Ship_Owner> ships;

    /** Board constructor */
    public Board() {
        owners = new HashSet<>(); // set of cells representing ship owners
        marked = new HashSet<>();
        ships = new HashMap<>();
    }

    /**
     * Method to target a cell on the board to attack and attempt to sink ships (in game)
     *
     * Codes:
     * - -1: cell was already targeted earlier
     * - 0: cell contained part of ship
     * - 1: cell missed the ship
     * - 10: cell sunk the ship (final part of ship)
     * - 100: cell sunk the last ship
     *
     * @param position - grid position of cell to attack
     * @return code of whether operation was successful
     */
    public int target(String position) {
        int place = (position.charAt(0) - 'A')*SIDE_LENGTH + Integer.parseInt(position.substring(1));
        if (marked.contains(place)) return -1;
        else {
            boolean hit = ships.containsKey(place);
            marked.add(place);
            if (hit) {
                // update ship's owner to reflect number of ship cells still safe
                ships.get(place).links--;
                // if ship has been sunk, remove it from set of ships still safe
                if (ships.get(place).links == 0) {
                    owners.remove(ships.get(place).value);
                    return owners.isEmpty() ? 100 : 10;
                }
            }
            return hit ? 0 : 1;
        }
    }

    /**
     * Get String representation of Battleship board
     *
     * @param showComplete - flag to determine whether complete board will be revealed
     * @return String representation of the battleship board
     */
    public String display(boolean showComplete) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= SIDE_LENGTH; i++) {
            for (int j = 0; j <= SIDE_LENGTH; j++) {
                if (i == 0) sb.append(j == 0 ? " " : j);
                else if (j == 0) sb.append((char) ('A' + (i-1)));
                else {
                    int position = (i-1)*SIDE_LENGTH + j;
                    if (marked.contains(position) && ships.containsKey(position)) sb.append(Display_Piece.STRIKE);
                    else if (marked.contains(position)) sb.append(Display_Piece.MISS);
                    else if (ships.containsKey(position) && showComplete) sb.append(Display_Piece.OCCUPIED);
                    else sb.append(Display_Piece.FREE);
                }
                if (j != SIDE_LENGTH) sb.append(" ");
            }
            if (i != SIDE_LENGTH) sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Check if coordinates representing the ship are valid to be placed on the board
     *
     * Codes:
     * - -1 if length of coordinates doesn't match length of ship
     * - 0 if successful placement
     * - 1 if too close to another ship
     *
     * @param u - grid coordinate of top of ship
     * @param v - grid coordinate of bottom of ship
     * @param length - length that ship should be
     * @return code representing whether or not the coordinates work
     */
    private int check(String u, String v, int length) {
        // check length between cells
        int from = u.charAt(0) == v.charAt(0) ? Integer.parseInt(u.substring(1)) : u.charAt(0) - 'A';
        int to = u.charAt(0) == v.charAt(0) ? Integer.parseInt(v.substring(1)) : v.charAt(0) - 'A';
        if (Math.abs(to-from) != length-1) return -1;

        // calculate position of coordinates (on internal board)
        int u_position = (u.charAt(0)-'A')*SIDE_LENGTH + Integer.parseInt(u.substring(1));
        int v_position = (v.charAt(0)-'A')*SIDE_LENGTH + Integer.parseInt(v.substring(1));
        // rearrange coordinate positions so that u comes before v
        if (u_position > v_position) {
            int temp = u_position;
            u_position = v_position;
            v_position = temp;
        }

        // Check cells around proposed ship to see if a ship exists there already
        int ship_cell_distance = (v_position-u_position)/(length-1);
        int neighbour_cell_distance = ship_cell_distance == SIDE_LENGTH ? 1 : SIDE_LENGTH;
        // At ends of ship, need to check neighbours at ship's cell distance
        if (u_position % SIDE_LENGTH != 1 && ships.containsKey(u_position-ship_cell_distance)) return 1;
        if (v_position % SIDE_LENGTH != 0 && ships.containsKey(v_position+ship_cell_distance)) return 1;
        for (int i = u_position; i <= v_position; i+=ship_cell_distance) {
            for (int j = i-neighbour_cell_distance; j <= i+neighbour_cell_distance; j+=neighbour_cell_distance) {
                if (ships.containsKey(j)) return 1;
            }
        }
        return 0;
    }

    /**
     * Attempt to place a ship on the board, given the coordinates and the ship to place
     *
     * Codes:
     * - -1 if length of coordinates doesn't match length of ship
     * - 0 if successful placement
     * - 1 if too close to another ship
     *
     * @param u - grid location of start of ship
     * @param v - grid location of end of ship
     * @param b - ship to place on board
     * @return code determining success of placement
     */
    public int place(String u, String v, Ship b) {
        int valid = check(u, v, b.getSize()); // check if coordinates entered can be used
        if (valid == 0) {
            // Can place ship at specified location, calculate distance between each cell of the boat and place ship
            int u_position = (u.charAt(0)-'A')*SIDE_LENGTH + Integer.parseInt(u.substring(1));
            int v_position = (v.charAt(0)-'A')*SIDE_LENGTH + Integer.parseInt(v.substring(1));
            int step = (v_position-u_position)/(b.getSize()-1);
            place(u_position, v_position, step);
        }
        return valid;
    }

    /**
     * Private method to place ship on the board given the first and last cells as well as the distance between cells
     *
     * @param a - location of first cell
     * @param b - location of last cell
     * @param step - distance between each cell
     */
    private void place(int a, int b, int step) {
        // Let the second cell be the ship's owner (any cell in the ship's path would do)
        Ship_Owner o = new Ship_Owner(b);
        owners.add(b);
        // Add ship's cells to the board
        for (int i = a; i != b+step; i+=step) {
            ships.put(i, o);
            o.links++; // update links to owner (keeping track of number of un-hit cells of the ship)
        }
    }

    /**
     * Check if the given board has had it's ships sunk
     *
     * @return whether there are ships left
     */
    public boolean isCompleted() {
        return owners.isEmpty();
    }
}