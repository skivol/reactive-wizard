package se.fortnox.reactivewizard;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.mockito.Mockito.verify;

public class SetupModuleTest {

    @Test
    public void testLogging() {
        final Logger mock = Mockito.mock(Logger.class);
        final RuntimeException error = new RuntimeException("");

        SetupModule.logError(mock, error);

        verify(mock).warn("Tried to send item or error to subscriber but the subscriber had already left. " +
            "This could happen when you merge two (or more) observables and one reports an error while the other a moment later tries to " +
            "call the subscriber with another error but the subscriber already left at the first error.", error);
    }
}
