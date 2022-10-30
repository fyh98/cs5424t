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
        entityManagerFactoryRef = "entityManagerFactoryXcnd49",
        transactionManagerRef = "transactionManagerXcnd49",
        basePackages = {"cs5424t.ysql.DAO.Xcnd49"})
class Xcnd49 {

    @Autowired
    @Qualifier("xcnd49Datasource")
    private DataSource xcnd49Datasource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerXcnd49")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryXcnd49(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryXcnd49")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryXcnd49(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(xcnd49Datasource)
                .packages("cs5424t.ysql.Entities.Xcnd49")
                .persistenceUnit("xcnd49PersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    @Bean(name = "transactionManagerXcnd49")
    public PlatformTransactionManager transactionManagerXcnd49(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryXcnd49(builder).getObject());
    }
}
