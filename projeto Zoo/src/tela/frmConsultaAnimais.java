package tela;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Ed Soluções
 */
public class frmConsultaAnimais extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmConsultaAnimais.class.getName());

    private void atualizarTabela(){
        DefaultTableModel modelo = (DefaultTableModel) tblAnimais.getModel();
        
        modelo.setRowCount(0);
        
        String sql = "SELECT * FROM animal "
                     + "ORDER BY id";
        
        try (Connection conex = Conexao.conectar();
             PreparedStatement stmt = conex.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery() ){
            
            while (resultado.next()){
                modelo.addRow(new Object[] {
                    resultado.getInt("id"),
                    resultado.getString("especie"),
                    resultado.getString("nome"),
                    resultado.getInt("idade")
                });
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(
                        this,
                        "Erro ao atualizar tabela:\n" + erro.getMessage(),
                        "Erro no banco de dados",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

    public frmConsultaAnimais() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Zoo 261T - Consulta de Animais");
        tblAnimais.setModel(new DefaultTableModel(new Object[][]{},
                            new String[] {"ID","Espécie","Nome","idade"}){
                    @Override
                    public boolean isCellEditable(int linha, int coluna){
                        return false;
                    }
                    });                   
                
        tblAnimais.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        atualizarTabela();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAnimais = new javax.swing.JTable();
        btnAtualizaTabela = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();
        btnLocalizar = new javax.swing.JButton();
        cmbPesquisarPor = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        txtEspecie = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtIdade = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAlterar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblAnimais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAnimais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAnimaisMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAnimais);

        btnAtualizaTabela.setBackground(new java.awt.Color(204, 255, 51));
        btnAtualizaTabela.setText("Atualizar Tabela");
        btnAtualizaTabela.addActionListener(this::btnAtualizaTabelaActionPerformed);

        btnLocalizar.setBackground(new java.awt.Color(0, 204, 204));
        btnLocalizar.setText("Localizar");
        btnLocalizar.addActionListener(this::btnLocalizarActionPerformed);

        cmbPesquisarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Especie" }));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ed Soluções\\Downloads\\Zoológico Alegre em Família (1).png")); // NOI18N

        btnExcluir.setBackground(new java.awt.Color(255, 102, 102));
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(this::btnExcluirActionPerformed);

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);

        txtId.setEditable(false);

        jLabel2.setText("ID");

        jLabel3.setText("Especie");

        jLabel4.setText("Nome");

        jLabel5.setText("Idade");

        btnAlterar.setBackground(new java.awt.Color(204, 204, 0));
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(this::btnAlterarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAtualizaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(btnSair)
                        .addGap(150, 150, 150))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocalizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtualizaTabela)
                    .addComponent(btnExcluir)
                    .addComponent(btnSair)
                    .addComponent(btnAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        String pesquisa = txtPesquisar.getText().trim();
        
        if (pesquisa.isEmpty()){
            JOptionPane.showMessageDialog(
                        this,
                        "Digite o nome ou parte do nome do animal.",
                        "Pesquisa Vazia",
                        JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tblAnimais.getModel();
        
        modelo.setRowCount(0);
        
        String tipo_pesquisa = cmbPesquisarPor.getSelectedItem().toString();
        
        String sql ="";
        
        if (tipo_pesquisa == "Nome") {
        
             sql = "SELECT * FROM animal "
                   + "WHERE nome ILIKE ? "
                   + "ORDER BY nome";
                }
        
        else {
        
             sql = "SELECT * FROM animal "
                   + "WHERE especie ILIKE ? "
                   + "ORDER BY nome";
        
        }
        
        try (Connection conex = Conexao.conectar();
                
              PreparedStatement stmt = conex.prepareStatement(sql)){
            stmt.setString(1,"%" + pesquisa + "%");
            
            try (ResultSet resultado = stmt.executeQuery()){
                while (resultado.next()){
                    modelo.addRow(new Object [] {
                        resultado.getInt("id"),
                        resultado.getString("especie"),
                        resultado.getString("nome"),
                        resultado.getInt("idade") });
                }
            }
            if (modelo.getRowCount() == 0){
             JOptionPane.showMessageDialog(this,
                           "Nenhum animal encontrado para: " + pesquisa,
                           "Resultado da Pesquisa",
                           JOptionPane.INFORMATION_MESSAGE);
              }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(this,
                    "Erro ao localizar animal:\n" + erro.getMessage(),
                    "Erro no banco de dados",
                     JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void btnAtualizaTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizaTabelaActionPerformed
    atualizarTabela();
    txtPesquisar.setText("");
    txtPesquisar.requestFocus();
    }//GEN-LAST:event_btnAtualizaTabelaActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linha = tblAnimais.getSelectedRow();
        
        if (linha == -1){
            JOptionPane.showMessageDialog(this,"Selecione um animal para excluir!");
            return;
        }
        
        int id = Integer.parseInt(tblAnimais.getValueAt(linha,0).toString());
        
        int resposta = JOptionPane.showConfirmDialog(this, 
                          "Deseja realmente excluir o animal do ID "+ id + " ?",
                          "Confirmar Exclusão",
                          JOptionPane.YES_NO_CANCEL_OPTION,
                          JOptionPane.QUESTION_MESSAGE);
        
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }
        
        String sql = "DELETE FROM animal WHERE id = ?";
        
        try (Connection conex = Conexao.conectar();
              PreparedStatement stmt = conex.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            
            int registros = stmt.executeUpdate();
            if (registros > 0) {
                JOptionPane.showMessageDialog(this, "Animal excluido com sucesso!");
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Animal não encontrado!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(this,"Erro ao excluir animal: "+ erro.getMessage());
        }
        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void tblAnimaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAnimaisMouseClicked
        // Vamos Capturar a linha selecionada
        int linha = tblAnimais.getSelectedRow();
        
        //Se não houver linha selecionada
        if (linha == -1){
            return;
        }
        
        //Vamos preencher os valos das colunas para os campos da tela
        txtId.setText(tblAnimais.getValueAt(linha,0).toString());
        txtEspecie.setText(tblAnimais.getValueAt(linha,1).toString());
        txtNome.setText(tblAnimais.getValueAt(linha,2).toString());
        txtIdade.setText(tblAnimais.getValueAt(linha,3).toString());
    }//GEN-LAST:event_tblAnimaisMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
         //1. Capturar a linha selecionada
         int linha = tblAnimais.getSelectedRow();
         
         if (linha == -1){
             JOptionPane.showMessageDialog(this,"Selecione um animal na tabela para alterar");
             return;
         }
         
         //2. Pegar o ID da limha selecionada
         // O ID não será alterado.
         int id = Integer.parseInt(tblAnimais.getValueAt(linha,0).toString());
         
         //3. Ler os novos valores digitados nos campos especie,nome, idade
         String especie = txtEspecie.getText().trim();
         String nome = txtNome.getText().trim();
         String IdadeTexto = txtIdade.getText().trim();
         
         //4. Validar se todos os campos estão preenchidos
         if (especie.isEmpty() || nome.isEmpty() || IdadeTexto.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Preecha todos os campos");
             return;
         }
         
         //5. Verificar se o Idade é um número inteiro válido
         int idade;
         
         try {
             idade = Integer.parseInt(IdadeTexto);
             
             if (idade < 0) {
                 JOptionPane.showMessageDialog(this, "A idade não pode ser Negativa!");
                 return;
             }
         } catch (NumberFormatException erro) {
             JOptionPane.showMessageDialog(this,"A idade deve ser um número Inteiro");
             return;
         }
         
         // 6. Pedir Confirmação antes de gravar no banco de dados
         int resposta = JOptionPane.showConfirmDialog(this, 
                         "Deseja alterar os dados do animal de ID "+id+"?" ,
                         "Confirmar Alteração",
                         JOptionPane.YES_NO_CANCEL_OPTION,
                         JOptionPane.QUESTION_MESSAGE);
         if (resposta != JOptionPane.YES_OPTION) {
             return;
         }
         
         // 7. Alteramos apenas a escpecie, nome e idade
         // O WHERE garante que somente o animal selecionado será atualziado
         
         String sql = """
                      UPDATE animal
                      SET especie = ?, nome = ?, idade = ?
                      WHERE id = ?
                      """;
         
         try (Connection conex = Conexao.conectar();
              PreparedStatement stmt = conex.prepareStatement(sql)){
             //8. Preecher os Parametros(?) na mesma ordem do SQL
             stmt.setString(1,especie);
             stmt.setString(2,nome);
             stmt.setInt(3,idade);
             stmt.setInt(4, id);
             
             //9. Executamos a alteração no Banco de Dados
             int registros = stmt.executeUpdate();
             if (registros > 0){
                 JOptionPane.showMessageDialog(this, "Animal alterado com sucesso!");
                 atualizarTabela();
                 txtId.setText("");
                 txtEspecie.setText("");
                 txtNome.setText("");
                 txtIdade.setText("");
             } else {
                 JOptionPane.showMessageDialog(this, "Animal não encontrado");
             }
         } catch (SQLException erro){
           JOptionPane.showConfirmDialog(this, "Erro ao alterar animal: " + erro.getMessage());
         }
         
    }//GEN-LAST:event_btnAlterarActionPerformed

 
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new frmConsultaAnimais().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAtualizaTabela;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbPesquisarPor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAnimais;
    private javax.swing.JTextField txtEspecie;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdade;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
