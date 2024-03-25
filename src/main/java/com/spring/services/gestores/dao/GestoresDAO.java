package com.spring.services.gestores.dao;

import com.spring.services.gestores.model.GestoresModel;
import com.spring.utils.ConexionBD;
import com.spring.utils.RestResponse;
import org.apache.commons.io.input.TeeReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class GestoresDAO {
    private static final Logger LOGGER = LogManager.getLogger("GestoresDAO");
    ConexionBD conexionbd=new ConexionBD();
    public GestoresDAO() {
        //Vacio
    }

    public RestResponse<ArrayList<GestoresModel>> consultarGestoresTKM(){
        RestResponse<ArrayList<GestoresModel>> respuesta= new RestResponse<>();

        respuesta.setCode(0);
        respuesta.setError(true);
        String query="SELECT * FROM personal;";
        Statement st;
        try{
            ArrayList<GestoresModel> personal=new ArrayList<>();
            st=conexionbd.establecerConexion().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                GestoresModel persona=new GestoresModel();
                persona.setIdTkm(rs.getInt(1));
                persona.setIdGestor(rs.getString(2));
                persona.setNombreGestor(rs.getString(3));
                persona.setPassword(rs.getString(4));
                persona.setPuesto(rs.getInt(5));
                persona.setIdRegistro(rs.getInt(6));
                persona.setIdActualizo(rs.getInt(7));
                persona.setTurno(rs.getString(8));
                personal.add(persona);
            }
            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Prueba conexion BD");
            respuesta.setData(personal);
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarGestoresTKM: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarGestoresTKM: "+e);
        }
        return respuesta;
    }

    public RestResponse<String> insertarGestoresTKM(GestoresModel empleado){
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="INSERT INTO personal (idGestorSCL,nombre,password,puesto,idRegisto,turno) values (?,?,?,?,?,?);";
        try{
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);
            cs.setString(1, empleado.getIdGestor());
            cs.setString(2,empleado.getNombreGestor());
            cs.setString(3, empleado.getPassword());
            cs.setInt(4,empleado.getPuesto());
            cs.setInt(5,empleado.getIdRegistro());
            cs.setString(6,empleado.getTurno());
            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro insertado en la BD");
            respuesta.setData("Registro insertado correctamente");

        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarGestoresTKM: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarGestoresTKM: "+e);
        }
        return respuesta;
    }

    public RestResponse<String> actualizarGestoresTKM(GestoresModel empleado){
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="UPDATE personal SET personal.idGestorSCL=?,personal.nombre=?,personal.password=?,personal.puesto=?,personal.idRegisto=?,personal.idActualizo=?,personal.turno=? WHERE personal.id=?";
        try{
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);
            cs.setString(1, empleado.getIdGestor());
            cs.setString(2,empleado.getNombreGestor());
            cs.setString(3, empleado.getPassword());
            cs.setInt(4,empleado.getPuesto());
            cs.setInt(5,empleado.getIdRegistro());
            cs.setInt(6,empleado.getIdActualizo());
            cs.setString(7,empleado.getTurno());
            cs.setInt(8,empleado.getIdTkm());

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro actualizado en la BD");
            respuesta.setData("Registro actualizado correctamente");
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarGestoresTKM: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarGestoresTKM: "+e);
        }
        return respuesta;
    }

    public RestResponse<String> eliminarGestoresTKM(String idEmpleado){
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query="DELETE FROM personal WHERE personal.id=?";
        try{
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);
            cs.setInt(1,Integer.parseInt(idEmpleado));

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro borrado en la BD");
            respuesta.setData("Registro borrado correctamente");
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarGestoresTKM: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarGestoresTKM: "+e);
        }
        return respuesta;
    }
}
