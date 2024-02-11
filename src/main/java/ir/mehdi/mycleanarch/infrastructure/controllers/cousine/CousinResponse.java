package ir.mehdi.mycleanarch.infrastructure.controllers.cousine;

import ir.mehdi.mycleanarch.domain.models.Cousin;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class CousinResponse {
    private final Long id;
    private final String name;

    private static CousinResponse from(Cousin cousin) {
        return new CousinResponse(cousin.getId().getNumber(), cousin.getName());
    }
    
    public static List<CousinResponse> from(List<Cousin> cousins) {
        return cousins
                .stream()
                .map(CousinResponse::from)
                .collect(Collectors.toList());
    }
}
