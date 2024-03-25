package com.spring.services.pagos.logic;

import com.spring.services.pagos.model.datosEntradaPagosPlanes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.ArrayList;

@Component
public class SetearDatosPagoPlanesLog {

    public SetearDatosPagoPlanesLog() {
        //Vacio
    }

    public String prepararCU(String cu){
        String[] cuSeparado = cu.split("-");
        String[] separaPaisCanal = cuSeparado[0].split("");
        String idPais = null;
        String idCanal = null;
        String idSucursal = cuSeparado[1];
        String folio = null;
        if (cuSeparado.length == 4) {
            folio = cuSeparado[2] + "" + cuSeparado[3];
        }
        else {
            folio = cuSeparado[2];
        }

        if (separaPaisCanal.length == 4) {
            idPais = separaPaisCanal[0]+separaPaisCanal[1];
            idCanal = separaPaisCanal[2] + separaPaisCanal[3];

        }
        else if(separaPaisCanal.length == 3){
            idPais = "0"+separaPaisCanal[0];
            idCanal = separaPaisCanal[1] + separaPaisCanal[2];
        }
        else {
            idPais = "0"+separaPaisCanal[0];
            idCanal = "0"+separaPaisCanal[1];
        }

        return idPais + "-" + idCanal + "-" + idSucursal + "-" + folio;
    }

    public String obtenerActivo(JSONObject planesCU){
        JSONObject entrada=planesCU;
        String respuesta="SN";

        JSONArray planes=entrada.getJSONArray("resultado");
        if(planes.length()!=0) {
            String statusInicial = planes.getJSONObject(0).getString("status");
            if ("CUMPLIDO".equals(statusInicial)) {
                respuesta = planes.getJSONObject(0).getString("idPlan") + "|" + statusInicial;
            } else {
                for (int i = 0; i < planes.length(); i++) {
                    JSONObject plan = (JSONObject) planes.get(i);
                    if ("ACTIVO".equals(plan.get("status"))) {
                        String idPlan = (String) plan.get("idPlan");
                        String status = (String) plan.get("status");

                        respuesta = idPlan + "|" + status;
                    }
                }
            }
        }
        return respuesta;
    }

    public String generarExcelPagos(ArrayList<datosEntradaPagosPlanes> pagos){
        String respuesta=null;

        try{

            SXSSFWorkbook wb = new SXSSFWorkbook(1);
            SXSSFSheet sheet = wb.createSheet();
            Row nRow = null;
            Cell nCell = null;
            Object[] objHead = {
                    "CLIENTE_UNICO",
                    "PLAN_ACTIVO",
                    "STATUS_PLAN",
                    "MONTO_DE_PAGO",
                    "MONTO_DEPOSITADO",
                    "STATUS_PAGO",
                    "DIA(S)_PAGO",
                    "GESTOR"
            };
            nRow = sheet.createRow(0);
            for (int i = 0; i < objHead.length; i++) {
                nCell = nRow.createCell(i);
                nCell.setCellValue(objHead[i].toString());
            }
            int pageRowNo = 1;
            for(int j=0; j<pagos.toArray().length;j++){
                String statusPago="PAGADO";
                if("0".equals(pagos.get(j).getMontoDepositado())){
                    statusPago="NO PAGADO";
                }

                Object[] objExcelBody = new Object[8];
                objExcelBody[0]=pagos.get(j).getCuFinal();
                objExcelBody[1]=pagos.get(j).getIdPlanActivo();
                objExcelBody[2]=pagos.get(j).getStatus();
                objExcelBody[3]=pagos.get(j).getMontoPrometido();
                objExcelBody[4]=pagos.get(j).getMontoDepositado();
                objExcelBody[5]=statusPago;
                objExcelBody[6]=pagos.get(j).getDiaPago();
                objExcelBody[7]=pagos.get(j).getGestor();

                nRow = sheet.createRow(pageRowNo++);

                for (int h = 0; h < objExcelBody.length; h++) {
                    nCell = nRow.createCell(h);
                    nCell.setCellValue(objExcelBody[h].toString());
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\cobazteca\\Downloads\\Pagos.xlsx");
            wb.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            wb.dispose();

            respuesta="Archivo generado correctamente";
        }
        catch (Exception e){
            respuesta="Algo ocurrio "+e;
        }
        return respuesta;
    }
}
