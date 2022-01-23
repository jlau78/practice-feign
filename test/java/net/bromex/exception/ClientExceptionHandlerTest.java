package net.bromex.exception;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientExceptionHandlerTest {

    ClientExceptionHandler testObj;

    @Mock
    NotImplementedException nIException;
    @Mock
    FeignException.NotFound notFoundException;


    @BeforeEach
    public void setUp() {
        testObj = new ClientExceptionHandler();

    }

    @Test
    public void testNotImplementedException() {
        ResponseEntity<ErrorMessage> response = testObj.handleException(nIException);

        assertNotNull(response);
    }

    @Test
    public void testNotFoundException() {
        ResponseEntity<ErrorMessage> response = testObj.handleNotFound(notFoundException);

        assertNotNull(response);
        assertEquals("ERR404", response.getBody().getCode());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
