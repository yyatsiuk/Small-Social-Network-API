package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.GroupDTO;
import com.yyatsiuk.social.sonet.dto.request.CreateGroupRequest;
import com.yyatsiuk.social.sonet.dto.request.UpdateGroupRequest;
import com.yyatsiuk.social.sonet.dto.response.GroupPageableResponse;
import com.yyatsiuk.social.sonet.model.Group;
import com.yyatsiuk.social.sonet.service.GroupService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("SpringElInspection")
@RestController
@RequestMapping("api/groups")
public class GroupController {

    private final GroupService groupService;
    private final ModelMapper mapper;
    private final CustomModelMapper<Group, GroupDTO> customMapper;

    @Autowired
    public GroupController(
            GroupService groupService,
            ModelMapper mapper,
            @Qualifier("groupMapper") CustomModelMapper<Group, GroupDTO> customMapper
    ) {
        this.groupService = groupService;
        this.mapper = mapper;
        this.customMapper = customMapper;
    }

    @GetMapping
    public ResponseEntity<GroupPageableResponse> getGroups(Pageable pageable) {
        Page<Group> groupPage = groupService.getAllGroups(pageable);

        return handlePageableResponse(groupPage);

    }

    @GetMapping("/creator")
    public ResponseEntity<Boolean> isGroupCreator(@Valid @RequestParam Long userId, @RequestParam Long groupId) {
        boolean response = groupService.isGroupCreator(userId, groupId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable("groupId") Long groupId) {
        return new ResponseEntity<>(customMapper.toDTO(groupService.findById(groupId)), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<GroupPageableResponse> getByName(
            @RequestParam("name") String groupName, Pageable pageable) {
        Page<Group> groupPage = groupService.findByName(groupName, pageable);
        return handlePageableResponse(groupPage);
    }

    @PostMapping("/create")
    public ResponseEntity<GroupDTO> createGroup(
            @Valid @RequestBody CreateGroupRequest createRequest) {

        Group group = groupService.create(createRequest);
        return new ResponseEntity<>(mapper.map(group, GroupDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/{groupId}")
    @PreAuthorize("isGroupCreator(#groupId)")
    public ResponseEntity<GroupDTO> updateGroup(
            @Valid @RequestBody UpdateGroupRequest updateRequest,
            @PathVariable Long groupId
    ) {

        Group group = groupService.update(updateRequest, groupId);
        return new ResponseEntity<>(mapper.map(group, GroupDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("isGroupCreator(#groupId)")
    public ResponseEntity deleteGroup(@PathVariable Long groupId) {
        groupService.delete(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<GroupPageableResponse> handlePageableResponse(Page<Group> groupPage) {
        Integer numbersOfPage = groupPage.getTotalPages();
        List<Group> groupList = groupPage.getContent();

        List<GroupDTO> groupDTOs =
                mapper.map(groupList, new TypeToken<List<GroupDTO>>() {
                }.getType());


        GroupPageableResponse response = new GroupPageableResponse();
        response.setGroupDTOList(groupDTOs);
        response.setNumberOfPages(numbersOfPage);
        response.setAmountOfGroups(groupPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
