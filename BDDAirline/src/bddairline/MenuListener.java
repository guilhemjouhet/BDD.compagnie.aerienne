package bddairline;
import java.awt.event.*;
        
public class MenuListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        String commande=e.getActionCommand();
        BDDAirline.faireRequete("SELECT * FROM "+commande);
    }
}
