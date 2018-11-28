package bddairline;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JTable;

/*Cette classe gère les boîtes de dialogue pour la suppression d'éléments
Attributs :
-listeFields la liste des champs de texte
-tablesql l'objet TableSQL concerné par l'ajout
-primaires la liste des clés primaires de la table
-f la fenêtre principale
*/
public class DialogSuppr extends JDialog{
    private JTextField[] listeFields;
    private TableSQL tablesql;
    private ArrayList<String> primaires;
    private Fenetre f;
    
    /*Le constructeur de la classe
    Paramètres :
    -parent l'objet JFrame parent (la fenêtre principale)
    -titre le titre de la fenêtre
    -modal le type de modalité de la fenêtre (si il bloque ou non la fenêtre principale)
    -table la TableSQL concernée
    */
    public DialogSuppr(Fenetre parent, String titre, Dialog.ModalityType modal, TableSQL table){
        //On initalise la boîte de dialogue
        super(parent,titre,modal);
        f=parent;
        tablesql=table;
        
        //On réucpère la liste des clés primaires de la table
        primaires=new ArrayList<String>();
            for (int i=0;i<tablesql.attributs.size();i++){
                if (tablesql.attributs.get(i)[2].equals("Primaire")){
                    primaires.add((String)tablesql.attributs.get(i)[0]);
                }
            }
         
        //On règle la disposition de la fenêtre
        this.setLayout(new GridLayout(0,2));
        listeFields=new JTextField[primaires.size()];
        //On crée un champ de texte pour chaque clé primaire de la table
        for(int i=0;i<primaires.size();i++){
            this.add(new JLabel(primaires.get(i)));
            JTextField tf=new JTextField();
            listeFields[i]=tf;
            this.add(tf);
        }
        //On crée un bouton supprimer qui lance la méthode suppression quand on appuie dessus
        JButton boutonSupprimer=new JButton("Supprimer");
        this.add(boutonSupprimer);
        boutonSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suppression();
            }
        });
        //On crée un bouton terminer qui ferme la fenêtre quand on appuie dessus
        JButton boutonTerminer=new JButton("Terminer");
        boutonTerminer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(boutonTerminer);
        //On rend la fenêtre visible
        this.pack();
        this.setVisible(true);  
    }
    
    //Cette méthode permet la suppression de l'élément indiqué
    public void suppression(){
        //On consruit la requete
        String requete="DELETE FROM "+tablesql.nom+" WHERE";
        //Pour cela, on récupère les clés primaires de l'élément indiqué
        for(int i=0;i<listeFields.length;i++){
            requete+=" "+primaires.get(i)+"='"+listeFields[i].getText()+"'";
            if(i+1<listeFields.length){
                requete+=" AND";
            }
        }
        try{
            //On lance la requête
            BDDAirline.faireRequete("Suppression", requete, tablesql.nom);
            Iterator it = BDDAirline.organisation.entrySet().iterator();
            /*On actualise toutes les tables au cas ou certains éléments dépendaient
            de celui supprimé*/
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                TableSQL table=(TableSQL) me.getValue();
                String nomTable=table.nom;
                try{
                    BDDAirline.faireRequete("Select","SELECT * FROM "+nomTable,nomTable);
                }catch(SQLException ex){}
            }
        }
        catch(SQLException ex){
            //En cas d'exception SQL, on affiche le message d'erreur correspondant.
            String messageErreur=ex.getMessage();
            JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
}
