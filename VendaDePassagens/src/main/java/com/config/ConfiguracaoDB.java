package com.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import javax.sql.DataSource;

// Esta classe vai gerenciar nossa conexão centralizada
public class ConfiguracaoDB {

    // --- Copie as constantes da sua classe ConexaoDB antiga ---
    private static final String DB_HOST = "127.0.0.1";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "companhia_aerea";
    private static final String DB_USER = "root";//Pedro vc vai precisar mudar isso aqui pro seu nome no workbench
    private static final String DB_PASS = "@cassioLPS227";//aqui tambem
    private static final String JDBC_URL = String.format("jdbc:mariadb://%s:%s/%s",
            DB_HOST, DB_PORT, DB_NAME);

    // --- Propriedades do Spring ---

    private static DataSource dataSource;
    private static JdbcTemplate jdbcTemplate;

    // Bloco "static" é executado uma vez quando a classe é carregada
    static {
        try {
            // 1. Criar o DataSource
            // SimpleDriverDataSource é a implementação mais básica do Spring.
            // (Em produção, você usaria um pool como HikariCP)
            SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setDriverClass(org.mariadb.jdbc.Driver.class); // O driver
            ds.setUrl(JDBC_URL);
            ds.setUsername(DB_USER);
            ds.setPassword(DB_PASS);

            dataSource = ds;

            // 2. Criar o JdbcTemplate usando o DataSource
            jdbcTemplate = new JdbcTemplate(dataSource);

            System.out.println("DataSource e JdbcTemplate configurados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro crítico ao configurar o DataSource: " + e.getMessage());
            // Se isso falhar, a aplicação inteira não pode funcionar com o DB.
            throw new RuntimeException("Falha na inicialização do banco de dados", e);
        }
    }

    // Méthodo "getter" para que o resto do app possa acessar o template
    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    private ConfiguracaoDB() {}
}