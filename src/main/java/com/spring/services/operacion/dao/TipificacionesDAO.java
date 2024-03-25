package com.spring.services.operacion.dao;

import com.spring.services.operacion.model.TipificacionesModel;
import com.spring.utils.ConexionBD;
import com.spring.utils.RestResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class TipificacionesDAO {
    private static final Logger LOGGER = LogManager.getLogger("TipificacionesDAO");
    private ConexionBD conexionbd=new ConexionBD();

    public TipificacionesDAO() {
        //Vacio
    }

    public RestResponse<ArrayList<TipificacionesModel>> consultarTipificaciones() {
        RestResponse<ArrayList<TipificacionesModel>> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="SELECT * FROM tipificaciones;";
        Statement st;
        try{
            ArrayList<TipificacionesModel> tipifaciones=new ArrayList<>();
            st=conexionbd.establecerConexion2().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                TipificacionesModel tipificacion=new TipificacionesModel();
                tipificacion.setId(rs.getInt(1));
                tipificacion.setTipificacion(rs.getString(2));
                tipificacion.setValor(rs.getInt(3));
                tipificacion.setInserto(rs.getInt(4));
                tipificacion.setActualizo(rs.getInt(5));
                tipificacion.setFechaInserto(rs.getString(6));
                tipifaciones.add(tipificacion);
            }

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Se obtuvieron las tipificaciones");
            respuesta.setData(tipifaciones);
            rs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarTipificaciones: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarTipificaciones: "+e);
        }

        return respuesta;
    }

    public RestResponse<String> insertarTipificaciones(TipificacionesModel tipif) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="INSERT INTO tipificaciones (tipificacion,valor,inserto,fechaInserto) values (?,?,?,?);";
        try {
            LOGGER.log(Level.INFO, () -> "REQUEST insertarTipificaciones: "+tipif);
            CallableStatement cs = conexionbd.establecerConexion2().prepareCall(query);
            cs.setString(1, tipif.getTipificacion());
            cs.setInt(2,tipif.getValor());
            cs.setInt(3,tipif.getInserto());
            cs.setString(4,tipif.getFechaInserto());
            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro insertado en la BD");
            respuesta.setData("Registro insertado correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE insertarTipificaciones: "+respuesta);
            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE insertarTipificaciones: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE insertarTipificaciones: "+e);
        }

        return respuesta;
    }

    public RestResponse<String> actualizarTipificaciones(TipificacionesModel tipif) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="UPDATE tipificaciones SET tipificaciones.tipificacion=?,tipificaciones.valor=?,tipificaciones.actualizo=? WHERE tipificaciones.id=?";
        try{
            LOGGER.log(Level.INFO, () -> "REQUEST actualizarTipificaciones: "+tipif);
            CallableStatement cs=conexionbd.establecerConexion2().prepareCall(query);
            cs.setString(1, tipif.getTipificacion());
            cs.setInt(2,tipif.getValor());
            cs.setInt(3,tipif.getActualizo());
            cs.setInt(4,tipif.getId());
            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro actualizado en la BD");
            respuesta.setData("Registro actualizado correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE actualizarTipificaciones: "+respuesta);
            cs.close();

        }
        catch (Exception e){
            respuesta.setMessage("ISSUE actualizarTipificaciones: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE actualizarTipificaciones: "+e);
        }
        return respuesta;
    }

    public RestResponse<String> borrarTipificaciones(String idTipificacion,String idSupervisor) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="DELETE FROM tipificaciones WHERE tipificaciones.id=?";
        try{
            LOGGER.log(Level.INFO, () -> "REQUEST borrarTipificaciones idTipificacion: "+idTipificacion+", idSupervisor: "+idSupervisor);
            CallableStatement cs=conexionbd.establecerConexion2().prepareCall(query);
            cs.setInt(1,Integer.parseInt(idTipificacion));

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro borrado en la BD");
            respuesta.setData("Registro borrado correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE borrarTipificaciones: "+respuesta);
            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE borrarTipificaciones: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE borrarTipificaciones: "+e);
        }
        return respuesta;
    }
}

