package com.spring.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final Logger LOGGER = LogManager.getLogger("ConexionBD");
    Connection conectar = null;
    Connection conectar2 = null;
//    private String url;
//    private String userio;
//    private String password;


    public ConexionBD() {
        //Vacio
    }

    String usuario="BDUSERCLTKMBAZ";
    String password="COLETkmBD2023.3";
//    String usuario="sa";
//    String password="CobranzaLegalTKMBD23.";

    String bd="BDCLBAZTKM";
    //String ip="localhost";
    String ip="172.16.201.28";
    String puerto="1433";
    String cadena="jdbc:sqlserver://"+ip+":"+puerto+"/"+bd;


    String usuario2="sa";
    String password2="COLETkmBD2023.1";
    String bd2="BDCLBAZTKM2";
    String ip2="172.16.201.27";
    String puerto2="1433";
    String cadena2="jdbc:sqlserver://"+ip2+":"+puerto2+"/"+bd2;


    public Connection establecerConexion(){
        try{
//            String cadena="jdbc:sqlserver://"+ip+":"+puerto+";databaseName="+bd+";integratedSecurity=true;encrypt=true;trustServerCertificate=true";
            String cadena="jdbc:sqlserver://"+ip+":"+puerto+";databaseName="+bd+";encrypt=true;trustServerCertificate=true";
            conectar= DriverManager.getConnection(cadena,usuario,password);
            //LOGGER.log(Level.INFO, () -> "Se conecto correctamente a la BD");
        }
        catch (Exception e){
            LOGGER.log(Level.INFO, () -> "ISSUE Al intentar conectar con la BD: "+e);
        }
        return conectar;
    }



    public Connection establecerConexion2(){
        try{

            String cadena2="jdbc:sqlserver://"+ip2+":"+puerto2+";databaseName="+bd2+";encrypt=true;trustServerCertificate=true";
            conectar2= DriverManager.getConnection(cadena2,usuario2,password2);
        }
        catch (Exception e){
            LOGGER.log(Level.INFO, () -> "ISSUE Al intentar conectar con la BD2: "+e);
        }
        return conectar2;
    }


}
