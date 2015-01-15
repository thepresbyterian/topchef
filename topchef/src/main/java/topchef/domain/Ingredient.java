package topchef.domain;

import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY)
//@JsonFilter(value="tag")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Document
public class Ingredient {
	private Unit units;
	private Float quantity;
	private String description;
	
	@Id
	private String id;
	
	public Ingredient() {
		
	}
	
	public String getId() {
		return id;
	}
		
	public void setId(String id) {
		this.id = id;
	}
	
	public Float getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	
	public Unit getUnits() {
		return units;
	}

	public void setUnits(Unit units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private Ingredient(Ingredient.Builder builder) {
		this.units = builder.units;
		this.quantity = builder.quantity;
		this.description = builder.description;
		this.id = builder.id;
	}
		
	public static class Builder {
		private Unit units;
		private Float quantity;
		private String description;
		private String id;
		
		public Builder() {}
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder units(Unit units) {
			this.units = units;
			return this;
		}
		
		public Builder quantity(Float quantity) {
			this.quantity = quantity;
			return this;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Ingredient build() {
			return new Ingredient(this);
		}
	}

}
