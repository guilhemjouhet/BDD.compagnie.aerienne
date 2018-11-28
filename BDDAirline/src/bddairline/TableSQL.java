package bddairline;
import java.util.ArrayList;

/*Cette classe permet de stocker le nom et les informations sur les attributs
des tables de la BDD.
Attributs :
-nom, le nom de la table
-attributs, qui contient le nom des attributs, leur type, et si ce sont des clés primairess
*/
public class TableSQL {
    String nom;
    public ArrayList<Object[]> attributs;
    
    /*Constructeur de la classe
    Paramètre:
    -n, le nom de la table
    */
    public TableSQL(String n){
        //On renseigne le nom et on initialise la liste des attributs
        nom=n;
        attributs=new ArrayList<Object[]>();
    }
    
    /*Cette méthode permet d'ajouter un attribut
    Paramètre :
    -attribut, la liste des infos sur l'attribut.
    */
    public void addAttribut(Object[] attribut){
        attributs.add(attribut);
    }
    
    //Cette méthode affiche les infos sur la table sous forme de texte pour des tests.
    public String toString(){
        String s=nom+"(";
        for(int i=0;i<attributs.size();i++){
            s=s+" "+ (String)attributs.get(i)[0]+(String)attributs.get(i)[1];
        }
        s+=" )";
        return s;
    }
}
