package com.statistics.controllers;

import com.statistics.models.Transaction;
import com.statistics.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TransactionControllerTest {

    @Mock
    TransactionService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldCacheTransaction() throws Exception {
        TransactionController controller = new TransactionController(service);

        controller.processTransaction(any(Transaction.class));

        verify(service, times(1)).cacheTransaction(any(Transaction.class));
    }

    @Test
    public void shouldReturnHttpStatus201WhenTransactionIsCached() throws Exception {
        ResponseEntity cachedTransactionResponse = new ResponseEntity(HttpStatus.CREATED);

        doReturn(cachedTransactionResponse).when(service).cacheTransaction(any(Transaction.class));

        TransactionController controller = new TransactionController(service);
        ResponseEntity response = controller.processTransaction(any(Transaction.class));

        assertThat(response, is(cachedTransactionResponse));
    }

    @Test
    public void shouldReturnHttpStatus204WhenTransactionNotCached() throws Exception {
        ResponseEntity notCachedTransactionResponse = new ResponseEntity(HttpStatus.NO_CONTENT);

        doReturn(notCachedTransactionResponse).when(service).cacheTransaction(any(Transaction.class));

        TransactionController controller = new TransactionController(service);
        ResponseEntity response = controller.processTransaction(any(Transaction.class));

        assertThat(response, is(notCachedTransactionResponse));
    }
}