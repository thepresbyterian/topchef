package topchef.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class User {
	private String firstName;
	private String lastName;
	private List<String> roles;
	private String password;
	private Boolean isEnabled;
	
	@Indexed(unique = true)
	private String userName;
	private String email;
	private String status;
	
	@Id
	private String id;
		
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public User() { }
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(Boolean b) {
		this.isEnabled = b;
	}
	
	private User(User.Builder builder) {			
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.userName = builder.userName;
		this.status = builder.status;
		this.id = builder.id;		
		this.roles = builder.roles;	
		this.isEnabled = builder.isEnabled;		
		this.password = builder.password;
	}
	
	public static class Builder {
		private String firstName;
		private String lastName;
		private String userName;
		private String email;
		private String status;
		private String id;
		private List<String> roles;
		private String password;
		private Boolean isEnabled;
			
		public Builder() {			
		}
		
		public Builder isEnabled(Boolean isEnabled) {
			this.isEnabled = isEnabled;
			return this;
		}
				
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder roles(List<String> roles) {
			this.roles = roles;
			return this;
		}
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder status(String status) {
			this.status = status;
			return this;
		}
		
		
		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
}
