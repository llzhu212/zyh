package com.zyh.service.classteacher;

import java.util.List;

import com.zyh.entity.classteacher.ZyhClassTeacher;
import com.zyh.entity.classteacher.ZyhClassTeacherExample;

public interface IClassTeacherService {
	
	public void addClassTeacher(ZyhClassTeacher classTeacher) throws Exception;
	
	public void updateClassTeacher(ZyhClassTeacher classTeacher) throws Exception;
	
	public void deleteClassTeacher(String teacherid) throws Exception;
	
	public ZyhClassTeacher queryTeacherById(String teacherid) throws Exception;
	
	public List<ZyhClassTeacher> findTeacherList(ZyhClassTeacherExample example) throws Exception;
	
	public List<ZyhClassTeacher> findTeacherByPage(ZyhClassTeacherExample example,
			int pageNum, int pageSize) throws Exception;
}
