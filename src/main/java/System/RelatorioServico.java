package System;



import System.ConexaoBanco;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class RelatorioServico {
    private String idRelatorio;
    private String idSupervisor;
    private String descricao;
    private String reclamacoes;
    private Date data;

    public RelatorioServico() {}

    // Getters e Setters
    public String getIdRelatorio() { return idRelatorio; }
    public void setIdRelatorio(String idRelatorio) { this.idRelatorio = idRelatorio; }

    public String getIdSupervisor() { return idSupervisor; }
    public void setIdSupervisor(String idSupervisor) { this.idSupervisor = idSupervisor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getReclamacoes() { return reclamacoes; }
    public void setReclamacoes(String reclamacoes) { this.reclamacoes = reclamacoes; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    // Métodos do Banco de Dados

   public static void Reclamacao(String numID, Date data, funcionarios func, String reclamacao) {
    
    

    String sql = "INSERT INTO relatorioservico (Data, reclamacoes, idRelatório, idSupervisor) VALUES (?,?,?,?)";
    
    try (Connection conexao = ConexaoBanco.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setDate(1, data);
        stmt.setString(2,reclamacao); 
        stmt.setString(3, numID);
        stmt.setString(4, func.getId());
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("->Reclamação registrado com sucesso!");
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao registrar reclamação: " + e.getMessage());
    }
}

   public static void Description(String numID, Date data, funcionarios func, String descricaoCompleta) {
    
    

    String sql = "INSERT INTO relatorioservico (Data, Descricao, idRelatório, idSupervisor) VALUES (?,?,?,?)";
    
    try (Connection conexao = ConexaoBanco.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setDate(1, data);
        stmt.setString(2,descricaoCompleta); 
        stmt.setString(3, numID);
        stmt.setString(4, func.getId());
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("-> Relatório registrado com sucesso!");
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao registrar descrição: " + e.getMessage());
    }
}


    public static String consultaR(String idReclamacao ,String idSuper) {
      boolean buscarTodos = (idReclamacao == null || idReclamacao.trim().isEmpty());
    
    String sql;
    if (buscarTodos) {
        sql = "SELECT idReclamacao, reclamacoes FROM relatorioservico WHERE idSupervisor = ?";
    } else {
        sql = "SELECT idReclamacao, reclamacoes FROM relatorioservico WHERE idSupervisor = ? AND idReclamacao = ?";
    }
    
    StringBuilder resultadoFinal = new StringBuilder();
    
    try (Connection conexao = ConexaoBanco.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, idSuper);
        
        // Se não for para buscar todos, define o segundo parâmetro (idRelatório)
        if (!buscarTodos) {
            stmt.setString(2, idReclamacao);
        }
        
        try (ResultSet rs = stmt.executeQuery()) {
            boolean encontrou = false;
            
            while (rs.next()) {
                encontrou = true;
                String id = rs.getString("idReclamacao");
                String desc = rs.getString("reclamacoes");
                
                // Formata como cada linha vai aparecer no JTextArea
                resultadoFinal.append("ID: ").append(id).append("\n");
                resultadoFinal.append("reclamacoes: ").append(desc).append("\n");
                resultadoFinal.append("----------------------------------------\n");
            }

            if (!encontrou) {
                return null;
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao consultar reclamações: " + e.getMessage());
    }
    
    return resultadoFinal.toString(); 
}

    



    public static String consultaD(String idSuper, String idConsulta) {
    // Se o idConsulta estiver vazio ou nulo, busca todas as descrições do supervisor
    boolean buscarTodos = (idConsulta == null || idConsulta.trim().isEmpty());
    
    String sql;
    if (buscarTodos) {
        sql = "SELECT idRelatório, descricao FROM relatorioservico WHERE idSupervisor = ?";
    } else {
        sql = "SELECT idRelatório, descricao FROM relatorioservico WHERE idSupervisor = ? AND idRelatório = ?";
    }
    
    StringBuilder resultadoFinal = new StringBuilder();
    
    try (Connection conexao = ConexaoBanco.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, idSuper);
        
        // Se não for para buscar todos, define o segundo parâmetro (idRelatório)
        if (!buscarTodos) {
            stmt.setString(2, idConsulta);
        }
        
        try (ResultSet rs = stmt.executeQuery()) {
            boolean encontrou = false;
            
            while (rs.next()) {
                encontrou = true;
                String id = rs.getString("idRelatório");
                String desc = rs.getString("descricao");
                
                // Formata como cada linha vai aparecer no JTextArea
                resultadoFinal.append("ID: ").append(id).append("\n");
                resultadoFinal.append("Descrição: ").append(desc).append("\n");
                resultadoFinal.append("----------------------------------------\n");
            }

            if (!encontrou) {
                return null; // Retorna null se não achar nada
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao consultar descrições: " + e.getMessage());
    }
    
    return resultadoFinal.toString(); 
}


}

