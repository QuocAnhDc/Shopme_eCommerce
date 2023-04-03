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
        List<Category> rootCategories = categoryRepository.findRootCategories();
        return listHierarchicalCategories(rootCategories);
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories){
        List<Category> hierarchicalCategories = new ArrayList<>();

        for(Category rootCategory : rootCategories){
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();
            for(Category subCategory : children){
                String name = "--"+subCategory.getName();
                subCategory = Category.copyFull(subCategory,name);
                hierarchicalCategories.add(subCategory);

                listSubHierarchicalCategories(hierarchicalCategories,subCategory,1);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,Category parent,int subLevel){
        Set<Category> children = parent.getChildren();
        int newSubLevel = subLevel+1;
        for(Category subCategory : children){
            String name ="";
            for(int i =0;i<subLevel;i++){
                name+="--";
            }

            name+=subCategory.getName();
            hierarchicalCategories.add(Category.copyFull(subCategory,name));

            listSubHierarchicalCategories(hierarchicalCategories,subCategory,newSubLevel);
        }
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
                    listSubCategoryUsedInForm(categoriesUsedInForm,subCategory,1);
                }
            }
        }

        return categoriesUsedInForm;
    }
    private void listSubCategoryUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel){
        int newSubLevel = subLevel+1;
        Set<Category> children = parent.getChildren();
        for(Category subCategory : children){
            String name="";
            for(int i =0;i<newSubLevel;i++){
                name+="--";
            }

            categoriesUsedInForm.add(new Category(subCategory.getId(),name+subCategory.getName()));
            listSubCategoryUsedInForm(categoriesUsedInForm,subCategory,newSubLevel);
        }
    }

}
