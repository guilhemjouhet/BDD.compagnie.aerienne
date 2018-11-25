package bddairline;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;

public class Fenetre extends JFrame{
    private Map<String,JTable> dictTables;
    private String profil;
    private ButtonListener bListener;
    private JDialog choixProfil;
    
    public Fenetre(String titre){
        super(titre);
        JPanel p = (JPanel) this.getContentPane();
        this.setPreferredSize(new Dimension(680, 460));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
        bListener=new ButtonListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        
        choixProfil=new JDialog(this,"Choix du Profil");
        choixProfil.getContentPane().setLayout(new BoxLayout(choixProfil.getContentPane(),BoxLayout.PAGE_AXIS));
        choixProfil.add(new JLabel("Choisissez votre profil :"));
        
        JButton boutonAdmin=new JButton("Administrateur");
        boutonAdmin.setActionCommand("Administrateur");
        boutonAdmin.addActionListener(bListener);
        choixProfil.add(boutonAdmin);
        
        JButton boutonPassager=new JButton("Passager");
        boutonPassager.setActionCommand("Passager");
        boutonPassager.addActionListener(bListener);
        choixProfil.add(boutonPassager);
       
        JButton boutonPilote=new JButton("Pilote");
        boutonPilote.setActionCommand("Pilote");
        boutonPilote.addActionListener(bListener);
        choixProfil.add(boutonPilote);
                
        JButton boutonEquip=new JButton("Equipage");
        boutonEquip.setActionCommand("Equipage");
        boutonEquip.addActionListener(bListener);
        choixProfil.add(boutonEquip);
        
        JButton boutonPsol=new JButton("Personnel Sol");
        boutonPsol.setActionCommand("Personnel Sol");
        boutonPsol.addActionListener(bListener);
        choixProfil.add(boutonPsol);
        
        choixProfil.pack();
        choixProfil.setVisible(true);
    }
    
    public void initialise(String commande){
        choixProfil.dispose();
        JTabbedPane tab=new JTabbedPane();
        dictTables=new HashMap<String,JTable>();
        profil=commande;
        
        if (commande.equals("Administrateur")){
            Iterator it = BDDAirline.organisation.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                TableSQL table=(TableSQL) me.getValue();
                String nomTable=table.nom;
                dictTables.put(nomTable,new JTable());
                BDDAirline.faireRequete("Select","SELECT * FROM "+nomTable,nomTable);
                
                JPanel panelTab=new JPanel();
                panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
                JMenuBar barre=new JMenuBar();
                panelTab.add(barre);
                JButton bAjout=new JButton("Ajouter");
                bAjout.setActionCommand("Ajouter"+nomTable);
                bAjout.addActionListener(bListener);
                barre.add(bAjout);
                panelTab.add(new JScrollPane(dictTables.get(nomTable)));
                tab.addTab(nomTable,panelTab);
            }
        }
        else if (commande.equals("Pilote")){
            JPanel panelTab=new JPanel();
            panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
            String nomPrenom = (String)JOptionPane.showInputDialog(this,"Quel sont vos nom et prenom ?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String[] nomPrenomsp=nomPrenom.split(" ");
            dictTables.put("pilotes",new JTable());
            panelTab.add(new JScrollPane(dictTables.get("pilotes")));
            BDDAirline.faireRequete("Select","SELECT * FROM pilotes WHERE nom='"+nomPrenomsp[0]+"' AND prenom='"+nomPrenomsp[1]+"'","pilotes");
            tab.addTab("Pilote",panelTab);
        }
        else if (commande.equals("Equipage")){
            JPanel panelTab=new JPanel();
            panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
            String nomPrenom = (String)JOptionPane.showInputDialog(this,"Quel sont vos nom et prenom ?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String[] nomPrenomsp=nomPrenom.split(" ");
            dictTables.put("equipage",new JTable());
            panelTab.add(new JScrollPane(dictTables.get("equipage")));
            BDDAirline.faireRequete("Select","SELECT * FROM equipage WHERE nom='"+nomPrenomsp[0]+"' AND prenom='"+nomPrenomsp[1]+"'","equipage");
            tab.addTab("Equipage",panelTab);
        }
        else if (commande.equals("Personnel Sol")){
            JPanel panelTab=new JPanel();
            panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
            String nomPrenom = (String)JOptionPane.showInputDialog(this,"Quel sont vos nom et prenom ?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String[] nomPrenomsp=nomPrenom.split(" ");
            dictTables.put("personnel_sol",new JTable());
            panelTab.add(new JScrollPane(dictTables.get("personnel_sol")));
            BDDAirline.faireRequete("Select","SELECT * FROM personnel_sol WHERE nom='"+nomPrenomsp[0]+"' AND prenom='"+nomPrenomsp[1]+"'","personnel_sol");
            tab.addTab("Personnel Sol",panelTab);
        }
        else if (commande.equals("Passager")){
            String nomPrenom = (String)JOptionPane.showInputDialog(this,"Quel sont vos nom et prenom ?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String[] nomPrenomsp=nomPrenom.split(" ");
            JPanel[] panels=new JPanel[3];
            for(int i=0;i<3;i++){
                panels[i]=new JPanel();
                panels[i].setLayout(new BoxLayout(panels[i],BoxLayout.PAGE_AXIS));
            }
            
            dictTables.put("passagers",new JTable());
            panels[0].add(new JScrollPane(dictTables.get("passagers")));
            BDDAirline.faireRequete("Select","SELECT * FROM passagers WHERE nom='"+nomPrenomsp[0]+"' AND prenom='"+nomPrenomsp[1]+"'","passagers");
            
            dictTables.put("billets",new JTable());
            panels[1].add(new JScrollPane(dictTables.get("billets")));
            BDDAirline.faireRequete("Select","SELECT billets.* FROM reservation,passagers,billets WHERE passagers.nom='"+nomPrenomsp[0]+"' AND passagers.prenom='"+nomPrenomsp[1]+"' AND passagers.idpassager=reservation.idpassager AND reservation.numerobillet=billets.numerobillet","billets");
            
            dictTables.put("reservation",new JTable());
            panels[2].add(new JScrollPane(dictTables.get("reservation")));
            BDDAirline.faireRequete("Select","SELECT reservation.* FROM reservation,passagers WHERE passagers.nom='"+nomPrenomsp[0]+"' AND passagers.prenom='"+nomPrenomsp[1]+"' AND passagers.idpassager=reservation.idpassager","reservation");
            
            tab.addTab("Passager",panels[0]);
            tab.addTab("Billets",panels[1]);
            tab.addTab("Reservations",panels[2]);
        }
        
        this.add(tab);
        this.pack();
    }
    
    public void remplirTable(Object[][] donnees,String[] colonnes,String tableARemplir){
        DefaultTableModel tablemodel=new DefaultTableModel(donnees,colonnes);
        dictTables.get(tableARemplir).setModel(tablemodel);
    }
}
