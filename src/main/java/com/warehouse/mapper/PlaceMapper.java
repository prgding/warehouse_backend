package com.warehouse.mapper;

import com.warehouse.entity.Place;

import java.util.List;

public interface PlaceMapper {

    //查询所有产地
    public List<Place> findAllPlace();
}
