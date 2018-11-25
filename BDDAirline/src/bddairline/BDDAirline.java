package bddairline;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class BDDAirline {
    private static Fenetre f;
    private static GereSQL connectionBDD;
    public static HashMap<String,TableSQL> organisation;
    
    public static void main(String[] args) {      
        connectionBDD=new GereSQL();
        organisation=connectionBDD.metaDonnees();
        f = new Fenetre("BDD Airlines");
    }
    
    public static void faireRequete(String type,String requete,String tableARemplir){
        Object[] colonnesResultat = connectionBDD.requeteSQL(type,requete,tableARemplir);
        String[] colonnes = (String[]) colonnesResultat[0];
        ArrayList<ArrayList<String>> resRequete=(ArrayList<ArrayList<String>>) colonnesResultat[1];
        int nblignes=resRequete.size();
        String[][] donnees = new String[nblignes][];
        for (int i = 0; i < nblignes; i++) {
            ArrayList<String> row = resRequete.get(i);
            donnees[i] = row.toArray(new String[row.size()]);
        }
        f.remplirTable(donnees,colonnes,tableARemplir);
        
    }
    
}
