package bddairline;
import java.util.ArrayList;

public class TableSQL {
    String nom;
    public ArrayList<Object[]> attributs;
    
    public TableSQL(String n){
        nom=n;
        attributs=new ArrayList<Object[]>();
    }
    
    public void addAttribut(Object[] attribut){
        attributs.add(attribut);
    }
    
    public String toString(){
        String s=nom+"(";
        for(int i=0;i<attributs.size();i++){
            s=s+" "+ (String)attributs.get(i)[0];
        }
        s+=" )";
        return s;
    }
}
