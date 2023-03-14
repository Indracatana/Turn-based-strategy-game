import java.util.*;

public class Shop implements CellElement{

    List<Potion> potions = new ArrayList<>();
    List<Potion> lst = new ArrayList<>();  //lista cu potiunile existente

    public Shop(){   //lista cu potiuni va fi generata la momentul instantierii
        create_list();
    }

    public void create_list() {
        lst.add(new HealthPotion());
        lst.add(new ManaPotion());

        Random rand = new Random();
        int random_int = (int) Math.floor(Math.random() * (4 - 2 + 1) + 2);
        for (int j = 0; j < random_int; j++) {
            int index = rand.nextInt(lst.size());
            Potion potion = lst.get(index);
            potions.add(potion);  //lista cu potiuni aleatoare
        }
    }

    //potiunea va fi selectata si stearsa din lista dupa
    public Potion select(int i){
        Potion p=potions.get(i);
        potions.remove(i);
        return p;
    }

    //daca tipul celulei este "SHOP" se va afisa "S"
    public String toCharacter(){
        return "S";
    }
}
