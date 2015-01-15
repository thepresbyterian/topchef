package topchef.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import topchef.domain.Recipe;
import topchef.domain.User;
import topchef.repositories.recipe.RecipeRepository;
import topchef.repositories.user.UserRepository;

@Service
public class UsersService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;
		
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		final User user = getUserDetail(username);		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		org.springframework.security.core.userdetails.User userDetail = 
				new TopchefUser(user.getUserName(), 
								user.getPassword(),
								user.getIsEnabled(), true, true, true, getAuthorities(user), user.getId());		
		
		return userDetail;
	}

	public List<GrantedAuthority> getAuthorities(User user) {
		List<String> roles = user.getRoles();
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		
		if(roles != null) {
			for(String role : roles) {
				authList.add(new SimpleGrantedAuthority(role));			
			}		
		}
		return authList;
	}

	private User getUserDetail(String username) {
		User user = userRepository.findByUserName(username);
		return  user;
	}
	
	public void deleteUser(String id) {		
		User user = userRepository.findOne(id);		
		List<Recipe> recipes = recipeRepository.findByUserId(id);
		recipeRepository.delete(recipes);		
		userRepository.delete(user);		
	}
}