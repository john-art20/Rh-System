package System;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Atestado {
    private int id;
    private String idFunc;
    private String nome;
    private Date dataInicio;
     private Date dataTermino;
      private String CRM;
       private String nomeMedico;

    public String getIdFunc() {return idFunc;}
    public void setIdFunc(String idFunc) {this.idFunc = idFunc;}
    
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public Date getDataInicio() {return dataInicio;}
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}
    
    public Date getDataTermino() {return dataTermino;}
    public void setDataTermino(Date dataTermino) {this.dataTermino = dataTermino;}
    
    public String getCRM() {return CRM;}
    public void setCRM(String CRM) {this.CRM = CRM;}
    
    public String getNomeMedico() {return nomeMedico; }
    public void setNomeMedico(String nomeMedico) {this.nomeMedico = nomeMedico;}
    


    // Método com letra minúscula para realizar o cadastro
    public static void CadAtestado(java.sql.Date inicio, java.sql.Date termino, String CRM, String medico, funcionarios func) {
        
        String sql = "INSERT INTO atestados (idFunc, nome, dataInicio, dataTermino, nomeMedico, CRM) VALUES (?,?,?,?,?,?)";

        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, func.getId());
            stmt.setString(2, func.getNome());
            stmt.setDate(3, inicio);
            stmt.setDate(4, termino);
            stmt.setString(5, medico);
            stmt.setString(6, CRM);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\n-> Atestado cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }
}