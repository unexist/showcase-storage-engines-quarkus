/**
 * @package Showcase-Storage-Engines-Quarkus
 *
 * @file Todo repository
 * @copyright 2020-present Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.infrastructure.persistence;

import dev.unexist.showcase.todo.domain.todo.Todo;
import dev.unexist.showcase.todo.domain.todo.TodoRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("duck")
public class DuckListTodoRepository implements TodoRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DuckListTodoRepository.class);

    private static final String CREATE_TABLE = "CREATE TABLE todos ("
        + "id SERIAL,"
        + "title TEXT NOT NULL,"
        + "description TEXT NOT NULL)";

    private Connection connection;

    /**
     * Constructor
     **/

    DuckListTodoRepository() {
        try {
            Class.forName("org.duckdb.DuckDBDriver");

            this.connection = DriverManager.getConnection("jdbc:duckdb:");

            Statement stmt = this.connection.createStatement();

            stmt.executeUpdate(CREATE_TABLE);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(final Todo todo) {
        String query = "INSERT INTO todos(title, string) VALUES (?,?)";

        try (PreparedStatement prepStmt = this.connection.prepareStatement(query)) {
            prepStmt.setString(1, todo.getTitle());
            prepStmt.setString(2, todo.getDescription());
            prepStmt.addBatch();

            prepStmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean update(final Todo todo) {
        throw new NotImplementedException("Needs to be implemented later");
    }

    @Override
    public boolean deleteById(int id) {
        throw new NotImplementedException("Needs to be implemented later");
    }

    @Override
    public List<Todo> getAll() {
        throw new NotImplementedException("Needs to be implemented later");
    }

    @Override
    public Optional<Todo> findById(int id) {
        throw new NotImplementedException("Needs to be implemented later");
    }
}