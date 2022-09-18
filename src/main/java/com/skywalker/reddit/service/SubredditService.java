package com.skywalker.reddit.service;

import com.skywalker.reddit.dto.SubredditDto;
import com.skywalker.reddit.entity.Subreddit;
import com.skywalker.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit subreddit = subredditRepository.save(populateSubredditEntity(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    private Subreddit populateSubredditEntity(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getSubredditName())
                .description(subredditDto.getDescription())
                .build();
    }

    @Transactional
    public List<SubredditDto> getAllSubreddit(){
        return subredditRepository.findAll()
                .stream()
                .map(this::populateSubredditDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Subreddit does not exists"));
        return this.populateSubredditDto(subreddit);
    }

    private SubredditDto populateSubredditDto(Subreddit subreddit){
        return SubredditDto.builder()
                .id(subreddit.getId())
                .subredditName(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
}
