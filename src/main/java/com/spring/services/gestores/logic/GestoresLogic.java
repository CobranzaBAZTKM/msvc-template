package com.spring.services.gestores.logic;

import com.spring.services.gestores.dao.GestoresDAO;
import com.spring.services.gestores.model.GestoresModel;

import com.spring.services.pagos.logic.PromesasTKMLogic;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.utils.RestResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GestoresLogic {

    private static final Logger LOGGER = LogManager.getLogger("GestoresLogic");

    @Autowired
    private MantenimientoGestoresLogic mantGesLog;
    @Autowired
    private GestoresDAO gestoresDAO;
//    @Autowired
//    private PromesasTKMLogic promesasLog=new PromesasTKMLogic();


    public GestoresLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<GestoresModel>> consultarGestoresSCL(GestoresModel clientes) {
        RestResponse<ArrayList<GestoresModel>> respuesta=new RestResponse<>();
        RestResponse<JSONObject> obtenerGestoresComp=new RestResponse<>();
        ArrayList<GestoresModel> respArray=new ArrayList<>();

        int sumaIntentos=0;
        do{
            obtenerGestoresComp=mantGesLog.obtenerGestoresSCL(clientes);
            sumaIntentos++;
        }while(obtenerGestoresComp.getCode()!=1&&sumaIntentos!=10);

        if(obtenerGestoresComp.getCode()==1){
            JSONArray gestoresCompletos= obtenerGestoresComp.getData().getJSONArray("respuesta");
            for(int i=0;i<gestoresCompletos.length();i++){
                GestoresModel gestor=new GestoresModel();
                gestor.setIdGestor(gestoresCompletos.getJSONObject(i).getString("idgestor"));
                gestor.setNombreGestor(gestoresCompletos.getJSONObject(i).getString("nombre"));
                respArray.add(gestor);
            }
            respuesta.setCode(1);
            respuesta.setMessage("Gestores obtenidos");
            respuesta.setData(respArray);
            respuesta.setError(false);
        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("Favor de volver a intentar");
            respuesta.setError(false);

        }
        return respuesta;
    }

    public RestResponse<String> asignarClientesSCL(GestoresModel clientes,String tipoCarteraTKM) {
        RestResponse<String> respuesta=new RestResponse<>();
        RestResponse<JSONObject> obtenerGestoresComp=new RestResponse<>();
        int sumaIntentos=0;
        do{
            obtenerGestoresComp=mantGesLog.obtenerGestoresSCL(clientes);
            sumaIntentos++;
        }
        while(obtenerGestoresComp.getCode()!=1&&sumaIntentos!=10);

        if(obtenerGestoresComp.getCode()==1){
            String idGestor=null;
            JSONArray gestoresCompletos= obtenerGestoresComp.getData().getJSONArray("respuesta");
            for(int i=0;i<gestoresCompletos.length();i++){
                if(clientes.getNombreGestor().equals(gestoresCompletos.getJSONObject(i).getString("nombre"))){
                    idGestor=gestoresCompletos.getJSONObject(i).getString("idgestor");
                }
            }

            if(idGestor!=null){
                respuesta=this.enviarAsignarClientesSCL(clientes,idGestor,tipoCarteraTKM);
            }
            else{
                respuesta.setCode(0);
                respuesta.setMessage("Valida el nombre del Gestor");
                respuesta.setError(false);
            }
        }
        else{
            respuesta.setCode(0);
            respuesta.setMessage("Favor de volver a intentar");
            respuesta.setError(false);

        }

        return respuesta;
    }


    private RestResponse<String> enviarAsignarClientesSCL(GestoresModel clientes,String idGestor,String tipoCarteraTKM) {
        RestResponse<String> respuesta=new RestResponse<>();
        for(int i=0;i<clientes.getCuClientes().toArray().length;i++){
            this.enviarAsignarClientesSCLFinal(clientes,clientes.getCuClientes().get(i),idGestor,tipoCarteraTKM);
        }

        respuesta.setCode(1);
        respuesta.setMessage("Se asignador clientes");
        respuesta.setError(false);
        respuesta.setData("Se asignaron "+clientes.getCuClientes().toArray()+" al gestor: "+clientes.getNombreGestor());
        return  respuesta;
    }

    private RestResponse<String> enviarAsignarClientesSCLFinal(GestoresModel cokkie,String cu, String idGestor,String tipoCarteraTKM){
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setMessage("No se logro asignar el cliente");
        respuesta.setData("No se logro asignar el cliente");
        int intento=1;

        RestResponse<JSONObject> asignar=new RestResponse<>();
        do{
            int intentoLog=intento;
            LOGGER.log(Level.INFO, () -> "REQUEST Asignar clientes al gestor: "+idGestor+" ,CU: "+cu+" , intento: "+intentoLog);
            asignar=mantGesLog.asignarClientesGes(cokkie,cu,idGestor,tipoCarteraTKM);
            RestResponse<JSONObject> finalLog = asignar;
            LOGGER.log(Level.INFO, () -> "RESPONSE Asignar clientes al gestor: "+ finalLog);
            intento++;
        }
        while(asignar.getCode()!=1&&intento!=200);

        if(asignar.getCode()==1){
            respuesta.setCode(1);
            respuesta.setMessage("Cliente Asignado");
            respuesta.setData("Cliente Asignado");
        }

        return respuesta;
    }

    public RestResponse<String> asignarClientesSCLGestores(String clientes,String tipoCarteraTKM) {

        RestResponse<String> respuesta=new RestResponse<>();
        JSONObject json=new JSONObject(clientes);
        JSONArray planes=json.getJSONArray("cuentasGestores");
        int noAsignadas=0;
        int asignadas=0;
        LOGGER.log(Level.INFO, () -> "REQUEST asignarClientesSCLGestores: "+ clientes);
        for(int i=0;i<planes.length();i++){
            GestoresModel coo=new GestoresModel();
            coo.setCokkie(json.getString("cookie"));
            String cu=planes.getJSONObject(i).getString("clienteUnico");
            String idGes=planes.getJSONObject(i).getString("idGestorSCL");
            RestResponse<String> resp=this.enviarAsignarClientesSCLFinal(coo,cu,idGes,tipoCarteraTKM);
            if(resp.getCode()==1){
                PromesasTKMLogic promesasLog=new PromesasTKMLogic();
                PromesasModel promesaAsignada=new PromesasModel();
                promesaAsignada.setAsignado(1);
                promesaAsignada.setEdito(String.valueOf(json.getInt(("idAdminTKM"))));
                promesaAsignada.setId(planes.getJSONObject(i).getInt("idPromesaTKM"));
                promesasLog.actualizarPromesasAsignar(promesaAsignada);
                asignadas++;
            }
            else{
                noAsignadas++;
            }
        }

        respuesta.setCode(1);
        respuesta.setMessage("Se asignaron: "+asignadas+" ,No asignaron: "+noAsignadas+" ,TOTAL="+planes.length());
        respuesta.setData("Se asignaron: "+asignadas+" ,No asignaron: "+noAsignadas+" ,TOTAL="+planes.length());
        LOGGER.log(Level.INFO, () -> "RESPONSE asignarClientesSCLGestores: "+ respuesta);
        return respuesta;
    }

    public RestResponse<ArrayList<GestoresModel>> consultarGestoresTKM(){
        return gestoresDAO.consultarGestoresTKM();
    }

    public RestResponse<String> insertarGestoresTKM(GestoresModel empleado){
        return gestoresDAO.insertarGestoresTKM(empleado);
    }

    public RestResponse<String> actualizarGestoresTKM(GestoresModel empleado){
        return gestoresDAO.actualizarGestoresTKM(empleado);
    }

    public RestResponse<String> eliminarGestoresTKM(String idEmpleado){
        return gestoresDAO.eliminarGestoresTKM(idEmpleado);
    }

    public RestResponse<JSONObject>consultarGestionesCU(String datos){
        RestResponse<JSONObject> respuesta=new RestResponse<>();
        JSONObject json=new JSONObject(datos);
        String cu=json.getString("cu");
        String cook=(String) json.get("cookie");
        RestResponse<JSONObject> resp=new RestResponse<>();
        int intento=1;
        try {
            do {
                MantenimientoGestoresLogic mantLo=new MantenimientoGestoresLogic();
                resp = mantLo.consultarGestiones(cook, cu);
            }
            while (resp.getCode() != 1 && intento != 200);

            respuesta = resp;
        }catch (Exception e){
            String exc=""+e;
        }
        return respuesta;
    }


}

