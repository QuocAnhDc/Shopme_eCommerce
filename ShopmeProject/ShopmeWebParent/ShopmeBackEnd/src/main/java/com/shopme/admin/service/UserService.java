package com.shopme.admin.service;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.admin.exceptionHandel.UserNotFoundException;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 */
@Service
@Transactional
public class UserService {

    public static final int USER_PER_PAGE =5;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * getByEmail
     * @param email
     * email of user
     * @return
     * return a user has email
     */
    public User getByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    /**
     * listAll
     * @return
     * return a list user sort by field firstName
     */
    public List<User> listAll(){
        return (List<User>) userRepository.findAll(Sort.by("firstName").ascending());
    }

    /**
     *listByPage
     * @param pageNum
     * current page
     * @param sortField
     * sort entity like field
     * @param sortDir
     * sort entity like dir
     * @param keyword
     * sort entity if entity has keyword
     * @return
     * a page has list of user
     */
    public Page<User> listByPage(int pageNum, String sortField, String sortDir,String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1,USER_PER_PAGE, sort );

        if(keyword != null){
            return userRepository.findAll(keyword,pageable);
        }

        return userRepository.findAll(pageable);
    }

    /**
     * listRoles
     * @return
     * List roles in database
     */
    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    /**
     * save
     * @param user
     * user entity
     * @return
     * save user in database
     */
    public User save(User user){
        boolean isUpdatingUser = (user.getId() != null);
        if(isUpdatingUser){
            User existingUser = userRepository.findById(user.getId()).get();

            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    /**
     * updateAccount
     * @param userInForm
     * get data user in user form
     * @return
     * save user from user form
     */
    public User updateAccount(User userInForm){
        User userInDB = userRepository.findById(userInForm.getId()).get();
        if(!userInForm.getPassword().isEmpty()){
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }
        if(userInForm.getPhotos() != null){
            userInDB.setPhotos(userInForm.getPhotos());
        }

        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return userRepository.save(userInDB);
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email){
        User userByEmail = userRepository.getUserByEmail(email);

        if(userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if(isCreatingNew){
            if(userByEmail != null) return false;
        } else {
            if(userByEmail.getId() != id){
                return false;
            }
        }

        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try{
            return userRepository.findById(id).get();
        }
        catch (NoSuchElementException e){
            throw new UserNotFoundException("Could not find any user with ID "+ id);
        }

    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Could not find any user with ID "+ id);
        }

        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled){
        userRepository.updateEnabledStatus(id,enabled);
    }
}
