package com.warehouse.service.impl;

import com.warehouse.entity.Result;
import com.warehouse.entity.User;
import com.warehouse.mapper.UserMapper;
import com.warehouse.page.Page;
import com.warehouse.service.UserService;
import com.warehouse.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    //注入UserMapper
    @Autowired
    private UserMapper userMapper;

    //根据用户名查找用户的业务方法
    @Override
    public User findUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    //分页查询用户的业务方法
    @Override
    public Page queryUserPage(Page page, User user) {

        //查询用户总行数
        int userCount = userMapper.selectUserCount(user);

        //分页查询用户
        List<User> userList = userMapper.selectUserPage(page, user);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(userCount);
        page.setResultList(userList);

        return page;
    }

    //添加用户的业务方法
    @Override
    public Result saveUser(User user) {
        //根据用户名查询用户
        User oldUser = userMapper.findUserByCode(user.getUserCode());
        if (oldUser != null) {//用户已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "该用户已存在！");
        }
        //用户不存在,对密码加密,添加用户
        String userPwd = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(userPwd);
        userMapper.insertUser(user);
        return Result.ok("添加用户成功！");
    }

    //修改用户状态的业务方法
    @Override
    public Result updateUserState(User user) {
        //根据用户id修改用户状态
        int i = userMapper.updateUserState(user);
        if (i > 0) {
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
    }

    //根据用户id删除用户的业务方法
    @Override
    public int deleteUserById(Integer userId) {
        //根据用户id修改用户状态为删除状态
        return userMapper.setUserDelete(userId);
    }

    //修改用户昵称的业务方法
    @Override
    public Result updateUserName(User user) {
        //根据用户id修改用户昵称
        int i = userMapper.updateNameById(user);
        if (i > 0) {//修改成功
            return Result.ok("用户修改成功！");
        }
        //修改失败
        return Result.err(Result.CODE_ERR_BUSINESS, "用户修改失败！");
    }

    //重置密码的业务方法
    @Override
    public Result resetPwd(Integer userId) {

        //创建User对象并保存用户id和加密后的重置密码123456
        User user = new User();
        user.setUserId(userId);
        user.setUserPwd(DigestUtil.hmacSign("123456"));

        //根据用户id修改密码
        int i = userMapper.updatePwdById(user);

        if (i > 0) {//密码修改成功
            return Result.ok("密码重置成功！");
        }
        //密码修改失败
        return Result.err(Result.CODE_ERR_BUSINESS, "密码重置失败！");
    }
}
