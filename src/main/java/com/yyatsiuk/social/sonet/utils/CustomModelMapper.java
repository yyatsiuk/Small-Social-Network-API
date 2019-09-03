package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.DTO;
import com.yyatsiuk.social.sonet.model.entity.BaseEntity;

public interface CustomModelMapper<T extends BaseEntity, D extends DTO> {

      D toDTO(T entity);
}
