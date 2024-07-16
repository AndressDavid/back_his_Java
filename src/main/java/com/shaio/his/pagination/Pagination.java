package com.shaio.his.pagination;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.shaio.his.AS400.DBAS400;


public class Pagination {
	
	private StructPagination pagination_data;
	private boolean cambiarNombreDatos = false;
	private ArrayList<StructCambioNombreDatos> sCambioNombre;
	

	public StructPagination getPagination_data() {
		return pagination_data;
	}

	public void setPagination_data(StructPagination pagination_data) {
		this.pagination_data = pagination_data;
	}
	
	public boolean CambiarNombreDatos() {
		return cambiarNombreDatos;
	}

	public void setCambiarNombreDatos(boolean cambiarNombreDatos) {
		this.cambiarNombreDatos = cambiarNombreDatos;
	}
	
	public ArrayList<StructCambioNombreDatos> getsCambioNombre() {
		return sCambioNombre;
	}

	public void setsCambioNombre(ArrayList<StructCambioNombreDatos> sCambioNombre) {
		this.sCambioNombre = sCambioNombre;
	}
	

	
	public String sqlConsultaFilter(String select, String orderSql, String whereManual) {
		
		int numeroRegistroPagina = pagination_data.getMaxPage();	
		
		int offset = (pagination_data.getPage() -1) * numeroRegistroPagina + 1;
		int limit = pagination_data.getPage()* numeroRegistroPagina;
		
		String filtroFinal;
		String filtro = crearFiltros(pagination_data.getFilter(),cambiarNombreDatos );
		
		System.out.println("OK:"+filtro);
		
		if(filtro.length() > 0) {
			filtroFinal = " WHERE " + filtro.substring(0 , filtro.length() -4) + " AND "+whereManual;
		}else {
			filtroFinal = filtro + " WHERE " +whereManual;
		}
		
		String sqlTablas = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY "+orderSql+" )"
				+ "AS NUM_FILA_SQL, "+select+ " "+filtroFinal+" FETCH FIRST "+limit+" ROWS ONLY ) AS BASEDATA "
						+ "WHERE NUM_FILA_SQL BETWEEN "+offset+" AND "+limit +" "+ordenSql(pagination_data.getOrden());
		
		System.out.println(sqlTablas);
		
		return sqlTablas;
	}
	
	
	public String sqlConsulta() {
		
		int numeroRegistroPagina = pagination_data.getMaxPage();	
		
		int offset = (pagination_data.getPage() -1) * numeroRegistroPagina + 1;
		int limit = pagination_data.getPage()* numeroRegistroPagina;
		
		String sqlTablas = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY  NIGING DESC)"
				+ "AS NUM_FILA_SQL, "+pagination_data.getSelect()+ " FETCH FIRST "+limit+" ROWS ONLY ) AS BASEDATA "
						+ "WHERE NUM_FILA_SQL BETWEEN "+offset+" AND "+limit +" "+ordenSql(pagination_data.getOrden());
		
		System.out.println(sqlTablas);
		
		return sqlTablas;
	}
	
	
	private String crearFiltros(ArrayList<StructFilters> filters, boolean cambiarCampos) {
		
		String filtro ="";
		
		for(int i =0; i < filters.size(); i++) {
			
			String filterColumn = filters.get(i).getColumn();
			String filterType = filters.get(i).getFilter();
			String filterValue = filters.get(i).getValue();
			
			
			if(cambiarNombreDatos) {
				filtro = filtro + filtrosFlutter(filterType, cambiarNombreDatos(filterColumn),filterValue ) + " AND ";
				
			}else {
				filtro = filtro + filtrosFlutter(filterType,filterColumn, filterValue ) + " AND ";
			}
	
		}
		
		return filtro;
	}
	
	private String cambiarNombreDatos(String campo) {
		
		String campoCambiado ="";
		
		for(int i =0; i < sCambioNombre.size(); i++) {
			
			if(sCambioNombre.get(i).getOrigenColumna().equals(campo)) {
				
				return sCambioNombre.get(i).getDestinoColumna();
		
			}	
			
		}
		return campoCambiado;
	}
	
	private String ordenSql(ArrayList<StructOrder> order) {
		
		
		if(order.get(0).getType() == null) {
			System.out.println("entro ");
			return "";
		}
		
		String tipo = order.get(0).getType();
		String columna =  (cambiarNombreDatos) ? cambiarNombreDatos(order.get(0).getColumn()) : order.get(0).getColumn();

		
		switch(tipo){
			case "ascending":
				
				return "order by " + columna + " ASC";
			case "descending": 
				return "order by " + columna + " DESC";
			default:
				return "";
		}
	}
	
	
	public int countPages(String select, String whereManual ) {
		
		int paginas =1;
		int numeroRegistroPagina = pagination_data.getMaxPage();
		
		
		String filtroFinal;
		String filtro = crearFiltros(pagination_data.getFilter(),cambiarNombreDatos);
		
		if(filtro.length() > 0) {
			filtroFinal = " WHERE " + filtro.substring(0 , filtro.length() -4) + " AND "+whereManual;
		}else {
			filtroFinal = filtro + " WHERE " + whereManual;
		}
		
		String sqlPaginas =  "SELECT " +select +" "+filtroFinal ;
		
		try {
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlPaginas);
			
			while (rs.next()) {
				paginas = (int) Math.ceil(rs.getFloat("paginas")/ numeroRegistroPagina);
			}
			
		}catch (Exception e) {
			paginas =0;
			e.printStackTrace();
		}
		
		return paginas;
	}
	
	
	private String filtrosFlutter(String filtro , String campo, String dato) {
		
		String where="";
		
		switch(filtro){
			case "like":
				where = where + campo +" like'%"+dato+"%'";
			break;
			
			case "like%":
				where = where + campo +" like'"+dato+"%'";
			break;
			case "%like":
					where = where + campo +" like'%"+dato+"'";
			break;
					
			case "mayor":
				where = where + campo +" > "+dato;
			break;	
			case "mayor_igual":
					where = where + campo +" >= "+dato;
			break;		
			case "menor":
				where = where + campo +" < "+dato;
			break;
			case "menor_igual":
				where = where + campo +" <= "+dato;
			break;
			case "igual":
				where = where + campo +" = "+dato;
			break;
			default:
				where = where + campo +" "+ filtro +" "+dato;
			break;
	
		}
		
		return where;
		
	}

}
