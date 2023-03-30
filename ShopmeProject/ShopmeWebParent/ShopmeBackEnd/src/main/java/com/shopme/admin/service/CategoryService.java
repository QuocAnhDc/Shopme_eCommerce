package com.shopme.admin.service;

import com.shopme.admin.repository.CategoryRepository;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll(){
        return (List<Category>) categoryRepository.findAll();
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> listCategoriesUsedInForm(){

        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = categoryRepository.findAll();

        for (Category category : categoriesInDB){
            if(category.getParent() == null){
                categoriesUsedInForm.add(new Category(category.getId(),category.getName()));

                Set<Category> children = category.getChildren();

                for (Category subCategory : children){
                    String name = "--"+subCategory.getName();
                    categoriesUsedInForm.add(new Category(subCategory.getId(),name));
                    listChildren(categoriesUsedInForm,subCategory,1);
                }
            }
        }

        return categoriesUsedInForm;
    }
    private void listChildren(List<Category> categoriesUsedInForm, Category parent,int subLevel){
        int newSubLevel = subLevel+1;
        Set<Category> children = parent.getChildren();
        for(Category subCategory : children){
            String name="";
            for(int i =0;i<newSubLevel;i++){
                name+="--";
            }

            categoriesUsedInForm.add(new Category(subCategory.getId(),name+subCategory.getName()));
            listChildren(categoriesUsedInForm,subCategory,newSubLevel);
        }
    }

}
