package frgp.utn.edu.ar.quepasa.service.impl;


import frgp.utn.edu.ar.quepasa.data.request.post.PostCreateRequest;
import frgp.utn.edu.ar.quepasa.data.request.post.PostPatchEditRequest;
import frgp.utn.edu.ar.quepasa.model.Post;
import frgp.utn.edu.ar.quepasa.model.PostSubtype;
import frgp.utn.edu.ar.quepasa.model.User;
import frgp.utn.edu.ar.quepasa.model.geo.Neighbourhood;
import frgp.utn.edu.ar.quepasa.repository.PostRepository;
import frgp.utn.edu.ar.quepasa.repository.PostSubtypeRepository;
import frgp.utn.edu.ar.quepasa.repository.UserRepository;
import frgp.utn.edu.ar.quepasa.repository.geo.NeighbourhoodRepository;
import frgp.utn.edu.ar.quepasa.service.OwnerService;
import frgp.utn.edu.ar.quepasa.service.PostService;
import frgp.utn.edu.ar.quepasa.service.VoteService;
import frgp.utn.edu.ar.quepasa.service.validators.PostSubtypeObjectValidatorBuilder;
import frgp.utn.edu.ar.quepasa.service.validators.geo.neighbours.NeighbourhoodObjectValidatorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;


@Service("postService")
public class PostServiceImpl implements PostService {

    private final OwnerService ownerService;
    private final VoteService voteService;
    private final PostRepository postRepository;
    private final PostSubtypeRepository postSubtypeRepository;
    private final UserRepository userRepository;
    private final NeighbourhoodRepository neighbourhoodRepository;

    @Autowired
    public PostServiceImpl(
            OwnerService ownerService, VoteService voteService,
            PostRepository postRepository,
            PostSubtypeRepository postSubtypeRepository,
            UserRepository userRepository,
            NeighbourhoodRepository neighbourhoodRepository
    ) {
        this.ownerService = ownerService;
        this.voteService = voteService;
        this.postRepository = postRepository;
        this.postSubtypeRepository = postSubtypeRepository;
        this.userRepository = userRepository;
        this.neighbourhoodRepository = neighbourhoodRepository;
    }

    @Override
    public Page<Post> listPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(voteService::populate);
    }

    @Override
    public Post findById(Integer id) {
        return voteService.populate(postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found")));
    }

    @Override
    public Page<Post> findByOp(Integer originalPoster, Pageable pageable) {
        User user = userRepository.findById(originalPoster)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return postRepository.findByOwner(user, pageable).map(voteService::populate);
    }

    @Override
    public Post create(PostCreateRequest newPost, User originalPoster) {
        Post post = new Post();
        post.setOwner(originalPoster);
        post.setAudience((newPost.getAudience()));
        post.setTitle(newPost.getTitle());
        var subtype = new PostSubtypeObjectValidatorBuilder(newPost.getSubtype(), postSubtypeRepository)
                .isActive(postSubtypeRepository)
                .build();
        post.setSubtype(subtype);
        post.setDescription(newPost.getDescription());
        var neighbourhood = new NeighbourhoodObjectValidatorBuilder(newPost.getNeighbourhood(), neighbourhoodRepository)
                .isActive(neighbourhoodRepository)
                .build();
        post.setNeighbourhood(neighbourhood);
        post.setTimestamp(newPost.getTimestamp());
        post.setTags(newPost.getTags());
        postRepository.save(post);
        return voteService.populate(post);
    }

    @Override
    public Post update(Integer id, PostPatchEditRequest newPost, User originalPoster) throws AccessDeniedException {
        Post post = findById(id);
        ownerService.of(post)
                .isOwner()
                .isAdmin()
                .orElseFail();
        if(newPost.getTitle() != null) post.setTitle(newPost.getTitle());
        if(newPost.getSubtype() != null) {
            var subtype = new PostSubtypeObjectValidatorBuilder(newPost.getSubtype(), postSubtypeRepository)
                    .isActive(postSubtypeRepository)
                    .build();
            post.setSubtype(subtype);
        }
        if(newPost.getDescription() != null) post.setDescription(newPost.getDescription());
        if(newPost.getNeighbourhood() != null) {
            var neighbourhood = new NeighbourhoodObjectValidatorBuilder(newPost.getNeighbourhood(), neighbourhoodRepository)
                    .isActive(neighbourhoodRepository)
                    .build();
            post.setNeighbourhood(neighbourhood);
        }
        if(newPost.getTags() != null) post.setTags(newPost.getTags());
        postRepository.save(post);
        return voteService.populate(post);
    }

    @Override
    public void delete(Integer id, User originalPoster) throws AccessDeniedException {
        Post post = findById(id);
        ownerService.of(post)
                .isOwner()
                .isAdmin()
                .isModerator()
                .orElseFail();
        post.setActive(false);
        postRepository.save(post);
    }
}
