package bddairline;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;

/*Cette classe gère les fenêtre d'ajout d'élément
Attributs :
-listeFields la liste des champs de texte
-tablesql l'objet TableSQL concerné par l'ajout
*/
public class DialogAjout extends JDialog{
    private JTextField[] listeFields;
    private TableSQL tablesql;
    
    /*Le constructeur de la classe
    Paramètres :
    -parent l'objet JFrame parent (la fenêtre principale)
    -titre le titre de la fenêtre
    -modal le type de modalité de la fenêtre (si il bloque ou non la fenêtre principale)
    -table la TableSQL concernée
    */
    public DialogAjout(JFrame parent, String titre, ModalityType modal, TableSQL table){
        //On intialise la boîte de dialogue
        super(parent,titre,modal);
        tablesql=table;
        //On règle la disposition
        this.setLayout(new GridLayout(0,2));
        listeFields=new JTextField[tablesql.attributs.size()];
        //Pour chaque attribut de la table, on crée un champ de texte et un label le décrivant
        for(int i=0;i<table.attributs.size();i++){
            this.add(new JLabel((String)tablesql.attributs.get(i)[0]));
            JTextField tf=new JTextField();
            listeFields[i]=tf;
            this.add(tf);
        }
        //On crée un bouton ajouter qui lance la méthode ajout quand on clique dessus
        JButton boutonAjouter=new JButton("Ajouter");
        this.add(boutonAjouter);
        boutonAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajout();
            }
        });
        //On crée un bouton Terminer qui ferme la fenêtre quand on clique dessus
        JButton boutonTerminer=new JButton("Terminer");
        boutonTerminer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(boutonTerminer);
        //On affiche la fenêtre
        this.pack();
        this.setVisible(true);  
    }
    
    //Cette méthode permet d'ajouter l'élément dont on a renseigné les attributs.
    public void ajout(){
        //On crée la requete
        String requete="INSERT INTO "+tablesql.nom+" VALUES (";
        //Pour cela, on récupère les données entrées et on les incorpore.
        for(int i=0;i<listeFields.length;i++){
            if(i!=0){
                requete+=",";
            }
            requete+="'"+listeFields[i].getText()+"'";
        }
        requete+=")";
        //Puis on lance la requête
        try{
            BDDAirline.faireRequete("Ajout", requete, tablesql.nom);
        }
        catch(SQLException ex){
            //En cas d'exception SQL, on affiche le message d'erreur correspondant dans une fenêtre.
            String messageErreur=ex.getMessage();
            JOptionPane.showMessageDialog(this,messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
}
