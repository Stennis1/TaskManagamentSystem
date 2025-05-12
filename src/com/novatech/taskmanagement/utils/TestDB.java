package com.novatech.taskmanagement.utils;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            Class.forName("org.postgresql.Driver");
            if (connection != null) {
                System.out.println("Connection successful!");
            }
        } catch (Exception e) {
            System.err.println("Connection failed! " + e.getMessage());
        }

    }
}
