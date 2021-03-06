package com.sdrc.usermgmtdatacollector.datacollector.implhandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import com.sdrc.usermgmtdatacollector.domain.Area;
import com.sdrc.usermgmtdatacollector.domain.AreaLevel;
import com.sdrc.usermgmtdatacollector.repositories.AreaLevelRepository;
import com.sdrc.usermgmtdatacollector.repositories.AreaRepository;

import in.co.sdrc.sdrcdatacollector.document.Question;
import in.co.sdrc.sdrcdatacollector.document.TypeDetail;
import in.co.sdrc.sdrcdatacollector.handlers.IDbQueryHandler;
import in.co.sdrc.sdrcdatacollector.models.OptionModel;
import in.co.sdrc.sdrcdatacollector.models.QuestionModel;

/**
 * @author Debiprasad Parida (debiprasad@sdrc.co.in)
 * @author subham
 */
@Component
public class IDbQueryHandlerImpl implements IDbQueryHandler {

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private AreaLevelRepository areaLevelRepository;

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	

	@Override
	public List<OptionModel> getOptions(QuestionModel questionModel, Map<Integer, TypeDetail> typeDetailsMap,
			Question question, String checkedValue, Object user1, Map<String, Object> paramKeyValueMap) {

		List<OptionModel> listOfOptions = new ArrayList<>();
		String tableName = questionModel.getTableName().split("\\$\\$")[0].trim();
		String areaLevel = "";
		if (tableName.equals("area"))
			areaLevel = questionModel.getTableName().split("\\$\\$")[1].trim().split("=")[1];
		List<Area> areas = null;

		switch (tableName) {

		case "area": {
			// listOfOptions = new ArrayList<>();
			switch (areaLevel) {

			case "state": {
				areas = areaRepository.findByAreaLevelAreaLevelIdOrderByAreaIdAsc(1);
			}
				break;

			case "district":

			{
				areas = areaRepository.findByAreaLevelAreaLevelIdOrderByAreaIdAsc(2);
			}

				break;
			case "city": {
				areas = areaRepository.findByAreaLevelAreaLevelIdOrderByAreaIdAsc(3);
				//areas.add(areaRepository.findByAreaId(600005));
			}
				break;

			case "block": {
				areas = areaRepository.findByAreaLevelAreaLevelIdOrderByAreaIdAsc(4);
				//areas.add(areaRepository.findByAreaId(600001));
			}
				break;
			case "gp": {
				areas = areaRepository.findByAreaLevelAreaLevelIdOrderByAreaIdAsc(5);
				//areas.add(areaRepository.findByAreaId(600002));
			}

				break;

			/*case "shelter home": {
				//areas = areaRepository.findByAreaLevelAreaLevelIdOrderByAreaIdAsc(6);
				//areas.add(areaRepository.findByAreaId(16752));
			}

				break;*/

			}

			if (areas != null) {

				int order = 0;
				for (Area area : areas) {

					Map<String, Object> extraKeyMap = new HashMap<>();
					OptionModel optionModel = new OptionModel();
					/*
					 * //9, 10, 11, 12, 13, 14, 15, 16, 17, 57, 58, 59
					 */
				
						optionModel.setKey(area.getAreaId());
						optionModel.setValue(area.getAreaName());
						optionModel.setOrder(order++);
						if (area.getAreaLevel() != null) {
							optionModel.setParentId(area.getParentAreaId());
							optionModel.setLevel(area.getAreaLevel().getAreaLevelId());
						} else {
							optionModel.setParentId2(-2);
						}
						optionModel.setVisible(true);
						extraKeyMap.put("state_id", area.getStateId());
						extraKeyMap.put("district_id", area.getDistrictId());
						extraKeyMap.put("tahasil_id", area.getTahasilId());
						extraKeyMap.put("block_id", area.getBlockId());

						optionModel.setExtraKeyMap(extraKeyMap);
						listOfOptions.add(optionModel);
					
					questionModel.setOptions(listOfOptions);
				}

			}

		}
			break;
		case "areaLevel": {
			List<AreaLevel> areaLevels = areaLevelRepository.findAll();
			areaLevels.removeIf(findFacilityLevel(configurableEnvironment.getProperty("facility.area.level.name"))); // remove

			for (AreaLevel areaLvl : areaLevels) {
				OptionModel optionModel = new OptionModel();
				optionModel.setKey(areaLvl.getAreaLevelId());
				optionModel.setValue(areaLvl.getAreaLevelName());
				listOfOptions.add(optionModel);
			}
			questionModel.setOptions(listOfOptions);
		}
			break;

		}

		return listOfOptions;

	}

	public Predicate<AreaLevel> findFacilityLevel(String facility) {
		return f -> f.getAreaLevelName().equalsIgnoreCase(facility);
	}

	@Override
	public String getDropDownValueForRawData(String tableName, Integer dropdownId) {
		// TODO Auto-generated method stub
		return null;
	}

	public QuestionModel setValueForTextBoxFromExternal(QuestionModel questionModel, Question question,
			Map<String, Object> paramKeyValMap, HttpSession session, Object user) {
		return questionModel;
		//
		// String featureName = questionModel.getFeatures();
		// UserModel userModel = (UserModel) user;
		// if (featureName != null &&
		// featureName.contains("fetch_from_external")) {
		// for (String feature : featureName.split("@AND")) {
		// switch (feature.split(":")[0]) {
		// case "fetch_from_external": {
		// switch (feature.split(":")[1]) {
		// case "supervisor_name":
		// questionModel.setValue(userModel.getFirstName()+"
		// "+userModel.getLastName());
		// break;
		// case "organization":
		// questionModel.setValue(userModel.getOrgName());
		// break;
		// case "designation":
		// questionModel.setValue(userModel.getDesgnName());
		// break;
		// case "level":
		// questionModel.setValue(userModel.getAreaLevel());
		// break;
		// case "N/A":
		// questionModel.setValue("N/A");
		// }
		//
		// }
		// break;
		//
		// }
		// }
		// }
		//
		// return questionModel;
		//
	}

}
