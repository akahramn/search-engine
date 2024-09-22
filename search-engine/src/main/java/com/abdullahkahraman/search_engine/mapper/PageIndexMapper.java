package com.abdullahkahraman.search_engine.mapper;

import com.abdullahkahraman.search_engine.dto.SearchResponseDto;
import com.abdullahkahraman.search_engine.model.PageIndex;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PageIndexMapper {

    PageIndexMapper INSTANCE = Mappers.getMapper(PageIndexMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "link", target = "link")
    SearchResponseDto pageIndexToSearchResponseDto(PageIndex pageIndex);
}
