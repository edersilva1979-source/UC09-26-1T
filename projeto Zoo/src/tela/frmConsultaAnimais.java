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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLocalizar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAtualizaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLocalizar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAtualizaTabela)
                            .addComponent(btnExcluir)
                            .addComponent(btnSair)))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
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

 
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new frmConsultaAnimais().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizaTabela;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbPesquisarPor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAnimais;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
