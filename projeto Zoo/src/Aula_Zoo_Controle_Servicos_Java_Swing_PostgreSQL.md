# 🦁 Aula prática — Controle de Serviços do Zoológico

**Projeto:** Zoo da Turma 26 1T  
**Tecnologias:** Java Swing (NetBeans), JDBC, PostgreSQL e pgAdmin 4  
**Banco utilizado nesta aula:** `zoo_261T` (ajuste o nome se seu projeto estiver em outro banco).  
**Abordagem:** SQL diretamente nos eventos dos botões; sem classes de entidade, sem DAO e sem MVC.

> **Professor:** Nesta aula, nós vamos relacionar um animal e um zelador a um serviço. Digitaremos os IDs, consultaremos os nomes automaticamente, descreveremos o trabalho e registraremos a data atual. Um serviço começa **pendente** (`executado = FALSE`) e somente o botão **Concluir Serviço** poderá marcá-lo como executado (`TRUE`).

## 1. O que vamos construir

Uma janela `JFrame` chamada `FrmServicos`, contendo:

| Informação | Componente | Nome sugerido | Comportamento |
|---|---|---|---|
| ID do serviço | `JTextField` | `txtIdServico` | Não editável; preenchido ao selecionar na tabela |
| ID do animal | `JTextField` | `txtIdAnimal` | Digitado pelo usuário |
| Nome do animal | `JTextField` | `txtNomeAnimal` | Preenchido pela consulta; não editável |
| ID do zelador | `JTextField` | `txtIdZelador` | Digitado pelo usuário |
| Nome do zelador | `JTextField` | `txtNomeZelador` | Preenchido pela consulta; não editável |
| Descrição do serviço | `JTextArea` | `txtDescricao` | Obrigatória |
| Data de execução | `JTextField` | `txtDataExecucao` | Preenchida automaticamente; não editável |
| Executado | `JCheckBox` | `chkExecutado` | Apenas leitura; não editável pelo usuário |
| Serviços cadastrados | `JTable` | `tblServicos` | Lista serviços e permite selecionar registros |
| Buscar animal | `JButton` | `btnBuscarAnimal` | Consulta o animal pelo ID |
| Buscar zelador | `JButton` | `btnBuscarZelador` | Consulta o zelador pelo ID |
| Cadastrar | `JButton` | `btnCadastrar` | Insere um serviço pendente |
| Alterar | `JButton` | `btnAlterar` | Atualiza dados do serviço ainda pendente |
| Excluir | `JButton` | `btnExcluir` | Exclui após confirmação |
| Concluir Serviço | `JButton` | `btnConcluir` | Muda `executado` para `TRUE` |
| Atualizar | `JButton` | `btnAtualizar` | Recarrega a tabela |
| Limpar | `JButton` | `btnLimpar` | Limpa o formulário |

### Modelo visual

```text
┌─────────────────────────────────────────────────────────────────────────┐
│  ZOO — CONTROLE DE SERVIÇOS                                           │
│                                                                         │
│  Serviço nº [ automático ]     Data [ automática ]   [ ] Executado     │
│                                                                         │
│  ID Animal  [       ] [Buscar animal]                                  │
│  Nome       [ Nome encontrado automaticamente                       ]  │
│                                                                         │
│  ID Zelador [       ] [Buscar zelador]                                  │
│  Nome       [ Nome encontrado automaticamente                       ]  │
│                                                                         │
│  Serviço / descrição                                                   │
│  [                                                                    ] │
│  [                                                                    ] │
│                                                                         │
│  [Cadastrar] [Alterar] [Excluir] [Concluir Serviço] [Atualizar] [Limpar]│
│                                                                         │
│  ID | Animal | Zelador | Serviço | Data | Executado                     │
│  ---------------------------------------------------------------        │
│  ... registros cadastrados ...                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 2. Criando a tabela no PostgreSQL

Antes de executar, abra no pgAdmin o banco que já contém as tabelas **`animal`** e **`zelador`**. Esta aula pressupõe que ambas possuem uma coluna `id` numérica, chave primária, e uma coluna `nome`.

```sql
CREATE TABLE servico_zoo (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    animal_id INTEGER NOT NULL,
    zelador_id INTEGER NOT NULL,
    descricao TEXT NOT NULL,
    data_execucao DATE NOT NULL DEFAULT CURRENT_DATE,
    executado BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_servico_animal
        FOREIGN KEY (animal_id) REFERENCES animal(id),

    CONSTRAINT fk_servico_zelador
        FOREIGN KEY (zelador_id) REFERENCES zelador(id),

    CONSTRAINT chk_descricao_servico
        CHECK (LENGTH(TRIM(descricao)) > 0)
);
```

### Entendendo as regras

- `id`: identificador automático e único do serviço.
- `animal_id`: animal que receberá o serviço; obrigatório e deve existir em `animal`.
- `zelador_id`: responsável pelo serviço; obrigatório e deve existir em `zelador`.
- `descricao`: texto obrigatório e não pode conter apenas espaços.
- `data_execucao`: data do registro/execução prevista, preenchida automaticamente com `CURRENT_DATE` no cadastro. **Nesta versão, representa a data atribuída ao serviço no momento do cadastro, não necessariamente o instante real de conclusão.**
- `executado`: campo booleano obrigatório, com valor inicial `FALSE`.
- Não utilizaremos `ON DELETE CASCADE`: se um animal ou zelador tiver serviços vinculados, o banco impedirá sua exclusão, preservando o histórico.

**Observação didática:** para registrar separadamente o instante real de conclusão, poderíamos acrescentar no futuro `concluido_em TIMESTAMP`. Nesta aula, o foco é o campo booleano solicitado.

### Testando a estrutura

```sql
SELECT * FROM servico_zoo ORDER BY id;
```

> **Importante:** Se as tabelas anteriores tiverem nomes ou colunas diferentes, adapte as chaves estrangeiras e os `SELECTs`. Se o projeto estiver no banco `Zoo_new_T`, conecte-se a ele em vez de `zoo_261T`.

---

## 3. Classe de conexão

No pacote `conexao`, utilizaremos a classe `Conexao`. Caso já exista em seu projeto, **não é necessário duplicá-la**: apenas confira o nome do método de conexão.

```java
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() throws SQLException {
        String url = "jdbc:postgresql://localhost/zoo_261T";
        String usuario = "postgres";
        String senha = "root";

        return DriverManager.getConnection(url, usuario, senha);
    }
}
```

Adicione o driver JDBC do PostgreSQL ao projeto. Substitua `SUA_SENHA` pela senha local; **não publique credenciais reais no GitHub**.

---

## 4. Criando a janela no NetBeans

Vamos criar um **JFrame Form** chamado `FrmServicos`, dentro do pacote `view`. No editor visual, adicionaremos os componentes com os nomes da tabela da seção 1. Defina a operação de fechamento da janela como `DISPOSE_ON_CLOSE`.

Configure `txtIdServico`, `txtNomeAnimal`, `txtNomeZelador` e `txtDataExecucao` como **não editáveis**. Configure `chkExecutado` como **desabilitado**: ele mostrará o status, mas não permitirá que o usuário o altere manualmente.

Na `JTable`, configure as seguintes colunas **nesta ordem**:

```text
ID | ID Animal | Animal | ID Zelador | Zelador | Serviço | Data | Executado
```

É importante manter a ordem, pois os exemplos usam os índices de coluna de `0` a `7`.

### Imports necessários

```java
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
```

### Construtor da janela

Após o `initComponents()` gerado pelo NetBeans, chamaremos a atualização:

```java
public FrmServicos() {
    initComponents();
   
    txtDataExecucao.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    chkExecutado.setSelected(false);

    atualizarTabela();
}
```

**Nota:** `LocalDate.now()` serve para apresentar a data na interface. A data gravada no cadastro virá do `DEFAULT CURRENT_DATE` do PostgreSQL, garantindo que não precisemos informá-la no `INSERT`.

---

## 5. Botão Buscar Animal

Digitaremos o ID do animal e clicaremos em **Buscar animal**. O nome será preenchido automaticamente. Se não encontrarmos o registro, o nome permanecerá vazio.

No evento `btnBuscarAnimalActionPerformed`:

```java
private void btnBuscarAnimalActionPerformed(java.awt.event.ActionEvent evt) {

    txtNomeAnimal.setText("");
    int idAnimal;

    try {
        idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
        if (idAnimal <= 0) {
            JOptionPane.showMessageDialog(this, "Informe um ID de animal positivo.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Digite um ID de animal válido.");
        return;
    }

    String sql = "SELECT nome FROM animal WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, idAnimal);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                txtNomeAnimal.setText(rs.getString("nome"));
            } else {
                JOptionPane.showMessageDialog(this, "Animal não encontrado.");
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao buscar animal: " + e.getMessage());
    }
}
```

---

## 6. Botão Buscar Zelador

A pesquisa do zelador segue a mesma lógica, mas utiliza a tabela `zelador`.

```java
private void btnBuscarZeladorActionPerformed(java.awt.event.ActionEvent evt) {

    txtNomeZelador.setText("");
    int idZelador;

    try {
        idZelador = Integer.parseInt(txtIdZelador.getText().trim());
        if (idZelador <= 0) {
            JOptionPane.showMessageDialog(this, "Informe um ID de zelador positivo.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Digite um ID de zelador válido.");
        return;
    }

    String sql = "SELECT nome FROM zelador WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, idZelador);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                txtNomeZelador.setText(rs.getString("nome"));
            } else {
                JOptionPane.showMessageDialog(this, "Zelador não encontrado.");
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao buscar zelador: " + e.getMessage());
    }
}
```

**Dica:** se o aluno modificar um ID após uma busca, o nome anterior não deve ser considerado válido. Para evitar isso, no evento `keyReleased` de `txtIdAnimal`, escreva `txtNomeAnimal.setText("");`; faça o mesmo para `txtIdZelador` e `txtNomeZelador`. Os botões de cadastro e alteração também validarão novamente os IDs no banco.

---

## 7. Botão Cadastrar Serviço

Ao cadastrar, nós **não informaremos `executado` nem `data_execucao` no `INSERT`**. Assim, o PostgreSQL utilizará os valores padrão `FALSE` e `CURRENT_DATE`.

```java
private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {

    String descricao = txtDescricao.getText().trim();

    if (descricao.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Digite a descrição do serviço.");
        return;
    }

    int idAnimal;
    int idZelador;

    try {
        idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
        idZelador = Integer.parseInt(txtIdZelador.getText().trim());
        if (idAnimal <= 0 || idZelador <= 0) {
            JOptionPane.showMessageDialog(this, "Os IDs devem ser positivos.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Informe IDs numéricos válidos.");
        return;
    }

    String sql = "INSERT INTO servico_zoo (animal_id, zelador_id, descricao) "
               + "VALUES (?, ?, ?)";

    try (Connection conexao = Conexao.conectar();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {
        
            stmt.setInt(1, idAnimal);
            stmt.setInt(2, idZelador);
            stmt.setString(3, descricao);

            int linhas = stmt.executeUpdate();
            if (linhas == 1) {
                JOptionPane.showMessageDialog(this, "Serviço cadastrado como pendente!");
            }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao cadastrar serviço: " + e.getMessage());
    }
}
```

---

## 8. Atualizando a JTable

Este será o nosso método auxiliar de listagem. O restante das operações de banco permanecerá nos botões.

```java
private void atualizarTabela() {

    DefaultTableModel modelo =
        (DefaultTableModel) tblServicos.getModel();

    modelo.setRowCount(0);

    String sql = "SELECT s.id, s.animal_id, a.nome AS animal_nome, "
               + "s.zelador_id, z.nome AS zelador_nome, "
               + "s.descricao, s.data_execucao, s.executado "
               + "FROM servico_zoo s "
               + "INNER JOIN animal a ON a.id = s.animal_id "
               + "INNER JOIN zelador z ON z.id = s.zelador_id "
               + "ORDER BY s.id";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            modelo.addRow(new Object[] {
                rs.getInt("id"),
                rs.getInt("animal_id"),
                rs.getString("animal_nome"),
                rs.getInt("zelador_id"),
                rs.getString("zelador_nome"),
                rs.getString("descricao"),
                rs.getDate("data_execucao"),
                rs.getBoolean("executado")
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao carregar serviços: " + e.getMessage());
    }
}
```

### Botão Atualizar

```java
private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
    atualizarTabela();
}
```

---

## 9. Selecionando um serviço na JTable

Quando clicarmos em uma linha, preencheremos os campos do formulário.

```java
private void tblServicosMouseClicked(java.awt.event.MouseEvent evt) {

    int linha = tblServicos.getSelectedRow();
    if (linha == -1) return;

    txtIdServico.setText(tblServicos.getValueAt(linha, 0).toString());
    txtIdAnimal.setText(tblServicos.getValueAt(linha, 1).toString());
    txtNomeAnimal.setText(tblServicos.getValueAt(linha, 2).toString());
    txtIdZelador.setText(tblServicos.getValueAt(linha, 3).toString());
    txtNomeZelador.setText(tblServicos.getValueAt(linha, 4).toString());
    txtDescricao.setText(tblServicos.getValueAt(linha, 5).toString());
    txtDataExecucao.setText(tblServicos.getValueAt(linha, 6).toString());
    chkExecutado.setSelected(
        Boolean.TRUE.equals(tblServicos.getValueAt(linha, 7))
    );
}
```

> O `ID do serviço` não será editado. Sempre recuperaremos esse ID da **linha selecionada** para executar `UPDATE` ou `DELETE`, evitando alterar o registro errado.

---

## 10. Botão Alterar

Vamos permitir alterar **animal, zelador e descrição**, mas **não** a data automática nem o status. Um serviço já concluído ficará bloqueado para alteração nesta versão.

```java
private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {

    int linha = tblServicos.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela.");
        return;
    }

    int idServico = Integer.parseInt(
        tblServicos.getValueAt(linha, 0).toString()
    );

    if (Boolean.TRUE.equals(tblServicos.getValueAt(linha, 7))) {
        JOptionPane.showMessageDialog(this, "Serviços concluídos não podem ser alterados.");
        return;
    }

    String descricao = txtDescricao.getText().trim();
    if (descricao.isEmpty()) {
        JOptionPane.showMessageDialog(this, "A descrição não pode ficar vazia.");
        return;
    }

    int idAnimal;
    int idZelador;

    try {
        idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
        idZelador = Integer.parseInt(txtIdZelador.getText().trim());
        if (idAnimal <= 0 || idZelador <= 0) {
            JOptionPane.showMessageDialog(this, "Os IDs devem ser positivos.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "IDs inválidos.");
        return;
    }

    int resposta = JOptionPane.showConfirmDialog(
        this,
        "Confirma a alteração do serviço nº " + idServico + "?",
        "Alterar serviço",
        JOptionPane.YES_NO_OPTION
    );

    if (resposta != JOptionPane.YES_OPTION) return;

    String sql = "UPDATE servico_zoo "
               + "SET animal_id = ?, zelador_id = ?, descricao = ? "
               + "WHERE id = ? AND executado = FALSE";

    try (Connection conexao = Conexao.conectar()) {

        // Validamos novamente os vínculos antes de atualizar.
        try (PreparedStatement buscaAnimal = conexao.prepareStatement(
                 "SELECT id FROM animal WHERE id = ?");
             PreparedStatement buscaZelador = conexao.prepareStatement(
                 "SELECT id FROM zelador WHERE id = ?")) {

            buscaAnimal.setInt(1, idAnimal);
            buscaZelador.setInt(1, idZelador);

            try (ResultSet rs = buscaAnimal.executeQuery()) {
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Animal não encontrado.");
                    return;
                }
            }

            try (ResultSet rs = buscaZelador.executeQuery()) {
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Zelador não encontrado.");
                    return;
                }
            }
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            stmt.setInt(2, idZelador);
            stmt.setString(3, descricao);
            stmt.setInt(4, idServico);

            int linhas = stmt.executeUpdate();

            JOptionPane.showMessageDialog(this,
                linhas == 1 ? "Serviço alterado com sucesso!"
                            : "Serviço não encontrado ou já concluído.");

            atualizarTabela();
            limparCampos();
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao alterar serviço: " + e.getMessage());
    }
}
```

---

## 11. Botão Excluir

Antes de excluir, solicitaremos confirmação. Neste exemplo, permitiremos excluir qualquer serviço, inclusive concluído, **desde que o usuário confirme**. Em um sistema profissional, normalmente restringiríamos a exclusão de registros concluídos para preservar a auditoria.

```java
private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {

    int linha = tblServicos.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir.");
        return;
    }

    int idServico = Integer.parseInt(
        tblServicos.getValueAt(linha, 0).toString()
    );

    int resposta = JOptionPane.showConfirmDialog(
        this,
        "Deseja realmente excluir o serviço nº " + idServico + "?",
        "Confirmar exclusão",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (resposta != JOptionPane.YES_OPTION) return;

    String sql = "DELETE FROM servico_zoo WHERE id = ?";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, idServico);
        int linhas = stmt.executeUpdate();

        JOptionPane.showMessageDialog(this,
            linhas == 1 ? "Serviço excluído com sucesso!"
                        : "Serviço não encontrado.");

        atualizarTabela();
        limparCampos();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao excluir serviço: " + e.getMessage());
    }
}
```

---

## 12. Botão Concluir Serviço ⭐

Este é o botão principal da atividade! Ele será **o único controle da tela que mudará `executado` de `FALSE` para `TRUE`**.

```java
private void btnConcluirActionPerformed(java.awt.event.ActionEvent evt) {

    int linha = tblServicos.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um serviço para concluir.");
        return;
    }

    int idServico = Integer.parseInt(
        tblServicos.getValueAt(linha, 0).toString()
    );

    if (Boolean.TRUE.equals(tblServicos.getValueAt(linha, 7))) {
        JOptionPane.showMessageDialog(this, "Este serviço já está concluído.");
        return;
    }

    int resposta = JOptionPane.showConfirmDialog(
        this,
        "Confirmar a conclusão do serviço nº " + idServico + "?",
        "Concluir serviço",
        JOptionPane.YES_NO_OPTION
    );

    if (resposta != JOptionPane.YES_OPTION) return;

    String sql = "UPDATE servico_zoo "
               + "SET executado = TRUE "
               + "WHERE id = ? AND executado = FALSE";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, idServico);
        int linhas = stmt.executeUpdate();

        JOptionPane.showMessageDialog(this,
            linhas == 1 ? "Serviço concluído com sucesso!"
                        : "Serviço não encontrado ou já concluído.");

        atualizarTabela();
        limparCampos();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao concluir serviço: " + e.getMessage());
    }
}
```

**Por que utilizamos `WHERE id = ? AND executado = FALSE`?** Porque, além de identificar o serviço, evitamos concluir novamente um registro que já esteja marcado como executado.

---

## 13. Botão Limpar

Para manter os botões mais simples, criaremos **um método auxiliar apenas para limpar os campos da interface**. Ele não executa SQL.

```java
private void limparCampos() {
    txtIdServico.setText("");
    txtIdAnimal.setText("");
    txtNomeAnimal.setText("");
    txtIdZelador.setText("");
    txtNomeZelador.setText("");
    txtDescricao.setText("");
    txtDataExecucao.setText(LocalDate.now().toString());
    chkExecutado.setSelected(false);
    tblServicos.clearSelection();
    txtIdAnimal.requestFocus();
}
```

No evento do botão:

```java
private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {
    limparCampos();
}
```

---

## 14. Integrando ao menu principal do zoológico

Na `FrmPrincipal`, criaremos um submenu **Serviços** (por exemplo, dentro de **Cadastro** ou em um menu próprio). No evento do `JMenuItem`:

```java
private void mnuServicosActionPerformed(java.awt.event.ActionEvent evt) {
    FrmServicos tela = new FrmServicos();
    tela.setLocationRelativeTo(null);
    tela.setVisible(true);
}
```

Se a janela principal estiver em outro pacote, será necessário importar `view.FrmServicos`.

---

## 15. Testando diretamente no PostgreSQL

### Visualizar serviços com nomes

```sql
SELECT
    s.id,
    a.nome AS animal,
    z.nome AS zelador,
    s.descricao,
    s.data_execucao,
    s.executado
FROM servico_zoo s
JOIN animal a ON a.id = s.animal_id
JOIN zelador z ON z.id = s.zelador_id
ORDER BY s.id;
```

### Visualizar apenas serviços pendentes

```sql
SELECT *
FROM servico_zoo
WHERE executado = FALSE
ORDER BY id;
```

### Visualizar apenas serviços concluídos

```sql
SELECT *
FROM servico_zoo
WHERE executado = TRUE
ORDER BY id;
```

### Contar serviços por status

```sql
SELECT
    COUNT(*) AS total,
    COUNT(*) FILTER (WHERE executado = FALSE) AS pendentes,
    COUNT(*) FILTER (WHERE executado = TRUE) AS concluidos
FROM servico_zoo;
```

---

## 16. Roteiro de testes para a turma

- [ ] Abrir a tela e verificar se os serviços existentes aparecem na `JTable`.
- [ ] Pesquisar um ID de animal existente e conferir o nome preenchido automaticamente.
- [ ] Pesquisar um ID de zelador existente e conferir o nome.
- [ ] Pesquisar um ID inexistente e verificar a mensagem.
- [ ] Tentar cadastrar um serviço sem descrição.
- [ ] Cadastrar um serviço e conferir que a data foi preenchida e `executado` está `FALSE`.
- [ ] Selecionar uma linha e verificar o preenchimento dos campos.
- [ ] Alterar a descrição de um serviço pendente.
- [ ] Cancelar uma exclusão e verificar que o registro permanece.
- [ ] Confirmar uma exclusão e verificar que apenas o serviço escolhido foi apagado.
- [ ] Concluir um serviço e verificar que `executado` mudou para `TRUE`.
- [ ] Tentar concluir novamente o mesmo serviço.
- [ ] Tentar alterar um serviço concluído.
- [ ] Conferir os resultados com `SELECT` no pgAdmin.

### Exercícios extras

1. Criar um filtro para exibir somente os serviços pendentes.
2. Adicionar uma coluna de status com texto **Pendente** ou **Concluído**.
3. Exibir a quantidade de serviços pendentes em um `JLabel`.
4. Acrescentar `concluido_em TIMESTAMP` para registrar o instante real da conclusão.
5. Impedir a exclusão de serviços concluídos para preservar o histórico.

---

## 17. Erros comuns e como resolver

| Situação | Possível causa | Correção |
|---|---|---|
| `relation "animal" does not exist` | Banco incorreto ou nome diferente | Conferir conexão e nome da tabela |
| `relation "zelador" does not exist` | Tabela ainda não criada | Criar a tabela antes de `servico_zoo` |
| Violação de chave estrangeira | ID de animal/zelador inexistente | Buscar um ID válido |
| `NumberFormatException` | ID vazio ou não numérico | Validar com `Integer.parseInt` em `try/catch` |
| Colunas trocadas na `JTable` | Ordem do modelo diferente | Conferir os oito cabeçalhos e índices |
| `cannot find symbol Conexao` | Nome de classe/pacote diferente | Corrigir importação e método de conexão |
| Driver JDBC ausente | Biblioteca não adicionada | Adicionar o driver PostgreSQL JDBC |
| Fechar serviços encerra o programa | `EXIT_ON_CLOSE` na tela secundária | Usar `DISPOSE_ON_CLOSE` |

---

## 18. Conclusão do professor

Nesta aula, nós construímos uma funcionalidade importante para nosso zoológico: **um controle de serviços com relacionamento entre três tabelas**.

Aprendemos a buscar nomes a partir de IDs, utilizar **chaves estrangeiras**, preencher uma `JTable` com `JOIN`, validar informações, cadastrar serviços com **valores padrão do banco**, alterar e excluir registros e, principalmente, **concluir um serviço através de um botão que atualiza um campo booleano**.

Tudo isso utilizando **Java Swing, PostgreSQL e JDBC diretamente nos eventos**, sem criar classes de entidade ou DAO. 🦁🐘

> **Desafio final:** agora que sabemos qual animal recebeu o serviço e qual zelador foi responsável, como poderíamos gerar um relatório de produtividade dos zeladores?
