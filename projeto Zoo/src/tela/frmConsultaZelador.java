package tela;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eder Silva
 */
public class frmConsultaZelador extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmConsultaZelador.class.getName());


    private void atualizarTabela(){
        DefaultTableModel modelo = (DefaultTableModel) tblZeladores.getModel();
        
        modelo.setRowCount(0);
        
        String sql = "SELECT * FROM zelador "
                     + "ORDER BY id";
        
        try (Connection conex = Conexao.conectar();
             PreparedStatement stmt = conex.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery() ){
            
            while (resultado.next()){
                modelo.addRow(new Object[] {
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("especialidade"),
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

    
    public frmConsultaZelador() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Zoo 261T - Consulta de Zeladores");
        tblZeladores.setModel(new DefaultTableModel(new Object[][]{},
                            new String[] {"ID","Nome","Especialidade","Idade"}){
                    @Override
                    public boolean isCellEditable(int linha, int coluna){
                        return false;
                    }
                    });                   
                
        tblZeladores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        atualizarTabela();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbPesquisarPor = new javax.swing.JComboBox<>();
        txtPesquisar = new javax.swing.JTextField();
        btnLocalizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblZeladores = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Zeladores");

        cmbPesquisarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Especialidade" }));

        btnLocalizar.setBackground(new java.awt.Color(102, 255, 102));
        btnLocalizar.setText("Localizar");
        btnLocalizar.addActionListener(this::btnLocalizarActionPerformed);

        tblZeladores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblZeladores);

        jButton2.setBackground(new java.awt.Color(255, 255, 102));
        jButton2.setText("Atualizar Tabela");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        btnExcluir.setBackground(new java.awt.Color(255, 102, 102));
        btnExcluir.setText("Excluir");

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ed Soluções\\Downloads\\Alegria no Zoológico (2).png")); // NOI18N

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147)
                .addComponent(btnSair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(65, 65, 65))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbPesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLocalizar))
                        .addGap(73, 73, 73))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnExcluir)
                    .addComponent(btnSair))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        String pesquisa = txtPesquisar.getText().trim();
        
        if (pesquisa.isEmpty()){
            JOptionPane.showMessageDialog(
                        this,
                        "Digite o nome ou parte do nome do Zelador.",
                        "Pesquisa Vazia",
                        JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tblZeladores.getModel();
        
        modelo.setRowCount(0);
        
        String tipo_pesquisa = cmbPesquisarPor.getSelectedItem().toString();
        
        String sql ="";
        
        if (tipo_pesquisa == "Nome") {
        
             sql = "SELECT * FROM zelador "
                   + "WHERE nome ILIKE ? "
                   + "ORDER BY nome";
                }
        
        else {
        
             sql = "SELECT * FROM zelador "
                   + "WHERE especialidade ILIKE ? "
                   + "ORDER BY nome";
        
        }
        
        try (Connection conex = Conexao.conectar();
                
              PreparedStatement stmt = conex.prepareStatement(sql)){
            stmt.setString(1,"%" + pesquisa + "%");
            
            try (ResultSet resultado = stmt.executeQuery()){
                while (resultado.next()){
                    modelo.addRow(new Object [] {
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("especialidade"),
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      atualizarTabela();
      txtPesquisar.setText("");
      txtPesquisar.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
     
        java.awt.EventQueue.invokeLater(() -> new frmConsultaZelador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbPesquisarPor;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblZeladores;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
