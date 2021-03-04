package com.Msoftwares.Twitter.Clone.Service;

import com.Msoftwares.Twitter.Clone.Model.Post;
import com.Msoftwares.Twitter.Clone.Repo.PostRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepo postRepo;
    private final Scheduler dbScheduler;

    public PostService(PostRepo postRepo, Scheduler dbScheduler){
        this.postRepo = postRepo;
        this.dbScheduler = dbScheduler;
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<Post> save(Post post){
        return Mono.fromCallable(() -> postRepo.save(post)).publishOn(dbScheduler);
    }

    public Flux<Post> getPosts(){
        return Flux.fromIterable(postRepo.findAll()).publishOn(dbScheduler);
    }

    public Flux<Post> getRelevantPosts(String screenName){
        return Flux.fromIterable(postRepo
                .findByPostUser_ScreenNameOrContentContains(screenName, "@" + screenName))
                .publishOn(dbScheduler);
    }

}
