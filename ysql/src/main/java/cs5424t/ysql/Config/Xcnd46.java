package cs5424t.ysql.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "entityManagerFactoryXcnd46",
        transactionManagerRef = "transactionManagerXcnd46",
        basePackages = {"cs5424t.ysql.DAO.Xcnd46"}) // 设置Repository所在位置
class Xcnd46 {

    @Autowired
    @Qualifier("xcnd46Datasource")
    private DataSource xcnd46Datasource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "entityManagerXcnd46")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryXcnd46(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryXcnd46")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryXcnd46(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(xcnd46Datasource)
                .packages("cs5424t.ysql.Entities.Xcnd46") // 设置实体类所在位置
                .persistenceUnit("xcnd46PersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    @Bean(name = "transactionManagerXcnd46")
    public PlatformTransactionManager transactionManagerXcnd46(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryXcnd46(builder).getObject());
    }
}
