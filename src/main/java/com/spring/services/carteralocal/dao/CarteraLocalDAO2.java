package com.spring.services.carteralocal.dao;

import com.spring.services.cartera.model.ClienteModel;
import com.spring.services.constantes.Constantes;
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
public class CarteraLocalDAO2 {

    private static final Logger LOGGER = LogManager.getLogger("CarteraLocalDAO");
    ConexionBD conexionbd=new ConexionBD();
    public CarteraLocalDAO2() {
        //Vacio
    }

    public RestResponse<ArrayList<ClienteModel>> consultarCarteraDescarte(String tipoCarteraTKM){
        RestResponse<ArrayList<ClienteModel>> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String tablaBD=null;
        if("1".equals(tipoCarteraTKM)){
            tablaBD="carteraDescarte;";
        }
        else{
            tablaBD="carteraDescarteVIP;";
        }

        String query= Constantes.consultaBD+tablaBD;
        Statement st;
        try{
            ArrayList<ClienteModel> cuentas=new ArrayList<>();
            st=conexionbd.establecerConexion2().createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                ClienteModel cuenta=new ClienteModel();
                cuenta.setCLIENTE_UNICO(rs.getString(1));
                cuenta.setNOMBRE_CTE(rs.getString(2));
                cuenta.setRFC_CTE(rs.getString(3));
                cuenta.setGENERO_CLIENTE(rs.getString(4));
                cuenta.setEDAD_CLIENTE(rs.getString(5));
                cuenta.setOCUPACION(rs.getString(6));
                cuenta.setCORREO_ELECTRONICO(rs.getString(7));
                cuenta.setDIRECCION_CTE(rs.getString(8));
                cuenta.setNUM_EXT_CTE(rs.getString(9));
                cuenta.setNUM_INT_CTE(rs.getString(10));
                cuenta.setCP_CTE(rs.getString(11));
                cuenta.setCOLONIA_CTE(rs.getString(12));
                cuenta.setPOBLACION_CTE(rs.getString(13));
                cuenta.setESTADO_CTE(rs.getString(14));
                cuenta.setTERRITORIO(rs.getString(15));
                cuenta.setTERRITORIAL(rs.getString(16));
                cuenta.setZONA(rs.getString(17));
                cuenta.setZONAL(rs.getString(18));
                cuenta.setNOMBRE_DESPACHO(rs.getString(19));
                cuenta.setGERENCIA(rs.getString(20));
                cuenta.setFECHA_ASIGNACION(rs.getString(21));
                cuenta.setDIAS_ASIGNACION(rs.getString(22));
                cuenta.setREFERENCIAS_DOMICILIO(rs.getString(23));
                cuenta.setCLASIFICACION_CTE(rs.getString(24));
                cuenta.setDIQUE(rs.getString(25));
                cuenta.setATRASO_MAXIMO(rs.getString(26));
                cuenta.setDIAS_ATRASO(Integer.parseInt(rs.getString(27)));
                cuenta.setSALDO(rs.getString(28));
                cuenta.setMORATORIOS(rs.getString(29));
                cuenta.setSALDO_TOTAL(Float.parseFloat(rs.getString(30)));
                cuenta.setSALDO_ATRASADO(rs.getString(31));
                cuenta.setSALDO_REQUERIDO(rs.getString(32));
                cuenta.setPAGO_PUNTUAL(rs.getString(33));
                cuenta.setPAGO_NORMAL(rs.getString(34));
                cuenta.setPRODUCTO(rs.getString(35));
                cuenta.setFECHA_ULTIMO_PAGO(rs.getString(36));
                cuenta.setIMP_ULTIMO_PAGO(rs.getString(37));
                cuenta.setCALLE_EMPLEO(rs.getString(38));
                cuenta.setNUM_EXT_EMPLEO(rs.getString(39));
                cuenta.setNUM_INT_EMPLEO(rs.getString(40));
                cuenta.setCOLONIA_EMPLEO(rs.getString(41));
                cuenta.setPOBLACION_EMPLEO(rs.getString(42));
                cuenta.setESTADO_EMPLEO(rs.getString(43));
                cuenta.setNOMBRE_AVAL(rs.getString(44));
                cuenta.setTEL_AVAL(rs.getString(45));
                cuenta.setCALLE_AVAL(rs.getString(46));
                cuenta.setNUM_EXT_AVAL(rs.getString(47));
                cuenta.setCOLONIA_AVAL(rs.getString(48));
                cuenta.setCP_AVAL(rs.getString(49));
                cuenta.setPOBLACION_AVAL(rs.getString(50));
                cuenta.setESTADO_AVAL(rs.getString(51));
                cuenta.setCLIENTE_GRUPAL(rs.getString(52));
                cuenta.setFIPAISGEO(rs.getString(53));
                cuenta.setFICUADRANTEGEO(rs.getString(54));
                cuenta.setFIZONAGEO(rs.getString(55));
                cuenta.setFIDIAPAGO(rs.getString(56));
                cuenta.setTELEFONO1(rs.getString(57));
                cuenta.setTELEFONO2(rs.getString(58));
                cuenta.setTELEFONO3(rs.getString(59));
                cuenta.setTELEFONO4(rs.getString(60));
                cuenta.setTIPOTEL1(rs.getString(61));
                cuenta.setTIPOTEL2(rs.getString(62));
                cuenta.setTIPOTEL3(rs.getString(63));
                cuenta.setTIPOTEL4(rs.getString(64));
                cuenta.setLATITUD(rs.getString(65));
                cuenta.setLONGITUD(rs.getString(66));
                cuenta.setDESPACHO_GESTIONO(rs.getString(67));
                cuenta.setULTIMA_GESTION(rs.getString(68));
                cuenta.setGESTION_DESC(rs.getString(69));
                cuenta.setCAMPANIA_RELAMPAGO(rs.getString(70));
                cuenta.setCAMPANIA(rs.getString(71));
                cuenta.setTIPO_CARTERA(rs.getString(72));
                cuenta.setID_GRUPO(rs.getString(73));
                cuenta.setGRUPO_MAZ(rs.getString(74));
                cuenta.setCLAVE_SPEI(rs.getString(75));
                cuenta.setPAGOS_CLIENTE(rs.getString(76));
                cuenta.setMONTO_PAGOS(rs.getString(77));
                cuenta.setGESTORES(rs.getString(78));
                cuenta.setFOLIO_PLAN(rs.getString(79));
                cuenta.setSEGMENTO_GENERACION(rs.getString(80));
                cuenta.setESTATUS_PLAN(rs.getString(81));
                cuenta.setSEMANAS_ATRASO(rs.getString(82));
                cuenta.setATRASO(rs.getString(83));
                cuenta.setGENERACION_PLAN(rs.getString(84));
                cuenta.setCANCELACION_CUMPLIMIENTO_PLAN(rs.getString(85));
                cuenta.setULTIMO_ESTATUS(rs.getString(86));
                cuenta.setEMPLEADO(rs.getString(87));
                cuenta.setCANAL(rs.getString(88));
                cuenta.setABONO_SEMANAL(rs.getString(89));
                cuenta.setPLAZO(rs.getString(90));
                cuenta.setMONTO_ABONADO(rs.getString(91));
                cuenta.setMONTO_PLAN(rs.getString(92));
                cuenta.setENGANCHE(rs.getString(93));
                cuenta.setPAGOS_RECIBIDOS(rs.getString(94));
                cuenta.setSALDO_ANTES_DEL_PLAN(rs.getString(95));
                cuenta.setSALDO_ATRASADO_ANTES_PLAN(rs.getString(96));
                cuenta.setMORATORIOS_ANTES_PLAN(rs.getString(97));
                cuenta.setESTATUS_PROMESA_PAGO(rs.getString(98));
                cuenta.setMONTO_PROMESA_PAGO(rs.getString(99));
                cuenta.setSEGMENTO(Integer.parseInt(rs.getString(100)));
                cuenta.setTURNO(rs.getString(102));
                cuenta.setTIPOCARTERATKM(rs.getString(103));

                cuentas.add(cuenta);


            }
            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Se obtuvieron correctamente las promesas");
            respuesta.setData(cuentas);
            st.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarCarteraCompleta: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarCarteraCompleta: "+e);

        }

        return respuesta;
    }

    public RestResponse<String> insertarCarteraDescarte(ArrayList<ClienteModel> cuenta,String tipoCarteraTKM){
        RestResponse<String> respuesta=new RestResponse<>();
        respuesta.setCode(0);
        respuesta.setError(true);
        String tablaBD=null;
        if("1".equals(tipoCarteraTKM)){
            tablaBD="carteraDescarte";
        }
        else{
            tablaBD="carteraDescarteVIP";
        }

        String query= Constantes.insertarBD+tablaBD+"(" +
                "CLIENTE_UNICO," +
                "NOMBRE_CTE," +
                "RFC_CTE," +
                "GENERO_CLIENTE," +
                "EDAD_CLIENTE," +
                "OCUPACION," +
                "CORREO_ELECTRONICO," +
                "DIRECCION_CTE," +
                "NUM_EXT_CTE," +
                "NUM_INT_CTE," +
                "CP_CTE," +
                "COLONIA_CTE," +
                "POBLACION_CTE," +
                "ESTADO_CTE," +
                "TERRITORIO," +
                "TERRITORIAL," +
                "ZONA," +
                "ZONAL," +
                "NOMBRE_DESPACHO," +
                "GERENCIA," +
                "FECHA_ASIGNACION," +
                "DIAS_ASIGNACION," +
                "REFERENCIAS_DOMICILIO," +
                "CLASIFICACION_CTE," +
                "DIQUE," +
                "ATRASO_MAXIMO," +
                "DIAS_ATRASO," +
                "SALDO," +
                "MORATORIOS," +
                "SALDO_TOTAL," +
                "SALDO_ATRASADO," +
                "SALDO_REQUERIDO," +
                "PAGO_PUNTUAL," +
                "PAGO_NORMAL," +
                "PRODUCTO," +
                "FECHA_ULTIMO_PAGO," +
                "IMP_ULTIMO_PAGO," +
                "CALLE_EMPLEO," +
                "NUM_EXT_EMPLEO," +
                "NUM_INT_EMPLEO," +
                "COLONIA_EMPLEO," +
                "POBLACION_EMPLEO," +
                "ESTADO_EMPLEO," +
                "NOMBRE_AVAL," +
                "TEL_AVAL," +
                "CALLE_AVAL," +
                "NUM_EXT_AVAL," +
                "COLONIA_AVAL," +
                "CP_AVAL," +
                "POBLACION_AVAL," +
                "ESTADO_AVAL," +
                "CLIENTE_GRUPAL," +
                "FIPAISGEO," +
                "FICUADRANTEGEO," +
                "FIZONAGEO," +
                "FIDIAPAGO," +
                "TELEFONO1," +
                "TELEFONO2," +
                "TELEFONO3," +
                "TELEFONO4," +
                "TIPOTEL1," +
                "TIPOTEL2," +
                "TIPOTEL3," +
                "TIPOTEL4," +
                "LATITUD," +
                "LONGITUD," +
                "DESPACHO_GESTIONO," +
                "ULTIMA_GESTION," +
                "GESTION_DESC," +
                "CAMPANIA_RELAMPAGO," +
                "CAMPANIA," +
                "TIPO_CARTERA," +
                "ID_GRUPO," +
                "GRUPO_MAZ," +
                "CLAVE_SPEI," +
                "PAGOS_CLIENTE," +
                "MONTO_PAGOS," +
                "GESTORES," +
                "FOLIO_PLAN," +
                "SEGMENTO_GENERACION," +
                "ESTATUS_PLAN," +
                "SEMANAS_ATRASO," +
                "ATRASO," +
                "GENERACION_PLAN," +
                "CANCELACION_CUMPLIMIENTO_PLAN," +
                "ULTIMO_ESTATUS," +
                "EMPLEADO," +
                "CANAL," +
                "ABONO_SEMANAL," +
                "PLAZO," +
                "MONTO_ABONADO," +
                "MONTO_PLAN," +
                "ENGANCHE," +
                "PAGOS_RECIBIDOS," +
                "SALDO_ANTES_DEL_PLAN," +
                "SALDO_ATRASADO_ANTES_PLAN," +
                "MORATORIOS_ANTES_PLAN," +
                "ESTATUS_PROMESA_PAGO," +
                "MONTO_PROMESA_PAGO," +
                "SEGMENTO," +
                "TURNO,"+
                "TIPOCARTERATKM)"+Constantes.valuesBD+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try{
            CallableStatement cs=conexionbd.establecerConexion2().prepareCall(query);


            for(int i=0;i<cuenta.size();i++){
                cs.setString(1,cuenta.get(i).getCLIENTE_UNICO());
                cs.setString(2,cuenta.get(i).getNOMBRE_CTE());
                cs.setString(3,cuenta.get(i).getRFC_CTE());
                cs.setString(4,cuenta.get(i).getGENERO_CLIENTE());
                cs.setString(5,cuenta.get(i).getEDAD_CLIENTE());
                cs.setString(6,cuenta.get(i).getOCUPACION());
                cs.setString(7,cuenta.get(i).getCORREO_ELECTRONICO());
                cs.setString(8,cuenta.get(i).getDIRECCION_CTE());
                cs.setString(9,cuenta.get(i).getNUM_EXT_CTE());
                cs.setString(10,cuenta.get(i).getNUM_INT_CTE());
                cs.setString(11,cuenta.get(i).getCP_CTE());
                cs.setString(12,cuenta.get(i).getCOLONIA_CTE());
                cs.setString(13,cuenta.get(i).getPOBLACION_CTE());
                cs.setString(14,cuenta.get(i).getESTADO_CTE());
                cs.setString(15,cuenta.get(i).getTERRITORIO());
                cs.setString(16,cuenta.get(i).getTERRITORIAL());
                cs.setString(17,cuenta.get(i).getZONA());
                cs.setString(18,cuenta.get(i).getZONAL());
                cs.setString(19,cuenta.get(i).getNOMBRE_DESPACHO());
                cs.setString(20,cuenta.get(i).getGERENCIA());
                cs.setString(21,cuenta.get(i).getFECHA_ASIGNACION());
                cs.setString(22,cuenta.get(i).getDIAS_ASIGNACION());
                cs.setString(23,cuenta.get(i).getREFERENCIAS_DOMICILIO());
                cs.setString(24,cuenta.get(i).getCLASIFICACION_CTE());
                cs.setString(25,cuenta.get(i).getDIQUE());
                cs.setString(26,cuenta.get(i).getATRASO_MAXIMO());
                cs.setString(27, String.valueOf(cuenta.get(i).getDIAS_ATRASO()));
                cs.setString(28,cuenta.get(i).getSALDO());
                cs.setString(29,cuenta.get(i).getMORATORIOS());
                cs.setString(30, String.valueOf(cuenta.get(i).getSALDO_TOTAL()));
                cs.setString(31,cuenta.get(i).getSALDO_ATRASADO());
                cs.setString(32,cuenta.get(i).getSALDO_REQUERIDO());
                cs.setString(33,cuenta.get(i).getPAGO_PUNTUAL());
                cs.setString(34,cuenta.get(i).getPAGO_NORMAL());
                cs.setString(35,cuenta.get(i).getPRODUCTO());
                cs.setString(36,cuenta.get(i).getFECHA_ULTIMO_PAGO());
                cs.setString(37,cuenta.get(i).getIMP_ULTIMO_PAGO());
                cs.setString(38,cuenta.get(i).getCALLE_EMPLEO());
                cs.setString(39,cuenta.get(i).getNUM_EXT_EMPLEO());
                cs.setString(40,cuenta.get(i).getNUM_INT_EMPLEO());
                cs.setString(41,cuenta.get(i).getCOLONIA_EMPLEO());
                cs.setString(42,cuenta.get(i).getPOBLACION_EMPLEO());
                cs.setString(43,cuenta.get(i).getESTADO_EMPLEO());
                cs.setString(44,cuenta.get(i).getNOMBRE_AVAL());
                cs.setString(45,cuenta.get(i).getTEL_AVAL());
                cs.setString(46,cuenta.get(i).getCALLE_AVAL());
                cs.setString(47,cuenta.get(i).getNUM_EXT_AVAL());
                cs.setString(48,cuenta.get(i).getCOLONIA_AVAL());
                cs.setString(49,cuenta.get(i).getCP_AVAL());
                cs.setString(50,cuenta.get(i).getPOBLACION_AVAL());
                cs.setString(51,cuenta.get(i).getESTADO_AVAL());
                cs.setString(52,cuenta.get(i).getCLIENTE_GRUPAL());
                cs.setString(53,cuenta.get(i).getFIPAISGEO());
                cs.setString(54,cuenta.get(i).getFICUADRANTEGEO());
                cs.setString(55,cuenta.get(i).getFIZONAGEO());
                cs.setString(56,cuenta.get(i).getFIDIAPAGO());
                cs.setString(57,cuenta.get(i).getTELEFONO1());
                cs.setString(58,cuenta.get(i).getTELEFONO2());
                cs.setString(59,cuenta.get(i).getTELEFONO3());
                cs.setString(60,cuenta.get(i).getTELEFONO4());
                cs.setString(61,cuenta.get(i).getTIPOTEL1());
                cs.setString(62,cuenta.get(i).getTIPOTEL2());
                cs.setString(63,cuenta.get(i).getTIPOTEL3());
                cs.setString(64,cuenta.get(i).getTIPOTEL4());
                cs.setString(65,cuenta.get(i).getLATITUD());
                cs.setString(66,cuenta.get(i).getLONGITUD());
                cs.setString(67,cuenta.get(i).getDESPACHO_GESTIONO());
                cs.setString(68,cuenta.get(i).getULTIMA_GESTION());
                cs.setString(69,cuenta.get(i).getGESTION_DESC());
                cs.setString(70,cuenta.get(i).getCAMPANIA_RELAMPAGO());
                cs.setString(71,cuenta.get(i).getCAMPANIA());
                cs.setString(72,cuenta.get(i).getTIPO_CARTERA());
                cs.setString(73,cuenta.get(i).getID_GRUPO());
                cs.setString(74,cuenta.get(i).getGRUPO_MAZ());
                cs.setString(75,cuenta.get(i).getCLAVE_SPEI());
                cs.setString(76,cuenta.get(i).getPAGOS_CLIENTE());
                cs.setString(77,cuenta.get(i).getMONTO_PAGOS());
                cs.setString(78,cuenta.get(i).getGESTORES());
                cs.setString(79,cuenta.get(i).getFOLIO_PLAN());
                cs.setString(80,cuenta.get(i).getSEGMENTO_GENERACION());
                cs.setString(81,cuenta.get(i).getESTATUS_PLAN());
                cs.setString(82,cuenta.get(i).getSEMANAS_ATRASO());
                cs.setString(83,cuenta.get(i).getATRASO());
                cs.setString(84,cuenta.get(i).getGENERACION_PLAN());
                cs.setString(85,cuenta.get(i).getCANCELACION_CUMPLIMIENTO_PLAN());
                cs.setString(86,cuenta.get(i).getULTIMO_ESTATUS());
                cs.setString(87,cuenta.get(i).getEMPLEADO());
                cs.setString(88,cuenta.get(i).getCANAL());
                cs.setString(89,cuenta.get(i).getABONO_SEMANAL());
                cs.setString(90,cuenta.get(i).getPLAZO());
                cs.setString(91,cuenta.get(i).getMONTO_ABONADO());
                cs.setString(92,cuenta.get(i).getMONTO_PLAN());
                cs.setString(93,cuenta.get(i).getENGANCHE());
                cs.setString(94,cuenta.get(i).getPAGOS_RECIBIDOS());
                cs.setString(95,cuenta.get(i).getSALDO_ANTES_DEL_PLAN());
                cs.setString(96,cuenta.get(i).getSALDO_ATRASADO_ANTES_PLAN());
                cs.setString(97,cuenta.get(i).getMORATORIOS_ANTES_PLAN());
                cs.setString(98,cuenta.get(i).getESTATUS_PROMESA_PAGO());
                cs.setString(99,cuenta.get(i).getMONTO_PROMESA_PAGO());
                cs.setString(100, String.valueOf(cuenta.get(i).getSEGMENTO()));
                cs.setString(101, cuenta.get(i).getTURNO());
                cs.setString(102,cuenta.get(i).getTIPOCARTERATKM());
                cs.execute();
            }


            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Registro insertado en la BD");
            respuesta.setData("Registro insertado correctamente");

            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE consultarCarteraDescarte: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE consultarCarteraDescarte: "+e);
        }
        return respuesta;
    }


    public RestResponse<String>borrarCarteraDescarte(String tipoCarteraTKM){
        RestResponse<String> respuesta=new RestResponse<>();
        String tablaBD=null;
        if("1".equals(tipoCarteraTKM)){
            tablaBD="carteraDescarte";
        }
        else{
            tablaBD="carteraDescarteVIP";
        }
        String query=Constantes.deleteBD+tablaBD;
        try{
            CallableStatement cs=conexionbd.establecerConexion2().prepareCall(query);
            cs.execute();

            respuesta.setCode(1);
            respuesta.setError(false);
            respuesta.setMessage("Toda la tabla se borro correctamente");
            respuesta.setData("Toda la tabla se borro correctamente");
            cs.close();
        }
        catch (Exception e){
            respuesta.setMessage("ISSUE borrarCarteraDescarte: "+e);
            LOGGER.log(Level.INFO, () -> "ISSUE borrarCarteraDescarte: "+e);
        }
        return respuesta;

    }


}
