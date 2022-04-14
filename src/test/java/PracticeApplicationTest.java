import net.bromex.PracticeApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PracticeApplicationTest {

    @Spy
    PracticeApplication testObj;

    @BeforeEach
    public void setUp() {
        testObj = new PracticeApplication();
    }

    @Test
    public void testPracticeApplication() {
        Assertions.assertNotNull(testObj);
    }

}
