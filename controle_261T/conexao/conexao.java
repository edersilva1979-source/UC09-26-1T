package conexao;

/**
 *
 * @author Eder Silva
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

    public static Connection conectar() {

        Connection conexao = null;

        String url = "jdbc:postgresql://localhost/BD_controle_261T";
        String usuario = "postgres";
        String senha = "root";

        try {

            conexao = DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );

            System.out.println("Conexao realizada com sucesso!");

        } catch (SQLException erro) {

            System.out.println("Erro ao conectar com o banco.");
            System.out.println(erro.getMessage());
        }

        return conexao;
    }
}