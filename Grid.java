import java.lang.reflect.Type;
import java.util.*;

public class Grid extends ArrayList<Type> {

    public static int lungime, latime;
    static ArrayList<ArrayList<Cell>> map = new ArrayList<>();
    static public Character personaj;  //referinta la personajul curent
    static public Cell cel_curenta;   //celula curenta a personajului
    static int x, y;  //coordonatele celulei curente

    private Grid() {
    }

    public static void set_cel_curenta() {   //metoda pentru setarea celulei curente si a coordonatelor acesteia pe harta
        cel_curenta = map.get(x).get(y);
        cel_curenta.Ox = x;
        cel_curenta.Oy = y;
    }

    //generez initial o harta cu lungimea si latimea dorite, cu celule nule
    public static ArrayList<ArrayList<Cell>> generare(int lung, int lat) {
        lungime = lung;
        latime = lat;

        for (int i = 0; i < lat; i++) {
            ArrayList<Cell> a = new ArrayList<>(lung);
            for (int j = 0; j < lung; j++) {
                Cell c = null;
                a.add(c);
            }
            map.add(a);
        }
        return map;
    }

    //metoda pentru printarea hartii in modul dorit
    public static String print_map() {
        String str = "Starea hartii:\n";
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++)
                if (i == x && j == y) {

                    if (map.get(i).get(j).cells == Cell.CellEnum.EMPTY)
                        str += "P" + " ";  //adaug P daca celula este de tipul EMPTY
                    else
                        str += "P" + map.get(i).get(j) + " "; //adaug P in fata caracterului specific celulei curente

                } else {
                    if (map.get(i).get(j) == null)
                        str += "?" + " ";   //daca celula nu a fost inca vizitata afisez "?"
                    else
                        str += map.get(i).get(j) + " ";  //afisez caracterul specific celulei curente
                }

            str += "\n";
        }
        return str;
    }

    static public void set_personaj(Character c) {   //metoda pentru setarea personajului curent
        personaj = c;
        c.current_Ox = x;
        c.current_Oy = y;
    }

    public static void set_cell(int x, int y, Cell cell) {  //metoda pentru setarea unei celule
        map.get(x).set(y, cell);
    }

    public static void first_cell(ArrayList<ArrayList<Cell>> map_test) {  //harta de start
        set_cell(x, y, map_test.get(x).get(y));
        cel_curenta = map.get(0).get(0);
    }

    //la fiecare deplasare actualizez celula curenta
    public static void goNorth(ArrayList<ArrayList<Cell>> map_test) {
        if (x > 0) { //se poate deplasa
            x--;
            set_cell(x, y, map_test.get(x).get(y));
            set_cel_curenta();
        } else System.out.println("Can not move to north!");
    }

    public static void goSouth(ArrayList<ArrayList<Cell>> map_test) {
        if (x < latime - 1) {  //se poate deplasa
            x++;
            set_cell(x, y, map_test.get(x).get(y));
            set_cel_curenta();
        } else System.out.println("Can not move to south!");
    }

    public static void goWest(ArrayList<ArrayList<Cell>> map_test) {
        if (y > 0) { //se poate deplasa
            y--;
            set_cell(x, y, map_test.get(x).get(y));
            set_cel_curenta();
        } else System.out.println("Can not move to west!");
    }

    public static void goEast(ArrayList<ArrayList<Cell>> map_test) {
        if (y < lungime - 1) { //se poate deplasa
            y++;
            set_cell(x, y, map_test.get(x).get(y));
            set_cel_curenta();
        } else System.out.println("Can not move to east!");
    }
}

class Cell {
    int Ox, Oy;  //coordonatele pe harta
    CellEnum cells;
    boolean vizitat = false;
    CellElement element;

    public enum CellEnum {  //enum pentru tipul celulei
        EMPTY, ENEMY, SHOP, FINISH
    }

    //celulele pot fi instantiate in doua moduri, in functie de tipul lor:
    //cu ajutorul CellEnum pentru "FINISH"si "EMPTY"
    //cu ajutorul interfetei CellElement si astfel instantiez un obiect de tipul "Shop"sau "Enemy"
    public Cell(int Ox, int Oy, CellEnum cells) {
        this.Ox = Ox;
        this.Oy = Oy;
        this.cells = cells;
    }

    public Cell(int Ox, int Oy, CellElement element) {
        this.Ox = Ox;
        this.Oy = Oy;
        this.element = element;
    }

    //afisare caracter functie de tipul celulei
    public String print() {
        String s = "";
        if (cells != null) {
            switch (cells) {
                case EMPTY:
                    s = "N";
                    break;
                case FINISH:
                    s = "F";
                    break;
            }
        } else {
            s = element.toCharacter();   //pentru SHOP sau ENEMY
        }
        return s;
    }
    public String toString() {
        return print();
    }
}

interface CellElement {
    String toCharacter();
}