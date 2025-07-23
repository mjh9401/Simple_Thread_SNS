package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.exception.follow.FollowAlreadyExistsException;
import com.example.board.exception.follow.FollowNotFoundException;
import com.example.board.exception.follow.InvalidFollowException;
import com.example.board.exception.post.PostNotFoundException;
import com.example.board.exception.user.UserAlreadyExistsException;
import com.example.board.exception.user.UserNotAllowedException;
import com.example.board.exception.user.UserNotFoundException;
import com.example.board.model.entity.FollowEntity;
import com.example.board.model.entity.LikeEntity;
import com.example.board.model.entity.PostEntity;
import com.example.board.model.entity.UserEntity;
import com.example.board.model.user.Follower;
import com.example.board.model.user.LikedUser;
import com.example.board.model.user.User;
import com.example.board.model.user.UserAuthenticationResponse;
import com.example.board.model.user.UserPatchRequestBody;
import com.example.board.repository.FollowEntityRepository;
import com.example.board.repository.LikeEntityRepository;
import com.example.board.repository.PostEntityRepository;
import com.example.board.repository.UserEntityRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private FollowEntityRepository followEntityRepository;
    @Autowired
    private PostEntityRepository postEntityRepository;
    @Autowired
    private LikeEntityRepository likeEntityRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
   


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username).orElseThrow(
            ()-> new UserNotFoundException(username)
        );
    }

    public User signUp(String username, String password) {
        userEntityRepository
            .findByUsername(username)
            .ifPresent(
                user ->{
                    throw new UserAlreadyExistsException(); 
                });
        
        var savedUserEntity = userEntityRepository.save(UserEntity.of(username, passwordEncoder.encode(password)));

        return User.from(savedUserEntity);
    }

    public UserAuthenticationResponse authenticate(String username, String password) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        
        if(passwordEncoder.matches(password, userEntity.getPassword())){
            var accessToken = jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(accessToken);
        }else{
            throw new UserNotFoundException();
        }
    }

    public List<User> getUsers(String query, UserEntity currentUser) {

        List<UserEntity> userEntities;
        
       if(query != null && !query.isBlank()){
            userEntities = userEntityRepository.findByUsernameContaining(query);
        }else{
            userEntities = userEntityRepository.findAll();
        }

        return userEntities.stream().map(userEnity-> getUserWithFollowingsStatus(userEnity, currentUser)).toList();

    }

    public User getUser(String username, UserEntity currentUser) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));  
        
        return getUserWithFollowingsStatus(userEntity, currentUser);    
    }

    private User getUserWithFollowingsStatus(UserEntity userEntity, UserEntity currentUser){
        var isFollowing = followEntityRepository.findByFollowerAndFollowing(currentUser, userEntity).isPresent();

        return User.from(userEntity,isFollowing);
    }

    public User updateUser(String username, UserPatchRequestBody userPatchRequestBody, UserEntity currentUser) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        if(!userEntity.equals(currentUser)){
            throw new UserNotAllowedException();
        }

        if(userPatchRequestBody.description() != null){
            userEntity.setDescription(userPatchRequestBody.description());
        }
        
        return User.from(userEntityRepository.save(userEntity));
    }

    @Transactional
    public User follow(String username, UserEntity currentUser) {
        var following = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        
        if(following.equals(currentUser)){
            throw new InvalidFollowException("A user cannot follow themselves.");
        }

        followEntityRepository.findByFollowerAndFollowing(currentUser,following)
            .ifPresent(follow ->{
                throw new FollowAlreadyExistsException(currentUser,following);
            }
        );
    
        followEntityRepository.save(FollowEntity.of(currentUser, following));

        following.setFollowersCount(following.getFollowersCount() + 1);
        currentUser.setFollwingsCount(following.getFollwingsCount()+1);

        userEntityRepository.saveAll(List.of(following,currentUser));

        return User.from(following,true);
    }

    @Transactional
    public User unfollow(String username, UserEntity currentUser) {
        var following = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        
        if(following.equals(currentUser)){
            throw new InvalidFollowException("A user cannot unfollow themselves.");
        }

        var followEntity = followEntityRepository.findByFollowerAndFollowing(currentUser,following)
            .orElseGet(() ->{
                throw new FollowNotFoundException(currentUser,following);
            }
        );
    
        followEntityRepository.delete(followEntity);

        following.setFollowersCount(Math.max(0, following.getFollowersCount() - 1));
        currentUser.setFollwingsCount(Math.max(0, following.getFollwingsCount() - 1));

        userEntityRepository.saveAll(List.of(following,currentUser));

        return User.from(following,false);
    }

    public List<Follower> getFollowersByUsername(String username, UserEntity currentUser) {
        var following = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        
        var followEntity = followEntityRepository.findByFollowing(following);
        
        return followEntity.stream()
            .map(follow -> Follower.from(
                getUserWithFollowingsStatus(follow.getFollower(), currentUser), follow.getCreatedDateTime())
            ).toList();
    }

    public List<User> getFollowingsByUsername(String username, UserEntity currentUser) {
        var follower = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        
        var followEntity = followEntityRepository.findByFollower(follower);

        return followEntity.stream().map(follow -> getUserWithFollowingsStatus(follow.getFollowing(), currentUser)).toList();
    }

    public List<LikedUser> getLikedUsersByPostId(Long postId, UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId)
                            .orElseThrow(() -> new PostNotFoundException(postId));
        var likeEntites = likeEntityRepository.findByPost(postEntity);

        return likeEntites.stream().map(likeEntity->getLikedUserWithFollowingsStatus(likeEntity, postEntity, currentUser)).toList();
    }

    public List<LikedUser> getLikedUsersByUser(String username, UserEntity currentUser) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));

        var postEntities = postEntityRepository.findByUser(userEntity);
        
        return postEntities.stream().flatMap(
            postEntity->likeEntityRepository.findByPost(postEntity).stream().map(likeEntity->getLikedUserWithFollowingsStatus(likeEntity, postEntity, currentUser))
        ).toList();
    }

   

    private LikedUser getLikedUserWithFollowingsStatus(LikeEntity likeEntity, PostEntity postEntity, UserEntity currentUser){
        var likedUserEntity = likeEntity.getUser();
        var userWithFollowingStatus = getUserWithFollowingsStatus(likedUserEntity, currentUser);
            
        return LikedUser.from(userWithFollowingStatus,postEntity.getPostId() , likeEntity.getCreatedDateTime());
    }

   

    

}
