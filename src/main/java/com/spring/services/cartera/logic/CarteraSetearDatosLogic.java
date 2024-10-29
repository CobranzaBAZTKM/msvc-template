package com.spring.services.cartera.logic;

import com.spring.services.cartera.model.ClienteModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.ArrayList;


@Component
public class CarteraSetearDatosLogic {

    public CarteraSetearDatosLogic() {
        //Vacio
    }

    public ArrayList<ClienteModel> setearDatos(ArrayList<String> datosSS,Integer segmento,int tipoCarteraTKM){
        ArrayList<ClienteModel> respuesta=new ArrayList<>();

        for(int i=0;i<datosSS.toArray().length;i++){
            String[] separarDatosCliente =((String)datosSS.get(i)).split("\\|");

            ClienteModel cliente=new ClienteModel();
            cliente.setCLIENTE_UNICO(separarDatosCliente[0]);
            cliente.setNOMBRE_CTE(separarDatosCliente[1]);
            cliente.setRFC_CTE(separarDatosCliente[2]);
            cliente.setGENERO_CLIENTE(separarDatosCliente[3]);
            cliente.setEDAD_CLIENTE(separarDatosCliente[4]);
            cliente.setOCUPACION(separarDatosCliente[5]);
            cliente.setCORREO_ELECTRONICO(separarDatosCliente[6]);
            cliente.setDIRECCION_CTE(separarDatosCliente[7]);
            cliente.setNUM_EXT_CTE(separarDatosCliente[8]);
            cliente.setNUM_INT_CTE(separarDatosCliente[9]);
            cliente.setCP_CTE(separarDatosCliente[10]);
            cliente.setCOLONIA_CTE(separarDatosCliente[11]);
            cliente.setPOBLACION_CTE(separarDatosCliente[12]);
            cliente.setESTADO_CTE(separarDatosCliente[13]);
            cliente.setTERRITORIO(separarDatosCliente[14]);
            cliente.setTERRITORIAL(separarDatosCliente[15]);
            cliente.setZONA(separarDatosCliente[16]);
            cliente.setZONAL(separarDatosCliente[17]);
            cliente.setNOMBRE_DESPACHO(separarDatosCliente[18]);
            cliente.setGERENCIA(separarDatosCliente[19]);
            cliente.setFECHA_ASIGNACION(separarDatosCliente[20]);
            cliente.setDIAS_ASIGNACION(separarDatosCliente[21]);
            cliente.setREFERENCIAS_DOMICILIO(separarDatosCliente[22]);
            cliente.setCLASIFICACION_CTE(separarDatosCliente[23]);
            cliente.setDIQUE(separarDatosCliente[24]);
            cliente.setATRASO_MAXIMO(separarDatosCliente[25]);
            cliente.setDIAS_ATRASO(Integer.parseInt(separarDatosCliente[26]));
            cliente.setSALDO(separarDatosCliente[27]);
            cliente.setMORATORIOS(separarDatosCliente[28]);
            cliente.setSALDO_TOTAL(Float.parseFloat(separarDatosCliente[29]));
            cliente.setSALDO_ATRASADO(separarDatosCliente[30]);
            cliente.setSALDO_REQUERIDO(separarDatosCliente[31]);
            cliente.setPAGO_PUNTUAL(separarDatosCliente[32]);
            cliente.setPAGO_NORMAL(separarDatosCliente[33]);
            cliente.setPRODUCTO(separarDatosCliente[34]);
            cliente.setFECHA_ULTIMO_PAGO(separarDatosCliente[35]);
            cliente.setIMP_ULTIMO_PAGO(separarDatosCliente[36]);
            cliente.setCALLE_EMPLEO(separarDatosCliente[37]);
            cliente.setNUM_EXT_EMPLEO(separarDatosCliente[38]);
            cliente.setNUM_INT_EMPLEO(separarDatosCliente[39]);
            cliente.setCOLONIA_EMPLEO(separarDatosCliente[40]);
            cliente.setPOBLACION_EMPLEO(separarDatosCliente[41]);
            cliente.setESTADO_EMPLEO(separarDatosCliente[42]);
            cliente.setNOMBRE_AVAL(separarDatosCliente[43]);
            cliente.setTEL_AVAL(separarDatosCliente[44]);
            cliente.setCALLE_AVAL(separarDatosCliente[45]);
            cliente.setNUM_EXT_AVAL(separarDatosCliente[46]);
            cliente.setCOLONIA_AVAL(separarDatosCliente[47]);
            cliente.setCP_AVAL(separarDatosCliente[48]);
            cliente.setPOBLACION_AVAL(separarDatosCliente[49]);
            cliente.setESTADO_AVAL(separarDatosCliente[50]);
            cliente.setCLIENTE_GRUPAL(separarDatosCliente[51]);
            cliente.setFIPAISGEO(separarDatosCliente[52]);
            cliente.setFICUADRANTEGEO(separarDatosCliente[53]);
            cliente.setFIZONAGEO(separarDatosCliente[54]);
            cliente.setFIDIAPAGO(separarDatosCliente[55]);
            cliente.setTELEFONO1(separarDatosCliente[56]);
            cliente.setTELEFONO2(separarDatosCliente[57]);
            cliente.setTELEFONO3(separarDatosCliente[58]);
            cliente.setTELEFONO4(separarDatosCliente[59]);
            cliente.setTIPOTEL1(separarDatosCliente[60]);
            cliente.setTIPOTEL2(separarDatosCliente[61]);
            cliente.setTIPOTEL3(separarDatosCliente[62]);
            cliente.setTIPOTEL4(separarDatosCliente[63]);
            cliente.setLATITUD(separarDatosCliente[64]);
            cliente.setLONGITUD(separarDatosCliente[65]);
            cliente.setDESPACHO_GESTIONO(separarDatosCliente[66]);
            cliente.setULTIMA_GESTION(separarDatosCliente[67]);
            cliente.setGESTION_DESC(separarDatosCliente[68]);
            cliente.setCAMPANIA_RELAMPAGO(separarDatosCliente[69]);
            cliente.setCAMPANIA(separarDatosCliente[70]);
            cliente.setTIPO_CARTERA(separarDatosCliente[71]);
            cliente.setID_GRUPO(separarDatosCliente[72]);
            cliente.setGRUPO_MAZ(separarDatosCliente[73]);
            cliente.setCLAVE_SPEI(separarDatosCliente[74]);
            cliente.setPAGOS_CLIENTE(separarDatosCliente[75]);
            cliente.setMONTO_PAGOS(separarDatosCliente[76]);
            cliente.setGESTORES(separarDatosCliente[77]);
            cliente.setFOLIO_PLAN(separarDatosCliente[78]);
            cliente.setSEGMENTO_GENERACION(separarDatosCliente[79]);
            cliente.setESTATUS_PLAN(separarDatosCliente[80]);
            cliente.setSEMANAS_ATRASO(separarDatosCliente[81]);
            cliente.setATRASO(separarDatosCliente[82]);
            cliente.setGENERACION_PLAN(separarDatosCliente[83]);
            cliente.setCANCELACION_CUMPLIMIENTO_PLAN(separarDatosCliente[84]);
            cliente.setULTIMO_ESTATUS(separarDatosCliente[85]);
            cliente.setEMPLEADO(separarDatosCliente[86]);
            cliente.setCANAL(separarDatosCliente[87]);
            cliente.setABONO_SEMANAL(separarDatosCliente[88]);
            cliente.setPLAZO(separarDatosCliente[89]);
            cliente.setMONTO_ABONADO(separarDatosCliente[90]);
            cliente.setMONTO_PLAN(separarDatosCliente[91]);
            cliente.setENGANCHE(separarDatosCliente[92]);
            cliente.setPAGOS_RECIBIDOS(separarDatosCliente[93]);
            cliente.setSALDO_ANTES_DEL_PLAN(separarDatosCliente[94]);
            cliente.setSALDO_ATRASADO_ANTES_PLAN(separarDatosCliente[95]);
            cliente.setMORATORIOS_ANTES_PLAN(separarDatosCliente[96]);
            cliente.setESTATUS_PROMESA_PAGO(separarDatosCliente[97]);
            cliente.setMONTO_PROMESA_PAGO(separarDatosCliente[98]);
            cliente.setSEGMENTO(segmento);

            switch (tipoCarteraTKM){
                case 1:
                    cliente.setTIPOCARTERATKM("Normalidad");
                    break;
                case 2:
                    cliente.setTIPOCARTERATKM("VIP");
                    break;
                case 3:
                    cliente.setTIPOCARTERATKM("Territorios");
                    break;
                default:
                    //Vacio
                    break;
            }

            respuesta.add(cliente);

        }

        return respuesta;
    }

    public ArrayList<ClienteModel> setearDatosSegTipo(ArrayList<ClienteModel> datosSS, String tipo) {
        ArrayList<ClienteModel> respuesta = new ArrayList<>();
        for(int i=0;i<datosSS.toArray().length;i++){
            if(tipo.equals(datosSS.get(i).getCAMPANIA()) || tipo.equals(datosSS.get(i).getPRODUCTO())){
                respuesta.add(this.setearCliente(datosSS.get(i)));
            }
        }

        return respuesta;
    }

    public String generarExcelCartera(ArrayList<ClienteModel> clientes, String nombreDocumento){
        String respuesta=null;

        try{
            SXSSFWorkbook wb = new SXSSFWorkbook(1);
            SXSSFSheet sheet = wb.createSheet();
            Row nRow = null;
            Cell nCell = null;
            Object[] objHead = {
                    "CLIENTE_UNICO",
                    "NOMBRE_CTE",
                    "RFC_CTE",
                    "GENERO_CLIENTE",
                    "EDAD_CLIENTE",
                    "OCUPACION",
                    "CORREO_ELECTRONICO",
                    "DIRECCION_CTE",
                    "NUM_EXT_CTE",
                    "NUM_INT_CTE",
                    "CP_CTE",
                    "COLONIA_CTE",
                    "POBLACION_CTE",
                    "ESTADO_CTE",
                    "TERRITORIO",
                    "TERRITORIAL",
                    "ZONA",
                    "ZONAL",
                    "NOMBRE_DESPACHO",
                    "GERENCIA",
                    "FECHA_ASIGNACION",
                    "DIAS_ASIGNACION",
                    "REFERENCIAS_DOMICILIO",
                    "CLASIFICACION_CTE",
                    "DIQUE",
                    "ATRASO_MAXIMO",
                    "DIAS_ATRASO",
                    "SALDO",
                    "MORATORIOS",
                    "SALDO_TOTAL",
                    "SALDO_ATRASADO",
                    "SALDO_REQUERIDO",
                    "PAGO_PUNTUAL",
                    "PAGO_NORMAL",
                    "PRODUCTO",
                    "FECHA_ULTIMO_PAGO",
                    "IMP_ULTIMO_PAGO",
                    "CALLE_EMPLEO",
                    "NUM_EXT_EMPLEO",
                    "NUM_INT_EMPLEO",
                    "COLONIA_EMPLEO",
                    "POBLACION_EMPLEO",
                    "ESTADO_EMPLEO",
                    "NOMBRE_AVAL",
                    "TEL_AVAL",
                    "CALLE_AVAL",
                    "NUM_EXT_AVAL",
                    "COLONIA_AVAL",
                    "CP_AVAL",
                    "POBLACION_AVAL",
                    "ESTADO_AVAL",
                    "CLIENTE_GRUPAL",
                    "FIPAISGEO",
                    "FICUADRANTEGEO",
                    "FIZONAGEO",
                    "FIDIAPAGO",
                    "TELEFONO1",
                    "TELEFONO2",
                    "TELEFONO3",
                    "TELEFONO4",
                    "TIPOTEL1",
                    "TIPOTEL2",
                    "TIPOTEL3",
                    "TIPOTEL4",
                    "LATITUD",
                    "LONGITUD",
                    "DESPACHO_GESTIONO",
                    "ULTIMA_GESTION",
                    "GESTION_DESC",
                    "CAMPANIA_RELAMPAGO",
                    "CAMPANIA",
                    "TIPO_CARTERA",
                    "ID_GRUPO",
                    "GRUPO_MAZ",
                    "CLAVE_SPEI",
                    "PAGOS_CLIENTE",
                    "MONTO_PAGOS",
                    "GESTORES",
                    "FOLIO_PLAN",
                    "SEGMENTO_GENERACION",
                    "ESTATUS_PLAN",
                    "SEMANAS_ATRASO",
                    "ATRASO",
                    "GENERACION_PLAN",
                    "CANCELACION_CUMPLIMIENTO_PLAN",
                    "ULTIMO_ESTATUS",
                    "EMPLEADO",
                    "CANAL",
                    "ABONO_SEMANAL",
                    "PLAZO",
                    "MONTO_ABONADO",
                    "MONTO_PLAN",
                    "ENGANCHE",
                    "PAGOS_RECIBIDOS",
                    "SALDO_ANTES_DEL_PLAN",
                    "SALDO_ATRASADO_ANTES_PLAN",
                    "MORATORIOS_ANTES_PLAN",
                    "ESTATUS_PROMESA_PAGO",
                    "MONTO_PROMESA_PAGO",
                    "SEGMENTO"
            };
            nRow = sheet.createRow(0);
            for (int i = 0; i < objHead.length; i++) {
                nCell = nRow.createCell(i);
                nCell.setCellValue(objHead[i].toString());
            }

            int pageRowNo = 1;
            for(int j=0; j<clientes.toArray().length;j++){
                Object[] objExcelBody = new Object[100];
                objExcelBody[0]=clientes.get(j).getCLIENTE_UNICO();
                objExcelBody[1]=clientes.get(j).getNOMBRE_CTE();
                objExcelBody[2]=clientes.get(j).getRFC_CTE();
                objExcelBody[3]=clientes.get(j).getGENERO_CLIENTE();
                objExcelBody[4]=clientes.get(j).getEDAD_CLIENTE();
                objExcelBody[5]=clientes.get(j).getOCUPACION();
                objExcelBody[6]=clientes.get(j).getCORREO_ELECTRONICO();
                objExcelBody[7]=clientes.get(j).getDIRECCION_CTE();
                objExcelBody[8]=clientes.get(j).getNUM_EXT_CTE();
                objExcelBody[9]=clientes.get(j).getNUM_INT_CTE();
                objExcelBody[10]=clientes.get(j).getCP_CTE();
                objExcelBody[11]=clientes.get(j).getCOLONIA_CTE();
                objExcelBody[12]=clientes.get(j).getPOBLACION_CTE();
                objExcelBody[13]=clientes.get(j).getESTADO_CTE();
                objExcelBody[14]=clientes.get(j).getTERRITORIO();
                objExcelBody[15]=clientes.get(j).getTERRITORIAL();
                objExcelBody[16]=clientes.get(j).getZONA();
                objExcelBody[17]=clientes.get(j).getZONAL();
                objExcelBody[18]=clientes.get(j).getNOMBRE_DESPACHO();
                objExcelBody[19]=clientes.get(j).getGERENCIA();
                objExcelBody[20]=clientes.get(j).getFECHA_ASIGNACION();
                objExcelBody[21]=clientes.get(j).getDIAS_ASIGNACION();
                objExcelBody[22]=clientes.get(j).getREFERENCIAS_DOMICILIO();
                objExcelBody[23]=clientes.get(j).getCLASIFICACION_CTE();
                objExcelBody[24]=clientes.get(j).getDIQUE();
                objExcelBody[25]=clientes.get(j).getATRASO_MAXIMO();
                objExcelBody[26]=clientes.get(j).getDIAS_ATRASO();
                objExcelBody[27]=clientes.get(j).getSALDO();
                objExcelBody[28]=clientes.get(j).getMORATORIOS();
                objExcelBody[29]=clientes.get(j).getSALDO_TOTAL();
                objExcelBody[30]=clientes.get(j).getSALDO_ATRASADO();
                objExcelBody[31]=clientes.get(j).getSALDO_REQUERIDO();
                objExcelBody[32]=clientes.get(j).getPAGO_PUNTUAL();
                objExcelBody[33]=clientes.get(j).getPAGO_NORMAL();
                objExcelBody[34]=clientes.get(j).getPRODUCTO();
                objExcelBody[35]=clientes.get(j).getFECHA_ULTIMO_PAGO();
                objExcelBody[36]=clientes.get(j).getIMP_ULTIMO_PAGO();
                objExcelBody[37]=clientes.get(j).getCALLE_EMPLEO();
                objExcelBody[38]=clientes.get(j).getNUM_EXT_EMPLEO();
                objExcelBody[39]=clientes.get(j).getNUM_INT_EMPLEO();
                objExcelBody[40]=clientes.get(j).getCOLONIA_EMPLEO();
                objExcelBody[41]=clientes.get(j).getPOBLACION_EMPLEO();
                objExcelBody[42]=clientes.get(j).getESTADO_EMPLEO();
                objExcelBody[43]=clientes.get(j).getNOMBRE_AVAL();
                objExcelBody[44]=clientes.get(j).getTEL_AVAL();
                objExcelBody[45]=clientes.get(j).getCALLE_AVAL();
                objExcelBody[46]=clientes.get(j).getNUM_EXT_AVAL();
                objExcelBody[47]=clientes.get(j).getCOLONIA_AVAL();
                objExcelBody[48]=clientes.get(j).getCP_AVAL();
                objExcelBody[49]=clientes.get(j).getPOBLACION_AVAL();
                objExcelBody[50]=clientes.get(j).getESTADO_AVAL();
                objExcelBody[51]=clientes.get(j).getCLIENTE_GRUPAL();
                objExcelBody[52]=clientes.get(j).getFIPAISGEO();
                objExcelBody[53]=clientes.get(j).getFICUADRANTEGEO();
                objExcelBody[54]=clientes.get(j).getFIZONAGEO();
                objExcelBody[55]=clientes.get(j).getFIDIAPAGO();
                objExcelBody[56]=clientes.get(j).getTELEFONO1();
                objExcelBody[57]=clientes.get(j).getTELEFONO2();
                objExcelBody[58]=clientes.get(j).getTELEFONO3();
                objExcelBody[59]=clientes.get(j).getTELEFONO4();
                objExcelBody[60]=clientes.get(j).getTIPOTEL1();
                objExcelBody[61]=clientes.get(j).getTIPOTEL2();
                objExcelBody[62]=clientes.get(j).getTIPOTEL3();
                objExcelBody[63]=clientes.get(j).getTIPOTEL4();
                objExcelBody[64]=clientes.get(j).getLATITUD();
                objExcelBody[65]=clientes.get(j).getLONGITUD();
                objExcelBody[66]=clientes.get(j).getDESPACHO_GESTIONO();
                objExcelBody[67]=clientes.get(j).getULTIMA_GESTION();
                objExcelBody[68]=clientes.get(j).getGESTION_DESC();
                objExcelBody[69]=clientes.get(j).getCAMPANIA_RELAMPAGO();
                objExcelBody[70]=clientes.get(j).getCAMPANIA();
                objExcelBody[71]=clientes.get(j).getTIPO_CARTERA();
                objExcelBody[72]=clientes.get(j).getID_GRUPO();
                objExcelBody[73]=clientes.get(j).getGRUPO_MAZ();
                objExcelBody[74]=clientes.get(j).getCLAVE_SPEI();
                objExcelBody[75]=clientes.get(j).getPAGOS_CLIENTE();
                objExcelBody[76]=clientes.get(j).getMONTO_PAGOS();
                objExcelBody[77]=clientes.get(j).getGESTORES();
                objExcelBody[78]=clientes.get(j).getFOLIO_PLAN();
                objExcelBody[79]=clientes.get(j).getSEGMENTO_GENERACION();
                objExcelBody[80]=clientes.get(j).getESTATUS_PLAN();
                objExcelBody[81]=clientes.get(j).getSEMANAS_ATRASO();
                objExcelBody[82]=clientes.get(j).getATRASO();
                objExcelBody[83]=clientes.get(j).getGENERACION_PLAN();
                objExcelBody[84]=clientes.get(j).getCANCELACION_CUMPLIMIENTO_PLAN();
                objExcelBody[85]=clientes.get(j).getULTIMO_ESTATUS();
                objExcelBody[86]=clientes.get(j).getEMPLEADO();
                objExcelBody[87]=clientes.get(j).getCANAL();
                objExcelBody[88]=clientes.get(j).getABONO_SEMANAL();
                objExcelBody[89]=clientes.get(j).getPLAZO();
                objExcelBody[90]=clientes.get(j).getMONTO_ABONADO();
                objExcelBody[91]=clientes.get(j).getMONTO_PLAN();
                objExcelBody[92]=clientes.get(j).getENGANCHE();
                objExcelBody[93]=clientes.get(j).getPAGOS_RECIBIDOS();
                objExcelBody[94]=clientes.get(j).getSALDO_ANTES_DEL_PLAN();
                objExcelBody[95]=clientes.get(j).getSALDO_ATRASADO_ANTES_PLAN();
                objExcelBody[96]=clientes.get(j).getMORATORIOS_ANTES_PLAN();
                objExcelBody[97]=clientes.get(j).getESTATUS_PROMESA_PAGO();
                objExcelBody[98]=clientes.get(j).getMONTO_PROMESA_PAGO();
                objExcelBody[99]=clientes.get(j).getSEGMENTO();

                nRow = sheet.createRow(pageRowNo++);

                for (int h = 0; h < objExcelBody.length; h++) {
                    nCell = nRow.createCell(h);
                    nCell.setCellValue(objExcelBody[h].toString());
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(nombreDocumento);
//            ("C:\\Users\\cobazteca\\Downloads\\segmento5.xlsx");
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



    public String generarExcelSMS(ArrayList<ClienteModel> clientes, String nombreDocumento, String mensajesSMS){
        String respuesta=null;
        try{
            SXSSFWorkbook wb = new SXSSFWorkbook(1);
            SXSSFSheet sheet = wb.createSheet();
            Row nRow = null;
            Cell nCell = null;
            Object[] objHead = {
                    "NUMERO",
                    "MENSAJE"
            };
            nRow = sheet.createRow(0);
            for (int i = 0; i < objHead.length; i++) {
                nCell = nRow.createCell(i);
                nCell.setCellValue(objHead[i].toString());
            }

            int pageRowNo = 1;
            for(int j=0; j<clientes.toArray().length;j++){
                String numero=null;
                if(!"".equals(clientes.get(j).getTELEFONO1())&&clientes.get(j).getTELEFONO1()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO1())){
                    numero=clientes.get(j).getTELEFONO1();
                }
                else if(!"".equals(clientes.get(j).getTELEFONO2())&&clientes.get(j).getTELEFONO2()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO2())){
                    numero=clientes.get(j).getTELEFONO2();
                }
                else if(!"".equals(clientes.get(j).getTELEFONO3())&&clientes.get(j).getTELEFONO3()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO3())){
                    numero=clientes.get(j).getTELEFONO3();
                }
                else if(!"".equals(clientes.get(j).getTELEFONO4())&&clientes.get(j).getTELEFONO4()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO4())){
                    numero=clientes.get(j).getTELEFONO4();
                }
                else{
                    numero="";
                }

                if(!"".equals(numero)){
                    Object[] objExcelBody = new Object[2];
                    objExcelBody[0]=numero;
                    objExcelBody[1]=mensajesSMS;
                    nRow = sheet.createRow(pageRowNo++);
                    for (int h = 0; h < objExcelBody.length; h++) {
                        nCell = nRow.createCell(h);
                        nCell.setCellValue(objExcelBody[h].toString());
                    }

                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(nombreDocumento);
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

    public ArrayList<ClienteModel> discriminacionPNITC(ArrayList<ClienteModel> datosSS){
        ArrayList<ClienteModel> respuesta = new ArrayList<>();
        for(int i=0;i<datosSS.toArray().length;i++){
            if(!"Normalidad".equals(datosSS.get(i).getCAMPANIA())&& !"Preventa".equals(datosSS.get(i).getCAMPANIA())&&!"ITALIKA".equals(datosSS.get(i).getPRODUCTO())
                &&!"CDT".equals(datosSS.get(i).getPRODUCTO())&&!"TOR".equals(datosSS.get(i).getPRODUCTO())&&!"MAZ".equals(datosSS.get(i).getPRODUCTO())){
                respuesta.add(this.setearCliente(datosSS.get(i)));
            }
        }
        return respuesta;
    }

    public ArrayList<ClienteModel> clientesSaldos(ArrayList<ClienteModel> datosSS, String tipo) {
        ArrayList<ClienteModel> respuesta = new ArrayList<>();
        for(int i=0;i<datosSS.toArray().length;i++){
            if("ALTO".equals(tipo) && datosSS.get(i).getSALDO_TOTAL()>=5000){
                respuesta.add(this.setearCliente(datosSS.get(i)));
            }
            else if("BAJO".equals(tipo) && datosSS.get(i).getSALDO_TOTAL() <= 4999){
                respuesta.add(this.setearCliente(datosSS.get(i)));
            }
            else if("MEDIO".equals(tipo) && datosSS.get(i).getSALDO_TOTAL()>1 && datosSS.get(i).getSALDO_TOTAL()<3){
                respuesta.add(this.setearCliente(datosSS.get(i)));
            }
        }
        return respuesta;
    }

    public ClienteModel setearCliente(ClienteModel datosSS){
        ClienteModel cliente=new ClienteModel();
        cliente.setCLIENTE_UNICO(datosSS.getCLIENTE_UNICO());
        cliente.setNOMBRE_CTE(datosSS.getNOMBRE_CTE());
        cliente.setRFC_CTE(datosSS.getRFC_CTE());
        cliente.setGENERO_CLIENTE(datosSS.getGENERO_CLIENTE());
        cliente.setEDAD_CLIENTE(datosSS.getEDAD_CLIENTE());
        cliente.setOCUPACION(datosSS.getOCUPACION());
        cliente.setCORREO_ELECTRONICO(datosSS.getCORREO_ELECTRONICO());
        cliente.setDIRECCION_CTE(datosSS.getDIRECCION_CTE());
        cliente.setNUM_EXT_CTE(datosSS.getNUM_EXT_CTE());
        cliente.setNUM_INT_CTE(datosSS.getNUM_INT_CTE());
        cliente.setCP_CTE(datosSS.getCP_CTE());
        cliente.setCOLONIA_CTE(datosSS.getCOLONIA_CTE());
        cliente.setPOBLACION_CTE(datosSS.getPOBLACION_CTE());
        cliente.setESTADO_CTE(datosSS.getESTADO_CTE());
        cliente.setTERRITORIO(datosSS.getTERRITORIO());
        cliente.setTERRITORIAL(datosSS.getTERRITORIAL());
        cliente.setZONA(datosSS.getZONA());
        cliente.setZONAL(datosSS.getZONAL());
        cliente.setNOMBRE_DESPACHO(datosSS.getNOMBRE_DESPACHO());
        cliente.setGERENCIA(datosSS.getGERENCIA());
        cliente.setFECHA_ASIGNACION(datosSS.getFECHA_ASIGNACION());
        cliente.setDIAS_ASIGNACION(datosSS.getDIAS_ASIGNACION());
        cliente.setREFERENCIAS_DOMICILIO(datosSS.getREFERENCIAS_DOMICILIO());
        cliente.setCLASIFICACION_CTE(datosSS.getCLASIFICACION_CTE());
        cliente.setDIQUE(datosSS.getDIQUE());
        cliente.setATRASO_MAXIMO(datosSS.getATRASO_MAXIMO());
        cliente.setDIAS_ATRASO(datosSS.getDIAS_ATRASO());
        cliente.setSALDO(datosSS.getSALDO());
        cliente.setMORATORIOS(datosSS.getMORATORIOS());
        cliente.setSALDO_TOTAL(datosSS.getSALDO_TOTAL());
        cliente.setSALDO_ATRASADO(datosSS.getSALDO_ATRASADO());
        cliente.setSALDO_REQUERIDO(datosSS.getSALDO_REQUERIDO());
        cliente.setPAGO_PUNTUAL(datosSS.getPAGO_PUNTUAL());
        cliente.setPAGO_NORMAL(datosSS.getPAGO_NORMAL());
        cliente.setPRODUCTO(datosSS.getPRODUCTO());
        cliente.setFECHA_ULTIMO_PAGO(datosSS.getFECHA_ULTIMO_PAGO());
        cliente.setIMP_ULTIMO_PAGO(datosSS.getIMP_ULTIMO_PAGO());
        cliente.setCALLE_EMPLEO(datosSS.getCALLE_EMPLEO());
        cliente.setNUM_EXT_EMPLEO(datosSS.getNUM_EXT_EMPLEO());
        cliente.setNUM_INT_EMPLEO(datosSS.getNUM_INT_EMPLEO());
        cliente.setCOLONIA_EMPLEO(datosSS.getCOLONIA_EMPLEO());
        cliente.setPOBLACION_EMPLEO(datosSS.getPOBLACION_EMPLEO());
        cliente.setESTADO_EMPLEO(datosSS.getESTADO_EMPLEO());
        cliente.setNOMBRE_AVAL(datosSS.getNOMBRE_AVAL());
        cliente.setTEL_AVAL(datosSS.getTEL_AVAL());
        cliente.setCALLE_AVAL(datosSS.getCALLE_AVAL());
        cliente.setNUM_EXT_AVAL(datosSS.getNUM_EXT_AVAL());
        cliente.setCOLONIA_AVAL(datosSS.getCOLONIA_AVAL());
        cliente.setCP_AVAL(datosSS.getCP_AVAL());
        cliente.setPOBLACION_AVAL(datosSS.getPOBLACION_AVAL());
        cliente.setESTADO_AVAL(datosSS.getESTADO_AVAL());
        cliente.setCLIENTE_GRUPAL(datosSS.getCLIENTE_GRUPAL());
        cliente.setFIPAISGEO(datosSS.getFIPAISGEO());
        cliente.setFICUADRANTEGEO(datosSS.getFICUADRANTEGEO());
        cliente.setFIZONAGEO(datosSS.getFIZONAGEO());
        cliente.setFIDIAPAGO(datosSS.getFIDIAPAGO());
        cliente.setTELEFONO1(datosSS.getTELEFONO1());
        cliente.setTELEFONO2(datosSS.getTELEFONO2());
        cliente.setTELEFONO3(datosSS.getTELEFONO3());
        cliente.setTELEFONO4(datosSS.getTELEFONO4());
        cliente.setTIPOTEL1(datosSS.getTIPOTEL1());
        cliente.setTIPOTEL2(datosSS.getTIPOTEL2());
        cliente.setTIPOTEL3(datosSS.getTIPOTEL3());
        cliente.setTIPOTEL4(datosSS.getTIPOTEL4());
        cliente.setLATITUD(datosSS.getLATITUD());
        cliente.setLONGITUD(datosSS.getLONGITUD());
        cliente.setDESPACHO_GESTIONO(datosSS.getDESPACHO_GESTIONO());
        cliente.setULTIMA_GESTION(datosSS.getULTIMA_GESTION());
        cliente.setGESTION_DESC(datosSS.getGESTION_DESC());
        cliente.setCAMPANIA_RELAMPAGO(datosSS.getCAMPANIA_RELAMPAGO());
        cliente.setCAMPANIA(datosSS.getCAMPANIA());
        cliente.setTIPO_CARTERA(datosSS.getTIPO_CARTERA());
        cliente.setID_GRUPO(datosSS.getID_GRUPO());
        cliente.setGRUPO_MAZ(datosSS.getGRUPO_MAZ());
        cliente.setCLAVE_SPEI(datosSS.getCLAVE_SPEI());
        cliente.setPAGOS_CLIENTE(datosSS.getPAGOS_CLIENTE());
        cliente.setMONTO_PAGOS(datosSS.getMONTO_PAGOS());
        cliente.setGESTORES(datosSS.getGESTORES());
        cliente.setFOLIO_PLAN(datosSS.getFOLIO_PLAN());
        cliente.setSEGMENTO_GENERACION(datosSS.getSEGMENTO_GENERACION());
        cliente.setESTATUS_PLAN(datosSS.getESTATUS_PLAN());
        cliente.setSEMANAS_ATRASO(datosSS.getSEMANAS_ATRASO());
        cliente.setATRASO(datosSS.getATRASO());
        cliente.setGENERACION_PLAN(datosSS.getGENERACION_PLAN());
        cliente.setCANCELACION_CUMPLIMIENTO_PLAN(datosSS.getCANCELACION_CUMPLIMIENTO_PLAN());
        cliente.setULTIMO_ESTATUS(datosSS.getULTIMO_ESTATUS());
        cliente.setEMPLEADO(datosSS.getEMPLEADO());
        cliente.setCANAL(datosSS.getCANAL());
        cliente.setABONO_SEMANAL(datosSS.getABONO_SEMANAL());
        cliente.setPLAZO(datosSS.getPLAZO());
        cliente.setMONTO_ABONADO(datosSS.getMONTO_ABONADO());
        cliente.setMONTO_PLAN(datosSS.getMONTO_PLAN());
        cliente.setENGANCHE(datosSS.getENGANCHE());
        cliente.setPAGOS_RECIBIDOS(datosSS.getPAGOS_RECIBIDOS());
        cliente.setSALDO_ANTES_DEL_PLAN(datosSS.getSALDO_ANTES_DEL_PLAN());
        cliente.setSALDO_ATRASADO_ANTES_PLAN(datosSS.getSALDO_ATRASADO_ANTES_PLAN());
        cliente.setMORATORIOS_ANTES_PLAN(datosSS.getMORATORIOS_ANTES_PLAN());
        cliente.setESTATUS_PROMESA_PAGO(datosSS.getESTATUS_PROMESA_PAGO());
        cliente.setMONTO_PROMESA_PAGO(datosSS.getMONTO_PROMESA_PAGO());
        cliente.setSEGMENTO(datosSS.getSEGMENTO());
        return cliente;
    }

    public Double obtenerSaldoTotal(ArrayList<ClienteModel> datos){
        Double respuesta = 0.0;

        for(int i=0; i<datos.toArray().length;i++){
            respuesta=respuesta+datos.get(i).getSALDO_TOTAL();
        }

        String saldo=String.valueOf(respuesta);
        return respuesta;
    }


    public String generarExcelBlasters(ArrayList<ClienteModel> clientes, String nombreDocumento){
        String respuesta=null;
        try{
            SXSSFWorkbook wb = new SXSSFWorkbook(1);
            SXSSFSheet sheet = wb.createSheet();
            Row nRow = null;
            Cell nCell = null;
            Object[] objHead = {
                    "TELEFONO_CELULAR",
                    "NOMBRE"
            };
            nRow = sheet.createRow(0);
            for (int i = 0; i < objHead.length; i++) {
                nCell = nRow.createCell(i);
                nCell.setCellValue(objHead[i].toString());
            }

            int pageRowNo = 1;
            for(int j=0; j<clientes.toArray().length;j++){
                String numero=null;
                if(!"".equals(clientes.get(j).getTELEFONO1())&&clientes.get(j).getTELEFONO1()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO1())){
                    numero=clientes.get(j).getTELEFONO1();
                }
                else if(!"".equals(clientes.get(j).getTELEFONO2())&&clientes.get(j).getTELEFONO2()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO2())){
                    numero=clientes.get(j).getTELEFONO2();
                }
                else if(!"".equals(clientes.get(j).getTELEFONO3())&&clientes.get(j).getTELEFONO3()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO3())){
                    numero=clientes.get(j).getTELEFONO3();
                }
                else if(!"".equals(clientes.get(j).getTELEFONO4())&&clientes.get(j).getTELEFONO4()!=null&&!"N/A".equals(clientes.get(j).getTELEFONO4())){
                    numero=clientes.get(j).getTELEFONO4();
                }
                else{
                    numero="";
                }

                if(!"".equals(numero)){
                    Object[] objExcelBody = new Object[2];
                    objExcelBody[0]=numero;
                    objExcelBody[1]=clientes.get(j).getCLIENTE_UNICO();
                    nRow = sheet.createRow(pageRowNo++);
                    for (int h = 0; h < objExcelBody.length; h++) {
                        nCell = nRow.createCell(h);
                        nCell.setCellValue(objExcelBody[h].toString());
                    }

                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(nombreDocumento);
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
