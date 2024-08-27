package siit.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NameValidationTest {
    NameValidation nameValidationMock;
    @BeforeEach
    public void setUp(){
        nameValidationMock=mock(NameValidation.class);
    }
    @ParameterizedTest
    @ValueSource(strings = {"adriana","","T"})
    void nameIsEmptyOrNull(String name) {
      doThrow(new IllegalArgumentException("mocked exception message")).when(nameValidationMock).nameIsEmptyOrNull(name);
      assertThrows(IllegalArgumentException.class,()->{
         nameValidationMock.nameIsEmptyOrNull(name);
      });
    }

    @ParameterizedTest
    @ValueSource(strings = {"adriana","$$$$$","T_.ogh","%%%%"})
    void verifyNameFormat(String name) {
        doThrow(new IllegalArgumentException("mocked exception message")).when(nameValidationMock).verifyNameFormat(name);
        assertThrows(IllegalArgumentException.class,()->{
            nameValidationMock.verifyNameFormat(name);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"adrianaAlexandraBorodCampean","","t"})
    void verifyNameLength(String name) {
        doThrow(new IllegalArgumentException("mocked exception message")).when(nameValidationMock).verifyNameLength(name);
        assertThrows(IllegalArgumentException.class,()->{
            nameValidationMock.verifyNameLength(name);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"adriana","","t","@@@","987"})
    void isOnlyLetters(String name) {
        when(nameValidationMock.isOnlyLetters(name)).thenReturn(true);

        boolean result=nameValidationMock.isOnlyLetters(name);

        assertTrue(result);
    }
}