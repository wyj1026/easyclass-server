package cn.tegongdete.easyclass.service;

import cn.tegongdete.easyclass.mapper.UserClassRoleMapper;
import cn.tegongdete.easyclass.model.UserClassRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    @Autowired
    private UserClassRoleMapper userClassRoleMapper;

    public List<Integer> getClassesByUserId(Integer id, String role) {
        List<UserClassRole> classRoles = userClassRoleMapper.selectList(new QueryWrapper<UserClassRole>()
                .lambda().eq(UserClassRole::getUserId, id).eq(UserClassRole::getRole, role));
        List<Integer> classids = classRoles.stream().map(classRole -> {return classRole.getClassId();}).collect(Collectors.toList());
        return classids;
    }

}
