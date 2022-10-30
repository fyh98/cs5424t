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
        entityManagerFactoryRef = "entityManagerFactoryXcnd48",
        transactionManagerRef = "transactionManagerXcnd48",
        basePackages = {"cs5424t.ysql.DAO.Xcnd48"})
class Xcnd48 {

    @Autowired
    @Qualifier("xcnd48Datasource")
    private DataSource xcnd48Datasource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerXcnd48")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryXcnd48(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryXcnd48")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryXcnd48(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(xcnd48Datasource)
                .packages("cs5424t.ysql.Entities.Xcnd48")
                .persistenceUnit("xcnd48PersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    @Bean(name = "transactionManagerXcnd48")
    public PlatformTransactionManager transactionManagerXcnd48(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryXcnd48(builder).getObject());
    }
}
