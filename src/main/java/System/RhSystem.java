///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
// */
//
//package System;
//
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.Duration;
//import java.time.LocalTime;
//import java.util.Scanner;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author Admin
// */
//public class RhSystem {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args)  {
//        Scanner scc = new Scanner(System.in);
//        boolean rodando = true;
//     JOptionPane.showMessageDialog(null, "Olá, seja Bem - Vindo ao Rh System, o seu sistema de rh.\n"
//             + "No banco de dados foi inserido cadastros para cada modalidade. Bom proveito");
//     
//     
//       while (rodando) {
//         
//        System.out.println("Digite seu id(ou SAIR para encerrar a aplicação)");
//        String id=scc.nextLine();
//           
//        if (id.equalsIgnoreCase("sair")) {
//                System.out.println("Encerrando a aplicação...");
//                rodando = false;
//                System.exit(0);
//            }
//        
//        System.out.println("Digite sua senha");
//        String senha=scc.nextLine();
//       
//        
//       if (funcionarios.autenticarUsuario(id, senha)) { 
//      String idd = id.toUpperCase();
//      
//      if (idd.startsWith("CLT")) {
//            System.out.println("\n--- DIRECIONANDO PARA O PAINEL CLT ---");
//            menuCLT(id, senha,scc);
//        } 
//        else if (idd.startsWith("PJ")) {
//            System.out.println("\n--- DIRECIONANDO PARA O PAINEL PJ ---");
//            menuPJ(id, senha, scc);
//        } 
//        else if (idd.startsWith("SP")) {
//            System.out.println("\n--- DIRECIONANDO PARA O PAINEL SUPERVISOR ---");
//            menuSupervisor(id, senha, scc);
//        } 
//        else if (idd.startsWith("RH")) {
//            System.out.println("\n--- DIRECIONANDO PARA O PAINEL RH ---");
//            menuRH(id, senha, scc);
//        } 
//        else {
//            System.out.println("ID inválido! O ID deve começar com CLT, PJ, SP ou RH.");
//        }
//    } else {
//            System.out.println("\n[ERRO] Acesso negado: ID ou Senha incorretos no banco de dados.");
//        }}}
//
//
//
//
//
//
//    
//    public static void menuCLT(String id, String senha, Scanner scc) {
//    
//        int opcao = -1;
//       
//
//   
//    while (opcao != 0) {
//        System.out.println("\n--- PAINEL CLT ---");
//        System.out.println("1 - Bater Ponto Entrada");
//        System.out.println("2 - Bater Ponto Saída");
//        System.out.println("3 - Consultar Banco de Horas");
//        System.out.println("4 - Consultar folha de pagamento");
//        System.out.println("0 - Logout (Voltar ao Login)");
//        System.out.println("99 - Sair)");
//        System.out.print("Escolha uma opção: ");
//
//        opcao = scc.nextInt();
//
//        switch (opcao) {
//            case 1:
//             RegistroPonto.baterPontoEntrada(id); 
//                break;
//                case 2:
//             RegistroPonto.baterPontoSaida(id); 
//                break;
//            case 3:
//                RegistroPonto.consultarPonto(id); 
//                break;
//                case 4:
//                FolhaPagamento.folhaPagamento(id); 
//                break;
//            case 0:
//                System.out.println("Saindo do Painel CLT...");
//                scc.nextLine();
//                break;
//                case 99:
//                System.out.println("Encerrando o sistema");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Opção inválida! Tente novamente.");
//        }
//    }}
//
//    public static void menuPJ(String id, String senha,  Scanner scc) {
//         int opcao = -1;
//       
//
//    while (opcao != 0) {
//        System.out.println("\n--- PAINEL PJ ---");
//        System.out.println("1 - Consultar folha de pagamento");
//        System.out.println("2 - Marcar horas trabalhadas");
//        System.out.println("3 - Consultar banco de horas");
//        System.out.println("0 - Logout (Voltar ao Login)");
//        System.out.println("99 - Sair)");
//        System.out.print("Escolha uma opção: ");
//
//        opcao = scc.nextInt();
//
//        switch (opcao) {
//          
//                case 1:
//                horasPJ.folhaPagamentoPJ(id); 
//                break;
//                case 2:
//                horasPJ.folhaPJ(id); 
//                break;
//                case 3:
//                horasPJ.consultarPontoPJ(id);
//                break;
//            case 0:
//                System.out.println("Saindo do Painel PJ...");
//                scc.nextLine();
//                break;
//                case 99:
//                System.out.println("Encerrando o sistema");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Opção inválida! Tente novamente.");
//        }
//    }
//       
//    }
//
//    public static void menuSupervisor(String id, String senha, Scanner scc) {
//  int opcao = -1;
//       
//
//  
//    while (opcao != 0) {
//        System.out.println("\n--- PAINEL SP ---");
//        System.out.println("1 - Consultar folha de pagamento");
//        System.out.println("2 - Marcar horas trabalhadas");
//        System.out.println("3 - Consultar banco de horas");
//        System.out.println("4 - Reclamações");
//        System.out.println("5 - Descrição de serviço");
//        System.out.println("0 - Logout (Voltar ao Login)");
//         System.out.println("99 - Sair)");
//        System.out.print("Escolha uma opção: ");
//
//        opcao = scc.nextInt();
//
//        switch (opcao) {
//          
//                case 1:
//                horasPJ.folhaPagamentoPJ(id); 
//                break;
//                case 2:
//                horasPJ.folhaPJ(id); 
//                break;
//                case 3:
//                horasPJ.consultarPontoPJ(id); 
//                break;
//                 case 4:
//               RelatorioServico.Reclamacao(id);
//                break;
//                case 5:
//                RelatorioServico.Description(id, senha, id, senha); 
//                break;
//            case 0:
//                System.out.println("Saindo do Painel SP...");
//                scc.nextLine();
//                break;
//                case 99:
//                System.out.println("Encerrando o sistema");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Opção inválida! Tente novamente.");
//        }
//    }
//    }
//
//    public static void menuRH(String id, String senha, Scanner scc) {
//        
//         int opcao = -1;
//       
//
//   
//    while (opcao != 0) {
//        System.out.println("\n--- PAINEL RH ---");
//        System.out.println("1 - Cadastrar Funcionário");
//        System.out.println("2 - Cadastrar Atestado");
//        System.out.println("3 - Consultar Reclamações");
//        System.out.println("4 - Consultar Descrições");
//        System.out.println("0 - Logout (Voltar ao Login)");
//         System.out.println("99 - Sair)");
//        System.out.print("Escolha uma opção: ");
//
//        opcao = scc.nextInt();
//
//        switch (opcao) {
//          
//                case 1:
//               funcionarios.Cadastrar(id);
//                break;
//                 case 2:
//              Atestados.atestados(id); 
//                break;
//                case 3:
//              RelatorioServico.consultaR(senha, id);
//                break;
//                case 4:
//              RelatorioServico.consultaD(senha, id);
//                break;
//            case 0:
//                System.out.println("Saindo do Painel RH...");
//                scc.nextLine();
//                break;
//                case 99:
//                System.out.println("Encerrando o sistema");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Opção inválida! Tente novamente.");
//        }
//    }
//    }
//
//    
//
//
//}
//
//
//
//
