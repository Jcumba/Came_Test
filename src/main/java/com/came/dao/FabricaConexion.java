package com.came.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexion {

    public int Produccion = 1;
    public int Testing = 2;
    public int LocalHost = 3;
    public int Came = 4;

    public Connection getConexion(int Servidor) throws SQLException {
        switch (Servidor) {
            case 1:
                return DriverManager.getConnection("jdbc:oracle:thin:@35.237.73.4:1521:XE", "CAME", "vallegrande2018");
            case 2:
                return DriverManager.getConnection("jdbc:oracle:thin:@192.168.168.3:1521:XE", "CAME", "AdS_2018-Came");
//                return DriverManager.getConnection("jdbc:oracle:thin:@testing.vallegrande.edu.pe:1521:XE", "CAME", "vallegrande2018");
            case 3:
                return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Came", "vallegrande2018");
            case 4:
                return DriverManager.getConnection("jdbc:oracle:thin:@localhost:4910:XE", "Came", "Came-VG_2018");
//                return DriverManager.getConnection("jdbc:oracle:thin:@35.231.16.77:4910:XE", "Came", "Came-VG_2018");
            default:
                return null;
        }
    }

}
