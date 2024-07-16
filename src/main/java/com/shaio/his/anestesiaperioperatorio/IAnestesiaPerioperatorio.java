package com.shaio.his.anestesiaperioperatorio;

public interface IAnestesiaPerioperatorio {
    public ResponseAnestesiaPerioperatorio ListarCupsAnestesia();
    public ResponseAnestesiaValidar ValidarDatosAnestesia(StructAnestesiavalidar cJsonDatos);
    public ResponseAnestesiaGuardar GuardarDatosAnestesia(StructAnestesiaguardar cJsonData);
}

