package com.spring.services.pagos.dao;

import com.spring.services.constantes.Constantes;
import com.spring.services.pagos.model.PromesasModel;
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
public class PromesasTKMDAO {
    private static final Logger LOGGER = LogManager.getLogger("PromesasTKMDAO");
    ConexionBD conexionbd=new ConexionBD();

    public PromesasTKMDAO() {
        //Vacio
    }

    public RestResponse<ArrayList<PromesasModel>> consultarPromesas() {
        RestResponse<ArrayList<PromesasModel>> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query= Constantes.consultaBD+"promesaspp;";
        Statement st;
        try{
            ArrayList<PromesasModel> promesas=new ArrayList<>();
            st=conexionbd.establecerConexion().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                PromesasModel promesa=new PromesasModel();
                promesa.setId(rs.getInt(1));
                promesa.setFechaIngesoPP(rs.getString(2));
                promesa.setFechaPago(rs.getString(3));
                promesa.setFechaVencimientoPP(rs.getString(4));
                promesa.setFolio(rs.getString(5));
                promesa.setMontoPago(rs.getString(6));
                promesa.setNombreCliente(rs.getString(7));
                promesa.setClienteUnico(rs.getString(8));
                promesa.setTelefono(rs.getString(9));
                promesa.setIdGestorSCL(rs.getString(10));
                promesa.setNombreGestor(rs.getString(11));
                promesa.setObservaciones(rs.getString(12));
                promesa.setAsignado(rs.getInt(13));
                promesa.setWhatsApp(rs.getInt(14));
                promesa.setNota(rs.getString(15));
                promesa.setEdito(rs.getString(16));
                promesa.setIdGestorTKM(rs.getInt(17));
                promesa.setInserto(rs.getInt(18));
                promesa.setTipoLlamada(rs.getString(22));
                promesa.setFechInser(rs.getString(23));
                promesa.setPagoFinal(rs.getInt(24));
                promesa.setTurnoGestor(rs.getString(25));
                promesa.setIdAutorizo(rs.getInt(26));
                promesa.setTipoCartera(rs.getString(27));
                promesa.setIdGestorSCLVIP(rs.getString(28));
                promesa.setRecurrencia(rs.getString(29));
                promesa.setMontoInicial(rs.getString(30));
                promesa.setMontoSemanal(rs.getString(31));
                promesas.add(promesa);
            }
            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Se obtuvieron correctamente las promesas");
            respuesta.setData(promesas);
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarPromesasTKM: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarPromesasTKM: "+e);
        }
        return respuesta;

    }

    public RestResponse<String> insertarPromesas(PromesasModel promesa) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query=Constantes.insertarBD+"promesaspp (fechaIngresoPP,fechaPago,fechaVencimientoPP,folio,montoPago,nombreCliente,clienteUnico,telefono,idGestorSCL,nombreGestor,observaciones,asignado,whatsApp,nota,idGestorTKM,inserto,tipoLlamada,fechInser,pagoFinal,turnoGestor,idAutorizo,tipoCartera,idGestorSCLVIP,recurrencia,montoInicial,montoSemanal)"+Constantes.valuesBD+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try{
            LOGGER.log(Level.INFO, () -> "REQUEST insertarPromesas: "+promesa.toString());
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);
            cs.setString(1,promesa.getFechaIngesoPP());
            cs.setString(2,promesa.getFechaPago());
            cs.setString(3,promesa.getFechaVencimientoPP());
            cs.setString(4,promesa.getFolio());
            cs.setString(5,promesa.getMontoPago());
            cs.setString(6,promesa.getNombreCliente());
            cs.setString(7,promesa.getClienteUnico());
            cs.setString(8,promesa.getTelefono());
            cs.setString(9,promesa.getIdGestorSCL());
            cs.setString(10,promesa.getNombreGestor());
            cs.setString(11,promesa.getObservaciones());
            cs.setInt(12,promesa.getAsignado());
            cs.setInt(13,promesa.getWhatsApp());
            cs.setString(14,promesa.getNota());
            cs.setInt(15,promesa.getIdGestorTKM());
            cs.setInt(16,promesa.getInserto());
            cs.setString(17,promesa.getTipoLlamada());
            cs.setString(18,promesa.getFechInser());
            cs.setInt(19,promesa.getPagoFinal());
            cs.setString(20,promesa.getTurnoGestor());
            cs.setInt(21,promesa.getIdAutorizo());
            cs.setString(22,promesa.getTipoCartera());
            cs.setString(23,promesa.getIdGestorSCLVIP());
            cs.setString(24, promesa.getRecurrencia());
            cs.setString(25,promesa.getMontoInicial());
            cs.setString(26,promesa.getMontoSemanal());

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro insertado en la BD");
            respuesta.setData("Registro insertado correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE insertarPromesas: "+respuesta.toString());
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE insertarPromesas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE insertarPromesas: "+e);
        }
        return respuesta;

    }


    public RestResponse<String> actualizarPromesas(PromesasModel promesa) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query=Constantes.updateBD+"promesaspp SET promesaspp.fechaIngresoPP=?,promesaspp.fechaPago=?,promesaspp.fechaVencimientoPP=?, promesaspp.folio=?, promesaspp.montoPago=?, promesaspp.nombreCliente=?, promesaspp.clienteUnico=?, promesaspp.telefono=?, promesaspp.idGestorSCL=?, promesaspp.nombreGestor=?, promesaspp.observaciones=?, promesaspp.asignado=?, promesaspp.whatsApp=?, promesaspp.nota=?, promesaspp.edito=?, promesaspp.idGestorTKM=?, promesaspp.inserto=?, promesaspp.tipoLlamada=?,promesaspp.pagoFinal=?, promesaspp.recurrencia=?,promesaspp.montoSemanal=? WHERE promesaspp.id=?";
        try{
            LOGGER.log(Level.INFO, () -> "REQUEST actualizarPromesas: "+promesa.toString());
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);

            cs.setString(1,promesa.getFechaIngesoPP());
            cs.setString(2,promesa.getFechaPago());
            cs.setString(3,promesa.getFechaVencimientoPP());
            cs.setString(4,promesa.getFolio());
            cs.setString(5,promesa.getMontoPago());
            cs.setString(6,promesa.getNombreCliente());
            cs.setString(7,promesa.getClienteUnico());
            cs.setString(8,promesa.getTelefono());
            cs.setString(9,promesa.getIdGestorSCL());
            cs.setString(10,promesa.getNombreGestor());
            cs.setString(11,promesa.getObservaciones());
            cs.setInt(12,promesa.getAsignado());
            cs.setInt(13,promesa.getWhatsApp());
            cs.setString(14,promesa.getNota());
            cs.setString(15,promesa.getEdito());
            cs.setInt(16,promesa.getIdGestorTKM());
            cs.setInt(17,promesa.getInserto());
            cs.setString(18,promesa.getTipoLlamada());
            cs.setInt(19,promesa.getPagoFinal());
            cs.setString(20,promesa.getRecurrencia());
            cs.setString(21,promesa.getMontoSemanal());
            cs.setInt(22,promesa.getId());

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro actualizado en la BD");
            respuesta.setData("Registro actualizado correctamente");

            LOGGER.log(Level.INFO, () -> "RESPONSE actualizarPromesas: "+respuesta.toString());
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE actualizarPromesas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE actualizarPromesas: "+e);
        }
        return respuesta;

    }

    public RestResponse<String> actualizarPromesasAsignar(PromesasModel promesa) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query=Constantes.updateBD+"promesaspp SET promesaspp.asignado=?, promesaspp.edito=? WHERE promesaspp.id=?";
        try{
            LOGGER.log(Level.INFO, () -> "REQUEST actualizarPromesasAsignar: "+promesa.toString());
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);

            cs.setInt(1,promesa.getAsignado());
            cs.setString(2,promesa.getEdito());
            cs.setInt(3,promesa.getId());

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro asignado en la BD");
            respuesta.setData("Registro asignado correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE actualizarPromesasAsignar: "+respuesta.toString());
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE actualizarPromesasAsignar: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE actualizarPromesasAsignar: "+e);
        }
        return respuesta;

    }



    public RestResponse<String> actualizarPromesasEstPag(PromesasModel promesa) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query=Constantes.updateBD+"promesaspp SET promesaspp.nota=?, promesaspp.pagoFinal=?,promesaspp.fechaPago=?,promesaspp.fechaVencimientoPP=?,promesaspp.recurrencia=?,promesaspp.montoPago=? WHERE promesaspp.id=?";
        try{
            LOGGER.log(Level.INFO, () -> "REQUEST actualizarPromesasEstPag: "+promesa.toString());
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);


            cs.setString(1,promesa.getNota());
            cs.setInt(2,promesa.getPagoFinal());
            cs.setString(3,promesa.getFechaPago());
            cs.setString(4,promesa.getFechaVencimientoPP());
            cs.setString(5,promesa.getRecurrencia());
            cs.setString(6,promesa.getMontoPago());
            cs.setInt(7,promesa.getId());

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro actualizado en la BD");
            respuesta.setData("Registro actualizado correctamente");

            LOGGER.log(Level.INFO, () -> "RESPONSE actualizarPromesasEstPag: "+respuesta.toString());
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE actualizarPromesasEstPag: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE actualizarPromesasEstPag: "+e);
        }
        return respuesta;

    }



    public RestResponse<String> borrarPromesas(String idPromesa,String idAdmin) {
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String query=Constantes.deleteBD+"promesaspp WHERE promesaspp.id=?";
        try {
            LOGGER.log(Level.INFO, () -> "REQUEST borrarPromesas: "+idPromesa+", idAdmin: "+idAdmin);
            CallableStatement cs=conexionbd.establecerConexion().prepareCall(query);
            cs.setInt(1,Integer.parseInt(idPromesa));

            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro borrado en la BD");
            respuesta.setData("Registro borrado correctamente");
            LOGGER.log(Level.INFO, () -> "RESPONSE borrarPromesas: "+respuesta);
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE borrarPromesas: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE borrarPromesas: "+e);
        }
        return respuesta;
    }
}
