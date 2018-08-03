package com.iekun.ef.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.controller.UserManagerController;
import com.iekun.ef.dao.ResourceMapper;
import com.iekun.ef.dao.RoleResourceMapper;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.Resource;
import com.iekun.ef.model.RoleResource;
import com.iekun.ef.model.User;

@Service
public class ResourceService {

	
	 private static Logger logger = LoggerFactory.getLogger( ResourceService.class );
	 
	 @Autowired
	 private ResourceMapper resourceMapper;
	 
	 @Autowired
	 private RoleResourceMapper roleResourceMapper; 
	
	@SuppressWarnings("rawtypes")
	
	public  Map<String, Object> getResourceInfo(  Map<String, Object> model) {
		 
		 
		 //List treeMenus =  this.getResourceInfoTest();
		 //model.put("treeMenus", treeMenus);
		 //model.put("modules", treeMenus);
		 //return model;
		return this.getResourceInfoModel(model);
		 
	 }
	 
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	 public  Map<String, Object> getResourceInfoByRoleId(  Map<String, Object> model, long roleId) {
		 
		 List treeMenusByRoleId = this.getMenusByRoleId(roleId);
		 model.put("treeMenus", treeMenusByRoleId);
		 return model;
	 }
	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public List getMenus()
	 {


	        List treeMenus = new ArrayList();
	        
	        List subMenusList = new ArrayList();
	        
	        List secondSubMenusList = new ArrayList();
	        
	        List<Resource>  resourceList =(List<Resource>) resourceMapper.selectSourceList();
	        for (Resource resource : resourceList)
	        {
	        	 /* 1.如果该节点的parentid为0则，则该节点为一级节点。
	        	  * 2.如果该节点的parentid对应节点的parentid为0，则该节点为二级节点。
	        	  * 3.如果该节点的parentid对应的节点的parentid不为0，则该节点为三级节点。
	        	  * */
	        	if(resource.getParentId() == 0)
	        	{
	        		Map<String, Object>  menu1 = new HashMap();
	        		menu1.put("displayName", resource.getName());
	        		
	        		if (resource.getIcon() == null)
	        		{
	        			menu1.put("icon", "");
	        		}
	        		else
	        		{
	        			menu1.put("icon", resource.getIcon());
	        		}
	        		
	        		if(resource.getUrl() == null)
	        		{
	        			menu1.put("url", "");
	        		}
	        		else
	        		{
	        			menu1.put("url", resource.getUrl());
	        		}
	        		
	        		
	        		if (subMenusList.size() == 0)
	        		{
	        			treeMenus.add(menu1);
	        			//subMenusList.clear();
	        		}
	        		else
	        		{
	        			if (resource.getParentId() != 0)
	        			{
	        				treeMenus.add(menu1);
	        			}
	        			else
	        			{
	        				/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经遍历完，则入列表。*/
		        			menu1.put("children", subMenusList );
		        			subMenusList = new ArrayList();
		        			treeMenus.add(menu1);
		        			//subMenusList.clear();
	        			}
	        		}
	       
	        		continue;
	        	}
	        	
	        	if(resourceList.get(resource.getParentId().intValue() - 1).getParentId() == 0)
	        	{
	        		
	        		Map<String, Object>  menu2 = new HashMap();
	        		menu2.put("displayName", resource.getName());
	        		/*menu2.put("icon", resource.getIcon());*/
	        		/*menu2.put("url", resource.getUrl());*/
	        		if (resource.getIcon() == null)
	        		{
	        			menu2.put("icon", "");
	        		}
	        		else
	        		{
	        			menu2.put("icon", resource.getIcon());
	        		}
	        		if(resource.getUrl() == null)
	        		{
	        			menu2.put("url", "");
	        		}
	        		else
	        		{
	        			menu2.put("url", resource.getUrl());
	        		}
	        		
	        		if (secondSubMenusList.size() == 0)
	        		{
	        			subMenusList.add(menu2);
	        			//secondSubMenusList.clear();
	        		}
	        		
	        		else
	        		{
	        			if(resourceList.get(resource.getId().intValue()-2).getParentId() == resource.getParentId())
	        			{
	        				subMenusList.add(menu2);
	        			}
	        			else
	        			{
	        				/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经遍历完，则入列表。*/
		        			menu2.put("children", secondSubMenusList);
		        			secondSubMenusList = new ArrayList();
		        			subMenusList.add(menu2);
		        			//secondSubMenusList.clear();
	        			}
	        		}
	        		
	        		//menu.clear();
	        		continue;
	        	}
	     		        	        	
	        	if(resourceList.get(resourceList.get(resource.getParentId().intValue() - 1).getParentId().intValue() - 1).getParentId() == 0)
	        	{
	        		
	        		/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经找完，则入列表。*/
	        		Map<String, Object>  menu3 = new HashMap();
	        		menu3.put("displayName", resource.getName());
	        		/*menu3.put("icon", resource.getIcon() );*/
	        		/*menu3.put("url", resource.getUrl() );*/
	        		if (resource.getIcon() == null)
	        		{
	        			menu3.put("icon", "");
	        		}
	        		else
	        		{
	        			menu3.put("icon", resource.getIcon());
	        		}
	        		
	        		if(resource.getUrl() == null)
	        		{
	        			menu3.put("url", "");
	        		}
	        		else
	        		{
	        			menu3.put("url", resource.getUrl());
	        		}
	        		secondSubMenusList.add(menu3);
	        		
	        	}
	            	
	        }
   
	        return treeMenus;
	 
	 }
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  List getMenusByRoleId_old(long roleId) {

	        List treeMenus = new ArrayList();
	        
	        List subMenusList = new ArrayList();
	        
	        List secondSubMenusList = new ArrayList();
	        
	        int findPos, findInnerPos;
	        
	        List<Resource>  resourceList =(List<Resource>) resourceMapper.selectSourceListByRoleId(roleId);
	        for (Resource resource : resourceList)
	        {
	        	 /* 1.如果该节点的parentid为0则，则该节点为一级节点。
	        	  * 2.如果该节点的parentid对应节点的parentid为0，则该节点为二级节点。
	        	  * 3.如果该节点的parentid对应的节点的parentid不为0，则该节点为三级节点。
	        	  * */
	        	if(resource.getParentId() == 0)
	        	{
	        		Map<String, Object>  menu1 = new HashMap();
	        		menu1.put("displayName", resource.getName());
	        		
	        		if (resource.getIcon() == null)
	        		{
	        			menu1.put("icon", "");
	        		}
	        		else
	        		{
	        			menu1.put("icon", resource.getIcon());
	        		}
	        		
	        		if(resource.getUrl() == null)
	        		{
	        			menu1.put("url", "");
	        		}
	        		else
	        		{
	        			menu1.put("url", resource.getUrl());
	        		}
	        		
	        		
	        		if (subMenusList.size() == 0)
	        		{
	        			treeMenus.add(menu1);
	        			//subMenusList.clear();
	        		}
	        		else
	        		{
        				/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经遍历完，则入列表。*/
	        			menu1.put("children", subMenusList );
	        			subMenusList = new ArrayList();
	        			treeMenus.add(menu1);
	        			//subMenusList.clear();
        			
	        		}
	       
	        		continue;
	        	}
	        	
	        	findPos = this.findResourcePosById(resourceList,resource.getParentId().intValue());
	        	if(resourceList.get(findPos).getParentId() == 0)
	        	{
	        		
	        		Map<String, Object>  menu2 = new HashMap();
	        		menu2.put("displayName", resource.getName());
	        		/*menu2.put("icon", resource.getIcon());*/
	        		/*menu2.put("url", resource.getUrl());*/
	        		if (resource.getIcon() == null)
	        		{
	        			menu2.put("icon", "");
	        		}
	        		else
	        		{
	        			menu2.put("icon", resource.getIcon());
	        		}
	        		if(resource.getUrl() == null)
	        		{
	        			menu2.put("url", "");
	        		}
	        		else
	        		{
	        			menu2.put("url", resource.getUrl());
	        		}
	        		
	        		if (secondSubMenusList.size() == 0)
	        		{
	        			subMenusList.add(menu2);
	        			//secondSubMenusList.clear();
	        		}
	        		
	        		else
	        		{
	        			findPos = this.findResourcePosById(resourceList, resource.getId().intValue()-1);
	        			
	        			if(resourceList.get(findPos).getParentId() == resource.getParentId())
	        			{
	        				subMenusList.add(menu2);
	        			}
	        			else
	        			{
	        				/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经遍历完，则入列表。*/
		        			menu2.put("children", secondSubMenusList);
		        			secondSubMenusList = new ArrayList();
		        			subMenusList.add(menu2);
		        			//secondSubMenusList.clear();
	        			}
	        		}
	        		
	        		//menu.clear();
  	        		continue;
	        	}
	        	
	        	findInnerPos = this.findResourcePosById(resourceList, resource.getParentId().intValue()); 
	        	findPos = this.findResourcePosById(resourceList, resourceList.get(findInnerPos).getParentId().intValue()); 
	        	
	        	if(resourceList.get(findPos).getParentId() == 0)
	        	{
	        		
	        		/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经找完，则入列表。*/
	        		Map<String, Object>  menu3 = new HashMap();
	        		menu3.put("displayName", resource.getName());
	        		/*menu3.put("icon", resource.getIcon() );*/
	        		/*menu3.put("url", resource.getUrl() );*/
	        		if (resource.getIcon() == null)
	        		{
	        			menu3.put("icon", "");
	        		}
	        		else
	        		{
	        			menu3.put("icon", resource.getIcon());
	        		}
	        		
	        		if(resource.getUrl() == null)
	        		{
	        			menu3.put("url", "");
	        		}
	        		else
	        		{
	        			menu3.put("url", resource.getUrl());
	        		}
	        		secondSubMenusList.add(menu3);
	        		
	        	}
	            	
	        }
      
	        return treeMenus;
	  }
	
	
		private int findResourcePosById(List<Resource>  resourceList, int Id)
		{
			for (int i =0; i < resourceList.size(); i++)
			{
				if (resourceList.get(i).getId().intValue() == Id)
				{
					return i;
				}
			}
			
			return 0;
		}

		public  List getMenusByRoleId(long roleId) {
			
			@SuppressWarnings("unchecked")
			List<Resource> menuList = this.getMenusList(roleId);
			List<Resource> resourceSecondmenuList = null;
			List<Resource> resourceThirdmenuList = null;
			List treeMenus = new ArrayList();
	        List subMenusList = new ArrayList();
	        
	        List secondSubMenusList =new ArrayList();
			
			for (Resource resource : menuList)
			{
				Map<String, Object>  menu1 = new HashMap();
        		menu1.put("displayName", resource.getName());
        		//subMenusList = new ArrayList();
        		if (resource.getIcon() == null)
        		{
        			menu1.put("icon", "");
        		}
        		else
        		{
        			menu1.put("icon", resource.getIcon());
        		}
        		
        		if(resource.getUrl() == null) 
        		{
        			menu1.put("url", ""); 
        		}
        		else
        		{
        			menu1.put("url", resource.getUrl());
        		}
        		
        		
        		resourceSecondmenuList = resource.getChildMenus();
        		if (resourceSecondmenuList != null)
        		{
        			for (Resource Secondresource : resourceSecondmenuList)
            		{
            			Map<String, Object>  menu2 = new HashMap();
    	        		menu2.put("displayName", Secondresource.getName());
    	        		//secondSubMenusList = new ArrayList();
    	        		
    	        		if (Secondresource.getIcon() == null)
    	        		{
    	        			menu2.put("icon", "");
    	        		}
    	        		else
    	        		{
    	        			menu2.put("icon", Secondresource.getIcon());
    	        		}
    	        		if(Secondresource.getUrl() == null)
    	        		{
    	        			menu2.put("url", "");
    	        		}
    	        		else
    	        		{
    	        			menu2.put("url", Secondresource.getUrl());
    	        		}
    	        		
    	        		resourceThirdmenuList = Secondresource.getChildMenus();
    	        		if(resourceThirdmenuList != null)
    	        		{
    	        			for(Resource Thirdresource : resourceThirdmenuList)
        	        		{
        	        			Map<String, Object>  menu3 = new HashMap();
        		        		menu3.put("displayName", Thirdresource.getName());
        		        		/*menu3.put("icon", resource.getIcon() );*/
        		        		/*menu3.put("url", resource.getUrl() );*/
        		        		if (Thirdresource.getIcon() == null)
        		        		{
        		        			menu3.put("icon", "");
        		        		}
        		        		else
        		        		{
        		        			menu3.put("icon", Thirdresource.getIcon());
        		        		}
        		        		
        		        		if(Thirdresource.getUrl() == null)
        		        		{
        		        			menu3.put("url", "");
        		        		}
        		        		else
        		        		{
        		        			menu3.put("url", Thirdresource.getUrl());
        		        		}
        		        		
        		        		secondSubMenusList.add(menu3);
        		        	
        	        		}
    	        		}
    	        		
    	        		
    	        		if (secondSubMenusList.size() == 0)
    	        		{
    	        			subMenusList.add(menu2);
    	        			//secondSubMenusList.clear();
    	        		}
    	        		else
    	        		{
    	        			/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经遍历完，则入列表。*/
    	        			menu2.put("children", secondSubMenusList);
    	        			secondSubMenusList = new ArrayList();
    	        			subMenusList.add(menu2);
    	        		}
    	        		
            		}
        		}
        		
        		if (subMenusList.size() == 0)
        		{
        			treeMenus.add(menu1);
        			//subMenusList.clear();
        		}
        		else
        		{
    				/*如果节点下有子节点的个数不为0，且所有当前节点的子节点都已经遍历完，则入列表。*/
        			menu1.put("children", subMenusList );
        			subMenusList = new ArrayList();
        			treeMenus.add(menu1);
        			//subMenusList.clear();
    			
        		}
        	
			}
			
			return treeMenus;
			
		}
		
		public List getMenusList(long roleId) {
		   
		    List<Resource>  resourceList =(List<Resource>) resourceMapper.selectSourceListByRoleId(roleId);
		    
		    for (int i = 0; i < resourceList.size(); i++)
		    {
		    	resourceList.get(i).setDisplayName(resourceList.get(i).getName());
	        }
		    // 最后的结果
		    List<Resource> menuList = new ArrayList<Resource>();
		    // 先找到所有的一级菜单
		    for (int i = 0; i < resourceList.size(); i++) {
		        // 一级菜单没有parentId
		        if (resourceList.get(i).getParentId() == 0) {
		            menuList.add(resourceList.get(i));
		        }
		    }
		    // 为一级菜单设置子菜单，getChild是递归调用的
		    for (Resource menu : menuList) {
		        menu.setChildMenus(this.getChild(menu.getId(), resourceList));
		    }
		    Map<String,Object> jsonMap = new HashMap<>();
		    return menuList;

		}


		/**
		 * 递归查找子菜单
		 * 
		 * @param id
		 *            当前菜单id
		 * @param rootMenu
		 *            要查找的列表
		 * @return
		 */
		private List<Resource> getChild(Long id, List<Resource> rootMenu) {
		    // 子菜单
		    List<Resource> childList = new ArrayList<>();
		    for (Resource menu : rootMenu) {
		        // 遍历所有节点，将父菜单id与传过来的id比较
		        if (menu.getParentId() != 0) {
		            if (menu.getParentId().equals(id)) {
		                childList.add(menu);
		            }
		        }
		    }
		    // 把子菜单的子菜单再循环一遍
		    for (Resource menu : childList) {// 没有url子菜单还有子菜单
		        if (/*StringUtils.isBlank(menu.getUrl())*/menu.getUrl() == null) {
		            // 递归
		            menu.setChildMenus(getChild(menu.getId(), rootMenu));
		        }
		    } // 递归退出条件
		    if (childList.size() == 0) {
		        return null;
		    }
		    return childList;
		}
		
		
		public void updateRoleResource(long roleId, String[] resources) {
			// TODO Auto-generated method stub
			/*1. 先删除已有的资源关联 ；2.插入新的关联记录到 t_role_resource表 */
			roleResourceMapper.deleteByRoleId(roleId);
			this.insertNewRoleResource(roleId, resources);
			 
		}
		
		public void insertNewRoleResource(long roleId, String[] resources)
		{
			Map<String,Object> params=new HashMap<String, Object>();
			RoleResource existRoleResource;
			if (resources == null)
			{
				return;
			}
			for(int i =0; i < resources.length; i++)
			{
				 RoleResource  roleResource = new RoleResource();
				 roleResource.setResourceId( Long.parseLong(resources[i]));
				 roleResource.setRoleId(roleId);
				 params.put("resourceId", Long.parseLong(resources[i]));
			     params.put("roleId", roleId);
				 existRoleResource = roleResourceMapper.selectByResourceId(params);
				 if (existRoleResource == null)
				 {
					 roleResourceMapper.insert(roleResource);
				 }

				 this.insertParentRoleSource(Long.parseLong(resources[i]), roleId);
			}
		}
		
		private void insertParentRoleSource(long resourceId, long roleId)
		{
			  /*1.t_resource表查找得到该节点的父节点ID； 2.t_role_resource中父节点不存在，则插入t_role_resource*/
			  long parentId;
			  long parentParentId;
			  RoleResource roleResource = null;
			  Map<String,Object> params=new HashMap<String, Object>();
			  parentId = resourceMapper.getParentIdByResourceId(resourceId);
			  if(parentId == 0)
			  {
				  return;
			  }
			
		      params.put("resourceId", parentId);
		      params.put("roleId", roleId);
			  roleResource = roleResourceMapper.selectByResourceId(params);
			  
			  if (roleResource == null)
			  {
				  roleResource = new RoleResource();
				  roleResource.setResourceId(parentId);
				  roleResource.setRoleId(roleId);
				  roleResourceMapper.insertSelective(roleResource);
			  }
			  
			  parentParentId = resourceMapper.getParentIdByResourceId(parentId);
			  if(parentParentId == 0)
			  {
				  return;
			  }
			  
			  params.put("resourceId", parentParentId);
		      params.put("roleId", roleId);
			  roleResource = roleResourceMapper.selectByResourceId(params);
			  
			  if (roleResource == null)
			  {
				  roleResource = new RoleResource();
				  roleResource.setResourceId(parentParentId);
				  roleResource.setRoleId(roleId);
				  roleResourceMapper.insertSelective(roleResource);
			  }
	
		}
		
		public  Map<String, Object> getResourceInfoModel(Map<String, Object> model) {
			
			List moduleList = new ArrayList();
			
			Long parentId,id ;
			
			List <Long> idList;
			
			List  resourceBranchList = null;
			
			Map<String, Object> resMap;
			
			Map<String, Object>  module;
			
			Resource resource;
			
			List<Resource>  resourceList =(List<Resource>) resourceMapper.selectSourceList();
			
			Map<String, Object> resourceMapBranch = this.getResourceMapBranch(resourceList);
			
			for(Map.Entry<String, Object> entry:resourceMapBranch.entrySet())
	        {
				idList = (List <Long>)(entry.getValue());
				parentId = Long.parseLong(entry.getKey());
				
				resourceBranchList = new  ArrayList();
				for(int i=0; i < idList.size(); i++)
				{
					  resMap = new HashMap();
					  resource = getResourceById(idList.get(i), resourceList);
					  resMap.put("displayName",resource.getName());
					  resMap.put("value",resource.getId());
					  
					  resourceBranchList.add(resMap);
				}
				
				module = new HashMap();
				resource = getResourceById(parentId, resourceList);
				module.put("diaplayName", resource.getName());
				module.put("resources", resourceBranchList);
    		  
				moduleList.add(module);
				
	        }
			
			model.put("modules", moduleList);

		    return model;
			
		}
		
		private  Map<String, Object> getResourceMapBranch(List<Resource>  resourceList)
		{
			Map<String, Object>  resouceBranchMap = new HashMap<String,Object>();
			List<Resource>  innerResourceList = new ArrayList<Resource>();
			innerResourceList.addAll(resourceList);
			Resource resource, parentResource, parentParentSource;
			ArrayList<Long> resourceBranchList = null;
			
			for(int i =0; i < resourceList.size(); i ++)
			{
				  resource = resourceList.get(i);
				  if(resource.getParentId().equals(new Long(0)))
		    	  {
		    		  resourceBranchList = new  ArrayList();
		    		  //resourceBranchList.add(new Long(121312));
		    		  
		    		  resouceBranchMap.put(String.valueOf(resource.getId()), resourceBranchList);
		    	  }
			}
			
			for(int i = 0; i < resourceList.size(); i ++)
			{
				resource = resourceList.get(i);
				if(resource.getParentId().equals(new Long(0)))
				{
					continue;
				}
				else
				{
					parentResource = this.getResourceById(resource.getParentId(), resourceList);
					if (parentResource.getParentId().equals(new Long(0)))
					{
						 resourceBranchList = (ArrayList<Long>)resouceBranchMap.get(String.valueOf(resource.getParentId()));
						 resourceBranchList.add(resource.getId());
					}
					else
					{
						parentParentSource = this.getResourceById(parentResource.getParentId(), resourceList);
						logger.info("parentId: " + parentResource.getParentId());
						if (parentParentSource.getParentId().equals(new Long(0)))
						{
							resourceBranchList = (ArrayList<Long>)resouceBranchMap.get(String.valueOf(parentResource.getParentId()));
							resourceBranchList.add(resource.getId());
						}
					}
				}
				
			}
						
			return resouceBranchMap;
			
		}
		
		
		private Resource getResourceById(Long id, List<Resource>  resourceList)
		{
			Resource 	resource ;
			for(int i =0; i < resourceList.size(); i ++)
			{
				resource = resourceList.get(i);
				if (resource.getId().equals(id))
				{
					return resource;
					
				}
			}
			return null;
		}
				
		
		
				
	
}
