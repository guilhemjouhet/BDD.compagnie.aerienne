package bddairline;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dialog;
        
public class ButtonListener implements ActionListener{
    Fenetre f;
    public ButtonListener(Fenetre fenetre){
        f=fenetre;
    }
    public void actionPerformed(ActionEvent e){
        String commande=e.getActionCommand();
        if(commande.equals("Administrateur") || commande.equals("Passager") || commande.equals("Pilote") || commande.equals("Equipage") || commande.equals("Personnel Sol")){
            f.initialise(commande);
        }
        else if(commande.substring(0,Math.min(7,commande.length())).equals("Ajouter")){
            String nomTable=commande.substring(7);
            DialogAjout dialog=new DialogAjout(f,"Ajouter un élément",Dialog.ModalityType.DOCUMENT_MODAL,BDDAirline.organisation.get(nomTable));
        }
        else{
        }
    }
}
