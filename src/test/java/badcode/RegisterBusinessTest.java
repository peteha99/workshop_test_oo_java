package badcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class mockSpeakerRepository implements SpeakerRepository {
    @Override
    public Integer saveSpeaker(Speaker speaker) {
        return 1;
    }
}
    public class RegisterBusinessTest {

        private RegisterBusiness business = new RegisterBusiness();

        @Test
        public void casePass01() {
            RegisterBusiness registerBusiness = new RegisterBusiness();
            Speaker speaker = new Speaker();
            speaker.setFirstName("pete");
            speaker.setLastName("srw");
            speaker.setEmail("petesrw@gmail.com");
            int actualResult = registerBusiness.register(new mockSpeakerRepository(), speaker);

            assertEquals(1, actualResult);
        }

        @Test
        public void testRegisterCaseNameNullThrowsIfNotExit() {
            Exception exception = assertThrows(ArgumentNullException.class, () -> {
                business.register(null, new Speaker());
            });

            String expectedMessage = "First name is required.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

        }

        @Test
        public void testRegisterCaseLastNameNullThrowsIfNotExit() {
            Speaker speaker = new Speaker();
            speaker.setFirstName("test");
            Exception exception = assertThrows(ArgumentNullException.class, () -> {
                business.register(null, speaker);
            });

            String expectedMessage = "Last name is required.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

        }

        @Test
        public void testRegisterCaseEmailNullThrowsIfNotExit() {
            Speaker speaker = new Speaker();
            speaker.setFirstName("test");
            speaker.setLastName("lastname");
            Exception exception = assertThrows(ArgumentNullException.class, () -> {
                business.register(null, speaker);
            });

            String expectedMessage = "Email is required.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

        }

        @Test
        public void testRegisterCaseEmailIsNotDomainThrowsIfNotExit() {
            Speaker speaker = new Speaker();
            speaker.setFirstName("test");
            speaker.setLastName("lastname");
            speaker.setEmail("email@window.com");
            Exception exception = assertThrows(SpeakerDoesntMeetRequirementsException.class, () -> {
                business.register(null, speaker);
            });

            String expectedMessage = "Speaker doesn't meet our standard rules.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

        }

        @Test
        public void testRegisterCaseNotSaveSpeakerThrowsIfNotExit() {
            Speaker speaker = new Speaker();
            speaker.setFirstName("test");
            speaker.setLastName("lastname");
            speaker.setEmail("email@live.com");
            Exception exception = assertThrows(SaveSpeakerException.class, () -> {
                business.register(null, speaker);
            });

            String expectedMessage = "Can't save a speaker.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));

        }

        @Test
        public void testGetEmailDomainCaseEmailIsNotDomainThrowsIfNotExit() {
            try {
                business.getEmailDomain("test");
                fail();
            } catch (DomainEmailInvalidException e) {

            }
        }
    }