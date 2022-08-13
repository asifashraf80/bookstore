/**
 * @Project : Library Management
 * FileName : BookServiceImpl.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.service.impl;

import static com.bookstore.mgmt.common.Constants.ZERO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bookstore.mgmt.common.Utility;
import com.bookstore.mgmt.common.Utility.OrderReference;
import com.bookstore.mgmt.domain.Book;
import com.bookstore.mgmt.domain.BookOrder;
import com.bookstore.mgmt.domain.BookType;
import com.bookstore.mgmt.domain.BookTypePromotion;
import com.bookstore.mgmt.domain.OrderItem;
import com.bookstore.mgmt.domain.Promotion;
import com.bookstore.mgmt.dto.BaseSearchRequest.Filter;
import com.bookstore.mgmt.dto.BookDTO;
import com.bookstore.mgmt.dto.BookRequest;
import com.bookstore.mgmt.dto.BookTypeDTO;
import com.bookstore.mgmt.dto.CheckoutRequest;
import com.bookstore.mgmt.dto.OrderDTO;
import com.bookstore.mgmt.dto.OrderItemDTO;
import com.bookstore.mgmt.dto.PaginatedResults;
import com.bookstore.mgmt.dto.SearchBookRequest;
import com.bookstore.mgmt.exception.BookNotFoundException;
import com.bookstore.mgmt.exception.DupplicateBookException;
import com.bookstore.mgmt.exception.InvalidCheckoutRequestException;
import com.bookstore.mgmt.exception.InvalidSearchCriteriaException;
import com.bookstore.mgmt.repository.BookRepository;
import com.bookstore.mgmt.repository.BookTypeRepository;
import com.bookstore.mgmt.repository.OrderItemRepository;
import com.bookstore.mgmt.repository.OrderRepository;
import com.bookstore.mgmt.repository.PromotionRepository;
import com.bookstore.mgmt.service.BookService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Sajid
 *
 */
@Service
@Log4j2
public class BookServiceImpl implements BookService {

	/**
	 * the bookTypeRepository
	 */
	@Autowired
	private BookTypeRepository bookTypeRepo;

	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Value("#{'${bookstore.allowed.criterias}'.split(',')}")
	private List<String> allowedFilters = new ArrayList<String>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public BookDTO saveBook(BookRequest request) {
		log.info("Saving Book Service....");
		Assert.notNull(request.getIsbn(), "ISBN is required");
		Book book = null;
		if (null == request.getId()) {
			book = handleNewRequest(request);
		} else {
			book = handleUpdateRequest(request);
		}

		Assert.notNull(book, "Unable to Save Book");
		BookDTO dto = bookToDTO(book);
		return dto;

	}

	/**
	 * Handle UpdateBook Request
	 * 
	 * @param request
	 * @Author : Sajid
	 */
	private Book handleUpdateRequest(BookRequest request) {
		Book book = bookRepo.findByIdAndCurrent(request.getId(), Boolean.TRUE).orElse(null);
		validateUpdateRequest(request, book);
		log.debug("Marking Exiting Book as Not current");
		book.setCurrent(Boolean.FALSE);
		Book newRecord = generateNewRecord(request);
		return newRecord;
	}

	/**
	 * Handle New Book Request
	 * 
	 * @param request
	 * @return
	 * @Author : Sajid
	 */
	private Book handleNewRequest(BookRequest request) {
		log.debug("Handing New Book Request");
		validateNewBook(request);
		Book newRecord = generateNewRecord(request);
		return newRecord;
	}

	/**
	 * @param request
	 * @param bookType
	 * @return
	 * @Author : Sajid
	 */
	private Book generateNewRecord(BookRequest request) {
		log.debug("Creating new Version of Book");
		BookType bookType = bookTypeRepo.findByCode(request.getBookTypeCode());
		Assert.notNull(bookType, "Book Type is not Available");
		Book newRecord = new Book();
		BeanUtils.copyProperties(request, newRecord, "bookTypeCode", "id");
		newRecord.setCurrent(Boolean.TRUE);
		newRecord.setDeleted(Boolean.FALSE);
		newRecord.setBookType(bookType);
		bookRepo.save(newRecord);
		return newRecord;
	}

	/**
	 * Validate UpdateBook request
	 * 
	 * @param request
	 * @param existingRecord
	 * @Author : Sajid
	 */
	private void validateUpdateRequest(BookRequest request, Book existingRecord) {
		log.debug("Validating Update Book Request...");
		if (null == existingRecord) {
			throw new BookNotFoundException("Book not found!");
		}
		Book byIsbn = bookRepo.findByIsbnAndDeletedAndCurrent(request.getIsbn(), Boolean.FALSE,Boolean.TRUE);
		if (byIsbn != null && !byIsbn.getId().equals(existingRecord.getId())) {
			throw new DupplicateBookException("Another Book already exists with ISBN!");
		}
	}

	/**
	 * Validate New Book Request
	 * 
	 * @param request
	 * @Author : Sajid
	 */
	private void validateNewBook(BookRequest request) {
		log.debug("Validating New Record...");
		Book book = bookRepo.findByIsbnAndDeleted(request.getIsbn(), Boolean.FALSE);
		if (book != null) {
			throw new DupplicateBookException("Another Book already exists with ISBN");
		}
	}

	private PageRequest createPageRequest(Long currentPage, Long pageSize) {
		Assert.notNull(currentPage, "Invalid Current page value");
		Assert.notNull(pageSize, "Invalid page size value");
		PageRequest pageRequest = PageRequest.of(currentPage.intValue(), pageSize.intValue());
		return pageRequest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public PaginatedResults<BookDTO> searchBook(SearchBookRequest request) {
		log.debug("Searching Books by Criteria");
		PageRequest pageRequest = createPageRequest(request.getPageNo(), request.getPageSize());
		Specification<Book> searchFilter = Specification
				.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("current"), Boolean.TRUE))
				.and((Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleted"),
						Boolean.FALSE))	;
		if (!Utility.isNullOrEmpty(request.getFilters())) {
			for (Filter filter : request.getFilters()) {
				searchFilter = applyFilters(filter, searchFilter);
			}
		}
		Page<Book> searchResult = bookRepo.findAll(searchFilter, pageRequest);
		return generatedPaginatedResult(searchResult, request.getPageNo().intValue(), request.getPageSize().intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public String deleteBook(String isbn) {
		log.debug("Deleting Book {}...", isbn);
		Book book = bookRepo.findByIsbnAndDeleted(isbn, Boolean.FALSE);
		Assert.notNull(book, "Book not found");
		book.setDeleted(Boolean.TRUE);
		return "Success";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public OrderDTO checkout(CheckoutRequest request) {
		log.debug("Checkout Books");

		log.debug("Creating New Order.....");
		// create new order
		BookOrder order = new BookOrder();
		order.setReferenceNumber(OrderReference.generateOrderReference());
		orderRepository.save(order);

		List<Book> books = new ArrayList<Book>();
		request.getBookIds().forEach(bookId -> {
			// Search all selected books
			Specification<Book> searchFilter = Specification
					.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), bookId))
					.and((Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleted"),
							Boolean.FALSE))
					.and((Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("current"),
							Boolean.TRUE));
			Book book = bookRepo.findOne(searchFilter).orElse(null);
			if (null == book) {
				throw new BookNotFoundException("Book Not Found BookId " + bookId);
			}
			books.add(book);

		});

		
		if(Utility.isNullOrEmpty(request.getPromocode())) {
			// add All books to order without applying promotion
			books.forEach(book->addOrderItems(order, book, null));
		}else {
			//apply promotion and add books to order
			applyPromotion(books, request.getPromocode(), order);	
		}
		OrderDTO response = generateCheckoutResponse(order);
		return response;
	}

	/**
	 * Generate Response for Checkout Service
	 * 
	 * @param order
	 * @return
	 * @Author : Sajid
	 */
	private OrderDTO generateCheckoutResponse(BookOrder order) {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setOrderReference(order.getReferenceNumber());
		if(null != order.getPromotion()) {
			orderDto.setPromotionCode(order.getPromotion().getCode());
		}
		List<OrderItemDTO> dtoList = order.getItems().stream().map(orderItem -> {
			OrderItemDTO dto = new OrderItemDTO();
			BeanUtils.copyProperties(orderItem.getBook(), dto, "bookType");
			BookTypeDTO bookType = new BookTypeDTO();
			BeanUtils.copyProperties(orderItem.getBook().getBookType(), bookType);
			dto.setBookType(bookType);
			dto.setDiscountRate(orderItem.getDiscountRate());
			dto.setPrice(orderItem.getPrice());
			dto.setDiscountedPrice(calculateDiscountedPrice(orderItem.getPrice(), orderItem.getDiscountRate()));
			return dto;
		}).collect(Collectors.toList());
		orderDto.setItems(dtoList);
		Double totalAmount = orderDto.getItems().stream().mapToDouble(orderItem -> orderItem.getDiscountedPrice())
				.sum();
		orderDto.setTotalAmount(totalAmount);
		return orderDto;
	}

	/**
	 * Calculate Discounted price
	 * 
	 * @param orignalPrice
	 * @param discountRate
	 * @return
	 * @Author : Sajid
	 */

	private Double calculateDiscountedPrice(Double orignalPrice, Double discountRate) {
		if (null != discountRate && discountRate == 0) {
			return orignalPrice;
		}
		return (orignalPrice - (orignalPrice * (discountRate / 100)));

	}

	/**
	 * Apply promotion discount on book order
	 * 
	 * @param books
	 * @param promotionCode
	 */
	private void applyPromotion(List<Book> books, String promotionCode, BookOrder order) {
		log.debug("apply promotion code for checkout");
		
		Assert.notNull(promotionCode, "Promotion Code is required");
		Promotion promotion = promotionRepository.findByCode(promotionCode);
		Assert.notNull(promotion, "Promotion is not Available");
		Set<BookTypePromotion> bookTypePromotions = promotion.getPromotions();
		order.setPromotion(promotion);
		if (Utility.isNullOrEmpty(books)) {
			throw new InvalidCheckoutRequestException("No Books are available");
		}
		for (Book book : books) {
			Optional<BookTypePromotion> bookTypePromotion = bookTypePromotions.stream()
					.filter(btPromotion -> btPromotion.getBookType().getCode().equals(book.getBookType().getCode()))
					.findFirst();
			addOrderItems(order, book, bookTypePromotion.orElse(null));
		}
	}

	private void addOrderItems(BookOrder order, Book book, BookTypePromotion bookTypePromo) {
		OrderItem item = new OrderItem();
		item.setBook(book);
		if (null != bookTypePromo) {
			item.setDiscountRate(bookTypePromo.getDiscountRate());
		} else {
			item.setDiscountRate(ZERO.doubleValue());
		}

		item.setOrder(order);
		item.setPrice(book.getPrice());
		orderItemRepository.save(item);
		order.getItems().add(item);
	}

	/**
	 * Apply Filters for Search
	 * 
	 * @param filter
	 * @param searchFilter
	 */
	private Specification<Book> applyFilters(Filter filter, Specification<Book> searchFilter) {
		String filterId = filter.getId();

		if (Utility.isNullOrEmpty(filterId)) {
			throw new InvalidSearchCriteriaException("Filter Id is required");
		}

		if (!allowedFilters.contains(filterId)) {
			throw new InvalidSearchCriteriaException("Invalid search criteria.");
		}

		StringBuilder value = new StringBuilder("%").append(filter.getValue().toUpperCase()).append("%");
		searchFilter = searchFilter.and((Specification) (root, query, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.upper(root.get(filter.getId())), value.toString()));
		return searchFilter;
	}

	/**
	 * Generate Paginated Result
	 * 
	 * @param searchResult
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @Author : Sajid
	 */
	private PaginatedResults<BookDTO> generatedPaginatedResult(Page<Book> searchResult, int currentPage, int pageSize) {
		log.debug("Generating Paginated Result ...");
		List<BookDTO> searchData = new ArrayList<BookDTO>();
		PaginatedResults<BookDTO> searchPage = null;
		if (null != searchResult) {
			searchData = mapBookToDto(searchResult.getContent());
			int totalElements = (int) searchResult.getTotalElements();
			searchPage = new PaginatedResults<BookDTO>(searchData, totalElements, pageSize, currentPage,
					searchResult.getTotalPages());
		}
		return searchPage;
	}

	private List<BookDTO> mapBookToDto(List<Book> books) {
		List<BookDTO> dtoList = books.stream().map(book -> {
			return bookToDTO(book);
		}).collect(Collectors.toList());
		return dtoList;
	}

	/**
	 * @param book
	 * @return
	 * @Author : Sajid
	 */
	private BookDTO bookToDTO(Book book) {
		BookDTO dto = new BookDTO();
		BeanUtils.copyProperties(book, dto, "bookType");
		BookTypeDTO bookType = new BookTypeDTO();
		BeanUtils.copyProperties(book.getBookType(), bookType);
		dto.setBookType(bookType);
		return dto;
	}
}
