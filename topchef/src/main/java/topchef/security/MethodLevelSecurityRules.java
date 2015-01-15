package topchef.security;

import org.springframework.stereotype.Component;

import topchef.services.TopchefUser;

@Component(value="methodSecurity")
public class MethodLevelSecurityRules {
	public boolean principalIsUser(Object principal, String userId) {
		if(principal != null && principal instanceof TopchefUser) {
			TopchefUser user = (TopchefUser)principal;
			return user.id != null && user.id.equals(userId); 
		}
		return false;
	}
}
