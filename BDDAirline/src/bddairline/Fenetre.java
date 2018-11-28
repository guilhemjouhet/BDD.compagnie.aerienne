package bddairline;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.SQLException;


/* Cette classe gère la fenêtre principale du programme.
Attributs :
-dictTables, qui associe les éléments graphiques JTable a des noms
-profil, le profil d'utilisateur choisi
-bListener, qui gère l'appui sur les boutons
-choixProfil, la boite de dialogue qui permet le choix du profil d'utilisateur
*/
public class Fenetre extends JFrame{
    public Map<String,JTable> dictTables;
    private String profil;
    private ButtonListener bListener;
    private JDialog choixProfil;
    
    /*Le constructeur de la classe
    Paramètre:
    -titre, le nom à donner à la fenêtre.
    */
    public Fenetre(String titre){
        //On initialise l'objet comme un JFrame classique
        super(titre);
        JPanel p = (JPanel) this.getContentPane();
        //On gère la taille et la disposition des éléments
        this.setPreferredSize(new Dimension(680, 460));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
        //On crée bListener
        bListener=new ButtonListener(this);
        //On gère la fermeture de la fenêtre/du programme et on affiche la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        
        //On crée la fenêtre choixProfil
        choixProfil=new JDialog(this,"Choix du Profil");
        choixProfil.getContentPane().setLayout(new BoxLayout(choixProfil.getContentPane(),BoxLayout.PAGE_AXIS));
        choixProfil.add(new JLabel("Choisissez votre profil :"));
        
        //On ajoute un bouton pour chaque profil, on le rattache a bListener.
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
        
        //Puis on rend choixProfil visible
        choixProfil.pack();
        choixProfil.setVisible(true);
    }
    
    /*Cette méthode initialise l'interface graphique en fonction du profil d'utilisateur.
    Paramètre :
    -commande, le profil choisi
    */
    public void initialise(String commande){
        //On supprime choixProfil (plus nécessaire)
        choixProfil.dispose();
        //On crée un JTabbedPane qui permet d'afficher des onglets
        JTabbedPane tab=new JTabbedPane();
        dictTables=new HashMap<String,JTable>();
        
        //Selon le profil...
        profil=commande;
        if (profil.equals("Administrateur")){
            //Pour un administrateur
            Iterator it = BDDAirline.organisation.entrySet().iterator();
            while(it.hasNext()){
                //On récupère les données de toutes les tables
                Map.Entry me = (Map.Entry)it.next();
                TableSQL table=(TableSQL) me.getValue();
                String nomTable=table.nom;
                dictTables.put(nomTable,new JTable());
                try{
                    BDDAirline.faireRequete("Select","SELECT * FROM "+nomTable,nomTable);
                }catch(SQLException ex){}
                
                //On les affiches chacune dans un onglet
                JPanel panelTab=new JPanel();
                panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
                JMenuBar barre=new JMenuBar();
                panelTab.add(barre);
                
                //On ajoute une barre de menu pour modifier les données
                JButton bAjout=new JButton("Ajouter");
                bAjout.setActionCommand("Ajouter"+nomTable);
                bAjout.addActionListener(bListener);
                barre.add(bAjout);
                
                JButton bSuppr=new JButton("Supprimer");
                bSuppr.setActionCommand("Supprimer"+nomTable);
                bSuppr.addActionListener(bListener);
                barre.add(bSuppr);
                
                //On met tout dans un conteneur permettant de scroller
                panelTab.add(new JScrollPane(dictTables.get(nomTable)));
                tab.addTab(nomTable,panelTab);
            }
        }
        else if (profil.equals("Pilote")){
            //Si c'est un pilote
            JPanel panelTab=new JPanel();
            panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
            
            //On demande le nom et le prénom
            String nom = (String)JOptionPane.showInputDialog(this,"Quel est votre nom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String prenom = (String)JOptionPane.showInputDialog(this,"Quel est votre prenom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            dictTables.put("pilotes",new JTable());
            panelTab.add(new JScrollPane(dictTables.get("pilotes")));
            //On récupère les données correspondant a ce nom dans la BDD
            try{
                BDDAirline.faireRequete("Select","SELECT * FROM pilotes WHERE nom='"+nom+"' AND prenom='"+prenom+"'","pilotes");
            }catch(SQLException ex){
                String messageErreur=ex.getMessage();
                JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
            }
            //On met tout dans un onglet
            tab.addTab("Pilote",panelTab);
        }
        else if (profil.equals("Equipage")){
            //De même si c'est un membre de l'équipage
            JPanel panelTab=new JPanel();
            panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
            
            String nom = (String)JOptionPane.showInputDialog(this,"Quel est votre nom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String prenom = (String)JOptionPane.showInputDialog(this,"Quel est votre prenom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            dictTables.put("equipage",new JTable());
            panelTab.add(new JScrollPane(dictTables.get("equipage")));
            try{
                BDDAirline.faireRequete("Select","SELECT * FROM equipage WHERE nom='"+nom+"' AND prenom='"+prenom+"'","equipage");
            }catch(SQLException ex){
                String messageErreur=ex.getMessage();
                JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
            }
            tab.addTab("Equipage",panelTab);
        }
        else if (profil.equals("Personnel Sol")){
            //De même pour les employés au sol
            JPanel panelTab=new JPanel();
            panelTab.setLayout(new BoxLayout(panelTab,BoxLayout.PAGE_AXIS));
            
            String nom = (String)JOptionPane.showInputDialog(this,"Quel est votre nom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String prenom = (String)JOptionPane.showInputDialog(this,"Quel est votre prenom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            dictTables.put("personnel_sol",new JTable());
            panelTab.add(new JScrollPane(dictTables.get("personnel_sol")));
            try{
                BDDAirline.faireRequete("Select","SELECT * FROM personnel_sol WHERE nom='"+nom+"' AND prenom='"+prenom+"'","personnel_sol");
            }catch(SQLException ex){
                String messageErreur=ex.getMessage();
                JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
            }
            tab.addTab("Personnel Sol",panelTab);
        }
        else if (profil.equals("Passager")){
            //Pareil pour un passager mais on récupère aussi les infos sur les billets et les réservations
            String nom = (String)JOptionPane.showInputDialog(this,"Quel est votre nom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            String prenom = (String)JOptionPane.showInputDialog(this,"Quel est votre prenom?","Customized Dialog",JOptionPane.PLAIN_MESSAGE);
            
            JPanel[] panels=new JPanel[3];
            for(int i=0;i<3;i++){
                panels[i]=new JPanel();
                panels[i].setLayout(new BoxLayout(panels[i],BoxLayout.PAGE_AXIS));
            }
            
            dictTables.put("passagers",new JTable());
            panels[0].add(new JScrollPane(dictTables.get("passagers")));
            try{
                BDDAirline.faireRequete("Select","SELECT * FROM passagers WHERE nom='"+nom+"' AND prenom='"+prenom+"'","passagers");
            }catch(SQLException ex){
                String messageErreur=ex.getMessage();
                JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
            }
            
            dictTables.put("billets",new JTable());
            panels[1].add(new JScrollPane(dictTables.get("billets")));
            try{
                BDDAirline.faireRequete("Select","SELECT billets.* FROM reservation,passagers,billets WHERE passagers.nom='"+nom+"' AND passagers.prenom='"+prenom+"' AND passagers.idpassager=reservation.idpassager AND reservation.numerobillet=billets.numerobillet","billets");
            }catch(SQLException ex){
                String messageErreur=ex.getMessage();
                JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
            }
            
            dictTables.put("reservation",new JTable());
            panels[2].add(new JScrollPane(dictTables.get("reservation")));
            try{
                BDDAirline.faireRequete("Select","SELECT reservation.* FROM reservation,passagers WHERE passagers.nom='"+nom+"' AND passagers.prenom='"+prenom+"' AND passagers.idpassager=reservation.idpassager","reservation");
            }catch(SQLException ex){
                String messageErreur=ex.getMessage();
                JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
            }
            tab.addTab("Passager",panels[0]);
            tab.addTab("Billets",panels[1]);
            tab.addTab("Reservations",panels[2]);
        }
        
        this.add(tab);
        this.pack();
    }
    
    /*Cette méthode permet de remplir les tables de l'interface graphique avec les
    données récupérées par un requête
    Paramètres :
    -donnees les données à mettre dans la tabl
    -colonnes le nom des colonnes
    -tableARemplir le nom de la table à remplir
    */
    public void remplirTable(Object[][] donnees,String[] colonnes,String tableARemplir){
        //On met a jour la table
        DefaultTableModel tablemodel=new DefaultTableModel(donnees,colonnes);
        dictTables.get(tableARemplir).setModel(tablemodel);
    }
}
