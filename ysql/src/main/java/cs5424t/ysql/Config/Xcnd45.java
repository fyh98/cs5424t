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
        entityManagerFactoryRef = "entityManagerFactoryXcnd45",
        transactionManagerRef = "transactionManagerXcnd45",
        basePackages = {"cs5424t.ysql.DAO.Xcnd45"})
class Xcnd45 {

    @Autowired
    @Qualifier("xcnd45Datasource")
    private DataSource xcnd45Datasource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "entityManagerXcnd45")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryXcnd45(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryXcnd45")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryXcnd45(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(xcnd45Datasource)
                .packages("cs5424t.ysql.Entities.Xcnd45")
                .persistenceUnit("xcnd45PersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerXcnd45")
    public PlatformTransactionManager transactionManagerXcnd45(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryXcnd45(builder).getObject());
    }
}
