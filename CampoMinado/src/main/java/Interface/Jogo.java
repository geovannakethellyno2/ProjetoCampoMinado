/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author GeovannaOliviera
 */
public class Jogo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jogo.class.getName());

    /**
     * Creates new form Jogo
     */
   //LOCAL ONDE CRIA AS VARIAVEIS
    
    //JButton precisa da importacao da sua biblioteca
    //btnCampos e é o nome da variavel - (você que escolhe)
    //matriz com - 10 linhas e 10 colunas
    
JButton [][] btnCampos= new JButton[10][10];
 
// MATRIZ PARA GUARDAR AS BOMBAS - TRUE P/ BOMBA, FALSE P/ NUMERO
boolean [][] bombas  = new boolean [10][10];

//MATRIZ PARA OS CAMPOS QUE FOREM ABERTOS
 boolean [][] abertos = new boolean [10] [10];
 
 int quantidadeBombas = 15;
 
 boolean jogoEncerrado= false;
 
 int quantidadeDeCasasAbertas=0;
    int segundosPassados = 0;
    Timer cronometro;
 
            
// construtor da classe/tela - sem ele a tela nao e nada
    public Jogo() {
        initComponents();
        //definir o tamanho do painel
        
        painelCampo.setPreferredSize(new Dimension (900,700));
        criarTabuleiro();
      }
      //CRIAR AS NOSSAS FUNÇÕES/METADOS
    
    public void criarTabuleiro(){
    // definir que o painel será dividido em 10 linhas e 10 clunas
   // com altura 2px e largura 2px
    painelCampo.setLayout(new GridLayout(10,10,2,2));
    
    for (int coluna=0;coluna<=9;coluna++){
     for(int linha=0;linha<=9;linha++){
    //VARIAVL BOTÃO PARA GUARDAR OS DADOS PROVISORIOS
    JButton botao= new JButton();
    botao.setFont(new Font("Arial", Font.BOLD,16));
    botao.setBackground(new Color(255,192,230));
    botao.setForeground(Color.WHITE);
    
    //remover marcas do botao q vem por padrao
    botao.setFocusPainted(false);
    botao.setEnabled(false);
    final int linhaSelecionada= linha;
    final int colunaSelecionada= coluna;

   //adicionnaf o evento de clique para abrir as casas
    botao.addActionListener((ActionEvent Evento)->{
            abrirBotao(linhaSelecionada,colunaSelecionada);
    });     
     //adicionar botão dentro da matriz
     btnCampos[linha][coluna]=botao;
     //adicionar dentro do painel
     painelCampo.add(botao);
    
     
     } //FIM Da segunda for     
    }//fim da primeira for
    
    
    }//FIM DO METADO CriarTabuleiro
    
    public void AdicionarBombas(){
    // criar variavel random para gerar valores aleatorips
    Random sorteador= new Random();
    int bombasAdicionadas= 0;
    
    while(bombasAdicionadas <quantidadeBombas){
     //SORTEAR O NUMERO DA LINHA E COLUNA QUE VAI FICAR A BOMBA   
    int linhas= sorteador.nextInt(10);
    int coluna= sorteador.nextInt(10);
    //VERIFICAR SE NÃO EXISTE BOMBA ADICIONADA NO LOCAL
    if(!bombas[linhas][coluna]){
    //adicionar bomba na matriz
    bombas[linhas][coluna]=true;
    bombasAdicionadas++;
    }
    
     }
    }
       
    public void IniciarJogo(){
        LimparJogo();
        //chamar o metodo adicionarBombas
        AdicionarBombas();
        IniciarCronometro();
        //depois precisamos iniciar os botoes do jogo
        for(int colunas=0;colunas<=9;colunas++){
            for(int linhas=0;linhas<=9;linhas++){
                JButton botao = btnCampos[linhas][colunas];
                //deixar os botoes visiveis e clicaveis
                botao.setEnabled(true);
            }//fim do 2° for
        }//fim do 1° for
        btnIniciar.setText("REINICIAR");
    }//fim do iniciar jogo
    
    
    public void abrirBotao(int linha, int coluna){
        // verificar se o jogo foi finalizado
        if(jogoEncerrado) return;
        
        //verificar se o botao ja foi aberto
        if(abertos[linha][coluna]) return;
      
        /*se o jogo ainda estiver rodando e o botão ainda não tiver
        sido aberto - então vamos abrir o botão*/
        abertos[linha][coluna]=true;
        quantidadeDeCasasAbertas++;
          
        // acessar o que tem dentro do botão
        JButton botao = btnCampos[linha][coluna];
        //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
        if(bombas[linha][coluna]){
            //variavel que recebe nossa imagem
           ImageIcon imgBomba = new ImageIcon( 
                   getClass().getResource("/assets/bomb.png"));
           //colocar a imagem no botao
           botao.setIcon(imgBomba);
           FinalizarJogo(false);
           return;
        }else{
            ImageIcon imgBandeira = new ImageIcon(
                getClass().getResource("/assets/finish.png"));
            botao.setIcon(imgBandeira);
            return;
        }
        
    }// fim do metodo abrirBotao
    
    
    // este metodo informa quando a pessoa perder ou ganhar o jogo
    public void FinalizarJogo(boolean venceu){
       mostrarBombas();
        //vamos informar que o jogo acabou
        jogoEncerrado=true;
        cronometro.stop();
        
        //verificar se a pessa venceu ou não
        if(venceu){
            JOptionPane.showMessageDialog(
                    this,"Parabéns você venceu!");
            LimparJogo();
        }else{
            JOptionPane.showMessageDialog(
                    this,"Ops, você perdeu o jogo!");
            LimparJogo();
        }
       }//fim do FinalizarJogo
    
    
    public void VerificarVitoria(){
        // armazenar a quantidade de casas com bandeiras
        int casasSemBomba= 100 - quantidadeBombas;
        //se a pessoa abriu todas as bandeiras e não abriu nenhuma bomba
        // então ela venceu o jogo, e o finalizarJogo imprime a mensagem
        if(quantidadeDeCasasAbertas == casasSemBomba){
            FinalizarJogo(true);
        }
  
        
    }
    
    
    public void IniciarCronometro(){
        // zerar o cronometro caso tenha tido um jogo anterior
        if(cronometro !=null){
            cronometro.stop();
        }
        // reseta o cronometro
        segundosPassados = 0;
        tfTempo.setText("00:00");
        
        // converter o tempo em minutos e segundos
        // o cronometro conta de 1 em 1 segundo, e vai convertendo
        cronometro = new Timer(1000, Evento->{
            segundosPassados++;
            int minutos = segundosPassados/60;
            int horas = minutos/60;
            int segundo = segundosPassados%60;
            //mostrar o tempo dentro da váriavel
            tfTempo.setText(
            String.format("%02d:%02d:%02d",horas,minutos,segundo));
                       
        });
        cronometro.start();
        
        
    }
    
    
    public void LimparJogo(){
         quantidadeDeCasasAbertas=0;
         jogoEncerrado=false;
        
         
        for(int coluna=0;coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                bombas[linha][coluna]=false;
                abertos[linha][coluna]=false;
               
                
                //limpeza dos botões
                JButton botao = btnCampos[linha][coluna];
                botao.setIcon(null);
                
            }//fim do 2° for
        }//fim do 1° for
         AdicionarBombas(); 
         IniciarCronometro();
        
    }//fim do LimparJogo
    
    
    public void mostrarBombas(){
       for(int coluna=0;coluna<=9;coluna++){
           for(int linha=0;linha<=9;linha++){
                JButton botao = btnCampos[linha][coluna];
                //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
                if(bombas[linha][coluna]){
                    //variavel que recebe nossa imagem
                   ImageIcon imgBomba = new ImageIcon( 
                           getClass().getResource("/assets/bomb.png"));
                   //colocar a imagem no botao
                   botao.setIcon(imgBomba);
                   
                }//fim do if
           }//fim do 2° for
       }// fim do 1° for      
    }// fim do mostrarBombas
    

            
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        titulo = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        tfTempo = new javax.swing.JTextField();
        painelCampo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        titulo.setBackground(new java.awt.Color(153, 0, 51));
        titulo.setFont(new java.awt.Font("Snap ITC", 1, 48)); // NOI18N
        titulo.setText("Campo Minado");

        btnIniciar.setBackground(new java.awt.Color(204, 0, 0));
        btnIniciar.setFont(new java.awt.Font("Snap ITC", 1, 24)); // NOI18N
        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(this::btnIniciarActionPerformed);

        tfTempo.setEditable(false);
        tfTempo.setBackground(new java.awt.Color(255, 102, 102));
        tfTempo.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tfTempo.setText("00:00");

        painelCampo.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout painelCampoLayout = new javax.swing.GroupLayout(painelCampo);
        painelCampo.setLayout(painelCampoLayout);
        painelCampoLayout.setHorizontalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painelCampoLayout.setVerticalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCampo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(painelCampo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
      IniciarJogo();
      
    }//GEN-LAST:event_btnIniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Jogo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel painelCampo;
    private javax.swing.JTextField tfTempo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
