package ir.mehdi.mycleanarch.usecases.cousine;

import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.repositories.CousinRepository;

import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

import java.util.List;


public class GetAllCousinsUseCase extends UseCase<GetAllCousinsUseCase.InputValues, GetAllCousinsUseCase.OutputValues> {
    private final CousinRepository repository;

    public GetAllCousinsUseCase(CousinRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.getAll());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<Cousin> cousins;
    }
}
