package com.shaio.his.consentimientoInformado;

import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class StructConsentimiento {
 	
 	
 	private String fecha;
    private Paciente paciente;
    private String idDocumento="";
    private String plantilla;
    private String nombrePlantilla;
    private Medico medico;
    private String consentimiento;

	

    	
        public static StructConsentimiento fromJson(String jsonString) throws JsonSyntaxException {
         	Gson gson = new Gson();
         	return gson.fromJson(jsonString, StructConsentimiento.class);
         }
         	
    	
 		public void setFecha(String fecha) {
 			this.fecha = fecha;
 		}
 
		public Paciente getPaciente() {
			return paciente;
		}

		public void setPaciente(Paciente paciente) {
			this.paciente = paciente;
		}
 
 		public String getPlantilla() {
 			return plantilla;
 		}
 
 		public void setPlantilla(String plantilla) {
 			this.plantilla = plantilla;
 		}
 
		public Medico getMedico() {
			return medico;
		}

		public void setMedico(Medico medico) {
			this.medico = medico;
		}
		
 
 	
 
 
 
 		public String getNombrePlantilla() {
 			return nombrePlantilla;
 		}

 		public void setNombrePlantilla(String nombrePlantilla) {
 			this.nombrePlantilla = nombrePlantilla;
 		}
 
 
		public String getIdDocumento() {
			return idDocumento;
		}


		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}



 
 
		public String getConsentimiento() {
			return consentimiento;
		}


		public void setConsentimiento(String consentimiento) {
			this.consentimiento = consentimiento;
		}





		public class ID {
	        private String oid;
	    }
	    
	    public class Firma {
	        private String paciente;
			private String acuendiente;
	        private String medico;
	        private String testigo;
	        public String getPaciente() {
				return paciente;
			}
			public void setPaciente(String paciente) {
				this.paciente = paciente;
			}
			public String getAcuendiente() {
				return acuendiente;
			}
			public void setAcuendiente(String acuendiente) {
				this.acuendiente = acuendiente;
			}
			public String getMedico() {
				return medico;
			}
			public void setMedico(String medico) {
				this.medico = medico;
			}
			public String getTestigo() {
				return testigo;
			}
			public void setTestigo(String testigo) {
				this.testigo = testigo;
			}
 
	    }
	    
	    public class Localizacion {
	    	
	    	private Double longitude;
	        private Double latitude;
	        private Object timestamp;
	        private Double accuracy;
	        private Long altitude;
	        private Object floor;
	        private Long heading;
	        private Long speed;
	        private Long speedAccuracy;
	        private Boolean isMocked;
	    	
	    	
	        public Double getLongitude() {
				return longitude;
			}
			public void setLongitude(Double longitude) {
				this.longitude = longitude;
			}
			public Double getLatitude() {
				return latitude;
			}
			public void setLatitude(Double latitude) {
				this.latitude = latitude;
			}
			public long getTimestamp() {
				//JsonObject timestampObj = timestamp.getAsJsonObject("timestamp");
				//long timestamp2 = timestampObj.get("$numberLong").getAsLong();
				return 0;
				//return timestamp;
			}
			public void setTimestamp(JsonObject t) {
				//JsonObject timestampObj = jsonObject.getAsJsonObject("timestamp");
				//long timestamp = timestampObj.get("$numberLong").getAsLong();
				//this.timestamp = t;
			}
			public Double getAccuracy() {
				return accuracy;
			}
			public void setAccuracy(Double accuracy) {
				this.accuracy = accuracy;
			}
			public Long getAltitude() {
				return altitude;
			}
			public void setAltitude(Long altitude) {
				this.altitude = altitude;
			}
			public Object getFloor() {
				return floor;
			}
			public void setFloor(Object floor) {
				this.floor = floor;
			}
			public Long getHeading() {
				return heading;
			}
			public void setHeading(Long heading) {
				this.heading = heading;
			}
			public Long getSpeed() {
				return speed;
			}
			public void setSpeed(Long speed) {
				this.speed = speed;
			}
			public Long getSpeedAccuracy() {
				return speedAccuracy;
			}
			public void setSpeedAccuracy(Long speedAccuracy) {
				this.speedAccuracy = speedAccuracy;
			}
			public Boolean getIsMocked() {
				return isMocked;
			}
			public void setIsMocked(Boolean isMocked) {
				this.isMocked = isMocked;
			}
		
	    }
	    
	    public class Medico {
	        public String getRegistroMedico() {
				return registroMedico;
			}
			public void setRegistroMedico(String registroMedico) {
				this.registroMedico = registroMedico;
			}
			public String getNombre() {
				return nombre;
			}
			public void setNombre(String nombre) {
				this.nombre = nombre;
			}
			public String getUsuario() {
				return usuario;
			}
			public void setUsuario(String usuario) {
				this.usuario = usuario;
			}
			private String registroMedico;
	        private String nombre;
	        private String usuario;
	    }
	    
	    public class Paciente {
	      
	    	public String getDocumento() {
				return documento;
			}
			public void setDocumento(String documento) {
				this.documento = documento;
			}
			public String getTipoDocumento() {
				return tipoDocumento;
			}
			public void setTipoDocumento(String tipoDocumento) {
				this.tipoDocumento = tipoDocumento;
			}
			public String getIngreso() {
				return ingreso;
			}
			public void setIngreso(String ingreso) {
				this.ingreso = ingreso;
			}
			public String getNombres() {
				return nombres;
			}
			public void setNombres(String nombres) {
				this.nombres = nombres;
			}
			private String documento;
	        private String tipoDocumento;
	        private String ingreso;
	        private String nombres;
	    }
 
 	    
 }