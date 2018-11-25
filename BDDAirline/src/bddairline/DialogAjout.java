/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bddairline;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;
/**
 *
 * @author hp
 */
public class DialogAjout extends JDialog{
    private JTextField[] listeFields;
    private TableSQL tablesql;
    public DialogAjout(JFrame parent, String titre, ModalityType modal, TableSQL table){
        super(parent,titre,modal);
        tablesql=table;
        this.setLayout(new GridLayout(0,2));
        listeFields=new JTextField[tablesql.attributs.size()];
        for(int i=0;i<table.attributs.size();i++){
            this.add(new JLabel((String)tablesql.attributs.get(i)[0]));
            JTextField tf=new JTextField();
            listeFields[i]=tf;
            this.add(tf);
        }
        JButton boutonAjouter=new JButton("Ajouter");
        this.add(boutonAjouter);
        boutonAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajout();
            }
        });
        JButton boutonTerminer=new JButton("Terminer");
        boutonTerminer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(boutonTerminer);
        this.pack();
        this.setVisible(true);  
    }
    
    public void ajout(){
        String requete="INSERT INTO "+tablesql.nom+" VALUES (";
        for(int i=0;i<listeFields.length;i++){
            if(i!=0){
                requete+=",";
            }
            requete+="'"+listeFields[i].getText()+"'";
        }
        requete+=")";
        BDDAirline.faireRequete("Ajout", requete, tablesql.nom);
    }
}
