import java.util.*;

//clasa unde voi genera harta cu configuratia dorita
public class Matrice_testare {
    ArrayList<ArrayList<Cell>> map_test;

    public ArrayList<ArrayList<Cell>> matrice_testare() {
        System.out.println("Se configureaza harta...\n");
        map_test = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < 5; i++) {
            ArrayList<Cell> a = new ArrayList(5);
            for (int j = 0; j < 5; j++) {
                Cell c = new Cell(i, j, Cell.CellEnum.EMPTY);
                a.add(c);
            }
            map_test.add(a);
        }

        map_test.get(0).set(3,  new Cell(1,3,new Shop()));
        map_test.get(1).set(3,  new Cell(1,3,new Shop()));
        map_test.get(2).set(0,  new Cell(2,0,new Shop()));
        map_test.get(3).set(4,  new Cell(3,4,new Enemy()));
        map_test.get(4).set(4,  new Cell(4,4,Cell.CellEnum.FINISH));

        return map_test;
    }
}
