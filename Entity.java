import java.util.*;

interface Visited {
    void accept(Visitor v);
}

//clasa Entity implementeaza interfata Visited specific sablonului Visitor
//se va modela efectul pe care il au abilitatile asupra entitatilor
public abstract class Entity implements Visited {

    List<Spell> abilities = new ArrayList<>();
    List<Spell> lst = new ArrayList<>(3);  //lista in care adaug abilitatile posibile

    public void create_list() {
        lst.add(new Ice());
        lst.add(new Earth());
        lst.add(new Fire());
        Random rand = new Random();
        int random_int = (int) Math.floor(Math.random() * (4 - 2 + 1) + 2);
        for (int j = 0; j < random_int; j++) {
            int index = rand.nextInt(lst.size());
            Spell ability = lst.get(index);
            abilities.add(ability);    //creez o lista cu 2-4 abiliati random pentru personaj
        }
    }

    int current_life, max_life = 100;
    int current_power, max_power = 100;
    //initial protectiile sunt false, acestea avand valori unice pentru fiecare personaj/inamic
    boolean fire = false;
    boolean earth = false;
    boolean ice = false;

    public void regenerare_life(int valoare) {
        if (current_life + valoare <= max_life)  //nu se va depasi maximul
            current_life = current_life + valoare;
        else current_life = max_life;
    }

    public void regenerare_power(int valoare) {
        if (current_power + valoare <= max_power)  //nu se va depasi maximul
            current_power = current_power + valoare;
        else current_power = max_power;
    }

    public void use_ability(Entity e, Spell s) {
        int index = -1;

        for (int i = 0; i < abilities.size(); i++) {  //caut daca enititatea are abilitatea dorita
            if (abilities.get(i).getClass() == s.getClass()) {
                index = i;  //salvez pozitia pe care o are in lista de abilitati
                break;
            }
        }

        if (index != -1) {
            if (s.get_cost() < current_power) {  //daca are destula mana
                current_power = current_power - s.get_cost();   //costul de mana pentru folosirea abilitatii
                e.accept(s);
                abilities.remove(index);  //elimin abilitatea din lista dupa ce o folosesc
            } else {
                System.out.println("Personajul nu mai are destula mana si va ataca normal!");
                e.receiveDamage(getDamage());
            }
        } else System.out.println("Personajul nu are aceasta abilitate!");

    }

    abstract void receiveDamage(int valoare);  //metoda pentru inregistrarea unei pierderi de viata

    abstract int getDamage(); //metoda care returneaza damage-ul dat in cazul unui atac normal
}


