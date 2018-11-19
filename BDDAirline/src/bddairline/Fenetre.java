package bddairline;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class Fenetre extends JFrame{
    private JTable table;
            
    public Fenetre(String titre){
        super(titre);
        JPanel p = (JPanel) this.getContentPane();
        this.setPreferredSize(new Dimension(680, 460));
        MenuListener menulistener=new MenuListener();
        
        JMenuBar barre = new JMenuBar();
        JButton menuappareils = new JButton("Appareils");
        menuappareils.setActionCommand("appareils");
        menuappareils.addActionListener(menulistener);
        barre.add(menuappareils);
        JButton menuliaisons = new JButton("Liaisons");
        menuliaisons.setActionCommand("liaison");
        menuliaisons.addActionListener(menulistener);
        barre.add(menuliaisons);
        JButton menuvols = new JButton("Vols");
        menuvols.setActionCommand("vol");
        menuvols.addActionListener(menulistener);
        barre.add(menuvols);
        this.setJMenuBar(barre);
        
        
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        this.add(scrollPane);
    }
    
    public void remplirTable(Object[][] donnees,String[] colonnes){
        DefaultTableModel tablemodel=new DefaultTableModel(donnees,colonnes);
        table.setModel(tablemodel);
    }
}
