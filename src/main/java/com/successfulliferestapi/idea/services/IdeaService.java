package com.successfulliferestapi.Idea.services;

import com.successfulliferestapi.Goal.constants.GoalMessages;
import com.successfulliferestapi.Goal.exceptions.GoalException;
import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Goal.repositories.GoalRepository;
import com.successfulliferestapi.Idea.constants.IdeaMessages;
import com.successfulliferestapi.Idea.exceptions.IdeaException;
import com.successfulliferestapi.Idea.models.dto.AddIdeaDTO;
import com.successfulliferestapi.Idea.models.dto.IdeaSuccessResponseDTO;
import com.successfulliferestapi.Idea.models.dto.EditIdeaDTO;
import com.successfulliferestapi.Idea.models.dto.IdeaDTO;
import com.successfulliferestapi.Idea.models.entity.Idea;
import com.successfulliferestapi.Idea.models.entity.IdeaTag;
import com.successfulliferestapi.Idea.repositories.IdeaRepository;
import com.successfulliferestapi.Idea.repositories.IdeaTagRepository;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.models.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IdeaService {

    private final IdeaRepository ideaRepository;
    private final GoalRepository goalRepository;
    private final IdeaTagRepository ideaTagRepository;
    private final ModelMapper modelMapper;

    //CREATE Idea
    public IdeaSuccessResponseDTO add(User user, AddIdeaDTO addIdeaDTO) {

        Idea idea = modelMapper.map(addIdeaDTO,Idea.class);
        idea.setUser(user);

        if (addIdeaDTO.getGoalId() != null) {
            Optional<Goal> optionalGoal = goalRepository.findByIdAndUserId(addIdeaDTO.getGoalId(),user.getId());
            if (optionalGoal.isEmpty()) {
                throw new GoalException(GoalMessages.Error.NOT_FOUND);
            }
            idea.setGoal(optionalGoal.get());
        }

        // Save the new idea to the database
        Idea savedIdea = ideaRepository.save(idea);

        if (addIdeaDTO.getTags() != null) {
            Set<IdeaTag> newTags = new HashSet<>();
            for (String newTagName : addIdeaDTO.getTags()) {
                // Check if there is an existing tag with the same name
                IdeaTag existingTag = ideaTagRepository.findByName(newTagName);
                if (existingTag != null) {
                    newTags.add(existingTag);
                } else {
                    // If there is no existing tag, create a new one
                    IdeaTag newTag = new IdeaTag(newTagName);
                    newTags.add(newTag);
                }
            }
            savedIdea.setTags(newTags);
            savedIdea = ideaRepository.save(idea);
        }
        // Convert the idea entity to a DTO
        IdeaDTO ideaDTO = modelMapper.map(savedIdea, IdeaDTO.class);

        return new IdeaSuccessResponseDTO(IdeaMessages.Success.ADDED,ideaDTO);
    }

    //UPDATE Idea
    public IdeaSuccessResponseDTO update(Long ideaId, Long userId, EditIdeaDTO editIdeaDTO) {
        // Retrieve the existing Idea entity from the database
        Idea idea = ideaRepository.findByIdAndUserId(ideaId, userId)
                .orElseThrow(() -> new IdeaException(IdeaMessages.Error.NOT_FOUND));

        boolean isThereChange = false;

        // Update the title, if provided in the DTO
        if (editIdeaDTO.getTitle() != null && !editIdeaDTO.getTitle().equals(idea.getTitle())) {
            Optional<Idea> existingIdeaWithTitle = ideaRepository.findByTitle(editIdeaDTO.getTitle());
            if (existingIdeaWithTitle.isPresent()) {
                throw new IdeaException(IdeaMessages.Error.DUPLICATE_TITLE);
            }
            idea.setTitle(editIdeaDTO.getTitle());
            isThereChange = true;
        }

        // Update the description, if provided in the DTO
        if (editIdeaDTO.getDescription() != null) {
            idea.setDescription(editIdeaDTO.getDescription());
            isThereChange = true;
        }

        // Update the tags, if provided in the DTO
        if (editIdeaDTO.getTags() != null && !editIdeaDTO.getTags().isEmpty()) {
            Set<String> newTagNames = editIdeaDTO.getTags();
            Set<IdeaTag> newTags = new HashSet<>();
            for (String newTagName : newTagNames) {
                // Check if there is an existing tag with the same name
                IdeaTag existingTag = ideaTagRepository.findByName(newTagName);
                if (existingTag != null) {
                    newTags.add(existingTag);
                } else {
                    // If there is no existing tag, create a new one
                    IdeaTag newTag = new IdeaTag(newTagName);
                    newTags.add(newTag);
                }
            }
            idea.setTags(newTags);
            isThereChange = true;
        }

        if (!isThereChange) {
            throw new IdeaException(IdeaMessages.Error.NO_CHANGES);
        }

        // Save the updated Idea entity to the database
        Idea updatedIdea = ideaRepository.save(idea);

        // Convert the updated Idea entity to a DTO and return it
        IdeaDTO ideaDTO = modelMapper.map(updatedIdea, IdeaDTO.class);
        return new IdeaSuccessResponseDTO(IdeaMessages.Success.UPDATED, ideaDTO);
    }

    //DELETE Idea
    public SuccessResponseDTO deleteIdea(Long id, User user) {
        // Retrieve the existing Idea entity from the database
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new IdeaException(IdeaMessages.Error.NOT_FOUND));

        //Check if the user is owner of the idea
        if (!idea.getUser().getEmail().equals(user.getEmail())) {
            throw new IdeaException(IdeaMessages.Error.NOT_FOUND);
        }

        ideaRepository.delete(idea);
        return new SuccessResponseDTO(IdeaMessages.Success.DELETED);
    }

    //GET Idea By ID
    public IdeaDTO getIdeaById(Long id, Long userId) {

        // Retrieve the existing Idea entity from the database
        Idea idea = ideaRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IdeaException(IdeaMessages.Error.NOT_FOUND));

        return modelMapper.map(idea, IdeaDTO.class);
    }

    //GET All user Ideas
    public Page<IdeaDTO> findByUserId(Long userId, Pageable pageable) {

        // Retrieve the existing user Ideas from the database
        Page<Idea> ideasPage = ideaRepository.findAllByUserId(userId, pageable);

        if (ideasPage == null) {
            throw new IdeaException(IdeaMessages.Error.NO_IDEAS_YET);
        }

        return ideasPage.map(i -> modelMapper.map(i, IdeaDTO.class));
    }

    //GET All Goal Ideas
    public Page<IdeaDTO> getGoalIdeas(Long userId, Long goalId, Pageable pageable) {
        Page<Idea> ideasPage = ideaRepository.findByUserIdAndGoalId(userId, goalId, pageable);
        return ideasPage.map(idea -> {
            IdeaDTO ideaDTO = modelMapper.map(idea, IdeaDTO.class);
            ideaDTO.setGoalId(idea.getGoal().getId());
            return ideaDTO;
        });
    }

    //GET Goal Ideas By Tag
    public Page<IdeaDTO> getGoalIdeasByTag(Long userId, Long goalId, String tagName, Pageable pageable) {
        Optional<IdeaTag> optionalTag = Optional.ofNullable(ideaTagRepository.findByName(tagName));
        if (optionalTag.isEmpty()) {
            throw new IdeaException(IdeaMessages.Error.NO_IDEAS_BY_TAG);
        }
        Page<Idea> ideasPage =
                ideaRepository.findByUserIdAndGoalIdAndTags_Id(
                        userId,
                        goalId,
                        optionalTag.get().getId(),
                        pageable
                );
        return ideasPage.map(idea -> modelMapper.map(idea, IdeaDTO.class));
    }

    //GET Ideas By TAG
    public Page<IdeaDTO> getIdeasByTag(Long userId, String tagName, Pageable pageable) {
        Optional<IdeaTag> optionalTag = Optional.ofNullable(ideaTagRepository.findByName(tagName));
        if (optionalTag.isEmpty()) {
            throw new IdeaException(IdeaMessages.Error.NO_IDEAS_BY_TAG);
        }
        Page<Idea> ideasPage = ideaRepository.findByUserIdAndTags_Id(userId, optionalTag.get().getId(), pageable);
        return ideasPage.map(idea -> modelMapper.map(idea, IdeaDTO.class));
    }

    //GET Idea Tags
    public Set<String> getUserIdeaTags(Long userId) {
        List<Idea> ideas = ideaRepository.findAllByUserId(userId);

        Set<String> tags = new HashSet<>();
        for (Idea idea : ideas) {
            for (IdeaTag tag : idea.getTags()) {
                tags.add(tag.getName());
            }
        }

        return tags;
    }

    //GET Goal Ideas tags
    public Set<String> getGoalIdeasTags(Long userId,Long goalId) {
        List<Idea> ideas = ideaRepository.findAllByUserIdAndGoalId(userId,goalId);

        Set<String> tags = new HashSet<>();
        for (Idea idea : ideas) {
            for (IdeaTag tag : idea.getTags()) {
                tags.add(tag.getName());
            }
        }

        return tags;
    }

}
