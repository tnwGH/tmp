package com.scot.quiz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

// only demo
public class QuizH2PrepareModule extends AbstractModule {

	@Override
    protected void configure() {
		startH2();

        install(new PrivateModule() {
        	@Override
            protected void configure() {
                Names.bindProperties(binder(), getProperties());
                bind(DataSource.class).toProvider(H2DataSourceProvider.class);
                bind(H2PrepareData.class).asEagerSingleton();
        	}
        });
	}

	private void startH2() {
		// TODO graceful stop
        try {
        	Server server = 
        			Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
        			.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    private Properties getProperties() {
    	Properties properties = new Properties();
    	properties.setProperty("url", "jdbc:h2:~/test");
    	properties.setProperty("username", "sa");
    	properties.setProperty("password", "");
        return properties;
    }

    static class H2DataSourceProvider implements Provider<DataSource> {
        private final String url;
        private final String username;
        private final String password;

        @Inject
        public H2DataSourceProvider(@Named("url") final String url,
                                    @Named("username") final String username,
                                    @Named("password") final String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public DataSource get() {
            final JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            return dataSource;
        }
    }

    static class H2PrepareData {
        private final DataSource dataSource;

        @Inject
        public H2PrepareData(final DataSource dataSource) {
            this.dataSource = dataSource;

            Connection cn = null;
            Statement stmt = null;
            try {
            	cn = dataSource.getConnection();
            	cn.setAutoCommit(false);
                stmt = cn.createStatement();
                stmt.execute("DROP TABLE IF EXISTS QUIZ;");
                stmt.execute("CREATE TABLE QUIZ (id int, type int, text varchar(255))");
                stmt.execute("INSERT INTO QUIZ (id, type, text) VALUES (1, 11, 'Question #1')");
                stmt.execute("INSERT INTO QUIZ (id, type, text) VALUES (2, 12, 'Question #2')");
                stmt.execute("INSERT INTO QUIZ (id, type, text) VALUES (3, 13, 'Question #3')");
                stmt.execute("INSERT INTO QUIZ (id, type, text) VALUES (4, 14, 'Question #4')");
                stmt.execute("INSERT INTO QUIZ (id, type, text) VALUES (5, 15, 'Question #5')");
                stmt.close();
                cn.commit();
            } catch (SQLException e) {
            	e.printStackTrace();
    		} finally {
    			try {
    				cn.close();
    			} catch (Exception e) {}
    		}

        }
    }

}