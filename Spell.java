interface Visitor {
    void visit(Rogue r);

    void visit(Warrior w);

    void visit(Mage m);

    void visit(Enemy e);
}

//clasa Spell implementeaza interfata Visitor specific sablonului Visitor
//se va modela efectul pe care il au abilitatile asupra entitatilor
public abstract class Spell implements Visitor {
    int damage;
    int cost;   //cost mana

    abstract int get_cost();
}

class Ice extends Spell {

    public Ice() {
        damage = 16;
        cost = 21;
    }

    public int get_cost() {
        return cost;
    }

    @Override
    public void visit(Mage m) {  //personajul se va proteja de damage
        System.out.println("\nA fost folosita abilitatea Gheata!");
        System.out.println("Mage este imun la Gheata!");
        m.receiveDamage(0);  //damage primit
    }

    @Override
    public void visit(Rogue r) {
        System.out.println("\nA fost folosita abilitatea Gheata!");
        r.receiveDamage(damage);
    }

    @Override
    public void visit(Warrior w) {
        System.out.println("\nA fost folosita abilitatea Gheata!");
        w.receiveDamage(damage);
    }

    public void visit(Enemy e) {
        if (Boolean.compare(e.ice, true) == 0) {
            System.out.println("\nA fost folosita abilitatea Gheata!");
            System.out.println("Inamicul este imun la Gheata!");
            e.receiveDamage(0);
        } else {
            System.out.println("\nA fost folosita abilitatea Gheata!");
            e.receiveDamage(damage);
        }
    }
}

class Fire extends Spell {

    public Fire() {
        damage = 19;
        cost = 25;
    }

    public int get_cost() {
        return cost;
    }

    @Override
    public void visit(Mage m) {
        System.out.println("\nA fost folosita abilitatea Foc!");
        m.receiveDamage(damage);
    }

    @Override
    public void visit(Rogue r) {
        System.out.println("\nA fost folosita abilitatea Foc!");
        r.receiveDamage(damage);
    }

    @Override
    public void visit(Warrior w) { //personajul se va proteja de damage
        System.out.println("\nA fost folosita abilitatea Foc!");
        System.out.println("Warrior este imun la Foc!");
        w.receiveDamage(0);
    }

    public void visit(Enemy e) {
        if (Boolean.compare(e.fire, true) == 0) {
            System.out.println("\nA fost folosita abilitatea Foc!");
            System.out.println("Inamicul este imun la Foc!");
            e.receiveDamage(0);
        } else {
            System.out.println("\nA fost folosita abilitatea Foc!");
            e.receiveDamage(damage);
        }
    }
}

class Earth extends Spell {
    int damage;
    int cost;  //cost mana

    public Earth() {
        damage = 13;
        cost = 16;
    }

    public int get_cost() {
        return cost;
    }

    public String toString() {
        return "I am earth";
    }

    @Override
    public void visit(Mage m) {
        System.out.println("\nA fost folosita abilitatea Pamant!");
        m.receiveDamage(damage);
    }

    @Override
    public void visit(Rogue r) {  //personajul se va proteja de damage
        System.out.println("\nA fost folosita abilitatea Pamant!");
        System.out.println("Rogue este imun la Pamant!");
        r.receiveDamage(0);
    }

    @Override
    public void visit(Warrior w) {
        System.out.println("\nA fost folosita abilitatea Pamant!");
        w.receiveDamage(damage);
    }

    public void visit(Enemy e) {
        if (Boolean.compare(e.earth, true) == 0) {
            System.out.println("\nA fost folosita abilitatea Pamant!");
            System.out.println("Inamicul este imun la Pamant!");
            e.receiveDamage(0);
        } else {
            System.out.println("\nA fost folosita abilitatea Pamant!");
            e.receiveDamage(damage);
        }
    }
}