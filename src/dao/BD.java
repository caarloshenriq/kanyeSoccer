package dao;

import java.sql.Connection;

import controller.Conexao;

public class BD {
    public static Connection connection = null;

    public static void main(String[] args) {
        try {
            Conexao.getInstancia().abrirConexao();
            System.out.println("Base de dados criada com sucesso");
            Conexao.getInstancia().fecharConexao();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
