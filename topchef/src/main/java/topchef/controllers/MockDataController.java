package topchef.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import topchef.domain.Unit;
import topchef.domain.User;
import topchef.repositories.unit.UnitRepository;
import topchef.repositories.user.UserRepository;

import com.fasterxml.jackson.annotation.JsonFormat;

@Controller
@RequestMapping("/mock")
@JsonFormat
public class MockDataController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static String[][] data = { { "Kenny", "Hunt", "khunt@uwlax.edu", "123" }, 
			{ "Benjamin", "Knight", "bknight0@nationalgeographic.com", "123" },
			{ "Howard", "Wright", "hwright1@github.com", "123" }, { "George", "Robinson", "grobinson2@seesaa.net", "123" },
			{ "Tammy", "White", "twhite3@uol.com.br", "123" }, { "Scott", "Jones", "sjones4@pinterest.com", "123" },
			{ "Wanda", "Murphy", "wmurphy5@barnesandnoble.com", "123" }, { "Lawrence", "Alexander", "lalexander6@youku.com", "123" },
			{ "Andrew", "Owens", "aowens7@nydailynews.com", "123" }, { "Andrea", "Carter", "acarter8@vk.com", "123" },
			{ "Louis", "Franklin", "lfranklin9@yahoo.co.jp", "123" }, { "Amy", "Berry", "aberrya@cnn.com", "123" },
			{ "Elizabeth", "Fisher", "efisherb@tripadvisor.com", "123" }, { "Donald", "Kim", "dkimc@walmart.com", "123" },
			{ "Thomas", "Reynolds", "treynoldsd@nps.gov", "123" }, { "Helen", "Myers", "hmyerse@a8.net", "123" },
			{ "Irene", "Ross", "irossf@comsenz.com", "123" }, { "Christina", "Reyes", "creyesg@aol.com", "123" },
			{ "Phyllis", "Roberts", "probertsh@webeden.co.uk", "123" }, { "Aaron", "Gray", "agrayi@blog.com", "123" },
			{ "Wayne", "Roberts", "wrobertsj@cam.ac.uk", "123" }, { "Jeffrey", "Hansen", "jhansenk@printfriendly.com", "123" },
			{ "Lois", "Morrison", "lmorrisonl@symantec.com", "123" }, { "Kathleen", "Ellis", "kellism@paginegialle.it", "123" },
			{ "Karen", "Ellis", "kellisn@wix.com", "123" }, { "Gregory", "Dixon", "gdixono@weebly.com", "123" },
			{ "Marilyn", "Kelly", "mkellyp@digg.com", "123" }, { "Sarah", "Alexander", "salexanderq@jimdo.com", "123" },
			{ "Justin", "Reid", "jreidr@boston.com", "123" }, { "Joan", "Russell", "jrussells@sakura.ne.jp", "123" },
			{ "Joyce", "Murphy", "jmurphyt@yale.edu", "123" }, { "Craig", "Henderson", "chendersonu@merriam-webster.com", "123" },
			{ "John", "Sanders", "jsandersv@123-reg.co.uk", "123" }, { "Albert", "Montgomery", "amontgomeryw@histats.com", "123" },
			{ "Albert", "King", "akingx@cnn.com", "123" }, { "Walter", "Hernandez", "whernandezy@flavors.me", "123" },
			{ "Kimberly", "Fisher", "kfisherz@gravatar.com", "123" }, { "Melissa", "Franklin", "mfranklin10@pbs.org", "123" },
			{ "Karen", "Chapman", "kchapman11@is.gd", "123" }, { "Steve", "Mason", "smason12@bing.com", "123" },
			{ "Ashley", "Chavez", "achavez13@latimes.com", "123" }, { "Juan", "Russell", "jrussell14@globo.com", "123" },
			{ "Jane", "Baker", "jbaker15@ft.com", "123" }, { "Daniel", "Simpson", "dsimpson16@surveymonkey.com", "123" },
			{ "Randy", "Mason", "rmason17@bloomberg.com", "123" }, { "Anthony", "Flores", "aflores18@discuz.net", "123" },
			{ "Marilyn", "Garza", "mgarza19@altervista.org", "123" }, { "Jose", "Ramirez", "jramirez1a@blogtalkradio.com", "123" },
			{ "Robin", "Nichols", "rnichols1b@cornell.edu", "123" }, { "Justin", "Knight", "jknight1c@wix.com", "123" },
			{ "Frank", "Mccoy", "fmccoy1d@ocn.ne.jp", "123" }, { "Brenda", "Elliott", "belliott1e@blogs.com", "123" },
			{ "Victor", "Ross", "vross1f@google.ca", "123" }, { "Gary", "Bailey", "gbailey1g@nymag.com", "123" },
			{ "Jeffrey", "Smith", "jsmith1h@facebook.com", "123" }, { "Timothy", "Chapman", "tchapman1i@uiuc.edu", "123" },
			{ "Kevin", "Welch", "kwelch1j@washingtonpost.com", "123" }, { "Angela", "Burton", "aburton1k@pinterest.com", "123" },
			{ "Victor", "Hawkins", "vhawkins1l@ehow.com", "123" }, { "Amy", "Warren", "awarren1m@weather.com", "123" },
			{ "Jeffrey", "Hanson", "jhanson1n@shop-pro.jp", "123" }, { "Anna", "Reed", "areed1o@bizjournals.com", "123" },
			{ "Phillip", "Burton", "pburton1p@dot.gov", "123" }, { "Joe", "Gonzalez", "jgonzalez1q@accuweather.com", "123" },
			{ "David", "Andrews", "dandrews1r@amazon.co.uk", "123" }, { "Wanda", "Meyer", "wmeyer1s@mail.ru", "123" },
			{ "Martha", "Burke", "mburke1t@com.com", "123" }, { "Craig", "Mendoza", "cmendoza1u@naver.com", "123" },
			{ "Joe", "Shaw", "jshaw1v@umich.edu", "123" }, { "Anthony", "Fowler", "afowler1w@bloglines.com", "123" },
			{ "Aaron", "Harris", "aharris1x@imgur.com", "123" }, { "Peter", "Barnes", "pbarnes1y@sphinn.com", "123" },
			{ "Linda", "Scott", "lscott1z@cyberchimps.com", "123" }, { "Jane", "Arnold", "jarnold20@skype.com", "123" },
			{ "Donna", "Gilbert", "dgilbert21@cbslocal.com", "123" }, { "Carol", "Grant", "cgrant22@blogtalkradio.com", "123" },
			{ "Pamela", "Armstrong", "parmstrong23@diigo.com", "123" }, { "Jerry", "Stone", "jstone24@ocn.ne.jp", "123" },
			{ "Christina", "Miller", "cmiller25@phpbb.com", "123" }, { "Bobby", "Marshall", "bmarshall26@icq.com", "123" },
			{ "Randy", "Allen", "rallen27@tinypic.com", "123" }, { "Sandra", "Sims", "ssims28@cargocollective.com", "123" },
			{ "Kathy", "Peters", "kpeters29@storify.com", "123" }, { "Jacqueline", "Berry", "jberry2a@slideshare.net", "123" },
			{ "Nicholas", "Carter", "ncarter2b@accuweather.com", "123" }, { "Ernest", "Hudson", "ehudson2c@abc.net.au", "123" },
			{ "Sara", "Nguyen", "snguyen2d@myspace.com", "123" }, { "Craig", "Reed", "creed2e@t-online.de", "123" },
			{ "David", "Armstrong", "darmstrong2f@paypal.com", "123" }, { "Howard", "Gordon", "hgordon2g@blogtalkradio.com", "123" },
			{ "Jessica", "Sullivan", "jsullivan2h@blog.com", "123" }, { "Angela", "Frazier", "afrazier2i@opera.com", "123" },
			{ "Cheryl", "Rodriguez", "crodriguez2j@ebay.com", "123" }, { "Louise", "Brooks", "lbrooks2k@cyberchimps.com", "123" },
			{ "Shawn", "Kim", "skim2l@google.ru", "123" }, { "Marie", "Wallace", "mwallace2m@rakuten.co.jp", "123" },
			{ "Elizabeth", "Bishop", "ebishop2n@sphinn.com", "123" }, { "Joe", "Jones", "jjones2o@mtv.com", "123" },
			{ "Dorothy", "Williams", "dwilliams2p@lycos.com", "123" }, { "Pamela", "Foster", "pfoster2q@odnoklassniki.ru", "123" },
			{ "Karen", "Duncan", "kduncan2r@tinyurl.com", "123" } };

	private static String[] units = { "teaspoon", "tablespoon", "pinch", "item", "cup", "oz", "sprig", "gallon", "litre", "gram", "pint", "quart", "to taste" };

	@RequestMapping(method = RequestMethod.GET, value = "/create/users")
	public List<User> createUsers(HttpServletResponse response) {
		List<String> users = Arrays.asList(new String[] { "ROLE_USER" });
		List<String> admins = Arrays.asList(new String[] { "ROLE_USER", "ROLE_ADMIN" });
		Set<String> usernames = new HashSet<String>();
		int counter = 0;
		for (String[] datum : data) {
			String firstName = datum[0];
			String lastName = datum[1];
			String email = datum[2];
			String password = datum[3];
			String userName = firstName.charAt(0) + lastName.substring(0, Math.min(lastName.length(), 5));
			userName = userName.toLowerCase();
			
			while(usernames.contains(userName)) {
				userName += (int)(Math.random() * 10);
			}
			usernames.add(userName);
			List<String> roles = null;
			if (counter < 5) {
				roles = admins;
			} else {
				roles = users;
			}
			counter++;

			boolean enabled = Math.random() <= .8;
			if(userName.equals("khunt")) enabled = true;						
			password = bCryptPasswordEncoder.encode(password);
			
			try {
				User newUser = new User.Builder()
					.email(email)
					.firstName(firstName)
					.lastName(lastName)
					.password(password)
					.userName(userName)
					.status("")
					.isEnabled(enabled)
					.roles(roles)
					.build();
				userRepository.save(newUser);
			} catch (Exception e) {
			}
		}
				
		return userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/create/units")
	public List<Unit> createUnits() {

		for (String unit : units) {
			try {
				Unit newUnit = new Unit.Builder().name(unit).build();
				unitRepository.save(newUnit);
			} catch (Exception e) {

			}
		}

		return unitRepository.findAll();
	}
}