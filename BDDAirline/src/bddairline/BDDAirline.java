package bddairline;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

/*La classe principale du programme
Attributs :
- f la fenêtre principale du programme
- connectionBDD, l'objet qui gère la connexion avec la base de données SQL
- organisation, un dictionnaire qui stocke les informations sur les tables de la base de données*/
public class BDDAirline {
    private static Fenetre f;
    private static GereSQL connectionBDD;
    public static HashMap<String,TableSQL> organisation;
    
    //La fonction principale du programme
    public static void main(String[] args) {
        //On crée les attributs connectionBDD et organisation, et on lance la fenêtre principale
        connectionBDD=new GereSQL();
        organisation=connectionBDD.metaDonnees();
        f = new Fenetre("BDD Airlines");
    }
    
    /*Cette méthode lance une requête SQL, et récupère le résultat à afficher
    Paramètres :
    -type, le type de requête SQL à faire
    -requete, la requête exacte à executer
    -tableARemplir, le nom de la table à remplir dans l'interface graphique*/
    public static void faireRequete(String type,String requete,String tableARemplir) throws SQLException{
        try{
            //On lance la requete et on récupère les résultats
            Object[] colonnesResultat = connectionBDD.requeteSQL(type,requete,tableARemplir);
            String[] colonnes = (String[]) colonnesResultat[0];
            ArrayList<ArrayList<String>> resRequete=(ArrayList<ArrayList<String>>) colonnesResultat[1];
            //On convertit les données reçues dans le bon type pour la table
            int nblignes=resRequete.size();
            String[][] donnees = new String[nblignes][];
            for (int i = 0; i < nblignes; i++) {
                ArrayList<String> row = resRequete.get(i);
                donnees[i] = row.toArray(new String[row.size()]);
            }
            //Enfin, on remplit la table
            f.remplirTable(donnees,colonnes,tableARemplir);
        }
        catch(SQLException ex){
            //En cas de requette éronnée, on transmet l'exception.
            throw(ex);
        }
        
    }
    
    /* Cette méthode permet de lancer une requete select sans actualiser les tables affichées, 
    mais en récupérant les données dans un tableau
    Paramètres :
    -type, le type de requête SQL à faire
    -requete, la requête exacte à executer
    -tableARemplir, qu'on garde car il faut un argument pour la méthode requeteSQL de connectionBDD*/
    public static String[][] requetePasActu(String type,String requete,String tableARemplir) throws SQLException{
        try{
            //On effectue la requete
            Object[] colonnesResultat = connectionBDD.requeteSQL(type,requete,tableARemplir);
            String[] colonnes = (String[]) colonnesResultat[0];
            ArrayList<ArrayList<String>> resRequete=(ArrayList<ArrayList<String>>) colonnesResultat[1];
            //On convertit les résultat dans un tableau
            int nblignes=resRequete.size();
            String[][] donnees = new String[nblignes][];
            for (int i = 0; i < nblignes; i++) {
                ArrayList<String> row = resRequete.get(i);
                donnees[i] = row.toArray(new String[row.size()]);
            }
            //On renvoire le résultat
            return donnees;
            
        }
        catch(SQLException ex){
            // En cas de requete éronnée, on transmet l'exception
            throw(ex);
        }
    }
}
