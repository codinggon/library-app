package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLeanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.repository.book.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }


    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    public void leanBook(BookLeanRequest request) {
        //1. 책 정보를 가져온다.
        Book book = bookRepository.findByName(request.getBookName()).orElseThrow(IllegalArgumentException::new);

        //2. 대출 기록 정보를 확인해서 대출중인지 확인
        //3. 확인 후 대출중이라면 예외를 발생시킨다.
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("대출되어있는 책입니다.");
        }

        // 4. 유저 정보를 가져온다.
        //5. 유저정보와 책 정보를 깁나으로 UserLoanHistory를 저장
        User user = userRepository.findByName(request.getUserName());

//        userLoanHistoryRepository.save(new UserLoanHistory(user,book.getName()));
        user.loanBook(book.getName());
    }

    public void returnBook(BookReturnRequest request) {
        // 4. 유저 정보를 가져온다.
        //5. 유저정보와 책 정보를 깁나으로 UserLoanHistory를 저장
        User user = userRepository.findByName(request.getUserName());
        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName()).orElseThrow(IllegalArgumentException::new);
        history.doReturn();
    }
}
