package it.univaq.disim.oop.pharmathome.business.impl.db;

public class ConnectionDB	{

    private static final String DB_NAME = "pharmathome";
    protected static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME + "?noAccessToProcedureBodies=true" + "&serverTimezone=Europe/Rome";
    protected static final String DB_USER = "admin";
    protected static final String DB_PASSWORD = "admin";
}