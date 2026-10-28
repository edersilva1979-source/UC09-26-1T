
package tela;
import java.sql.Connection;
import conexao.Conexao;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 *
 * @author Ed Soluções
 */
public class frmAnimal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmAnimal.class.getName());

 
    public frmAnimal() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Cadastro de Animais");
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEspecie = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        spnIdade = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        btnConsultaAnimal = new javax.swing.JButton();
        btnConsultaZelador = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Zoológico da turma 26 1T");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(15, 15, 15))
        );

        txtEspecie.setToolTipText("Digite aqui a Especie do Animal");
        txtEspecie.setBorder(javax.swing.BorderFactory.createTitledBorder("Especie"));

        txtNome.setToolTipText("Digite aqui o Nome do Animal");
        txtNome.setBorder(javax.swing.BorderFactory.createTitledBorder("Nome"));

        btnSalvar.setBackground(new java.awt.Color(153, 255, 153));
        btnSalvar.setText("Salvar");
        btnSalvar.setToolTipText("Clique para salvar no Banco de Dados");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(this::btnSalvarActionPerformed);

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ed Soluções\\Downloads\\Zoológico da Turma 26 1T (1).png")); // NOI18N

        spnIdade.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spnIdade.setBorder(javax.swing.BorderFactory.createTitledBorder("Idade"));

        jButton1.setBackground(new java.awt.Color(153, 255, 153));
        jButton1.setText("Cadastro Zelador");

        btnConsultaAnimal.setBackground(new java.awt.Color(102, 204, 255));
        btnConsultaAnimal.setText("Consulta Animal");
        btnConsultaAnimal.addActionListener(this::btnConsultaAnimalActionPerformed);

        btnConsultaZelador.setBackground(new java.awt.Color(102, 153, 255));
        btnConsultaZelador.setText("Consulta Zelador");
        btnConsultaZelador.addActionListener(this::btnConsultaZeladorActionPerformed);

        jMenu1.setText("Cadastro");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Consulta");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Serviço");

        jMenuItem1.setText("Serviços");
        jMenuItem1.addActionListener(this::jMenuItem1ActionPerformed);
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(btnSalvar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(spnIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(200, 200, 200))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnConsultaZelador)
                                .addGap(18, 18, 18)
                                .addComponent(btnConsultaAnimal)
                                .addGap(74, 74, 74)))
                        .addComponent(jLabel2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtEspecie)
                        .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(txtEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spnIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsultaAnimal)
                            .addComponent(btnConsultaZelador))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String sqlInsert = """
                           INSERT INTO animal
                           (especie,nome, idade)
                           VALUES (?,?,?)
                           """;
        try{
            Connection conex = Conexao.conectar();
            PreparedStatement stmt = conex.prepareStatement(sqlInsert);
            
            String especie = txtEspecie.getText();
            String nome = txtNome.getText();
            int idade = Integer.parseInt(spnIdade.getValue().toString());
            
            stmt.setString(1, especie);
            stmt.setString(2, nome);
            stmt.setInt(3, idade);
            
            int linhas = stmt.executeUpdate();
            if (linhas > 0){
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                txtEspecie.setText("");
                txtNome.setText("");
                spnIdade.setValue(0);
                txtEspecie.requestFocus();
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(this,"Não foi possível Cadastrar! - Erro: "+ erro);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnConsultaAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaAnimalActionPerformed
        frmConsultaAnimais tela3 = new frmConsultaAnimais();
        tela3.setVisible(true);
    }//GEN-LAST:event_btnConsultaAnimalActionPerformed

    private void btnConsultaZeladorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaZeladorActionPerformed
        frmConsultaZelador tela3 = new frmConsultaZelador();
        tela3.setVisible(true);
    }//GEN-LAST:event_btnConsultaZeladorActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
         frmServicos tela3 = new frmServicos();
        tela3.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

  
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(() -> new frmAnimal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultaAnimal;
    private javax.swing.JButton btnConsultaZelador;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner spnIdade;
    private javax.swing.JTextField txtEspecie;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
