package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.exception.post.PostNotFoundException;
import com.example.board.exception.reply.ReplyNotFoundException;
import com.example.board.exception.user.UserNotAllowedException;
import com.example.board.model.entity.ReplyEntity;
import com.example.board.model.entity.UserEntity;
import com.example.board.model.reply.Reply;
import com.example.board.model.reply.ReplyPatchRequestBody;
import com.example.board.model.reply.ReplyPostRequestBody;
import com.example.board.repository.PostEntityRepository;
import com.example.board.repository.ReplyEntityRepository;
import com.example.board.repository.UserEntityRepository;

@Service
public class ReplyService {
    @Autowired
    private ReplyEntityRepository replyEntityRepository;
    @Autowired
    private PostEntityRepository postEntityRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;


    public List<Reply> getRepliesByPostId(Long postId){
        var postEntity = postEntityRepository.findById(postId)
                            .orElseThrow(() -> new PostNotFoundException(postId));
        var replyEntities = replyEntityRepository.findByPost(postEntity);

        return replyEntities.stream().map(Reply::from).toList();
    }

    @Transactional
    public Reply createReply(Long postId, ReplyPostRequestBody replyPostRequestBody, UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId)
                            .orElseThrow(() -> new PostNotFoundException(postId));
        
        var replyEntity = replyEntityRepository.save( ReplyEntity.of(replyPostRequestBody.body(), currentUser, postEntity));

        postEntity.setRepliesCount(postEntity.getRepliesCount() + 1);

        return Reply.from(replyEntity);
    }   

  

    public Reply updateReply(ReplyPatchRequestBody replyPatchRequestBody, Long postId, Long replyId, UserEntity currentUser) {
        postEntityRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        var replyEntity = replyEntityRepository.findById(replyId).orElseThrow(()-> new ReplyNotFoundException(replyId));

        if(!replyEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }

        replyEntity.setBody(replyPatchRequestBody.body());
        return Reply.from(replyEntityRepository.save(replyEntity));    
    }

    @Transactional
    public void deleteReply(Long postId, Long replyId, UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        var replyEntity = replyEntityRepository.findById(replyId).orElseThrow(()-> new ReplyNotFoundException(replyId));

        if(!replyEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }

        replyEntityRepository.delete(replyEntity);
        postEntity.setRepliesCount(Math.max(0, postEntity.getRepliesCount() - 1));
        postEntityRepository.save(postEntity);
    }   

}
