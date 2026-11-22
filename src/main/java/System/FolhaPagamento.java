package System;


import System.ConexaoBanco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class FolhaPagamento {
    
  
    private String idFolha;
    private String idFunc;
    private String mesAno;
    private double Valor;
    private String Comprovante;
    private double HoraSalario;

    private String id;
    private String nome;
    private int totalDias;
    private double valorHora;
    private long horasExatas;
    private double horasExtras;
    private long minutosRestantes;
    private double totalBruto;
    private double inss;
    private double fgts;
    private double totalLimpo;
    private double ValorExtras;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getTotalDias() { return totalDias; }
    public void setTotalDias(int totalDias) { this.totalDias = totalDias; }

    public double getValorHora() { return valorHora; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }

    public long getHorasExatas() { return horasExatas; }
    public void setHorasExatas(long horasExatas) { this.horasExatas = horasExatas; }

    public long getMinutosRestantes() { return minutosRestantes; }
    public void setMinutosRestantes(long minutosRestantes) { this.minutosRestantes = minutosRestantes; }

    public double getTotalBruto() { return totalBruto; }
    public void setTotalBruto(double totalBruto) { this.totalBruto = totalBruto; }

    public double getInss() { return inss; }
    public void setInss(double inss) { this.inss = inss; }

    public double getFgts() { return fgts; }
    public void setFgts(double fgts) { this.fgts = fgts; }

    public double getTotalLimpo() { return totalLimpo; }
    public void setTotalLimpo(double totalLimpo) { this.totalLimpo = totalLimpo; }
    
    public double getHorasExtras() {return horasExtras;}
    public void setHorasExtras(double horasExtras) {this.horasExtras = horasExtras;}

    public double getValorExtras() {return ValorExtras;}
    public void setValorExtras(double ValorExtra) {this.ValorExtras = ValorExtra;}
    
    
    
   public static FolhaPagamento calcularFolha(funcionarios func) {
     String sql = "SELECT c.Nome, c.ValorHora, COUNT(DISTINCT r.Data) AS totalDias, " +
             "SUM(CASE " +
             "  WHEN r.HoraSaída >= r.HoraEntrada " +
             "    THEN TIME_TO_SEC(TIMEDIFF(r.HoraSaída, r.HoraEntrada)) " +
             "  ELSE TIME_TO_SEC(TIMEDIFF(r.HoraSaída, r.HoraEntrada)) + 86400 " +
             "END) AS totalSegundos " +
             "FROM funcionarios c " +
             "INNER JOIN RegistroPonto r ON c.id = r.idFunc " +
             "WHERE c.id = ? AND r.HoraEntrada IS NOT NULL AND r.HoraSaída IS NOT NULL " +
             "GROUP BY c.id, c.Nome, c.ValorHora";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, func.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FolhaPagamento folha = new FolhaPagamento();
                
                folha.setId(func.getId());
                folha.setNome(func.getNome());
                folha.setTotalDias(rs.getInt("totalDias"));
                
                double valorHora = rs.getDouble("ValorHora");
                folha.setValorHora(func.getValorHora());

                long totalSegundos = rs.getLong("totalSegundos");
                folha.setHorasExatas(totalSegundos / 3600);
                folha.setMinutosRestantes((totalSegundos % 3600) / 60);
                
                

                double horasDecimais = totalSegundos / 3600.0;
                double limiteHoras = 220.0;   // jornada mensal normal
                double horasNormais = Math.min(horasDecimais, limiteHoras);
                double horasExtras = Math.max(0, horasDecimais - limiteHoras);

                double adicionalExtra = 1.5;  // 50% a mais

                double valorNormal = horasNormais * valorHora;
                double valorExtra = horasExtras * valorHora * adicionalExtra;
                double totalBruto = valorNormal + valorExtra;

               folha.setHorasExtras(horasExtras);
               folha.setValorExtras(valorExtra);
                folha.setTotalBruto(totalBruto);
                
                // Exemplo de cálculos de impostos
                folha.setInss(totalBruto * 0.08);
                folha.setFgts(totalBruto * 0.08);
                folha.setTotalLimpo(totalBruto - (totalBruto * 0.08));

                return folha; // Devolve o objeto pronto com todos os dados calculados
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
        return null;
    }
}
