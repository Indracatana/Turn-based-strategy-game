import java.util.*;

import org.json.simple.JSONArray;

import java.io.*;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {

    //Singleton pattern cu instantiere intarziata pentru a restrictiona numarul de instantieri ale clasei
    private static Game instance = null;

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    List<Account> conturi = new ArrayList<>();   //lista de conturi logate
    Map<Cell.CellEnum, List<String>> map = new HashMap<>();   //dictionar avand drept cheie tipul celulei si drept valoare
    Character personaj;                                       //o lista de siruri cu povesti

    public void get_stories() {  //metoda pentru incarcarea dictionarului cu date parsate din "stories.json"
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("stories.json"));
            JSONObject jo = (JSONObject) obj;
            JSONArray stories = (JSONArray) jo.get("stories");

            //pentru fiecare tip de celula creez o lista de povesti
            List<String> povesti_EMPTY = new ArrayList<>();
            List<String> povesti_ENEMY = new ArrayList<>();
            List<String> povesti_SHOP = new ArrayList<>();
            List<String> povesti_FINISH = new ArrayList<>();

            for (int i = 0; i < stories.size(); i++) {

                JSONObject story = ((JSONObject) stories.get(i));
                String type = ((String) (story).get("type"));
                String value = ((String) (story).get("value"));

                if (type.equals("EMPTY"))
                    povesti_EMPTY.add(value);
                if (type.equals("ENEMY"))
                    povesti_ENEMY.add(value);
                if (type.equals("SHOP"))
                    povesti_SHOP.add(value);
                if (type.equals("FINISH")) {
                    povesti_FINISH.add(value);
                }
            }

            map.put(Cell.CellEnum.EMPTY, povesti_EMPTY);
            map.put(Cell.CellEnum.ENEMY, povesti_ENEMY);
            map.put(Cell.CellEnum.SHOP, povesti_SHOP);
            map.put(Cell.CellEnum.FINISH, povesti_FINISH);

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {  //metoda pentru incarcarea datelor despre fiecare cont parsate din "account.json"

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("accounts.json"));
            JSONObject jo = (JSONObject) obj;
            JSONArray accounts = (JSONArray) jo.get("accounts");

            for (int i = 0; i < accounts.size(); i++) {
                Account acc = new Account();
                List<String> game = new ArrayList<>();  //colectia sortata in ordine crescatoare cu jocurile preferate
                Personaj personaj;

                JSONObject cred = ((JSONObject) ((JSONObject) accounts.get(i)).get("credentials"));
                String email = ((String) (cred).get("email"));
                String pass = ((String) (cred).get("password"));
                Credentials cr = new Credentials(email, pass);

                String nume = ((String) ((JSONObject) accounts.get(i)).get("name"));
                String country = ((String) ((JSONObject) accounts.get(i)).get("country"));

                String maps_completed = ((String) ((JSONObject) accounts.get(i)).get("maps_completed"));
                acc.nr_jocuri = Integer.parseInt(maps_completed);

                JSONArray favorite_games = ((JSONArray) ((JSONObject) accounts.get(i)).get("favorite_games"));
                for (int j = 0; j < favorite_games.size(); j++) {

                    game.add((String) favorite_games.get(j));
                    Collections.sort(game);
                }

                //crearea unui obiect de tip information
                acc.set_information(new Account.Information.Information_Builder(nume)
                        .set_credentials(cr).set_favourite_games(game).set_country(country).build());

                //informatii depre personaje
                JSONArray characters = ((JSONArray) ((JSONObject) accounts.get(i)).get("characters"));
                for (int j = 0; j < characters.size(); j++) {
                    String name = ((String) ((JSONObject) characters.get(j)).get("name"));
                    String profession = ((String) ((JSONObject) characters.get(j)).get("profession"));
                    String level = ((String) ((JSONObject) characters.get(j)).get("level"));
                    Long experience = ((Long) ((JSONObject) characters.get(j)).get("experience"));
                    personaj = new Personaj(name, profession, level, experience);
                    acc.personaje.add(personaj);
                }
                conturi.add(acc);
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    //metoda ce afiseaza o poveste pentru tipul de celula si elimina povestea din lista
    public void display_story(Cell cell) {
        if (Boolean.compare(cell.vizitat, false) == 0) {  //daca celula nu a fost vizitata inainte

            if (cell.cells != null && cell.cells.toString().equals("EMPTY")) {
                List<String> povesti = map.get(Cell.CellEnum.EMPTY);
                System.out.println(povesti.get(0));
                povesti.remove(0);

            } else if (cell.element != null && cell.element instanceof Enemy) {
                List<String> povesti = map.get(Cell.CellEnum.ENEMY);
                System.out.println(povesti.get(0));
                povesti.remove(0);

            } else if (cell.element != null && cell.element instanceof Shop) {
                List<String> povesti = map.get(Cell.CellEnum.SHOP);
                System.out.println(povesti.get(0));
                povesti.remove(0);

            } else if (cell.cells != null && cell.cells.toString().equals("FINISH")) {
                List<String> povesti = map.get(Cell.CellEnum.FINISH);
                System.out.println(povesti.get(0));
                povesti.remove(0);
            }
        }
    }

    int experienta_castigata;  //informatii ce vor fi afisate la finalul jocului
    int monede_castigate;

    //metoda ce afiseaza lista de optiuni disponibile pentru celula curenta si preia comenzile
    // urmatoare conform scenariului de testare
    public void get_option(Cell cell) {
        if (Boolean.compare(cell.vizitat, false) == 0) {

            if (cell.cells != null && cell.cells.toString().equals("EMPTY")) {
                double val = Math.random() * 100;
                if (val < 20) { //sansa de 20% de a primi monede
                    System.out.println("\nNumar monede: " + personaj.inventory.nr_monede);
                    System.out.println("Ce noroc! Am gasit monede!");
                    int monede = 0;
                    monede += (int) Math.floor(Math.random() * (6 - 2 + 1) + 2);//poate gasi intre 2-6 monede
                    monede_castigate += monede;
                    personaj.inventory.nr_monede += monede;
                    System.out.println("Numar curent monede: " + personaj.inventory.nr_monede + "\n");
                } else  //nu au fost primite monede
                    System.out.println("\nNumar monede: " + personaj.inventory.nr_monede + "\n");

                //daca celula curenta este de tipul "ENEMY" se vor afisa informatii despre viata si mana curenta a personajelor
                //la inceputul luptei si pe tot parcursul ei, luptandu-se pana la moartea unuia dintre ei
            } else if (cell.element != null && cell.element instanceof Enemy) {
                System.out.println("\nAi un inamic!\n" + "\nInformatii inamic:\n" + cell.element);
                System.out.println("\nInformatii personaj:\nViata curenta: " + personaj.current_life
                        + "\nMana curenta: " + personaj.current_power);
                List<String> options = new ArrayList<>(3);
                options.add("Ataca normal inamicul.");
                options.add("Foloseste abilitate.");
                options.add("Foloseste potiune.");
                System.out.println("\nIata optiunile tale:");
                for (int i = 0; i < 3; i++)
                    System.out.println(i + ") " + options.get(i));

                while (personaj.abilities.size() >= 1) {   // la inceput personajul isi va folosi toate abilitatile

                    //lupta va fi pe ture: fiecare va ataca o data
                    if (((Enemy) cell.element).current_life >= 1 && personaj.current_life >= 1) {
                        System.out.println("\nRandul tau:");
                        personaj.use_ability((Enemy) cell.element, personaj.abilities.get(0));
                    }
                    if (personaj.current_life >= 1 && ((Enemy) cell.element).current_life >= 1) {
                        System.out.println("\nRandul inamicului:");
                        //inamicul are sanse de 20% de a folosi o abilitate
                        ((Enemy) cell.element).alege_tip_atac(personaj);
                    }

                    //personajul pierde
                    if (personaj.current_life <= 0) {
                        System.out.println("Ai fost invins!");
                        System.exit(0);
                    }

                    //personajul invinge
                    if (((Enemy) cell.element).current_life <= 0) {
                        System.out.println("Ai invins!");
                        experienta_castigata += 20;  // experienta castigata pentru invingerea unui inamic
                        personaj.experience += experienta_castigata;
                        personaj.level_update();

                        double val = Math.random() * 100;
                        if (val < 80) {  //sanse de 80% sa primeasca monede dupa victorie
                            System.out.println("\nNumar monede: " + personaj.inventory.nr_monede);
                            System.out.println("Ce noroc! Ai castigat monede dupa victorie!");
                            int monede;
                            monede = (int) Math.floor(Math.random() * (20 - 10 + 1) + 10);//poate castiga intre 10-20 monede
                            monede_castigate += monede;
                            personaj.inventory.nr_monede += monede;
                            System.out.println("Numar curent monede: " + personaj.inventory.nr_monede + "\n");

                        } else {
                            System.out.println("Victoria nu ti-a adus monede!");
                            System.out.println("Numar monede: " + personaj.inventory.nr_monede);
                        }
                    }
                }

                //apoi lupta continua pana la victoria unuia, personajul atacand normal sau incercand sa foloseasca o potiune
                while (personaj.current_life >= 1 && ((Enemy) cell.element).current_life >= 1) {

                    //daca viata personajului este <20, iar inventarul mai contine o potiune de viata,
                    //atat timp cat inamicul nu a fost invins deja, aceasta se va folosi
                    if (personaj.current_power < 20 && ((Enemy) cell.element).current_life >= 0 &&
                            (personaj.inventory.check_availabilty(new ManaPotion()))) {
                        System.out.println("\nMana curenta < 20! Se va incerca folosirea unei potiuni de mana!\n");
                        Potion p1 = new ManaPotion();
                        p1.use_potion(personaj);
                        System.out.println("Inventarul curent: " + personaj.inventory.potions);

                        //analog
                    } else if (personaj.current_life < 20 && ((Enemy) cell.element).current_life >= 0 &&
                            (personaj.inventory.check_availabilty(new HealthPotion()))) {

                        System.out.println("\nViata curenta < 20! Se va incerca folosirea unei potiuni de viata!\n");
                        Potion p2 = new HealthPotion();
                        p2.use_potion(personaj);
                        System.out.println("Inventarul curent: " + personaj.inventory.potions);

                        //daca nu a fost folosita o potiune, personajul si inamicul se vor ataca reciproc
                    } else {
                        if (((Enemy) cell.element).current_life >= 1 && personaj.current_life >= 1) {
                            System.out.println("\nRandul tau:");
                            ((Enemy) cell.element).receiveDamage(personaj.getDamage());  //personajul ataca normal
                        }
                    }
                    if (personaj.current_life >= 1 && ((Enemy) cell.element).current_life >= 1) {
                        System.out.println("\nRandul inamicului:");
                        ((Enemy) cell.element).alege_tip_atac(personaj);
                    }

                    //personajul a pierdut
                    if (((Enemy) cell.element).current_life >= 1 && personaj.current_life <= 0) {
                        System.out.println("Ai fost invins!");
                        System.exit(0);
                    }

                    //personajul a castigat
                    if (((Enemy) cell.element).current_life <= 0) {
                        System.out.println("\n\nAi invins!");
                        experienta_castigata += 20;  // experienta castigata pentru invingerea unui inamic
                        personaj.experience += experienta_castigata;
                        personaj.level_update();

                        double val = Math.random() * 100;
                        if (val < 80) {  //sanse de 80% sa primeasca monede dupa victorie
                            System.out.println("\nNumar monede: " + personaj.inventory.nr_monede);
                            System.out.println("Ce noroc! Ai castigat monede dupa victorie!");
                            int monede;
                            monede = (int) Math.floor(Math.random() * (20 - 10 + 1) + 10);//poate castiga intre 10-20 monede
                            monede_castigate += monede;
                            personaj.inventory.nr_monede += monede;
                            System.out.println("Numar curent monede: " + personaj.inventory.nr_monede + "\n");
                        } else
                            System.out.println("Victoria nu ti-a adus monede!");
                        System.out.println("Numar monede: " + personaj.inventory.nr_monede + "\n");
                    }
                }

                //daca celula curenta este de tipul "SHOP" personajul va cumpara o potiune de mana si una de viata
            } else if (cell.element != null && cell.element instanceof Shop) {

                System.out.println("\nIata ce potiuni poti cumpara:");
                for (int i = 0; i < ((Shop) cell.element).potions.size(); i++)
                    System.out.println(((Shop) cell.element).potions.get(i) + "; Pret:" +
                            ((Shop) cell.element).potions.get(i).get_price());
                System.out.println("\nNumar monede: " + personaj.inventory.nr_monede);
                System.out.println("Greutate inventar ramasa: " + personaj.inventory.left_weight() + "\n");
                System.out.println("Se incearca cumpararea unei potiuni de viata...");
                personaj.buy_potion((Shop) cell.element, new HealthPotion());
                System.out.println("Se incearca cumpararea unei potiuni de mana...");
                personaj.buy_potion((Shop) cell.element, new ManaPotion());
                System.out.println("Inventarul curent: " + personaj.inventory.potions);
                System.out.println("Numar monede ramase: " + personaj.inventory.nr_monede);
                System.out.println("Greutate inventar ramasa: " + personaj.inventory.left_weight() + "\n");

                //daca personajul se afla pe o casuta de "FINISH" se vor afisa informatii despre jucator
                // si despre experienta si monedele acumulate
            } else if (cell.cells != null && cell.cells.toString().equals("FINISH")) {
                experienta_castigata += 25;  // experienta castigata pentru terminarea unui joc
                personaj.experience += experienta_castigata;
                personaj.level_update();
                int monede = 30;
                monede_castigate += monede;  //monedele castigate pentru terminarea unui joc
                personaj.inventory.nr_monede += monede;
                System.out.println("\nSfarsitul jocului!");
                System.out.println("\nExperienta castigata pe parcursul jocului: " + experienta_castigata);
                System.out.println("Numar monede acumulate: " + monede_castigate);
                System.out.println("\nInformatii jucator:\nNume: " + personaj.nume);
                System.out.println("Profesie: " + personaj.getClass().getName());
                System.out.println("Nivel: " + personaj.level);
                System.out.println("Experienta: " + personaj.experience);
            }
            cell.vizitat = true;
        }
    }
}

class Account {
    Information inf;   //informatii despre jucator
    List<Personaj> personaje = new ArrayList<>();   //lista cu personajele contului
    int nr_jocuri;  //numarul de jocuri jucate

    public String toString() {
        return "\n" + inf + "\nNr jocuri: " + nr_jocuri + "\n" + personaje + "\n";
    }

    public void set_information(Information inf) {
        this.inf = inf;
    }

    public Information get_informations() {
        return inf;
    }

    //Sablonul Builder folosit pentru a instantia un obiect de tipul Information
    class Information {

        private final Credentials cred;
        private final List<String> games;
        private final String nume_player;
        private final String country;

        public Information(Information_Builder builder) {

            this.cred = builder.cred;
            this.games = builder.games;
            this.nume_player = builder.nume_player;
            this.country = builder.country;
        }

        public Credentials get_cred() {
            return cred;
        }

        public String get_country() {
            return country;
        }

        public List<String> get_games() {
            return games;
        }

        public String get_nume() {
            return nume_player;
        }

        public static class Information_Builder {

            private Credentials cred = null;
            private List<String> games = new ArrayList<>();
            private final String nume_player;
            private String country;

            public Information_Builder(String nume_player) {
                this.nume_player = nume_player;
            }

            public Information_Builder set_credentials(Credentials cred) {
                this.cred = cred;
                return this;
            }

            public Information_Builder set_favourite_games(List<String> games) {
                this.games = games;
                return this;
            }

            public Information_Builder set_country(String country) {
                this.country = country;
                return this;
            }

            //exceptie la incercarea realizarii unui obiect Information fara credentiale sau nume
            public Information build() throws InformationIncompleteException {
                if (cred == null || nume_player == null) {
                    throw new InformationIncompleteException();
                }
                return new Account().new Information(this);
            }
        }
    }
}

class InformationIncompleteException extends Exception {
    public InformationIncompleteException() {
        super("Credentials or name can not be null");
    }
}

class Credentials {
    private final String email;  //datele vor fi incapsulate
    private final String pass;

    public String toString() {
        String s = "";
        for (int i = 0; i < pass.length(); i++)
            s += "*"; //parola va fi ascunsa
        return "Email:" + email + "   Parola: " + s;
    }

    public Credentials(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}

//clasa care modeleaza informatiile despre personaje
class Personaj {
    String name, proffesion;
    String level;
    long experience;

    public Personaj(String name, String proffesion, String level, long experience) {
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.proffesion = proffesion;
    }

    public String toString() {
        return "Nume: " + name + " \nProfesie: " + proffesion + " \nNivel: " + level + " \nExperienta: " + experience + "\n";
    }
}