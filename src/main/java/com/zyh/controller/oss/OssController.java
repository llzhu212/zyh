package com.zyh.controller.oss;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.zyh.controller.oss.vo.OssVO;
import com.zyh.entity.common.ResponeToWeb;
import com.zyh.service.oss.IOssService;
import com.zyh.service.oss.common.OssCommon;

import net.sf.json.JSONObject;

//@Connector
@RestController
@RequestMapping("/oss")
@WebServlet(asyncSupported = true)
public class OssController extends HttpServlet {

	private Logger log = Logger.getLogger("error");

	@Autowired
	private IOssService ossService;

	@RequestMapping(value = "/headImgUpload", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ResponeToWeb headImgUpload(HttpServletRequest request, MultipartFile file) {
		ResponeToWeb responeToWeb = new ResponeToWeb();
		Map<String, Object> map = new HashMap<>();
		try {
			String url = ossService.uploadImg(file);
			responeToWeb.setMsg("图片上传成功");
			responeToWeb.setSuccess(true);
			map.put("resule", url);
			responeToWeb.setValue(map);
		} catch (Exception e) {
			log.error(e.getMessage());
			responeToWeb.setMsg(e.getMessage());
			responeToWeb.setSuccess(false);
		}
		return responeToWeb;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String endpoint = "*";
		String accessId = "*";
		String accessKey = "*";
		String bucket = "*";
		String dir = "user-dir";
		String host = "http://" + bucket + "." + endpoint;
		OSSClient client = new OSSClient(endpoint, accessId, accessKey);
		try {
			long expireTime = 30;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);

			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessid", accessId);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			// respMap.put("expire", formatISO8601Date(expiration));
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));
			JSONObject ja1 = JSONObject.fromObject(respMap);
			System.out.println(ja1.toString());
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST");
			response(request, response, ja1.toString());

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
		String callbackFunName = request.getParameter("callback");
		if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
			response.getWriter().println(results);
		else
			response.getWriter().println(callbackFunName + "( " + results + " )");
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@RequestMapping("/getOss.act")
	public ResponeToWeb getOssConfig(HttpServletRequest request, HttpServletResponse response){
		ResponeToWeb responeToWeb = new ResponeToWeb();
		Map<String, Object> map = new HashMap<>();
		 String endpoint = OssCommon.ENDPOINT;
	        String accessId = OssCommon.ACCESSKEYID;
	        String accessKey =OssCommon.ACCESSKEYSECRET;
	        String bucket = OssCommon.BUCKETNAME;
	        String dir = OssCommon.FILEDIR;
	        String host = "http://" + bucket + "." + endpoint;
	        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
	        try {
	        	long expireTime = 30;
	        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
	            Date expiration = new Date(expireEndTime);
	            PolicyConditions policyConds = new PolicyConditions();
	            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
	            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

	            String postPolicy = client.generatePostPolicy(expiration, policyConds);
	            byte[] binaryData = postPolicy.getBytes("utf-8");
	            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
	            String postSignature = client.calculatePostSignature(postPolicy);
	            OssVO ossVO = new OssVO();
	            ossVO.setAccessid(accessId);
	            ossVO.setPolicy(encodedPolicy);
	            ossVO.setSignature(postSignature);
	            ossVO.setDir(dir);
	            ossVO.setHost(host);
	            ossVO.setExpire(String.valueOf(expireEndTime / 1000));
	            map.put("result", ossVO);
	            responeToWeb.setSuccess(true);
	            responeToWeb.setValue(map);
			} catch (Exception e) {
				log.error(e.getMessage());
				responeToWeb.setSuccess(false);
				responeToWeb.setMsg(e.getMessage());
			}
		return responeToWeb;
	}
}
