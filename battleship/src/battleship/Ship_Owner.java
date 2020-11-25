package battleship;

/* POJO class to keep track of "owner" of a ship
* (used to track cells on the board to a ship and one cell is made it's owner) */
public class Ship_Owner {
    int value, links;

    public Ship_Owner(int v) {
        value = v;
        links = 0;
    }
}