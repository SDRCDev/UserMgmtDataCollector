
package  com.sdrc.usermgmtdatacollector.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.sdrc.usermgmt.domain.Designation;
import org.sdrc.usermgmt.mongodb.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.sdrc.usermgmtdatacollector.domain.Area;
import com.sdrc.usermgmtdatacollector.model.UserModel;
import com.sdrc.usermgmtdatacollector.repositories.AreaRepository;

/**
 * @author Subham Ashish (subham@sdrc.co.in)
 *
 */
@Component
public class TokenInfoExtracter {

	@Autowired(required = false)
	private TokenStore tokenStore;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private DesignationRepository designationRepository;

	/*
	 * it retrieves the user-info from JWT token.
	 */
	public Map<String, Object> tokenInfo(OAuth2Authentication auth) {

		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
		return accessToken.getAdditionalInformation();

	}

	/*
	 * Extracting the user info from JWT token and setting it to UserModel Object
	 */
	public UserModel getUserModelInfo(OAuth2Authentication auth) {

		Map<String, Object> tokenInfoMap = tokenInfo(auth);

		UserModel user = new UserModel();

		user.setEmailId(tokenInfoMap.get("emailId") != null ? tokenInfoMap.get("emailId").toString() : "");

		user.setUserId(tokenInfoMap.get("userId").toString());

		Set<String> idSet = new HashSet<>();
		Set<String> roleSet = new HashSet<>();

		Map<String, Object> sessionAreaMap = (Map<String, Object>) tokenInfoMap.get("sessionMap");

		StringTokenizer stId = new StringTokenizer(tokenInfoMap.get("designationIds").toString(), ",");

		while (stId.hasMoreTokens()) {
			String nextToken = stId.nextToken();
			nextToken = nextToken.replace("[", "").replace("]", "");

			idSet.add(nextToken);
		}

		StringTokenizer stName = new StringTokenizer(tokenInfoMap.get("designationNames").toString(), ",");

		while (stName.hasMoreTokens()) {
			String nextToken = stName.nextToken();
			nextToken = nextToken.replace("[", "").replace("]", "");
			roleSet.add(nextToken);
		}

		if (idSet.contains("") || roleSet.contains("")) {
			if (sessionAreaMap != null && sessionAreaMap.containsKey("desg")) {

				List<Object> desgList = (List<Object>) sessionAreaMap.get("desg");
				List<Designation> desgs = new ArrayList<>();
				Set<String> desgId = new HashSet<>();
				for (Object a : desgList) {
					desgId.add(a.toString());
				}
				user.setRoleIds(desgId);
				roleSet.clear();
				roleSet.add(
						designationRepository.findById(desgId.stream().collect(Collectors.toList()).get(0)).getName());
				user.setRoles(roleSet);
				tokenInfoMap.put("designationNames", roleSet.stream().collect(Collectors.toList()));
				tokenInfoMap.put("designationIds", desgId);
			}
		} else {
			user.setRoleIds(idSet);
			user.setRoles(roleSet);
		}

		if (sessionAreaMap != null && sessionAreaMap.containsKey("areaIds")) {

			List<Object> areaList = (List<Object>) sessionAreaMap.get("areaIds");
			List<Area> areas = new ArrayList<>();
			List<Integer> areaId = new ArrayList<>();
			for (Object a : areaList) {
				areaId.add((Integer) a);
			}
			user.setAreaIds(areaId);
			user.setAreas(areaRepository.findByAreaIdIn(areaId));
		}

		// extracting designation slug-id from tokenInfoMap
		List<Integer> desgSlugIds = (List<Integer>) (tokenInfoMap.get("desgSlugId"));
		user.setDesgSlugIds(desgSlugIds);
		user.setDesgnName(((List<String>) (tokenInfoMap.get("designationNames"))).get(0));

		// StringTokenizer partnerIdToken = new
		// StringTokenizer(sessionAreaMap.get("partnertId").toString(), ",");

		Set<String> partnerIdS = new HashSet<>();

		/*
		 * while (partnerIdToken.hasMoreTokens()) {
		 * 
		 * String nextToken = partnerIdToken.nextToken();
		 * nextToken=nextToken.replace("[","").replace("]","");
		 * partnerIdS.add(nextToken); }
		 */

		// user.setPartnerId(new ArrayList<>(partnerIdS));
		user.setAreaLevel("STATE");

		return user;
	}

	public UserModel getUserModelWithOutRoleDesig(OAuth2Authentication auth) {

		Map<String, Object> tokenInfoMap = tokenInfo(auth);

		UserModel user = new UserModel();

		user.setEmailId(tokenInfoMap.get("emailId") != null ? tokenInfoMap.get("emailId").toString() : "");

		user.setUserId(tokenInfoMap.get("userId").toString());

		Set<String> idSet = new HashSet<>();
		Set<String> roleSet = new HashSet<>();

		Map<String, Object> sessionAreaMap = (Map<String, Object>) tokenInfoMap.get("sessionMap");

		StringTokenizer stId = new StringTokenizer(tokenInfoMap.get("designationIds").toString(), ",");

		while (stId.hasMoreTokens()) {
			String nextToken = stId.nextToken();
			nextToken = nextToken.replace("[", "").replace("]", "");

			idSet.add(nextToken);
		}

		StringTokenizer stName = new StringTokenizer(tokenInfoMap.get("designationNames").toString(), ",");

		while (stName.hasMoreTokens()) {
			String nextToken = stName.nextToken();
			nextToken = nextToken.replace("[", "").replace("]", "");
			roleSet.add(nextToken);
		}

		if (idSet.contains("") || roleSet.contains("")) {
			if (sessionAreaMap != null && sessionAreaMap.containsKey("desg")) {

				List<Object> desgList = (List<Object>) sessionAreaMap.get("desg");
				List<Designation> desgs = new ArrayList<>();
				Set<String> desgId = new HashSet<>();
				for (Object a : desgList) {
					desgId.add(a.toString());
				}
				user.setRoleIds(desgId);
//				roleSet.clear();
//				roleSet.add(
//						designationRepository.findById(desgId.stream().collect(Collectors.toList()).get(0)).getName());
//				user.setRoles(roleSet);
				tokenInfoMap.put("designationNames", roleSet.stream().collect(Collectors.toList()));
				tokenInfoMap.put("designationIds", desgId);
			}
		} else {
			user.setRoleIds(idSet);
//			user.setRoles(roleSet);
		}

		if (sessionAreaMap != null && sessionAreaMap.containsKey("areaIds")) {

			List<Object> areaList = (List<Object>) sessionAreaMap.get("areaIds");
			List<Area> areas = new ArrayList<>();
			List<Integer> areaId = new ArrayList<>();
			for (Object a : areaList) {
				areaId.add((Integer) a);
			}
			user.setAreaIds(areaId);
//			user.setAreas(areaRepository.findByAreaIdIn(areaId));
		}

		// extracting designation slug-id from tokenInfoMap
		List<Integer> desgSlugIds = (List<Integer>) (tokenInfoMap.get("desgSlugId"));
		user.setDesgSlugIds(desgSlugIds);
		user.setDesgnName(((List<String>) (tokenInfoMap.get("designationNames"))).get(0));

		// StringTokenizer partnerIdToken = new
		// StringTokenizer(sessionAreaMap.get("partnertId").toString(), ",");

		Set<String> partnerIdS = new HashSet<>();

		/*
		 * while (partnerIdToken.hasMoreTokens()) {
		 * 
		 * String nextToken = partnerIdToken.nextToken();
		 * nextToken=nextToken.replace("[","").replace("]","");
		 * partnerIdS.add(nextToken); }
		 */

		// user.setPartnerId(new ArrayList<>(partnerIdS));
		user.setAreaLevel("STATE");

		return user;
	}
}
