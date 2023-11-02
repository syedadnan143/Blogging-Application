package com.cwd.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.cwd.blog.entities.Category;
import com.cwd.blog.exceptions.ResoueceNotFountException;
import com.cwd.blog.payloads.CategoryDto;
import com.cwd.blog.repositories.CategoryRepo;
import com.cwd.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
          
	  @Autowired
	  private CategoryRepo categoryRepo;
	  @Autowired
	  private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categorydto) {
		// TODO Auto-generated method stub
	    Category cat = 	this.modelMapper.map(categorydto, Category.class);
	    Category addedCat=  this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> 
		new ResoueceNotFountException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> 
		new ResoueceNotFountException("Category","category id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat =this.categoryRepo.findById(categoryId).orElseThrow(()->
		new ResoueceNotFountException("Category","category id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories =  this.categoryRepo.findAll();
		List<CategoryDto> catDtos =  categories.stream().map((cat)-> 
		this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

}
