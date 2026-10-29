# 🚗 AutoManager — Sistema de Gestão de Oficina Mecânica

## Projeto | Java Swing, NetBeans e PostgreSQL

**Proposta:** desenvolver um sistema desktop para uma oficina mecânica, utilizando telas `JFrame`, menus e submenus, cadastros, consultas, movimentação de ordens de serviço e relatórios.

**Orientação do professor:** Nesta atividade, nós vamos construir um sistema inspirado em uma necessidade real do mercado. Eu vou apresentar os requisitos e algumas dicas, mas **não entregarei o código pronto**. Vocês deverão aplicar os conhecimentos de Java Swing, JDBC e PostgreSQL para implementar cada funcionalidade.

> **Regras da atividade:** não criar classes de entidade (`Cliente`, `Veiculo`, `Mecanico`, `Servico`, `OrdemServico`), DAO ou MVC. Os comandos de banco de dados serão programados diretamente nos eventos dos botões. A classe `Conexao` poderá centralizar a conexão JDBC. Todas as janelas visuais deverão ser `JFrame` e possuir uma imagem relacionada à sua finalidade.

---

## Prazos acordados com a Turma

- 1º Check - 03/11 (Tela principal / Banco de Dados e Tabelas / Modulos de Clientes).
- 2º Check - 05/11 (Modulos de Veículos / Mecanicos ).
- 3º Check - 09/11 (Modulo de Serviços / Ordem de Serviços).

###Entrega Final 10/11

---

## 1. Objetivos de aprendizagem

Ao final, nós deveremos ser capazes de:

- Criar um banco de dados relacional e definir tipos, chaves e restrições.
- Construir uma janela principal com `JMenuBar`, `JMenu` e `JMenuItem`.
- Abrir diferentes janelas `JFrame` a partir dos submenus.
- Desenvolver operações de cadastro, consulta, alteração e exclusão.
- Preencher `JTable` com dados obtidos do PostgreSQL.
- Relacionar clientes, veículos, mecânicos, serviços e ordens de serviço.
- Implementar validações e confirmações antes de ações importantes.
- Gerar relatórios visuais com filtros e indicadores.

## 2. Cenário do projeto

A oficina **AutoManager** precisa organizar seus clientes, veículos, profissionais e serviços oferecidos. Quando um cliente leva seu veículo à oficina, a equipe registra uma **ordem de serviço (OS)**, identifica o mecânico responsável, escolhe o serviço a realizar e acompanha a situação do atendimento.

**Fluxo básico:** cadastrar cliente → cadastrar veículo → cadastrar mecânico e serviço → abrir ordem de serviço → atualizar status e valores → consultar relatórios.

Nesta primeira versão, cada ordem de serviço estará vinculada a **um veículo, um mecânico e um serviço**. Essa simplificação permitirá trabalhar com chaves estrangeiras sem criar uma tabela adicional de itens da OS. Como desafio futuro, poderemos permitir vários serviços na mesma ordem.

---

## 3. Banco de dados e tabelas

**Nome do banco:** `AutoManager_T261T`.

No PostgreSQL, nós criaremos **cinco tabelas**: `cliente`, `veiculo`, `mecanico`, `servico` e `ordem_servico`.

### 3.1 Tabela `cliente`

| Campo | Tipo PostgreSQL sugerido | Regras |
|---|---|---|
| `id` | `INTEGER` com identidade automática | **PRIMARY KEY**, único, obrigatório e gerado automaticamente |
| `nome` | `VARCHAR(120)` | **NOT NULL**; não aceitar texto vazio na aplicação |
| `cpf` | `VARCHAR(11)` | **NOT NULL**, **UNIQUE**; armazenar somente dígitos; validar 11 caracteres e, como melhoria, os dígitos verificadores |
| `telefone` | `VARCHAR(20)` | **NOT NULL**; validar preenchimento |
| `email` | `VARCHAR(150)` | Opcional; quando preenchido, validar formato na aplicação |
| `endereco` | `VARCHAR(200)` | Opcional |
| `ativo` | `BOOLEAN` | **NOT NULL**, **DEFAULT TRUE** |

**Dica:** CPF deve ser texto, e não número, para preservar zeros à esquerda. O CPF não poderá se repetir entre clientes.

### 3.2 Tabela `veiculo`

| Campo | Tipo PostgreSQL sugerido | Regras |
|---|---|---|
| `id` | `INTEGER` com identidade automática | **PRIMARY KEY** |
| `cliente_id` | `INTEGER` | **NOT NULL**, **FOREIGN KEY** para `cliente(id)` |
| `placa` | `VARCHAR(7)` | **NOT NULL**, **UNIQUE**; armazenar sem hífen e padronizar maiúsculas |
| `marca` | `VARCHAR(60)` | **NOT NULL** |
| `modelo` | `VARCHAR(80)` | **NOT NULL** |
| `ano` | `INTEGER` | **NOT NULL**, **CHECK** para um intervalo válido, por exemplo, de 1900 a 2100 |
| `cor` | `VARCHAR(40)` | Opcional |
| `observacao` | `TEXT` | Opcional |
| `ativo` | `BOOLEAN` | **NOT NULL**, **DEFAULT TRUE** |

**Dica:** um cliente pode possuir vários veículos, mas cada veículo terá um cliente responsável nesta versão. A placa não poderá se repetir.

### 3.3 Tabela `mecanico`

| Campo | Tipo PostgreSQL sugerido | Regras |
|---|---|---|
| `id` | `INTEGER` com identidade automática | **PRIMARY KEY** |
| `nome` | `VARCHAR(120)` | **NOT NULL** |
| `especialidade` | `VARCHAR(100)` | **NOT NULL**; exemplos: Motor, Elétrica, Suspensão |
| `telefone` | `VARCHAR(20)` | Opcional |
| `data_admissao` | `DATE` | **NOT NULL**, **DEFAULT CURRENT_DATE** |
| `observacao` | `TEXT` | Opcional |
| `ativo` | `BOOLEAN` | **NOT NULL**, **DEFAULT TRUE** |

**Dica:** dois mecânicos podem ter o mesmo nome ou especialidade. O identificador `id` é o campo que não poderá se repetir.

### 3.4 Tabela `servico`

| Campo | Tipo PostgreSQL sugerido | Regras |
|---|---|---|
| `id` | `INTEGER` com identidade automática | **PRIMARY KEY** |
| `descricao` | `VARCHAR(150)` | **NOT NULL**, **UNIQUE** nesta atividade |
| `categoria` | `VARCHAR(80)` | **NOT NULL**; exemplos: Revisão, Freios, Motor |
| `valor_base` | `NUMERIC(10,2)` | **NOT NULL**, **DEFAULT 0**, **CHECK** valor maior ou igual a zero |
| `tempo_estimado_horas` | `NUMERIC(5,2)` | Opcional; **CHECK** maior que zero quando informado |
| `observacao` | `TEXT` | Opcional |
| `ativo` | `BOOLEAN` | **NOT NULL**, **DEFAULT TRUE** |

**Dica:** para valores monetários, utilizaremos `NUMERIC`, evitando erros de arredondamento de tipos de ponto flutuante. A descrição do serviço será única **por decisão didática**; em sistemas reais, pode ser necessário usar um código de serviço.

### 3.5 Tabela `ordem_servico`

| Campo | Tipo PostgreSQL sugerido | Regras |
|---|---|---|
| `id` | `INTEGER` com identidade automática | **PRIMARY KEY**; número da OS |
| `veiculo_id` | `INTEGER` | **NOT NULL**, **FOREIGN KEY** para `veiculo(id)` |
| `mecanico_id` | `INTEGER` | **NOT NULL**, **FOREIGN KEY** para `mecanico(id)` |
| `servico_id` | `INTEGER` | **NOT NULL**, **FOREIGN KEY** para `servico(id)` |
| `data_abertura` | `TIMESTAMP` | **NOT NULL**, **DEFAULT CURRENT_TIMESTAMP** |
| `data_conclusao` | `TIMESTAMP` | Opcional; preencher quando a OS for concluída |
| `descricao_problema` | `TEXT` | **NOT NULL**; não aceitar texto vazio na aplicação |
| `diagnostico` | `TEXT` | Opcional |
| `status` | `VARCHAR(20)` | **NOT NULL**, **DEFAULT 'ABERTA'**, **CHECK** entre `ABERTA`, `EM_ANDAMENTO`, `CONCLUIDA`, `CANCELADA` |
| `valor_cobrado` | `NUMERIC(10,2)` | **NOT NULL**, **DEFAULT 0**, **CHECK** valor maior ou igual a zero |
| `observacao` | `TEXT` | Opcional |

**Dicas importantes:**

- O número da OS será único e gerado automaticamente.
- O valor cobrado pode ser iniciado com o valor base do serviço selecionado, mas deverá ser armazenado na OS para preservar o valor efetivamente negociado.
- `data_conclusao` deverá permanecer vazia enquanto a OS não estiver concluída.
- Ao concluir uma OS, a aplicação deverá exigir a data de conclusão; ela não poderá ser anterior à abertura.
- Uma OS não poderá referenciar veículo, mecânico ou serviço inexistente.
- Não permitir abrir uma nova OS com mecânico, veículo ou serviço inativo, conforme regra da aplicação.
- **Não usaremos `ON DELETE CASCADE`** nos relacionamentos históricos: excluir um cliente ou veículo não deve apagar ordens de serviço por acidente.

### 3.6 Relacionamentos entre tabelas

| Origem | Destino | Relação |
|---|---|---|
| `cliente.id` | `veiculo.cliente_id` | Um cliente pode ter vários veículos |
| `veiculo.id` | `ordem_servico.veiculo_id` | Um veículo pode ter várias OS |
| `mecanico.id` | `ordem_servico.mecanico_id` | Um mecânico pode atender várias OS |
| `servico.id` | `ordem_servico.servico_id` | Um serviço pode aparecer em várias OS |

**Atenção:** o PostgreSQL garante unicidade, chaves estrangeiras, `NOT NULL` e verificações `CHECK`. Já a validação de campos preenchidos apenas com espaços, mensagens amigáveis e regras de fluxo serão implementadas na aplicação.

---

## 4. Estrutura do projeto no NetBeans

**Nome do projeto:** `AutoManager`.

Organizaremos os arquivos em pacotes:

- `conexao`: classe `Conexao`, responsável apenas por abrir a conexão JDBC.
- `view`: todas as telas do tipo `JFrame`.
- `imagens`: logotipo, carros, mecânicos, ferramentas, relatórios, ajuda e sobre.

**Não criaremos** pacotes `model`, `dao` ou `controller` nesta atividade.

### 4.1 Telas obrigatórias

| JFrame | Finalidade |
|---|---|
| `FrmPrincipal` | Menu principal e apresentação |
| `FrmCadastroCliente` | Cadastro de clientes |
| `FrmCadastroVeiculo` | Cadastro de veículos |
| `FrmCadastroMecanico` | Cadastro de mecânicos |
| `FrmCadastroServico` | Cadastro de serviços |
| `FrmCadastroOS` | Abertura de ordens de serviço |
| `FrmConsultaCliente` | Pesquisa e manutenção de clientes |
| `FrmConsultaVeiculo` | Pesquisa e manutenção de veículos |
| `FrmConsultaMecanico` | Pesquisa e manutenção de mecânicos |
| `FrmConsultaServico` | Pesquisa e manutenção de serviços |
| `FrmConsultaOS` | Pesquisa, atualização de status e manutenção das OS |
| `FrmRelatorioClientes` | Relação de clientes |
| `FrmRelatorioVeiculos` | Relação de veículos |
| `FrmRelatorioMecanicos` | Relação de mecânicos |
| `FrmRelatorioServicos` | Relação de serviços |
| `FrmRelatorioOS` | Relatório gerencial de ordens de serviço |
| `FrmSobre` | Informações do sistema |
| `FrmAjuda` | Instruções de utilização |

**Total: 18 janelas `JFrame`.**

---

## 5. Menus e submenus

Na janela principal, criaremos uma `JMenuBar` com os seguintes menus:

| Menu | Submenus |
|---|---|
| **Cadastro** | Clientes; Veículos; Mecânicos; Serviços; Abrir Ordem de Serviço |
| **Consulta** | Clientes; Veículos; Mecânicos; Serviços; Ordens de Serviço |
| **Relatório** | Clientes; Veículos; Mecânicos; Serviços; Ordens de Serviço |
| **Sobre** | Sobre o AutoManager |
| **Ajuda** | Como Utilizar o Sistema |
| **Sair** | Encerrar Sistema |

Cada submenu deverá abrir sua respectiva janela `JFrame`.

**Regras de navegação:**

- A janela principal terá imagem de destaque e título **AutoManager — Gestão de Oficina Mecânica**.
- Todas as janelas secundárias terão uma imagem, título, campos ou indicadores e botão **Fechar**.
- Fechar uma janela secundária **não** deverá encerrar o programa.
- O submenu **Encerrar Sistema** deverá solicitar confirmação antes de sair.

---

## 6. Telas de cadastro

### 6.1 Cadastro de clientes

**Campos na tela:** ID (somente leitura), Nome, CPF, Telefone, E-mail, Endereço e Ativo.

**Componentes sugeridos:** `JTextField`, `JCheckBox`, `JLabel` e `JButton`.

**Botões:** Cadastrar, Limpar e Fechar.

**Validações:** nome, CPF e telefone obrigatórios; CPF com 11 dígitos, não duplicado; e-mail válido quando preenchido.

### 6.2 Cadastro de veículos

**Campos na tela:** ID (somente leitura), Cliente (`Buscar na Tabela Cliente`), Placa, Marca, Modelo, Ano, Cor, Observação e Ativo.

**Componentes sugeridos:** `JTextField`, `JComboBox`, `JTextArea`, `JCheckBox` e botões.

**Botões:** Cadastrar, Limpar e Fechar.

**Validações:** cliente existente e ativo; placa obrigatória e única; marca e modelo obrigatórios; ano dentro do intervalo definido.

**Dica:** o `Cliente` deverá exibir uma identificação amigável do cliente, mas o registro salvo deverá usar seu `id`. Sem criar classe de entidade, nós poderemos manter uma lista paralela de IDs correspondente às posições do combo ou usar outra estratégia simples orientada pelo professor.

### 6.3 Cadastro de mecânicos

**Campos na tela:** ID, Nome, Especialidade, Telefone, Data de Admissão, Observação e Ativo.

**Botões:** Cadastrar, Limpar e Fechar.

**Validações:** nome e especialidade obrigatórios; data de admissão válida; campo Ativo inicialmente marcado.

### 6.4 Cadastro de serviços

**Campos na tela:** ID, Descrição, Categoria, Valor Base, Tempo Estimado em Horas, Observação e Ativo.

**Botões:** Cadastrar, Limpar e Fechar.

**Validações:** descrição única e obrigatória; categoria obrigatória; valor não negativo; tempo estimado positivo quando informado.

**Dica:** não devemos armazenar valores de dinheiro como `DOUBLE` na tabela.

### 6.5 Abertura de ordem de serviço

**Campos na tela:** Número da OS (automático), Veículo (`buscar na tabela Veículo`), Mecânico (`buscar na tabela Mecanico`), Serviço (`buscar na tabela Serviço`), Data de Abertura (automática), Descrição do Problema, Diagnóstico, Status, Valor Cobrado, Observação e Data de Conclusão.

**Botões:** Abrir OS, Limpar e Fechar.

**Regras:**

1. Selecionar um veículo cadastrado e ativo.
2. Selecionar um mecânico ativo.
3. Selecionar um serviço ativo.
4. Informar a descrição do problema.
5. Iniciar o status em **ABERTA**.
6. Utilizar a data e a hora atuais como abertura.
7. Manter a data de conclusão vazia até a finalização.
8. Sugerir o valor base do serviço, permitindo definir o valor cobrado.

**Dica:** uma ordem de serviço representa um atendimento específico. O mesmo veículo poderá retornar à oficina e gerar outras ordens.

---

## 7. Telas de consulta e manutenção

Criaremos uma tela de consulta para **cada uma das cinco tabelas**.

Todas deverão possuir:

- Imagem relacionada ao módulo.
- `JTable` com os registros.
- `JComboBox` para selecionar o tipo de pesquisa.
- `JTextField` para informar o valor pesquisado.
- Botões **Localizar**, **Atualizar**, **Alterar**, **Excluir** e **Fechar**.
- Campos para editar os dados do registro selecionado.
- Confirmação antes de alterar ou excluir.
- Carregamento automático dos dados ao abrir a janela.

**Orientação:** faremos o SQL de localização, alteração e exclusão diretamente nos eventos dos botões, sem DAO. Poderemos utilizar um método auxiliar `atualizarTabela()` apenas para recarregar a `JTable`.

### 7.1 Consulta de clientes

**Colunas:** ID, Nome, CPF, Telefone, E-mail, Endereço, Ativo.

**Filtros:** ID, Nome, CPF.

**Atenção:** um cliente com veículos cadastrados não poderá ser excluído fisicamente enquanto houver referências. Nesse caso, o sistema deverá informar a restrição e oferecer a alternativa de desativação.

### 7.2 Consulta de veículos

**Colunas:** ID, Cliente, Placa, Marca, Modelo, Ano, Cor, Observação, Ativo.

**Filtros:** ID, Placa, Marca, Modelo, Cliente.

**Atenção:** veículos com ordens de serviço não deverão ser apagados, para preservar o histórico da oficina.

### 7.3 Consulta de mecânicos

**Colunas:** ID, Nome, Especialidade, Telefone, Data de Admissão, Ativo.

**Filtros:** ID, Nome, Especialidade.

**Atenção:** um mecânico com OS vinculada deverá ser desativado, e não excluído fisicamente.

### 7.4 Consulta de serviços

**Colunas:** ID, Descrição, Categoria, Valor Base, Tempo Estimado, Ativo.

**Filtros:** ID, Descrição, Categoria.

**Atenção:** não permitir valores negativos ao alterar preços; serviços já utilizados em OS devem preservar o histórico.

### 7.5 Consulta de ordens de serviço

**Colunas sugeridas:** Número da OS, Placa, Cliente, Mecânico, Serviço, Data de Abertura, Status, Valor Cobrado e Data de Conclusão.

**Filtros:** Número da OS, Placa, Cliente, Mecânico, Status e Período de Abertura.

**Campos de edição:** Mecânico, Serviço, Diagnóstico, Status, Valor Cobrado, Observação e Data de Conclusão.

**Regras:**

- Permitir alterar o status para **EM_ANDAMENTO**, **CONCLUIDA** ou **CANCELADA**, respeitando o fluxo definido.
- Exigir data de conclusão quando a OS for concluída.
- Não permitir data de conclusão anterior à abertura.
- Não permitir valor cobrado negativo.
- Antes de excluir uma OS, solicitar confirmação; **como regra recomendada**, preservar o histórico e preferir o status **CANCELADA** à exclusão definitiva.

**Dica:** nas consultas de OS, precisaremos combinar dados de várias tabelas. Vamos revisar o conceito de `JOIN` e as chaves estrangeiras.

---

## 8. Relatórios

Cada relatório será uma janela `JFrame` com imagem, título, filtros, `JTable`, indicadores e botões **Gerar Relatório** e **Fechar**.

| Relatório | Informações esperadas |
|---|---|
| Clientes | Relação de clientes; total, ativos e inativos |
| Veículos | Relação por cliente, marca ou situação; total de veículos |
| Mecânicos | Relação por especialidade; total, ativos e inativos |
| Serviços | Lista de serviços; valores base; serviços ativos |
| Ordens de Serviço | OS por período e status; quantidade aberta, em andamento, concluída e cancelada; soma dos valores cobrados nas OS concluídas |

**Dicas:**

- Utilizaremos consultas com filtros e funções de agregação do PostgreSQL.
- No relatório de OS, a soma dos valores de OS concluídas será um **indicador de serviços concluídos**, não necessariamente de pagamentos recebidos, pois esta versão não terá controle financeiro de recebimentos.
- Exportar ou imprimir relatórios será uma **atividade extra**, não obrigatória.

---

## 9. Telas Sobre e Ajuda

### `FrmSobre`

Exibir logotipo, nome do sistema, versão, tecnologias utilizadas, turma, integrantes e uma descrição breve do AutoManager.

### `FrmAjuda`

Explicar, em linguagem simples, como cadastrar clientes, veículos, mecânicos e serviços; abrir OS; localizar registros; alterar dados; concluir ou cancelar atendimentos; gerar relatórios e encerrar o programa.

As duas janelas terão imagem e botão **Fechar**.

---

## 10. Identidade visual e imagens

Todas as telas deverão ter uma imagem coerente com a função apresentada:

- Principal: oficina ou automóvel.
- Clientes: atendimento ao cliente.
- Veículos: carro ou garagem.
- Mecânicos: ferramentas ou profissional trabalhando.
- Serviços: manutenção automotiva.
- Ordens de Serviço: checklist ou prancheta.
- Relatórios: gráficos ou painel gerencial.
- Sobre e Ajuda: logotipo e ícones informativos.

**Sugestão de paleta:** azul-escuro, grafite, branco e laranja para destaques. Organizaremos os componentes com alinhamento, títulos e espaçamentos consistentes.

**Dica:** armazenar as imagens nos recursos do projeto, evitando caminhos absolutos do computador do aluno.

---

## 11. Sequência sugerida de desenvolvimento

### Etapa 1 — Planejamento e banco de dados

- [ ] Identificar as cinco tabelas e seus relacionamentos.
- [ ] Criar o banco `AutoManager_T261T`.
- [ ] Definir tipos de dados, chaves primárias, chaves estrangeiras, `UNIQUE`, `NOT NULL`, `DEFAULT` e `CHECK`.
- [ ] Inserir dados fictícios para testes.

### Etapa 2 — Estrutura do projeto

- [ ] Criar projeto Java no NetBeans.
- [ ] Adicionar driver JDBC do PostgreSQL.
- [ ] Criar a classe `Conexao`.
- [ ] Criar os pacotes de telas e imagens.

### Etapa 3 — Navegação

- [ ] Criar `FrmPrincipal`.
- [ ] Montar menus e submenus.
- [ ] Criar todas as janelas `JFrame`.
- [ ] Programar a abertura das telas e a confirmação para sair.

### Etapa 4 — Cadastros

- [ ] Implementar Cliente, Veículo, Mecânico e Serviço.
- [ ] Implementar a abertura de OS.
- [ ] Validar campos e testar restrições do banco.

### Etapa 5 — Consultas e manutenção

- [ ] Implementar as cinco telas de consulta.
- [ ] Carregar `JTable` automaticamente.
- [ ] Programar Localizar, Atualizar, Alterar e Excluir.
- [ ] Testar as restrições de exclusão e o status das OS.

### Etapa 6 — Relatórios e finalização

- [ ] Implementar os cinco relatórios.
- [ ] Criar Sobre e Ajuda.
- [ ] Adicionar imagens em todas as telas.
- [ ] Revisar mensagens e navegação.
- [ ] Testar o sistema completo.

---

## 12. Testes obrigatórios

Nós deveremos demonstrar, no mínimo:

1. Cadastro de um cliente com CPF válido e único.
2. Tentativa de cadastrar CPF repetido.
3. Cadastro de dois veículos para o mesmo cliente.
4. Tentativa de cadastrar placa repetida.
5. Cadastro de um mecânico e um serviço.
6. Tentativa de informar valor negativo em serviço.
7. Abertura de OS com veículo, mecânico e serviço existentes.
8. Alteração de OS de **ABERTA** para **EM_ANDAMENTO** e depois **CONCLUIDA**.
9. Tentativa de concluir OS com data inválida.
10. Consulta de OS por placa e por status.
11. Tentativa de excluir um veículo com OS vinculada.
12. Geração de relatório com quantidade de OS por status.
13. Abertura e fechamento de todas as janelas sem encerrar o sistema indevidamente.



---

## 13. Desafios extras — evolução profissional

Depois de concluir a primeira versão, nós poderemos evoluir o AutoManager com:

- Uma tabela de **itens da OS**, permitindo vários serviços na mesma ordem.
- Controle de peças utilizadas e baixa de estoque.
- Controle de pagamentos e recebimentos.
- Histórico de alterações de status.
- Login com perfis de acesso.
- Impressão de orçamento e ordem de serviço.
- Indicadores de produtividade dos mecânicos.

---

## 14. Encerramento do professor

O **AutoManager** foi planejado para ser mais do que um exercício de cadastro: ele representa um processo de negócio real, com informações relacionadas, regras de integridade e acompanhamento de atendimentos.

Nesta etapa, **eu não vou fornecer o código pronto**. Nós vamos interpretar os requisitos, construir as telas e decidir como utilizar o JDBC nos eventos dos botões. O desafio será transformar as regras descritas neste roteiro em um sistema desktop funcional, organizado e apresentável em um portfólio profissional.

**Bom desenvolvimento! 🚗🔧**
