package System;



import System.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class funcionarios {
    
    private String id;
    private String nome;
    private String senha;
    private String tipoPerfil;
    private double valorHora;

    
    public funcionarios() {}

    public funcionarios(String id, String nome, String senha, String tipoPerfil, double valorHora) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.tipoPerfil = tipoPerfil;
        this.valorHora = valorHora;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipoPerfil() { return tipoPerfil; }
    public void setTipoPerfil(String tipoPerfil) { this.tipoPerfil = tipoPerfil; }

    public double getValorHora() { return valorHora; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }

    // Métodos de Banco de Dados referentes a Funcionário

    public static funcionarios autenticarUsuario(funcionarios func) {
        String sql = "SELECT * FROM funcionarios WHERE id = ? AND Senha = ?";
        funcionarios Funcionario = null;
       
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, func.getId());
            stmt.setString(2, func.getSenha());
            ResultSet rs = stmt.executeQuery();
            
            
             while (rs.next()) {
              Funcionario = new funcionarios();
               Funcionario.setId(rs.getString("id"));
               Funcionario.setNome(rs.getString("Nome"));
              Funcionario.setSenha(rs.getString("Senha"));
            Funcionario.setTipoPerfil(rs.getString("Tipo_Perfil"));
             Funcionario.setValorHora(rs.getDouble("ValorHora"));
                                  }
            
            
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco: " + e.getMessage());
        }
        return Funcionario;
    }

   public static void Cadastrar(funcionarios func) {
    
     
    String sql = "INSERT INTO funcionarios "
    + "(id, Nome, Senha, Tipo_perfil, ValorHora) VALUES (?,?,?,?,?)";
             
    
             try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
         
     
        stmt.setString(1, func.getId());
        stmt.setString(2, func.getNome());
        stmt.setString(3, func.getSenha());
        stmt.setString(4, func.getTipoPerfil());
        stmt.setDouble(5, func.getValorHora());

       
        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("\n-> Funcionário cadastrado com sucesso!");
            
          
        }
    } catch (SQLException e) {
        System.out.println("Erro no cadastro: " + e.getMessage());
    }
}}

