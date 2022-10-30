package cs5424t.ysql.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryXcnd47",
        transactionManagerRef = "transactionManagerXcnd47",
        basePackages = {"cs5424t.ysql.DAO.Xcnd47"})
class Xcnd47 {

    @Autowired
    @Qualifier("xcnd47Datasource")
    private DataSource xcnd47Datasource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerXcnd47")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryXcnd47(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryXcnd47")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryXcnd47(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(xcnd47Datasource)
                .packages("cs5424t.ysql.Entities.Xcnd47")
                .persistenceUnit("xcnd47PersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    @Bean(name = "transactionManagerXcnd47")
    public PlatformTransactionManager transactionManagerXcnd47(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryXcnd47(builder).getObject());
    }
}
