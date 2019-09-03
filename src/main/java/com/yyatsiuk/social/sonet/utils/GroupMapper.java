package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.GroupDTO;
import com.yyatsiuk.social.sonet.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("groupMapper")
public class GroupMapper implements CustomModelMapper<Group, GroupDTO>{

    private final ModelMapper modelMapper;

    @Autowired
    public GroupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GroupDTO toDTO(Group group) {
        GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
        groupDTO.setCreatorId(group.getCreator().getId());
        return groupDTO;
    }
}
