package com.aetxabao.chat.server.utils;

import com.aetxabao.chat.server.model.Record;
import com.aetxabao.chat.server.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class OrmUtil {
    private static StandardServiceRegistry standardRegistry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build();

                // Create Metadata
                Metadata metadata = new MetadataSources(standardRegistry)
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Record.class)
                        .getMetadataBuilder()
                        .build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                System.out.println("ERROR");
                e.printStackTrace();
                if (standardRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(standardRegistry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (standardRegistry != null) {
            StandardServiceRegistryBuilder.destroy(standardRegistry);
        }
    }

}