package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//todo: remove AllArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public final class Url {

    private Long id;

    @ToString.Include
    private String name;
}
