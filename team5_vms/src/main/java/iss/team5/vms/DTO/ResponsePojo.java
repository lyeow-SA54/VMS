package iss.team5.vms.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePojo {
//   @JsonProperty("<result>") // only required, if fieldName != jsonFieldName
   private String result; 
   
   public ResponsePojo (@JsonProperty("result") String result)
   {
	   this.result=result;
   }

public String getResponse() {
	return result;
}
}
