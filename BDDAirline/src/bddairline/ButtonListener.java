package bddairline;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dialog;
import java.util.*;
import java.sql.SQLException;

/*Cette classe gère les boutons de la fenêtre principale.
Attributs :
-f la fenêtre concernée
*/
        
public class ButtonListener implements ActionListener{
    Fenetre f;
    
    //Le constructeur de la classe
    public ButtonListener(Fenetre fenetre){
        //On récupère la valeur de f.
        f=fenetre;
    }
    
    //La méthode qui détermine l'action a effectuer après l'appui sur un bouton
    public void actionPerformed(ActionEvent e){
        //On récupère la commande correspondant au bouton choisi
        String commande=e.getActionCommand();
        if(commande.equals("Administrateur") || commande.equals("Passager") || commande.equals("Pilote") || commande.equals("Equipage") || commande.equals("Personnel Sol")){
            //S'il s'agit d'un bouton de la fenêtre de choix de profil, on initialise la fenêtre principale en conséquence
            f.initialise(commande);
        }
        else if(commande.substring(0,Math.min(7,commande.length())).equals("Ajouter")){
            //Si c'est une demande d'ajout, on récupère le nom de la table concernée
            String nomTable=commande.substring(7);
            //Puis on ouvre une fenêtre d'ajout.
            DialogAjout dialog=new DialogAjout(f,"Ajouter un élément",Dialog.ModalityType.DOCUMENT_MODAL,BDDAirline.organisation.get(nomTable));
        }
        else if(commande.substring(0,Math.min(9,commande.length())).equals("Supprimer")){
            //Pour une suppression, on procède de manière similaire.
            String nomTable=commande.substring(9);
            DialogSuppr dialog=new DialogSuppr(f,"Supprimer un élément",Dialog.ModalityType.DOCUMENT_MODAL,BDDAirline.organisation.get(nomTable));
        }
    }
}
