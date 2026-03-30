package com.blog.Blog_api.services;

import java.util.List;

import com.blog.Blog_api.payloads.CategoryDto;

public interface CategoryService {
    //create
    CategoryDto createCategory (CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    //delete
    public void deleteCategory(Integer categoryId);

    //get
    CategoryDto getCategory(Integer categoryId);

    //getAll
    List<CategoryDto> getCategories();

}
