package topchef.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

import topchef.annotations.CacheControl;
import topchef.annotations.CachePolicy;
import topchef.domain.User;
import topchef.repositories.user.UserRepository;
import topchef.services.UsersService;

/*** Note that @PostAuthorize is important since
 * we want to ensure that the principal object in the
 * annotation is a valid principal (in order to obtain the id).
 * If we use @PreAuthorize, the principal object is a String and 
 * doesn't have an id.
 */

@RestController
@RequestMapping("/api")
@JsonFormat
@CacheControl(maxAge=300, policy={CachePolicy.PUBLIC})
public class UsersController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UsersService usersService;
		
	public List<String> rolesAsList(String ...roles) {
		return Arrays.asList(roles);
	}
		
	@RequestMapping(method = RequestMethod.GET, value = "/profile")
	@PostAuthorize("isAuthenticated()")	
	public User profile(Principal principal) {		
		User user = userRepository.findByUserName(principal.getName());
		user.setPassword(null);
		return user;
	}
		
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{userid}/email")
	@PostAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public User changeEmail(@PathVariable String userid, @RequestParam String email) {
		User user = userRepository.findOne(userid);		
		
		user.setEmail(email);
		userRepository.update(user);
		return userRepository.findOne(userid);		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/users/{userid}/enabled")
	@PostAuthorize("hasRole('ROLE_ADMIN') and @methodSecurity.principalIsUser(principal,#userid)")		
	public User updateEnabled(@PathVariable String userid, @RequestParam Boolean enabled) {
		User user = userRepository.findOne(userid);		
		
		user.setIsEnabled(enabled);
		userRepository.update(user);
		return userRepository.findOne(userid);		
	}	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{userid}/status")
	@PostAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public User changeStatus(@PathVariable String userid, @RequestParam String status) {
		User user =  userRepository.findOne(userid);
		user.setStatus(status);
		userRepository.update(user);
		return userRepository.findOne(userid);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{userid}")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public User getUser(@PathVariable String userid) {
		return userRepository.findOne(userid);		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{userid}")
	@PostAuthorize("hasRole('ROLE_ADMIN') or @methodSecurity.principalIsUser(principal,#userid)")	
	public User updateUser(@PathVariable String userid, @RequestBody User user) {
		userRepository.update(user);
		return userRepository.findOne(userid);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{userid}")
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUser(@PathVariable String userid, HttpServletResponse response) {
		usersService.deleteUser(userid);
	}	
	
	@RequestMapping(method = RequestMethod.POST, value = "/users")
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	public User createUser(@RequestBody User user) {					
		user = userRepository.save(user);
		return user;		
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getUsers() {
		Sort sort = new Sort(Sort.Direction.ASC, "lastName", "firstName", "userName");
		return userRepository.findAll(sort);		
	}
	

}