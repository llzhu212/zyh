package com.zyh.controller.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyh.controller.company.vo.PositionQueryVO;
import com.zyh.controller.user.common.UserCom;
import com.zyh.entity.common.ResponeToWeb;
import com.zyh.entity.company.ZyhCompany;
import com.zyh.entity.company.ZyhCompanyPosition;
import com.zyh.entity.company.ZyhCompanyPositionExample;
import com.zyh.entity.company.ZyhCompanyPositionExample.Criteria;
import com.zyh.service.company.ICompanyPositionService;
import com.zyh.service.company.ICompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	private Logger log = Logger.getLogger("error");
	
	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private ICompanyPositionService companyPositionService;
	
	@RequestMapping("/addCompany.act")
	public ResponeToWeb addCompany(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ZyhCompany zyhCompany = om.readValue(json, ZyhCompany.class);
			companyService.addCompany(zyhCompany);
			responeToWeb.setMsg("添加成功");
			responeToWeb.setSuccess(true);
		} catch (Exception e) {
			log.error("添加失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
	
	@RequestMapping("/updateCompany.act")
	public ResponeToWeb updateCompany(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ZyhCompany zyhCompany = om.readValue(json, ZyhCompany.class);
			if (null!=zyhCompany.getId()&&!"".equals(zyhCompany.getId())) {
				companyService.updateCompanyById(zyhCompany);
				responeToWeb.setMsg("修改成功");
				responeToWeb.setSuccess(true);
			}else{
				responeToWeb.setMsg(UserCom.ERROR_IDNULL);
				responeToWeb.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("修改失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
	
	@RequestMapping("/delCompany.act")
	public ResponeToWeb delCompany(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id = om.readTree("id").asText();
			if (null!=id&&!"".equals(id)){
				companyService.delCompanyById(id);
				responeToWeb.setMsg("删除成功");
				responeToWeb.setSuccess(true);
			}else{
				responeToWeb.setMsg(UserCom.ERROR_IDNULL);
				responeToWeb.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("删除失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
	
	@RequestMapping("/addPosition.act")
	public ResponeToWeb addPosition(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ZyhCompanyPosition zyhCompanyPosition = om.readValue(json, ZyhCompanyPosition.class);
			Date date = new Date();
			zyhCompanyPosition.setOptime(date);
			companyPositionService.addCompanyPosition(zyhCompanyPosition);
			responeToWeb.setMsg("添加成功");
			responeToWeb.setSuccess(true);
		} catch (Exception e) {
			log.error("添加失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
	
	@RequestMapping("/updatePosition.act")
	public ResponeToWeb updatePosition(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ZyhCompanyPosition zyhCompanyPosition = om.readValue(json, ZyhCompanyPosition.class);
			if (null!=zyhCompanyPosition.getId()&&!"".equals(zyhCompanyPosition.getId())) {
				companyPositionService.updateCompanyPositionById(zyhCompanyPosition);
				responeToWeb.setMsg("修改成功");
				responeToWeb.setSuccess(true);
			}else{
				responeToWeb.setMsg(UserCom.ERROR_IDNULL);
				responeToWeb.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("修改失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
	
	@RequestMapping("/delPosition.act")
	public ResponeToWeb delPosition(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		ObjectMapper om = new ObjectMapper();
		try {
			String id = om.readTree("id").asText();
			if (null!=id&&!"".equals(id)){
				companyPositionService.delCompanyPositionById(id);
				responeToWeb.setMsg("删除成功");
				responeToWeb.setSuccess(true);
			}else{
				responeToWeb.setMsg(UserCom.ERROR_IDNULL);
				responeToWeb.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("删除失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
	
	@RequestMapping("/queryPosition.act")
	public ResponeToWeb queryPositionByPage(@RequestBody String json){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		List<ZyhCompanyPosition> zyhCompanyPositions = new ArrayList<ZyhCompanyPosition>();
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PositionQueryVO positionQueryVO = om.readValue(json, PositionQueryVO.class);
			ZyhCompanyPositionExample zyhCompanyPositionExample = new ZyhCompanyPositionExample();
			Criteria criteria = zyhCompanyPositionExample.createCriteria();
			if (null!=positionQueryVO.getId()&&!"".equals(positionQueryVO.getId())) {
				criteria.andIdEqualTo(positionQueryVO.getId());
			}else {
				if (null!=positionQueryVO.getCompanyId()&&!"".equals(positionQueryVO.getCompanyId())) {
					criteria.andCompanyidEqualTo(positionQueryVO.getCompanyId());
				}
				if (null!=positionQueryVO.getCompanyName()&&!"".equals(positionQueryVO.getCompanyName())) {
					criteria.andCompnaynameLike("%"+positionQueryVO.getCompanyName()+"%");
				}
				if (null!=positionQueryVO.getWorkcity()&&!"".equals(positionQueryVO.getWorkcity())) {
					criteria.andWorkcityEqualTo(positionQueryVO.getWorkcity());
				}
				if (0!=positionQueryVO.getMinmoney()) {
					criteria.andMinmoneyGreaterThanOrEqualTo(positionQueryVO.getMinmoney());
				}
				if (0!=positionQueryVO.getMaxmoney()) {
					criteria.andMaxmoneyLessThanOrEqualTo(positionQueryVO.getMaxmoney());
				}
				if (null!=positionQueryVO.getPostionName()&&!"".equals(positionQueryVO.getPostionName())) {
					criteria.andPositionnameLike("%"+positionQueryVO.getPostionName()+"%");
				}
			}
			
			map = companyPositionService.selectCompanyPositionByPage(zyhCompanyPositionExample, positionQueryVO.getPageNum(), positionQueryVO.getPageSize());
			responeToWeb.setMsg("查询成功");
			responeToWeb.setSuccess(true);
			responeToWeb.setValue(map);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}
}
