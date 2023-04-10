package com.successfulliferestapi.Idea.controllers;

import com.successfulliferestapi.Idea.models.dto.AddIdeaDTO;
import com.successfulliferestapi.Idea.models.dto.EditIdeaDTO;
import com.successfulliferestapi.Idea.services.IdeaService;
import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ideas")
@RequiredArgsConstructor
public class IdeaController {
    private final IdeaService ideaService;

    @PostMapping()
    public ResponseEntity<?> addIdea(@Valid @RequestBody AddIdeaDTO addIdeaDTO,
                                     BindingResult result,
                                     Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.status(201).body(ideaService.add(user, addIdeaDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PatchMapping("/{ideaId}")
    public ResponseEntity<?> updateIdea(@PathVariable Long ideaId,
                                        @RequestBody EditIdeaDTO editIdeaDTO,
                                        Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(ideaService.update(ideaId,user.getId(),editIdeaDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIdea(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(ideaService.deleteIdea(id, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get Idea by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getIdeaById(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(ideaService.getIdeaById(id, user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get all ideas
    @GetMapping
    public ResponseEntity<?> getAllIdeas(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "25") int size,
                                         Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            return ResponseEntity.ok(ideaService.findByUserId(user.getId(), pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get all ideas by tag name
    @GetMapping("/tags/{tagName}")
    public ResponseEntity<?> getIdeasByTag(@PathVariable String tagName,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "25") int size,
                                           Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            return ResponseEntity.ok(ideaService.getIdeasByTag(user.getId(), tagName, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get all tags
    @GetMapping("/tags")
    public ResponseEntity<?> getUserIdeaTags(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(ideaService.getUserIdeaTags(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET all goal ideas
    @GetMapping("/goals/{goalId}")
    public ResponseEntity<?> getAllGoalIdeas(@PathVariable Long goalId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "25") int size,
                                             Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            return ResponseEntity.ok(ideaService.getGoalIdeas(user.getId(), goalId, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get All goal ideas by tag
    @GetMapping("/goals/{goalId}/tags/{tagName}")
    public ResponseEntity<?> getAllGoalIdeasByTag(@PathVariable Long goalId,
                                                  @PathVariable String tagName,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "25") int size,
                                                  Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            return ResponseEntity.ok(ideaService.getGoalIdeasByTag(user.getId(), goalId, tagName, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get all goal ideas tags
    @GetMapping("/tags/goals/{goalId}")
    public ResponseEntity<?> getGoalIdeasTags(Authentication authentication, @PathVariable Long goalId) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(ideaService.getGoalIdeasTags(user.getId(),goalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
