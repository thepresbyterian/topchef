package topchef.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import topchef.annotations.CacheControl;
import topchef.annotations.CachePolicy;
import topchef.domain.Unit;
import topchef.services.RecipesService;

import com.fasterxml.jackson.annotation.JsonFormat;


@RestController
@RequestMapping("/api/static/")
@JsonFormat
@CacheControl(maxAge=300, policy={CachePolicy.PUBLIC})
public class GlobalsController {
	@Autowired
	private RecipesService recipesService;

	@RequestMapping(method = RequestMethod.GET, value = "units")
	@PreAuthorize("isAuthenticated()")		
	public List<Unit> getUnits() {
		return recipesService.getUnits();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "units")
	@PreAuthorize("hasRole('ROLE_ADMIN')")		
	public List<Unit> createUnit(@RequestBody String unit) {
		recipesService.createUnit(unit);
		return getUnits();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "units/{unit}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")		
	public List<Unit> deleteUnit(@PathVariable String unit) {
		recipesService.deleteUnit(unit);
		return getUnits();
	}	
}
