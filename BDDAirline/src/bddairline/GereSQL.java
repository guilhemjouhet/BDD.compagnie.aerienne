package bddairline;
import java.sql.*;
import java.util.*;

public class GereSQL {
    public GereSQL(){
    }
    
    public Connection connecte(){
    try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
        
        Connection conn = null;
           
        try {
            conn =
                DriverManager.getConnection("jdbc:mysql://localhost/mydb?useLegacyDatetimeCode=false&serverTimezone=America/New_York"+"&user=Cedric&password=password&useSSL=false&allowPublicKeyRetrieval=true");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
            return conn;
        }
    
    public Object[] requeteSQL(String type, String requete,String table){
        Connection conn=connecte();
        Statement stmt = null;
        
        if (!type.equals("Select")){
            try {
                stmt = conn.createStatement();
                stmt.execute(requete);
                conn.close();
            }

            catch (SQLException ex){
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            return selectSQL("SELECT * FROM "+table,table);
        }
        else{
            return selectSQL(requete,table);
        }
    }
    
    public HashMap<String,TableSQL> metaDonnees(){
        Connection conn=connecte();
        HashMap<String,TableSQL> orga=new HashMap<String,TableSQL>();
        try{
            DatabaseMetaData metadata=conn.getMetaData();
            ResultSet donneesTables=metadata.getTables("mydb",null,"%",null);
            ArrayList<String> nomTables=new ArrayList<String>();
            while (donneesTables.next()) {
                String nom=donneesTables.getString("TABLE_NAME");
                TableSQL tsql=new TableSQL(nom);
                ResultSet donneesColonnes=metadata.getColumns("mydb",null,nom,null);
                while (donneesColonnes.next()){
                    Object[] carac=new String[3];
                    carac[0]=donneesColonnes.getString("COLUMN_NAME");
                    carac[1]=donneesColonnes.getString("DATA_TYPE");
                    carac[2]=donneesColonnes.getString("DATA_TYPE");
                    tsql.addAttribut(carac);
                }
                orga.put(nom,tsql);
            }
            conn.close();
        }
        catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return orga;
    }
    
    public Object[] selectSQL(String requete,String table){
        Connection conn=connecte();
        ArrayList<ArrayList<String>> resultat=new ArrayList<ArrayList<String>>();
        Statement stmt = null;
        ResultSet rs = null;
        Object[] colonnesResultat=new Object[2];
        conn=connecte();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            ResultSetMetaData metadata=rs.getMetaData();
            int nbcolonnes=metadata.getColumnCount();
            
            while (rs.next()) {
                ArrayList<String> lignei=new ArrayList();
                for(int j=1;j<=nbcolonnes;j++){
                    lignei.add(rs.getString(j));
                }
                resultat.add(lignei);
            }
            
            String[] colonnes=new String[nbcolonnes];
            for (int j=1;j<=nbcolonnes;j++){
                colonnes[j-1]=metadata.getColumnName(j);
                colonnesResultat[0]=colonnes;
                colonnesResultat[1]=resultat;
                conn.close();
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return colonnesResultat;
    }
}
