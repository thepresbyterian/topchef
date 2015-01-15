package topchef.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeStep {
	private String description;		
	private Integer number;
	
	public RecipeStep() { 
	}
			
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Integer getNumber() {
		return this.number;
	}

	private RecipeStep(Builder builder) {
		this.number = builder.number;
		this.description = builder.description;
	}
	
	public static class Builder {
		private String description;
		private Integer number;
		
		public Builder() {}
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder number(Integer number) {
			this.number = number;
			return this;
		}
		
		public RecipeStep build() {
			return new RecipeStep(this);
		}		
	}
}
