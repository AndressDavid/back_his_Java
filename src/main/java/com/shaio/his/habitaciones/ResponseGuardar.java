package com.shaio.his.habitaciones;

import com.shaio.his.ResponseJson;

public class ResponseGuardar extends ResponseJson {
    private String respuesta;

    public ResponseGuardar(String errorMessage, int errorCode, String respuesta) {
        super(errorMessage, errorCode);
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
