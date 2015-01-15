package topchef.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "units")
public class Unit {
	private String name;
	
	@Id
	private String id;
	
	public Unit() {		
	}
	
	public Unit(Builder builder) {
		this.name = builder.name;
		this.id = builder.id;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static class Builder {
		private String name;
		private String id;
		
		public Builder() { }
			
		public Builder name(String name) {
			this.name = name;
			return this;
		}
				
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Unit build() {
			return new Unit(this);
		}
	}
}
