package badcode;

import java.util.Arrays;
import java.util.Optional;

public class RegisterBusiness {

    public Integer register(SpeakerRepository repository, Speaker speaker) {
        Integer speakerId;
        String[] domains = {"gmail.com", "live.com"};

        validateRequest(speaker);
        String emailDomain = getEmailDomain(speaker.getEmail()); // Avoid ArrayIndexOutOfBound
        valiateEmail(domains, emailDomain);
        speaker.setRegistrationFee(getFee(speaker.getExp()));
        try {
            speakerId = repository.saveSpeaker(speaker);
        } catch (Exception exception) {
            throw new SaveSpeakerException("Can't save a speaker.");
        }

        return speakerId;
    }

    private void valiateEmail(String[] domains, String emailDomain) {
        Optional<String> checkEmailIsValid = Arrays.stream(domains).filter(a -> a.equals(emailDomain)).findFirst();
        if(!checkEmailIsValid.isPresent()) {
            throw new SpeakerDoesntMeetRequirementsException("Speaker doesn't meet our standard rules.");
        }
    }

    private void validateRequest(Speaker speaker) {
        if (isNullOrEmpty(speaker.getFirstName())) {
            throw new ArgumentNullException("First name is required.");
        }
        if (isNullOrEmpty(speaker.getLastName())) {
            throw new ArgumentNullException("Last name is required.");
        }
        if (isNullOrEmpty(speaker.getEmail())) {
            throw new ArgumentNullException("Email is required.");
        }
    }

    private boolean isNullOrEmpty(String firstName) {
        return firstName == null || firstName.trim().equals("");
    }

    int getFee(int experienceYear) {
        int fee = 0;
        if (experienceYear <= 1) {
            fee = 500;
        } else if (experienceYear <= 3) {
            fee = 250;
        } else if (experienceYear <= 5) {
            fee = 100;
        } else if (experienceYear <= 9) {
            fee = 50;
        }
        return fee;
    }

    public String getEmailDomain(String email) {
        String[] inputs = email.trim().split("@");
        if (inputs.length == 2) return inputs[1];
        throw new DomainEmailInvalidException();
    }

}
