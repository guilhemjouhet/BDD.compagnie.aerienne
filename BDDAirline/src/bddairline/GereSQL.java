package bddairline;
import java.sql.*;
import java.util.ArrayList;

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
                DriverManager.getConnection("jdbc:mysql://localhost/mydb?useLegacyDatetimeCode=false&serverTimezone=America/New_York"+"&user=user&password=password");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
            return conn;
        }
    
    public Object[] requeteSQL(String requete){
        Connection conn=connecte();
        ArrayList<ArrayList<String>> resultat=new ArrayList<ArrayList<String>>();
        Statement stmt = null;
        ResultSet rs = null;
        Object[] colonnesResultat=new Object[2];
        
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
            }
        }

        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        try{
            conn.close();
        }catch(SQLException e){
        }
        return colonnesResultat;
    }
}
