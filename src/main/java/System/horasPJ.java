package System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;


public class horasPJ {
    
    // 1. ATRIBUTOS
    private int idPonto;
    private String idFunc;
    private String data;
    private String horaEntrada;
    private String horaSaida;
    private double trabalhoHoras;
    
      private String id;
    private String nome;
    private int totalDias;
    private double valorHora;
    private long horasExatas;
    private double horasExtras;
    private long minutosRestantes;
    private double totalBruto;
    private double totalLimpo;
    private double ValorExtras;

    // Construtor Vazio
    public horasPJ() {}

    // 2. GETTERS E SETTERS
    public int getIdPonto() { return idPonto; }
    public void setIdPonto(int idPonto) { this.idPonto = idPonto; }

    public String getIdFunc() { return idFunc; }
    public void setIdFunc(String idFunc) { this.idFunc = idFunc; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }

    public String getHoraSaida() { return horaSaida; }
    public void setHoraSaida(String horaSaida) { this.horaSaida = horaSaida; }

    public double getTrabalhoHoras() { return trabalhoHoras; }
    public void setTrabalhoHoras(double trabalhoHoras) { this.trabalhoHoras = trabalhoHoras; }

    
    
    
    public String getId() {return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public int getTotalDias() {return totalDias;}
    public void setTotalDias(int totalDias) { this.totalDias = totalDias;}

    public double getValorHora() {return valorHora;}
    public void setValorHora(double valorHora) { this.valorHora = valorHora;}

    public long getHorasExatas() { return horasExatas;}
    public void setHorasExatas(long horasExatas) { this.horasExatas = horasExatas; }

    public double getHorasExtras() { return horasExtras; }
    public void setHorasExtras(double horasExtras) { this.horasExtras = horasExtras; }

    public long getMinutosRestantes() { return minutosRestantes; }
    public void setMinutosRestantes(long minutosRestantes) { this.minutosRestantes = minutosRestantes; }

    public double getTotalBruto() { return totalBruto;}
    public void setTotalBruto(double totalBruto) {this.totalBruto = totalBruto; }

    public double getTotalLimpo() {return totalLimpo;}
    public void setTotalLimpo(double totalLimpo) {this.totalLimpo = totalLimpo;}

    public double getValorExtras() { return ValorExtras;}
    public void setValorExtras(double ValorExtras) {this.ValorExtras = ValorExtras;}
    
    

    // 3. MÉTODOS DE BANCO DE DADOS (Injetados direto na classe)

    // Lança as horas do PJ no banco
   public static void folhaPJ(funcionarios func, java.sql.Time Entradasql, java.sql.Time Saidasql, java.sql.Date datasql) {

    LocalTime horaEnt = Entradasql.toLocalTime();
    LocalTime horaSai = Saidasql.toLocalTime();

    Duration duracao = Duration.between(horaEnt, horaSai);

    // se a saída for antes da entrada, assume que passou da meia-noite
    if (duracao.isNegative()) {
        duracao = duracao.plusHours(24);
    }

    double qtdhoras = duracao.toMinutes() / 60.0;   // <- esta linha estava faltando
    long horasExatas = duracao.toHours();
    long minutosSobrando = duracao.toMinutesPart();

    System.out.println("Tempo trabalhado: " + horasExatas + "h e " + minutosSobrando + "min");

    String sql = "INSERT INTO horaspj (idFunc, Data, HoraEntrada, HoraSaída, TrabalhoHoras) VALUES (?,?,?,?,?)";

    try (Connection conn = ConexaoBanco.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, func.getId());
        stmt.setDate(2, datasql);
        stmt.setTime(3, Entradasql);
        stmt.setTime(4, Saidasql);
        stmt.setDouble(5, qtdhoras);

        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("\n-> Horas salvas com sucesso para o PJ!");
        }
    } catch (SQLException e) {
        System.out.println("Erro ao registrar horas: " + e.getMessage());
    }
}

    // Calcula a folha de pagamento do PJ (Derivação)
    public static horasPJ calcularFolhaPJ(funcionarios func) {

   String sql = "SELECT c.Nome, c.ValorHora, " +
             "COUNT(DISTINCT h.Data) AS totalDias, " +
             "SUM(h.TrabalhoHoras) AS totalHoras " +
             "FROM funcionarios c " +
             "INNER JOIN horaspj h ON c.id = h.idFunc " +
             "WHERE c.id = ? AND h.TrabalhoHoras > 0 " +
             "GROUP BY c.id, c.Nome, c.ValorHora";
                  

    try (Connection conn = ConexaoBanco.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, func.getId());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            horasPJ folha = new horasPJ();

            folha.setId(func.getId());
            folha.setNome(func.getNome());
            folha.setTotalDias(rs.getInt("totalDias"));

            double valorHora = rs.getDouble("ValorHora");
            folha.setValorHora(valorHora);

            // já vem em horas decimais
            double horasDecimais = rs.getDouble("totalHoras");
            long horas = (long) horasDecimais;
            long minutos = Math.round((horasDecimais - horas) * 60);
            if (minutos == 60) {   // evita "2h 60min"
                horas++;
                minutos = 0;
            }
            folha.setHorasExatas(horas);
            folha.setMinutosRestantes(minutos);

            double limiteHoras = 187.0;
            double horasNormais = Math.min(horasDecimais, limiteHoras);
            double horasExtras = Math.max(0, horasDecimais - limiteHoras);

            double adicionalExtra = 1.5;

            double valorNormal = horasNormais * valorHora;
            double valorExtra = horasExtras * valorHora * adicionalExtra;
            double totalBruto = valorNormal + valorExtra;

            folha.setHorasExtras(horasExtras);
            folha.setValorExtras(valorExtra);
            folha.setTotalBruto(totalBruto);
            folha.setTotalLimpo(totalBruto);

            return folha;
        }
    } catch (SQLException e) {
        System.out.println("Erro na consulta: " + e.getMessage());
    }
    return null;
}

//   if (rs.next()) {
//                String nome = rs.getString("Nome");
//                int totalDias = rs.getInt("totalDias");
//                double totalHoras = rs.getDouble("totalHoras");
//                double valorHora = rs.getDouble("ValorHora");
//                double valorTotal = rs.getDouble("valorTotal");
   
    
    
        public static List<String[]> consultarPontoPJ(funcionarios func) {
    String sql = "SELECT Data, HoraEntrada, HoraSaída FROM horasPJ WHERE idFunc = ?";
    List<String[]> listaPontos = new ArrayList<>();
    
    try (Connection conn = ConexaoBanco.getConexao();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, func.getId());
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               
                
              
                String data = rs.getString("Data");
                String entrada = rs.getString("HoraEntrada");
                String saida = rs.getString("HoraSaída");
                
                // Adiciona os dados em um array e guarda na lista
                listaPontos.add(new String[]{data, entrada, saida});
            }
        }   
    } catch (SQLException e) {
        System.out.println("Erro ao consultar ponto: " + e.getMessage());
    }
    
    return listaPontos; // Retorna a lista pronta e segura
    
}
}

