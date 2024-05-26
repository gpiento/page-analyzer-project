package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import io.javalin.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuildUrlPage {

    private Url url;
    private Map<String, List<ValidationError<Object>>> errors; // <key, value>
}
