// Francisco J. Melendez Laureano
// CCOM-4029 : Java Project 2 (Rummy Card Game)
import Interfaces.*;
import Sources.*;

import Sources.Table;

// Can be used to play test individual Objects
public class Proj2 {

    public static void main(String args[]) {

        System.out.println("Hello, World!\nWelcome to Rummy");

        // Players are up to 2,
        // if only 1 player pick an opponent 'AI'

        Table table;
        table = new Table();
        table.setVisible(true);

    } // end of main()

}
