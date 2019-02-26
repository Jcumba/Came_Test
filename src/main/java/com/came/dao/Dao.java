package com.came.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class Dao {

    private Connection cn = null;

    public void Conexion() {
        FabricaConexion fcn;
        try {
            if (cn == null) {
                fcn = new FabricaConexion();
                Class.forName("oracle.jdbc.OracleDriver");
                cn = fcn.getConexion(fcn.Produccion);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error -> " + e.getMessage());
        }
    }

    public void Cerrar() throws SQLException {      //Cerrar la coneccion
        if (cn != null) {
            if (cn.isClosed() == false) {
                cn.close();
                cn = null;
            }
        }
    }

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Dao dao = new Dao();
        dao.Conexion();
        if (dao.getCn() != null) {
            System.out.println("Conectado");
        } else {
            System.err.println("Coneccion es null Error");
        }
    }
}
