package com.spring.services.operacion.dao;

import com.spring.services.operacion.model.GestionLlamadasModel;
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
public class GestionLlamadasDAO {
    private static final Logger LOGGER = LogManager.getLogger("GestionLlamadasDAO");
    private ConexionBD conexionbd=new ConexionBD();
    public GestionLlamadasDAO() {
        //Vacio
    }

    public RestResponse<ArrayList<GestionLlamadasModel>> consultarGestionLlamadas() {
        RestResponse<ArrayList<GestionLlamadasModel>> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="SELECT * FROM gestiones;";
        Statement st;
        try{
            ArrayList<GestionLlamadasModel> gestiones=new ArrayList<>();
            st=conexionbd.establecerConexion2().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                GestionLlamadasModel gestion=new GestionLlamadasModel();
                gestion.setIdGestionLlam(rs.getInt(1));
                gestion.setClienteUnico(rs.getString(2));
                gestion.setTelefono(rs.getString(3));
                gestion.setIdGestorTkm(rs.getInt(4));
                gestion.setIdTipificacion(rs.getInt(5));
                gestion.setComentario(rs.getString(6));
                gestion.setFechaInserto(rs.getString(7));
                gestion.setHoraInserto(rs.getString(8));
                gestion.setTipoCarteraTKM(rs.getString(9));
                gestiones.add(gestion);
            }
            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Se obtuvieron las gestiones telefonicas");
            respuesta.setData(gestiones);
            rs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarGestionLlamadas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarGestionLlamadas: "+e);
        }


        return respuesta;
    }

    public RestResponse<ArrayList<GestionLlamadasModel>> consultarGestionLlamadasNumero(String numero) {
        RestResponse<ArrayList<GestionLlamadasModel>> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="SELECT * FROM gestiones WHERE gestiones.telefono='"+numero+"'";
        Statement st;
        try{
            ArrayList<GestionLlamadasModel> gestiones=new ArrayList<>();
            st=conexionbd.establecerConexion2().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                GestionLlamadasModel gestion=new GestionLlamadasModel();
                gestion.setIdGestionLlam(rs.getInt(1));
                gestion.setClienteUnico(rs.getString(2));
                gestion.setTelefono(rs.getString(3));
                gestion.setIdGestorTkm(rs.getInt(4));
                gestion.setIdTipificacion(rs.getInt(5));
                gestion.setComentario(rs.getString(6));
                gestion.setFechaInserto(rs.getString(7));
                gestion.setHoraInserto(rs.getString(8));
                gestion.setTipoCarteraTKM(rs.getString(9));
                gestiones.add(gestion);
            }
            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Se obtuvieron las gestiones telefonicas");
            respuesta.setData(gestiones);
            rs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarGestionLlamadas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarGestionLlamadas: "+e);
        }


        return respuesta;
    }

    public RestResponse<String> insertarGestionLlamadas(GestionLlamadasModel gestLlam) {
        RestResponse<String>respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="INSERT INTO gestiones(clienteUnico,telefono,idGestorTKM,idTipificacion,comentario,fechaInserto,horaInserto,tipoCarteraTKM) values (?,?,?,?,?,?,?,?);";
        try{
            CallableStatement cs = conexionbd.establecerConexion2().prepareCall(query);
            cs.setString(1,gestLlam.getClienteUnico());
            cs.setString(2,gestLlam.getTelefono());
            cs.setInt(3,gestLlam.getIdGestorTkm());
            cs.setInt(4,gestLlam.getIdTipificacion());
            cs.setString(5, gestLlam.getComentario());
            cs.setString(6,gestLlam.getFechaInserto());
            cs.setString(7,gestLlam.getHoraInserto());
            cs.setString(8,gestLlam.getTipoCarteraTKM());
            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro insertado en la BD");
            respuesta.setData("Registro insertado correctamente");
            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE insertarGestionLlamadas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE insertarGestionLlamadas: "+e);
        }

        return respuesta;
    }

    public RestResponse<String> actualizarGestionLlamadas(GestionLlamadasModel gestLlam) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="UPDATE gestiones SET gestiones.clienteUnico=?,gestiones.telefono=?,gestiones.idGestorTKM=?,gestiones.idTipificacion=?,gestiones.comentario=? WHERE gestiones.id=?";
		try {
            CallableStatement cs=conexionbd.establecerConexion2().prepareCall(query);
            cs.setString(1,gestLlam.getClienteUnico());
            cs.setString(2,gestLlam.getTelefono());
            cs.setInt(3,gestLlam.getIdGestorTkm());
            cs.setInt(4,gestLlam.getIdTipificacion());
            cs.setString(5,gestLlam.getComentario());
            cs.setInt(6,gestLlam.getIdGestionLlam());
            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro actualizado en la BD");
            respuesta.setData("Registro actualizado correctamente");
            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE actualizarGestionLlamadas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE actualizarGestionLlamadas: "+e);
        }
        return respuesta;
    }

    public RestResponse<String> borrarGestionLlamadas(ArrayList<String> idGestion, String idSupervisor) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="DELETE FROM gestiones WHERE gestiones.id=?";
        try{

            CallableStatement cs=conexionbd.establecerConexion2().prepareCall(query);
            for(int i=0;i< idGestion.size();i++) {
                String id=idGestion.get(i);
                LOGGER.log(Level.INFO, () -> "REQUEST  borrarGestionLlamadas idGestion"+id+", idSupervisor: "+idSupervisor);
                cs.setInt(1, Integer.parseInt(id));
                cs.execute();
            }


            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registros borrados en la BD");
            respuesta.setData("Registro borrados correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE  borrarGestionLlamadas "+respuesta);
            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE borrarGestionLlamadas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE borrarGestionLlamadas: "+e);
        }
        return respuesta;
    }
}
