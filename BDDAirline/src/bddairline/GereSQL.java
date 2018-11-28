package bddairline;
import java.sql.*;
import java.util.*;

/* Cette classe contient les méthodes qui permettent de dialoguer avec la base de données.
Attributs :
-conn, l'objet Connection qui permet le lien avec la BDD
*/

public class GereSQL {
    private Connection conn;
    
    //Constructeur de la classe (ne fait rien)
    public GereSQL(){}
    
    //Cette méthode permet de se connecter à la base de données
    public Connection connecte(){
    try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
        
        conn = null;
        try {
            //On lance la connexion
            conn =DriverManager.getConnection("jdbc:mysql://localhost/mydb?useLegacyDatetimeCode=false&serverTimezone=America/New_York"+"&user=Cedric&password=password&useSSL=false&allowPublicKeyRetrieval=true");
        }catch (SQLException ex) {
            //On gère les éventuels problèmes
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
            //On retourne l'objet Connection obtenu
            return conn;
        }
    
    /*Cette méthode permet d'effectuer une requete SQL et de renvoyer la table mise à jour
    Paramètres :
    -type, le type de la requete
    -requete, la requete SQL a executer
    -table, le nom de la table SQL concernée
    */
    public Object[] requeteSQL(String type, String requete,String table) throws SQLException{
        conn=connecte();
        Statement stmt = null;
        //On vérifie si il s'agit d'une requete Select ou non
        if (!type.equals("Select")){
            try {
                //Si non, on effectue la requete
                stmt = conn.createStatement();
                stmt.execute(requete);
                conn.close();
            }catch (SQLException ex){
                //En transmettant les éventuelles exceptions
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw(ex);
            }finally{
                //On vérifie enfin que la connexion et les objets qui en dépendent sont bien fermés
                closePropre();
                closeStmt(stmt);
            }
            //Puis on renvoie la table modifiée en entier.
            return selectSQL("SELECT * FROM "+table,table);
        }
        else{
            //Sinon, on utilise la méthode selectSQL.
            closePropre();
            return selectSQL(requete,table);
        }
    }
    
    /*Cette méthode permet de récupérer les données sur les tables et les
    attributs de la BDD, notamment pour créer l'attribut organisation de la classe
    principale.*/
    public HashMap<String,TableSQL> metaDonnees(){
        //On se connecte à la base de données
        conn=connecte();
        HashMap<String,TableSQL> orga=new HashMap<String,TableSQL>();
        ResultSet donneesTables=null;
        ResultSet donneesColonnes=null;
        ResultSet clePrimairesSet=null;
        try{
            //On récupère les métadonnées
            DatabaseMetaData metadata=conn.getMetaData();
            //On en extrait la liste des tables
            donneesTables=metadata.getTables("mydb",null,"%",null);
            ArrayList<String> nomTables=new ArrayList<String>();
            while (donneesTables.next()) {
                //Pour chaque table, on récupère son nom et on crée l'objet TableSQL correspondant
                String nom=donneesTables.getString("TABLE_NAME");
                TableSQL tsql=new TableSQL(nom);
                //On récupère les données sur les attributs et la liste des clés primaires
                donneesColonnes=metadata.getColumns("mydb",null,nom,null);
                clePrimairesSet=metadata.getPrimaryKeys("mydb",null,nom);
                //On stocke la liste des clés primaires dans un ArrayList, plus smple à manipuler
                ArrayList<String> clePrimaires=new ArrayList<String>();
                while (clePrimairesSet.next()){
                    clePrimaires.add(clePrimairesSet.getString("COLUMN_NAME"));
                }
                //Enfin, pour chaque attribut on prend son nom, le type de données et si c'est une clé primaire
                while (donneesColonnes.next()){
                    Object[] carac=new String[3];
                    carac[0]=donneesColonnes.getString("COLUMN_NAME");
                    carac[1]=donneesColonnes.getString("DATA_TYPE");
                    carac[2]="NPrimaire";
                    for(int i=0;i<clePrimaires.size();i++){
                        if (clePrimaires.get(i).equals(carac[0])){
                            carac[2]="Primaire";
                        }
                    }
                    //Le résultat est renseigné dans l'objet TableSQL
                    tsql.addAttribut(carac);
                }
                //On ajoute le résultat dans 
                orga.put(nom,tsql);
            }
            conn.close();
        }catch(SQLException ex){
            //On gère les exceptions
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }finally{
            //Enfin, on vérifie que la connexion est bien fermée.
            closePropre();
            closeRS(donneesColonnes);
            closeRS(donneesTables);
            closeRS(clePrimairesSet);
        }
        //On renvoie le résultat
        return orga;
    }
    
    /* Cette méthode permet de faire une requette Select.
    Paramètres :
    -requete la requete à lancer
    -table, la table concernée.
    */
    public Object[] selectSQL(String requete,String table){
        //On se connecte
        conn=connecte();
        ArrayList<ArrayList<String>> resultat=new ArrayList<ArrayList<String>>();
        Statement stmt = null;
        ResultSet rs = null;
        Object[] colonnesResultat=new Object[2];
        try {
            //On lance la requete
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            ResultSetMetaData metadata=rs.getMetaData();
            int nbcolonnes=metadata.getColumnCount();
            
            while (rs.next()) {
                //On stocke les résultats
                ArrayList<String> lignei=new ArrayList();
                for(int j=1;j<=nbcolonnes;j++){
                    lignei.add(rs.getString(j));
                }
                resultat.add(lignei);
            }
            
            //On récupère les en-têtes des colonnes
            String[] colonnes=new String[nbcolonnes];
            for (int j=1;j<=nbcolonnes;j++){
                colonnes[j-1]=metadata.getColumnName(j);
                colonnesResultat[0]=colonnes;
                colonnesResultat[1]=resultat;
            }
            conn.close();
        }catch (SQLException ex){
            //On gère les exceptions
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }finally{
            //On vérifie que tout est bien fermé
            closePropre();
            closeRS(rs);
            closeStmt(stmt);
        }
        //On renvoie le résultat
        return colonnesResultat;
    }
    
    public void closePropre(){
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException ex){}
    }
    
    public void closeRS(ResultSet rs){
        try{
            if (rs!=null){
                    rs.close();
            }
        }catch(SQLException ex){}
    }
    
    public void closeStmt(Statement stmt){
        try{
            if (stmt!=null){
                    stmt.close();
            }
        }catch(SQLException ex){}
    }
}
