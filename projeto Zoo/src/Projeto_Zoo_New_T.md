# 🦁 Projeto Integrador — Zoo New T

**Java Swing + PostgreSQL | NetBeans | Aula prática**

## 1. Apresentação

Nesta atividade, eu proponho que nós construamos um sistema completo para um zoológico. Vamos utilizar **JFrame em todas as janelas**, menus, imagens, formulários, JTable e PostgreSQL. Para reforçar o conteúdo de JDBC, **não criaremos classes Animal, Zelador, DAO ou Controller**: faremos as operações SQL diretamente nos eventos dos botões. A única classe auxiliar será `Conexao`, e teremos o método `atualizarTabela()` nas consultas.

### Objetivos
- Criar o banco `Zoo_new_T` com as tabelas `animal` e `zelador`.
- Desenvolver a janela principal com `JMenuBar`, `JMenu` e `JMenuItem`.
- Cadastrar, consultar, alterar e excluir animais e zeladores.
- Carregar automaticamente as JTable ao abrir as telas de consulta.
- Desenvolver relatórios visuais, tela Sobre e tela Ajuda.
- Aplicar validações, mensagens de confirmação e tratamento de erros.

> **Importante:** “Sem usar objeto” significa não criar objetos de domínio `Animal` ou `Zelador`. Swing e JDBC utilizam objetos internamente, como `JFrame`, `Connection` e `PreparedStatement`.

---

## 2. Banco de dados PostgreSQL

No pgAdmin 4, conectado ao servidor, vamos executar:

```sql
CREATE DATABASE "Zoo_new_T";
```

Depois vamos selecionar **Zoo_new_T** e abrir um novo **Query Tool** conectado a ele. Criamos as tabelas:

```sql
CREATE TABLE animal (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    especie VARCHAR(100) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade INTEGER NOT NULL DEFAULT 0 CHECK (idade >= 0),
    observacao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE zelador (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(120) NOT NULL,
    idade INTEGER NOT NULL DEFAULT 0 CHECK (idade >= 0),
    observacao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);
```

**Campos de animais:** ID, Espécie, Nome, Idade, Observação e Ativo.

**Campos de zeladores:** ID, Nome, Especialidade, Idade, Observação e Ativo.

`id` é gerado automaticamente, `idade` é `INTEGER`, `observacao` permite textos longos e `ativo` é um booleano (`true`/`false`).

### Dados para nossos testes

```sql
INSERT INTO animal (especie, nome, idade, observacao, ativo) VALUES
('Leão', 'Simba', 5, 'Animal tranquilo', TRUE),
('Girafa', 'Mel', 3, 'Gosta de folhas frescas', TRUE),
('Tigre', 'Thor', 7, 'Acompanhamento diário', TRUE),
('Elefante', 'Dumbo', 12, 'Gosta de banho', TRUE),
('Macaco', 'Chico', 4, 'Muito brincalhão', FALSE);

INSERT INTO zelador (nome, especialidade, idade, observacao, ativo) VALUES
('Carlos Silva', 'Mamíferos', 35, 'Turno da manhã', TRUE),
('Ana Souza', 'Aves', 28, 'Aves tropicais', TRUE),
('Roberto Lima', 'Répteis', 42, 'Responsável pelo terrário', TRUE),
('Mariana Costa', 'Felinos', 31, 'Setor dos felinos', TRUE),
('Juliana Martins', 'Higienização', 39, 'Limpeza de recintos', FALSE);

SELECT * FROM animal ORDER BY id;
SELECT * FROM zelador ORDER BY id;
```

---

## 3. Organização do projeto no NetBeans

Vamos criar o projeto **ZooNewT**, adicionar o driver **PostgreSQL JDBC** às bibliotecas e organizar as classes assim:

```text
src/
├── conexao/
│   └── Conexao.java
├── view/
│   ├── FrmPrincipal.java
│   ├── FrmCadastroAnimal.java
│   ├── FrmCadastroZelador.java
│   ├── FrmConsultaAnimal.java
│   ├── FrmConsultaZelador.java
│   ├── FrmRelatorioAnimal.java
│   ├── FrmRelatorioZelador.java
│   ├── FrmSobre.java
│   └── FrmAjuda.java
└── imagens/
    ├── logo.png
    ├── animais.png
    ├── zeladores.png
    ├── relatorio.png
    ├── sobre.png
    └── ajuda.png
```

**Todas as telas serão criadas em `New > JFrame Form`.** Usaremos `DISPOSE_ON_CLOSE` nas janelas secundárias e `EXIT_ON_CLOSE` somente na janela principal.

### 3.1 Classe de conexão

```java
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conectar() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/Zoo_new_T";
        String usuario = "postgres";
        String senha = "SUA_SENHA";
        return DriverManager.getConnection(url, usuario, senha);
    }
}
```

Vamos ajustar usuário e senha para nosso ambiente. **Não publicaremos senhas reais no GitHub.**

---

## 4. Menu principal — `FrmPrincipal`

Na tela principal, colocaremos um logotipo do zoológico, título, mensagem de boas-vindas e `JMenuBar`.

```text
Cadastro
  ├── Animais
  └── Zeladores
Consulta
  ├── Animais
  └── Zeladores
Relatório
  ├── Relatório de Animais
  └── Relatório de Zeladores
Sobre
  └── Sobre o Sistema
Ajuda
  └── Como Utilizar
Sair
  └── Encerrar Sistema
```

No evento de **Cadastro > Animais**, escreveremos:

```java
private void mnuCadastroAnimalActionPerformed(java.awt.event.ActionEvent evt) {
    FrmCadastroAnimal tela = new FrmCadastroAnimal();
    tela.setLocationRelativeTo(null);
    tela.setVisible(true);
}
```

Faremos o mesmo para os outros submenus, trocando a classe. No submenu **Sair**:

```java
int resposta = javax.swing.JOptionPane.showConfirmDialog(
    this, "Deseja encerrar o sistema?", "Sair",
    javax.swing.JOptionPane.YES_NO_OPTION);
if (resposta == javax.swing.JOptionPane.YES_OPTION) {
    System.exit(0);
}
```

---

## 5. Cadastro de animais — `FrmCadastroAnimal`

Vamos colocar uma imagem e os seguintes componentes:

| Campo | Componente | Nome |
|---|---|---|
| ID | JTextField, não editável | `txtId` |
| Espécie | JTextField | `txtEspecie` |
| Nome | JTextField | `txtNome` |
| Idade | JTextField | `txtIdade` |
| Observação | JTextArea em JScrollPane | `txtObservacao` |
| Ativo | JCheckBox | `chkAtivo` |
| Imagem | JLabel | `lblImagem` |
| Ações | JButton | `btnCadastrar`, `btnLimpar`, `btnFechar` |

O campo ID ficará vazio no cadastro: quem gera o número é o PostgreSQL.

### 5.1 Programando diretamente o botão Cadastrar

Imports na tela:

```java
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
```

Evento:

```java
private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {
    String especie = txtEspecie.getText().trim();
    String nome = txtNome.getText().trim();
    String idadeTexto = txtIdade.getText().trim();
    String observacao = txtObservacao.getText().trim();
    boolean ativo = chkAtivo.isSelected();

    if (especie.isEmpty() || nome.isEmpty() || idadeTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha espécie, nome e idade.");
        return;
    }

    int idade;
    try {
        idade = Integer.parseInt(idadeTexto);
        if (idade < 0) {
            JOptionPane.showMessageDialog(this, "Idade não pode ser negativa.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro.");
        return;
    }

    String sql = "INSERT INTO animal (especie, nome, idade, observacao, ativo) "
               + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, especie);
        stmt.setString(2, nome);
        stmt.setInt(3, idade);
        stmt.setString(4, observacao);
        stmt.setBoolean(5, ativo);

        if (stmt.executeUpdate() == 1) {
            JOptionPane.showMessageDialog(this, "Animal cadastrado!");
            txtEspecie.setText("");
            txtNome.setText("");
            txtIdade.setText("");
            txtObservacao.setText("");
            chkAtivo.setSelected(true);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
    }
}
```

**Botão Limpar:** limpar os campos e marcar `chkAtivo`. **Botão Fechar:** `dispose();`.

---

## 6. Cadastro de zeladores — `FrmCadastroZelador`

Vamos repetir a ideia, mas com **ID, Nome, Especialidade, Idade, Observação e Ativo**, além de uma imagem. Componentes: `txtId`, `txtNome`, `txtEspecialidade`, `txtIdade`, `txtObservacao`, `chkAtivo`, `lblImagem` e botões Cadastrar, Limpar e Fechar.

O comando SQL será:

```sql
INSERT INTO zelador (nome, especialidade, idade, observacao, ativo)
VALUES (?, ?, ?, ?, ?);
```

Depois de validar os campos, escreveremos **dentro do botão Cadastrar**:

```java
String sql = "INSERT INTO zelador (nome, especialidade, idade, observacao, ativo) "
           + "VALUES (?, ?, ?, ?, ?)";
try (Connection conexao = Conexao.conectar();
     PreparedStatement stmt = conexao.prepareStatement(sql)) {
    stmt.setString(1, txtNome.getText().trim());
    stmt.setString(2, txtEspecialidade.getText().trim());
    stmt.setInt(3, idade); // previamente validada com Integer.parseInt
    stmt.setString(4, txtObservacao.getText().trim());
    stmt.setBoolean(5, chkAtivo.isSelected());
    stmt.executeUpdate();
    JOptionPane.showMessageDialog(this, "Zelador cadastrado!");
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
}
```

**Atividade:** nós deveremos escrever as validações de nome, especialidade e idade seguindo o cadastro de animais. Não usaremos uma classe `Zelador`.

---

## 7. Consulta de animais — `FrmConsultaAnimal`

Nossa tela terá uma imagem, uma `JTable` chamada `tblAnimais` com colunas nesta ordem:

```text
ID | Espécie | Nome | Idade | Observação | Ativo
```

Também colocaremos:
- `cbFiltro` (`JComboBox`): **ID, ESPÉCIE, NOME**.
- `txtPesquisa` (`JTextField`).
- Campos de edição: `txtId` (não editável), `txtEspecie`, `txtNome`, `txtIdade`, `txtObservacao` e `chkAtivo`.
- Botões `btnAtualizar`, `btnLocalizar`, `btnAlterar`, `btnExcluir` e `btnFechar`.

### 7.1 Método `atualizarTabela()`

Esta é nossa **única rotina auxiliar de consulta**, pois será chamada ao abrir a janela e depois de alterações e exclusões.

Imports adicionais:

```java
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
```

```java
private void atualizarTabela() {
    DefaultTableModel modelo = (DefaultTableModel) tblAnimais.getModel();
    modelo.setRowCount(0);

    String sql = "SELECT id, especie, nome, idade, observacao, ativo "
               + "FROM animal ORDER BY id";

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            modelo.addRow(new Object[] {
                rs.getInt("id"), rs.getString("especie"),
                rs.getString("nome"), rs.getInt("idade"),
                rs.getString("observacao"), rs.getBoolean("ativo")
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
    }
}
```

No construtor, **depois de `initComponents()`**:

```java
public FrmConsultaAnimal() {
    initComponents();
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    atualizarTabela();
}
```

No botão Atualizar:

```java
atualizarTabela();
```

### 7.2 Botão Localizar — SQL direto no botão

```java
private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {
    String filtro = cbFiltro.getSelectedItem().toString();
    String valor = txtPesquisa.getText().trim();
    if (valor.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Digite o valor da pesquisa.");
        return;
    }

    String sql;
    switch (filtro) {
        case "ID": sql = "SELECT * FROM animal WHERE id = ? ORDER BY id"; break;
        case "ESPÉCIE": sql = "SELECT * FROM animal WHERE especie ILIKE ? ORDER BY id"; break;
        case "NOME": sql = "SELECT * FROM animal WHERE nome ILIKE ? ORDER BY id"; break;
        default: return;
    }

    Integer id = null;
    if (filtro.equals("ID")) {
        try {
            id = Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID deve ser numérico.");
            return;
        }
    }

    DefaultTableModel modelo = (DefaultTableModel) tblAnimais.getModel();
    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        if (id != null) stmt.setInt(1, id);
        else stmt.setString(1, "%" + valor + "%");

        try (ResultSet rs = stmt.executeQuery()) {
            modelo.setRowCount(0);
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getInt("id"), rs.getString("especie"),
                    rs.getString("nome"), rs.getInt("idade"),
                    rs.getString("observacao"), rs.getBoolean("ativo")
                });
            }
        }
        if (modelo.getRowCount() == 0)
            JOptionPane.showMessageDialog(this, "Nenhum animal encontrado.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao localizar: " + e.getMessage());
    }
}
```

### 7.3 Seleção de linha

No evento `mouseClicked` da JTable, nós preencheremos os campos de edição:

```java
int linha = tblAnimais.getSelectedRow();
if (linha == -1) return;
txtId.setText(tblAnimais.getValueAt(linha, 0).toString());
txtEspecie.setText(tblAnimais.getValueAt(linha, 1).toString());
txtNome.setText(tblAnimais.getValueAt(linha, 2).toString());
txtIdade.setText(tblAnimais.getValueAt(linha, 3).toString());
Object obs = tblAnimais.getValueAt(linha, 4);
txtObservacao.setText(obs == null ? "" : obs.toString());
chkAtivo.setSelected(Boolean.TRUE.equals(tblAnimais.getValueAt(linha, 5)));
```

### 7.4 Botão Alterar — direto no evento

```java
private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {
    int linha = tblAnimais.getSelectedRow();
    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um animal.");
        return;
    }
    int id = Integer.parseInt(tblAnimais.getValueAt(linha, 0).toString());
    String especie = txtEspecie.getText().trim();
    String nome = txtNome.getText().trim();
    if (especie.isEmpty() || nome.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha espécie e nome.");
        return;
    }
    int idade;
    try {
        idade = Integer.parseInt(txtIdade.getText().trim());
        if (idade < 0) {
            JOptionPane.showMessageDialog(this, "Idade inválida.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Idade deve ser numérica.");
        return;
    }
    if (JOptionPane.showConfirmDialog(this, "Alterar o animal " + id + "?",
            "Confirmação", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

    String sql = "UPDATE animal SET especie=?, nome=?, idade=?, observacao=?, ativo=? WHERE id=?";
    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, especie);
        stmt.setString(2, nome);
        stmt.setInt(3, idade);
        stmt.setString(4, txtObservacao.getText().trim());
        stmt.setBoolean(5, chkAtivo.isSelected());
        stmt.setInt(6, id);
        int linhas = stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, linhas == 1 ? "Alterado!" : "Não encontrado.");
        atualizarTabela();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao alterar: " + e.getMessage());
    }
}
```

### 7.5 Botão Excluir — direto no evento

```java
private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {
    int linha = tblAnimais.getSelectedRow();
    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um animal.");
        return;
    }
    int id = Integer.parseInt(tblAnimais.getValueAt(linha, 0).toString());
    int resposta = JOptionPane.showConfirmDialog(this,
        "Deseja excluir definitivamente o animal ID " + id + "?",
        "Confirmar exclusão", JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    if (resposta != JOptionPane.YES_OPTION) return;

    String sql = "DELETE FROM animal WHERE id = ?";
    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int linhas = stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, linhas == 1 ? "Excluído!" : "Não encontrado.");
        atualizarTabela();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
    }
}
```

**Atenção:** sem `WHERE id = ?`, um `DELETE` poderia apagar todos os registros. A confirmação deve acontecer **antes** da exclusão.

---

## 8. Consulta de zeladores — `FrmConsultaZelador`

Vamos criar uma tela semelhante, mas com as colunas **ID, Nome, Especialidade, Idade, Observação, Ativo**. Componentes: `tblZeladores`, `cbFiltro` com **ID, NOME, ESPECIALIDADE**, `txtPesquisa`, campos de edição, `chkAtivo`, imagem e botões.

Nós vamos repetir a programação direta nos botões, adaptando os SQLs:

```sql
-- Atualizar tabela
SELECT id, nome, especialidade, idade, observacao, ativo
FROM zelador ORDER BY id;

-- Localizar por ID
SELECT * FROM zelador WHERE id = ? ORDER BY id;

-- Localizar por parte do nome
SELECT * FROM zelador WHERE nome ILIKE ? ORDER BY id;

-- Localizar por parte da especialidade
SELECT * FROM zelador WHERE especialidade ILIKE ? ORDER BY id;

-- Alterar
UPDATE zelador
SET nome=?, especialidade=?, idade=?, observacao=?, ativo=?
WHERE id=?;

-- Excluir
DELETE FROM zelador WHERE id=?;
```

No método `atualizarTabela()`, usaremos `ResultSet` e `DefaultTableModel`, como na consulta de animais, mas com `rs.getString("especialidade")`. No construtor, chamaremos `atualizarTabela()` depois de `initComponents()`.

**Desafio obrigatório:** a turma deverá escrever os eventos de Localizar, Alterar e Excluir completos, **sem criar métodos separados para essas operações**, utilizando o código de animais como referência. A validação da idade e a confirmação de exclusão são obrigatórias.

---

## 9. Relatórios — duas janelas JFrame

### `FrmRelatorioAnimal`
- Imagem de animais.
- `tblRelatorio` com todos os campos.
- Botões **Gerar Relatório** e **Fechar**.
- Labels `lblTotal`, `lblAtivos` e `lblInativos`.

```sql
SELECT id, especie, nome, idade, observacao, ativo
FROM animal ORDER BY id;

SELECT COUNT(*) AS total,
       COUNT(*) FILTER (WHERE ativo = TRUE) AS ativos,
       COUNT(*) FILTER (WHERE ativo = FALSE) AS inativos
FROM animal;
```

No botão **Gerar Relatório**, vamos executar as duas consultas: a primeira preencherá a JTable e a segunda preencherá os três labels. Podemos reutilizar o padrão `Connection` + `PreparedStatement` + `ResultSet` mostrado na consulta de animais.

### `FrmRelatorioZelador`
- Imagem dos zeladores.
- `tblRelatorio` com os seis campos de zelador.
- Botões **Gerar Relatório** e **Fechar**.
- Indicadores de total, ativos e inativos.

```sql
SELECT id, nome, especialidade, idade, observacao, ativo
FROM zelador ORDER BY id;

SELECT COUNT(*) AS total,
       COUNT(*) FILTER (WHERE ativo = TRUE) AS ativos,
       COUNT(*) FILTER (WHERE ativo = FALSE) AS inativos
FROM zelador;
```

**Nesta etapa, relatório significa exibição em JTable.** Exportação para PDF ou impressão será um desafio extra.

---

## 10. Tela Sobre — `FrmSobre`

Criaremos um JFrame com imagem e informações:

```text
ZOO NEW T
Sistema de Gestão de Animais e Zeladores
Versão 1.0
Tecnologias: Java Swing, JDBC e PostgreSQL
Turma: ______________________
Integrantes: _________________
```

Teremos um botão **Fechar** com `dispose()`.

## 11. Tela Ajuda — `FrmAjuda`

Criaremos outro JFrame com imagem, `JTextArea` não editável e botão Fechar. A ajuda explicará como:

1. Cadastrar animais e zeladores.
2. Consultar por ID ou parte do texto.
3. Selecionar uma linha para edição.
4. Alterar um registro.
5. Excluir após confirmação.
6. Atualizar a JTable.
7. Visualizar relatórios.
8. Usar o campo Ativo para identificar registros em atividade.

---

## 12. Colocando imagens nas telas

Para cada janela, vamos incluir um `JLabel` com imagem. Colocaremos as imagens em `src/imagens/` e usaremos caminhos de recursos, não caminhos absolutos do Windows:

```java
lblImagem.setIcon(new javax.swing.ImageIcon(
    getClass().getResource("/imagens/animais.png")
));
```

Vamos usar imagens diferentes para cadastro, consulta, relatórios, Sobre e Ajuda. **Verifiquem o nome e a extensão de cada arquivo** para evitar erros ao carregar recursos.

---

## 13. Fluxo do sistema

```text
                   FrmPrincipal
                        |
                    JMenuBar
                        |
        +---------------+---------------+
        |               |               |
     Cadastro         Consulta        Relatório
        |               |               |
  Animal/Zelador   Animal/Zelador   Animal/Zelador
        |               |               |
      INSERT      SELECT/UPDATE/DELETE  SELECT
        |               |               |
        +---------------+---------------+
                        |
                    Conexao
                        |
                   PostgreSQL
                        |
                    Zoo_new_T
```

Os menus **Sobre** e **Ajuda** abrirão janelas informativas. O menu **Sair** encerrará o sistema após confirmação.

---

## 14. Roteiro de desenvolvimento

- [ ] Criar o banco `Zoo_new_T`.
- [ ] Criar as tabelas e inserir dados de teste.
- [ ] Criar o projeto no NetBeans e adicionar o JDBC.
- [ ] Criar a classe `Conexao`.
- [ ] Criar `FrmPrincipal` com seis menus e submenus.
- [ ] Criar `FrmCadastroAnimal` e programar Cadastrar/Limpar/Fechar.
- [ ] Criar `FrmCadastroZelador` e programar Cadastrar/Limpar/Fechar.
- [ ] Criar `FrmConsultaAnimal` com JTable, atualização, pesquisa, alteração e exclusão.
- [ ] Criar `FrmConsultaZelador` com as mesmas operações.
- [ ] Criar os dois relatórios com contagem de ativos e inativos.
- [ ] Criar as telas Sobre e Ajuda.
- [ ] Adicionar imagem em **todas** as janelas.
- [ ] Testar todos os menus, botões e operações no pgAdmin.

## 15. Critérios de avaliação

| Critério | Pontos |
|---|---:|
| Banco de dados e tabelas | 15 |
| Conexão JDBC | 10 |
| Menu principal e submenus | 15 |
| Dois cadastros | 20 |
| Duas consultas com alteração e exclusão | 20 |
| Dois relatórios | 10 |
| Imagens, Sobre, Ajuda e organização visual | 5 |
| Testes e funcionamento geral | 5 |
| **Total** | **100** |

## 16. Desafios extras

- Filtrar somente registros ativos.
- Pesquisar animais por faixa de idade.
- Criar botão **Desativar** em vez de excluir definitivamente.
- Ordenar colunas da JTable.
- Exportar relatórios para PDF.

---

## Conclusão

Neste projeto, nós vamos reunir o que aprendemos sobre **Java Swing, JFrame, menus, JTable, eventos, SQL, JDBC e PostgreSQL**. Vamos entender cada operação diretamente nos botões e consolidar o funcionamento de um sistema desktop completo. Em aulas futuras, poderemos reorganizar o código usando MVC e DAO, comparando as vantagens de cada abordagem.

**Desafio final: fazer o Zoo New T funcionar do menu principal até o banco de dados! 🦁🐘🐒**
