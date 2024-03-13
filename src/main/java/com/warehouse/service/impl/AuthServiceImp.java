package com.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.warehouse.dto.AssignAuthDto;
import com.warehouse.entity.Auth;
import com.warehouse.mapper.AuthMapper;
import com.warehouse.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImp implements AuthService {

	//注入AuthMapper
	@Autowired
	private AuthMapper authMapper;

	//注入Redis模板
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 根据用户id查询用户权限(菜单)树的业务方法
	 */
	@Override
	public List<Auth> findAuthTree(int userId){
		//先从redis中查询缓存,查到的是权限(菜单)树List<Auth>转的json串
		String authTreeListJson = redisTemplate.opsForValue().get(userId + ":authTree");
		if(StringUtils.hasText(authTreeListJson)){//redis中查到缓存
			//将json串转回权限(菜单)树List<Auth>并返回
			List<Auth> authTreeList = JSON.parseArray(authTreeListJson, Auth.class);
			return authTreeList;
		}
		//redis中没有查到缓存,从数据库表中查询所有权限(菜单)
		List<Auth> allAuthList = authMapper.findAllAuth(userId);
		//将所有权限(菜单)List<Auth>转成权限(菜单)树List<Auth>
		List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
		//将权限(菜单)树List<Auth>转成json串并保存到redis
		redisTemplate.opsForValue().set(userId + ":authTree", JSON.toJSONString(authTreeList));
		//返回权限(菜单)树List<Auth>
		return authTreeList;
	}

	//将所有权限(菜单)转成权限(菜单)树的递归算法
	private List<Auth> allAuthToAuthTree(List<Auth> allAuthList, int parentId){
		//获取父权限(菜单)id为参数parentId的所有权限(菜单)
		//【parentId最初为0,即最初查的是所有一级权限(菜单)】
		List<Auth> authList = new ArrayList<>();
		for (Auth auth : allAuthList) {
			if(auth.getParentId()==parentId){
				authList.add(auth);
			}
		}
		//查询List<Auth> authList中每个权限(菜单)的所有子级权限(菜单)
		for (Auth auth : authList) {
			List<Auth> childAuthList = allAuthToAuthTree(allAuthList, auth.getAuthId());
			auth.setChildAuth(childAuthList);
		}
		return authList;
	}

	//查询整个权限(菜单)树的业务方法
	@Override
	public List<Auth> allAuthTree() {
		//先从redis中查询缓存,查到的是整个权限(菜单)树List<Auth>转的json串
		String allAuthTreeJson = redisTemplate.opsForValue().get("all:authTree");
		if(StringUtils.hasText(allAuthTreeJson)){//redis中查到缓存
			//将json串转回整个权限(菜单)树List<Auth>并返回
			List<Auth> allAuthTreeList = JSON.parseArray(allAuthTreeJson, Auth.class);
			return allAuthTreeList;
		}
		//redis中没有查到缓存,从数据库表中查询所有权限(菜单)
		List<Auth> allAuthList = authMapper.getAllAuth();
		//将所有权限(菜单)List<Auth>转成整个权限(菜单)树List<Auth>
		List<Auth> allAuthTreeList = allAuthToAuthTree(allAuthList, 0);
		//将整个权限(菜单)树List<Auth>转成json串并保存到redis
		redisTemplate.opsForValue().set("all:authTree", JSON.toJSONString(allAuthTreeList));
		//返回整个权限(菜单)树List<Auth>
		return allAuthTreeList;
	}

	//给角色分配权限(菜单)的业务方法
	@Transactional//事务处理
	@Override
	public void assignAuth(AssignAuthDto assignAuthDto) {

		//拿到角色id
		Integer roleId = assignAuthDto.getRoleId();
		//拿到给角色分配的所有权限(菜单)id
		List<Integer> authIds = assignAuthDto.getAuthIds();

		//根据角色id删除给角色已分配的所有权限(菜单)
		authMapper.delAuthByRoleId(roleId);

		//循环添加角色权限(菜单)关系
		for (Integer authId : authIds) {
			authMapper.insertRoleAuth(roleId, authId);
		}
	}

}
