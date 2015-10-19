package com.crm.guard.service;

import com.crm.guard.dao.GroupAuthorityDAO;
import com.crm.guard.dao.GroupDAO;
import com.crm.guard.dao.GroupMemberDAO;
import com.crm.guard.dao.UserDAO;
import com.crm.guard.entity.Group;
import com.crm.guard.entity.GroupMember;
import com.crm.guard.entity.User;
import com.crm.guard.service.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

    public final static String systemUserName = "system";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private GroupMemberDAO groupMemberDAO;

    @Autowired
    private GroupAuthorityDAO groupAuthorityDAO;


    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return userDAO.get(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User system() {
        return userDAO.get(systemUserName);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<GrantedAuthority> findAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        final List<Long> groupsId = groupMemberDAO.findGroupsId(user);
        final List<String> authorities1 = groupAuthorityDAO.findAuthorities(groupsId);
        for (String authority : authorities1) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        return authorities;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDAO.getAll();  // TODO use cache
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public GroupMemberDAO getGroupMemberDAO() {
        return groupMemberDAO;
    }

    public void setGroupMemberDAO(GroupMemberDAO groupMemberDAO) {
        this.groupMemberDAO = groupMemberDAO;
    }

    public GroupAuthorityDAO getGroupAuthorityDAO() {
        return groupAuthorityDAO;
    }

    public void setGroupAuthorityDAO(GroupAuthorityDAO groupAuthorityDAO) {
        this.groupAuthorityDAO = groupAuthorityDAO;
    }

    @Transactional
    @Override
    public void createWorkUsers() {
        Group users = groupDAO.byName("Users");
        if (users == null) {
            throw new IllegalStateException("Нет группы Users");
        }

        Group operators = groupDAO.byName("Operators");
        if (operators == null) {
            throw new IllegalStateException("Нет группы Operators");
        }

        Group admins = groupDAO.byName("Administrators");
        if (admins == null) {
            throw new IllegalStateException("Нет группы Administrators");
        }

        createPotapova(users, operators);
        createTuvaeva(users, operators);
        createUlianova(users, operators);
        createKurganova(users, operators, admins);
        createAdmin(users, admins);
        createSystem(users, admins);
    }

    private void createAdmin(Group users, Group admins) {
        if (userDAO.get("admin") == null) {
            User user;
            GroupMember groupMember;

            user = new User();
            user.setName("admin");
            user.setPassword("admin");
            user.setLastName("admin");
            user.setFirstName("admin");
            user.setMiddleName("admin");
            user.setEnabled(true);
            userDAO.saveUser(user);

            groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(admins);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);
        }
    }


    private void createSystem(Group users, Group admins) {
        if (userDAO.get("system") == null) {
            User user;
            GroupMember groupMember;

            user = new User();
            user.setName("system");
            user.setPassword("system");
            user.setLastName("system");
            user.setFirstName("system");
            user.setMiddleName("system");
            user.setEnabled(true);
            userDAO.saveUser(user);

            groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(admins);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);
        }
    }

    private void createKurganova(Group users, Group operators, Group admins) {
        if (userDAO.get("kurganova") == null) {
            User user;
            GroupMember groupMember;


            user = new User();
            user.setName("kurganova");
            user.setLastName("Курганова");
            user.setFirstName("Д.");
            user.setMiddleName("А.");
            user.setPassword("kurganova");
            user.setEnabled(true);
            userDAO.saveUser(user);

            groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(operators);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(admins);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);
        }
    }

    private void createUlianova(Group users, Group operators) {
        if (userDAO.get("ulianova") == null) {
            GroupMember groupMember;
            User user = new User();
            user.setName("ulianova");
            user.setLastName("Ульянова");
            user.setFirstName("С.");
            user.setMiddleName("П.");
            user.setPassword("ulianova");
            user.setEnabled(true);
            userDAO.saveUser(user);

            groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(operators);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);
        }
    }

    private void createTuvaeva(Group users, Group operators) {
        if (userDAO.get("tuvaeva") == null) {
            User user = new User();
            user.setName("tuvaeva");
            user.setLastName("Туваева");
            user.setFirstName("М.");
            user.setMiddleName("А.");
            user.setPassword("tuvaeva");
            user.setEnabled(true);
            userDAO.saveUser(user);

            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(operators);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);
        }
    }

    private void createPotapova(Group users, Group operators) {
        if (userDAO.get("potapova") == null) {
            User user = new User();

            user.setName("potapova");
            user.setLastName("Потапова");
            user.setFirstName("Н.");
            user.setMiddleName("А.");
            user.setPassword("potapova");
            user.setEnabled(true);
            userDAO.saveUser(user);

            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(operators);
            groupMember.setUser(user);
            groupMemberDAO.saveOrUpdate(groupMember);
        }
    }

}
