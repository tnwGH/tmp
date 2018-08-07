package com.scot.quiz;

import java.util.Properties;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.AbstractModule;
import com.google.inject.PrivateModule;
import com.google.inject.name.Names;

public class QuizMyBatisModule extends AbstractModule {

	@Override
    protected void configure() {

        install(new PrivateModule() {
        	@Override
            protected void configure() {
        		install(new MyBatisModule() {
        			@Override
        			protected void initialize() {
        				Names.bindProperties(binder(), getProperties());
		        		install(JdbcHelper.H2_EMBEDDED);
		                bindDataSourceProviderType(PooledDataSourceProvider.class);
		                bindTransactionFactoryType(JdbcTransactionFactory.class);

		                addMapperClass(QuizMapper.class);
        			}
        		});
        		
        		expose(QuizMapper.class);
        	}
        });

    }

	private Properties getProperties() {
		Properties myBatisProperties = new Properties();
		/*myBatisProperties.setProperty("mybatis.environment.id", "default");*/
		myBatisProperties.setProperty("mybatis.environment.id", "test");
		myBatisProperties.setProperty("JDBC.schema", "~/test");
		myBatisProperties.setProperty("JDBC.username", "sa");
		myBatisProperties.setProperty("JDBC.password", "");
		myBatisProperties.setProperty("JDBC.autoCommit", "false");
		return myBatisProperties;
	}

}