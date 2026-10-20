package conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Eder Silva
 */
public class Conexao {
     public static Connection conectar(){
         
         Connection conexao = null;
         
         String url = "jdbc:postgresql://localhost/zoo_261T";
         String usuario = "postgres";
         String senha = "root";
         
         try{
            conexao = DriverManager.getConnection(url,usuario,senha); 
         } catch (SQLException erro){
             System.out.println("Erro ao Conectar com o Banco - Erro: " + erro);
         }
         return conexao;
     }
}
