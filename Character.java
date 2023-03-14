public abstract class Character extends Entity {
    String nume;
    int current_Ox, current_Oy;  //coordonatele curente ale personajului
    int experience;
    int level;
    int strength, charisma, dexterity;   //atributele

    Inventory inventory = new Inventory();

    public void buy_potion(Shop s, Potion p) {

        int index = -1;
        for (int i = 0; i < s.potions.size(); i++) {   //verific daca exista potiunea in magazin
            if (s.potions.get(i).getClass() == p.getClass()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            //daca exista monede suficiente si spatiu suficient in inventar
            if (p.get_weight() <= inventory.left_weight() && p.get_price() <= inventory.nr_monede) {
                inventory.add_potion(s.select(index));
                System.out.println("A fost cumparata o potiune de tipul " + p.getClass().getName() + "!\n");
                inventory.current_weight += p.get_weight();
                inventory.nr_monede -= p.get_price();
            } else System.out.println("Nu exista suficienti bani sau monede!");

        } else System.out.println("Nu a fost gasita potiunea!");
    }

    abstract void level_update();
}

class Warrior extends Character {
    public Warrior() {   //toate personajele pornesc cu viata si mana maxima
        current_life = 100;
        current_power = 100;
        strength = 100;  //atribut principal
        charisma = 70;
        dexterity = 65;
        fire = true;  //protectia
        inventory.max_weight = 90;
        inventory.nr_monede = (int) Math.floor(Math.random() * (40 - 20 + 1) + 20);
        create_list();

    }

    public void level_update() {  //functie care inregistreaza cresterea in nivel
        if (experience > 50) {   //daca personajul trece de experienta 50 pentru nivel curent acesta va avansa nivelul
            level += 1;
            experience = experience - 50;
            charisma += 10;   //experienta castigata dupa cresterea nivelului
            strength += 10;
        }
    }

    void receiveDamage(int valoare) {
        int chance = (charisma + dexterity) / 3;  //formula de calcul a sansei de a injumatati
        //  damage-ul primit functie de atribute secundare
        double val = Math.random() * 100;
        if (val < chance) {
            System.out.println("\nWarrior va injumatati damage-ul primit!");
            current_life = current_life - valoare / 2;
        } else
            current_life = current_life - valoare;
        //afisez viata si mana curenta dupa fiecare atac
        if (current_life >= 0)
            System.out.println("\nWarrior:\nViata curenta:" + current_life);
        else
            System.out.println("\nWarrior:\nViata curenta: 0"); //atunci cand personajul are viata 0 jocul ia final
        System.out.println("Mana curenta:" + current_power);
    }

    int getDamage() {
        int damage = 10;
        int chance = 50;   //sansa ca personajul sa dea damage dublu

        double val = Math.random() * 100;
        if (val < chance) {
            System.out.println("\nWarrior va ataca normal!");
            System.out.println("\nWarrior va da damage dublu!");
            return 2 * damage;
        } else {
            System.out.println("\nWarrior va ataca normal!");
            return damage;
        }

    }

    public String toString() {
        return "Warrior!\nNume: " + nume + "\nExperience:  " + experience + "\nLevel: " + level;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}

class Rogue extends Character {
    public Rogue() {
        current_life = 100;
        current_power = 100;
        strength = 80;
        charisma = 65;
        dexterity = 100;  //atribut principal
        earth = true;  //protectia
        inventory.max_weight = 70;
        inventory.nr_monede = (int) Math.floor(Math.random() * (40 - 20 + 1) + 20);
        create_list();

    }

    public void level_update() { //functie care inregistreaza cresterea in nivel
        if (experience > 50) {   //daca personajul trece de experienta 50 pentru nivel curent acesta va avansa nivelul
            level += 1;
            experience = experience - 50;
            charisma += 10;   //experienta castigata dupa cresterea nivelului
            strength += 10;
        }
    }

    //analog
    void receiveDamage(int valoare) {
        int chance = (charisma + strength) / 3;

        double val = Math.random() * 100;
        if (val < chance) {
            System.out.println("\nRogue va injumatati damage-ul primit!");
            current_life = current_life - valoare / 2;
        } else
            current_life = current_life - valoare;
        if (current_life >= 0)
            System.out.println("\nRogue:\nViata curenta:" + current_life);
        else
            System.out.println("\nRogue:\nViata curenta: 0");  //atunci cand personajul are viata 0 jocul ia final
        System.out.println("Mana curenta:" + current_power);
    }

    int getDamage() {
        int damage = 7;
        int chance = 40;

        double val = Math.random() * 100;
        if (val < chance) {
            System.out.println("\nRogue va ataca normal!");
            System.out.println("\nRogue va da damage dublu!");
            return 2 * damage;
        } else {
            System.out.println("\nRogue va ataca normal!");
            return damage;
        }
    }

    public String toString() {
        return "Rogue!\nNume: " + nume + "\nExperience:  " + experience + "\nLevel: " + level;

    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}

class Mage extends Character {
    public Mage() {
        current_life = 100;
        current_power = 100;
        strength = 60;
        charisma = 100;  //atribut principal
        dexterity = 70;
        ice = true;
        inventory.max_weight = 50;
        inventory.nr_monede = (int) Math.floor(Math.random() * (40 - 20 + 1) + 20);  //destule monede pentru a cumpara
        // o potiune de mana si unde viata
        create_list();
    }

    public void level_update() {  //functie care inregistreaza cresterea in nivel
        if (experience > 50) {   //daca personajul trece de experienta 50 pentru nivel curent acesta va avansa nivelul
            level += 1;
            experience = experience - 50;
            charisma += 10;   //experienta castigata dupa cresterea nivelului
            strength += 10;
        }
    }

    void receiveDamage(int valoare) {
        int chance = (strength + dexterity) / 3;
        double val = Math.random() * 100;
        if (val < chance) {
            System.out.println("\nMage va injumatati damage-ul primit!");
            current_life = current_life - valoare / 2;
        } else
            current_life = current_life - valoare;
        if (current_life >= 0)
            System.out.println("\nMage:\nViata curenta:" + current_life);
        else
            System.out.println("\nMage:\nViata curenta: 0");   //atunci cand personajul are viata 0 jocul ia final
        System.out.println("Mana curenta:" + current_power);
    }

    int getDamage() {
        int damage = 9;
        int chance = 40;

        double val = Math.random() * 100;
        if (val < chance) {
            System.out.println("\nMage va ataca normal!");
            System.out.println("\nMage va da damage dublu!");
            return 2 * damage;
        } else {
            System.out.println("\nMage va ataca normal!");
            return damage;
        }
    }

    public String toString() {
        return "Mage!\nNume: " + nume + "\nExperience:  " + experience + "\nLevel: " + level;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}

//Factory Pattern folosit pentru a instantia personajele
class Characters {
    public enum CharacterType {
        Warrior, Rogue, Mage
    }

    public static Character createCharacter(CharacterType characterType) {
        switch (characterType) {
            case Warrior:
                return new Warrior();
            case Rogue:
                return new Rogue();
            case Mage:
                return new Mage();
        }
        throw new IllegalArgumentException("The character type " +
                characterType + " is not recognized.");
    }

}
