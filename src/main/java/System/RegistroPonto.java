package System;

import System.ConexaoBanco;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RegistroPonto {
    private String idPonto;
    private Date data;
    private Time horaEntrada;
    private Time horaSaida;
    private String idFunc;

    public RegistroPonto() {}

    // Getters e Setters
    public String getIdPonto() { return idPonto; }
    public void setIdPonto(String idPonto) { this.idPonto = idPonto; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Time getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(Time horaEntrada) { this.horaEntrada = horaEntrada; }

    public Time getHoraSaida() { return horaSaida; }
    public void setHoraSaida(Time horaSaida) { this.horaSaida = horaSaida; }

    public String getIdFunc() { return idFunc; }
    public void setIdFunc(String idFunc) { this.idFunc = idFunc; }

    // Métodos Operacionais

    public static void baterPontoEntrada(funcionarios func) {
        String sql = "INSERT INTO registroponto (idPonto, Data, HoraEntrada, idFunc) VALUES (?, CURDATE(), CURTIME(), ?)";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String idPonto = "PONTO_" + System.currentTimeMillis();
            stmt.setString(1, idPonto);
            stmt.setString(2, func.getId());

            if (stmt.executeUpdate() > 0) {
                System.out.println("-> Ponto batido com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao registrar ponto: " + e.getMessage());
        }
    }

    public static void baterPontoSaida(funcionarios func) {
        String sql = "UPDATE registroponto SET HoraSaída = CURTIME() WHERE idFunc = ? AND Data = CURDATE() AND HoraSaída IS NULL";
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, func.getId());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("-> Ponto de SAÍDA registrado com sucesso!");
            } else {
                System.out.println("-> Atenção: Você não bateu a ENTRADA hoje ou a saída já foi registrada!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao registrar saída: " + e.getMessage());
        }
    }

    public static List<String[]> consultarPonto(funcionarios func) {
    String sql = "SELECT Data, HoraEntrada, HoraSaída FROM registroponto WHERE idFunc = ?";
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