import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

//clasa care va modela pagina de autentificare a jocului
class Autentificare extends JFrame implements ListSelectionListener, ActionListener {
    JList<Credentials> list;
    JList<Personaj> list2;
    JScrollPane scr, scr2;
    GridLayout layout;
    JLabel text, text2;
    JTextArea info;
    JPanel open_panel;
    JPanel panel;
    JButton button;
    JButton button2;

    int index;

    Vector<Credentials> conturi = new Vector<>();
    Vector<Personaj> personaje = new Vector<>();

    String player_name, player_country;
    int player_nr_maps;
    List<String> player_games;
    Game g;

    //obiect Game transmis drept argument, intrucat acesta poate fi instantiat doar o data
    public Autentificare(String titlu, Game g) {
        super(titlu);
        this.g = g;

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //primul panel pentru incepera jocului
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(650, 400));
        panel.setBackground(Color.gray);
        button = new JButton("Apasa pentru a trece la alegerea contului:");
        button.setFont(new Font("Calibri", Font.BOLD, 18));
        button.setBackground(Color.orange);
        button.addActionListener(this);
        text = new JLabel("Sa inceapa jocul!");
        text.setFont(new Font("Playbill", Font.PLAIN, 50));
        text.setAlignmentX(CENTER_ALIGNMENT);
        button.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(text);
        panel.add(button);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setVisible(true);
        add(panel, BorderLayout.CENTER);

        getLayeredPane().getComponent(1).setFont(new Font("ALGERIAN", Font.PLAIN, 18));

        for (int i = 0; i < g.conturi.size(); i++) {
            Account.Information info = g.conturi.get(i).get_informations();
            Credentials c = info.get_cred();
            conturi.add(c);
        }

        //lista conturilor din clasa Game parsate din "accounts.json"
        list = new JList<>(conturi);
        list.addListSelectionListener(this);  //doresc sa aleg un cont
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        scr = new JScrollPane(list);
        scr.setPreferredSize(new Dimension(650, 400));
        scr.getViewport().getView().setBackground(Color.lightGray);
        scr.getViewport().getView().setForeground(Color.black);
        Font font = new Font("Dialog", Font.ITALIC, 14);
        scr.getViewport().getView().setFont(font);

        button2 = new JButton("Alege personajul dorit:");
        button2.setFont(new Font("Calibri", Font.BOLD, 18));
        button2.setBackground(Color.orange);
        button2.addActionListener(this);
        text2 = new JLabel();
        text2.setFont(new Font("Playbill", Font.PLAIN, 50));
        text2.setAlignmentX(CENTER_ALIGNMENT);
        button2.setAlignmentX(CENTER_ALIGNMENT);

        info = new JTextArea(20, 5);
        info.setFont(new Font("Serif", Font.ITALIC, 20));
        info.setAlignmentX(CENTER_ALIGNMENT);
        info.setBackground(Color.gray);

        layout = new GridLayout(1, 1);
        setLayout(layout);
        pack();
        show();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (list.isSelectionEmpty())
            return;
        else {
            Credentials selectat = list.getSelectedValue();
            index = conturi.indexOf(selectat);  //salvez index-ul contului selectat
            getContentPane().remove(scr);

            Account.Information inf = g.conturi.get(index).get_informations();
            List<Personaj> pers=g.conturi.get(index).personaje;  //lista personajelor contului selectat
            for(int i=0;i<pers.size();i++){
                personaje.add(pers.get(i));
            }

            //informatii generale despre jucator
            player_name = inf.get_nume();
            player_country = inf.get_country();
            player_games = inf.get_games();
            player_nr_maps = g.conturi.get(index).nr_jocuri;

            text2.setText("Bun venit, " + player_name + "!\n");
            info.setText("Informatii despre jucator:\n" + "Tara: " + player_country + "\nJocuri preferate: " +
                    player_games + "\nNumar jocuri completate: " + player_nr_maps);

            panel.add(text2);
            panel.add(info);
            panel.add(button2);
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            add(panel, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
            this.pack();
        }

        //odata ales personajul inchid fereastra pentru pagina de autentificare
        if (list2 != null && !list2.isSelectionEmpty()) {
            dispose();  //inchid aceasta fereastra

            Personaj personaj= list2.getSelectedValue();
            //instantiez obiectul de tip "PaginaFinal" caruia ii dau ca argument
            // informatii generale despre personaj si progresul sau
            PaginaFinal f=new PaginaFinal("Pagina de final", player_name,
                    player_nr_maps+1,Integer.parseInt(personaj.level),personaj.experience);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();

             //daca este apasat primul buton voi afisa JScrollPane-ul cu lista conturilor
            if (button.getText().equals("Apasa pentru a trece la alegerea contului:")) {
                scr.setVisible(true);
                panel.remove(button);
                panel.remove(text);
                getContentPane().remove(panel);
                add(scr);
                setTitle("Pagina de autentificare");
                this.revalidate();
                this.repaint();
                this.pack();

                //daca este apasat primul buton voi afisa JScrollPane-ul cu lista personajelor
            } else if (button.getText().equals( "Alege personajul dorit:")) {

                getContentPane().remove(panel);
                list2 = new JList<>(personaje);
                list2.addListSelectionListener(this);
                list2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                list2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                scr2 = new JScrollPane(list2);
                scr2.setPreferredSize(new Dimension(650, 400));
                scr2.getViewport().getView().setBackground(Color.lightGray);
                scr2.getViewport().getView().setForeground(Color.black);
                scr2.getViewport().getView().setFont(new Font("Dialog", Font.ITALIC, 14));
                add(scr2);
                setTitle("Pagina de autentificare");
                this.revalidate();
                this.repaint();
                this.pack();
            }
        }
    }
}
