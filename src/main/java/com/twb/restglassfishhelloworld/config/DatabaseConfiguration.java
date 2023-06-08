package com.twb.restglassfishhelloworld.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.logging.Logger;

@WebListener
public class DatabaseConfiguration implements ServletContextListener {
    public static final String PERSISTENCE_UNIT_NAME = "default";
    private static final String CHANGELOG_FILE_LOCATION = "db/changelog.xml";
    private final Logger logger = Logger.getLogger(DatabaseConfiguration.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting liquibase update...");

        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
             EntityManager em = entityManagerFactory.createEntityManager()) {

            Session session = em.unwrap(Session.class);
            session.doWork(connection -> {
                try {
                    Database database = DatabaseFactory.getInstance()
                            .findCorrectDatabaseImplementation(new JdbcConnection(connection));
                    CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
                    updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
                    updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, CHANGELOG_FILE_LOCATION);
                    updateCommand.execute();
                    logger.info("Liquibase update completed successfully.");
                } catch (DatabaseException | CommandExecutionException e) {
                    throw new SQLException("Failed execute liquibase update", e);
                }
            });
        }
    }
}
