package jimnorth1982.javagqlshowcase.controller;

import jimnorth1982.javagqlshowcase.model.RentalAgreement;
import jimnorth1982.javagqlshowcase.model.RentalAgreementInput;
import jimnorth1982.javagqlshowcase.model.Tool;
import jimnorth1982.javagqlshowcase.repo.RentalAgreementRepository;
import jimnorth1982.javagqlshowcase.repo.ToolRepository;
import jimnorth1982.javagqlshowcase.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import static jimnorth1982.javagqlshowcase.calendar.Holidays.defaultHolidays;

@AllArgsConstructor
@Controller
public class RentalAgreementController {
    private final RentalAgreementRepository rentalAgreementRepository;
    private final ToolRepository toolRepository;

    @QueryMapping
    public RentalAgreement findRentalAgreementById(@Argument Long id) {
        return rentalAgreementRepository.findById(id);
    }

    @QueryMapping
    public List<RentalAgreement> findAllRentalAgreements() {
        return rentalAgreementRepository.findAll();
    }

    @MutationMapping
    public RentalAgreement createRentalAgreement(@Argument RentalAgreementInput rentalAgreementInput) throws ValidationException {
        Tool tool = toolRepository.findById(rentalAgreementInput.toolId());
        RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalAgreementInput)
                .validate()
                .finalizeAgreement(defaultHolidays());

        return rentalAgreementRepository.save(rentalAgreement);
    }
}
