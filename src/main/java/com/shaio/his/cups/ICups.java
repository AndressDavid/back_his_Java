package com.shaio.his.cups;


public interface ICups {
    public ResponseCups ListarCups(String filter);
    public ResponseCups ListaCupsQuirurgicos( StructFiltrosCupsQuirurgicos filtros );
}
