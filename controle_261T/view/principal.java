package view;

/**
 *
 * @author Eder Silva
 */


import conexao.conexao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class principal {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("     CADASTRO DE PRODUTOS");
        System.out.println("==============================");

        
        System.out.print("Digite a descricao do produto: ");
        String descricao = scanner.nextLine();

        if (descricao.isEmpty()){
                System.out.println("A descricao não pode ser vazia");
                return;
        }
        
        System.out.print("Digite a quantidade em estoque: ");
        int estoque = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o valor de venda: ");
        String valorDigitado = scanner.nextLine();
        
        if (valorDigitado.isEmpty() || Integer.parseInt(valorDigitado)<0) {
           System.out.println("O valor de Venda não pode ser Vazio e nem Menor que ZERO");
           return;
        }
        
        BigDecimal valorVenda =
                new BigDecimal(valorDigitado);

        String sqlInsert = """
                INSERT INTO Produto
                (descricao, estoque, valor_venda)
                VALUES (?, ?, ?)
                """;

        String sqlSelect = """
                SELECT id, descricao, estoque, valor_venda
                FROM Produto
                ORDER BY id
                """;

        try {
            Connection conex = conexao.conectar();
            PreparedStatement stmt = conex.prepareStatement(sqlInsert);
            
         
            

            stmt.setString(1, descricao);
            stmt.setInt(2, estoque);
            stmt.setBigDecimal(3, valorVenda);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println();
                System.out.println("Produto cadastrado com sucesso!");
            }

            System.out.println();
            System.out.println("==============================");
            System.out.println("     PRODUTOS CADASTRADOS");
            System.out.println("==============================");

            try (
                PreparedStatement stmtConsulta =
                        conex.prepareStatement(sqlSelect);

                ResultSet resultado =
                        stmtConsulta.executeQuery()
            ) {

                while (resultado.next()) {

                    int id = resultado.getInt("id");
                    String desc = resultado.getString("descricao");
                    int qtd = resultado.getInt("estoque");
                    BigDecimal valor =
                            resultado.getBigDecimal("valor_venda");

                    System.out.println();
                    System.out.println("ID: " + id);
                    System.out.println("Descricao: " + desc);
                    System.out.println("Estoque: " + qtd);
                    System.out.println("Valor de venda: R$ " + valor);
                    System.out.println("==============================");
                }
            }

        } catch (SQLException erro) {

            System.out.println();
            System.out.println("Erro ao acessar o banco.");
            System.out.println(erro.getMessage());

        } catch (NumberFormatException erro) {

            System.out.println();
            System.out.println(
                    "Estoque ou valor informado é inválido."
            );
        }

        scanner.close();
       
    }
}