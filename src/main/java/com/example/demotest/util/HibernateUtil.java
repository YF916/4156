package com.example.demotest.util;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

//code from https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            /*Configuration cfg = new Configuration()
                    .addAnnotatedClass(com.example.demotest.model.Responder.class)
                    .addAnnotatedClass(com.example.demotest.model.User.class)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.username", "springuser")
                    .setProperty("hibernate.connection.password", "ThePassword")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/ase")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.format_sql", "true");
                    //.addFile("Responder.hbm.xml").addFile("User.hbm.xml");*/
            //.configure("hibernate.cfg.xml")
            Configuration cfg = new Configuration().addAnnotatedClass(Responder.class)
                    .addAnnotatedClass(User.class)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.username", "hibernateuser")
                    .setProperty("hibernate.connection.password", "hibernatePassword")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/hibernate")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.format_sql", "true");
            return cfg
                    .buildSessionFactory(
                    new StandardServiceRegistryBuilder().build());
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
