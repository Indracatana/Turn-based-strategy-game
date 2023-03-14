import java.util.*;

public class Enemy extends Entity implements CellElement {
    boolean ok;

    public Enemy() {
        current_life = (int) Math.floor(Math.random() * (100 - 50 + 1) + 50);  //viata curenta generata random
        current_power = (int) Math.floor(Math.random() * (100 - 40 + 1) + 40);  //mana curenta generata random
        max_power = 100;
        max_life = 100;
        Random rd = new Random();
        //protectiile vor avea valori random
        fire = rd.nextBoolean();
        ice = rd.nextBoolean();
        earth = rd.nextBoolean();
        create_list();   //lista de abilitati generate random
    }

    void receiveDamage(int valoare) {
        Random rd = new Random();
        ok = rd.nextBoolean();   //sansa de 50% ca inamicul sa evite damge-ul
        if (Boolean.compare(ok, false) == 0)
            current_life = current_life - valoare;
        else System.out.println("\nDamage evitat!");
        if(current_life>=0)
        System.out.println("\nInamic:\nViata curenta:" + current_life);
        else
            System.out.println("\nInamic:\nViata curenta: 0");

        System.out.println("Mana curenta:" + current_power);
    }

    int getDamage() {
        Random rd = new Random();
        ok = rd.nextBoolean();  //sansa de 50% ca inamicul sa dea damge dublu
        if (Boolean.compare(ok, false) == 0)
            return 6;
        else {
            System.out.println("\nVa da damage dublu!");
            return 12;
        }
    }

    //metoda pentru a alege modul in care inamicul va ataca
    public void alege_tip_atac(Character c) {
        if(current_life<=0)
            return;
        double val = Math.random() * 100;
        if (val < 80) {   //sansa de 80% ca inamicul sa atace normal
            System.out.println("\nInamicul va ataca normal!");
            c.receiveDamage(getDamage());
        } else {    //sansa de 20% ca inamicul sa foloseasca abilitate
            if (abilities.size() > 0) {   //daca inamicul mai are abilitati
                use_ability(c, abilities.get(0));
            } else
                c.receiveDamage(getDamage());
        }
    }

    //daca tipul celulei este "ENEMY" se va afisa "E"
    @Override
    public String toCharacter() {
        return "E";
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String toString() {
        return "Viata curenta: " + current_life + "\nMana curenta: " +
                current_power;
    }
}
