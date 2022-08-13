/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.mgmt.domain.OrderItem;

@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {

}
