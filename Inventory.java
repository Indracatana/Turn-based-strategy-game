import java.util.*;

class Inventory {
    List<Potion> potions = new ArrayList<>();  //lista de potiuni
    int max_weight;
    int nr_monede;
    int current_weight;

    public void add_potion(Potion p) {
        potions.add(p);
    }

    public void remove_potion(Potion p) {
        int ok = 0;
        for (int i = 0; i < potions.size(); i++) {  //se va cauta potiunea dorita si se va sterge daca exista
            if (potions.get(i).getClass() == p.getClass()) {
                potions.remove(i);
                ok = 1;
                break;
            }
        }
        if (ok == 0)
            System.out.println("\nNu s-a putut gasi potiunea " + p.getClass().getName() + "!\n");
    }

    //se va cauta daca potiunea dorita exista
    public boolean check_availabilty(Potion p) {
        for (int i = 0; i < potions.size(); i++) {
            if (potions.get(i).getClass() == p.getClass()) {
                return true;
            }
        }
        return false;
    }

    public int left_weight() {
        return max_weight - current_weight;
    }
}

interface Potion {

    void use_potion(Character c);

    int get_price();

    int get_value();  //metoda pentru a prelua valoarea de regenerare

    int get_weight();
}

class HealthPotion implements Potion {
    private final int price;
    private final int weight;
    private final int value;

    public HealthPotion() {
        price = 10;
        value = 20;
        weight = 22;
    }

    //metoda pentru utilizarea unei potiuni si eliminarea ei din lista dupa
    public void use_potion(Character c) {
        System.out.println("A fost folosita o potiune pentru viata!\n");
        c.regenerare_life(get_value());
        System.out.println(c.getClass().getName() + ":\nViata curenta:" + c.current_life);
        System.out.println("Mana curenta:" + c.current_power + "\n");
        c.inventory.remove_potion(new HealthPotion());
    }

    public int get_price() {
        return price;
    }

    public int get_value() {
        return value;
    }

    public int get_weight() {
        return weight;
    }

    public String toString() {
        return "HealthPotion";
    }
}

class ManaPotion implements Potion {
    private final int price;
    private final int weight;
    private final int value;

    public ManaPotion() {
        price = 8;
        value = 18;
        weight = 20;
    }

    //metoda pentru utilizarea unei potiuni si eliminarea ei din lista dupa
    public void use_potion(Character c) {
        System.out.println("A fost folosita o potiune pentru mana!\n");
        c.regenerare_power(get_value());
        System.out.println(c.getClass().getName() + ":\nViata curenta:" + c.current_life);
        System.out.println("Mana curenta:" + c.current_power + "\n");
        c.inventory.remove_potion(new ManaPotion());
    }

    public int get_price() {
        return price;
    }

    public int get_value() {
        return value;
    }

    public int get_weight() {
        return weight;
    }

    public String toString() {
        return "ManaPotion";
    }
}
