package estimate_capacity;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EstimateCapacity
{
//TODO: Talvez fazer Connection protected para globalmente ser usado por todos
//TODO:METER CONEXÕES reais
//Nao existe propriamente variaveis de um objeto já que pode haver dois bibliotecarios diferentes e tem de ser on demand
    public EstimateCapacity(){

    }

    public int select_capacity(){

        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/test";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT capacity FROM capacity");
            int capacity=-1;
            while (rs.next())
            {
                capacity = rs.getInt("capacity");
                break;
            }
            if (capacity==-1)
            {
                throw new Exception();
            }
            else
            {
                return capacity;
            }
        }catch (Exception e){
            System.err.println("Problema na query à base de dados!!!!\nContacte o administrador do sistema");//TODO: Alterar para onde recebe o -1 e para modo gráfico!!
            return -1;
        }
    }

    public boolean updatecapacity(int novacapacidade){

        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/test";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            Statement st = conn.createStatement();

            st.executeUpdate("update capacity set capacity = " + novacapacidade);


            return true;

        }catch (Exception e){
            System.err.println("Problema na query à base de dados!!!!\nContacte o administrador do sistema");//TODO: Alterar para onde recebe o false e para modo gráfico!!
            return false;
        }
    }
}
