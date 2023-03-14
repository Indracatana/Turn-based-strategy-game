import javax.swing.*;
import java.awt.*;

//clasa care va modela pagina de final a jocului
class PaginaFinal extends JFrame {

    GridLayout layout;
    JTextArea info;
    JPanel panel;
    JLabel txt;

    String player_name;
    int player_nr_maps;
    int level;
    long experience;

    public PaginaFinal(String titlu, String player_name,
                       int player_nr_maps, int level, long experience) {
        super(titlu);
        //informatii despre jucator
        this.player_name = player_name;
        this.player_nr_maps = player_nr_maps;
        this.level = level;
        this.experience = experience;

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(650, 400));
        panel.setBackground(Color.gray);
        txt = new JLabel("Jocul a luat sfarsit!");
        txt.setFont(new Font("Playbill", Font.PLAIN, 50));
        txt.setAlignmentX(CENTER_ALIGNMENT);
        info = new JTextArea(10, 10);
        info.setText("\nNume: " + player_name +"\n\nProgresul final:"+
                "\nNumar jocuri completate: " + player_nr_maps + "\nNivel: " +
                level + "\nExperienta: " + experience);
        info.setFont(new Font("Dialog", Font.PLAIN, 20));
        info.setBackground(Color.gray);
        info.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(txt);
        panel.add(info);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setVisible(true);
        add(panel, BorderLayout.CENTER);
        getLayeredPane().getComponent(1).setFont(new Font("ALGERIAN", Font.PLAIN, 18));

        layout = new GridLayout(1, 1);
        setLayout(layout);
        pack();
        show();
    }

}
