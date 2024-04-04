package com.spring.services.pagos.logic;


import com.spring.services.gestores.logic.GestoresLogic;
import com.spring.services.pagos.dao.PromesasTKMDAO;
import com.spring.services.pagos.model.PromesasModel;
import com.spring.utils.RestResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class PromesasTKMLogic {
    @Autowired
    private PromesasTKMDAO gesDao;

    PromesasTKMDAO gesDao2=new PromesasTKMDAO();
//    @Autowired
//    private GestoresLogic gestLog=new GestoresLogic();


    public PromesasTKMLogic() {
        //Vacio
    }

    public RestResponse<ArrayList<PromesasModel>> consultarPromesas() {
        return gesDao2.consultarPromesas();
    }

    public RestResponse<String> insertarPromesas(PromesasModel promesa) {
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = new Date();
        String fechaFinal=fecha.format(fechaActual);
        String[] separarFecha=fechaFinal.split("/");
        Integer mesInt= Integer.parseInt(separarFecha[1])+1;
        String mesFinal="";
        if(mesInt==13){
            mesFinal="01";
        }else if(mesInt<10){
            mesFinal="0"+mesInt;
        }else{
            mesFinal=String.valueOf(mesInt);
        }
        promesa.setFechInser(separarFecha[0]+"/"+mesFinal+"/"+separarFecha[2]);

        return gesDao.insertarPromesas(promesa);
    }

    public RestResponse<String> actualizarPromesas(PromesasModel promesa) {
        return gesDao.actualizarPromesas(promesa);
    }

    public RestResponse<String> borrarPromesas(String idPromesa,String idAdmin) {
        return gesDao2.borrarPromesas(idPromesa,idAdmin);
    }

    public RestResponse<String> actualizarPromesasAsignar(PromesasModel promesa) {

        return gesDao2.actualizarPromesasAsignar(promesa);
    }

    public RestResponse<ArrayList<PromesasModel>> consultarPromesasCGestion(String cookie) {
        RestResponse<ArrayList<PromesasModel>>respuesta=new RestResponse<>();
        JSONObject json=new JSONObject(cookie);
        JSONArray cus=json.getJSONArray("promesas");
        String cokkie=json.getString("cookie");
        ArrayList<PromesasModel> promesas=new ArrayList<>();
        for(int i=0;i<cus.length();i++){
            PromesasModel promesa=new PromesasModel();
            String[] cuPreparado=cus.getJSONObject(i).getString("clienteUnico").split("-");
            String pais="0"+cuPreparado[0];
            String canal=cuPreparado[1].length()==2?cuPreparado[1]:"0"+cuPreparado[1];
            String sucursal=cuPreparado[2].length()==3?"0"+cuPreparado[2]:cuPreparado[2];
            String[] folio =cuPreparado[3].split("");
            String folio1="";
            String folio2="";
            String folioFinal="";
            String cuFinal="";

            if(cuPreparado[3].length()<=4){
                folio1=cuPreparado[3];
            }
            else{
                for(int j=0; j<4;j++){
                    folio1= folio1+folio[j];
                }
                for(int k=4; k<folio.length;k++){
                    folio2= folio2+folio[k];
                }
            }

            if(("").equals(folio2)){
                folioFinal=folio1;
            }
            else{
                folioFinal=folio1+"-"+folio2;
            }

            cuFinal=pais+canal+"-"+sucursal+"-"+folioFinal;

            String jsonConGest="{\n" +
                    "    \"cookie\":\""+cokkie+"\",\n" +
                    "    \"cu\":\""+cuFinal+"\"\n" +
                    "}";
            GestoresLogic gestLog=new GestoresLogic();
            RestResponse<JSONObject>gestiones=gestLog.consultarGestionesCU(jsonConGest);
            if(gestiones.getCode()==1){

//                JSONObject jsonGes=new JSONObject(gestiones.getData());
                JSONArray jsonArrGest=gestiones.getData().getJSONArray("respuesta");
                for(int j=0;j<jsonArrGest.length();j++){
                    if(promesa.getGestion3()==null){
                        String comentarioArray=null;

                        if(!jsonArrGest.getJSONObject(j).isNull("comentario")){
                            comentarioArray=(String) jsonArrGest.getJSONObject(j).get("comentario");
                        }
                        else{
                            comentarioArray="gestion telefonica";
                        }

                        if(!comentarioArray.contains("gestion telefonica")){
                            if(!comentarioArray.contains("GESTION TELEFONICA BLASTER Y SMS SOLICTANDO PAGO HOY")) {
                                if (promesa.getGestion1() == null) {
                                    promesa.setGestion1((String) jsonArrGest.getJSONObject(j).get("fechaGestion") + " / " + (String) jsonArrGest.getJSONObject(j).get("comentario"));
                                } else if (promesa.getGestion2() == null) {
                                    promesa.setGestion2((String) jsonArrGest.getJSONObject(j).get("fechaGestion") + " / " + (String) jsonArrGest.getJSONObject(j).get("comentario"));
                                } else {
                                    promesa.setGestion3((String) jsonArrGest.getJSONObject(j).get("fechaGestion") + " / " + (String) jsonArrGest.getJSONObject(j).get("comentario"));
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else{
                promesa.setGestion1("No se obtuvo Gestion");
                promesa.setGestion2("No se obtuvo Gestion");
                promesa.setGestion3("No se obtuvo Gestion");
            }

            promesa.setIdGestorSCL((String) cus.getJSONObject(i).get("idGestorSCL"));
            promesa.setWhatsApp((Integer) cus.getJSONObject(i).get("whatsApp"));
            promesa.setAsignado((Integer) cus.getJSONObject(i).get("asignado"));
            promesa.setIdGestorTKM((Integer) cus.getJSONObject(i).get("idGestorTKM"));
            promesa.setMontoPago((String) cus.getJSONObject(i).get("montoPago"));
            promesa.setFechaPago((String) cus.getJSONObject(i).get("fechaPago"));
            promesa.setInserto((Integer) cus.getJSONObject(i).get("inserto"));
            promesa.setNombreCliente((String) cus.getJSONObject(i).get("nombreCliente"));
            promesa.setFechaVencimientoPP((String) cus.getJSONObject(i).get("fechaVencimientoPP"));
            promesa.setNombreGestor((String) cus.getJSONObject(i).get("nombreGestor"));
            promesa.setFechaIngesoPP((String) cus.getJSONObject(i).get("fechaIngesoPP"));
            promesa.setFolio((String) cus.getJSONObject(i).get("folio"));
            promesa.setId((Integer) cus.getJSONObject(i).get("id"));
            promesa.setTelefono((String) cus.getJSONObject(i).get("telefono"));
            promesa.setTipoLlamada((String) cus.getJSONObject(i).get("tipoLlamada"));
            promesa.setClienteUnico((String) cus.getJSONObject(i).get("clienteUnico"));
            promesa.setFechInser((String) cus.getJSONObject(i).get("fechInser"));
            promesa.setPagoFinal((Integer) cus.getJSONObject(i).get("pagoFinal"));
            promesa.setTurnoGestor((String) cus.getJSONObject(i).get("turnoGestor"));

            if(!cus.getJSONObject(i).isNull("edito")){
                promesa.setEdito((String) cus.getJSONObject(i).get("edito"));
            }

            if(!cus.getJSONObject(i).isNull("observaciones")) {
                promesa.setObservaciones((String) cus.getJSONObject(i).get("observaciones"));
            }

            if(!cus.getJSONObject(i).isNull("nota")) {
                promesa.setNota((String) cus.getJSONObject(i).get("nota"));
            }


            promesas.add(promesa);



        }

        respuesta.setCode(1);
        respuesta.setMessage("Base regresada");
        respuesta.setData(promesas);
        respuesta.setError(false);


        return respuesta;
    }

    public RestResponse<String> actualizarPromesasEstPag(PromesasModel promesa) {
        return gesDao2.actualizarPromesasEstPag(promesa);
    }


}
