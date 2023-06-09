package com.shopme.admin.repository;

import com.shopme.common.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL ")
    public List<Category> findRootCategories();
}
