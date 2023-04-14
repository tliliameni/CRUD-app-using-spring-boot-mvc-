package com.example.demo.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Post;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PostRepository;


@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Post")
public class PostRestController {
	   @Autowired
	    private  PostRepository postRepository;

	    @GetMapping("/posts")
	    public List< Post> getAllPostss() {
	        return  postRepository.findAll();
	    }

	    @GetMapping("/posts/{id}")
	    public ResponseEntity< Post> getPostsById(@PathVariable(value = "id") Long postId)
	        throws ResourceNotFoundException {
	    	 Post post =  postRepository.findById(postId)
	          .orElseThrow(() -> new ResourceNotFoundException("Post not found for this id :: " + postId));
	        return ResponseEntity.ok().body(post);
	    }
	    
	    @PostMapping("/addpost")
	    public  Post createPost(@Valid @RequestBody  Post post) {
	        return  postRepository.save(post);
	    }

	    @PutMapping("/editPost/{id}")
	    public ResponseEntity< Post> updatePost(@PathVariable(value = "id") Long postId,
	         @Valid @RequestBody  Post postDetails) throws ResourceNotFoundException {
	    	 Post post =  postRepository.findById(postId)
	        .orElseThrow(() -> new ResourceNotFoundException("Post not found for this id :: " + postId));

	    	 post.setTitle(postDetails.getTitle());
	    	 post.setType(postDetails.getType());
	    	 post.setDescription(postDetails.getDescription());
	    	 post.setPrice(postDetails.getPrice());
	         final  Post updatePost =  postRepository.save(post);
	        return ResponseEntity.ok(updatePost);
	    }

	    @DeleteMapping("/delete/{id}")
	    public Map<String, Boolean> deletePost(@PathVariable(value = "id") Long postId)
	         throws ResourceNotFoundException {
	    	 Post post =  postRepository.findById(postId)
	       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + postId));
	    	 postRepository.delete(post);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
}
