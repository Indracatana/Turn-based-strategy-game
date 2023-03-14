import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.event.*;
import java.util.List;


public class Testare {

    public static void main(String[] args) {

        System.out.println("Daca doresti desfasurarea jocului in terminal," +
                " scrie 'T', altfel, daca doresti in GUI, scrie 'G'!\n");
        System.out.println("Alege:");

        Scanner in = new Scanner(System.in);

        String s = in.nextLine();
        //cazul in care aplicatia va rula in terminal
        if (s.equals("T")) {
            System.out.println("\nTerminal\n");
            Game g = Game.getInstance();
            g.run();
            g.get_stories();

            System.out.println("Alege contul prin specificarea numarului acestuia:\n");

            for (int i = 0; i < g.conturi.size(); i++) {
                Account.Information info = g.conturi.get(i).get_informations();
                Credentials c = info.get_cred();
                System.out.println(i + ") " + c);
            }

            s = in.nextLine();

            int indice = (Integer.parseInt(s));  //indicele contului ales

            Account.Information info = g.conturi.get(indice).get_informations();
            String nume = info.get_nume();
            String cnt = info.get_country();
            int nr_jocuri = g.conturi.get(indice).nr_jocuri;

            List<String> jocuri = info.get_games();
            System.out.println("\nBun venit, " + nume + "!");
            System.out.println("\nInformatii despre jucator:\n" + "Tara: " + cnt + "\nJocuri preferate: " +
                    jocuri + "\nNumar jocuri completate: " + nr_jocuri + "\n");

            List<Personaj> pers = g.conturi.get(indice).personaje;

            //afisez toate personajele contului
            for (int i = 0; i < pers.size(); i++) {
                System.out.println(i + ") " + pers.get(i));
            }

            System.out.println("Alege personajul dorit prin specificarea numarului acestuia:");

            s = in.nextLine();
            int indice2 = (Integer.parseInt(s));  //indicele personajului ales

            System.out.println("\nPersonajul ales este:\n\n" + pers.get(indice2));
            //salvez cateva informatii necesare despre personaj
            String player_name = (pers.get(indice2).name);
            int player_level = (Integer.parseInt(pers.get(indice2).level));
            long player_experience = pers.get(indice2).experience;

            String character = pers.get(indice2).proffesion;  //tipul personajului pentru a il putea crea

            //Factory Pattern pentru a instantia personajele
            Character ch = null;

            if (character.equals("Rogue")) {
                ch = Characters.createCharacter(Characters.CharacterType.Rogue);
            }
            if (character.equals("Warrior")) {
                ch = Characters.createCharacter(Characters.CharacterType.Warrior);
            }
            if (character.equals("Mage")) {
                ch = Characters.createCharacter(Characters.CharacterType.Mage);
            }
            //atribui informatiile personajului
            ch.level = player_level;
            ch.experience = (int) player_experience;
            ch.nume = player_name;
            g.personaj = ch;

            Matrice_testare m = new Matrice_testare();  //clasa unde creez harta cu configuratia dorita
            ArrayList<ArrayList<Cell>> map_test = m.matrice_testare();  //harta cu configuratia dorita
            Grid.generare(m.map_test.size(), m.map_test.get(0).size());  //generez si harta jocului de dimensiunile
                                                                 //dorite si cu celule initial nule
            Grid.set_personaj(ch);  //setez personajul dorit

            //clasa interna pentru a realiza trecerea la urmatoarea mutare din scenariu la apasarea tastei "P"
            class KeyListenerTestare extends JFrame implements KeyListener {
                JTextField data;
                JLabel label;
                JPanel panell;
                String s = "";
                int nr_mutare = 0;

                public KeyListenerTestare() {
                    panell = new JPanel(new FlowLayout());
                    label = new JLabel("Apasati tasta 'P' pentru a trece la urmatoarea mutare!");
                    data = new JTextField(20);
                    data.addKeyListener(this);  //verific ce tasta a fost apasata
                    setLocationRelativeTo(null);
                    setVisible(false);
                    panell.add(label);
                    panell.add(data);
                    add(panell, BorderLayout.CENTER);
                    setTitle("Fereastra");
                    setLayout(new FlowLayout());
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    pack();
                    show();
                }

                public void keyPressed(KeyEvent e) {
                    s = String.valueOf(e.getKeyChar());
                    if (e.getKeyChar() == 'p') {  //mutarile se vor face la apasarea tastei "P"
                        nr_mutare++;
                        //scenariul hardcodat
                        if (nr_mutare == 1) {
                            Grid.first_cell(map_test);  //matricea de start
                            System.out.println(Grid.print_map());  //afisez starea matricei
                            g.display_story(Grid.cel_curenta);   //afisez povestea pentru celula curenta
                            g.get_option(Grid.cel_curenta);   //afisez optiunile functie de celula curenta si
                            //trec la urmatoarele operatii cerute de scenariu
                        }
                        if (nr_mutare == 2) {
                            Grid.goEast(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 3) {
                            Grid.goEast(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 4) {
                            Grid.goEast(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 5) {
                            Grid.goEast(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 6) {
                            Grid.goSouth(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 7) {
                            Grid.goSouth(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 8) {

                            Grid.goSouth(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                        if (nr_mutare == 9) {
                            Grid.goSouth(map_test);
                            System.out.println(Grid.print_map());
                            g.display_story(Grid.cel_curenta);
                            g.get_option(Grid.cel_curenta);
                        }
                    }
                }

                public void keyTyped(KeyEvent e) {

                }

                public void keyReleased(KeyEvent e) {
                }
            }
            KeyListenerTestare e = new KeyListenerTestare();
        }

        //cazul in care aplicatia va rula in interfata grafica
        if (s.equals("G")) {
            System.out.println("GUI");
            Game g = Game.getInstance();
            g.run();
            Autentificare a = new Autentificare("World of Marcel", g);
        }

    }
}
